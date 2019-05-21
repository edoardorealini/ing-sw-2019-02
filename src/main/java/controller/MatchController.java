package controller;

import exception.*;
import model.Match;
import model.map.*;
import model.map.MapBuilder;
import model.player.Player;
import model.powerup.PowerUp;
import model.weapons.Weapon;

public class MatchController {

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
        //TODO add check ok mapID (value)

        match.getMap().fillWeaponBox(match.getWeaponDeck());
        match.getMap().fillAmmo(match.getAmmoDeck());

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
    public void usePowerUpAsAmmo(PowerUp powerUp) throws NotInYourPossessException{
        if (match.getCurrentPlayer().hasPowerUp(powerUp)) {
            powerUpController.usePowerUpAsAmmo(powerUp);
        }
        else
            throw new NotInYourPossessException("The powerUp" + powerUp.getName() + "is not in your hand");
    }

    public void useTeleporter(PowerUp teleporter, Square destination) throws NotInYourPossessException {
        if(match.getCurrentPlayer().hasPowerUp(teleporter)) {
            powerUpController.useTeleporter(teleporter, destination);
        }
        else
            throw new NotInYourPossessException("The powerUp" + teleporter.getName() + "is not in your hand");
    }

    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws NotAllowedMoveException, NotInYourPossessException{
        if(match.getCurrentPlayer().hasPowerUp(newton)) {
            powerUpController.useNewton(newton, affectedPlayer, destination);
        }
        else
            throw new NotInYourPossessException("The powerUp" + newton.getName() + "is not in your hand");
    }

    public void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) throws NotInYourPossessException, NotAllowedTargetException {
        if(user.hasPowerUp(tagbackGrenade)) {
            powerUpController.useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
        }
        else
            throw new NotInYourPossessException("The powerUp" + tagbackGrenade.getName() + "is not in your hand");

    }

    public void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) throws NotInYourPossessException{
        if(match.getCurrentPlayer().hasPowerUp(targetingScope)) {
            powerUpController.useTargetingScope(targetingScope, affectedPlayer);
        }
        else
            throw new NotInYourPossessException("The powerUp" + targetingScope.getName() + "is not in your hand");

    }

    public MoveController getMoveController() {
        return moveController;
    }

    public ShootController getShootController() {
        return shootController;
    }

    public GrabController getGrabController() { return grabController; }

    public PowerUpController getPowerUpController() { return powerUpController; }

    // metodo creazione di player
    public void addPlayer(String nickName, int ID){
        match.getPlayers().add(new Player(nickName, ID, getMatch()));
    }

    // aggiunto da edo, genera il player solo con il nickname e mette da solo id corretto sequenzialmente
    public void addPlayer(String nickName){
        match.getPlayers().add(new Player(nickName, match.getPlayers().size(), getMatch()));
        //setta current player se sono il primo a connettermi
        if(match.getPlayers().size() == 1)
            match.setCurrentPlayer(match.getPlayers().get(0));
    }


    public String RMICallTest(String message){
        System.out.println("Called test method with message: " + message);
        return "Called MatchController.RMICallTest(message) method with message: " + message;
    }

}