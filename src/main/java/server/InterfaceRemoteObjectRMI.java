package server;
import controller.MatchController;
import controller.MoveController;
import controller.ShootController;
import exception.NotAllowedMoveException;
import model.Match;
import model.map.Directions;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;
import model.weapons.Weapon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceRemoteObjectRMI extends Remote{

    Match getMatch() throws RemoteException;

    Map getMap() throws RemoteException;

    void buildMap(int mapID) throws RemoteException;

    //metodi derivanti da classe moveController
    void move(Player player, Square destination, int maxDistanceAllowed) throws Exception, RemoteException;

    boolean isAllowedMove(Square startingPoint, Square destination, int maxDistance) throws RemoteException;

    void moveOneSquare(Directions direction) throws  Exception, RemoteException;

    void moveOneSquare(Directions direction, Player player) throws Exception, RemoteException;


    //metodi da grab controller
    void grabAmmoCard() throws Exception, RemoteException;

    void grabWeapon(Weapon weapon) throws Exception, RemoteException;

    //metodi di powerUpController
    void usePowerUpAsAmmo(PowerUp powerUp) throws Exception, RemoteException;

    void useTeleporter(PowerUp teleporter, Square destination) throws RemoteException;

    void useNewton(PowerUp newton, Player affectedPlayer, Square destination)  throws NotAllowedMoveException, RemoteException;

    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) throws RemoteException;

    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) throws RemoteException;


    String RMICallTest(String message) throws  RemoteException;

}
