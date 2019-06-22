package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceConnectionHandler;
import commons.InterfaceServerControllerRMI;
import controller.MatchController;
import exception.InvalidInputException;
import model.Match;
import model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionHandler extends UnicastRemoteObject implements InterfaceConnectionHandler {

    private ArrayList<InterfaceServerControllerRMI> serverControllers;      //list of all the matches ONLINE
    private HashMap<String, InterfaceServerControllerRMI> connectedPlayers; //links a connected player to his controller, useful for RECONNECTIONS! (after a disconnect)

    public ConnectionHandler() throws RemoteException{
        //here i create the first "remote controller"
        this.serverControllers = new ArrayList<>();
        this.connectedPlayers = new HashMap<>(10);
    }

    //this method is very similar to the register method in ServerControllerRMI!
    public InterfaceServerControllerRMI askForConnection(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, InvalidInputException {
        //per prima cosa dovrei verificare se il giocatore è già connesso su uno dei "server" che ho nella lista
        //altrimenti devo creare un server nuovo di PACCA
        //inizio a gestire il caso in cui devo creare un server nuovo in modo da testare subito almeno la creazione di una partita.

        //A player can't have the same name of another connected player (also in another match)
        for(InterfaceServerControllerRMI server: serverControllers){
            for(Player p: server.getPlayers())
                if(p.getNickname().equals(nickname) && server.getMatchStatus()) //check if there is a player with the same nickname into an active match.
                    throw new InvalidInputException("Please choose another nickname, this one is already in use in another match");
        }

        //checking if there is a not-started match in the queue of matches.
        for(InterfaceServerControllerRMI server: serverControllers){
            if(!server.getMatchStatus()){
                //if there is, i return this instance of the remote object (containing all the match information!) Useless to start a new thread here!
                System.out.println(("[CONNECTIONHANDLER]: There are " + serverControllers.size() + " active matches in this server"));
                return server;
            }
        }

        //otherwise i have to create a new match (by creating a new matchController and a new ServerController, then pushing it.
        InterfaceServerControllerRMI tmpServer = new ServerControllerRMI(new MatchController());
        serverControllers.add(tmpServer);
        connectedPlayers.put(nickname, tmpServer);
        System.out.println(("[CONNECTIONHANDLER]: There are " + serverControllers.size() + " active matches in this server"));
        return tmpServer;
        //se sono qui significa che non ci sono partite già iniziate.
    }
}
