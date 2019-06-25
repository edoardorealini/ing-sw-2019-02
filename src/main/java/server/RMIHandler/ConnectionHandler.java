package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceConnectionHandler;
import commons.InterfaceServerControllerRMI;
import controller.MatchController;
import exception.InvalidInputException;
import model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ConnectionHandler extends UnicastRemoteObject implements InterfaceConnectionHandler {

    private ArrayList<InterfaceServerControllerRMI> serverControllers;                              //list of all the matches ONLINE
    private ArrayList<InterfaceClientControllerRMI> clientControllers;                              //references to all the client controllers
    private HashMap<InterfaceClientControllerRMI, InterfaceServerControllerRMI> clientToServer;     //links a connected player's controller to his controller, useful for RECONNECTIONS! (after a disconnect)
    private HashMap<String, InterfaceServerControllerRMI> nicknameToServer;
    private HashMap<InterfaceClientControllerRMI, Integer> clientToHashedNickname;                  // save the name of the connected players.

    private Timer clientCheckerTimer;
    private TimerTask clientCheckerTask;

    public ConnectionHandler() throws RemoteException{
        //here i create the first "remote controller"
        this.serverControllers = new ArrayList<>();
        this.clientControllers = new ArrayList<>();
        this.clientToServer = new HashMap<>(100);
        this.nicknameToServer = new HashMap<>(100);
        this.clientToHashedNickname = new HashMap<>(100);
    }

    //this method is very similar to the register method in ServerControllerRMI!
    public InterfaceServerControllerRMI askForConnection(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, InvalidInputException {
        //per prima cosa dovrei verificare se il giocatore è già connesso su uno dei "server" che ho nella lista
        //altrimenti devo creare un server nuovo di PACCA
        //inizio a gestire il caso in cui devo creare un server nuovo in modo da testare subito almeno la creazione di una partita.

        //A player can't have the same name of another connected player (also in another match)
        for(InterfaceServerControllerRMI server: serverControllers){
            for(Player p: server.getPlayers()) {
                if (p.getNickname().equals(nickname) && server.getMatchStatus() && p.isConnected()) //check if there is a player with the same nickname into an active match.
                    throw new InvalidInputException("Please choose another nickname, this one is already in use in another match");

                if (p.getNickname().equals(nickname) && !server.getMatchStatus() && !p.isConnected()) { //lobby reconnection !
                    //TODO agguingere le chiamate alle map per aggiornare i riferimenti
                    return server;
                }

                if (p.getNickname().equals(nickname) && server.getMatchStatus() && !p.isConnected()) { //game reconnection !
                    //TODO agguingere le chiamate alle map per aggiornare i riferimenti
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

    //this method is called when the server starts, its job is to check if a client is disconnected (not declared disconnection, EG lost network connection (remoteException)
    public void startClientPinger(){
        this.clientCheckerTimer = new Timer();
        this.clientCheckerTask = new TimerTask() {
            @Override
            public void run() {
                List<InterfaceClientControllerRMI> disconnectedPlayers = new ArrayList<>();
                for(InterfaceClientControllerRMI controller: clientControllers){
                    try{
                        controller.ping();
                    }catch (RemoteException e){
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

        clientCheckerTimer.schedule(clientCheckerTask, 1, 2000);
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
