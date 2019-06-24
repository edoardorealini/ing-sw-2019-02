package client.remoteController;

import client.GUI.FirstPage;
import commons.InterfaceConnectionHandler;
import commons.ShootingParametersClient;
import client.clientController.ReceiverClientControllerRMI;
import commons.InterfaceClientControllerRMI;
import exception.*;
import model.map.*;
import model.player.*;
import model.Match;
import model.powerup.*;

//TODO vedere server http
import commons.InterfaceServerControllerRMI;
import model.weapons.Weapon;

import javax.security.auth.login.FailedLoginException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SenderClientControllerRMI extends SenderClientRemoteController {

    private Match match;
    private InterfaceServerControllerRMI serverController;
    private InterfaceClientControllerRMI clientController;
    private InterfaceConnectionHandler connectionHandler;
    private FirstPage firstPage;
    private String nickname;
    private int hashedNickname;

    public SenderClientControllerRMI(String serverIP, String nickname, Match match, FirstPage fp) throws RemoteException, NotBoundException, FailedLoginException, InvalidInputException {
        try {
            this.match = match;
            this.firstPage = fp;
            //TODO EDO qui devo chidere all'oggetto connectionHandler su che partita loggarmi, ergo su che oggetto remoto bindarmi.
            //TODO do not HARCODE the port ask it to the user (coglione lui se la mette sbagliata)!
            Registry registry = LocateRegistry.getRegistry(serverIP, 1338);
            System.out.println("[INFO]: REGISTRY LOCATED CORRECTLY");
            clientController = new ReceiverClientControllerRMI(match, nickname, fp, this);

            connectionHandler = (InterfaceConnectionHandler) registry.lookup("connectionHandler");
            serverController = connectionHandler.askForConnection(clientController, nickname);

            System.out.println("[INFO]: LOOKUP AND BINDING GONE CORRECTLY");
            this.hashedNickname = serverController.register(clientController, nickname); //the server now has a controller to call methods on the client and return to the client his hashed nickname
            this.nickname = nickname;
        } catch (RemoteException e) {
            System.out.println("\n[ERROR]: Remote object not found");
            e.printStackTrace();

            throw new RemoteException("[ERROR]: Wrong IP or port, please retry");
        }
        catch (NotBoundException e) {
            System.out.println("\n[ERROR]: Remote object not bound correctly");
            throw new NotBoundException("[ERROR]: Wrong IP or port, please retry");
        }
        catch (FailedLoginException e){
            System.out.println(e.getMessage());
            throw new FailedLoginException(e.getMessage());
        }
    }

    @Override
    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public Match getMatch() {
        return null;
    }

    public void setFirstPage(FirstPage firstPage){
        this.firstPage = firstPage;
    }

    @Override
    public void buildMap(int mapID) throws WrongStatusException, WrongValueException, RemoteException, NotAllowedCallException {
        try {
            serverController.buildMap(mapID, this.hashedNickname);
        } catch (WrongStatusException e) {
            e.printStackTrace();
            throw new WrongStatusException(e.getMessage());
        }
        catch (WrongValueException e2){
            e2.printStackTrace();
            throw new WrongValueException(e2.getMessage());
        }
        catch(RemoteException e3){
            e3.printStackTrace();
            throw new RemoteException(e3.getMessage());
        } catch (NotAllowedCallException e) {
            e.printStackTrace();
            throw new NotAllowedCallException(e.getMessage());
        }
    }

    @Override
    public void setSkulls(int nSkulls) throws RemoteException, NotAllowedCallException {
        try {
            serverController.setSkulls(nSkulls, hashedNickname);
        } catch(RemoteException e) {
                e.printStackTrace();
                throw new RemoteException(e.getMessage());
        } catch (NotAllowedCallException e) {
            e.printStackTrace();
            throw new NotAllowedCallException(e.getMessage());
        }
    }

    @Override
    public void move(int iDestination, int jDestination) throws RemoteException, NotAllowedMoveException, InvalidInputException , WrongStatusException, NotAllowedCallException {
        try {
            serverController.move(iDestination, jDestination, this.hashedNickname);
        }catch(RemoteException remote){
            remote.printStackTrace();
            throw new RemoteException(remote.getMessage());
        }
        catch(NotAllowedMoveException notAllowedMove){
            notAllowedMove.printStackTrace();
            throw new NotAllowedMoveException(notAllowedMove.getMessage());
        }
        catch(InvalidInputException e){
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public void grabAmmoCard(int xDestination, int yDestination) throws WrongStatusException, WrongPositionException, NotAllowedCallException, RemoteException, InvalidInputException, NotAllowedMoveException {
        try {
            serverController.grabAmmoCard(xDestination, yDestination, this.hashedNickname);
        } catch (WrongStatusException e) {
            throw new WrongStatusException(e.getMessage());
        } catch (WrongPositionException e) {
            throw new WrongPositionException(e.getMessage());
        } catch (NotAllowedCallException e) {
            throw new NotAllowedCallException(e.getMessage());
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (NotAllowedMoveException e) {
            throw new NotAllowedMoveException(e.getMessage());
        }
    }

    @Override
    public void grabWeapon(int xDestination, int yDestination, int indexOfWeapon, int indexOfWeaponToSwap) throws NotAllowedCallException, WrongStatusException, RemoteException, NotEnoughAmmoException, WrongPositionException, InvalidInputException, NotAllowedMoveException {
        try{
            serverController.grabWeapon(xDestination, yDestination, indexOfWeapon, this.hashedNickname, indexOfWeaponToSwap);
        }catch (NotAllowedCallException e) {
            throw new NotAllowedCallException(e.getMessage());
        } catch (WrongStatusException e) {
            throw new WrongStatusException(e.getMessage());
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(e.getMessage());
        } catch (WrongPositionException e) {
            throw new WrongPositionException(e.getMessage());
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        } catch (NotAllowedMoveException e) {
            throw new NotAllowedMoveException(e.getMessage());
        }

    }

    @Override
    public void usePowerUpAsAmmo(int indexOfPowerUp) throws NotInYourPossessException, RemoteException, NotAllowedCallException {
        try {
            serverController.usePowerUpAsAmmo(indexOfPowerUp);
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        } catch (NotInYourPossessException e) {
            throw new NotInYourPossessException(e.getMessage());
        }

    }

    @Override
    public void useTeleporter(PowerUp teleporter, Square destination) {
        //TODO
    }

    @Override
    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination) {
        //TODO
    }

    @Override
    public void useTagBackGrenade(int indexOfTagBackGrenade) throws RemoteException, NotAllowedTargetException, WrongStatusException, NotInYourPossessException {
            serverController.useTagBackGrenade(indexOfTagBackGrenade, this.nickname, match.getCurrentPlayer().getNickname(), this.hashedNickname);
    }

    @Override
    public void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) {
        //TODO
    }

    @Override
    public String checkConnection(String IP) {
        try {
            return serverController.checkConnection(IP, this.hashedNickname);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "[RMIServer - ERROR]: Error in checking connection";

    }

    @Override
    public int connectedPlayers() {
        try {
            return serverController.connectedPlayers();
        }catch(RemoteException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public PlayerStatusHandler getPlayerStatus(int idPlayer){
        try {
            return serverController.getPlayerStatus(idPlayer);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean getMatchStatus(){
        try {
            return serverController.getMatchStatus();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void disconnectPlayer() {
        try {
            serverController.disconnectPlayer(this.hashedNickname);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public String getNickname() {
        return nickname;
    }

    public void spawn(int indexOfPowerUpInHand) throws NotInYourPossessException, WrongStatusException, RemoteException{
        try {
            serverController.spawn(indexOfPowerUpInHand, hashedNickname);
        } catch(NotInYourPossessException e){
            throw new NotInYourPossessException(e.getMessage());
        } catch(WrongStatusException e){
            throw new WrongStatusException(e.getMessage());
        } catch(RemoteException e){
            throw new RemoteException("Network call error");
        }
    }

    @Override
    public void shoot(ShootingParametersClient input) throws NotAllowedCallException, NotAllowedTargetException, NotAllowedMoveException, WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException, InvalidInputException {
        try {
            serverController.shoot(input, hashedNickname);
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        } catch (NotAllowedCallException e) {
           throw new NotAllowedCallException(e.getMessage());
        }  catch (NotAllowedMoveException e) {
            throw new NotAllowedMoveException(e.getMessage());
        } catch (NotAllowedShootingModeException e) {
            throw new NotAllowedShootingModeException(e.getMessage());
        } catch (WrongStatusException e) {
            throw new WrongStatusException(e.getMessage());
        } catch (NotAllowedTargetException e) {
            throw new NotAllowedTargetException(e.getMessage());
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(e.getMessage());
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    @Override
    public void skipAction() throws RemoteException, WrongStatusException{
        try{
            serverController.skipAction(this.hashedNickname);
        }catch (RemoteException e){
            throw new RemoteException();
        }
        catch (WrongStatusException e){
            throw new WrongStatusException(e.getMessage());
        }
    }

    @Override
    public void reload(int indexOfPowerUp) throws RemoteException, NotEnoughAmmoException, NotAllowedCallException, WrongStatusException {
        try {
            serverController.reload(indexOfPowerUp, hashedNickname);
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException(e.getMessage());
        } catch (NotAllowedCallException e) {
            throw new NotAllowedCallException(e.getMessage());
        } catch (WrongStatusException e) {
            throw new WrongStatusException(e.getMessage());
        }
    }

    // FRENZY METHODS

    public void makeAction1Frenzy(int posX,int posY,ShootingParametersClient input) throws RemoteException, WrongStatusException, NotAllowedTargetException, InvalidInputException, NotAllowedCallException, NotEnoughAmmoException, NotAllowedMoveException, NotAllowedShootingModeException {
        serverController.makeAction1Frenzy(posX,posY,input, this.hashedNickname);
    }

    public void makeAction1FrenzyLower(int posX,int posY,ShootingParametersClient input) throws RemoteException, WrongStatusException, NotAllowedTargetException, InvalidInputException, NotAllowedCallException, NotEnoughAmmoException, NotAllowedMoveException, NotAllowedShootingModeException {
        serverController.makeAction1FrenzyLower(posX,posY,input, this.hashedNickname);
    }

    public void makeAction2Frenzy(int posX,int posY) throws RemoteException, NotAllowedMoveException, NotAllowedCallException, WrongStatusException {
        serverController.makeAction2Frenzy(posX,posY, this.hashedNickname);
    }

    public void makeAction3Frenzy(int posX, int posY, Weapon wp, int indexOfWeaponToSwap) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {
        serverController.makeAction3Frenzy(posX,posY, wp, this.hashedNickname, indexOfWeaponToSwap);
    }

    public void makeAction2FrenzyLower(int posX, int posY, Weapon wp, int indexOfWeaponToSwap) throws NotAllowedMoveException, RemoteException, NotAllowedCallException, WrongStatusException {
        serverController.makeAction2FrenzyLower(posX,posY,wp, this.hashedNickname, indexOfWeaponToSwap);
    }

    @Override
    public void closeTimer(String timerName) throws RemoteException {
        try {
            serverController.closeTimer(timerName, hashedNickname);
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage());
        }
    }
}
