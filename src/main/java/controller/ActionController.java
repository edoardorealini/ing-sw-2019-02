package controller;

import model.map.Map;
import model.player.Player;
import model.map.Map;

public  abstract class ActionController {
    protected Player player;
    protected Map map;

    public abstract Player getPlayer();


}
