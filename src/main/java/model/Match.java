package model;

import model.ammo.AmmoDeck;
import model.map.*;
import model.player.Player;
import model.powerup.PowerUpDeck;
import model.weapons.WeaponDeck;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This is the most important class of model package
 * Contains all the match information, from the players, to the map, the decks and the killshotTrack
 */
public class Match implements Serializable {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private Map map;
    private WeaponDeck weaponDeck;
    private boolean matchIsActive;
    private KillShotTrack killShotTrack;

    /**
     * Default costructor, builds a complete match, shuffles the decks
     * It does not build the map
     * It exposes only a lot of getter and setter methods, the actual logic is in the controller package
     */
    public Match(){
        this.players = new ArrayList<>();
        this.powerUpDeck = new PowerUpDeck();
        this.ammoDeck = new AmmoDeck();
        this.weaponDeck = new WeaponDeck();
        weaponDeck.shuffleDeck();
        ammoDeck.shuffleDeck();
        powerUpDeck.shuffle();
        this.matchIsActive = false;
        this.killShotTrack = new KillShotTrack();
    }

    /**
     * This constructor builds a copy of a given match without givin the decks (this object is pushed to the clients)
     * @param another another match, to copy from
     */
    public Match(Match another){
        this.players = another.players;
        this.currentPlayer = another.currentPlayer;
        this.map = another.map;
        this.matchIsActive = another.matchIsActive;
        this.killShotTrack = another.killShotTrack;
        this.weaponDeck = null;
        this.ammoDeck = null;
        this.powerUpDeck = null;
    }


    public boolean getActiveStatusMatch(){
        return matchIsActive;
    }

    public Player getPlayer(String nickname){
        for(Player p: players){
            if(p.getNickname().equals(nickname))
                return p;
        }

        return null;
    }

    public void setMatchIsActive(boolean status){
        this.matchIsActive = status;
    }

    public void setAmmoDeck(AmmoDeck ammoDeck) {
        this.ammoDeck = ammoDeck;
    }

    public void setPowerUpDeck(PowerUpDeck powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    public AmmoDeck getAmmoDeck() {
        return ammoDeck;
    }

    public WeaponDeck getWeaponDeck() { return weaponDeck; }

    public PowerUpDeck getPowerUpDeck() {
        return powerUpDeck;
    }

    public Map getMap() {
        return map;
    }

    public int howManyPlayers(){
        return players.size();
    }

    public void setPlayers(Player player){
        players.add(player);
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public KillShotTrack getKillShotTrack() {
        return killShotTrack;
    }

}
