package model.player;

import model.ammo.AmmoDeck;
import model.map.Map;
import model.powerUp.PowerUpDeck;

import java.util.ArrayList;

public class Match {
    private ArrayList<Player> players;
    private int playerIdCorrente;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private Map map;

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
}
