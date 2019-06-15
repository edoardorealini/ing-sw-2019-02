package controller;

import exception.*;
import model.Color;
import model.Match;
import model.ShootMode;
import model.ShootingParametersInput;
import model.map.*;
import model.map.Map;
import model.map.MapBuilder;
import model.player.AbilityStatus;
import model.player.Board;
import model.player.Player;
import model.player.PlayerStatusHandler;
import model.powerup.PowerUp;
import model.weapons.*;
import server.RMIHandler.ServerControllerRMI;

import javax.security.auth.login.FailedLoginException;
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
                System.out.println("[INFO]: Map filled with ammos");

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


    public synchronized int getMaxDistanceAllowed(Player player){
        AbilityStatus abilityStatus = player.getStatus().getSpecialAbility();
        if(abilityStatus.equals(AbilityStatus.NORMAL))
            return 3;
        //TODO controllare qui AbiltyStatus di due tipi in FRENZY.
        if(abilityStatus.equals(AbilityStatus.FRENZY))
            return 4;
        return 0;
    }

    //the first spawn always occurs when a pleyer is the current player.
    public synchronized void spawn(PowerUp powerUpChosen, Player user) throws NotInYourPossessException, WrongStatusException{
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
            // OLD : match.getCurrentPlayer().goToNextStatus();
            goToNextStatus(match.getCurrentPlayer());
        }
        else
            throw new WrongStatusException("You cannot grab any ammo now!");
    }

    public  synchronized void grabWeapon(Weapon weapon) throws WrongPositionException, NotEnoughAmmoException, WrongStatusException {
        if(canDoAction()) {
            try {
                grabController.grabWeapon(weapon);
                goToNextStatus(match.getCurrentPlayer());
            } catch (WrongPositionException e) {
                throw new WrongPositionException(e.getMessage());
            } catch (NotEnoughAmmoException e2) {
                throw new NotEnoughAmmoException(e2.getMessage());
            }
        }
        else
            throw new WrongStatusException("You cannot grab any weapons now!");
    }

    public synchronized void grabMove(Square destination) throws NotAllowedMoveException{
        try{
            if(match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.NORMAL))
                moveController.move(match.getCurrentPlayer(), destination, 1);

            if(match.getCurrentPlayer().getStatus().getSpecialAbility().equals(AbilityStatus.ADRENALINE_PICK))
                moveController.move(match.getCurrentPlayer(), destination, 2);

        } catch (NotAllowedMoveException e) {
            e.printStackTrace();
            throw new NotAllowedMoveException(e.getMessage());
        }
    }

    //metodi di powerUpController
    public synchronized void usePowerUpAsAmmo(PowerUp powerUp) throws NotInYourPossessException, NotAllowedCallException {
            if (match.getCurrentPlayer().hasPowerUp(powerUp)) {
                try {
                    powerUpController.usePowerUpAsAmmo(powerUp);
                } catch (NotAllowedCallException e) {
                    throw new NotAllowedCallException(e.getMessage());
                }
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
           if(match.getPlayer(nickName.trim()).isConnected()) //with .trim() a usernames with the same name of another but with spaces is not allowed!
               throw new FailedLoginException("[ERROR]: Player already connected, try with another nickname");

           if(!match.getPlayer(nickName).isConnected()) {
               if(match.getActiveStatusMatch())
                   match.getPlayer(nickName).getStatus().setTurnStatusWaitTurn();

               if(!match.getActiveStatusMatch())
                   match.getPlayer(nickName).getStatus().setTurnStatusLobby();

               if(!checkThereIsLobbyMaster())
                   match.getPlayer(nickName).getStatus().setTurnStatusLobbyMaster();

               System.out.println("[INFO]: The player " + nickName + " is already registered, relogging ... ");
               return;
           }
        }

        if(match.getPlayers().size() > 4)
            throw new FailedLoginException("[ERROR]: The lobby is full, try again later . . .");


        match.getPlayers().add(new Player(nickName, match.getPlayers().size(), getMatch()));
        //qui devo aggiornare il numero di giocatori connessi e nel caso far partire i cronometri
        //setta current player se sono il primo a connettermi

        //if (match.getPlayers().size() == 1)
        //   match.setCurrentPlayer(match.getPlayers().get(0));

        if(!match.getActiveStatusMatch())
            match.getPlayer(nickName).getStatus().setTurnStatusLobby();

        if(!checkThereIsLobbyMaster())
            match.getPlayer(nickName).getStatus().setTurnStatusLobbyMaster();

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
                goToNextStatus(p);
                break;

            case RELOADING:
                p.getStatus().setTurnStatusEndTurn();
                goToNextStatus(p);
                break;

            case END_TURN:
                //TODO RICKY qui chiamiamo la routine di end_turn!! (ora possiamo siamo entro il match controller)
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
                if(!p.isDead())
                    p.getStatus().setTurnStatusFirstAction();
                else
                    p.getStatus().setTurnStatusSpawn();
                break;
        }
    }
    //nel dubbio metti synchronized un po' ovunque e dovrebbe andare a posto  ;)
    public synchronized void setNewCurrentPlayer(){
        //TODO controllo su giocatori disconnessi
        int idCurrentPlayer = match.getCurrentPlayer().getId();

        if(!everybodyRespawned()) {
            if (idCurrentPlayer == match.getPlayers().size() - 1) {
                //TODO prova a implementare attesa con timer
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
                        }, 1, 3000
                );
            }
        }
        else{
            if (idCurrentPlayer == match.getPlayers().size() - 1) {

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

    private boolean everybodyRespawned(){
        for(Player p: match.getPlayers())
            if(p.isInStatusRespawn())
                return false;

        return true;
    }

    private void putWaitFirstTurnInSpawn(){
        for(Player p: match.getPlayers()) {
            if (p.isInStatusWaitFirstTurn()) {
                goToNextStatus(p);
                try {
                    serverControllerRMI.pushMatchToAllPlayers();
                }catch(RemoteException e){
                    e.printStackTrace();
                }
                return;
            }
        }


    }

    //TODO metodi nuovi che devono essere aggiunti a tutto il giro (per ora solo RMI)per essere chiamati da client (vedi appuntiClient per capire cosa intendo con "giro")

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

            //qui devo cambiare chi è il master e gestire che se uno era in lobby torna in lobby quando si riconnette
        }
    }

    public synchronized void shoot(ShootingParametersInput input) throws WrongStatusException, NotAllowedTargetException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedShootingModeException {
        if(canDoAction()) {
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

            }  catch (NotAllowedMoveException e) {
                throw new  NotAllowedMoveException();
            } catch (NotEnoughAmmoException e) {

                //TODO pay with powerups

                throw new NotEnoughAmmoException("It seems you do not have enough ammo");
            } catch (NotAllowedTargetException e) {
                throw new NotAllowedTargetException();
            } catch (NotAllowedShootingModeException e) {
                throw new NotAllowedShootingModeException();
            }
            input.getWeapon().setWeaponStatus(WeaponAmmoStatus.UNLOADED);

            System.out.println("[INFO]: Updated lives of all player \n");

            for (Player p : match.getPlayers()) {
                System.out.println("[SHOOT]: " + p.getNickname()+ "'s " + p.getBoard().toStringLP());
                System.out.println("[SHOOT]: " + p.getNickname()+ "'s " + p.getBoard().toStringMarks());
                System.out.println("\n");
            }

            goToNextStatus(match.getCurrentPlayer());

            printPlayerStatuses();
        }
        else
            throw new WrongStatusException("You are not allowed to shoot now!");
    }

    public synchronized void reloadWeapon(Weapon weapon) throws NotEnoughAmmoException, WrongStatusException {

        if (!match.getCurrentPlayer().isInStatusReloading())
            throw new WrongStatusException("You cannot reload now");

        if (weapon.getWeaponStatus() == WeaponAmmoStatus.LOADED)
            return;

        int r = 0;             //cost of the weapon
        int b = 0;
        int y = 0;
        int actualRedAmmo;    //ammo already owned by the current player
        int actualBlueAmmo;
        int actualYellowAmmo;


        for (Color color : weapon.getCost()) {
            switch (color) {
                case RED: r++; break;
                case BLUE: b++; break;
                case YELLOW: y++; break;
                default: break;
            }
        }


        actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
        actualBlueAmmo = match.getCurrentPlayer().getAmmo().getBlueAmmo();
        actualYellowAmmo = match.getCurrentPlayer().getAmmo().getYellowAmmo();

        System.out.println("[RELOAD]: 1 " + match.getCurrentPlayer().getNickname() + " is reloading " + weapon.getName());

        if (actualRedAmmo - r < 0 || actualBlueAmmo - b < 0 || actualYellowAmmo - y < 0) {
            if (checkForPowerUpsAsAmmo(r - actualRedAmmo, b - actualBlueAmmo, y - actualYellowAmmo)) {
                System.out.println("[RELOAD]: 2 " + match.getCurrentPlayer().getNickname() + " is reloading " + weapon.getName());
                Timer waitForWeaponLoaded = new Timer();
                waitForWeaponLoaded.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                int r = 0;             //cost of the weapon
                                int b = 0;
                                int y = 0;
                                int actualRedAmmo;    //ammo already owned by the current player
                                int actualBlueAmmo;
                                int actualYellowAmmo;
                                for (Color color : weapon.getCost()) {
                                    switch (color) {
                                        case RED: r++; break;
                                        case BLUE: b++; break;
                                        case YELLOW: y++; break;
                                        default: break;
                                    }
                                }

                                actualRedAmmo = match.getCurrentPlayer().getAmmo().getRedAmmo();
                                actualBlueAmmo = match.getCurrentPlayer().getAmmo().getBlueAmmo();
                                actualYellowAmmo = match.getCurrentPlayer().getAmmo().getYellowAmmo();

                                if(actualRedAmmo - r < 0 || actualBlueAmmo - b < 0 || actualYellowAmmo - y < 0) {
                                    System.out.println("[RELOAD]: 3 " + match.getCurrentPlayer().getNickname() + " is reloading " + weapon.getName());
                                    try {
                                        serverControllerRMI.askForPowerUpAsAmmo();
                                        System.out.println("riga 781");
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    match.getCurrentPlayer().removeAmmo(r, b, y);
                                    weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
                                    waitForWeaponLoaded.cancel();
                                    waitForWeaponLoaded.purge();
                                }
                            }}, 1, 1000
                );
            } else {
                throw new NotEnoughAmmoException("It seems you don't have enough ammo");
            }
        } else {
            match.getCurrentPlayer().removeAmmo(r, b, y);
            weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
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

                match.getKillShotTrack().decreaseSkulls();      //remove just one skull

                //TODO if the number of skull is 0, frenzy mode should start (IDEA: set every player to frenzy status) - GOOD IDEA MANN e.

                board.initializeBoard();    //resetting the board of th killed player
                board.increaseNumberOfDeaths();

                p.falseDead();
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
                    int IDPlayer = board.whoMadeDamageBefore(arrayIDPlayersSameDamage);
                    int points = board.getPoints()[deaths];
                    match.getPlayers().get(IDPlayer).addPoints(points);
                    deaths++;
                    arrayIDPlayersSameDamage.remove(0);     //TODO maybe it doesn't work for concurrent access
                } while (!arrayIDPlayersSameDamage.isEmpty());
            }
        }

        match.getPlayers().get(board.getLifePoints()[0]).addPoints(1);   //first damage
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

}