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

public class GrabController{

    private Match match;

    public GrabController(Match match, MoveController moveCtrl){
        this.match = match;
    }


    public void setMatch(Match match) {
        this.match = match;
    }


    public void grabAmmoCard() throws WrongPositionException {
        if(match.getCurrentPlayer().getPosition().getType() == SquareType.NOSPAWN){
            match.getCurrentPlayer().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // aggiungo le munizioi e altro al player
            match.getAmmoDeck().addAmmoCard(match.getCurrentPlayer().getPosition().getAmmoTile()); // reinserisco la carta nel deck
            match.getCurrentPlayer().getPosition().setAmmoTile(match.getAmmoDeck().pickFirstCard()); // rimpiazzo la carta
        }
        else throw new WrongPositionException("Square is not type NOSPAWN");
    }

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