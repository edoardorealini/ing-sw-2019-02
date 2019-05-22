package server;
import exception.NotAllowedMoveException;
import exception.NotAllowedTargetException;
import model.Match;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRemoteObjectRMI extends Remote {

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

    int addPlayer(String nickName) throws RemoteException;

    int connectedPlayers() throws RemoteException;


}