package model.map;
import java.util.*;

import model.weapons.*;

public class SpawnSquare extends Square {
    private ArrayList<Weapon> weaponBox; //array che contiene i ref alle armi presenti nel box

    public SpawnSquare(Color color, Boolean activeStatus, ArrayList<Directions> allowedMoves, ArrayList<Directions> doors, ArrayList<Weapon> weapons){
        super(color, activeStatus, allowedMoves, doors);
        weaponBox = new ArrayList<>(weapons);
    }

    public SpawnSquare(Boolean activeStatus){
        super(activeStatus);
    }

    public List<Weapon> getWeaponBox() {
        return weaponBox;
    }

    /*
        addWeapon adds a weapon to the weaponBox container.
        A check to weaponDeck should be added (controller side)
    */
    public Boolean addWeapon(Weapon weapon){
        if(weaponBox.size() < 3) {
            weaponBox.add(weapon);
            return true;
        }
        return false;
    }

    /*
        removeWeapon removes a weapon from the weaponBox container.
        A check to weaponDeck should be added (controller side)
    */
    public Boolean removeWeapon(Weapon weapon){
        if(weaponBox.contains(weapon)) {
            weaponBox.remove(weapon);
            return true;
        }
        else{
            return false;
        }
    }

    /*
        listAvailableWeapons gives a list of all the available weapons in the weaponBox.
        Should be implemented better:
            take the name of the single weapon and put it into a simple linked list of strings
            then return the list
    */
    public String listAvailableWeapons(){
        //TODO better implemetation
        return weaponBox.toString();
    }

}
