package controller;

import model.Color;
import model.map.SquareType;
import model.Match;
import model.player.Player;
import model.map.Directions;
import model.weapons.Weapon;
import java.util.*;
public class GrabController extends ActionController {

    private Match match;

    //TODO costruttore?

    public Player getPlayer() {
        return match.getCurrentPlayer();
    }

    public void setPlayer(Player player){
        this.match.setCurrentPlayer(player);
    }

    /*
        implementazione del metodo astratto dalla classe abstract.
    */
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
            match.getCurrentPlayer().addAmmo(match.getCurrentPlayer().getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            match.getAmmoDeck().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // reinserisco la carta nel deck
            match.getCurrentPlayer().getPosition().setAmmoTile(match.getAmmoDeck().pickFirstCard()); // rimpiazzo la carta
        }
         // else  TODO THROW WRONGPOSITION ?
    }

    public void grabWeapon(int numberOfWeapon){         // da passare come argomento quale arma prendere, la 0 1 o 2
        if ((numberOfWeapon<3) && (numberOfWeapon>=0)){
            if(match.getCurrentPlayer().getPosition().getType() == SquareType.SPAWN){
                // inizio controllo munizioni disponibili
                List<Color> weaponCost = match.getCurrentPlayer().getPosition().getWeaponBox().get(numberOfWeapon).getCost();
                int redTmp=0, blueTemp=0, yelloTmp=0;
                for (int i=1; i<weaponCost.size(); i++){
                    switch (weaponCost.get(i)){
                        case BLUE:
                            blueTemp++;
                            break;
                        case RED:
                            redTmp++;
                            break;
                        case YELLOW:
                            yelloTmp++;
                            break;
                    }
                }

                if (    ((match.getCurrentPlayer().getAmmo().getRedAmmo()-redTmp)>=0)
                        && ((match.getCurrentPlayer().getAmmo().getBlueAmmo()-blueTemp)>=0)
                        && ((match.getCurrentPlayer().getAmmo().getYellowAmmo()-yelloTmp)>=0)) {
                    // fine controllo munizioni disponibili
                    match.getCurrentPlayer().removeAmmo(redTmp, blueTemp, yelloTmp); // il giocatore paga l'arma
                    match.getCurrentPlayer().addWeapons(match.getCurrentPlayer().getPosition().getWeaponBox().get(numberOfWeapon)); // arma aggiunta al giocatore
                    match.getCurrentPlayer().getPosition().removeWeapon(match.getCurrentPlayer().getPosition().getWeaponBox().get(numberOfWeapon)); // rimuovo arma dallo square
                    match.getCurrentPlayer().getPosition().addWeapon(match.getWeaponDeck().pickFirstCard()); // aggiungo un'arma allo square
                }
                // else  TODO THROW notEnoughAmmo ? gestisci di conseguenza se vuole pagare con i PowerUps
            }
            // else  TODO THROW WRONGPOSITION ?
        }
        else throw new IndexOutOfBoundsException();
    }

}