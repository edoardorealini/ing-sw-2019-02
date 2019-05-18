package server;

//TODO Johnny

import controller.MatchController;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;

public class AdrenalineRMIServer implements Runnable{

    private MatchController matchController;
    private int port;
    //TODO qui andrà dichiarato l'oggetto remoto vero e proprio (classe ancora da scrivere)

    public AdrenalineRMIServer(MatchController controller, int port){
        matchController = controller;
        this.port = port;
    }

    //prima prova mettendo come oggetto condiviso direttamente matchController
    public void run(){
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("matchController", matchController);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //dopo tutta la parte di setup dell' oggetto remoto
        System.out.println("[RMIServer]: ready to receive remote method calls");

    }

}
