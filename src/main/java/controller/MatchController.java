package controller;

import exception.NotAllowedMoveException;
import exception.NotEnoughAmmoException;
import exception.WrongPositionException;
import model.Match;
import model.map.*;
import model.player.Player;
import model.powerup.PowerUp;
import model.weapons.Weapon;

import java.rmi.server.UnicastRemoteObject;

public class MatchController extends UnicastRemoteObject implements MatchControllerInterface {
    private Match match;
    private GrabController grabController;
    private PowerUpController powerUpController;
    private ShootController shootController;
    private MoveController moveController;

    //TODO ci sono altri attributi da mettere qui? in teoria no
    //TODO pensare a tutta la logica di setup della partita. fornire metodi

    //TODO logica di creazione della partita e management su classe a parte, nell'utilizzatore di match controller, qui posso mettere solo dei metidi che saranno invocati da un processo apposito alla gestione dei turni.

    /*
        Costruttore 1
    */
    public MatchController(){
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
    public MatchController(Match match){
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

    /*
        implementazione del metodo astratto dalla classe abstract.
    */
    public Match getMatch() {
        return match;
    }

    public Map getMap(){
        return match.getMap();
    }

    public void buildMap(int mapID) {
        try {
            match.setMap(new MapBuilder().makeMap(mapID));
        } catch (Exception e) {
            e.printStackTrace(); //non serve per ora gestire con logger
        }
        //TODO remember to uncomment the following lines after the test
        /*
        match.getMap().fillWeaponBox(match.getWeaponDeck());
        match.getMap().fillAmmo(match.getAmmoDeck());
        */
    }

    //metodi derivanti da classe moveController
    public void move(Player player, Square destination, int maxDistanceAllowed) throws Exception{
        moveController.move(player,destination,maxDistanceAllowed);
    }

    public boolean isAllowedMove(Square startingPoint, Square destination, int maxDistance){
        return moveController.isAllowedMove(startingPoint,destination,maxDistance);
    }

    public void moveOneSquare(Directions direction) throws  Exception{
        moveController.moveOneSquare(direction);
    }

    public void moveOneSquare(Directions direction, Player player) throws Exception{
        moveController.moveOneSquare(direction, player);
    }

    public int minDistBetweenSquares(Square startingPoint, Square destination){
        return moveController.minDistBetweenSquares(startingPoint,destination);
    }

    //metodi da grab controller
    public void grabAmmoCard() throws Exception {
        grabController.grabAmmoCard();
    }

    public void grabWeapon(Weapon weapon) throws Exception{
        grabController.grabWeapon(weapon);
    }

    //metodi di powerUpController
    public void usePowerUpAsAmmo(PowerUp powerUp) throws Exception{
        powerUpController.usePowerUpAsAmmo(powerUp);
    }

    public void useTeleporter(PowerUp teleporter, Square destination){
        powerUpController.useTeleporter(teleporter, destination);
    }

    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws NotAllowedMoveException{
        powerUpController.useNewton(newton, affectedPlayer, destination);
    }

    public void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer){
        powerUpController.useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
    }

    public void useTargetingScope(PowerUp targetingScope, Player affectedPlayer){
        powerUpController.useTargetingScope(targetingScope, affectedPlayer);
    }

    public MoveController getMoveController() {
        return moveController;
    }

    public ShootController getShootController() {
        return shootController;
    }
}