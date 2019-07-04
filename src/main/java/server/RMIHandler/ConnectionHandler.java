package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceConnectionHandler;
import commons.InterfaceServerControllerRMI;
import controller.MatchController;
import exception.InvalidInputException;
import model.player.Player;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * ConnectionHandler is used to manage the incoming connections from new or already registered clients.
 * This is a class that extends UnicastRemoteObject, giving the possibility to be used from the client side
 * @author edoardo
 */

public class ConnectionHandler extends UnicastRemoteObject implements InterfaceConnectionHandler {

    private ArrayList<InterfaceServerControllerRMI> serverControllers;                              //list of all the matches ONLINE
    private ArrayList<InterfaceClientControllerRMI> clientControllers;                              //references to all the client controllers
    private HashMap<InterfaceClientControllerRMI, InterfaceServerControllerRMI> clientToServer;     //links a connected player's controller to his controller, useful for RECONNECTIONS! (after a disconnect)
    private HashMap<String, InterfaceServerControllerRMI> nicknameToServer;
    private HashMap<InterfaceClientControllerRMI, Integer> clientToHashedNickname;                  // save the name of the connected players.

    private Timer clientCheckerTimer;
    private TimerTask clientCheckerTask;

    /**
     * Default constructor, it builds all the data structures used to trace all the connections
     * @throws RemoteException when a network error occurs
     */
    public ConnectionHandler() throws RemoteException{
        //here i create the first "remote controller"
        this.serverControllers = new ArrayList<>();
        this.clientControllers = new ArrayList<>();
        this.clientToServer = new HashMap<>(100);
        this.nicknameToServer = new HashMap<>(100);
        this.clientToHashedNickname = new HashMap<>(100);
    }

    //this method is very similar to the register method in ServerControllerRMI!

    /**
     *  this method is very similar to the register method in ServerControllerRMI,
     *  basically is the method that a client has to call to make a connection with the server
     * @param clientController the client must give in input his clientController (also a remote object)
     * @param nickname the nicknama chosen by the user
     * @return returns to the client the reference to the server controller (also a remote object)
     * @throws RemoteException when a network error occurs
     * @throws InvalidInputException if the input is not correct
     * @see InterfaceClientControllerRMI
     * @see InterfaceServerControllerRMI
     */
    public InterfaceServerControllerRMI askForConnection(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, InvalidInputException {
        //per prima cosa dovrei verificare se il giocatore è già connesso su uno dei "server" che ho nella lista
        //altrimenti devo creare un server nuovo di PACCA
        //inizio a gestire il caso in cui devo creare un server nuovo in modo da testare subito almeno la creazione di una partita.

        //A player can't have the same name of another connected player (also in another match)
        for(InterfaceServerControllerRMI server: serverControllers){
            for(Player p: server.getPlayers()) {
                if (p.getNickname().equals(nickname) && server.getMatchStatus() && p.isConnected()) //check if there is a player with the same nickname into an active match.
                    throw new InvalidInputException("Please choose another nickname, this one is already in use in another match");

                if (p.getNickname().equals(nickname) && !p.isConnected() && server.getMatchStatus()) { //lobby reconnection !
                    clientToServer.put(clientController, server);
                    clientToHashedNickname.put(clientController, hashNickname(nickname));
                    clientControllers.add(clientController);
                    return server;
                }
            }
        }
        //checking if there is a not-started match in the queue of matches.
        for(InterfaceServerControllerRMI server: serverControllers){
            if(!server.getMatchStatus()){
                //if there is, i return this instance of the remote object (containing all the match information!) Useless to start a new thread here!
                System.out.println(("[CONNECTIONHANDLER]: There are " + serverControllers.size() + " active matches on this server"));
                clientToServer.put(clientController, server);
                clientToHashedNickname.put(clientController, hashNickname(nickname));
                nicknameToServer.put(nickname, server);
                clientControllers.add(clientController);
                return server;
            }
        }
        //otherwise i have to create a new match (by creating a new matchController and a new ServerController) then pushing it.
        InterfaceServerControllerRMI tmpServer = new ServerControllerRMI(new MatchController());
        serverControllers.add(tmpServer);
        clientToServer.put(clientController, tmpServer);
        clientToHashedNickname.put(clientController, hashNickname(nickname));
        nicknameToServer.put(nickname, tmpServer);
        clientControllers.add(clientController);
        System.out.println(("[CONNECTIONHANDLER]: There are " + serverControllers.size() + " active matches on this server"));
        return tmpServer;
        //se sono qui significa che non ci sono partite già iniziate.
    }


    /**
     *  This method is called when the server starts,
     *  its job is to check if a client is disconnected (not declared disconnection, EG lost network connection (remoteException is thrown))
     *  It's implemented as a scheduled timer to a fixed rate.
     */
    //
    public void startClientPinger(){
        this.clientCheckerTimer = new Timer();
        this.clientCheckerTask = new TimerTask() {
            @Override
            public void run() {
                List<InterfaceClientControllerRMI> disconnectedPlayers = new ArrayList<>();
                for(InterfaceClientControllerRMI controller: clientControllers){
                    try {
                        Timer timer = new Timer();

                        TimerTask interruptTimerTask = new TimerTask() {
                            @Override
                            public void run() {
                                disconnectedPlayers.add(controller);
                                for(InterfaceClientControllerRMI controller: disconnectedPlayers){
                                    try {
                                        int hashedID = clientToHashedNickname.get(controller);
                                        clientToServer.get(controller).disconnectPlayer(hashedID);
                                        clientToServer.remove(controller);
                                        clientToHashedNickname.remove(controller);
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                                clientControllers.removeAll(disconnectedPlayers);
                                timer.cancel();
                            }
                        };


                        timer.schedule(interruptTimerTask, 1500);
                        controller.ping();
                        timer.cancel();

                    } catch (RemoteException e) {
                        disconnectedPlayers.add(controller);
                    }
                }
                //here in disconnectedPlayers collection I have all the disconnectedPlayers players.
                for(InterfaceClientControllerRMI controller: disconnectedPlayers){
                    try {
                        int hashedID = clientToHashedNickname.get(controller);
                        clientToServer.get(controller).disconnectPlayer(hashedID);
                        clientToServer.remove(controller);
                        clientToHashedNickname.remove(controller);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                clientControllers.removeAll(disconnectedPlayers);
            }
        };

        clientCheckerTimer.schedule(clientCheckerTask, 1, 3000);
    }

    private int hashNickname(String nickname){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(nickname.getBytes());
            String hashedTemp = new String(messageDigest.digest());
            return hashedTemp.hashCode();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
