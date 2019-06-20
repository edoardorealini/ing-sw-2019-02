package client.remoteController;

import client.GUI.FirstPage;
import commons.ShootingParametersClient;
import exception.*;
import model.map.*;
import model.player.*;
import model.Match;
import model.powerup.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract class SenderClientRemoteController {

    abstract Match getMatch();

    public abstract void buildMap(int mapID) throws Exception;

    //metodi derivanti da classe moveController
    public abstract void move(int iDestination, int jDestination) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException;

    //metodi da grab controller
    public abstract void grabAmmoCard(int xDestination, int yDestination) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException;

    public abstract void grabWeapon(int xDestination, int yDestination, int indexOfWeapon) throws NotAllowedCallException, WrongStatusException, RemoteException, NotEnoughAmmoException, WrongPositionException, InvalidInputException, NotAllowedMoveException;

    //metodi di powerUpController
    public abstract void usePowerUpAsAmmo(int indexOfPowerUp) throws NotInYourPossessException, RemoteException, NotAllowedCallException;

    public abstract void useTeleporter(PowerUp teleporter, Square destination);

    public abstract void useNewton(PowerUp newton, Player affectedPlayer, Square destination);

    public  abstract void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer);

    public abstract void useTargetingScope(PowerUp targetingScope, Player affectedPlayer);

    public abstract String checkConnection(String IP);

    public abstract int connectedPlayers();

    public abstract PlayerStatusHandler getPlayerStatus(int idPlayer);

    public abstract boolean getMatchStatus();

    public abstract void setFirstPage(FirstPage firstPage);

    public  abstract void disconnectPlayer();

    public abstract String getNickname();

    public abstract void setSkulls(int nSkulls) throws RemoteException, NotAllowedCallException;

    public abstract void spawn(int indexOfPowerUpInHand) throws RemoteException, NotInYourPossessException, WrongStatusException;

    public abstract void shoot(ShootingParametersClient input) throws RemoteException, NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException, InvalidInputException;

    public abstract void skipAction() throws RemoteException, WrongStatusException;

    public abstract void reload(int indexOfPowerUp) throws RemoteException, NotEnoughAmmoException, NotAllowedCallException, WrongStatusException;

    public abstract void makeAction1Frenzy(int posX,int posY,ShootingParametersClient input);

    public abstract void makeAction1FrenzyLower(int posX,int posY,ShootingParametersClient input);

    public abstract void makeAction2Frenzy(int posX,int posY);
}
