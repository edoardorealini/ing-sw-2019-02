package commons;

import exception.InvalidInputException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceConnectionHandler extends Remote {
    //todo aggiungere metodi per richiedere la connessione (uno dovrebbe bastare)
    InterfaceServerControllerRMI askForConnection(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, InvalidInputException;

}
