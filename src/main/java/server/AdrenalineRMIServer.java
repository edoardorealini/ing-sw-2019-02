package server;

//TODO Johnny

import controller.MatchController;

public class AdrenalineRMIServer implements Runnable{

    private MatchController matchController;
    private int port;
    //TODO qui andrà dichiarato l'oggetto remoto vero e proprio (classe ancora da scrivere)

    public AdrenalineRMIServer(MatchController controller, int port){
        matchController = controller;
        this.port = port;
    }

    public void run(){

        //dopo tutta la parte di setup dell' oggetto remoto
        System.out.println("[RMIServer]: ready to receive remote method calls");
    }

}
