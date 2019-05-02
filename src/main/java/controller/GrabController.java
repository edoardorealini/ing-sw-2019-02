package controller;

import model.player.Player;
import model.map.Map;

public class GrabController extends ActionController {

    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){

        this.player = player;
    }



    public boolean moveAdmitted(String direction){




        return false;
    }

}
