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

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServerControllerRMI extends Remote {

    int register(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, FailedLoginException;

    Match getMatch(int clientHashedID) throws RemoteException;

    Map getMap(int clientHashedID) throws RemoteException;

    void buildMap(int mapID, int clientHashedID) throws RemoteException, Exception;

    //metodi derivanti da classe moveController
    void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed, int clientHashedID) throws Exception, RemoteException;

    boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance, int clientHashedID) throws RemoteException;

    void moveOneSquare(String movement, int clientHashedID) throws Exception, RemoteException;

    void moveOneSquare(String movement, Player player, int clientHashedID) throws Exception, RemoteException;


    //metodi da grab controller
    void grabAmmoCard(int clientHashedID) throws Exception, RemoteException;

    void grabWeapon(int indexOfWeapon, int clientHashedID) throws Exception, RemoteException;

    //metodi di powerUpController
    void usePowerUpAsAmmo(int indexOfPowerUp, int clientHashedID) throws Exception, RemoteException;

    void useTeleporter(PowerUp teleporter, Square destination, int clientHashedID) throws RemoteException;

    void useNewton(PowerUp newton, Player affectedPlayer, Square destination, int clientHashedID) throws NotAllowedMoveException, RemoteException;

    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, int clientHashedID) throws RemoteException, NotAllowedTargetException;

    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, int clientHashedID) throws RemoteException;


    String RMICallTest(String message, int clientHashedID) throws RemoteException;

    String checkConnection(String IP, int clientHashedID) throws RemoteException;

    void addPlayer(String nickName) throws RemoteException, FailedLoginException;

    int connectedPlayers() throws RemoteException;

    PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException, RemoteException;

    boolean getMatchStatus() throws RemoteException;

    void disconnectPlayer (int clientHashedID) throws RemoteException;

}