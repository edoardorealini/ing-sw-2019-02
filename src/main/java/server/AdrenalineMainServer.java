package server;

//TODO Edo, questa è la classe con il MAIN, sì, hai letto bene, il MAIN

import controller.MatchController;
import model.Match;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdrenalineMainServer {

    private Match match;
    private MatchController matchController;
    private int socketPort;
    private int rmiPort;
    private ExecutorService executor;

    public AdrenalineMainServer(int socketPort, int rmiPort){
        try {
            match = new Match();
            matchController = new MatchController(match);
            this.socketPort = socketPort;
            this.rmiPort = rmiPort;
            executor = Executors.newCachedThreadPool();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void launchSocketServer(){
        executor.submit(new AdrenalineSocketServer(matchController, socketPort));
    }

    public void launchRMIServer() throws RemoteException {
        //TODO capire ancora bene la logica
        executor.submit(new AdrenalineRMIServer(matchController, rmiPort));
    }

    public int getSocketPort() {
        return socketPort;
    }

    public int getRmiPort() {
        return rmiPort;
    }

    public static void main(String[] args) { //La porta si può chiedere come parametro di input in args

        AdrenalineMainServer mainServer = new AdrenalineMainServer(1337, 1338);

        try {
            mainServer.launchSocketServer();
            System.out.println("Launched SocketServer with IP: " + InetAddress.getLocalHost().getHostAddress() + " on port " + mainServer.getSocketPort() + ", waiting for connections");
            mainServer.launchRMIServer();
            System.out.println("Launched RMIServer  with IP: " + InetAddress.getLocalHost().getHostAddress() + " on port " + mainServer.getRmiPort() + ", waiting for connections");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
