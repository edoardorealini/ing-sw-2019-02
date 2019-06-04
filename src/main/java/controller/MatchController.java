package controller;

import controller.observer.Observer;
import exception.*;
import model.Color;
import model.Match;
import model.map.*;
import model.map.MapBuilder;
import model.player.Board;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;
import model.weapons.*;

import javax.security.auth.login.FailedLoginException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MatchController{

    private Match match;
    private GrabController grabController;
    private PowerUpController powerUpController;
    private ShootController shootController;
    private MoveController moveController;
    // private HashMap<WeaponName, String> weaponHashMap;
    private List<Observer> observers;  //TODO observers

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
        this.shootController = new ShootController(this.match, this.moveController);
        observers = new ArrayList<>(); //TODO riepmpire la struttura dati passando al costruttore nel momento della creazione dell'oggetto
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

    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for(Observer o: observers){
            o.update();
        }
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
    private   synchronized void usePowerUpAsAmmo(PowerUp powerUp) throws NotInYourPossessException {
            if (match.getCurrentPlayer().hasPowerUp(powerUp)) {
                powerUpController.usePowerUpAsAmmo(powerUp);
            } else
                throw new NotInYourPossessException("The powerUp" + powerUp.getName() + "is not in your hand");
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
    @Deprecated
    public  synchronized void addPlayer(String nickName, int ID) {
        match.getPlayers().add(new Player(nickName, ID, getMatch()));
    }

    // aggiunto da edo, genera il player solo con il nickname e mette da solo id corretto sequenzialmente basandosi sulla dimensione dell'array di player in gioco
    //usare questo!!
    public synchronized void addPlayer(String nickName) throws  FailedLoginException{

       if(checkPlayerPresence(nickName)) {
           if(match.getPlayer(nickName).isConnected())
               throw new FailedLoginException("[ERROR]: Player already connected, try with another nickname");
           if(!match.getPlayer(nickName).isConnected()) {
               match.getPlayer(nickName).getStatus().setTurnStatusWaitTurn();
               System.out.println("[INFO]: The player " + nickName + " is already registered, relogging ... ");
               return;
           }
       }

        match.getPlayers().add(new Player(nickName, match.getPlayers().size(), getMatch()));
        //setta current player se sono il primo a connettermi
        if (match.getPlayers().size() == 1) {
            match.setCurrentPlayer(match.getPlayers().get(0));
            match.getPlayer(nickName).getStatus().setTurnStatusMaster();
        }
        else
            match.getPlayer(nickName).getStatus().setTurnStatusWaitTurn();

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
            if(p.getNickname().equals(playerNickname)){
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
    /*
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

     */

    //metodo da chiamare con trucchetto quando un player si disconnette (vedi appunti per capire trucchetto)
    //TODO implementare la gestione dello stato DISCONNECTED quando un utente si disconnette, LATO CLIENT chiamare il metodo per la disconnessione
    public void disconnectPlayer(String nickname){
        if(checkPlayerPresence(nickname)){
            match.getPlayer(nickname).getStatus().setTurnStatusDisconnected();
        }
    }

    public synchronized void shoot(ShootingParametersInput input) throws WrongStatusException, NotAllowedTargetException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedShootingModeException {
        if(canDoAction()) {

            shootController.setInput(input);

            if (input.getWeapon().getWeaponStatus() != WeaponAmmoStatus.LOADED)
                throw new NotEnoughAmmoException("poveroo");  //TODO

            if (!(input.getShootModes().contains(ShootMode.BASIC) || input.getShootModes().contains(ShootMode.ALTERNATE)))
                throw new NotAllowedShootingModeException();

            if (input.getShootModes().contains(ShootMode.BASIC) && input.getShootModes().contains(ShootMode.ALTERNATE))
                throw new NotAllowedShootingModeException();

            try {       //switch that choose the right method for the right weapon

                switch (input.getWeapon().getName()) {
                    case ZX_2: shootController.shootZX2(); break;
                    case THOR: shootController.shootTHOR(); break;
                    case FURNACE: shootController.shootFurnace(); break;
                    case HELLION: shootController.shootHellion(); break;
                    case RAILGUN: shootController.shootRailGun(); break;
                    case SHOTGUN: shootController.shootShotgun(); break;
                    case WHISPER: shootController.shootWhisper(); break;
                    case PLASMA_GUN: shootController.shootPlasmaGun(); break;
                    case LOCK_RIFLE: shootController.shootLockRifle(); break;
                    case CYBERBLADE: shootController.shootCyberblade(); break;
                    case HEATSEEKER: shootController.shootHeatseeker(); break;
                    case SCHOCKWAVE: shootController.shootSchockWave(); break;
                    case POWER_GLOVE: shootController.shootPowerGlove(); break;
                    case MACHINE_GUN: shootController.shootMachineGun(); break;
                    case TRACTOR_BEAM: shootController.shootTractorBeam(); break;
                    case FLAMETHROWER: shootController.shootFlameThrower(); break;
                    case SLEDGEHAMMER: shootController.shootSledgehammer(); break;
                    case VORTEX_CANNON: shootController.shootCannonVortex(); break;
                    case ELECTROSCYTHE: shootController.shootElectroScythe(); break;
                    case ROCKET_LAUNCHER: shootController.shootRocketLauncher(); break;
                    case GRENADE_LAUNCHER: shootController.shootGrenadeLauncher(); break;
                }
            }  catch (NotAllowedMoveException e) {
                throw new  NotAllowedMoveException();
            } catch (NotEnoughAmmoException e) {
                throw new NotEnoughAmmoException("sei povero"); //TODO
            } catch (NotAllowedTargetException e) {
                throw new NotAllowedTargetException();
            } catch (NotAllowedShootingModeException e) {
                throw new NotAllowedShootingModeException();
            }

            input.getWeapon().setWeaponStatus(WeaponAmmoStatus.UNLOADED);


            match.getCurrentPlayer().goToNextStatus(); //don't touch
        }
        else
            throw new WrongStatusException("You are not allowed to shoot now!");
    }

    public synchronized void reloadWeapon(Weapon weapon) throws NotEnoughAmmoException {

        if (weapon.getWeaponStatus() == WeaponAmmoStatus.LOADED)
            return;

        Color firstColor = weapon.getCost().get(0);

        int r = 0;
        int b = 0;
        int y = 0;
        int actualRedAmmo;    //ammo already owned by the current player
        int actualBlueAmmo;
        int actualYellowAmmo;


        for (Color color : weapon.getCost()) {
            switch (color) {
                case RED:
                    r++;
                    break;
                case BLUE:
                    b++;
                    break;
                case YELLOW:
                    y++;
                    break;
                default:
                    break;
            }
        }

        if (weapon.getWeaponStatus() == WeaponAmmoStatus.PARTIALLYLOADED)
            switch (firstColor) {
                case RED:
                    r--;
                    break;
                case BLUE:
                    b--;
                    break;
                case YELLOW:
                    y--;
                    break;
                default:
                    break;
            }
        do {
            actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
            actualBlueAmmo = match.getCurrentPlayer().getAmmo().getBlueAmmo();
            actualYellowAmmo = match.getCurrentPlayer().getAmmo().getYellowAmmo();

            if (actualRedAmmo - r < 0 || actualBlueAmmo - b < 0 || actualYellowAmmo - y < 0) {
                if (checkForPowerUpsAsAmmo(r - actualRedAmmo, b - actualBlueAmmo, y - actualYellowAmmo)) {
                    /*
                    System.out.println("Do you want to use a powerUp? \n");
                    String in = new Scanner(System.in).nextLine();
                    if (in.equals("yes")) {
                        System.out.println(match.getCurrentPlayer().getAmmo().toString());
                        System.out.println(match.getCurrentPlayer().printPowerUps());
                        System.out.println("Choose the powerUp you want to use as Ammo:");
                        int n = new Scanner(System.in).nextInt();
                        PowerUp pow = match.getCurrentPlayer().getPowerUps()[n];
                        try {
                            usePowerUpAsAmmo(pow);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    */

                    //PowerUp power = TODO insert ask if the user wants to use a power up as an ammo
                    //usePowerUpAsAmmo(power);
                } else {
                    throw new NotEnoughAmmoException("It seems you don't have enough ammo");
                }
            } else {
                match.getCurrentPlayer().removeAmmo(r, b, y);
                weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
            }
        } while (weapon.getWeaponStatus() != WeaponAmmoStatus.LOADED);

    }

    private boolean checkForPowerUpsAsAmmo(int redNeeded, int blueNeeded, int yellowNeeded) {
        //this method return true if the current player can pay with power ups, so that maybe we can ask him if he wants to or not

        int r = 0;
        int b = 0;
        int y = 0;

        for (PowerUp pow : match.getCurrentPlayer().getPowerUps()) {
            if (pow != null) {
                switch (pow.getColor()) {
                    case RED:
                        r++;
                        break;
                    case BLUE:
                        b++;
                        break;
                    case YELLOW:
                        y++;
                        break;
                    default:
                        break;
                }
            }
        }

        return(r >= redNeeded && b >= blueNeeded && y >= yellowNeeded);
    }

    public void endOfTurn() {
        for (Player p : match.getPlayers()) {
            if (p.isDead()) {
                Board board = p.getBoard();
                addPoints(board);
                //update number of deaths
            }
        }
    }

    public void addPoints(Board board) {
        ArrayList<Integer> numberOfDamages = new ArrayList<>();
        for (Player p : match.getPlayers()) {
            int hits = board.howManyHits(p.getId());
            numberOfDamages.add(hits);
        }
        numberOfDamages.sort(Comparator.naturalOrder());
        Collections.reverse(numberOfDamages);

        //TODO mappa 1 a 1 chi ha fatto i danni e assegna i punti
    }


//here there is the code of the hashmap, it doesn't work well
/*
    private void setWeaponMap() {
        //this method sets the HashMap that is used to map the weapon selected by the client with its method of ShootController

        this.weaponHashMap.put(WeaponName.LOCK_RIFLE, "shootLockRifle");

    }

    public synchronized void shoot(ShootingParametersInput input) throws WrongStatusException, NotAllowedTargetException {
        if(canDoAction()){

            try {
                executeShoot(input.getWeapon().getName());
            } catch (NotAllowedTargetException e) {
                throw new NotAllowedTargetException();
            }

            match.getCurrentPlayer().goToNextStatus(); //non toccare
        }
        else
            throw new WrongStatusException("You are not allowed to shoot now!");
    }

    private void executeShoot(WeaponName name) throws NotAllowedMoveException, NotEnoughAmmoException, NotAllowedTargetException {
        try {
            Method method = shootController.getClass().getDeclaredMethod(this.weaponHashMap.get(name));
            method.invoke(this);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }  catch (NotAllowedTargetException e) {
            throw new NotAllowedTargetException();
        } catch (NotAllowedMoveException e) {
            throw new NotAllowedMoveException();
        } catch (NotEnoughAmmoException e) {
            throw new NotEnoughAmmoException("Not enough ammo");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
*/

}