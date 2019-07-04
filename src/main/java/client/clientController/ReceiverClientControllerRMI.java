package client.clientController;

//this is the remote object that the client shares with the server in order to help the server push information whenever needed
//info such as Model updates, turn status or other stuff.

import client.GUI.ChooseMap;
import client.GUI.FirstPage;
import client.GUI.MainPage;
import client.GUI.RespawnPopUp;
import client.remoteController.SenderClientRemoteController;
import commons.InterfaceClientControllerRMI;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Match;
import model.player.AbilityStatus;
import model.player.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


/**
 * This class is the remote object that the client exports to the server when connecting.
 * It is used by the server to act directly on the client
 * Double way communication
 * here are implemented all the methods that the server can call remotely to the client
 */
public class ReceiverClientControllerRMI extends UnicastRemoteObject implements InterfaceClientControllerRMI, ReceiverClientController {

    private Match match;
    private String nickname;
    private FirstPage firstPage;
    private ChooseMap chooseMap = null;
    private RespawnPopUp respawnPopUp;
    private MainPage mainPage;
    private SenderClientRemoteController senderRemoteController;

    public ReceiverClientControllerRMI(Match match, String nickname, FirstPage fp, SenderClientRemoteController senderClientRemoteController) throws RemoteException{
        this.match = match;
        this.nickname = nickname;
        this.firstPage = fp;
        this.senderRemoteController = senderClientRemoteController;
    }

    //here are implemented all the methods that the server can call remotely to the client

    /**
     * This  method is called by the server to check connection
     * @throws RemoteException when the client is disconnected
     */
    public void ping() throws RemoteException{
        return;
    }

    /**
     * Updates the view of the connected players
     * @param connectedPlayers updated connectedPlayers passed by the server
     * @throws RemoteException if network fails
     */
    public void updateConnectedPlayers(ArrayList<Player> connectedPlayers) throws RemoteException{
        match.setPlayers(connectedPlayers);
        for (int i=0;i<match.getPlayers().size();i++){
            System.out.println("[LOBBY]: Player "+ match.getPlayers().get(i).getNickname()+ " is in lobby");
        }
        System.out.println("[LOBBY]: Refreshing Lobby..");
        Platform.runLater(() -> firstPage.refreshPlayersInLobby());// Update on JavaFX Application Thread
    }

    /**
     * The server calls automatically this method when a client can pay a cost with a powerUp
     */
    public void askForPowerUpAsAmmo() {
        mainPage.setRemoteController(senderRemoteController);
        mainPage.setMatch(match);
        if (!mainPage.isPowerUpAsAmmoActive()) {      //check if there is a PowerUpAsAmmo already active
            Platform.runLater(
                    () -> {
                        try {
                            mainPage.askForPowerUpAsAmmo();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
        }

    }

    /**
     * Gets the nickname linked to this controller
     * @return returns the nickname
     * @throws RemoteException network error
     */
    public String getNickname() throws RemoteException{
        return this.nickname;
    }

    /**
     * This method is called when the match is started, the client has to see the MainPage
     */
    public void startGame(){
        System.out.println("[SERVER]: Starting a new game");
        mainPage = new MainPage();
        mainPage.setMatch(match);
        mainPage.setRemoteController(senderRemoteController);

        Platform.runLater( () -> {
                    try {
                        firstPage.closePrimaryStage();
                        if(chooseMap != null)
                            chooseMap.closePrimaryStage();
                        mainPage.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    /**
     * Updates the values in the client copy of the match object
     * @param match the updated match from the server (without the decks)
     */
    public void updateMatch(Match match){
        setMatch(match);
        Platform.runLater( () -> {
                firstPage.setMatch(match);
                firstPage.refreshPlayersInLobby();
        });
        if(mainPage != null) {
            Platform.runLater( () -> {
                mainPage.setMatch(match);
                senderRemoteController.setMatch(match);
                this.match = match;
                mainPage.refreshPlayersPosition();
                mainPage.refreshPoints();
                if(match.getPlayer(senderRemoteController.getNickname()).getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY)){
                    mainPage.setFrenzyMode(true);
                    mainPage.frenzyButtonBoosted();
                    System.out.println("[FRENZY]: Started FINAL FRENZY");
                    System.out.println("[FRENZY]: Current Player: "+match.getCurrentPlayer().getNickname()+ " in status "+match.getCurrentPlayer().getStatus().getTurnStatus());

                }
                if (match.getPlayer(senderRemoteController.getNickname()).getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY_LOWER)){
                    mainPage.setFrenzyMode(true);
                    mainPage.frenzyButtonLower();
                }
            });
        }
        //questo metodo viene chiamato piu volte.
    }

    private void setMatch(Match m){
        this.match = m;
    }

    /**
     * Used to ask the map to the MASTER player (opens the chooseMap window)
     * @throws Exception thrown by Application start method (JavaFX)
     */
    public void askMap() throws Exception{
        // Platform.runLater( () -> firstPage.closePrimaryStage());
        chooseMap = new ChooseMap();
        chooseMap.setRemoteController(senderRemoteController);
        Platform.runLater(
                () -> {
                    // Update UI here.
                    try {
                        chooseMap.start(new Stage());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );

    }

    /**
     * This method is called by the server on the client when he has to spawn or respawn
     * @throws RemoteException on network error
     */
    public void askSpawn() throws RemoteException{
        respawnPopUp = new RespawnPopUp();
        respawnPopUp.setSenderRemoteController(senderRemoteController);
        respawnPopUp.setMatch(match);

        Platform.runLater(
                ()-> {
                    try{
                        respawnPopUp.start(new Stage());
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
        );
    }

    public void waitForMap(){
        //LOL
    }

    /**
     * Asks to use the tagback grenade
     * @throws RemoteException on network error
     */
    @Override
    public void askForTagBackGrenade() throws RemoteException {
        mainPage.setRemoteController(senderRemoteController);
        mainPage.setMatch(match);
        Platform.runLater(
                    () -> {
                        try {
                            mainPage.askForTagBackPopup();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
    }

    /**
     * Asks the client to use the targeting scope
     * @throws RemoteException on network error
     */
    @Override
    public void askForTargetingScope() throws RemoteException {
        mainPage.setRemoteController(senderRemoteController);
        mainPage.setMatch(match);
        Platform.runLater(
                () -> {
                    try {
                        mainPage.askForTargetingScope();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * During the final moments of the game, called to popUp the ranking of the match
     * @throws RemoteException on network error
     */
    public void createRanking() throws RemoteException{
        mainPage.setRemoteController(senderRemoteController);
        mainPage.setMatch(match);
        Platform.runLater(
                () -> {
                    try {
                        mainPage.createRanking();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
