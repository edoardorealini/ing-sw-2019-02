package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceServerControllerRMI;
import controller.InputConverter;
import controller.MatchController;
import model.ShootingParametersInput;
import exception.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;

import javax.security.auth.login.FailedLoginException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

//ex remoteObjectRMI
//this is the class that generates the remote object, called by the client!
public class ServerControllerRMI extends UnicastRemoteObject implements InterfaceServerControllerRMI {

    private InputConverter converter;
    private MatchController matchController;
    private ArrayList<InterfaceClientControllerRMI> clientControllers; //todo better implementation with a Map!
    private HashMap<Integer, String>  hashNicknameID;  //it maps the nickname of a player with its hashed ID, the parameter used to identify a client
    private Timer timeout;
    private boolean timerStatus = false;

    /*
        Builder
     */
    public ServerControllerRMI(MatchController matchController) throws RemoteException {
        this.matchController = matchController;
        this.converter = new InputConverter(matchController.getMatch());
        this.clientControllers = new ArrayList<>(5);
        this.hashNicknameID = new HashMap<>();
    }

    /*
        LOGIN METHODS
        with this method a client MUST register to the server so the server can call back the methods of InterfaceClientController
     */
    public synchronized int register(InterfaceClientControllerRMI clientController, String nickname) throws FailedLoginException{
        System.out.println("[INFO]: Trying to connect a new client");
        String hashedTemp = "";       //inizialized to emptyString;
        try {
            clientControllers.add(clientController);

            addPlayer(nickname);
            clientController.ping();

            System.out.println("[INFO]: Client " + nickname + " pinged");
            System.out.println("[INFO]: There are: " + clientControllers.size() + " players connected and " +  matchController.getMatch().getPlayers().size() + " players registered to the server");
            //creating a token that the client will use to identify on the server!
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(nickname.getBytes());
            hashedTemp = new String(messageDigest.digest());
            this.hashNicknameID.put(hashedTemp.hashCode(), nickname);

            for(Player p: matchController.getMatch().getPlayers()){
                System.out.println("[INFO]: The client "+ p.getNickname() + " is now in status:" + p.getStatus().getTurnStatus());

            }
        } catch(FailedLoginException e){
            System.out.println(e.getMessage());
            throw new FailedLoginException(e.getMessage());
        } catch(RemoteException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashedTemp.hashCode();
    }

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
            if(connectedPlayers() >= 3 && connectedPlayers() < 5 && !timerStatus) {
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
                                }catch (RemoteException e){
                                    e.printStackTrace();
                                }
                                catch (Exception e){
                                    System.out.println("[ERROR]: Error launching the chooseMap window");
                                    e.printStackTrace();
                                }
                            }
                        }, 15000
                );
                timerStatus = true;
            } //todo rendere parametrico il delay, renderlo settabile da file di properties stile libreria JPOS ! (edo)

            if(connectedPlayers() == 5) {
                timeout.cancel();
                timeout.purge();
                try {
                    askMap();
                }catch (Exception e){
                    System.out.println("[ERROR]: Error launching the chooseMap window");
                    e.printStackTrace();
                }
            }

        }
    }

    //this method is called when the match has to start. Only the player in status "MASTER" has the ownership to choose a map and call the method buildmap
    //from buildmap --> call startGame() that has to be changed
    private synchronized void askMap() throws RemoteException, Exception{
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

    public synchronized void buildMap(int mapID, int clientHashedID) throws NotAllowedCallException, WrongValueException, WrongStatusException, RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            try {
                //building the map
                matchController.buildMap(mapID);
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

    private synchronized void startGame() throws RemoteException{
        try {
            System.out.println("[INFO]: Enough players to start the new game");
            pushMatchToAllPlayers();

            //notifying all the clients that the match is starting !
            for (InterfaceClientControllerRMI controller : clientControllers) {
                controller.startGame();
                if(matchController.getMatch().getPlayer(controller.getNickname()).isInStatusSpawn())
                    controller.askSpawn();
            }

            System.out.println("[INFO]: GAME STARTING . . .");


        }catch(RemoteException e){
            //exception thrown in case of connection error with client!
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

    }

    public synchronized void spawn(int powerUpID, int clientHashedID) throws NotInYourPossessException, WrongStatusException, RemoteException{
        try {
            matchController.spawn(converter.indexToPowerUp(powerUpID), matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
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

    public synchronized void disconnectPlayer(int clientHashedID) throws RemoteException {
        matchController.disconnectPlayer(hashNicknameID.get(clientHashedID));
        try {
            /*
        for (InterfaceClientControllerRMI c : clientControllers)
            if (hashNicknameID.get(clientHashedID).equals(c.getNickname()))
                clientControllers.remove(c);

             */
            clientControllers.removeIf(c -> {
                try {
                    return hashNicknameID.get(clientHashedID).equals(c.getNickname());
                } catch (RemoteException e) {
                    e.printStackTrace();
                    return false;
                }
            });

            if (connectedPlayers() < 3 && timeout != null) {
                System.out.println("[INFO]: Timeout killed");
                timeout.cancel();
                timeout.purge();
            }

            System.out.println("[INFO]: The client " + hashNicknameID.get(clientHashedID) + " has correctly been disconnected");
            for(Player p: matchController.getMatch().getPlayers()){
                System.out.println("[INFO]: The client "+ p.getNickname() + " is now in status:" + p.getStatus().getTurnStatus());

            }

        }
        catch(Exception e1){
            e1.printStackTrace();
        }
    }

    //TODO qui va corretto tutto aggiungendo anche il controllo sull'id hashato!!

    /*
        methods from moveController class
     */

    public synchronized void move(Player affectedPlayer, int iDestination, int jDestination, int maxDistanceAllowed, int clientHashedID) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.move(affectedPlayer, converter.indexToSquare(iDestination, jDestination), maxDistanceAllowed);
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /*
        methods from grabController class
     */
    public synchronized void grabAmmoCard(int clientHashedID) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.grabAmmoCard();
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    public synchronized void grabWeapon(int indexOfWeapon, int clientHashedID) throws WrongPositionException, NotEnoughAmmoException, WrongStatusException, NotAllowedCallException, RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.grabWeapon(converter.intToWeapon(indexOfWeapon));
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /*
        methods from powerup controller
     */
    public synchronized void useTeleporter(PowerUp teleporter, Square destination, int clientHashedID) throws NotInYourPossessException, WrongStatusException , RemoteException , NotAllowedCallException{
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.useTeleporter(teleporter, destination);
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    public synchronized void useNewton(PowerUp newton, Player affectedPlayer, Square destination, int clientHashedID) throws NotAllowedMoveException, NotAllowedCallException, NotInYourPossessException, WrongStatusException , RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.useNewton(newton, affectedPlayer, destination);
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    public synchronized void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, int clientHashedID) throws NotAllowedCallException, NotInYourPossessException, WrongStatusException , RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.useTargetingScope(targetingScope, affectedPlayer);
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    public synchronized void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, int clientHashedID) throws NotAllowedTargetException, NotInYourPossessException, WrongStatusException , RemoteException {
        //TODO gestire controlli di permissions per chiamare questo metodo!
        matchController.useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
    }

    /*
        Methods form shoot controller
    */
    public synchronized void shoot(ShootingParametersInput input, int clientHashedID) throws NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException  {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.shoot(input);
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }


    /*
        Update methods
    */
    private synchronized void updateAllPlayersStatus(){
        System.out.println("[INFO]: Updating the status of all the players.");
        for(Player p: matchController.getMatch().getPlayers())
            matchController.goToNextStatus(p);
    }

    private synchronized void pushMatchToAllPlayers() throws RemoteException{
        System.out.println("[INFO]: Pushing the updated match to all the players ");
        for(InterfaceClientControllerRMI controller: clientControllers)
            controller.updateMatch(matchController.getMatch());
    }

    //TODO capire se server effettivamente esporre questi metodi!
    public synchronized int connectedPlayers(){
        return matchController.connectedPlayers();
    }

    public synchronized PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException{
        return matchController.getPlayerStatus(idPlayer);
    }

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

    //connection check
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

    //USELESS METHODS:
    public synchronized String RMICallTest(String message, int clientHashedID) {
        System.out.println("Called test method with message: " + message);
        return "Called MatchController.RMICallTest(message) method with message: " + message;
    }

    public synchronized String checkConnection(String IP, int clientHashedID) {
        System.out.println("[INFO]: Connection with client " + IP + " completed successfully.");
        return "[RMIServer]: Connection status OK";
    }

    /*
        this method is for test use only
     */
    private synchronized int hashNickname(String nickName){

        String hashedTemp = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(nickName.getBytes());
            hashedTemp = new String(messageDigest.digest());
        }catch(Exception e){
            e.printStackTrace();
        }

        return hashedTemp.hashCode();

    }

    @Override
    public void setSkulls(int nSkulls, int clientHashedID) throws RemoteException, NotAllowedCallException {
        if (nSkulls < 5 || nSkulls > 8)
            throw new NotAllowedCallException("The chosen number for skulls is not allowed");

        if (checkHashedIDAsCurrentPlayer(clientHashedID))
             matchController.getMatch().getKillShotTrack().setSkulls(nSkulls);
        else
          throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }
}
