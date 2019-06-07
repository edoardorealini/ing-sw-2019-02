package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceServerControllerRMI;
import controller.InputConverter;
import controller.MatchController;
import exception.*;
import model.Match;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

//ex remoteObjectRMI
public class ServerControllerRMI extends UnicastRemoteObject implements InterfaceServerControllerRMI {

    private InputConverter converter;
    private MatchController matchController;
    private ArrayList<InterfaceClientControllerRMI> clientControllers; //better implementation with a Map!
    private HashMap<Integer, String>  hashNicknameID;  //it maps the nickname of a player with its hashed ID, the parameter used to identify a client
    private Timer timeout;

    public ServerControllerRMI(MatchController matchController) throws RemoteException {
        this.matchController = matchController;
        this.converter = new InputConverter(matchController.getMatch());
        this.clientControllers = new ArrayList<>(5);
        this.hashNicknameID = new HashMap<>();
    }

    //with this method a client MUST register to the server so the server can call back the methods of InterfaceClientController
    public int register(InterfaceClientControllerRMI clientController, String nickname) throws FailedLoginException{
        System.out.println("[INFO]: Trying to connect a new client");
        String hashedTemp = "";       //inizialized to emptyString;
        try {
            clientControllers.add(clientController);
            addPlayer(nickname);
            clientController.ping();
            System.out.println("[INFO]: Client " + nickname + " pinged");
            System.out.println("[INFO]: There are " + clientControllers.size() + " players connected");
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

    public int hashNickname(String nickName){

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

    public Map getMap(int clientHashedID) {
        return matchController.getMap();
    }


    //metodi derivanti da classe moveController
    public void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed, int clientHashedID) throws NotAllowedMoveException, RemoteException {
        matchController.move(player, converter.indexToSquare(iDestination, jDestination), maxDistanceAllowed);
    }

    //TODO qui va corretto tutto aggiungendo anche il controllo sull'id hashato!! 
    //metodi da grab controller
    public void grabAmmoCard(int clientHashedID) throws Exception {
        matchController.getGrabController().grabAmmoCard();
    }

    public void grabWeapon(int indexOfWeapon, int clientHashedID) throws Exception {
        matchController.getGrabController().grabWeapon(converter.intToWeapon(indexOfWeapon));
    }

    //metodi di powerUpController
    @Deprecated
    public void usePowerUpAsAmmo(int indexOfPowerUp, int clientHashedID) throws Exception {
        matchController.getPowerUpController().usePowerUpAsAmmo(converter.indexToPowerUp(indexOfPowerUp));
    }

    public void useTeleporter(PowerUp teleporter, Square destination, int clientHashedID) throws NotInYourPossessException, WrongStatusException {
        matchController.useTeleporter(teleporter, destination);
    }

    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination, int clientHashedID) throws NotAllowedMoveException, NotInYourPossessException, WrongStatusException {
        matchController.useNewton(newton, affectedPlayer, destination);
    }

    public void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, int clientHashedID) throws NotAllowedTargetException, NotInYourPossessException, WrongStatusException {
        matchController.useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
    }

    public void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, int clientHashedID) throws NotInYourPossessException, WrongStatusException {
        matchController.useTargetingScope(targetingScope, affectedPlayer);
    }


    public String RMICallTest(String message, int clientHashedID) {
        System.out.println("Called test method with message: " + message);
        return "Called MatchController.RMICallTest(message) method with message: " + message;
    }

    public String checkConnection(String IP, int clientHashedID) {
        System.out.println("[INFO]: Connection with client " + IP + " completed successfully.");
        return "[RMIServer]: Connection status OK";
    }

    public void addPlayer(String nickName) throws  FailedLoginException, RemoteException{
        try {
            matchController.addPlayer(nickName);
            System.out.println("[INFO]: Player " + nickName + " connected successfully");
            notifyNewPlayers();
        }catch(Exception e){
            throw new FailedLoginException(e.getMessage());
        }
        finally {
            if(connectedPlayers() >= 3 && connectedPlayers() < 5) {
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
                            }
                        }, 10000
                );
            }

            if(connectedPlayers() == 5) {
                timeout.cancel();
                timeout.purge();
                askMap();
            }

        }
    }

    //this method is called when the match has to start. Only the player in status "MASTER" has the ownership to choose a map and call the method buildmap
    //from buildmap --> call startGame() that has to be changed
    //TODO change implementation of startGame().
    public void askMap() throws RemoteException{
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

    private void updateAllPlayersStatus(){
        System.out.println("[INFO]: Updating the status of all the players.");
        for(Player p: matchController.getMatch().getPlayers())
            matchController.goToNextStatus(p);
    }

    private void pushMatchToAllPlayers() throws RemoteException{
        System.out.println("[INFO]: Pushing the updated match to all the players ");
        for(InterfaceClientControllerRMI controller: clientControllers)
            controller.updateMatch(matchController.getMatch());
    }


    public void buildMap(int mapID, int clientHashedID) throws  WrongValueException, WrongStatusException, RemoteException {
        try {
            //building the map
            matchController.buildMap(mapID);
            //starting the game pushing the updated model on all the clients ( this method calls the main page of the GUI ! )
            startGame();
        }catch (WrongValueException e){
            throw new WrongValueException(e.getMessage());
        }
        catch (WrongStatusException e2){
            throw new WrongStatusException(e2.getMessage());
        }
        catch (RemoteException e){
            throw new RemoteException(e.getMessage());
        }
    }

    public void startGame() throws RemoteException{
        try {
            System.out.println("[INFO]: Enough players to start the new game");
            System.out.println("[INFO]: GAME STARTING");

            //notifying all the clients that the match is starting !
            for (InterfaceClientControllerRMI controller : clientControllers)
                controller.startGame();

        }catch(RemoteException e){
            //exception thrown in case of connection error with client!
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }

    }

    public int connectedPlayers(){
        return matchController.connectedPlayers();
    }

    public PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException{
        return matchController.getPlayerStatus(idPlayer);
    }

    public boolean getMatchStatus() throws RemoteException{
        return matchController.getMatchStatus();
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

            if (connectedPlayers() < 3) {
                System.out.println("[INFO]: Timeout killed");
                timeout.cancel();
                timeout.purge();
            }

            System.out.println("[INFO]: The client " + hashNicknameID.get(clientHashedID) + " has correctly been disconnected");
            System.out.println("[INFO]: The client "+ hashNicknameID.get(clientHashedID) + " is now in status:" + matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getStatus().getTurnStatus());

            for(Player p: matchController.getMatch().getPlayers()){
                System.out.println("[INFO]: The client "+ p.getNickname() + " is now in status:" + p.getStatus().getTurnStatus());

            }

        }
        catch(Exception e1){
            e1.printStackTrace();
        }
    }

    private void notifyNewPlayers(){
        try {
            for (InterfaceClientControllerRMI c : clientControllers) {
                c.updateConnectedPlayers(matchController.getMatch().getPlayers());
            }
            System.out.println("[INFO]: Client notified with new player list");
        } catch(RemoteException e){
            e.printStackTrace();
        }
    }


    private boolean checkHashedIDAsCurrentPlayer(int hashedID) {
        return hashNicknameID.get(hashedID).equals(matchController.getMatch().getCurrentPlayer().getNickname());
    }

    @Override
    public boolean checkIfConnected(String nickname) throws RemoteException {
        try {
            return matchController.getMatch().getPlayers().contains(matchController.getMatch().getPlayer(nickname));
        }
        catch (NullPointerException e){
            return false;
        }
    }
}
