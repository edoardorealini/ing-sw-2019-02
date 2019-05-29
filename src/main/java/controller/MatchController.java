package controller;

import exception.*;
import model.Match;
import model.map.*;
import model.map.MapBuilder;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;
import model.weapons.Weapon;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchController{

    private Match match;
    private GrabController grabController;
    private PowerUpController powerUpController;
    private ShootController shootController;
    private MoveController moveController;

    // ci sono altri attributi da mettere qui? in teoria no
    // pensare a tutta la logica di setup della partita. fornire metodi

    // logica di creazione della partita e management su classe a parte, nell'utilizzatore di match controller, qui posso mettere solo dei metidi che saranno invocati da un processo apposito alla gestione dei turni.

    /*
        Costruttore 1
    */
    public MatchController() {
        this.match = new Match();

        this.moveController = new MoveController(this.match);       //oggetto comune a tutti i controller!
        this.grabController = new GrabController(this.match, this.moveController);
        this.powerUpController = new PowerUpController(this.match, this.moveController);
        this.shootController = new ShootController(this.match, this.moveController); //pullando va a post
        /*
            questa parte la gestirei in un metodo di setup della partita dopo la scelta della mappa da utente
            match.getMap().fillWeaponBox(match.getWeaponDeck());
            match.getMap().fillAmmo(match.getAmmoDeck());

         */
    }

    /*
        Costruttore 2 (non builda match ma lo prende in input)
    */
    public MatchController(Match match) {
        this.match = match;

        this.moveController = new MoveController(this.match);       //oggetto comune a tutti i controller!
        this.grabController = new GrabController(this.match, this.moveController);
        this.powerUpController = new PowerUpController(this.match, this.moveController);
        this.shootController = new ShootController(this.match, this.moveController); //pullando va a post
        /*
            questa parte la gestirei in un metodo di setup della partita dopo la scelta della mappa da utente
            match.getMap().fillWeaponBox(match.getWeaponDeck());
            match.getMap().fillAmmo(match.getAmmoDeck());

         */
    }

    public synchronized Match getMatch() {
        return match;
    }

    public synchronized Map getMap() {
        return match.getMap();
    }

    public synchronized void buildMap(int mapID) throws WrongValueException, WrongStatusException{
        if(canChooseMap()) {
            if (mapID <= 4 && mapID >= 1) {

                try {
                    match.setMap(new MapBuilder().makeMap(mapID));
                } catch (Exception e) {
                    e.printStackTrace(); //non serve per ora gestire con logger
                }

                match.getMap().fillWeaponBox(match.getWeaponDeck());
                match.getMap().fillAmmo(match.getAmmoDeck());

            } else
                throw new WrongValueException("Not a valid mapID");

            match.getCurrentPlayer().goToNextStatus(); //from master to Spawn (first player and first turn)
        }

        else
            throw new WrongStatusException("To select the map you must be in status MASTER!");

    }

    //ONLY for tests, do not expose it
    public synchronized void buildMapForTest(int mapID) throws Exception{
        if(mapID <= 4 && mapID >= 1) {

            try {
                match.setMap(new MapBuilder().makeMap(mapID));
            } catch (Exception e) {
                e.printStackTrace(); //non serve per ora gestire con logger
            }
        }
        else
            throw new Exception("Not a valid mapID");

    }

    //metodi derivanti da classe moveController
    public  synchronized void move(Player player, Square destination, int maxDistanceAllowed) throws Exception{
        moveController.move(player, destination, maxDistanceAllowed);
    }

    public  synchronized boolean isAllowedMove(Square startingPoint, Square destination, int maxDistance) {
        return moveController.isAllowedMove(startingPoint, destination, maxDistance);
    }

    @Deprecated
    public synchronized  void moveOneSquare(Directions direction) throws Exception {
        moveController.moveOneSquare(direction);
    }

    @Deprecated
    public  synchronized void moveOneSquare(Directions direction, Player player) throws Exception {
        moveController.moveOneSquare(direction, player);
    }

    public  synchronized int minDistBetweenSquares(Square startingPoint, Square destination) {
        return moveController.minDistBetweenSquares(startingPoint, destination);
    }

    //metodi da grab controller
    public synchronized  void grabAmmoCard() throws WrongStatusException, WrongPositionException {
        if(canDoAction()) {
            try {
                grabController.grabAmmoCard();
            }catch(WrongPositionException e){
                throw new WrongPositionException(e.getMessage());
            }

            match.getCurrentPlayer().goToNextStatus();

        }
        else
            throw new WrongStatusException("You cannot grab any ammo now!");
    }

    public  synchronized void grabWeapon(Weapon weapon) throws Exception {
        if(canDoAction()) {
            try {
                grabController.grabWeapon(weapon);
            } catch (WrongPositionException e) {
                throw new WrongPositionException(e.getMessage());
            } catch (NotEnoughAmmoException e2) {
                throw new NotEnoughAmmoException(e2.getMessage());
            }

            match.getCurrentPlayer().goToNextStatus();

        }
        else
            throw new WrongStatusException("You cannot grab any weapons now!");
    }

    //metodi di powerUpController
    public  synchronized void usePowerUpAsAmmo(PowerUp powerUp) throws NotInYourPossessException, WrongStatusException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(powerUp)) {
                powerUpController.usePowerUpAsAmmo(powerUp);
            } else
                throw new NotInYourPossessException("The powerUp" + powerUp.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You cannot use a PowerUp now");
    }

    public  synchronized void useTeleporter(PowerUp teleporter, Square destination) throws NotInYourPossessException, WrongStatusException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(teleporter)) {
                powerUpController.useTeleporter(teleporter, destination);
            } else
                throw new NotInYourPossessException("The powerUp" + teleporter.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You cannot use a PowerUp now!");
    }

    public  synchronized void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws WrongStatusException, NotAllowedMoveException, NotInYourPossessException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(newton)) {
                powerUpController.useNewton(newton, affectedPlayer, destination);
            } else
                throw new NotInYourPossessException("The powerUp" + newton.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You cannot use a PowerUp now!");
    }

    public  synchronized void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) throws NotInYourPossessException, NotAllowedTargetException, WrongStatusException {
        if(canUseTagbackGrenade(user)) {
            if (user.hasPowerUp(tagbackGrenade)) {
                powerUpController.useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
            } else
                throw new NotInYourPossessException("The powerUp" + tagbackGrenade.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You are not allowed to use a TagBack Grenade now!");

    }

    public synchronized  void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) throws NotInYourPossessException, WrongStatusException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(targetingScope)) {
                powerUpController.useTargetingScope(targetingScope, affectedPlayer);
            } else
                throw new NotInYourPossessException("The powerUp" + targetingScope.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You cannot use a PowerUp now!");
    }

    public synchronized  MoveController getMoveController() {
        return moveController;
    }

    public  synchronized ShootController getShootController() {
        return shootController;
    }

    public  synchronized GrabController getGrabController() {
        return grabController;
    }

    public  synchronized PowerUpController getPowerUpController() {
        return powerUpController;
    }

    // metodo creazione di player
    public  synchronized void addPlayer(String nickName, int ID) {
        match.getPlayers().add(new Player(nickName, ID, getMatch()));
    }

    // aggiunto da edo, genera il player solo con il nickname e mette da solo id corretto sequenzialmente basandosi sulla dimensione dell'array di player in gioco
    //usare questo!!
    public synchronized  int addPlayer(String nickName) {
        match.getPlayers().add(new Player(nickName, match.getPlayers().size(), getMatch()));
        //setta current player se sono il primo a connettermi
        if (match.getPlayers().size() == 1)
            match.setCurrentPlayer(match.getPlayers().get(0));

        return match.getPlayers().size() - 1;
    }

    //returns the number of connected players
    public synchronized  int connectedPlayers(){
        return match.getPlayers().size();
    }

    //FOR TEST PURPOSE ONLY
    public  synchronized String RMICallTest(String message){
        System.out.println("Called test method with message: " + message);
        return "Called MatchController.RMICallTest(message) method with message: " + message;
    }

    public PlayerStatusHandler getPlayerStatus(int idPlayer) throws WrongValueException{
        if(idPlayer >= 0 && idPlayer < match.getPlayers().size())
            return match.getPlayers().get(idPlayer).getStatus();

        else
            throw new WrongValueException("Not valid playerID, retry");
    }

    public boolean getMatchStatus(){
        return match.getActiveStatusMatch();
    }

    private boolean checkPlayerPresence(String playerNickname){
        for(Player p: match.getPlayers()){
            if(p.getNickname().equals(playerNickname) && !p.isConnected()){
                return true;
            }
        }
        return false;
    }

    //per action si intende solo MOVE, GRAB e SHOOT
    //insieme di metodi privati che servono per fare i controlli prima di fale le azioni nei metodi di matchController
    // NB quando fai il controllo nel metodo del controller lanciare l'eccezione WrongStatusException dicendo perchè c'è errore.

    private boolean canDoAction(){
        if(match.getCurrentPlayer().isInStatusFirstAction() || match.getCurrentPlayer().isInStatusSecondAction()){
            return true;
        }

        return false;
    }

    private boolean canUsePowerUp(){
        if(match.getCurrentPlayer().isInStatusFirstAction() || match.getCurrentPlayer().isInStatusSecondAction())
            return true;

        return false;
    }

    private boolean canChooseMap(){
        if(match.getCurrentPlayer().isInStatusMaster())
            return true;

        return false;
    }

    private boolean canUseTagbackGrenade(Player p){ //TODO aggiungere la gestione del fatto che devo aver subito danno !
        if(p.isInStatusWaitTurn())
            return true;

        return false;
    }

    //TODO metodi nuovi che devono essere aggiunti a tutto il giro (per ora solo RMI)per essere chiamati da client (vedi appuntiClient per capire cosa intendo con "giro")

    public void loginPlayer(String nickname) {
        if(checkPlayerPresence(nickname)) {
            //Il player è già registrato ma non era più in gioco (è in stato disconnected)
            match.getPlayer(nickname).getStatus().setTurnStatusWaitTurn();
            //metto il giocatore da disconnected a waitTurn !
        }
        else{
            addPlayer(nickname);
        }
    }

    //metodo da chiamare con trucchetto quando un player si disconnette (vedi appunti per capire trucchetto)
    //TODO implementare la gestione dello stato DISCONNECTED quando un utente si disconnette, LATO CLIENT chiamare il metodo per la disconnessione
    public void disconnectPlayer(String nickname){
        if(checkPlayerPresence(nickname)){
            match.getPlayer(nickname).getStatus().setTurnStatusDisconnected();
        }
    }

    public void shoot(ShootingParametersInput input) throws WrongStatusException{
        if(canDoAction()){
            //TODO ricky, qui devi implementare la shoot con lo switch a mappa

            match.getCurrentPlayer().goToNextStatus(); //non toccare
        }
        else
            throw new WrongStatusException("You are not allowed to shoot now!");
    }

}