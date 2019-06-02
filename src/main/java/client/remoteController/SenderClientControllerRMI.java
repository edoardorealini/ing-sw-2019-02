package client.remoteController;

import client.GUI.FirstPage;
import client.clientController.ReceiverClientControllerRMI;
import commons.InterfaceClientControllerRMI;
import model.map.*;
import model.player.*;
import model.Match;
import model.powerup.*;

//TODO vedere server http
import commons.InterfaceServerControllerRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SenderClientControllerRMI extends SenderClientRemoteController {

    private Match match;
    private InterfaceServerControllerRMI serverController;
    private InterfaceClientControllerRMI clientController;
    private FirstPage firstPage;

    public SenderClientControllerRMI(String serverIP, String nickname, Match match, FirstPage fp) throws RemoteException, NotBoundException{
        try {
            this.match = match;
            this.firstPage = fp;
            clientController = new ReceiverClientControllerRMI(match, nickname, fp);
            Registry registry = LocateRegistry.getRegistry(serverIP, 1338);
            serverController = (InterfaceServerControllerRMI) registry.lookup("remoteController");
            //UnicastRemoteObject.exportObject(clientController, 0);
            serverController.register(clientController, nickname); //the server now has a controller to call methods on the client
        } catch (RemoteException e) {
            System.out.println("\n[ERROR]: Remote object not found");
            e.printStackTrace();

            throw new RemoteException("[ERROR]: Wrong IP or port, please retry");
        }
        catch (NotBoundException e) {
            System.out.println("\n[ERROR]: Remote object not bound correctly");
            throw new NotBoundException("[ERROR]: Wrong IP or port, please retry");
        }
    }

    @Override
    public Map getMap(String nicknamePlayer) {
        try {
            return serverController.getMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    Match getMatch(String nicknamePlayer) {
        return null;
    }

    @Override
    public void buildMap(int mapID, String nicknamePlayer) throws Exception {
        try {
            serverController.buildMap(mapID);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed, String nicknamePlayer) {

    }

    @Override
    boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance, String nicknamePlayer) {
        return false;
    }

    @Override
    void moveOneSquare(String movement, String nicknamePlayer) {

    }

    @Override
    void moveOneSquare(String movement, Player player, String nicknamePlayer) {

    }

    @Override
    void grabAmmoCard(String nicknamePlayer) {

    }

    @Override
    void grabWeapon(int indexOfWeapon, String nicknamePlayer) {

    }

    @Override
    void usePowerUpAsAmmo(int indexOfPowerUp, String nicknamePlayer) {

    }

    @Override
    void useTeleporter(PowerUp teleporter, Square destination, String nicknamePlayer) {

    }

    @Override
    void useNewton(PowerUp newton, Player affectedPlayer, Square destination, String nicknamePlayer) {

    }

    @Override
    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, String nicknamePlayer) {

    }

    @Override
    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, String nicknamePlayer) {

    }

    @Override
    public String checkConnection(String IP, String nicknamePlayer) {
        try {
            return serverController.checkConnection(IP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "[RMIServer - ERROR]: Error in checking connection";

    }

    @Override
    public void addPlayer(String nickName) {
        try {
            serverController.addPlayer(nickName);
            firstPage.refreshPlayersInLobby();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public int connectedPlayers() {
        try {
            return serverController.connectedPlayers();
        }catch(RemoteException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public PlayerStatusHandler getPlayerStatus(int idPlayer, String nicknamePlayer){
        try {
            return serverController.getPlayerStatus(idPlayer);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean getMatchStatus(){
        try {
            return serverController.getMatchStatus();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void setFirstPage(FirstPage firstPage){
        this.firstPage = firstPage;
    }


}
