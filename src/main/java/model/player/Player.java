package model.player;

import model.Color;
import model.Match;
import model.ammo.AmmoCard;
import model.powerup.PowerUpDeck;
import model.powerup.PowerUpName;
import model.weapons.Weapon;
import model.map.Square;
import model.ammo.Ammo;
import model.powerup.PowerUp;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Player  implements Serializable {

    private String nickname;
    private int id;                  // NB. id = 9 non si può usare, vedi inizializzazione board
    private Square position;
    private Board board;
    private Weapon[] weapons = {null, null, null};
    private Ammo ammo;
    private PowerUp[] powerUps = {null, null, null};
    private int points;
    private PlayerStatusHandler status;
    private boolean dead; //valore da aggiornare quando si uccide il giocatore!! //TODO chiedere a ricky se lo fa con ShootController
    private Match match;

    public Player (String nickname, int id, Match match){
        this.nickname=nickname;
        this.id=id;
        this.position=null;
        this.board=new Board();
        ammo = new Ammo();
        points=0;
        status = new PlayerStatusHandler();

        if(id == 0){
            status.setTurnStatusLobbyMaster();
        }

        dead=false;
        this.match=match;

        //metto nella mano del player gia due power up per inizio della partita.
        powerUps[0] = match.getPowerUpDeck().pickFirstCard();
        powerUps[1] = match.getPowerUpDeck().pickFirstCard();

    }

    public boolean isConnected(){
        if(status.getTurnStatus().equals(RoundStatus.DISCONNECTED))
            return false;

        return true;
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
               // return "Weapon aggiunta";
            }
        }
        //return "spazio armi esaurito";
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

    public void transformPowerUpToAmmo(PowerUp powerUp){
        for (int i=0; i<3; i++){
            if (powerUp.equals(powerUps[i])){
                match.getPowerUpDeck().addPowerUps(powerUps[i]);
                powerUps[i]=null;
                ammo.addSpecificAmmo(powerUp.getColor(), 1);
                return;
            }
        }
    }

    public void removeAmmo(int redAmmo, int blueAmmo, int yellowAmmo){
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
                //return "PowerUp aggiunto";
            }
        }

        //return "spazio model.powerup esaurito";
    }

    // servirà per l'inzio del gioco nel quale i giocatori pescano i powerUps
    public void addPowerUpsHaphazard() { // pesco casualmente un PowerUp
        for (int i=0; i<3; i++){
            if (powerUps[i]==null) {
                powerUps[i] = match.getPowerUpDeck().pickFirstCard();
                //return "PowerUp aggiunto";
            }
        }

        //return "spazio model.powerup esaurito";
    }

    public void removePowerUps(PowerUp powerUp) {
        for (int i=0; i<3;i++){
            if (powerUp.equals(powerUps[i])){
                match.getPowerUpDeck().addPowerUps(powerUp);
                powerUps[i]=null;
                return;
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
            if(powerUps[i].equals(card)){
                return true;
            }
        }

        return false;
    }

    public boolean isInStatusWaitTurn(){
        if(status.getTurnStatus().equals(RoundStatus.WAIT_TURN))
            return true;

        return false;
    }

    public boolean isInStatusMaster(){
        if(status.getTurnStatus().equals(RoundStatus.MASTER))
            return true;

        return false;
    }

    public boolean isInStatusLobbyMaster(){
        if(status.getTurnStatus().equals(RoundStatus.LOBBY_MASTER))
            return true;

        return false;
    }

    public boolean isInStatusLobby(){
        if(status.getTurnStatus().equals(RoundStatus.LOBBY))
            return true;

        return false;
    }

    public boolean isInStatusFirstAction(){
        if(status.getTurnStatus().equals(RoundStatus.FIRST_ACTION))
            return true;

        return false;
    }

    public boolean isInStatusSecondAction(){
        if(status.getTurnStatus().equals(RoundStatus.SECOND_ACTION))
            return true;

        return false;
    }

    public boolean isInStatusSpawn(){
        if(status.getTurnStatus().equals(RoundStatus.SPAWN))
            return true;

        return false;
    }

    public boolean isInStatusReloading(){
        if(status.getTurnStatus().equals(RoundStatus.RELOADING))
            return true;

        return false;
    }

    public boolean isInStatusEndTurn(){
        if(status.getTurnStatus().equals(RoundStatus.END_TURN))
            return true;

        return false;
    }

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

}