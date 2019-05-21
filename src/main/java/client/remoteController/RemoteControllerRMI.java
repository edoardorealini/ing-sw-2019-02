package client.remoteController;

import model.Match;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;
import server.InterfaceRemoteObjectRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteControllerRMI extends RemoteController {

    private Match match;
    private InterfaceRemoteObjectRMI controller;

    public RemoteControllerRMI(String serverIP, int port) throws RemoteException { //TODO capire se passare la porta qui o meno
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP, port);
            controller = (InterfaceRemoteObjectRMI) registry.lookup("remoteController");
        } catch (Exception e) {
            System.out.println("\n[ERROR]: Remote object not found or bound correctly");
            throw new RemoteException("[ERROR]: Wrong IP or port, please retry");
        }
    }

    @Override
    public Map getMap() {
        try {
            return controller.getMap();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    Match getMatch() {
        return null;
    }

    @Override
    public void buildMap(int mapID) {

    }

    @Override
    void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed) {

    }

    @Override
    boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance) {
        return false;
    }

    @Override
    void moveOneSquare(String movement) {

    }

    @Override
    void moveOneSquare(String movement, Player player) {

    }

    @Override
    void grabAmmoCard() {

    }

    @Override
    void grabWeapon(int indexOfWeapon) {

    }

    @Override
    void usePowerUpAsAmmo(int indexOfPowerUp) {

    }

    @Override
    void useTeleporter(PowerUp teleporter, Square destination) {

    }

    @Override
    void useNewton(PowerUp newton, Player affectedPlayer, Square destination) {

    }

    @Override
    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) {

    }

    @Override
    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) {

    }

    @Override
    public String checkConnection(String IP) {
        try {
            return controller.checkConnection(IP);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "[RMIServer - ERROR]: Error in checking connection";

    }

    @Override
    public void addPlayer(String nickName) {
        try {
            controller.addPlayer(nickName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}