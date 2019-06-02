package client.clientController;

//this is the remote object that the client shares with the server in order to help the server push information whenever needed
//info such as Model updates, turn status or other stuff.

import commons.InterfaceClientControllerRMI;
import model.Color;
import model.Match;
import model.player.Player;
import model.powerup.PowerUp;
import model.powerup.PowerUpName;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClientControllerRMI extends UnicastRemoteObject implements InterfaceClientControllerRMI, ClientController{

    private Match match;
    private String nickname;

    public ClientControllerRMI(Match match, String nickname) throws RemoteException{
        this.match = match;
        this.nickname = nickname;
    }

    //here are implemented all the methods that the server can call remotely to the client

    public void ping() throws RemoteException{
        return;
    }

    public void updateConnectedPlayers(ArrayList<Player> connectedPlayers) throws RemoteException{
        match.setPlayers(connectedPlayers);
    }

    public PowerUp askForPowerUpAsAmmo() {
        //TODO per johnny, scegli se chiamare un popup
        return new PowerUp(Color.RED, PowerUpName.TELEPORTER);
    }



}
