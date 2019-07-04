package server;

import commons.PropertiesLoader;
import controller.MatchController;
import model.Match;
import server.RMIHandler.AdrenalineRMIServer;
import server.RMIHandler.ConnectionHandler;
import
        server.socketHandler.AdrenalineSocketServer;

import java.io.InputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This is the Main class of the server part of the application
 */
public class AdrenalineServer {

    private Match match;
    private MatchController matchController;
    private ConnectionHandler connectionHandler;
    private int socketPort;
    private int rmiPort;
    private ExecutorService executor;

    /**
     * Another constructor, builds the match and all the controllers
     * @param rmiPort requires in input the port
     */
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

    /**
     * Default constructor, builds the match and all the controllers
     *
     */
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

    /**
     * This method launches the runnable that manages the socketServer (receives socket connections)
     */
    public void launchSocketServer(){
        executor.submit(new AdrenalineSocketServer(matchController, socketPort));
    }

    /**
     * This method launches the runnable that manages the RMI server (binding of remote object and receiving calls)
     * @param port requires the port to bind the registry to
     * @throws RemoteException if a network error occurs
     */

    public void launchRMIServer(int port) throws RemoteException {
        executor.submit(new AdrenalineRMIServer(matchController, port));
        try {
            System.out.println("Launched RMIServer  with IP: " + InetAddress.getLocalHost().getHostAddress() + " on port " + port + ", waiting for connection requests");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Launches the connection handles, the first remote object published on the registry, waits for connections
     * @param port requirest the port to bind the registry to
     */
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

    /**
     * Main of AdrenalineServer
     * @param args contains the port given from the command line launch command
     */
    public static void main(String[] args) { //La porta si può chiedere come parametro di input in args

        if(args.length != 0){
            AdrenalineServer mainServer = new AdrenalineServer(Integer.parseInt(args[0]));
            try {
                System.out.println("\nWELCOME TO ADRENALINE SERVER v2.0.0");
                System.out.println("Developed by:  GioValca, MADSOMMA, RealNGneer");
                System.out.println("All rights reserved, July 2019\n\n");
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
                System.out.println("\nWELCOME TO ADRENALINE SERVER v2.0.0");
                System.out.println("Developed by:  GioValca, MADSOMMA, RealNGneer");
                System.out.println("All rights reserved, July 2019\n\n");
                //mainServer.launchRMIServer(RMIPort);
                mainServer.launchRMIConnectionHandler(RMIPort);
            }catch (Exception e1){
                e1.printStackTrace();
            }

        }
    }

}
