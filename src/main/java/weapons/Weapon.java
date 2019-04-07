package weapons;


import java.util.*;

public abstract class Weapon {
    private String name;
    private Color color;
    private ArrayList<Color> cost;
    private WeaponAmmoStatus ammoStatus;
    private int damage;
    private int involvedPlayers;

    public Weapon(){
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public List<Color> getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getInvolvedPlayers() {
        return involvedPlayers;
    }

    public WeaponAmmoStatus getAmmoStatus() {
        return ammoStatus;
    }
}
