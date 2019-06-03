package commons;

import model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceClientControllerRMI extends Remote {

    void ping() throws RemoteException;
    void updateConnectedPlayers(ArrayList<Player> connectedPlayers) throws RemoteException;
    String getNickname() throws RemoteException;
}
