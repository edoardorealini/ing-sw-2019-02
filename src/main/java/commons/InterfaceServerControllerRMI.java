package commons; //package commons contais the common interfaces and classes (the whole model should be included too) between Client and Server

import exception.NotAllowedMoveException;
import exception.NotAllowedTargetException;
import exception.WrongValueException;
import model.Match;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServerControllerRMI extends Remote {

    void register(InterfaceClientControllerRMI ClientController, String nickname) throws RemoteException;

    Match getMatch() throws RemoteException;

    Map getMap() throws RemoteException;

    void buildMap(int mapID) throws RemoteException, Exception;

    //metodi derivanti da classe moveController
    void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed) throws Exception, RemoteException;

    boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance) throws RemoteException;

    void moveOneSquare(String movement) throws Exception, RemoteException;

    void moveOneSquare(String movement, Player player) throws Exception, RemoteException;


    //metodi da grab controller
    void grabAmmoCard() throws Exception, RemoteException;

    void grabWeapon(int indexOfWeapon) throws Exception, RemoteException;

    //metodi di powerUpController
    void usePowerUpAsAmmo(int indexOfPowerUp) throws Exception, RemoteException;

    void useTeleporter(PowerUp teleporter, Square destination) throws RemoteException;

    void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws NotAllowedMoveException, RemoteException;

    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) throws RemoteException, NotAllowedTargetException;

    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) throws RemoteException;


    String RMICallTest(String message) throws RemoteException;

    String checkConnection(String IP) throws RemoteException;

    void addPlayer(String nickName) throws RemoteException;

    int connectedPlayers() throws RemoteException;

    PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException, RemoteException;

    boolean getMatchStatus() throws RemoteException;

}