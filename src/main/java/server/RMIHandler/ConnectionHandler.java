package server.RMIHandler;

import commons.InterfaceConnectionHandler;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionHandler extends UnicastRemoteObject implements InterfaceConnectionHandler {
    public ConnectionHandler() throws RemoteException{
        //here i create the first "remote controller"
    }
}
