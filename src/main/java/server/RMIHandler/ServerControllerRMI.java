package server.RMIHandler;

import commons.ShootingParametersClient;
import commons.InterfaceClientControllerRMI;
import commons.InterfaceServerControllerRMI;
import controller.InputConverter;
import controller.MatchController;
import exception.*;
import model.Match;
import model.ShootMode;
import model.ShootingParametersInput;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;
import model.weapons.Weapon;
import model.weapons.WeaponName;

import javax.security.auth.login.FailedLoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    private int lobbyDuration;
    private String propertyPath = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "adrenaline.properties";


    /*
        Builder
     */
    public ServerControllerRMI(MatchController matchController) throws RemoteException {
        this.matchController = matchController;
        this.converter = new InputConverter(matchController.getMatch());
        this.clientControllers = new ArrayList<>(5);
        this.hashNicknameID = new HashMap<>();
        matchController.setServerControllerRMI(this);
        getValuesFromProperties();
    }

    private void getValuesFromProperties(){
        Properties propertyLoader = new Properties();

        try{
            propertyLoader.load(new FileInputStream(propertyPath));
            this.lobbyDuration = Integer.parseInt(propertyLoader.getProperty("lobbyDuration"));
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR]: Failed loading info from properties file, setting the lobbyDuration to 30 seconds as default value");
            lobbyDuration = 30000; //default value for turn duration set to 30 seconds
        }
    }

    public ArrayList<Player> getPlayers(){
        return matchController.getMatch().getPlayers();
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
                        }, lobbyDuration
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

    private void printPlayerStatuses(){
        for(Player p: matchController.getMatch().getPlayers()){
            System.out.println("[STATUS]: The player "+ p.getNickname() + " is in status: " +p.getStatus().getTurnStatus());
        }
    }

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

    public synchronized void disconnectPlayer(int clientHashedID) throws RemoteException {
        try {
            /*
        for (InterfaceClientControllerRMI c : clientControllers)
            if (hashNicknameID.get(clientHashedID).equals(c.getNickname()))
                clientControllers.remove(c);

             */
            matchController.disconnectPlayer(hashNicknameID.get(clientHashedID));

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
                timerStatus = false;

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
    public synchronized void grabAmmoCard(int xDestination, int yDestination, int clientHashedID) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.grabMove(converter.indexToSquare(xDestination, yDestination));
            matchController.grabAmmoCard();
            System.out.println("[GRABAMMO]: The player " + hashNicknameID.get(clientHashedID) + " grabbed the ammo card from position X,Y = [" + xDestination + "," + yDestination + "]");
            pushMatchToAllPlayers();
        } else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }
    //lets the current player grab a weapon
    public synchronized void grabWeapon(int xDestination, int yDestination, int indexOfWeapon, int clientHashedID, int indexOfWeaponToSwap) throws NotAllowedMoveException, InvalidInputException, WrongPositionException, NotEnoughAmmoException, WrongStatusException, NotAllowedCallException, RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.grabMove(converter.indexToSquare(xDestination,yDestination));
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
            pushMatchToAllPlayers();
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    /*
        methods from powerup controller
     */
    //NB  x = i /  y = j !!
    public synchronized void useTeleporter(int indexOfPowerUp, int xDest, int yDest, int clientHashedID) throws NotInYourPossessException, WrongStatusException, RemoteException, NotAllowedCallException, NotAllowedMoveException, WrongPowerUpException, InvalidInputException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.useTeleporter(converter.indexToPowerUp(indexOfPowerUp, matchController.getMatch().getCurrentPlayer()), converter.indexToSquare(xDest, yDest));
        }
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }
    //NB  x = i /  y = j !!
    public synchronized void useNewton(int indexOfPowerUp, String affectedPlayer, int xDest, int yDest, int clientHashedID) throws NotAllowedMoveException, NotAllowedCallException, NotInYourPossessException, WrongStatusException, RemoteException, WrongValueException, InvalidInputException, WrongPowerUpException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.useNewton(converter.indexToPowerUp(indexOfPowerUp,matchController.getMatch().getCurrentPlayer()), converter.nameToPlayer(affectedPlayer), converter.indexToSquare(xDest, yDest));
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    //TODO TARGETING SCOPE
    public synchronized void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, int clientHashedID) throws NotAllowedCallException, NotInYourPossessException, WrongStatusException , RemoteException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.useTargetingScope(targetingScope, affectedPlayer);
        else
            throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");

    }

    @Override
    public synchronized void useTagBackGrenade(int indexOfTagBackGrenade, String user, String affectedPlayer, int clientHashedID) throws NotAllowedTargetException, NotInYourPossessException, WrongStatusException , RemoteException {
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

    @Override
    public void usePowerUpAsAmmo(int indexOfPow) throws RemoteException, NotInYourPossessException {
        matchController.usePowerUpAsAmmo(converter.indexToPowerUp(indexOfPow, matchController.getMatch().getCurrentPlayer()));
    }

    @Override
    public void askForPowerUpAsAmmo() throws RemoteException {
        for (InterfaceClientControllerRMI clientController : clientControllers) {
            if (clientController.getNickname().equals(matchController.getMatch().getCurrentPlayer().getNickname())) {
                 clientController.askForPowerUpAsAmmo();
                 pushMatchToAllPlayers();
            }
        }
    }

    // Methods for shoot controller
    @Override
    public synchronized void shoot(ShootingParametersClient input, int clientHashedID) throws NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException, InvalidInputException {
        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.shoot(parseInput(input));
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
        for(Player p: matchController.getMatch().getPlayers())
            matchController.goToNextStatus(p);

        System.out.println("[INFO]: Updating the status of all the players.");

    }

    public synchronized void pushMatchToAllPlayers() throws RemoteException{
        Match sharedMatch = new Match(matchController.getMatch());

        for(InterfaceClientControllerRMI controller: clientControllers)
            controller.updateMatch(sharedMatch);

        System.out.println("[INFO]: Pushing the updated match to all the players ");

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
        if (nSkulls < 0 || nSkulls > 8) // TODo non dir cazzate
            throw new NotAllowedCallException("The chosen number for skulls is not allowed");

        if (checkHashedIDAsCurrentPlayer(clientHashedID))
             matchController.getMatch().getKillShotTrack().setSkulls(nSkulls);
        else
          throw new NotAllowedCallException("You are not allowed to execute this action now, wait for your turn!");
    }

    public void skipAction(int clientHashedID) throws RemoteException, WrongStatusException {
        try {
            matchController.skipAction(matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
            pushMatchToAllPlayers();
        }catch (WrongStatusException e){
            e.printStackTrace();
            throw new  WrongStatusException(e.getMessage());
        }
    }

    // FRENZY METHODS

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

    public void makeAction3Frenzy(int posX, int posY, Weapon wp ,int clientHashedID) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.makeAction3Frenzy(matchController.getMap().getSquareFromIndex(posX, posY), wp ,matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
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

    public void makeAction2FrenzyLower(int posX, int posY, Weapon wp, int clientHashedID) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {

        if(checkHashedIDAsCurrentPlayer(clientHashedID)) {
            matchController.makeAction2FrenzyLower(matchController.getMap().getSquareFromIndex(posX, posY), wp ,matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)));
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

    @Override
    public void closeTimer(String timerName, int clientHashedID) throws RemoteException {
        if (checkHashedIDAsCurrentPlayer(clientHashedID))
            matchController.closeTimer(timerName);
        else
            throw new RemoteException("You cannot close a timer");
    }
}
