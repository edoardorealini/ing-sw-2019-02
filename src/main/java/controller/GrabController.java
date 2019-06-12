package controller;

import exception.NotEnoughAmmoException;
import exception.WrongPositionException;
import model.Color;
import model.map.SquareType;
import model.Match;
import model.player.Player;
import model.map.Directions;
import model.weapons.Weapon;
import model.weapons.WeaponAmmoStatus;

import java.util.*;
public class GrabController extends ActionController {

    private Match match;
    private MoveController moveController;

    public GrabController(Match match, MoveController moveCtrl){
        this.match = match;
        this.moveController = moveCtrl;
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

    /* classi già presenti in MoveController
    public boolean moveAdmitted(Directions direction){
        return((match.getMap().getAllowedMoves(match.getCurrentPlayer().getPosition())).contains(direction));

    }

    public void moveOneSquare(Directions direction){
        match.getCurrentPlayer().setPosition(match.getMap().getSquare(direction, match.getCurrentPlayer().getPosition()));
    }

    */

    /*TODO NB. sia nella grabAmmo che grabWeapon dopo aver raccolto la carta io la rimpiazzo subito, sarà da mettere un controller che impedisce di raccogliere di nuovo dallo stesso square (es di grabAmmo) */

    public void grabAmmoCard() throws WrongPositionException {
        if(match.getCurrentPlayer().getPosition().getType() == SquareType.NOSPAWN){
            match.getCurrentPlayer().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            match.getAmmoDeck().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // reinserisco la carta nel deck
            match.getCurrentPlayer().getPosition().setAmmoTile(match.getAmmoDeck().pickFirstCard()); // rimpiazzo la carta
        }
        else throw new WrongPositionException("Square is not type NOSPAWN");
    }

    public void grabWeapon(Weapon weapon) throws WrongPositionException, NotEnoughAmmoException {
            if(match.getCurrentPlayer().getPosition().getType() == SquareType.SPAWN){
                // inizio controllo munizioni disponibili
                if (match.getCurrentPlayer().getPosition().getAvailableWeapons().contains(weapon)){
                    List <Color> weaponCost = weapon.getCost();
                    int redTmp=0, blueTemp=0, yelloTmp=0;
                    for (int i = 1; i < weaponCost.size(); i++){
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
                        match.getCurrentPlayer().addWeapons(weapon); // arma aggiunta al giocatore
                        weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
                        match.getCurrentPlayer().getPosition().removeWeapon(weapon); // rimuovo arma dallo square
                        match.getCurrentPlayer().getPosition().addWeapon(match.getWeaponDeck().pickFirstCard()); // aggiungo un'arma allo square
                    }
                    else throw new NotEnoughAmmoException("Not enough ammo to buy this weapon");

                }
                else throw new IllegalArgumentException("No such a weapon in weaponBox");
            }
            else throw new WrongPositionException("Square is not type SPAWN");
    }

}