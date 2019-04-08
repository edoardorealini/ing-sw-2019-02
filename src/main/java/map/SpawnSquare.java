package map;
import java.util.*;

import weapons.*;

public class SpawnSquare extends Square {
    private ArrayList<Weapon> weaponBox; //array che contiene i ref alle armi presenti nel box

    public SpawnSquare(Color color, Boolean activeStatus, Directions[] allowedMoves, Directions[] doors){
        super(color, activeStatus, allowedMoves, doors);
    }

    public SpawnSquare(Boolean activeStatus){
        super(activeStatus);
    }

    public List<Weapon> getWeaponBox() {
        return weaponBox;
    }

    public Boolean addWeapon(Weapon weapon){
        if(weaponBox.size() < 3) {
            weaponBox.add(weapon);
            return true;
        }
        return false;
    }

    public Boolean removeWeapon(Weapon weapon){
        if(weaponBox.contains(weapon)) {
            weaponBox.remove(weapon);
            return true;
        }
        else{
            return false;
        }
    }
    public String listAvailableWeapons(){
        return weaponBox.toString();
    }

}
