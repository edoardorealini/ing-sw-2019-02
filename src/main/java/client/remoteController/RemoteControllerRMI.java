package client.remoteController;

import exception.*;
import model.map.*;
import model.player.*;
import model.Match;
import model.powerup.*;
import model.weapons.*;
import model.*;
import model.ammo.*;

//TODO vedere server http
import server.InterfaceRemoteObjectRMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteControllerRMI extends RemoteController {

    private Match match;
    private InterfaceRemoteObjectRMI controller;

    public RemoteControllerRMI(String serverIP, int port) throws RemoteException {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    Match getMatch() {
        return null;
    }

    @Override
    public void buildMap(int mapID) throws Exception {
        try {
            controller.buildMap(mapID);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
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
            e.printStackTrace();
        }

        return "[RMIServer - ERROR]: Error in checking connection";

    }

    @Override
    public int addPlayer(String nickName) {
        try {
            return controller.addPlayer(nickName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public int connectedPlayers() {
        try {
            return controller.connectedPlayers();
        }catch(RemoteException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public PlayerStatusHandler getPlayerStatus(int idPlayer){
        try {
            return controller.getPlayerStatus(idPlayer);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean getMatchStatus(){
        try {
            return controller.getMatchStatus();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
