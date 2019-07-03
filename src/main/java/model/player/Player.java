package model.player;

import model.Color;
import model.Match;
import model.ammo.AmmoCard;
import model.weapons.Weapon;
import model.map.Square;
import model.ammo.Ammo;
import model.powerup.PowerUp;

import java.io.Serializable;
import java.util.List;

/**
 * This class is the player in the game, with all his attributes
 */

public class Player implements Serializable{

    private String nickname;
    private int id;                  // NB. not usable id = 9 see the initialization of the board
    private Square position;
    private Board board;
    private Weapon[] weapons = {null, null, null};
    private Ammo ammo;
    private PowerUp[] powerUps = {null, null, null};
    private int points;
    private PlayerStatusHandler status;
    private boolean askForTagBackGrenade;
    private boolean beenDamaged;      //for targeting scope
    private boolean dead;
    private Match match;
    private Boolean frenzyBoard = false; // only for the lifeboard GUI
    private boolean endedGame = false;

    /**
     * This is the constructor of the player, it needs a unique nickname, an ID to determine the player more easy and the object match
     * that specify on which match the player is playing
     * @param nickname nickname of the player
     * @param id unique ID to recognize the player in the match
     * @param match is the class that contains most of the game match
     */

    public Player (String nickname, int id, Match match){
        this.nickname = nickname;
        this.id = id;
        this.position = null;
        this.askForTagBackGrenade = false;
        this.board = new Board();
        this.ammo = new Ammo();
        this.points = 0;
        this.status = new PlayerStatusHandler();
        this.dead = false;
        this.beenDamaged = false;
        this.match = match;
        powerUps[0] = match.getPowerUpDeck().pickFirstCard();   //the player has a power up at the start of the game

        this.status.setTurnStatusLobby();

        if(this.id == 0){
            this.status.setTurnStatusLobbyMaster();
        }

    }

    /**
     * This method sets the attribute of the player if it has finished the game
     * @param endedGame boolean that is true if the player is in the final part of the game
     */

    public void setEndedGame(boolean endedGame) {
        this.endedGame = endedGame;
    }

    /**
     * This method returns the value of the attribute End game of the player
     * @return endGame
     */

    public boolean isEndedGame() {
        return endedGame;
    }

    /**
     * This method verifies the connection of the player to the server
     * @return true if the player is connected
     */

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

    /**
     * This method adds a weapon to the player's weapons
     * @param w weapon to add
     */

    public void addWeapons( Weapon w) {
        for (int i = 0; i < 3; i++){
            if (weapons[i] == null) {
                this.weapons[i] = w;
                return;
            }
        }
    }

    /**
     * This method removes a weapon to the player's weapons
     * @param i indicates the index of the player's weapons to remove
     */

    public void removeWeapons(int i){
        if (i < 3 && i >= 0){
            weapons[i] = null;
        }
    }

    public void setBeenDamaged(boolean beenDamaged) {
        this.beenDamaged = beenDamaged;
    }

    public boolean hasBeenDamaged() {
        return beenDamaged;
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

    /**
     * This method adds an Ammo Card to the player
     * @param ammoCard it is the ammo Card to add
     */

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

    /**
     * This method transforms a Power Up into an Ammo of the same color
     * @param powerUp it is the Powe Up that you want to transform
     */

    public synchronized void transformPowerUpToAmmo(PowerUp powerUp) {
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

    public synchronized void removeSingleAmmo(Color color) {
        switch (color) {
            case RED: ammo.setRedAmmo(ammo.getRedAmmo()-1); break;
            case BLUE: ammo.setBlueAmmo(ammo.getBlueAmmo()-1); break;
            case YELLOW: ammo.setYellowAmmo(ammo.getYellowAmmo()-1); break;
            default: break;
        }
    }

    public PowerUp[] getPowerUps() {
        return powerUps;
    }

    /**
     * This method adds a Power Up to the player
     * @param p is the Power Up to add
     */

    public void addPowerUpsCard(PowerUp p) {
        for (int i=0; i<3; i++){
            if (powerUps[i]==null) {
                powerUps[i] = p;
                return;
            }
        }
    }

    /**
     * This method adds a Power Up to the player directly from the Power Up deck
     */

    public void addPowerUpsHaphazard() {
        for (int i=0; i<3; i++){
            if (powerUps[i] == null) {
                powerUps[i] = match.getPowerUpDeck().pickFirstCard();
                return;
            }
        }
    }

    /**
     * This method remove from the player a specific Power Up
     * @param powerUp is the Power Up to remove from the player
     */
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

    /**
     * This method returns the status of the player
     * @see model.player.PlayerStatusHandler for the statuses
     * @return the status of the player
     */

    public PlayerStatusHandler getStatus(){
        return status;
    }

    /**
     * This method sets true the attribute dead
     */

    public void trueDead() {
        dead = true;
    }

    /**
     * This method sets false the attribute dead
     */

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

    /**
     * This method prints the position of the player, it is helpful for debug
     * @return the position of the player
     */

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

    /**
     * This method checks if the player has the Power Up given by parameter
     * @param card is the Power Up to check
     * @return true if the card is contained
     */
    public boolean hasPowerUp(PowerUp card){
        for (int i = 0; i < 3; i++){
            if(powerUps[i] != null) {
                if (powerUps[i].equals(card)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Series of methods to check if the player is in these states
     * @return true if the player is in the status
     */

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

    /**
     * This method sets the attribute frenzy Board of the player
     * @see client.GUI.LifeBoard for usage
     * @param frenzyBoard true if you want to set true the attribute frenzyBoard of the player
     */

    public void setFrenzyBoard(Boolean frenzyBoard) {
        this.frenzyBoard = frenzyBoard;
    }

    public Boolean getFrenzyBoard() {
        return frenzyBoard;
    }


}