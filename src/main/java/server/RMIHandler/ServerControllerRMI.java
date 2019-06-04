package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceServerControllerRMI;
import controller.InputConverter;
import controller.MatchController;
import exception.NotAllowedMoveException;
import exception.NotAllowedTargetException;
import exception.WrongValueException;
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
import java.util.ArrayList;
import java.util.HashMap;

//ex remoteObjectRMI
public class ServerControllerRMI extends UnicastRemoteObject implements InterfaceServerControllerRMI {

    private InputConverter converter;
    private MatchController matchController;
    private ArrayList<InterfaceClientControllerRMI> clientControllers; //better implementation with a Map!
    private HashMap<Integer, String>  hashNicknameID;  //it maps the nickname of a player with its hashed ID, the parameter used to identify a client

    public ServerControllerRMI(MatchController matchController) throws RemoteException {
        this.matchController = matchController;
        this.converter = new InputConverter(matchController.getMatch());
        this.clientControllers = new ArrayList<>(5);
        this.hashNicknameID = new HashMap<>();
    }

    //with this method a client MUST register to the server so the server can call back the methods of InterfaceClientController
    public int register(InterfaceClientControllerRMI clientController, String nickname) throws FailedLoginException{
        System.out.println("Test connection to client");
        String hashedTemp = "";       //inizialized to emptyString;
        try {
            clientControllers.add(clientController);
            addPlayer(nickname);
            clientController.ping();
            System.out.println("[INFO]: Client " + nickname + " pinged");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(nickname.getBytes());
            hashedTemp = new String(messageDigest.digest());
            this.hashNicknameID.put(hashedTemp.hashCode(), nickname);
            System.out.println("[INFO]: The player "+ nickname + " ID = " + matchController.getMatch().getPlayer(nickname).getId() + " is now in status: " + matchController.getMatch().getPlayer(nickname).getStatus().getTurnStatus());

        } catch(FailedLoginException e){
            System.out.println(e.getMessage());
            throw new FailedLoginException(e.getMessage());
        } catch(RemoteException | NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashedTemp.hashCode();
    }

    public Match getMatch(int clientHashedID) {
        return matchController.getMatch();
    }

    public Map getMap(int clientHashedID) {
        return matchController.getMap();
    }

    public void buildMap(int mapID, int clientHashedID) throws  Exception{
        try {
            System.out.println("[RMIServer]: Building Map with mapID = " + mapID);
            matchController.buildMap(mapID);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //metodi derivanti da classe moveController
    public void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed, int clientHashedID) throws Exception {
        matchController.getMoveController().move(player, converter.indexToSquare(iDestination, jDestination), maxDistanceAllowed);
    }

    public boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance, int clientHashedID) {
        return matchController.getMoveController().isAllowedMove(startingPoint, converter.indexToSquare(iDestination, jDestination), maxDistance);
    }

    public void moveOneSquare(String movement, int clientHashedID) throws Exception {
        matchController.getMoveController().moveOneSquare(converter.stringToDirections(movement));
    }

    public void moveOneSquare(String movement, Player player, int clientHashedID) throws Exception {
        matchController.getMoveController().moveOneSquare(converter.stringToDirections(movement), player);
    }


    //metodi da grab controller
    public void grabAmmoCard(int clientHashedID) throws Exception {
        matchController.getGrabController().grabAmmoCard();
    }

    public void grabWeapon(int indexOfWeapon, int clientHashedID) throws Exception {
        matchController.getGrabController().grabWeapon(converter.intToWeapon(indexOfWeapon));
    }

    //metodi di powerUpController
    public void usePowerUpAsAmmo(int indexOfPowerUp, int clientHashedID) throws Exception {
        matchController.getPowerUpController().usePowerUpAsAmmo(converter.indexToPowerUp(indexOfPowerUp));
    }

    public void useTeleporter(PowerUp teleporter, Square destination, int clientHashedID) {
        matchController.getPowerUpController().useTeleporter(teleporter, destination);
    }

    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination, int clientHashedID) throws NotAllowedMoveException {
        matchController.getPowerUpController().useNewton(newton, affectedPlayer, destination);
    }

    public void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, int clientHashedID) throws NotAllowedTargetException {
        matchController.getPowerUpController().useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
    }

    public void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, int clientHashedID) {
        matchController.getPowerUpController().useTargetingScope(targetingScope, affectedPlayer);
    }


    public String RMICallTest(String message, int clientHashedID) {
        System.out.println("Called test method with message: " + message);
        return "Called MatchController.RMICallTest(message) method with message: " + message;
    }

    public String checkConnection(String IP, int clientHashedID) {
        System.out.println("[INFO]: Connection with client " + IP + " completed successfully.");
        return "[RMIServer]: Connection status OK";
    }

    public void addPlayer(String nickName) throws  FailedLoginException{
        try {
            matchController.addPlayer(nickName);
            System.out.println("[INFO]: Player " + nickName + " connected succesfully");
            notifyNewPlayers();
        }catch(Exception e){
            throw new FailedLoginException(e.getMessage());
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

            System.out.println("[INFO]: The client " + hashNicknameID.get(clientHashedID) + " has correctly been disconnected");
            System.out.println("[INFO]: The client "+ hashNicknameID.get(clientHashedID) + " is now in status:" + matchController.getMatch().getPlayer(hashNicknameID.get(clientHashedID)).getStatus().getTurnStatus());

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


}
