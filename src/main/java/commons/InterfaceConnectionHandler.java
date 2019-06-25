package commons;

import exception.InvalidInputException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceConnectionHandler extends Remote {
    InterfaceServerControllerRMI askForConnection(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, InvalidInputException;
}
