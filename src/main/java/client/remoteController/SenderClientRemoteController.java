package client.remoteController;

import client.GUI.FirstPage;
import exception.*;
import model.map.*;
import model.player.*;
import model.Match;
import model.powerup.*;

import javax.security.auth.login.FailedLoginException;
import java.rmi.RemoteException;

public abstract class SenderClientRemoteController {

    abstract Match getMatch();

    public abstract void buildMap(int mapID) throws Exception;

    //metodi derivanti da classe moveController
    abstract void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed, int clientHashedID) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException;

    //metodi da grab controller
    abstract void grabAmmoCard();

    abstract void grabWeapon(int indexOfWeapon);

    //metodi di powerUpController
    abstract void usePowerUpAsAmmo(int indexOfPowerUp);

    abstract void useTeleporter(PowerUp teleporter, Square destination);

    abstract void useNewton(PowerUp newton, Player affectedPlayer, Square destination);

    abstract void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer);

    abstract void useTargetingScope(PowerUp targetingScope, Player affectedPlayer);

    public abstract String checkConnection(String IP);

    public abstract int connectedPlayers();

    public abstract PlayerStatusHandler getPlayerStatus(int idPlayer);

    public abstract boolean getMatchStatus();

    public abstract void setFirstPage(FirstPage firstPage);

    public  abstract void disconnectPlayer();

    public abstract String getNickname();

    public abstract void setSkulls(int nSkulls) throws RemoteException, NotAllowedCallException;

    public abstract void spawn(int indexOfPowerUpInHand) throws RemoteException, NotInYourPossessException, WrongStatusException;
}
