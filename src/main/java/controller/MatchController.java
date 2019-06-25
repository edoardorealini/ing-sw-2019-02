package controller;

import exception.*;
import model.Color;
import model.Match;
import model.ShootMode;
import model.ShootingParametersInput;
import model.map.*;
import model.map.Map;
import model.map.MapBuilder;
import model.player.*;
import model.powerup.PowerUp;
import model.weapons.*;
import server.RMIHandler.ServerControllerRMI;

import javax.security.auth.login.FailedLoginException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;


public class MatchController{

    private Match match;
    private GrabController grabController;
    private PowerUpController powerUpController;
    private ShootController shootController;
    private MoveController moveController;
    // private HashMap<WeaponName, String> weaponHashMap;
    private ServerControllerRMI serverControllerRMI;
    private String propertyPath = "." + File.separatorChar + "src" + File.separatorChar + "main"
            + File.separatorChar + "resources" + File.separatorChar + "adrenaline.properties";
    private int turnDuration;
    private boolean turnTimerStatus = false;
    private Timer waitForPayment;
    private Timer waitForWeaponLoaded;
    private TimerTask waitForWeaponLoadedTask;
    private TimerTask waitForPaymentTask;

    /*
        Costruttore 1
    */
    public MatchController() {
        this.match = new Match();

        this.moveController = new MoveController(this.match);       //oggetto comune a tutti i controller!
        this.grabController = new GrabController(this.match, this.moveController);
        this.powerUpController = new PowerUpController(this.match, this.moveController);
        this.shootController = new ShootController(this.match, this.moveController);
        getValuesFromProperties();
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
        getValuesFromProperties();
    }

    private void getValuesFromProperties(){

        Properties propertyLoader = new Properties();
        try{
            propertyLoader.load(new FileInputStream(propertyPath));
            this.turnDuration = Integer.parseInt(propertyLoader.getProperty("turnDuration"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("[ERROR]: Failed loading info from properties file, setting the turnDuration to 2 minutes as default value");
            turnDuration = 120000; //default value for turn duration set to 2 minutes
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR]: Failed loading info from properties file, setting the turnDuration to 2 minutes as default value");
            turnDuration = 120000; //default value for turn duration set to 2 minutes
        }
    }

    public void setServerControllerRMI(ServerControllerRMI controller){
        this.serverControllerRMI = controller;
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
                    System.out.println("[INFO]: Map "+ mapID+ " built correctly.");
                } catch (Exception e) {
                    e.printStackTrace(); //non serve per ora gestire con logger
                }

                match.getMap().fillWeaponBox(match.getWeaponDeck());
                System.out.println("[INFO]: Map filled with weapons");

                match.getMap().fillAmmo(match.getAmmoDeck());
                System.out.println("[INFO]: Map filled with ammo");

            } else
                throw new WrongValueException("Not a valid mapID");
            // OLD:  match.getCurrentPlayer().goToNextStatus(); //from master to Spawn (first player and first turn)
            goToNextStatus(match.getCurrentPlayer());
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
    public  synchronized void move(Player player, Square destination, int maxDistanceAllowed) throws NotAllowedMoveException, WrongStatusException{
        if(canDoAction()) {
            try {
                moveController.move(player, destination, maxDistanceAllowed);
                goToNextStatus(player);
                printPlayerStatuses();
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
                throw new NotAllowedMoveException(e.getMessage());
            }
        }
        else
            throw new WrongStatusException("You are not allowed to execute a move action now, you must wait for your turn");
    }


    public synchronized int getMaxDistanceAllowed(Player player) {
        AbilityStatus abilityStatus = player.getStatus().getSpecialAbility();
        if(abilityStatus.equals(AbilityStatus.NORMAL) || abilityStatus.equals(AbilityStatus.ADRENALINE_PICK) || abilityStatus.equals(AbilityStatus.ADRENALINE_SHOOT))
            return 3;
        //TODO controllare qui AbiltyStatus di due tipi in FRENZY.
        if(abilityStatus.equals(AbilityStatus.FRENZY))
            return 4;
        return 3;
    }

    //the first spawn always occurs when a pleyer is the current player.
    public synchronized void spawn(PowerUp powerUpChosen, Player user) throws NotInYourPossessException, WrongStatusException {
        if(!user.hasPowerUp(powerUpChosen))
            throw new NotInYourPossessException("You don't have such powerup, please retry");

        if(!(user.isInStatusSpawn() || user.isInStatusRespawn())) {
            System.out.println("[ERROR]: The client " + user.getNickname() + " is trying to spawn in status: " + user.getStatus().getTurnStatus());
            throw new WrongStatusException("You must be in status respawn or spawn to choose where to spawn");
        }

        Square spawnPoint = match.getMap().getSpawnSquareFromColor(powerUpChosen.getColor());
        user.setPosition(spawnPoint);
        user.removePowerUps(powerUpChosen);

        System.out.println("[SPAWN]: Client " + user.getNickname() + " spawned correctly in square coordinates: X = " + getMap().getIndex(spawnPoint).get(0) + " - Y = " + getMap().getIndex(spawnPoint).get(1));
        goToNextStatus(user);
        printPlayerStatuses();

    }

    public void addPowerUpToSpawn(Player player){
        player.addPowerUpsHaphazard();
    }

    private void printPlayerStatuses(){
        for(Player p: match.getPlayers()){
            System.out.println("[STATUS]: The player "+ p.getNickname() + " is in status: " +p.getStatus().getTurnStatus());
        }
    }

    public synchronized boolean isAllowedMove(Square startingPoint, Square destination, int maxDistance) {
        return moveController.isAllowedMove(startingPoint, destination, maxDistance);
    }

    @Deprecated
    public synchronized void moveOneSquare(Directions direction) throws Exception {
        moveController.moveOneSquare(direction);
    }

    @Deprecated
    public synchronized void moveOneSquare(Directions direction, Player player) throws Exception {
        moveController.moveOneSquare(direction, player);
    }

    public synchronized int minDistBetweenSquares(Square startingPoint, Square destination) {
        return moveController.minDistBetweenSquares(startingPoint, destination);
    }

    //metodi da grab controller
    public synchronized  void grabAmmoCard() throws WrongStatusException, WrongPositionException {
        if(canDoAction()) {
            try {
                grabController.grabAmmoCard();
            } catch(WrongPositionException e){
                throw new WrongPositionException(e.getMessage());
            }

            goToNextStatus(match.getCurrentPlayer());
        }
        else
            throw new WrongStatusException("You cannot grab any ammo now!");
    }

    public synchronized void grabWeapon(Weapon weapon, int indexOfWeaponToSwap) throws WrongPositionException, NotEnoughAmmoException, WrongStatusException, NotAllowedCallException, RemoteException {
        if(canDoAction()) {
            try {
                if (indexOfWeaponToSwap >= 0 && indexOfWeaponToSwap < 3)
                    grabController.grabWeapon(weapon, indexOfWeaponToSwap);
                else
                    grabController.grabWeapon(weapon, -1);

                goToNextStatus(match.getCurrentPlayer());
                serverControllerRMI.pushMatchToAllPlayers();
            } catch (NotEnoughAmmoException e) {

                int redCost = getWeaponCost(weapon.getCost())[0];           //all the ammo that need to be paid
                int blueCost = getWeaponCost(weapon.getCost())[1];
                int yellowCost = getWeaponCost(weapon.getCost())[2];

                switch (weapon.getCost().get(0)) {
                    case YELLOW:
                        yellowCost--;
                        break;
                    case BLUE:
                        blueCost--;
                        break;
                    case RED:
                        redCost--;
                        break;
                    default:
                        break;
                }

                int actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
                int actualBlueAmmo =  match.getCurrentPlayer().getAmmo().getBlueAmmo();
                int actualYellowAmmo =  match.getCurrentPlayer().getAmmo().getYellowAmmo();

                if (checkForPowerUpsAsAmmo(redCost - actualRedAmmo, blueCost - actualBlueAmmo, yellowCost- actualYellowAmmo)) {
                    waitForPayment = new Timer();
                    waitForPaymentTask = new TimerTask() {
                        @Override
                        public void run() {

                            int redCost = getWeaponCost(weapon.getCost())[0];           //all the ammo that need to be paid
                            int blueCost = getWeaponCost(weapon.getCost())[1];
                            int yellowCost = getWeaponCost(weapon.getCost())[2];

                            switch (weapon.getCost().get(0)) {
                                case YELLOW:
                                    yellowCost--;
                                    break;
                                case BLUE:
                                    blueCost--;
                                    break;
                                case RED:
                                    redCost--;
                                    break;
                                default:
                                    break;
                            }

                            int actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
                            int actualBlueAmmo =  match.getCurrentPlayer().getAmmo().getBlueAmmo();
                            int actualYellowAmmo =  match.getCurrentPlayer().getAmmo().getYellowAmmo();

                            if(actualRedAmmo - redCost < 0 || actualBlueAmmo - blueCost < 0 || actualYellowAmmo - yellowCost < 0) {
                                System.out.println("[GRAB]: " + match.getCurrentPlayer().getNickname() + " is trying to pay with a power up.");
                                try {
                                    serverControllerRMI.askForPowerUpAsAmmo();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    grabWeapon(weapon, indexOfWeaponToSwap);
                                } catch (WrongStatusException | NotAllowedCallException | WrongPositionException | NotEnoughAmmoException | RemoteException ex) {
                                    System.out.println("[INFO]: Error in timer");
                                    ex.printStackTrace();
                                }
                                waitForPaymentTask.cancel();
                                waitForPayment.cancel();
                                waitForPayment.purge();
                            }
                        }
                    };
                    waitForPayment.schedule(waitForPaymentTask, 1, 2000);
                } else {
                    throw new NotEnoughAmmoException(e.getMessage());
                }
            }
        }
        else
            throw new WrongStatusException("You cannot grab any weapons now!");
    }

    public synchronized void grabMove(Square destination) throws NotAllowedMoveException{
        try {
            if(match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.NORMAL))
                moveController.move(match.getCurrentPlayer(), destination, 1);

            if(match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.ADRENALINE_PICK) || match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.ADRENALINE_SHOOT))
                moveController.move(match.getCurrentPlayer(), destination, 2);
        } catch (NotAllowedMoveException e) {
            e.printStackTrace();
            throw new NotAllowedMoveException(e.getMessage());
        }
    }

    //metodi di powerUpController
    public synchronized void usePowerUpAsAmmo(PowerUp powerUp) throws NotInYourPossessException {
            if (match.getCurrentPlayer().hasPowerUp(powerUp)) {
                powerUpController.usePowerUpAsAmmo(powerUp);
            } else
                throw new NotInYourPossessException("The powerUp" + powerUp.getName() + "is not in your hand");
    }

    public synchronized void useTeleporter(PowerUp teleporter, Square destination) throws NotInYourPossessException, WrongStatusException, NotAllowedMoveException, WrongPowerUpException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(teleporter)){
                powerUpController.useTeleporter(teleporter, destination);
            } else
                throw new NotInYourPossessException("The powerUp" + teleporter.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You cannot use a PowerUp now, wait for your turn!");
    }

    public synchronized void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws WrongStatusException, NotAllowedMoveException, NotInYourPossessException, WrongPowerUpException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(newton)) {
                powerUpController.useNewton(newton, affectedPlayer, destination);
            } else
                throw new NotInYourPossessException("The powerUp" + newton.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You cannot use a PowerUp now!");
    }

    public synchronized void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) throws NotInYourPossessException, WrongStatusException {
        if(canUseTagbackGrenade(user)) {
            if (user.hasPowerUp(tagbackGrenade)) {
                powerUpController.useTagbackGrenade(tagbackGrenade, user, affectedPlayer);
            } else
                throw new NotInYourPossessException("The powerUp" + tagbackGrenade.getName() + "is not in your hand");
        }
        else
            throw new WrongStatusException("You are not allowed to use a TagBack Grenade now!");

    }

    public synchronized void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, Color ammoToPay) throws NotInYourPossessException, WrongStatusException, NotEnoughAmmoException {
        if(canUsePowerUp()) {
            if (match.getCurrentPlayer().hasPowerUp(targetingScope)) {
                powerUpController.useTargetingScope(targetingScope, ammoToPay, affectedPlayer);
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
           if(match.getPlayer(nickName.trim()).isConnected()) //with .trim() a usernames with the same name of another but with spaces is not allowed!
               throw new FailedLoginException("[ERROR]: Player already connected, try with another nickname");

           if(!match.getPlayer(nickName).isConnected()) {

               if(!match.getActiveStatusMatch()) {
                   match.getPlayer(nickName).getStatus().setTurnStatusLobby();

                   if (!checkThereIsLobbyMaster())
                       match.getPlayer(nickName).getStatus().setTurnStatusLobbyMaster();
               }

               else{
                   match.getPlayer(nickName).getStatus().setTurnStatusWaitTurn();
               }

               System.out.println("[INFO]: The player " + nickName + " is already registered, relogging ... ");
               return;
           }
        }
        else {
            if (match.getPlayers().size() > 4)
                throw new FailedLoginException("[ERROR]: The lobby is full, try again later . . .");


            match.getPlayers().add(new Player(nickName, match.getPlayers().size(), getMatch()));
            //qui devo aggiornare il numero di giocatori connessi e nel caso far partire i cronometri
            //setta current player se sono il primo a connettermi

            //if (match.getPlayers().size() == 1)
            //   match.setCurrentPlayer(match.getPlayers().get(0));

            if (!match.getActiveStatusMatch())
                match.getPlayer(nickName).getStatus().setTurnStatusLobby();

            if (!checkThereIsLobbyMaster())
                match.getPlayer(nickName).getStatus().setTurnStatusLobbyMaster();
        }

    }

    private boolean checkThereIsMaster(){
        for(Player p: match.getPlayers()){
            if(p.isInStatusMaster())
                return true;
        }
        return false;
    }

    private boolean checkThereIsLobbyMaster(){
        for(Player p: match.getPlayers()){
            if(p.isInStatusLobbyMaster())
                return true;
        }
        return false;
    }

    //returns the number of connected players
    public synchronized  int connectedPlayers(){
        int tmp = 0;

        for(Player p: match.getPlayers())
            if(p.isConnected())
                tmp ++;

        return tmp;
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

    public boolean checkPlayerPresence(String playerNickname){
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

    //TODO controllare bene qui
    private boolean canDoAction(){
        if(match.getCurrentPlayer().isInStatusFirstAction() || match.getCurrentPlayer().isInStatusSecondAction()){
            return true;
        }

        return false;
    }

    private boolean canUsePowerUp(){
        if(match.getCurrentPlayer().isInStatusFirstAction() || match.getCurrentPlayer().isInStatusSecondAction() || match.getCurrentPlayer().isInStatusReloading())
            return true;

        return false;
    }

    private boolean checkIfCanSkipAction(Player p){
        if(p.isInStatusFirstAction() || p.isInStatusSecondAction() || p.isInStatusReloading())
            return true;
        return false;
    }

    private boolean checkIfCanSkipActionFrenzy(Player p){
        if(p.getStatus().getTurnStatus().equals(RoundStatus.FIRST_ACTION_FRENZY) || p.getStatus().getTurnStatus().equals(RoundStatus.SECOND_ACTION_FRENZY) || p.getStatus().getTurnStatus().equals(RoundStatus.FIRST_ACTION_LOWER_FRENZY))
            return true;
        return false;
    }

    private boolean canChooseMap(){
        if(match.getCurrentPlayer().isInStatusMaster())
            return true;

        return false;
    }

    private boolean canUseTagbackGrenade(Player p){
        return p.isInStatusWaitTurn();
    }

    private Timer turnTimer = new Timer();
    private TimerTask turnTimerTask = new TimerTask(){

    @Override
    public synchronized void run() {
        turnTimerStatus = false;
        Player player = match.getCurrentPlayer();
        //if i enter this timer it means that the player who launched it hasn't finished his turn
        System.out.println("[TURNTIMER]: The player " + player.getNickname() + " has expired his time to complete the turn, skipping his turn . . .");
        player.getStatus().setTurnStatusWaitTurn();
        setNewCurrentPlayer();

        try {
            serverControllerRMI.askRespawn();
            //questo serve solo per il primo turno, ovvero per gestire la prima spawn, in teoria poi non crea problemi. (TO TEST)
        } catch (Exception e) {
            e.printStackTrace();
        }


        }
    };

    public void goToNextStatus(Player p){

        switch(p.getStatus().getTurnStatus()){
            case LOBBY_MASTER:
                p.getStatus().setTurnStatusMaster();
                break;

            case LOBBY:
                p.getStatus().setTurnStatusWaitFirstTurn();
                break;

            case WAIT_FIRST_TURN:
                p.getStatus().setTurnStatusSpawn();
                break;

            case MASTER:
                p.getStatus().setTurnStatusSpawn();
                break;

            case SPAWN:
                p.getStatus().setTurnStatusFirstAction();
                turnTimerTask.cancel(); //cancel the timer if i arrive here, else automatically the player is sent to the next status.
                turnTimer.cancel();
                turnTimer.purge();
                turnTimer = new Timer();
                if(!turnTimerStatus) {
                    turnTimer = new Timer();
                    turnTimerTask = new TimerTask(){
                        @Override
                        public synchronized void run() {
                            turnTimerStatus = false;
                            Player player = match.getCurrentPlayer();
                            //if i enter this timer it means that the player who launched it hasn't finished his turn
                            System.out.println("[TURNTIMER]: The player " + player.getNickname() + " has expired his time to complete the turn, skipping his turn . . .");
                            /*
                            player.getStatus().setTurnStatusWaitTurn();
                            setNewCurrentPlayer();
                            try {
                                serverControllerRMI.askRespawn();
                                //questo serve solo per il primo turno, ovvero per gestire la prima spawn, in teoria poi non crea problemi. (TO TEST)
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            */
                            player.getStatus().setTurnStatusEndTurn();
                            goToNextStatus(player);

                        }
                    };
                    turnTimer.schedule(turnTimerTask, turnDuration);
                    turnTimerStatus = true;
                    System.out.println("[TURNTIMER]: Setted turn timer for " + turnDuration/1000 + " seconds.");
                }
                break;

            case FIRST_ACTION:
                p.getStatus().setTurnStatusSecondAction();
                break;

            case SECOND_ACTION:
                for(Weapon w: p.getWeapons())
                    if(w != null) {
                        if (!w.getWeaponStatus().equals(WeaponAmmoStatus.LOADED)) {
                            p.getStatus().setTurnStatusReloading();
                            return;
                        }
                    }
                p.getStatus().setTurnStatusEndTurn();
                turnTimerTask.cancel(); //cancel the timer if i arrive here, else automatically the player is sent to the next status.
                turnTimer.cancel();
                turnTimer.purge();
                turnTimerStatus = false;
                System.out.println("[TURNTIMER]: KILLED");
                goToNextStatus(p);
                break;

            case RELOADING:
                p.getStatus().setTurnStatusEndTurn();
                turnTimerTask.cancel(); //cancel the timer if i arrive here, else automatically the player is sent to the next status.
                turnTimer.cancel();
                turnTimer.purge();
                turnTimerStatus = false;
                System.out.println("[TURNTIMER]: KILLED");
                goToNextStatus(p);
                break;

            case END_TURN:
                turnTimerTask.cancel(); //cancel the timer if i arrive here, else automatically the player is sent to the next status.
                turnTimer.cancel();
                turnTimer.purge();

                endOfTurn(); // manages the points to the players
                //qui aggiungo qualcosa che manda avanti lo stato del primo giocatore dell'elenco che è in wait first turn (dandogli il diritto di essere chiamato per spawnare)
                //putWaitFirstTurnInSpawn();
                setNewCurrentPlayer();
                //in set current player in teoria attendo che quelli in respawn abbiano effettuato il loro respawn.
                try {
                    serverControllerRMI.askRespawn();
                    //questo serve solo per il primo turno, ovvero per gestire la prima spawn, in teoria poi non crea problemi. (TO TEST)
                }catch(Exception e){
                    e.printStackTrace();
                }

                p.getStatus().setTurnStatusWaitTurn();
                //ti ricordo che qesto metodo viene chiamato ogni volta che viene eseguita un'azione o in generale quando si vuole cambiare lo stato di un giocatore (seguendo l'ordine della macchina a stati)
                break;

            case RESPAWN:
                p.getStatus().setTurnStatusWaitTurn();
                break;

            case WAIT_TURN:
                if(!p.isDead()) {
                    p.getStatus().setTurnStatusFirstAction();
                    turnTimer.cancel(); //cancel the timer if i arrive here, else automatically the player is sent to the next status.
                    turnTimer.purge();
                    turnTimer = new Timer();
                    if(!turnTimerStatus) {
                        turnTimer =  new Timer();
                        turnTimerTask = new TimerTask(){
                            @Override
                            public synchronized void run() {
                                turnTimerStatus = false;
                                Player player = match.getCurrentPlayer();
                                //if i enter this timer it means that the player who launched it hasn't finished his turn
                                System.out.println("[TURNTIMER]: The player " + player.getNickname() + " has expired his time to complete the turn, skipping his turn . . .");
                                /*
                                player.getStatus().setTurnStatusWaitTurn();
                                setNewCurrentPlayer();
                                try {
                                    serverControllerRMI.askRespawn();
                                    //questo serve solo per il primo turno, ovvero per gestire la prima spawn, in teoria poi non crea problemi. (TO TEST)
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                */

                                player.getStatus().setTurnStatusEndTurn();
                                goToNextStatus(player);
                            }
                        };
                        turnTimer.schedule(turnTimerTask, turnDuration);
                        turnTimerStatus = true;
                        System.out.println("[TURNTIMER]: Setted turn timer for " + turnDuration/1000 + " seconds.");
                    }
                }

                else
                    p.getStatus().setTurnStatusSpawn();
                break;

            case DISCONNECTED:
                setNewCurrentPlayer();
                break;
        }
    }
    //nel dubbio metti synchronized un po' ovunque e dovrebbe andare a posto  ;)
    public synchronized void setNewCurrentPlayer(){
        //TODO controllo su giocatori disconnessi
        int idCurrentPlayer = match.getCurrentPlayer().getId();

        if(!everybodyRespawned()) {
            if (idCurrentPlayer == match.getPlayers().size() - 1) {
                //in teoria i timer eseguono un controllo ogni secondo per vedere se i giocatori in stato di respawn hanno effettuato il respawn.
                Timer waitForRespawn = new Timer();
                waitForRespawn.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                Match model = getMatch();
                                if (everybodyRespawned()) {
                                    System.out.println("[GAME] : Setting new current player, " + model.getPlayers().get(0).getNickname() + " is the current player now.");
                                    model.setCurrentPlayer(model.getPlayers().get(0));
                                    if(match.getKillShotTrack().getSkulls() <= 0){
                                        System.out.println("Current player before frenzy: "+match.getCurrentPlayer().getNickname()+" is in status "+match.getCurrentPlayer().getStatus().getTurnStatus());
                                        startFrenzyMode();
                                        System.out.println("Current player after frenzy: "+match.getCurrentPlayer().getNickname()+" is in status "+match.getCurrentPlayer().getStatus().getTurnStatus());
                                        try {
                                            serverControllerRMI.pushMatchToAllPlayers();
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        } finally {
                                            waitForRespawn.cancel();
                                            waitForRespawn.purge();
                                        }
                                    }
                                    else {
                                        goToNextStatus(model.getPlayers().get(0));
                                        try {
                                            serverControllerRMI.pushMatchToAllPlayers();
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        } finally {
                                            waitForRespawn.cancel();
                                            waitForRespawn.purge();
                                        }
                                    }
                                }
                            }
                        }, 1, 3000
                );
            } else {
                Timer waitForRespawn = new Timer();
                waitForRespawn.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                Match model = getMatch();
                                if (everybodyRespawned()) {
                                    System.out.println("[GAME] : Setting new current player, " + model.getPlayers().get(idCurrentPlayer + 1).getNickname() + " is the current player now.");
                                    model.setCurrentPlayer(model.getPlayers().get(idCurrentPlayer + 1));
                                    if(match.getKillShotTrack().getSkulls() <= 0){
                                        startFrenzyMode();
                                        try {
                                            serverControllerRMI.pushMatchToAllPlayers();
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        } finally {
                                            waitForRespawn.cancel();
                                            waitForRespawn.purge();
                                        }
                                    }
                                    else {
                                        goToNextStatus(model.getPlayers().get(idCurrentPlayer + 1));
                                        try {
                                            serverControllerRMI.pushMatchToAllPlayers();
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        } finally {
                                            waitForRespawn.cancel();
                                            waitForRespawn.purge();
                                        }
                                    }
                                }
                            }
                        }, 1, 3000
                );
            }
        }
        else{
            if (idCurrentPlayer == match.getPlayers().size() - 1){

                Match model = getMatch();
                System.out.println("[GAME] : Setting new current player, " + model.getPlayers().get(0).getNickname() + " is the current player now.");
                model.setCurrentPlayer(model.getPlayers().get(0));
                goToNextStatus(model.getPlayers().get(0));
                try {
                    serverControllerRMI.pushMatchToAllPlayers();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
            else{

                Match model = getMatch();
                System.out.println("[GAME] : Setting new current player, " + model.getPlayers().get(idCurrentPlayer + 1).getNickname() + " is the current player now.");
                model.setCurrentPlayer(model.getPlayers().get(idCurrentPlayer + 1));
                goToNextStatus(model.getPlayers().get(idCurrentPlayer + 1));
                try {
                    serverControllerRMI.pushMatchToAllPlayers();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


            }
        }


    }
    //setta i nuovi permessi per la frenzy
    //setta il current player in first action (in base al tipo )
    //setta tutti gli altri in wait turn frenzy
    private void startFrenzyMode(){

        if(match.getCurrentPlayer().getId() == 0) {
            for (int i = match.getCurrentPlayer().getId(); i < match.getPlayers().size(); i++)
                match.getPlayers().get(i).getStatus().setSpecialAbilityFrenzyLower();
        }

        else{
            for (int i = match.getCurrentPlayer().getId(); i < match.getPlayers().size(); i++)
                match.getPlayers().get(i).getStatus().setSpecialAbilityFrenzy();
            for (int i = 0; i < match.getCurrentPlayer().getId(); i++)
                match.getPlayers().get(i).getStatus().setSpecialAbilityFrenzyLower();
        }
        for(Player p: match.getPlayers()){
            p.getStatus().setTurnStatusWaitTurnFrenzy();
            if(p.getBoard().getTotalNumberOfDamages() == 0)
                p.setPlayerMoodFrenzy(true);

        }

        System.out.println("Finito di settare le abilitè dei player frenzy");
        for (Player p: match.getPlayers()){
            System.out.println(p.getNickname() + " status "+p.getStatus().getSpecialAbility());
        }

        if(match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY)) {
            match.getCurrentPlayer().getStatus().setTurnStatusFirstActionFrenzy();
            System.out.println("Settato First action Frenzy");
        }
        if(match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY_LOWER)) {
            match.getCurrentPlayer().getStatus().setTurnStatusFirstActionLowerFrenzy();
            System.out.println("Settato " +
                    "First action frenzy lower");
        }
        System.out.println("The current player : "+match.getCurrentPlayer().getNickname() +" is in Round status " + match.getCurrentPlayer().getStatus().getTurnStatus() );
    }

    private void goToNextStatusFrenzy(Player p){
        switch(p.getStatus().getTurnStatus()){
            case WAIT_TURN_FRENZY:
                if(p.getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY))
                    p.getStatus().setTurnStatusFirstActionFrenzy();
                if(p.getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY_LOWER))
                    p.getStatus().setTurnStatusFirstActionLowerFrenzy();
                break;

            case FIRST_ACTION_FRENZY:
                p.getStatus().setTurnStatusSecondActionFrenzy();
                break;

            case SECOND_ACTION_FRENZY:
                try {
                    serverControllerRMI.askRespawn();
                    //questo serve solo per il primo turno, ovvero per gestire la prima spawn, in teoria poi non crea problemi. (TO TEST)
                }catch(Exception e){
                    e.printStackTrace();
                }
                setNewCurrentPlayerFrenzy();
                p.getStatus().setTurnStatusEndGame();
                endOfTurn();
                goToNextStatusFrenzy(match.getCurrentPlayer());
                //TODO gestire fine della partita qui (controllare se tutti sono in endGame e mostrare dati partita)
                break;

            case FIRST_ACTION_LOWER_FRENZY:
                try {
                    serverControllerRMI.askRespawn();
                    //questo serve solo per il primo turno, ovvero per gestire la prima spawn, in teoria poi non crea problemi. (TO TEST)
                }catch(Exception e){
                    e.printStackTrace();
                }
                setNewCurrentPlayerFrenzy();
                p.getStatus().setTurnStatusEndGame();
                endOfTurn();
                goToNextStatusFrenzy(match.getCurrentPlayer());
                //TODO gestire fine della partita qui (controllare se tutti sono in endGame e mostrare dati partita)
                break;
        }
    }

    private void setNewCurrentPlayerFrenzy(){
        if(everybodyRespawned()) {
            if (match.getCurrentPlayer().getId() == (match.getPlayers().size() - 1)) {
                match.setCurrentPlayer(match.getPlayers().get(0));
            } else {
                match.setCurrentPlayer(match.getPlayers().get(match.getCurrentPlayer().getId() + 1));
            }
        }
        else{
            Timer waitForRespawn = new Timer();
            waitForRespawn.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            Match model = getMatch();
                            if (everybodyRespawned()) {
                                if (match.getCurrentPlayer().getId() == (match.getPlayers().size() - 1)) {
                                    match.setCurrentPlayer(match.getPlayers().get(0));
                                } else {
                                    match.setCurrentPlayer(match.getPlayers().get(match.getCurrentPlayer().getId() + 1));
                                }
                                waitForRespawn.cancel();
                                waitForRespawn.purge();
                            }
                        }
                    }, 1, 3000
            );

        }

    }

    private boolean everybodyRespawned(){
        for(Player p: match.getPlayers())
            if(p.isInStatusRespawn())
                return false;

        return true;
    }

    //metodo per andare a skippare la fase del turno (esempio uno vuole fare una sola action)
    public void skipAction(Player p) throws WrongStatusException{
        if(checkIfCanSkipAction(p)) {
            System.out.println("[TURN]: The player " + p.getNickname() + " skipped an action");
            goToNextStatus(p);
            printPlayerStatuses();
        }
        else
            throw new WrongStatusException("You cannot skip the turn now!");
    }

    public void skipActionFrenzy(Player p) throws WrongStatusException{
        if(checkIfCanSkipActionFrenzy(p)) {
            System.out.println("[TURN]: The player " + p.getNickname() + " skipped an action");
            goToNextStatusFrenzy(p);
            printPlayerStatuses();
        }
        else
            throw new WrongStatusException("You cannot skip the turn now!");
    }

    public void disconnectPlayer(String nickname){
        if(checkPlayerPresence(nickname)){

            if(match.getPlayer(nickname).isInStatusLobbyMaster() && match.getPlayers().size() > 1){
                match.getPlayer(nickname).getStatus().setTurnStatusDisconnected();
                for(Player p: match.getPlayers()){
                    if(p.isConnected()){
                        p.getStatus().setTurnStatusLobbyMaster();
                        break;
                    }
                }
            }

            else
                match.getPlayer(nickname).getStatus().setTurnStatusDisconnected();

            /*
            if(match.getPlayer(nickname).isInStatusMaster() && match.getPlayers().size() > 1){
                for(Player p: match.getPlayers()){
                    int count = 0;
                    if(p.getNickname().equals(nickname)){
                        match.getPlayers().get(count + 1).getStatus().setTurnStatusMaster();
                    }
                    count ++;
                }
            }

             */
        }
    }

    public synchronized void shoot(ShootingParametersInput input) throws WrongStatusException, NotAllowedTargetException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedShootingModeException, RemoteException {

        boolean shootCompleted = false;

        if(canDoAction()) {
            shootController.setInput(input);

            if (input.getWeapon().getWeaponStatus() != WeaponAmmoStatus.LOADED)
                throw new NotEnoughAmmoException("You are trying to shoot with an unloaded weapon, nice shot!");

            if (!(input.getShootModes().contains(ShootMode.BASIC) || input.getShootModes().contains(ShootMode.ALTERNATE)))
                throw new NotAllowedShootingModeException();

            if (input.getShootModes().contains(ShootMode.BASIC) && input.getShootModes().contains(ShootMode.ALTERNATE))
                throw new NotAllowedShootingModeException();


            try {       //switch that chooses the right method for the right weapon

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

                shootCompleted = true;      //if everything has worked in shoot method

            } catch (NotEnoughAmmoException e) {

                int redAmmoToPay = 0;           //all the ammo that need to be paid
                int blueAmmoToPay = 0;
                int yellowAmmoToPay = 0;

                for (ShootMode mode : input.getShootModes()) {
                    redAmmoToPay += getWeaponCost(input.getWeapon().getModeCost(mode))[0];
                    blueAmmoToPay += getWeaponCost(input.getWeapon().getModeCost(mode))[1];
                    yellowAmmoToPay += getWeaponCost(input.getWeapon().getModeCost(mode))[2];
                }

                int actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
                int actualBlueAmmo =  match.getCurrentPlayer().getAmmo().getBlueAmmo();
                int actualYellowAmmo =  match.getCurrentPlayer().getAmmo().getYellowAmmo();

                if (checkForPowerUpsAsAmmo(redAmmoToPay - actualRedAmmo, blueAmmoToPay - actualBlueAmmo, yellowAmmoToPay- actualYellowAmmo)) {
                    waitForPayment = new Timer();
                    waitForPaymentTask = new TimerTask() {
                        @Override
                        public void run() {

                            int redAmmoToPay = 0;           //all the ammo that need to be paid
                            int blueAmmoToPay = 0;
                            int yellowAmmoToPay = 0;

                            int actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
                            int actualBlueAmmo =  match.getCurrentPlayer().getAmmo().getBlueAmmo();
                            int actualYellowAmmo =  match.getCurrentPlayer().getAmmo().getYellowAmmo();

                            for (ShootMode mode : input.getShootModes()) {
                                redAmmoToPay += getWeaponCost(input.getWeapon().getModeCost(mode))[0];
                                blueAmmoToPay += getWeaponCost(input.getWeapon().getModeCost(mode))[1];
                                yellowAmmoToPay += getWeaponCost(input.getWeapon().getModeCost(mode))[2];
                            }


                            if(actualRedAmmo - redAmmoToPay < 0 || actualBlueAmmo - blueAmmoToPay < 0 || actualYellowAmmo - yellowAmmoToPay < 0) {
                                System.out.println("[SHOOT]: " + match.getCurrentPlayer().getNickname() + " is trying to pay with a power up.");
                                try {
                                    serverControllerRMI.askForPowerUpAsAmmo();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    shoot(input);
                                } catch (WrongStatusException | NotAllowedTargetException | NotAllowedMoveException | NotEnoughAmmoException | RemoteException | NotAllowedShootingModeException ex) {
                                    System.out.println("[INFO]: Error in timer");
                                    ex.printStackTrace();
                                }
                                waitForPaymentTask.cancel();
                                waitForPayment.cancel();
                                waitForPayment.purge();
                            }
                        }
                    };
                    waitForPayment.schedule(waitForPaymentTask, 1, 2000);
                } else {
                    throw new NotEnoughAmmoException(e.getMessage());
                }

            }   //end of catch NotEnoughAmmoException

            if (shootCompleted) {       //enter here if the shooting process has really terminated (no more timer waiting for powerup as ammo)

                for (Player player : match.getPlayers()) {
                    if (player.isAskForTagBackGrenade()) {
                        try {
                            serverControllerRMI.askForTagBackGrenade(player.getNickname());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }

                input.getWeapon().setWeaponStatus(WeaponAmmoStatus.UNLOADED);

                System.out.println("[INFO]: Weapon" + input.getWeapon().getName() + " is " + input.getWeapon().getWeaponStatus());

                System.out.println("[INFO]: Updated lives of all player \n");

                for (Player p : match.getPlayers()) {
                    System.out.println("[SHOOT]: " + p.getNickname() + "'s " + p.getBoard().toStringLP());
                    System.out.println("[SHOOT]: " + p.getNickname() + "'s " + p.getBoard().toStringMarks());
                    System.out.println("\n");
                }

                goToNextStatus(match.getCurrentPlayer());

                printPlayerStatuses();

                serverControllerRMI.pushMatchToAllPlayers();
            }
        }
        else
            throw new WrongStatusException("You are not allowed to shoot now!");
    }

    private synchronized int[] getWeaponCost(List<Color> cost) {
        //this method returns an array with the cost of weapon divided per colors
        // red, blue, yellow (order)
        int[] array = {0, 0, 0};

        for (Color color : cost) {
            switch (color) {
                case RED: array[0]++; break;
                case BLUE: array[1]++; break;
                case YELLOW: array[2]++; break;
                default: break;
            }
        }

        return array;

    }

    public synchronized void reloadWeapon(Weapon weapon) throws NotEnoughAmmoException, WrongStatusException, RemoteException {

        if (!match.getCurrentPlayer().isInStatusReloading())
            throw new WrongStatusException("You cannot reload now");

        if (weapon.getWeaponStatus() == WeaponAmmoStatus.LOADED)
            return;

        int r = getWeaponCost(weapon.getCost())[0];             //cost of the weapon
        int b = getWeaponCost(weapon.getCost())[1];
        int y = getWeaponCost(weapon.getCost())[2];
        int actualRedAmmo;    //ammo already owned by the current player
        int actualBlueAmmo;
        int actualYellowAmmo;

        actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
        actualBlueAmmo = match.getCurrentPlayer().getAmmo().getBlueAmmo();
        actualYellowAmmo = match.getCurrentPlayer().getAmmo().getYellowAmmo();

        if (actualRedAmmo - r < 0 || actualBlueAmmo - b < 0 || actualYellowAmmo - y < 0) {
            if (checkForPowerUpsAsAmmo(r - actualRedAmmo, b - actualBlueAmmo, y - actualYellowAmmo)) {
                waitForWeaponLoaded = new Timer();
                waitForWeaponLoadedTask =  new TimerTask() {
                    @Override
                    public void run() {
                        int actualRedAmmo;    //ammo already owned by the current player
                        int actualBlueAmmo;
                        int actualYellowAmmo;
                        int r = getWeaponCost(weapon.getCost())[0];             //cost of the weapon
                        int b = getWeaponCost(weapon.getCost())[1];
                        int y = getWeaponCost(weapon.getCost())[2];

                        actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
                        actualBlueAmmo = match.getCurrentPlayer().getAmmo().getBlueAmmo();
                        actualYellowAmmo = match.getCurrentPlayer().getAmmo().getYellowAmmo();

                        if(actualRedAmmo - r < 0 || actualBlueAmmo - b < 0 || actualYellowAmmo - y < 0) {
                            System.out.println("[RELOAD]: " + match.getCurrentPlayer().getNickname() + " is reloading " + weapon.getName());
                            try {
                                serverControllerRMI.askForPowerUpAsAmmo();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            match.getCurrentPlayer().removeAmmo(r, b, y);
                            weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
                            try {
                                serverControllerRMI.pushMatchToAllPlayers();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            waitForWeaponLoadedTask.cancel();
                            waitForWeaponLoaded.cancel();
                            waitForWeaponLoaded.purge();
                        }
                    }};
                waitForWeaponLoaded.schedule(waitForWeaponLoadedTask, 1, 2000);
            } else {
                throw new NotEnoughAmmoException("It seems you don't have enough ammo");
            }
        } else {
            match.getCurrentPlayer().removeAmmo(r, b, y);
            weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
            serverControllerRMI.pushMatchToAllPlayers();
        }

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

    public void endOfTurn(){
        //this method is called automatically by the server at the end of the turn of each player
        int numberOfPeopleKilled = 0;   //local variable that keeps the count of how many people have been killed in order to updated doubleKill in killShot track

        for (Player p : match.getPlayers()) {
            if (p.isDead()) {
                Board board = p.getBoard();
                scoreBoard(board);
                if (board.getLifePoints()[11] != 9)
                    match.getPlayers().get(board.getLifePoints()[11]).getBoard().updateMarks(1, p.getId(), board.getLifePoints()[11]);  //giving the revenge mark


                match.getKillShotTrack().setMortalShots(match.getCurrentPlayer().getId());  //with the following lines I replace the skull in KillShotTrack, with the token id of the current player
                if (board.isOverKilled())
                    match.getKillShotTrack().setMortalShots(match.getCurrentPlayer().getId());

                match.getKillShotTrack().setMortalShots(9);

                match.getKillShotTrack().decreaseSkulls();      //remove just one skull

                board.initializeBoard();    //resetting the board of th killed player
                board.increaseNumberOfDeaths();

                p.falseDead();
                p.getStatus().setSpecialAbilityNormal();
                p.getStatus().setTurnStatusRespawn();

                numberOfPeopleKilled++;

            }
        }

        if (numberOfPeopleKilled > 2)
            match.getKillShotTrack().setDoubleKill(match.getCurrentPlayer().getId());
    }

    private void scoreBoard(Board board) {
        //this method score the board of a dead player, giving points to the other players
        java.util.Map<Integer, List<String>> rank = new HashMap<>();
        ArrayList<Integer> numberOfDamages = new ArrayList<>();

        for (Player p : match.getPlayers()) {
            int hits = board.howManyHits(p.getId());
            numberOfDamages.add(hits);
        }



        for (int i = 0; i < numberOfDamages.size(); i++) {
            rank = addElementInRank(numberOfDamages.get(i), i, rank);
        }

        numberOfDamages.removeIf(x -> x.equals(0));   //remove the players who made no damage
        numberOfDamages = numberOfDamages.stream().distinct().collect(Collectors.toCollection(ArrayList::new));   //removing duplicates

        numberOfDamages.sort(Comparator.naturalOrder());
        Collections.reverse(numberOfDamages);

        int deaths = board.getNumberOfDeaths();

        for (int n : numberOfDamages) {
            if (rank.get(n).size() == 1) {
                int points = board.getPoints()[deaths];
                match.getPlayer(rank.get(n).get(0)).addPoints(points);
                deaths++;
            } else {
                ArrayList<Integer> arrayIDPlayersSameDamage = new ArrayList<>();
                for (String nickname : rank.get(n)) {
                    arrayIDPlayersSameDamage.add(match.getPlayer(nickname).getId());
                }
                do {
                    int idPlayer = board.whoMadeDamageBefore(arrayIDPlayersSameDamage);
                    int points = board.getPoints()[deaths];
                    match.getPlayers().get(idPlayer).addPoints(points);
                    deaths++;
                    arrayIDPlayersSameDamage.remove(0);     //TODO maybe it doesn't work for concurrent access
                } while (!arrayIDPlayersSameDamage.isEmpty());
            }
        }

        match.getPlayers().get(board.getLifePoints()[0]).addPoints(1);   //first blood
    }

    private java.util.Map<Integer, List<String>> addElementInRank(int damageMade, int idPlayer, java.util.Map<Integer, List<String>> rank) {

        List<String> currentValue = rank.get(damageMade);
        if (currentValue == null) {
            currentValue = new ArrayList<>();
            rank.put(damageMade, currentValue);
        }
        currentValue.add(match.getPlayers().get(idPlayer).getNickname());

        return rank;
    }

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


    // FRENZY METHODS
    public synchronized void shootFrenzy(ShootingParametersInput input) throws  NotAllowedTargetException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedShootingModeException {

            shootController.setInput(input);

            if (input.getWeapon().getWeaponStatus() != WeaponAmmoStatus.LOADED)
                throw new NotEnoughAmmoException("You are trying to shoot with an unloaded weapon, nice shot!");

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

            } catch (NotEnoughAmmoException e) {
                e.printStackTrace();
                throw new NotEnoughAmmoException("It seems you do not have enough ammo");
            }

            input.getWeapon().setWeaponStatus(WeaponAmmoStatus.UNLOADED);

            System.out.println("[INFO]: Updated lives of all player \n");

            for (Player p : match.getPlayers()) {
                System.out.println("[SHOOT]: " + p.getNickname()+ "'s " + p.getBoard().toStringLP());
                System.out.println("[SHOOT]: " + p.getNickname()+ "'s " + p.getBoard().toStringMarks());
                System.out.println("\n");
            }

            printPlayerStatuses();

    }

    public void makeAction1Frenzy(Square destination, ShootingParametersInput input, Player player) throws WrongStatusException, NotEnoughAmmoException, NotAllowedShootingModeException, NotAllowedMoveException, NotAllowedTargetException {
        System.out.println(match.getCurrentPlayer().getNickname()+" "+match.getCurrentPlayer().getStatus().getTurnStatus());
        if (canDoActionFrenzyBoosted()){
            try {
                moveController.move(player,destination,1);
                    shootFrenzy(input);
                    goToNextStatusFrenzy(player);
                } catch (NotAllowedTargetException e) {
                    throw new NotAllowedTargetException(e.getMessage());
                } catch (NotEnoughAmmoException e) {
                    throw new NotEnoughAmmoException(e.getMessage());
                } catch (NotAllowedShootingModeException e) {
                    throw new NotAllowedShootingModeException(e.getMessage());
                } catch (NotAllowedMoveException e) {
                throw new NotAllowedMoveException(e.getMessage());
            }
        }
        else
            throw new WrongStatusException("You are not allowed to execute Action 1 now, you must wait for your turn");


    }

    public void makeAction1FrenzyLower(Square destination, ShootingParametersInput input, Player player) throws WrongStatusException, NotEnoughAmmoException, NotAllowedMoveException, NotAllowedShootingModeException, NotAllowedTargetException {
        System.out.println(match.getCurrentPlayer().getNickname()+" "+match.getCurrentPlayer().getStatus().getTurnStatus());
        if (canDoActionFrenzyLower()){
           try {
               moveController.move(player,destination,2);
                   shootFrenzy(input);
                   goToNextStatusFrenzy(player);
               }  catch (NotAllowedTargetException e) {
                   throw new NotAllowedTargetException(e.getMessage());
               } catch (NotEnoughAmmoException e) {
                   throw new NotEnoughAmmoException(e.getMessage());
               } catch (NotAllowedShootingModeException e) {
                   throw new NotAllowedShootingModeException(e.getMessage());
               } catch (NotAllowedMoveException e) {
               throw new NotAllowedMoveException(e.getMessage());
           }
       }
       else
           throw new WrongStatusException("You are not allowed to execute Action 1 now, you must wait for your turn");

    }

    public void makeAction2Frenzy(Square destination, Player player) throws NotAllowedMoveException, WrongStatusException {
        System.out.println(match.getCurrentPlayer().getNickname()+" "+match.getCurrentPlayer().getStatus().getTurnStatus());
        if (canDoActionFrenzyBoosted()){
            try {
                moveController.move(player,destination,4);
                goToNextStatusFrenzy(player);
            } catch (NotAllowedMoveException e) {
                e.printStackTrace();
                throw new NotAllowedMoveException(e.getMessage());
            }
        }
        else
            throw new WrongStatusException("You are not allowed to execute Action 2 now, you must wait for your turn");

    }

    public void makeAction3Frenzy(Square destination,int numbOfWeaponToGrab,Player player, int indexOfWeaponToSwap) throws NotAllowedMoveException, WrongStatusException {
        System.out.println(match.getCurrentPlayer().getNickname()+" "+match.getCurrentPlayer().getStatus().getTurnStatus());
        if (canDoActionFrenzyBoosted()){
            try {
                moveController.move(player,destination,2);
                if (indexOfWeaponToSwap >= 0 && indexOfWeaponToSwap < 3){
                    if (destination.getType().equals(SquareType.SPAWN)){
                        grabController.grabWeapon(destination.getAvailableWeapons().get(numbOfWeaponToGrab), indexOfWeaponToSwap);
                    }
                    else grabController.grabAmmoCard();
                }
                else{
                    if (destination.getType().equals(SquareType.SPAWN)){
                        grabController.grabWeapon(destination.getAvailableWeapons().get(numbOfWeaponToGrab), -1);
                    }
                    else grabController.grabAmmoCard();
                }
                goToNextStatusFrenzy(player);
            } catch (NotAllowedMoveException | WrongPositionException | NotEnoughAmmoException | NotAllowedCallException e) {
                e.printStackTrace();
                throw new NotAllowedMoveException(e.getMessage());
            }
        }
        else
            throw new WrongStatusException("You are not allowed to execute Action 3 now, you must wait for your turn");
    }

    public void makeAction2FrenzyLower(Square destination,int numbOfWeaponToGrab ,Player player, int indexOfWeaponToSwap) throws NotAllowedMoveException, WrongStatusException {
        System.out.println(match.getCurrentPlayer().getNickname()+" "+match.getCurrentPlayer().getStatus().getTurnStatus());
        if (canDoActionFrenzyLower()){
            try {
                moveController.move(player,destination,3);
                if (indexOfWeaponToSwap >= 0 && indexOfWeaponToSwap < 3){
                    if (destination.getType().equals(SquareType.SPAWN)){
                        grabController.grabWeapon(destination.getAvailableWeapons().get(numbOfWeaponToGrab), indexOfWeaponToSwap);
                    }
                    else grabController.grabAmmoCard();
                }
                else{
                    if (destination.getType().equals(SquareType.SPAWN)){
                        grabController.grabWeapon(destination.getAvailableWeapons().get(numbOfWeaponToGrab), -1);
                    }
                    else grabController.grabAmmoCard();
                }
                goToNextStatusFrenzy(player);
            } catch (NotAllowedMoveException | WrongPositionException | NotEnoughAmmoException | NotAllowedCallException e) {
                e.printStackTrace();
                throw new NotAllowedMoveException(e.getMessage());
            }
        }
        else
            throw new WrongStatusException("You are not allowed to execute Action 2 now, you must wait for your turn");

    }

    public Boolean canDoActionFrenzyBoosted(){
        if (match.getCurrentPlayer().getStatus().getTurnStatus().equals(RoundStatus.FIRST_ACTION_FRENZY) || match.getCurrentPlayer().getStatus().getTurnStatus().equals(RoundStatus.SECOND_ACTION_FRENZY)) return true;
        else return false;
    }

    public Boolean canDoActionFrenzyLower(){
        if (match.getCurrentPlayer().getStatus().getTurnStatus().equals(RoundStatus.FIRST_ACTION_LOWER_FRENZY)) return true;
        else return false;
    }

    public void closeTimer(String timerName) {
        switch (timerName) {
            case "WaitForPayment":
                this.waitForPaymentTask.cancel();
                this.waitForPayment.cancel();
                this.waitForPayment.purge();
                break;
            case "WaitForWeaponLoaded":
                this.waitForWeaponLoadedTask.cancel();
                this.waitForWeaponLoaded.cancel();
                this.waitForWeaponLoaded.purge();
                break;
            default: break;
        }
    }
}