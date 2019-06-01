package commons;

import model.player.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceClientControllerRMI extends Remote {

    public void ping() throws RemoteException;
    public void updateConnectedPlayers(ArrayList<Player> connectedPlayers) throws RemoteException;

}
