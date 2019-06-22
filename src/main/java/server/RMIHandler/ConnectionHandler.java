package server.RMIHandler;

import commons.InterfaceClientControllerRMI;
import commons.InterfaceConnectionHandler;
import commons.InterfaceServerControllerRMI;
import controller.MatchController;
import model.Match;
import model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionHandler extends UnicastRemoteObject implements InterfaceConnectionHandler {

    private ArrayList<InterfaceServerControllerRMI> serverControllers;      //list of all the matches ONLINE
    private HashMap<String, InterfaceServerControllerRMI> connectedPlayers; //links a connected player to his controller

    public ConnectionHandler() throws RemoteException{
        //here i create the first "remote controller"
        this.serverControllers = new ArrayList<>();
        this.connectedPlayers = new HashMap<>(10);
    }

    //this method is very similar to the register method in ServerControllerRMI!
    public InterfaceServerControllerRMI askForConnection(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException {
        //per prima cosa dovrei verificare se il giocatore è già connesso su uno dei "server" che ho nella lista
        //altrimenti devo creare un server nuovo di PACCA
        //inizio a gestire il caso in cui devo creare un server nuovo in modo da testare subito almeno la creazione di una partita.
        for(InterfaceServerControllerRMI server: serverControllers){
            if(!server.getMatchStatus()){
                return server;
            }
        }

        InterfaceServerControllerRMI tmpServer = new ServerControllerRMI(new MatchController());
        serverControllers.add(tmpServer);
        connectedPlayers.put(nickname, tmpServer);
        return tmpServer;
        //se sono qui significa che non ci sono partite già iniziate.

    }
}
