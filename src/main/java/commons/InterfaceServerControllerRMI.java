package commons; //package commons contais the common interfaces and classes (the whole model should be included too) between Client and Server

import exception.*;
import model.Color;
import model.player.Player;
import model.player.PlayerStatusHandler;

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceServerControllerRMI extends Remote {

    ArrayList<Player> getPlayers() throws RemoteException;

    int register(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, FailedLoginException;

    void buildMap(int mapID, int clientHashedID) throws RemoteException, WrongValueException, WrongStatusException, NotAllowedCallException;

    //metodi di moveController
    void move(int iDestination, int jDestination, int clientHashedID) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException;

    //metodi da grabController
    void grabAmmoCard(int xDestination, int yDestination, int clientHashedID) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException;

    void grabWeapon(int xDestination, int yDestination, int indexOfWeapon, int clientHashedID, int indexOfWeaponToSwap) throws WrongPositionException, NotEnoughAmmoException, WrongStatusException, NotAllowedCallException , RemoteException, InvalidInputException, NotAllowedMoveException;

    //metodi di powerUpController
    void useTeleporter(int indexOfPowerUp, int xDest, int yDest, int clientHashedID) throws RemoteException, NotInYourPossessException, WrongStatusException, NotAllowedCallException, NotAllowedMoveException, WrongPowerUpException, InvalidInputException;

    void useNewton(int indexOfPowerUp, String affectedPlayer, int xDest, int yDest, int clientHashedID) throws NotAllowedMoveException, RemoteException, NotInYourPossessException, WrongStatusException, NotAllowedCallException, WrongValueException, InvalidInputException, WrongPowerUpException;

    void useTagBackGrenade(int indexOfTagBackGrenade, String user, String affectedPlayer, int clientHashedID) throws RemoteException, NotInYourPossessException, WrongStatusException;

    void useTargetingScope(int indexOfTargetingScope, String affectedPlayer, int clientHashedID, Color ammoColorToPay) throws RemoteException, NotInYourPossessException, WrongStatusException, NotAllowedCallException, NotEnoughAmmoException;

    void usePowerUpAsAmmo(int indexOfPow) throws RemoteException, NotInYourPossessException, NotAllowedCallException ;

    void askForPowerUpAsAmmo() throws RemoteException;

    String RMICallTest(String message, int clientHashedID) throws RemoteException;

    String checkConnection(String IP, int clientHashedID) throws RemoteException;

    int connectedPlayers() throws RemoteException;

    PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException, RemoteException;

    boolean getMatchStatus() throws RemoteException;

    void disconnectPlayer (int clientHashedID) throws RemoteException;

    boolean checkIfConnected(String nickname) throws RemoteException;

    void setSkulls(int nSkulls, int clientHashedID) throws RemoteException, NotAllowedCallException;

    void spawn(int powerUpID, int clientHashedID) throws RemoteException, NotInYourPossessException, WrongStatusException;

    void shoot(ShootingParametersClient input, int clientHashedID) throws NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException, InvalidInputException;

    void skipAction(int clientHashedID) throws RemoteException, WrongStatusException;

    void reload(int indexOfWeapon, int clientHashedID) throws RemoteException, NotEnoughAmmoException, NotAllowedCallException, WrongStatusException;

    void makeAction1Frenzy(int posX, int posY, ShootingParametersClient input, int clientHashedID) throws RemoteException, NotAllowedTargetException, NotAllowedShootingModeException, InvalidInputException, WrongStatusException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedCallException;

    void makeAction1FrenzyLower(int posX, int posY, ShootingParametersClient input, int clientHashedID) throws RemoteException, NotAllowedTargetException, NotAllowedShootingModeException, InvalidInputException, WrongStatusException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedCallException;

    void makeAction2Frenzy(int posX, int posY, int clientHashedID) throws RemoteException, NotAllowedMoveException, NotAllowedCallException, WrongStatusException;

    void makeAction3Frenzy(int posX, int posY, int numbOfWeaponToGrab, int clientHashedID, int indexOfWeaponToSwap) throws RemoteException, NotAllowedMoveException, NotAllowedCallException, WrongStatusException;

    void makeAction2FrenzyLower(int posX, int posY, int numbOfWeaponToGrab, int clientHashedID, int indexOfWeaponToSwap) throws RemoteException, NotAllowedMoveException, NotAllowedCallException, WrongStatusException;

    void askForTagBackGrenade(String nickname) throws RemoteException;

    void closeTimer(String timerName, int clientHashedID) throws RemoteException;
}
