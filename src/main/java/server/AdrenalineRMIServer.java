package server;

import controller.MatchController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;

public class AdrenalineRMIServer implements Runnable{

    //private MatchController matchController; IL MATCHCONTROLLER NON SERVE CHE CE L'ABBIA QUESTA CLASSE
    private int port;
    private RemoteObjectRMI remoteObjectRMI;

    public AdrenalineRMIServer(MatchController controller, int port) throws RemoteException {
       // matchController = controller;
        this.port = port;
        this.remoteObjectRMI = new RemoteObjectRMI(controller);
    }

    //prima prova mettendo come oggetto condiviso direttamente matchController
    //il metodo run è come se fosse il main
    public void run(){
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("remoteController", remoteObjectRMI);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //dopo tutta la parte di setup dell' oggetto remoto
        System.out.println("[RMIServer]: ready to receive remote method calls");

    }

}
