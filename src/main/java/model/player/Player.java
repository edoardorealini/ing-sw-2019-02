package model.player;

import model.Match;
import model.ammo.AmmoCard;
import model.weapons.Weapon;
import model.map.Square;
import model.ammo.Ammo;
import model.powerup.PowerUp;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable{

    private String nickname;
    private int id;                  // NB. id = 9 non si può usare, vedi inizializzazione board
    private Square position;
    private Board board;
    private Weapon[] weapons = {null, null, null};
    private Ammo ammo;
    private PowerUp[] powerUps = {null, null, null};
    private int points;
    private PlayerStatusHandler status;
    private RoundStatus roundStatus;
    private AbilityStatus abilityStatus;
    private boolean askForTagBackGrenade;
    private boolean dead;
    private Match match;
    private Boolean playerMoodFrenzy = false; // only for the lifeboard GUI

    public Player (String nickname, int id, Match match){
        this.nickname = nickname;
        this.id = id;
        this.position = null;
        this.askForTagBackGrenade = false;
        this.board = new Board();
        this.ammo = new Ammo();
        this.points = 0;
        this.status = new PlayerStatusHandler();
        dead = false;
        this.match = match;
        powerUps[0] = match.getPowerUpDeck().pickFirstCard();   //the player has a power up at the start of the game

        this.status.setTurnStatusLobby();

        if(this.id == 0){
            this.status.setTurnStatusLobbyMaster();
        }

    }

    public boolean isConnected() {
        return !status.getTurnStatus().equals(RoundStatus.DISCONNECTED);
    }

    public String getNickname() {
        return nickname;
    }

    public int getId() {
        return id;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public Board getBoard(){
        return this.board;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    public void addWeapons( Weapon w) {
        for (int i=0; i<3; i++){
            if (weapons[i]==null) {
                this.weapons[i] = w;
                return;
            }
        }
    }

    public void removeWeapons(int i){
        if (i<3 && i>=0){
            weapons[i]=null;
            //return "arma tolta";
        }
        //else return "impossibile togliere arma";
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public boolean isAskForTagBackGrenade() {
        return askForTagBackGrenade;
    }

    public void setAskForTagBackGrenade(boolean askForTagBackGrenade) {
        this.askForTagBackGrenade = askForTagBackGrenade;
    }

    public void addAmmoCard(AmmoCard ammoCard) {
        if ((ammo.getBlueAmmo()+ammoCard.getBlueAmmo())>=3){
            ammo.setBlueAmmo(3);
        }
        else ammo.setBlueAmmo(ammo.getBlueAmmo()+ammoCard.getBlueAmmo());

        if ((ammo.getYellowAmmo()+ammoCard.getYellowAmmo())>=3){
            ammo.setYellowAmmo(3);
        }
        else ammo.setYellowAmmo(ammo.getYellowAmmo()+ammoCard.getYellowAmmo());

        if ((ammo.getRedAmmo()+ammoCard.getRedAmmo())>=3){
            ammo.setRedAmmo(3);
        }
        else ammo.setRedAmmo(ammo.getRedAmmo()+ammoCard.getRedAmmo());

        if (ammoCard.isTherePowerUp()){
            for (int i=0; i<3; i++){
                if (powerUps[i]==null) {
                    this.powerUps[i] = match.getPowerUpDeck().pickFirstCard();
                    return;
                }
            }
        }
    }

    // metodo che serve per trasformare i powerUps in munizioni NB da chiamare solo se richiesto

    public synchronized void transformPowerUpToAmmo(PowerUp powerUp){
        for (int i=0; i<3; i++){
            if (powerUp.equals(powerUps[i])){
                match.getPowerUpDeck().addPowerUps(powerUps[i]);
                powerUps[i]=null;
                ammo.addSpecificAmmo(powerUp.getColor(), 1);
                return;
            }
        }
    }

    public synchronized void removeAmmo(int redAmmo, int blueAmmo, int yellowAmmo){
        ammo.setRedAmmo(ammo.getRedAmmo()-redAmmo);
        ammo.setBlueAmmo(ammo.getBlueAmmo()-blueAmmo);
        ammo.setYellowAmmo(ammo.getYellowAmmo()-yellowAmmo);
    }

    public PowerUp[] getPowerUps() {
        return powerUps;
    }

    public void addPowerUpsCard(PowerUp p) { // metodo in cui so quale sia il powerup da aggiungere (es PowerUp nella carta sulla mappa)
        for (int i=0; i<3; i++){
            if (powerUps[i]==null) {
                powerUps[i] = p;
                return;
            }
        }

        //return "spazio model.powerup esaurito";
    }

    // servirà per l'inzio del gioco nel quale i giocatori pescano i powerUps
    public void addPowerUpsHaphazard() { // pesco casualmente un PowerUp
        for (int i=0; i<3; i++){
            if (powerUps[i] == null) {
                powerUps[i] = match.getPowerUpDeck().pickFirstCard();
                return;
            }
        }

        //return "spazio model.powerup esaurito";
    }

    public void removePowerUps(PowerUp powerUp) {
        for (int i=0; i<3;i++){
            if(powerUps[i] != null) {
                if (powerUp.equals(powerUps[i])) {
                    match.getPowerUpDeck().addPowerUps(powerUp);
                    powerUps[i] = null;
                    return;
                }
            }
        }
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int p) {
        points += p;
    }

    public PlayerStatusHandler getStatus(){
        return status;
    }

    public void trueDead() {
        dead = true;
    }

    public void falseDead() {
        dead = false;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public String toString() {
        return nickname;
    }

    public String printPosition() {
        StringBuilder string = new StringBuilder();
        string.append("Player ");
        string.append(nickname);
        string.append(" is in position ");
        List<Integer> coordinates = match.getMap().getIndex(position);
        String temp = "[" + coordinates.get(0) + "," + coordinates.get(1) +"]";
        string.append(temp);
        return string.toString();
    }

    public boolean hasPowerUp( PowerUp card){
        for (int i = 0; i < 3; i++){
            if(powerUps[i] != null) {
                if (powerUps[i].equals(card)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isInStatusWaitTurn(){
        return status.getTurnStatus().equals(RoundStatus.WAIT_TURN);
    }

    public boolean isInStatusMaster(){
        return status.getTurnStatus().equals(RoundStatus.MASTER);
    }

    public boolean isInStatusLobbyMaster(){
        return status.getTurnStatus().equals(RoundStatus.LOBBY_MASTER);
    }

    public boolean isInStatusLobby(){
        return status.getTurnStatus().equals(RoundStatus.LOBBY);
    }

    public boolean isInStatusFirstAction(){
        return status.getTurnStatus().equals(RoundStatus.FIRST_ACTION);
    }

    public boolean isInStatusSecondAction(){
        return status.getTurnStatus().equals(RoundStatus.SECOND_ACTION);
    }

    public boolean isInStatusSpawn(){
        return status.getTurnStatus().equals(RoundStatus.SPAWN);
    }

    public boolean isInStatusReloading(){
        return status.getTurnStatus().equals(RoundStatus.RELOADING);
    }

    public boolean isInStatusWaitFirstTurn(){
        return status.getTurnStatus().equals(RoundStatus.WAIT_FIRST_TURN);
    }
    public boolean isInStatusRespawn(){
        return status.getTurnStatus().equals(RoundStatus.RESPAWN);
    }


    /*
    public void goToNextStatus(){
        switch(status.getTurnStatus()){
            case LOBBY_MASTER:
                status.setTurnStatusMaster();
                break;

            case LOBBY:
                status.setTurnStatusWaitFirstTurn();
                break;

            case WAIT_FIRST_TURN:
                status.setTurnStatusSpawn();
                break;

            case MASTER:
                status.setTurnStatusSpawn();
                break;

            case SPAWN:
                status.setTurnStatusFirstAction();
                break;

            case FIRST_ACTION:
                status.setTurnStatusSecondAction();
                break;

            case SECOND_ACTION:
                status.setTurnStatusReloading();
                break;

            case RELOADING:
                status.setTurnStatusEndTurn();
                break;

            case END_TURN:
                status.setTurnStatusWaitTurn();
                break;

            case WAIT_TURN:
                if(!dead)
                    status.setTurnStatusFirstAction();
                else
                    status.setTurnStatusSpawn();
                break;
        }
    }
    */

    public String printPowerUps() {
        StringBuilder string = new StringBuilder();
        for(int i=0; i<3; i++) {
            if (powerUps[i] != null) {
                string.append(i);
                string.append("-");
                string.append(powerUps[i]);
                string.append("\t");
            }
        }
        return string.toString();
    }

    public void setPlayerMoodFrenzy(Boolean playerMoodFrenzy) {
        this.playerMoodFrenzy = playerMoodFrenzy;
    }

    public Boolean getPlayerMoodFrenzy() {
        return playerMoodFrenzy;
    }
}