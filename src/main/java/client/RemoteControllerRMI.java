package client;

import model.Match;
import model.*;
import model.map.Map;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;
import server.InterfaceRemoteObjectRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteControllerRMI extends RemoteController{

    private Match match;
    private InterfaceRemoteObjectRMI controller;

    public RemoteControllerRMI(String serverIP, int port){ //TODO capire se passare la porta qui o meno
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP,port);
            controller = (InterfaceRemoteObjectRMI) registry.lookup("remoteController");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    Map getMap() {
        return null;
    }

    @Override
    Match getMatch() {
        return null;
    }

    @Override
    void buildMap(int mapID) {

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
}
