package controller;

import model.map.SquareType;
import model.player.Match;
import model.player.Player;
import model.map.Map;
import model.map.Directions;
import model.ammo.AmmoDeck;

public class GrabController extends ActionController {

    private Match match;

    @Override
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
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
            match.getAmmoDeck().addAmmoCard(player.getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            player.getPosition().setAmmoTile(match.getAmmoDeck().removeAmmoCard()); // rimpiazzo la carta
        }
    }

    

    // grab ammo

}
