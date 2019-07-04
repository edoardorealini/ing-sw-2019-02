package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceServerControllerRMI;
import commons.PropertiesLoader;
import commons.ShootingParametersClient;
import controller.InputConverter;
import controller.MatchController;
import exception.*;
import model.Color;
import model.Match;
import model.ShootMode;
import model.ShootingParametersInput;
import model.map.Square;
import model.player.AbilityStatus;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;
import model.powerup.PowerUpName;
import model.weapons.Weapon;
import model.weapons.WeaponName;

import javax.security.auth.login.FailedLoginException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * This is the class that generates the remote object, called by the client!
 * This object contains the implementation of the InterfaceServerControllerRMI
 * Contains all the client exposed methods that can be called remotely and some more
 * @author edoardorealini
 * @author MADSOMMA
 * @author GioValca
 */
public class ServerControllerRMI extends UnicastRemoteObject implements InterfaceServerControllerRMI {

    private transient InputConverter converter;
    private transient MatchController matchController;
    private ArrayList<InterfaceClientControllerRMI> clientControllers;
    private HashMap<String, InterfaceClientControllerRMI> nicknameToClient;
    private HashMap<Integer, String>  hashNicknameID;  //it maps the nickname of a player with its hashed ID, the parameter used to identify a client
    private transient Timer timeout;
    private boolean timerStatus = false;
    private int lobbyDuration;
    private String propertyFile = "/adrenaline.properties";

    /**
     * Constructor, creates a new remote controller given the match controller (sub layer)
     * @param matchController reference to the match controller, the main controller of the game
     * @throws RemoteException network error
     */
    public ServerControllerRMI(MatchController matchController) throws RemoteException {
        this.matchController = matchController;
        this.converter = new InputConverter(matchController.getMatch());
        this.clientControllers = new ArrayList<>(5);
        this.hashNicknameID = new HashMap<>();
        this.nicknameToClient = new HashMap<>(10);
        matchController.setServerControllerRMI(this);
        getValuesFromProperties();
    }

    /**
     * This method charges all the values from the property file
     */
    private void getValuesFromProperties(){
        this.lobbyDuration = PropertiesLoader.getLobbyTimerDuration();
    }

    /**
     * This method returns a list of all the registered players to a match
     * @return ArrayList containing all the connected players
     */
    public ArrayList<Player> getPlayers(){
        return matchController.getMatch().getPlayers();
    }

    /*
        LOGIN METHODS
        with this method a client MUST register to the server so the server can call back the methods of InterfaceClientController
     */

    /**
     * With register a client can register on this specific controller (and match)
     * @param clientController  the clientController is the remoteController of the client side (a remote object)
     * @param nickname the nickname that the user wants to use to play
     * @return returns the clientHashedID that the client uses to identify himself when calling the server
     * @throws FailedLoginException when the nickname is already in use or there is an error during the hash phase
     */
    public synchronized int register(InterfaceClientControllerRMI clientController, String nickname) throws FailedLoginException{
        System.out.println("[INFO]: Trying to connect a new client");
        String hashedTemp = "";       //inizialized to emptyString;
        try {
            clientControllers.add(clientController);
            pushMatchToAllPlayers();

            System.out.println("[INFO]: There are: " + clientControllers.size() + " players connected and " +  matchController.getMatch().getPlayers().size() + " players registered to the server");
            //creating a token that the client will use to identify on the server!
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(nickname.getBytes());
            hashedTemp = new String(messageDigest.digest());
            this.hashNicknameID.put(hashedTemp.hashCode(), nickname);
            this.nicknameToClient.put(nickname, clientController);

            addPlayer(nickname);
            clientController.ping();

            System.out.println("[INFO]: Client " + nickname + " pinged");

            for(Player p: matchController.getMatch().getPlayers()){
                System.out.println("[INFO]: The client "+ p.getNickname() + " is now in status:" + p.getStatus().getTurnStatus());
            }

            if(matchController.checkPlayerPresence(nickname) && matchController.getMatchStatus())
                clientController.startGame();

            pushMatchToAllPlayers();
        } catch(FailedLoginException e){
            System.out.println(e.getMessage());
            throw new FailedLoginException(e.getMessage());
        } catch(RemoteException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashedTemp.hashCode();
    }

    /**
     * Adds a player to the list of registered players, this method cannot be called from the client side
     * This method also checks the number of connected players, embedding the logic that starts the game!
     * @param nickName the nickname of the player to register
     * @throws FailedLoginException when the nickname is already in use
     * @throws RemoteException when a network error occurs
     */
    private synchronized void addPlayer(String nickName) throws  FailedLoginException, RemoteException{
        try {
            matchController.addPlayer(nickName);
            System.out.println("[INFO]: Player " + nickName + " connected successfully");
            notifyNewPlayers();
        }catch(Exception e){
            throw new FailedLoginException(e.getMessage());
        }
        finally {
            //the use of timerStatus preevents the creation of 2 or more timers!

            if(connectedPlayers() >= 3 && connectedPlayers() < 5 && !timerStatus && !matchController.getMatchStatus()) {
                System.out.println("[TIMER]: Starting countdown, the match will start soon . . . ");
                timeout = new Timer();
                timeout.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    //startGame is called later, the first action should be calling a client to ask for the map
                                    //startGame();
                                    askMap();
                                    timerStatus = true;
                                }catch (RemoteException e){
                                    e.printStackTrace();
                                }
                                catch (Exception e){
                                    System.out.println("[ERROR]: Error launching the chooseMap window");
                                    e.printStackTrace();
                                }
                            }
                        }, lobbyDuration
                );
                timerStatus = true;
            }

            if(connectedPlayers() == 5 && !matchController.getMatchStatus()) {
                timeout.cancel();
                timeout.purge();
                timeout = new Timer();
                timeout.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    //startGame is called later, the first action should be calling a client to ask for the map
                                    //startGame();
                                    askMap();
                                    timeout.cancel();
                                    timeout.purge();
                                    timerStatus = true;
                                }catch (RemoteException e){
                                    e.printStackTrace();
                                }
                                catch (Exception e){
                                    System.out.println("[ERROR]: Error launching the chooseMap window");
                                    e.printStackTrace();
                                }
                            }
                        }, 1
                );
                timerStatus = true;
            }
        }
    }

    private void removeDisconnectedPlayers(){
        ArrayList<Player> toRemove = new ArrayList<>();

        for(Player p: matchController.getMatch().getPlayers())
            if(!p.isConnected())
                toRemove.add(p);

        if(!toRemove.isEmpty()) {
            for(Player p: toRemove){
                InterfaceClientControllerRMI controllerToRemove = nicknameToClient.get(p.getNickname());
                clientControllers.remove(controllerToRemove);
                nicknameToClient.remove(p.getNickname());
            }
            matchController.getMatch().getPlayers().removeAll(toRemove);
            matchController.getMatch().getPlayers().trimToSize();
            /*
                System.out.println("sout of all players after removing");
                for(int i = 0; i < getPlayers().size(); i++)
                    System.out.println(i + " : " + matchController.getMatch().getPlayers().get(i) + " Id : " + matchController.getMatch().getPlayers().get(i).getId());
            */
            System.out.println("[DISCONNECT]: Removing the non active players, chi va via perde il posto all'osteria");

            //reformatting the players IDs
            for(int i = 0; i < getPlayers().size(); i++)
                matchController.getMatch().getPlayers().get(i).setId(i);
        }

        //after reformattin the ids
        /*
            for(int i = 0; i < getPlayers().size(); i++)
                System.out.println(i + " : " + matchController.getMatch().getPlayers().get(i) + " Id : " + matchController.getMatch().getPlayers().get(i).getId());
        */

    }

    /**
     * This method is called when the match has to start. Only the player in status "MASTER" has the ownership to choose a map and call the method buildmap
     * @throws RemoteException when a network error occurs
     * @throws Exception manages generic things that could happen
     */
    private synchronized void askMap() throws RemoteException, Exception{
        //removing the non active players
        removeDisconnectedPlayers();

        matchController.getMatch().setMatchIsActive(true);
        //this code is useful for having the master always in first position (he doesn't know lol)
        int master = 0;
        for(int i = 0; i < matchController.getMatch().getPlayers().size(); i++)
            if (matchController.getMatch().getPlayers().get(i).isInStatusLobbyMaster())
                master = i;
        //the master is always in first position
        Collections.swap(matchController.getMatch().getPlayers(),0, master);

        //here i have to update the status of all the players in the game
        updateAllPlayersStatus();

        //setting the currentplayer always as the first (master)
        matchController.getMatch().setCurrentPlayer(matchController.getMatch().getPlayers().get(0));

        pushMatchToAllPlayers();

        System.out.println("[INFO]: Asking for the map to the MASTER player.");
        //here i notify the player in MASTER that has to choose the map.
        for(InterfaceClientControllerRMI controller: clientControllers) {
            if (matchController.getMatch().getPlayer(controller.getNickname()).isInStatusMaster())
                controller.askMap(); //asking the map only to the master player.
            else
                controller.waitForMap(); //method called on the players that are not in status master.
        }
    }

    /**
     * This method when called asks the match controller do build the map of the specific ID
     * @param mapID the id of the map to build
     * @param clientHashedID identifies the client
     * @throws NotAllowedCallException when the player making the call is not allowed to (he is not the current player)
     * @throws WrongValueException an invalid id is given
     * @throws WrongStatusException the player is in a not allowed status (!= MASTER)
     * @throws RemoteException network error
     */
    public synchronized void buildMap(int mapID, int clientHashedID) throws NotAllowedCallException, WrongValueException, WrongStatusException, RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            try {
                //building the map
                matchController.buildMap(mapID);
                pushMatchToAllPlayers();
                //starting the game pushing the updated model on all the clients ( this method calls the main page of the GUI ! )
                startGame();
            } catch (WrongValueException e) {
                throw new WrongValueException(e.getMessage());
            } catch (WrongStatusException e2) {
                throw new WrongStatusException(e2.getMessage());
            } catch (RemoteException e) {
                throw new RemoteException(e.getMessage());
            }
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /**
     * This method starts a new game, using the loaded info in the class.
     * Basically calls a methos on each connected client activating the main page of the GUI
     * Asks the first player to spawn
     * @throws RemoteException
     */
    private synchronized void startGame() throws RemoteException{
        try {
            System.out.println("[INFO]: Enough players to start the new game");
            pushMatchToAllPlayers();

            System.out.println("[INFO]: S T A R T I N G   A   N E W   G A M E  . . .");
            printPlayerStatuses();

            //notifying all the clients that the match is starting !
            for (InterfaceClientControllerRMI controller : clientControllers) {
                controller.startGame();
                if(matchController.getMatch().getPlayer(controller.getNickname()).isInStatusSpawn()) {
                    matchController.addPowerUpToSpawn(matchController.getMatch().getPlayer(controller.getNickname()));
                    pushMatchToAllPlayers();
                    controller.askSpawn();
                    System.out.println("[INFO]: Asking client " + controller.getNickname() + " to spawn.");
                }
            }

            matchController.getMatch().setMatchIsActive(true);

        }catch(RemoteException e){
            //exception thrown in case of connection error with client!
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

    }

    /**
     * This method asks all the players in status spawn or respawn to respawn
     * Basically acrivates a pop up on the GUI asking to discard a powerUp
     * @throws RemoteException on network error
     */
    public void askRespawn() throws RemoteException{
        for (InterfaceClientControllerRMI controller : clientControllers) {
            if(matchController.getMatch().getPlayer(controller.getNickname()).isInStatusRespawn() || matchController.getMatch().getPlayer(controller.getNickname()).isInStatusSpawn()) {
                matchController.addPowerUpToSpawn(matchController.getMatch().getPlayer(controller.getNickname()));
                pushMatchToAllPlayers();
                controller.askSpawn();
                System.out.println("[INFO]: Asking client " + controller.getNickname() + " to spawn.");
            }
        }
    }

    /**
     * Prints to System.out all the player's statuses
     */
    private void printPlayerStatuses(){
        for(Player p: matchController.getMatch().getPlayers()){
            System.out.println("[STATUS]: The player "+ p.getNickname() + " is in status: " +p.getStatus().getTurnStatus());
        }
    }

    /**
     * this method is called from a client whenever he has to spawn
     * @param powerUpID id of the powerUp to discard (integer, order of powerUp in hand)
     * @param clientHashedID identifies the client as usual
     * @throws NotInYourPossessException if the player does not have the chosen powerUp
     * @throws WrongStatusException if the player asks to spawn when he is not in status spawn or respawn
     * @throws RemoteException on network error
     */
    public synchronized void spawn(int powerUpID, int clientHashedID) throws NotInYourPossessException, WrongStatusException, RemoteException{
        try {
            matchController.spawn(converter.indexToPowerUp(powerUpID, matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID))), matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            pushMatchToAllPlayers();
        }
        catch(NotInYourPossessException e){
            e.printStackTrace();
            throw new NotInYourPossessException(e.getMessage());
        }
        catch (WrongStatusException e){
            e.printStackTrace();
            throw new WrongStatusException(e.getMessage());
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }

    /**
     * This method is called whenever a client has to disconnect
     * @param clientHashedID identifies the client making the call
     * @throws RemoteException on network error
     */
    public synchronized void disconnectPlayer(int clientHashedID) throws RemoteException {
        try {

            matchController.disconnectPlayer(hashNicknameID.get(clientHashedID));

            System.out.println("[DISCONNECT]: Disconnected Player: " + hashNicknameID.get(clientHashedID));

            InterfaceClientControllerRMI toRemove = nicknameToClient.get(hashNicknameID.get(clientHashedID));
            clientControllers.remove(toRemove);
            nicknameToClient.remove(hashNicknameID.get(clientHashedID));

            if(connectedPlayers() < 3 && getMatchStatus()) {
                System.out.println("[MATCHCONTROLLER]: The number of players went below 3, stopping the game!");
                matchController.endGameRoutine();
                pushMatchToAllPlayers();
                createRanking();
            }

            if (connectedPlayers() < 3 && timeout != null) {
                System.out.println("[INFO]: Timeout killed");
                timeout.cancel();
                timeout.purge();
                timerStatus = false;
            }

            System.out.println("[INFO]: The client " + hashNicknameID.get(clientHashedID) + " has correctly been disconnected");
            for(Player p: matchController.getMatch().getPlayers()){
                System.out.println("[INFO]: The client "+ p.getNickname() + " is now in status:" + p.getStatus().getTurnStatus());
            }

            pushMatchToAllPlayers();
        }
        catch(Exception e1){
            e1.printStackTrace();
        }
    }

    /*
        methods from moveController class
     */

    /**
     * This method is called when a player wants to make a move in the game
     * It delegates to matchController the actual move
     * @param iDestination x coordinate of the square to move in
     * @param jDestination y coordinate of the square to move in
     * @param clientHashedID identifies the client univocally
     * @throws NotAllowedMoveException if the move exceeds the maximum distance allowed
     * @throws RemoteException on network error
     * @throws InvalidInputException if the given indexes are not acceptable values
     * @throws WrongStatusException if the player is in the wrong status to make the move, he has to be either in FIRST_ACTION or SECOND_ACTION
     * @throws NotAllowedCallException if the client hashed id is non the same as the current player
     */
    public synchronized void move(int iDestination, int jDestination, int clientHashedID) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            Player affectedPlayer = matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID));
            int maxDistance = matchController.getMaxDistanceAllowed(affectedPlayer);
            matchController.move(affectedPlayer, converter.indexToSquare(iDestination, jDestination), maxDistance);
            pushMatchToAllPlayers();
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /*
        methods from grabController class
     */

    /**
     * This method is called by a client when a player wants to grab an ammo card from a specific location
     * @param xDestination x coordinate of the wanted position
     * @param yDestination y coordinate of the wanted position
     * @param clientHashedID identifies the client making the call
     * @throws WrongStatusException if the player is in the wrong status to make this call
     * @throws WrongPositionException if the player cannot access to the given position to grab
     * @throws NotAllowedCallException if the player making the call is not allowed to (not current player)
     * @throws RemoteException on network error
     * @throws InvalidInputException if the coordinates are not allowed valued (e.g negative values)
     * @throws NotAllowedMoveException if the move that has to be made to grab is not allowed
     */
    public synchronized void grabAmmoCard(int xDestination, int yDestination, int clientHashedID) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            Square temp = matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getPosition();
            try {
                matchController.grabMove(converter.indexToSquare(xDestination, yDestination));
                matchController.grabAmmoCard();
            } catch (WrongStatusException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new WrongStatusException(e.getMessage());
            } catch (WrongPositionException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new WrongPositionException(e.getMessage());
            } catch (NotAllowedMoveException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotAllowedMoveException(e.getMessage());
            } catch (InvalidInputException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new InvalidInputException(e.getMessage());
            }
            System.out.println("[GRABAMMO]: The player " + hashNicknameID.get(clientHashedID) + " grabbed the ammo card from position X,Y = [" + xDestination + "," + yDestination + "]");
            pushMatchToAllPlayers();
        } else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    /**
     * This method is called by a client when he wants to grab a weapon froma specific location
     * @param xDestination x coordinate of the wanted position
     * @param yDestination y coordinate of the wanted position
     * @param clientHashedID identifies the client making the call
     * @param indexOfWeapon index of the weapon on the weaponBox
     * @param indexOfWeaponToSwap index of the weapon in hand to swap
     * @throws NotAllowedMoveException if the move that has to be made to grab is not allowed
     * @throws InvalidInputException if the coordinates are not allowed valued (e.g negative values)
     * @throws WrongPositionException if the player cannot access to the given position to grab
     * @throws NotEnoughAmmoException if the player cannot pay the ammo to grab the specific weapon
     * @throws WrongStatusException if the player is in the wrong status to make this call
     * @throws NotAllowedCallException if the player making the call is not allowed to (not current player)
     * @throws RemoteException on network error
     */
    public synchronized void grabWeapon(int xDestination, int yDestination, int indexOfWeapon, int clientHashedID, int indexOfWeaponToSwap) throws NotAllowedMoveException, InvalidInputException, WrongPositionException, NotEnoughAmmoException, WrongStatusException, NotAllowedCallException, RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            Square temp = matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getPosition();
            try {
                matchController.grabMove(converter.indexToSquare(xDestination, yDestination));

                WeaponName tempName = converter.intToWeapon(indexOfWeapon).getName();
                int numberOfOwnedWeapons = 0;
                for (Weapon weapon : matchController.getMatch().getCurrentPlayer().getWeapons()) {
                    if (weapon != null)
                        numberOfOwnedWeapons++;
                }

                if (numberOfOwnedWeapons == 3)
                    matchController.grabWeapon(converter.intToWeapon(indexOfWeapon), indexOfWeaponToSwap);
                else
                    matchController.grabWeapon(converter.intToWeapon(indexOfWeapon), -1);

                System.out.println("[GRABWEAPON]: The player " + hashNicknameID.get(clientHashedID)+ " grabbed the weapon " + tempName + " from position X,Y = ["+xDestination+","+yDestination+"]");
            } catch (WrongStatusException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new WrongStatusException(e.getMessage());
            } catch (WrongPositionException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new WrongPositionException(e.getMessage());
            } catch (NotAllowedMoveException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotAllowedMoveException(e.getMessage());
            } catch (InvalidInputException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new InvalidInputException(e.getMessage());
            } catch (NotEnoughAmmoException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotEnoughAmmoException(e.getMessage());
            }

            pushMatchToAllPlayers();
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /*
        methods from powerup controller
     */

    /**
     * useTeleporter is called when a teleporter powerUp has to be used
     * @param indexOfPowerUp index of the powerUp in hand
     * @param xDest x coordinate of destination
     * @param yDest y coordinate of destination
     * @param clientHashedID id to identify caller
     * @throws NotInYourPossessException if the player doesn't have the powerUp in hand
     * @throws WrongStatusException if the player hasn't access to the use of powerUps
     * @throws RemoteException on network error
     * @throws NotAllowedCallException if the player identified by the clientHashedID is not the current player
     * @throws NotAllowedMoveException if the move is not allowed
     * @throws WrongPowerUpException if the powerUp is not a Teleporter
     * @throws InvalidInputException if the coordinates are wrong or the index is not valid
     */
    public synchronized void useTeleporter(int indexOfPowerUp, int xDest, int yDest, int clientHashedID) throws NotInYourPossessException, WrongStatusException, RemoteException, NotAllowedCallException, NotAllowedMoveException, WrongPowerUpException, InvalidInputException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.useTeleporter(converter.indexToPowerUp(indexOfPowerUp, matchController.getMatch().getCurrentPlayer()), converter.indexToSquare(xDest, yDest));
            pushMatchToAllPlayers();
        } else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /**
     * useNewton is called when a newton powerUp has to be used
     * @param indexOfPowerUp index of the powerUp in hand
     * @param xDest x coordinate of destination
     * @param yDest y coordinate of destination
     * @param clientHashedID id to identify caller
     * @param affectedPlayer the player to move with the Newton effect
     * @throws NotInYourPossessException if the player doesn't have the powerUp in hand
     * @throws WrongStatusException if the player hasn't access to the use of powerUps
     * @throws RemoteException on network error
     * @throws NotAllowedCallException if the player identified by the clientHashedID is not the current player
     * @throws NotAllowedMoveException if the move is not allowed
     * @throws WrongPowerUpException if the powerUp is not a Newton
     * @throws InvalidInputException if the coordinates are wrong or the index is not valid
     * @throws WrongValueException wrong values
     */
    public synchronized void useNewton(int indexOfPowerUp, String affectedPlayer, int xDest, int yDest, int clientHashedID) throws NotAllowedMoveException, NotAllowedCallException, NotInYourPossessException, WrongStatusException, RemoteException, WrongValueException, InvalidInputException, WrongPowerUpException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.useNewton(converter.indexToPowerUp(indexOfPowerUp, matchController.getMatch().getCurrentPlayer()), converter.nameToPlayer(affectedPlayer), converter.indexToSquare(xDest, yDest));
            pushMatchToAllPlayers();
        } else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /**
     * This method is used when a Player wats to use a TargetingScope
     * @param indexOfTargetingScope index of the powerUp in hand
     * @param affectedPlayer player to accect with the effect of the targeting scope
     * @param clientHashedID identifies the client making the call
     * @param ammoColorToPay needed to verify if can pay the ammo requested
     * @throws NotAllowedCallException when the client hashed id is not the current player
     * @throws NotInYourPossessException if the player making the call does not have the powerUp in hand
     * @throws WrongStatusException inf the player has no rights to use the powerUp targeting scope
     * @throws RemoteException on network error
     * @throws NotEnoughAmmoException if the player cannot pay for the effect
     */
    @Override
    public synchronized void useTargetingScope(int indexOfTargetingScope, String affectedPlayer, int clientHashedID, Color ammoColorToPay) throws NotAllowedCallException, NotInYourPossessException, WrongStatusException, RemoteException, NotEnoughAmmoException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.useTargetingScope(converter.indexToPowerUp(indexOfTargetingScope, matchController.getMatch().getCurrentPlayer()), matchController.getMatch().getPlayer(affectedPlayer), ammoColorToPay);
            pushMatchToAllPlayers();
        } else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    /**
     * This method is called when a player wants to use the TagbackGrenade powerUp
     * @param indexOfTagBackGrenade index of powerUp in hand
     * @param user The player that wants to use the powerUp (diferent from the current player)
     * @param affectedPlayer the player to affect with the powerUp
     * @param clientHashedID used to identify the player making the call
     * @throws NotInYourPossessException if the player making the request does not have such powerUp in hand
     * @throws WrongStatusException if the player making the request is not allowed to
     * @throws RemoteException on network error
     */
    @Override
    public synchronized void useTagBackGrenade(int indexOfTagBackGrenade, String user, String affectedPlayer, int clientHashedID) throws  NotInYourPossessException, WrongStatusException , RemoteException {
        Player userPlayer =  matchController.getMatch().getPlayer(user);
        if (! userPlayer.getNickname().equals(hashNicknameID.get(clientHashedID)))
            throw new WrongStatusException("You can't use a Tagback Grenade now!");
        Player tagBackedPlayer = matchController.getMatch().getPlayer(affectedPlayer);
        PowerUp pow = converter.indexToPowerUp(indexOfTagBackGrenade, userPlayer);
        if (userPlayer.isAskForTagBackGrenade()) {
            matchController.useTagbackGrenade(pow, userPlayer, tagBackedPlayer);
            pushMatchToAllPlayers();
        } else
            throw new WrongStatusException("You can't use a Tagback Grenade now!");
    }

    /**
     * This method is used whenever a player has to pay a cost with a powerUp
     * @param indexOfPow index of powerUp in hand of the player
     * @throws RemoteException on network error
     * @throws NotInYourPossessException if the player making the request does not have such powerUp in hand
     */
    @Override
    public void usePowerUpAsAmmo(int indexOfPow) throws RemoteException, NotInYourPossessException {
        matchController.usePowerUpAsAmmo(converter.indexToPowerUp(indexOfPow, matchController.getMatch().getCurrentPlayer()));
    }

    /**
     * This method is used by the server to ask the players to use a powerUp ad Ammo
     * @throws RemoteException on network error
     */
    @Override
    public void askForPowerUpAsAmmo() throws RemoteException {
        for (InterfaceClientControllerRMI clientController : clientControllers) {
            if (clientController.getNickname().equals(matchController.getMatch().getCurrentPlayer().getNickname())) {
                 clientController.askForPowerUpAsAmmo();
                 pushMatchToAllPlayers();
                 break;
            }
        }
    }

    // Methods for shoot controller

    /**
     * This method is used whenever a user wants to make the shoot action in the game
     * @param input contains all the input paramenters required to make a shooting action
     * @param clientHashedID identifes the player making the request
     * @throws NotAllowedCallException if the player is non identifued correctly (wrong hashed id)
     * @throws NotAllowedTargetException if the targets are setted wrongly
     * @throws NotAllowedMoveException if the requested move in the action of shooting is not allowed
     * @throws WrongStatusException if the player cannot make the action, is in the wrong status
     * @throws NotEnoughAmmoException if the player does not have enough ammo to shoot
     * @throws NotAllowedShootingModeException if the shooting mode requested is wrongly setted
     * @throws RemoteException on network error
     * @throws InvalidInputException if there is an error in the input parameters
     */
    @Override
    public synchronized void shoot(ShootingParametersClient input, int clientHashedID) throws NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException, InvalidInputException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            Square temp = matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getPosition();
            int x;
            int y;

            for (Player p: matchController.getMatch().getPlayers()) {
                p.setBeenDamaged(false);
            }

            try {

                if (matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getStatus().getSpecialAbility() == AbilityStatus.ADRENALINE_SHOOT) {
                    x = input.getAdrenalineMoveIndexes().get(0);
                    y = input.getAdrenalineMoveIndexes().get(1);
                    matchController.getMoveController().move(matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)), converter.indexToSquare(x, y), 1);
                }
                matchController.shoot(parseInput(input));

            } catch (WrongStatusException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new WrongStatusException(e.getMessage());
            } catch (NotEnoughAmmoException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotEnoughAmmoException(e.getMessage());
            } catch (NotAllowedMoveException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotAllowedMoveException(e.getMessage());
            } catch (InvalidInputException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new InvalidInputException(e.getMessage());
            } catch (NotAllowedShootingModeException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotAllowedShootingModeException(e.getMessage());
            } catch (NotAllowedTargetException e) {
                matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).setPosition(temp);
                throw new NotAllowedTargetException(e.getMessage());
            }

            for (PowerUp powerUp : matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getPowerUps()) {
                if (powerUp != null && powerUp.getName().equals(PowerUpName.TARGETING_SCOPE)) {
                    for (InterfaceClientControllerRMI clientController : clientControllers) {
                        if (clientController.getNickname().equals(matchController.getMatch().getCurrentPlayer().getNickname())) {
                            clientController.askForTargetingScope();
                            break;
                        }
                    }
                }
            }

            pushMatchToAllPlayers();
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    private synchronized ShootingParametersInput parseInput(ShootingParametersClient input) throws NotAllowedTargetException, NotAllowedShootingModeException, InvalidInputException {
        Weapon weapon = null;
        ShootingParametersInput parameters = new ShootingParametersInput();

        for (Weapon wea: matchController.getMatch().getCurrentPlayer().getWeapons()) {
            if(wea != null) {
                if (wea.getName().equals(input.getName()))
                    weapon = wea;
            }
        }

        parameters.setWeapon(weapon);

        for (String name : input.getTargetPlayers()) {
            if (!name.equals(matchController.getMatch().getCurrentPlayer().getNickname()))
                parameters.setTargets(matchController.getMatch().getPlayer(name));
            else
                throw new NotAllowedTargetException("You cannot shoot to yourself");
        }

        if (!input.getShootModes().contains(ShootMode.BASIC) && !input.getShootModes().contains(ShootMode.ALTERNATE))
            throw new NotAllowedShootingModeException("Wrong shooting mode");

        for (ShootMode mode : input.getShootModes()) {
            parameters.setShootModes(mode);
        }

        if (! input.getSquaresCoordinates().isEmpty()) {
            int x = input.getSquaresCoordinates().get(0);
            int y = input.getSquaresCoordinates().get(1);
            parameters.setSquares(converter.indexToSquare(x, y));
        }

        if (input.getSquaresCoordinates().size() > 2) {
            int x = input.getSquaresCoordinates().get(2);
            int y = input.getSquaresCoordinates().get(3);
            parameters.setSquares(converter.indexToSquare(x, y));
        }

        parameters.setDirection(input.getDirection());

        parameters.setMakeDamageBeforeMove(input.getMakeDamageBeforeMove());

        return parameters;
    }

    /**
     * This method is called whenever a client has to reload his weapons (if allowed to)
     * @param indexOfWeapon index of weapon in hand to reload
     * @param clientHashedID allows the server to identify the client making the call
     * @throws RemoteException on network error
     * @throws NotEnoughAmmoException if the ammo of the player is not enough to reload the weapon
     * @throws NotAllowedCallException if the player is not allowed to make the call (not the current player)
     * @throws WrongStatusException if the player ha no rights to make such call
     */
    @Override
    public void reload(int indexOfWeapon, int clientHashedID) throws RemoteException, NotEnoughAmmoException, NotAllowedCallException, WrongStatusException {
        if (checkHashedIDAsCurrentPlayer(clientHashedID)) {
            try {
                matchController.reloadWeapon(converter.intToWeaponInHand(indexOfWeapon));
                pushMatchToAllPlayers();
            } catch (NotEnoughAmmoException e) {
                throw new NotEnoughAmmoException(e.getMessage());
            } catch (WrongStatusException e) {
                throw new WrongStatusException(e.getMessage());
            }
        } else {
            throw new NotAllowedCallException("Wait for your turn");
        }
    }

    /*
            Update methods
       */
    private synchronized void updateAllPlayersStatus(){
        ArrayList<Player> toRemove = new ArrayList<>();
        for(Player p: matchController.getMatch().getPlayers())
            matchController.goToNextStatus(p);

        System.out.println("[INFO]: Updating the status of all the connected players.");

    }

    /**
     * This method is used whenever the server has to notify the clints any changes in the game.
     * For example when a player changes position, is hitted or makes an action.
     * @throws RemoteException on newtork erros contacting the clients
     */
    public synchronized void pushMatchToAllPlayers() throws RemoteException{
        Match sharedMatch = new Match(matchController.getMatch());

        for(InterfaceClientControllerRMI controller: clientControllers)
            controller.updateMatch(sharedMatch);

        System.out.println("[INFO]: Pushing the updated match to all the players ");

    }

    /**
     * This method returns the number of connected players to {@code this} match
     * @return number of connected players
     */
    public synchronized int connectedPlayers(){
        return matchController.connectedPlayers();
    }

    /**
     * This method is used to get the status of a specific player, passed in input as his id
     * @param idPlayer id of the player
     * @return the whole status of the player, composed of TurnStatus and AbilityStatus
     * @throws WrongValueException if the player is not found
     * @see PlayerStatusHandler
     */
    public synchronized PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException{
        return matchController.getPlayerStatus(idPlayer);
    }

    /**
     * This method gets the status of the match, that can be either true = active or false = not active
     * @return the boolean containing the status of the match {@code this}
     * @throws RemoteException on network error
     */
    public synchronized boolean getMatchStatus() throws RemoteException{
        return matchController.getMatchStatus();
    }


    private synchronized void notifyNewPlayers(){
        try {
            for (InterfaceClientControllerRMI c : clientControllers) {
                c.updateConnectedPlayers(matchController.getMatch().getPlayers());
            }
            System.out.println("[INFO]: Client notified with new player list");
        } catch(RemoteException e){
            e.printStackTrace();
        }
    }

    //GENERAL CONTROLS:

    //Check if the caller of a method is actually the correct player (check on the token)
    private synchronized boolean checkHashedIDAsCurrentPlayer(int hashedID){
        return hashNicknameID.get(hashedID).equals(matchController.getMatch().getCurrentPlayer().getNickname());
    }

    /**
     * This method is used to check the connection on a specific player in This server
     * @param nickname nickname of the player to check connection
     * @return returns a boolean indicating wether the player with such nickname is connected or not
     * @throws RemoteException on network error
     */
    @Override
    public synchronized boolean checkIfConnected(String nickname) throws RemoteException {
        try {
            for(InterfaceClientControllerRMI controller: clientControllers)
                if(controller.getNickname().equals(nickname))
                    controller.ping(); // if a network error happens, a RemoteException is thrown.
            return matchController.getMatch().getPlayers().contains(matchController.getMatch().getPlayer(nickname));
        }
        catch (NullPointerException e){
            return false;
        }
        catch (RemoteException e){
            return false;
        }
    }

    /**
     * This method sets the number of remaining skulls in the mortal path
     * @param nSkulls number of skull to set
     * @param clientHashedID identyfies the player in the match
     * @throws RemoteException on network error
     * @throws NotAllowedCallException thrown when a player calls the method but he is not the current player
     */
    @Override
    public void setSkulls(int nSkulls, int clientHashedID) throws RemoteException, NotAllowedCallException {
        if (nSkulls < 5 || nSkulls > 8)
            throw new NotAllowedCallException("The chosen number for skulls is not allowed");

        if (checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.getMatch().getKillShotTrack().setSkulls(nSkulls);
            matchController.getMatch().getKillShotTrack().setTotalSkulls(nSkulls);
        }
        else
          throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /**
     * This method is called by an active client when he wants to skip an action
     * @param clientHashedID identifies the client making the call
     * @throws RemoteException on network error
     * @throws WrongStatusException if the player cannot skip the turn (he is waiting for his turn)
     */
    public void skipAction(int clientHashedID) throws RemoteException, WrongStatusException {
        try {
            matchController.skipAction(matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            pushMatchToAllPlayers();
        }catch (WrongStatusException e){
            e.printStackTrace();
            throw new  WrongStatusException(e.getMessage());
        }
    }

    /**
     * This method is similar to skipAction but it's used during the frenzy mode, has different paramenters on status controls
     * @param clientHashedID identifies the client making the calls
     * @throws RemoteException on network error
     * @throws WrongStatusException if the player cannot skip the turn (he is waiting for his turn)
     */
    public void skipActionFrenzy(int clientHashedID) throws RemoteException, WrongStatusException {
        try {
            matchController.skipActionFrenzy(matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            pushMatchToAllPlayers();
        }catch (WrongStatusException e){
            e.printStackTrace();
            throw new  WrongStatusException(e.getMessage());
        }
    }

    /**
     * This method is used by a player when he wants to perform a franzy action. In particular this method creates the Move + Reload + Shoot action
     * @param posX x coordinate of destination square
     * @param posY y coordinate of destination square
     * @param input contains all the shooting inputs
     * @param clientHashedID identifies the client when making the call
     * @throws RemoteException on network error
     * @throws NotAllowedTargetException if the specified target(s) in shoot are not allowed (see rules)
     * @throws NotAllowedShootingModeException if the shooting mode is not available for the chosen weapon
     * @throws InvalidInputException if the input paramenter has a general error
     * @throws WrongStatusException if the player making the call is not in First of Second action Frenzy mode
     * @throws NotAllowedMoveException if the requested move is not allowed (see rules)
     * @throws NotEnoughAmmoException if the player has not enough ammo to reload or use his weapon
     * @throws NotAllowedCallException if the player making the call is not the current player
     */
    public void makeAction1Frenzy(int posX, int posY, ShootingParametersClient input, int clientHashedID) throws RemoteException, NotAllowedTargetException, NotAllowedShootingModeException, InvalidInputException, WrongStatusException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedCallException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            try {
                matchController.makeAction1Frenzy(matchController.getMap().getSquareFromIndex(posX,posY), parseInput(input),matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(e.getMessage());
            } catch (NotAllowedShootingModeException e) {
                e.printStackTrace();
                throw new NotAllowedShootingModeException(e.getMessage());
            } catch (InvalidInputException e) {
                e.printStackTrace();
                throw new InvalidInputException(e.getMessage());
            } catch (WrongStatusException e) {
                e.printStackTrace();
                throw new WrongStatusException(e.getMessage());
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
                throw new NotAllowedMoveException(e.getMessage());
            } catch (NotEnoughAmmoException e) {
                e.printStackTrace();
                throw new NotEnoughAmmoException(e.getMessage());
            }
            try {
                pushMatchToAllPlayers();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    /**
     * This method is used to make the action Shoot but with 2 moves (action1 frenzy allows to make 1 move)
     * @param posX x coordinate of destination
     * @param posY y coordinate of destination
     * @param input input of shoot action
     * @param clientHashedID identifies the player making the call
     * @throws NotAllowedTargetException if the specified target(s) in shoot are not allowed (see rules)
     * @throws NotAllowedShootingModeException if the shooting mode is not available for the chosen weapon
     * @throws InvalidInputException if the input paramenter has a general error
     * @throws WrongStatusException if the player making the call is not in First of Second action Frenzy mode
     * @throws NotAllowedMoveException if the requested move is not allowed (see rules)
     * @throws NotEnoughAmmoException if the player has not enough ammo to reload or use his weapon
     * @throws RemoteException on network error
     * @throws NotAllowedCallException if the player making the call is not the current player
     */
    public void makeAction1FrenzyLower(int posX, int posY, ShootingParametersClient input, int clientHashedID) throws NotAllowedTargetException, NotAllowedShootingModeException, InvalidInputException, WrongStatusException, NotAllowedMoveException, NotEnoughAmmoException, RemoteException, NotAllowedCallException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            try {
                matchController.makeAction1FrenzyLower(matchController.getMap().getSquareFromIndex(posX,posY), parseInput(input),matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            } catch (NotAllowedTargetException e) {
                e.printStackTrace();
                throw new NotAllowedTargetException(e.getMessage());
            } catch (NotAllowedShootingModeException e) {
                e.printStackTrace();
                throw new NotAllowedShootingModeException(e.getMessage());
            } catch (InvalidInputException e) {
                e.printStackTrace();
                throw new InvalidInputException(e.getMessage());
            } catch (WrongStatusException e) {
                e.printStackTrace();
                throw new WrongStatusException(e.getMessage());
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
                throw new NotAllowedMoveException(e.getMessage());
            } catch (NotEnoughAmmoException e) {
                e.printStackTrace();
                throw new NotEnoughAmmoException(e.getMessage());
            }
            try {
                pushMatchToAllPlayers();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    /**
     *  This method embodies the controls and actions to realize the second action in frenzy mode.
     *  With second action has to be intended that with this method you can make the move up to 4 squares!
     * @param posX x coordinate of destination square
     * @param posY y coordinate of sqaure
     * @param clientHashedID identifies the client making the call
     * @throws NotAllowedMoveException if the requested move exceeds the maximum distance (4)
     * @throws RemoteException on network error
     * @throws NotAllowedCallException if the player making the call is not the current player
     * @throws WrongStatusException if the player making the call is in the wrong status
     */
    public void makeAction2Frenzy(int posX, int posY, int clientHashedID) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.makeAction2Frenzy(matchController.getMap().getSquareFromIndex(posX, posY), matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            try {
                pushMatchToAllPlayers();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
        }
        else{
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
        }

    }

    /**
     * This method allows you to make the action where you move up to 2 squares and the grab
     * @param posX x coordinate of destination
     * @param posY y coordinate of destination
     * @param numbOfWeaponToGrab index of weapon to grab in map
     * @param clientHashedID identifies the player making the call
     * @param indexOfWeaponToSwap index of weapon in hand to swap with picked one
     * @throws NotAllowedMoveException if the move exceeds 2 squares distance
     * @throws RemoteException on network error
     * @throws NotAllowedCallException if the player making the call is not the current player
     * @throws WrongStatusException if the player is in the wrong status of his turn
     */
    public void makeAction3Frenzy(int posX, int posY, int numbOfWeaponToGrab ,int clientHashedID, int indexOfWeaponToSwap) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.makeAction3Frenzy(matchController.getMap().getSquareFromIndex(posX, posY), numbOfWeaponToGrab ,matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)), indexOfWeaponToSwap);
            try {
                pushMatchToAllPlayers();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
        }
        else{
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
        }

    }

    /**
     * With this method you can perform the grab with 3 of maximum distance of move
     * @param posX x coordinate of destinations
     * @param posY y coordinate of destination
     * @param numbOfWeaponToGrab index of weapon to grab in map
     * @param clientHashedID identifies the player making the call
     * @param indexOfWeaponToSwap index of weapon in hand to swap with picked one
     * @throws NotAllowedMoveException if the move exceeds 3 squares distance
     * @throws RemoteException on network error
     * @throws NotAllowedCallException if the player making the call is not the current player
     * @throws WrongStatusException if the player is in the wrong status of his turn
     */
    public void makeAction2FrenzyLower(int posX, int posY, int numbOfWeaponToGrab, int clientHashedID, int indexOfWeaponToSwap) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.makeAction2FrenzyLower(matchController.getMap().getSquareFromIndex(posX, posY), numbOfWeaponToGrab ,matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)), indexOfWeaponToSwap);
            try {
                pushMatchToAllPlayers();
            } catch (RemoteException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
            }
        }
        else{
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
        }

    }

    /**
     * With this method you can ask to a specific client to use the tagback grenade
     * @param nickname nickname of the player to ask to
     * @throws RemoteException on network error
     */
    @Override
    public void askForTagBackGrenade(String nickname) throws RemoteException {
        //nickname is the name of the player whom I am asking to use the tagBack Grenade
        for (InterfaceClientControllerRMI clientController : clientControllers) {
            if (clientController.getNickname().equals(nickname)) {
                clientController.askForTagBackGrenade();
                pushMatchToAllPlayers();
            }
        }
    }

    /**
     * closes the timer of speficied name
     * @param timerName name of the timer to close
     * @param clientHashedID client that makes the request
     * @throws RemoteException on network error
     */
    @Override
    public void closeTimer(String timerName, int clientHashedID) throws RemoteException {
        if (checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.closeTimer(timerName);
        else
            throw new RemoteException("You cannot close a timer");
    }

    /**
     * creates the final ranking and displays it to the players
     * @throws RemoteException on network error
     */
    public synchronized void createRanking() throws RemoteException{
        for(InterfaceClientControllerRMI controller: clientControllers)
            controller.createRanking();

        System.out.println("[INFO]: Final Ranking ");

    }

    /**
     *   PING !
     * @throws RemoteException as usual, on network error :)
     */
    public void ping() throws RemoteException{
        return;
    }

}
