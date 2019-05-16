package server;

//TODO Edo, questa è la classe con il MAIN, sì, hai letto bene, il MAIN

import controller.MatchController;
import model.Match;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdrenalineMainServer {

    private Match match;
    private MatchController matchController;
    private int port;
    private ExecutorService executor;

    public AdrenalineMainServer(int port){
        match = new Match();
        matchController = new MatchController(match);
        this.port = port;
        executor = Executors.newCachedThreadPool();
    }

    public void launchSocketServer(){
        executor.submit(new AdrenalineSocketServer(matchController, port));
    }

    public void launchRMIServer(){
        //TODO capire ancora bene la logica
        executor.submit(new AdrenalineRMIServer(matchController));
    }

    public int getPort() {
        return port;
    }

    public static void main(String[] args) { //La porta si può chiedere come parametro di input in args
        AdrenalineMainServer mainServer = new AdrenalineMainServer(1337);

        mainServer.launchSocketServer();
        System.out.println("Launched SocketServer on port " + mainServer.getPort() + ", waiting for connections");
        mainServer.launchRMIServer();
        System.out.println("Launched RMIServer on port " + mainServer.getPort() + ", waiting for connections");
    }

}
