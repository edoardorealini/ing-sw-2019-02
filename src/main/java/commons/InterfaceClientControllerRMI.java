package commons;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceClientControllerRMI extends Remote {

    public void ping() throws RemoteException;

}
