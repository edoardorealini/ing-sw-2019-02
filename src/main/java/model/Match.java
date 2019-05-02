package model;

import model.ammo.AmmoDeck;
import model.map.*;
import model.player.Player;
import model.powerUp.PowerUpDeck;

import java.io.IOException;
import java.util.ArrayList;

public class Match {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private Map map;

    /*
        costruttore di match si aspetta in input id della mappa da costruire
    */

    public Match(int mapID){
        this.players = new ArrayList<>();
        this.powerUpDeck = new PowerUpDeck();
        this.ammoDeck = new AmmoDeck();
        try {
            this.map = new MapBuilder().makeMap(mapID);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setAmmoDeck(AmmoDeck ammoDeck) {
        this.ammoDeck = ammoDeck;
    }

    public void setPowerUpDeck(PowerUpDeck powerUpDeck) {
        this.powerUpDeck = powerUpDeck;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public AmmoDeck getAmmoDeck() {
        return ammoDeck;
    }

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
