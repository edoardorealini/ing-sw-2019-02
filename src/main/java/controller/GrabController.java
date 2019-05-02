package controller;

import model.map.SquareType;
import model.player.Player;
import model.map.Map;
import model.map.Directions;
import model.ammo.AmmoDeck;

public class GrabController extends ActionController {

    private AmmoDeck ammoDeck;

    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public void setAmmoDeck(AmmoDeck ammoDeck) {
        this.ammoDeck = ammoDeck;
    }

    public boolean moveAdmitted(Directions direction){
        return((map.getAllowedMoves(player.getPosition())).contains(direction));

    }

    public void moveOneSquare(Directions direction){
        player.setPosition(map.getSquare(direction, player.getPosition()));
    }

    public void grabAmmoCard(){
        if(player.getPosition().getType() == SquareType.NOSPAWN){
            player.addAmmo(player.getPosition().getAmmoTile());
            ammoDeck.addAmmoCard(player.getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            player.getPosition().setAmmoTile(ammoDeck.removeAmmoCard()); // rimpiazzo la carta
        }
    }

    

}
