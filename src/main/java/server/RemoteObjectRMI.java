package server;
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

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjectRMI extends UnicastRemoteObject implements InterfaceRemoteObjectRMI {

    private InputConverter converter;
    private MatchController matchController;

    public RemoteObjectRMI(MatchController matchController) throws RemoteException {
        this.matchController = matchController;
        this.converter = new InputConverter(matchController.getMatch());
    }

    public Match getMatch() {
        return matchController.getMatch();
    }

    public Map getMap() {
        return matchController.getMap();
    }

    public void buildMap(int mapID) throws  Exception{
        try {
            System.out.println("[RMIServer]: Building Map with mapID = " + mapID);
            matchController.buildMap(mapID);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    //metodi derivanti da classe moveController
    public void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed) throws Exception {
        matchController.getMoveController().move(player, converter.indexToSquare(iDestination, jDestination), maxDistanceAllowed);
    }

    public boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance) {
        return matchController.getMoveController().isAllowedMove(startingPoint, converter.indexToSquare(iDestination, jDestination), maxDistance);
    }

    public void moveOneSquare(String movement) throws Exception {
        matchController.getMoveController().moveOneSquare(converter.stringToDirections(movement));
    }

    public void moveOneSquare(String movement, Player player) throws Exception {
        matchController.getMoveController().moveOneSquare(converter.stringToDirections(movement), player);
    }


    //metodi da grab controller
    public void grabAmmoCard() throws Exception {
        matchController.getGrabController().grabAmmoCard();
    }

    public void grabWeapon(int indexOfWeapon) throws Exception {
        matchController.getGrabController().grabWeapon(converter.intToWeapon(indexOfWeapon));
    }

    //metodi di powerUpController
    public void usePowerUpAsAmmo(int indexOfPowerUp) throws Exception {
        matchController.getPowerUpController().usePowerUpAsAmmo(converter.indexToPowerUp(indexOfPowerUp));
    }

    public void useTeleporter(PowerUp teleporter, Square destination) {
        matchController.getPowerUpController().useTeleporter(teleporter, destination);
    }

    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws NotAllowedMoveException {
        matchController.getPowerUpController().useNewton(newton, affectedPlayer, destination);
    }

    public void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) throws NotAllowedTargetException {
        matchController.getPowerUpController().useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
    }

    public void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) {
        matchController.getPowerUpController().useTargetingScope(targetingScope, affectedPlayer);
    }


    public String RMICallTest(String message) {
        System.out.println("Called test method with message: " + message);
        return "Called MatchController.RMICallTest(message) method with message: " + message;
    }

    public String checkConnection(String IP) {
        System.out.println("[INFO]: Connection with client " + IP + " completed successfully.");
        return "[RMIServer]: Connection status OK";
    }

    public int addPlayer(String nickName) {
        return matchController.addPlayer(nickName);
    }

    public int connectedPlayers(){
        return matchController.connectedPlayers();
    }

    public PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException{
        return matchController.getPlayerStatus(idPlayer);
    }



}
