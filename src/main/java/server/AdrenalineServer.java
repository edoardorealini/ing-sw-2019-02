package server;

import commons.PropertiesLoader;
import controller.MatchController;
import model.Match;
import server.RMIHandler.AdrenalineRMIServer;
import server.RMIHandler.ConnectionHandler;
import server.socketHandler.AdrenalineSocketServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdrenalineServer {

    private Match match;
    private MatchController matchController;
    private ConnectionHandler connectionHandler;
    private int socketPort;
    private int rmiPort;
    private ExecutorService executor;

    public AdrenalineServer(int rmiPort){
        try {
            match = new Match();
            matchController = new MatchController(match);
            this.connectionHandler = new ConnectionHandler();
            this.socketPort = socketPort;
            this.rmiPort = rmiPort;
            executor = Executors.newCachedThreadPool();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public AdrenalineServer(){
        try {
            match = new Match();
            matchController = new MatchController(match);
            this.connectionHandler = new ConnectionHandler();
            executor = Executors.newCachedThreadPool();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void launchSocketServer(){
        executor.submit(new AdrenalineSocketServer(matchController, socketPort));
    }

    public void launchRMIServer(int port) throws RemoteException {
        executor.submit(new AdrenalineRMIServer(matchController, port));
        try {
            System.out.println("Launched RMIServer  with IP: " + InetAddress.getLocalHost().getHostAddress() + " on port " + port + ", waiting for connection requests");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void launchRMIConnectionHandler(int port){
        //qui devo istanziare un connectionHandler e pubblicarlo sul registry.
        try {
            //Il codice qui sotto diventa obsoleto.
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("connectionHandler", connectionHandler);
            connectionHandler.startClientPinger();
            System.out.println("Launched Server on IP: " + InetAddress.getLocalHost().getHostAddress() + ", on port: " + port + ", waiting for connection requests");
            System.out.println("[SERVER]: Connection handler launched and registered, ready to receive connections");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getSocketPort() {
        return socketPort;
    }

    public int getRmiPort() {
        return rmiPort;
    }

    public void setRmiPort(int rmiPort) {
        this.rmiPort = rmiPort;
    }

    public InputStream getPropertiesInputStream(){
        return getClass().getResourceAsStream("/adrenaline.properties");
    }

    public static void main(String[] args) { //La porta si può chiedere come parametro di input in args

        if(args.length != 0){
            AdrenalineServer mainServer = new AdrenalineServer(Integer.parseInt(args[0]));
            try {
                System.out.println("\nWELCOME TO ADRENALINE MAIN SERVER v1.0.0");
                System.out.println("Developed by:  GioValca, MADSOMMA, RealNGneer");
                System.out.println("All rights reserved, 2019\n\n");
                System.out.println("Starting server on the command line specified port ");
                //mainServer.launchRMIServer(Integer.parseInt(args[0]));
                mainServer.launchRMIConnectionHandler(Integer.parseInt(args[0]));
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        else {
            Properties loader = new Properties();
            AdrenalineServer mainServer = new AdrenalineServer();
            int RMIPort = PropertiesLoader.getDefaultRMIPort();
            mainServer.setRmiPort(RMIPort);

            try {
                System.out.println("\nWELCOME TO ADRENALINE MAIN SERVER v3.0.0");
                System.out.println("Developed by:  GioValca, MADSOMMA, RealNGneer");
                System.out.println("All rights reserved, 2019\n\n");
                System.out.println("Default port value loaded from properties file: adrenaline.properties");
                //mainServer.launchRMIServer(RMIPort);
                mainServer.launchRMIConnectionHandler(RMIPort);
            }catch (Exception e1){
                e1.printStackTrace();
            }

        }
    }

}
