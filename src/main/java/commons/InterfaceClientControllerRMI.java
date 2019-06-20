package commons;

import model.Match;
import model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceClientControllerRMI extends Remote {

    void ping() throws RemoteException;
    void updateConnectedPlayers(ArrayList<Player> connectedPlayers) throws RemoteException;
    String getNickname() throws RemoteException;
    void startGame() throws RemoteException;
    void askMap() throws  RemoteException, Exception;
    void askSpawn() throws RemoteException;
    void waitForMap() throws RemoteException;
    void updateMatch(Match match) throws RemoteException;
    void askForPowerUpAsAmmo() throws RemoteException;
    void askForTagBackGrenade() throws RemoteException;
}
