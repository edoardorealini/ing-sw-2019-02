package model.map;
import model.ammo.*;
import model.weapons.Weapon;
import java.util.*;
import  model.Color;

public class Square {

    private Boolean activeStatus;               //in una mappa possono esserci degli square non attivi.
    private Color color;                        //indica il colore della stanza.
    private ArrayList<Directions> allowedMoves; //Directions of the allowed moves in the single Square.
    private ArrayList<Directions> doors;        //Direction of door (if a direction is present, that is the door).
    private SquareType type;
    private ArrayList<Weapon> weaponBox;        //array che contiene i ref alle armi presenti nel box
    private AmmoCard ammoTile;

    public Square(){
        this.color = null;
        this.activeStatus = null;
        this.allowedMoves = null;
        this.doors = null;
    }

    public ArrayList<Weapon> getAvailableWeapons() {
        return weaponBox;
    }

    public void addWeapon(Weapon weapon){
        if(weaponBox.size() < 3) {
            weaponBox.add(weapon);
        }
    }

    public void removeWeapon(Weapon weapon){
            weaponBox.remove(weapon);
    }

    public AmmoCard getAmmoTile() {
        return ammoTile;
    }

    public void setAmmoTile(AmmoCard ammoTile) {
        this.ammoTile = ammoTile;
    }

    public Boolean isActive() {
        return activeStatus;
    }

    public Color getColor() {
        return color;
    }

    public List<Directions> getAllowedMoves() {
        return allowedMoves;
    }

    public List<Directions> getDoors(){
        return doors;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public void setActiveStatus(Boolean activeStatus){
        this.activeStatus = activeStatus;
    }

    public void setAllowedMoves(ArrayList<Directions> allowedMoves){
        this.allowedMoves = allowedMoves;
    }

    public void setDoors(ArrayList<Directions> doors) {
        this.doors = doors;
    }

    public SquareType getType() {
        return type;
    }
}