package player;

import ammo.AmmoCard;
import weapons.Weapon;
import map.Square;
import ammo.Ammo;
import powerUps.PowerUps;

public class Player {

    private String nickname;
    private int id;                  // NB. id = 9 non si può usare, vedi inizializzazione board
    private Square position;
    private Board board;
    private Weapon[] weapons = {null, null, null};
    private Ammo ammo;
    private PowerUps[] powerUps = {null, null, null};
    private int points;
    private PlayerStatusHandler status;
    private boolean dead;

    public Player (String nickname, int id, Square position, Board board,
                   PowerUps powerUps, PlayerStatusHandler status, boolean dead ){
        this.nickname=nickname;
        this.id=id;
        this.position=null;
        this.board=new Board();
        ammo = new Ammo();
        points=0;
        //status
        dead=false;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
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

    public void addAmmo(AmmoCard ammoCard) {
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
                    this.powerUps[i] = ammoCard.getPowerUps();      // controlla che prenda indirizzo di powerUps della carta;
                    return;
                }
            }
        }
    }

    public void removeAmmo(Ammo ammo) {
        // TODO da fare quando si implementa la classe weapon, ho bisogno il numero di ogni colore
    }

    public PowerUps[] getPowerUps() {
        return powerUps;
    }

    public void addPowerUps(PowerUps p) {
        for (int i=0; i<3; i++){
            if (powerUps[i]==null) {
                powerUps[i] = p;
                //return "PowerUps aggiunto";
            }
        }

        //return "spazio powerUps esaurito";
    }

    public void removePowerUps(int i) {
        if (i<3 && i>=0){
            powerUps[i]=null;
            //return "PowerUps tolto";
        }
        //else return "impossibile togliere PowerUps";
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int p) {
        points += p;
    }

    public PlayerStatusHandler getStatus() {
        return status;
    }

    public void setStatus(PlayerStatusHandler status) {
        this.status = status;
    }

    public void trueDead() {
        dead = true;
    }

    public void falseDead() {
        dead = false;
    }

}