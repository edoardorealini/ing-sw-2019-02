package server;

//TODO Johnny

import controller.MatchController;

public class AdrenalineRMIServer implements Runnable{

    MatchController matchController;

    public AdrenalineRMIServer(MatchController controller){
        matchController = controller;
    }

    public void run(){

    }

}
