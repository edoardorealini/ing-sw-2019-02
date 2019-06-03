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

import javax.security.auth.login.FailedLoginException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SenderClientControllerRMI extends SenderClientRemoteController {

    private Match match;
    private InterfaceServerControllerRMI serverController;
    private InterfaceClientControllerRMI clientController;
    private FirstPage firstPage;
    private String nickname;
    private int hashedNickname;

    public SenderClientControllerRMI(String serverIP, String nickname, Match match, FirstPage fp) throws RemoteException, NotBoundException, FailedLoginException{
        try {
            this.match = match;
            this.firstPage = fp;
            clientController = new ReceiverClientControllerRMI(match, nickname, fp);
            Registry registry = LocateRegistry.getRegistry(serverIP, 1338);
            System.out.println("[INFO]: REGISTRY LOCATED CORRECTLY");
            serverController = (InterfaceServerControllerRMI) registry.lookup("remoteController");
            System.out.println("[INFO]: LOOKUP AND BINDING GONE CORRECTLY");
            //UnicastRemoteObject.exportObject(clientController, 0);
            this.hashedNickname = serverController.register(clientController, nickname); //the server now has a controller to call methods on the client and return to the client his hashed nickname
            this.nickname = nickname;
        } catch (RemoteException e) {
            System.out.println("\n[ERROR]: Remote object not found");
            e.printStackTrace();

            throw new RemoteException("[ERROR]: Wrong IP or port, please retry");
        }
        catch (NotBoundException e) {
            System.out.println("\n[ERROR]: Remote object not bound correctly");
            throw new NotBoundException("[ERROR]: Wrong IP or port, please retry");
        }
        catch (FailedLoginException e){
            System.out.println(e.getMessage());
            throw new FailedLoginException(e.getMessage());
        }
    }

    @Override
    public Map getMap() {
        try {
            return serverController.getMap(this.hashedNickname);
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
            serverController.buildMap(mapID, this.hashedNickname);
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
            return serverController.checkConnection(IP, this.hashedNickname);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "[RMIServer - ERROR]: Error in checking connection";

    }

    @Override
    public void addPlayer(String nickName) throws FailedLoginException {
        try {
            serverController.addPlayer(nickName);
            firstPage.refreshPlayersInLobby();
        } catch (FailedLoginException e) {
            throw new FailedLoginException(e.getMessage());
        }
        catch (RemoteException e){
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
    public PlayerStatusHandler getPlayerStatus(int idPlayer){
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

    @Override
    public void disconnectPlayer() {
        try{
            serverController.disconnectPlayer(this.hashedNickname);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
