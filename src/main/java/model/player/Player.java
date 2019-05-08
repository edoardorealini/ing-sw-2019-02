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

public class Player {

    private String nickname;
    private int id;                  // NB. id = 9 non si può usare, vedi inizializzazione board
    private Square position;
    private Board board;
    private Weapon[] weapons = {null, null, null};
    private Ammo ammo;
    private PowerUp[] powerUps = {null, null, null};
    private int points;
    private PlayerStatusHandler status;
    private boolean dead;
    private Match match;

    public Player (String nickname, int id){
        this.nickname=nickname;
        this.id=id;
        this.position=null;
        this.board=new Board();
        ammo = new Ammo();
        points=0;
        status = new PlayerStatusHandler();
        if (id==0){
            this.status.setTurnStatusMaster();
        }
        else {
            this.status.setTurnStatusWaitTurn();
        }
        this.status.setSpecialAbilityNormal();
        dead=false;

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
    //TODO x Edo, bella man
    /*
    public void trasformPowerUpToAmmo(PowerUp powerUp){
        for (int i=0; i<3; i++){
            if ((powerUps[i].getColor()==color)&&(powerUps[i].getName()==powerUpName)){
                match.getPowerUpDeck().addPowerUps(powerUps[i]);
                powerUps[i]=null;
                ammo.addSpecificAmmo(color, 1);
                return;
            }
        }
    }
    */
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

    public void removePowerUps(int i) {
        if (i<3 && i>=0){
            powerUps[i]=null;
            //return "PowerUp tolto";
        }
        //else return "impossibile togliere PowerUp";
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
}