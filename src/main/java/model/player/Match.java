package model.player;

import java.util.ArrayList;

public class Match {
    private ArrayList<Player> players;
    private int playerIdCorrente;

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
