package controller;

import model.map.SquareType;
import model.Match;
import model.player.Player;
import model.map.Directions;

public class GrabController extends ActionController {

    private Match match;

    //TODO costruttore?

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
            match.getCurrentPlayer().getPosition().setAmmoTile(match.getAmmoDeck().pickFirstCard()); // rimpiazzo la carta
        }
    }

    public void grabWeapon(int numberOfWeapon){         // da passare come argomento quale arma prendere, la 0 1 o 2
        if ((numberOfWeapon<3) && (numberOfWeapon>=0)){
            if(match.getCurrentPlayer().getPosition().getType() == SquareType.SPAWN){
                // if (check)
                match.getCurrentPlayer().addWeapons(match.getCurrentPlayer().getPosition().getWeaponBox().get(numberOfWeapon)); // arma aggiunta al giocatore
                match.getCurrentPlayer().getPosition().removeWeapon(match.getCurrentPlayer().getPosition().getWeaponBox().get(numberOfWeapon)); // rimuovo arma dallo square
                match.getCurrentPlayer().getPosition().addWeapon(match.getWeaponDeck().pickFirstCard()); // aggiungo un'arma allo square
            }
        }

    }

    public boolean checkAmmoForWeapon(int numberOfWeapon){
        // TODO CHIUEDERE A RICHI QUALE SIA IL COSTO BASE DI UN ARMA
        return false; //provvisorio per far compilare
    }

}
