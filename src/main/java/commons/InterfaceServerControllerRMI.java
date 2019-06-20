package commons; //package commons contais the common interfaces and classes (the whole model should be included too) between Client and Server

import exception.*;
import model.map.Square;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;

import javax.security.auth.login.FailedLoginException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServerControllerRMI extends Remote {

    int register(InterfaceClientControllerRMI clientController, String nickname) throws RemoteException, FailedLoginException;

    void buildMap(int mapID, int clientHashedID) throws RemoteException, WrongValueException, WrongStatusException, NotAllowedCallException;

    //metodi di moveController
    void move(int iDestination, int jDestination, int clientHashedID) throws NotAllowedMoveException, RemoteException, InvalidInputException, WrongStatusException, NotAllowedCallException;

    //metodi da grabController
    void grabAmmoCard(int xDestination, int yDestination, int clientHashedID) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException;

    void grabWeapon(int xDestination, int yDestination, int indexOfWeapon, int clientHashedID) throws WrongPositionException, NotEnoughAmmoException, WrongStatusException, NotAllowedCallException , RemoteException, InvalidInputException, NotAllowedMoveException;

    //metodi di powerUpController
    void useTeleporter(PowerUp teleporter, Square destination, int clientHashedID) throws RemoteException, NotInYourPossessException, WrongStatusException, NotAllowedCallException;

    void useNewton(PowerUp newton, Player affectedPlayer, Square destination, int clientHashedID) throws NotAllowedMoveException, RemoteException, NotInYourPossessException, WrongStatusException, NotAllowedCallException;

    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, int clientHashedID) throws RemoteException, NotAllowedTargetException, NotInYourPossessException, WrongStatusException;

    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, int clientHashedID) throws RemoteException, NotInYourPossessException, WrongStatusException, NotAllowedCallException;

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

    void makeAction1Frenzy(int posX, int posY, ShootingParametersClient input, int clientHashedID);

    void makeAction1FrenzyLower(int posX, int posY, ShootingParametersClient input, int clientHashedID);
}