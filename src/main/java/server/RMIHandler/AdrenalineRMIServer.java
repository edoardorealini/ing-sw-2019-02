package server.RMIHandler;

import controller.MatchController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;

public class AdrenalineRMIServer implements Runnable{

    //private MatchController matchController; IL MATCHCONTROLLER NON SERVE CHE CE L'ABBIA QUESTA CLASSE
    private int port;
    private ServerControllerRMI remoteObjectRMI;

    public AdrenalineRMIServer(MatchController controller, int port) throws RemoteException {
       // matchController = controller;
        this.port = port;
        this.remoteObjectRMI = new ServerControllerRMI(controller);
    }

    //prima prova mettendo come oggetto condiviso direttamente matchController
    //il metodo run è come se fosse il main
    public void run(){
        try {
            //TODO EDO qui devo creare l'oggetto connectionHandler che si preoccupa di creare i nuovi match e bindare i controller corretti sul registry.
            //Il codice qui sotto diventa obsoleto.
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
