package client.clientController;

//this is the remote object that the client shares with the server in order to help the server push information whenever needed
//info such as Model updates, turn status or other stuff.

import commons.InterfaceClientControllerRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientControllerRMI extends UnicastRemoteObject implements InterfaceClientControllerRMI, ClientController{

    public ClientControllerRMI() throws RemoteException{
        //TODO
    }

    //here are implemented all the methods that the server can call remotely to the client

    public void ping() throws RemoteException {
        return;
    }

    public void run(){

    }

}
