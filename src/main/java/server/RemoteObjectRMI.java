package server;
import controller.MatchController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteObjectRMI extends UnicastRemoteObject implements InterfaceRemoteObjectRMI {
    private MatchController matchController;

    public RemoteObjectRMI(MatchController matchController) throws RemoteException{
        this.matchController = matchController;
    }


}
