package client.remoteController;

import client.GUI.FirstPage;
import commons.ShootingParametersClient;
import exception.*;
import model.Color;
import model.player.*;
import model.Match;

import java.rmi.RemoteException;

/**
 * This class is implemented in RMI mode and Socket mode (still to be implemented)
 * Having this class can make all the existing code compatible with 2 different connection methods
 */
public abstract class SenderClientRemoteController {

    public abstract Match getMatch();

    public abstract void setMatch(Match match);

    public abstract void buildMap(int mapID) throws Exception;

    //metodi derivanti da classe moveController
    public abstract void move(int iDestination, int jDestination) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException;

    //metodi da grab controller
    public abstract void grabAmmoCard(int xDestination, int yDestination) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException;

    public abstract void grabWeapon(int xDestination, int yDestination, int indexOfWeapon, int indexOfWeaponToSwap) throws NotAllowedCallException, WrongStatusException, RemoteException, NotEnoughAmmoException, WrongPositionException, InvalidInputException, NotAllowedMoveException;

    //metodi di powerUpController
    public abstract void usePowerUpAsAmmo(int indexOfPowerUp) throws NotInYourPossessException, RemoteException, NotAllowedCallException;

    public abstract void useTeleporter(int indexOfTeleporter, int xDestination, int yDestination) throws RemoteException, WrongStatusException, NotInYourPossessException, NotAllowedMoveException, NotAllowedCallException, InvalidInputException, WrongPowerUpException;

    public abstract void useNewton(int indexOfNewton, String affectedPlayer, int xDestination, int yDestination) throws RemoteException, WrongStatusException, NotInYourPossessException, NotAllowedMoveException, NotAllowedCallException, WrongPowerUpException, WrongValueException, InvalidInputException;

    public abstract void useTagBackGrenade(int indexOfTagBackGrenade) throws RemoteException, NotAllowedTargetException, WrongStatusException, NotInYourPossessException;

    public abstract void useTargetingScope(int indexOfTargetingScope, String affectedPlayer, Color ammoColor) throws RemoteException, NotInYourPossessException, WrongStatusException, NotEnoughAmmoException, NotAllowedCallException, NotAllowedTargetException;

    public abstract int connectedPlayers();

    public abstract PlayerStatusHandler getPlayerStatus(int idPlayer);

    public abstract boolean getMatchStatus();

    public abstract void setFirstPage(FirstPage firstPage);

    public  abstract void disconnectPlayer();

    public abstract String getNickname();

    public abstract void setSkulls(int nSkulls) throws RemoteException, NotAllowedCallException;

    public abstract void spawn(int indexOfPowerUpInHand) throws RemoteException, NotInYourPossessException, WrongStatusException;

    public abstract void shoot(ShootingParametersClient input) throws RemoteException, NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException, InvalidInputException;

    public abstract void skipActionFrenzy() throws RemoteException, WrongStatusException;

    public abstract void skipAction() throws RemoteException, WrongStatusException;

    public abstract void reload(int indexOfPowerUp) throws RemoteException, NotEnoughAmmoException, NotAllowedCallException, WrongStatusException;

    public abstract void makeAction1Frenzy(int posX,int posY,ShootingParametersClient input) throws RemoteException, WrongStatusException, NotAllowedTargetException, InvalidInputException, NotAllowedCallException, NotEnoughAmmoException, NotAllowedMoveException, NotAllowedShootingModeException;

    public abstract void makeAction1FrenzyLower(int posX,int posY,ShootingParametersClient input) throws RemoteException, WrongStatusException, NotAllowedTargetException, InvalidInputException, NotAllowedCallException, NotEnoughAmmoException, NotAllowedMoveException, NotAllowedShootingModeException;

    public abstract void makeAction2Frenzy(int posX,int posY) throws RemoteException, NotAllowedMoveException, NotAllowedCallException, WrongStatusException;

    public abstract void makeAction3Frenzy(int posX, int posY, int numbOfWeaponToGrab, int indexOfWeaponToSwap) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException;

    public abstract void makeAction2FrenzyLower(int posX, int posY, int numbOfWeaponToGrab, int indexOfWeaponToSwap) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException;

    public abstract void closeTimer(String timerName) throws RemoteException;

    public abstract void ping() throws RemoteException;
}
