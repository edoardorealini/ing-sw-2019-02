package model;

import model.ammo.AmmoDeck;
import model.map.*;
import model.player.Player;
import model.powerUp.PowerUpDeck;
import model.weapons.WeaponDeck;

import java.io.IOException;
import java.util.ArrayList;

public class Match {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private Map map;
    private WeaponDeck weaponDeck;

    /*
        costruttore di match si aspetta in input id della mappa da costruire
    */

    public Match(int mapID){
        this.players = new ArrayList<>();
        this.powerUpDeck = new PowerUpDeck();
        this.ammoDeck = new AmmoDeck();
        this.weaponDeck = new WeaponDeck();
        weaponDeck.shuffleDeck();
        ammoDeck.shuffleDeck();

        try {
            this.map = new MapBuilder().makeMap(mapID);
        }catch(IOException e){
            e.printStackTrace(); //non serve per ora gestire con logger
        }

        //TODO qui si potrebbe mettere la logica che riempie l'oggetto "map"
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
        players.add(players.size(), player);
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
}
