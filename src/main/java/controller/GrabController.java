package controller;

import model.player.Player;

public class GrabController extends ActionController {






    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public boolean moveAdmitted(){

    }
}
