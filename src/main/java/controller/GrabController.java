package controller;

import exception.NotAllowedCallException;
import exception.NotEnoughAmmoException;
import exception.WrongPositionException;
import model.Color;
import model.map.SquareType;
import model.Match;
import model.weapons.Weapon;
import model.weapons.WeaponAmmoStatus;

import java.util.*;

/**
 * This class control the action Grab and check if the player is allowed to do that
 */

public class GrabController{

    private Match match;

    /**
     * This method is the constructor for the Grab controller
     * @param match is the current match
     * @param moveCtrl is the Move controller (is helpful because before the Grab action you can move)
     */

    public GrabController(Match match, MoveController moveCtrl){
        this.match = match;
    }


    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * This method checks if the player can Grab an Ammo Card, and then grabs the ammo card
     * @throws WrongPositionException if the position requested is not type NOSPAWN
     * @see model.map.SquareType for the type of square
     */
    public void grabAmmoCard() throws WrongPositionException {
        if(match.getCurrentPlayer().getPosition().getType() == SquareType.NOSPAWN){
            match.getCurrentPlayer().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            match.getAmmoDeck().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // reinserisco la carta nel deck
            match.getCurrentPlayer().getPosition().setAmmoTile(match.getAmmoDeck().pickFirstCard()); // rimpiazzo la carta
        }
        else throw new WrongPositionException("Square is not type NOSPAWN");
    }

    /**
     * This method checks if the player can Grab Weapon, and then grabs the weapon
     * @param weapon is the weapon to Grab
     * @param indexOfWeaponToSwap if the player has already 3 weapons he has to chose the index of the Weapon to leave
     * @throws WrongPositionException if the position requested is not type SPAWN
     * @throws NotEnoughAmmoException if the player has not enough ammo to grab the weapon
     * @throws NotAllowedCallException if the weapon is not contained in the square
     * @see model.map.SquareType for the type of square
     */
    public void grabWeapon(Weapon weapon, int indexOfWeaponToSwap) throws WrongPositionException, NotEnoughAmmoException, NotAllowedCallException {
            if(match.getCurrentPlayer().getPosition().getType() == SquareType.SPAWN){

                // inizio controllo munizioni disponibili
                if (match.getCurrentPlayer().getPosition().getAvailableWeapons().contains(weapon)){
                    List <Color> weaponCost = weapon.getCost();
                    int redTmp=0, blueTemp=0, yelloTmp=0;
                    for (int i = 1; i < weaponCost.size(); i++) {       //starting from i = 1 because the first is free
                        switch (weaponCost.get(i)) {
                            case YELLOW:
                                yelloTmp++;
                                break;
                            case BLUE:
                                blueTemp++;
                                break;
                            case RED:
                                redTmp++;
                                break;
                            default:
                                break;
                        }
                    }

                    if (match.getCurrentPlayer().getAmmo().getRedAmmo()-redTmp >= 0 && match.getCurrentPlayer().getAmmo().getBlueAmmo()-blueTemp >=0 && match.getCurrentPlayer().getAmmo().getYellowAmmo()-yelloTmp >=0) {
                        match.getCurrentPlayer().removeAmmo(redTmp, blueTemp, yelloTmp);       //pays the cost

                        if (indexOfWeaponToSwap == -1) {        //if the player has enough room in hand for another weapon
                            match.getCurrentPlayer().addWeapons(weapon);
                            weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
                            match.getCurrentPlayer().getPosition().removeWeapon(weapon); //remove weapon from the square
                            match.getCurrentPlayer().getPosition().addWeapon(match.getWeaponDeck().pickFirstCard()); //replace the weapon in the square
                        } else {
                            Weapon temp = match.getCurrentPlayer().getWeapons()[indexOfWeaponToSwap];
                            temp.setWeaponStatus(WeaponAmmoStatus.PARTIALLYLOADED);
                            match.getCurrentPlayer().getWeapons()[indexOfWeaponToSwap] = null;
                            match.getCurrentPlayer().addWeapons(weapon);
                            weapon.setWeaponStatus(WeaponAmmoStatus.LOADED);
                            match.getCurrentPlayer().getPosition().removeWeapon(weapon); //remove weapon from the square
                            match.getCurrentPlayer().getPosition().addWeapon(temp); //replace the weapon in the square with the one that was in the hand of the player
                        }
                    } else throw new NotEnoughAmmoException("Not enough ammo to buy this weapon");

                } else throw new NotAllowedCallException("No such a weapon in weaponBox");

            } else throw new WrongPositionException("Square is not type SPAWN");
    }

}