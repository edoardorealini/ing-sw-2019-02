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
        return match.getCurrentPlayer();
    }

    public void setPlayer(Player player){
        this.match.setCurrentPlayer(player);
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public boolean moveAdmitted(Directions direction){
        return((match.getMap().getAllowedMoves(match.getCurrentPlayer().getPosition())).contains(direction));

    }

    public void moveOneSquare(Directions direction){
        match.getCurrentPlayer().setPosition(match.getMap().getSquare(direction, match.getCurrentPlayer().getPosition()));
    }

    public void grabAmmoCard(){
        if(match.getCurrentPlayer().getPosition().getType() == SquareType.NOSPAWN){
            match.getCurrentPlayer().addAmmo(match.getCurrentPlayer().getPosition().getAmmoTile());
            match.getAmmoDeck().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            match.getCurrentPlayer().getPosition().setAmmoTile(match.getAmmoDeck().removeAmmoCard()); // rimpiazzo la carta
        }
    }
    // grab ammo

}
