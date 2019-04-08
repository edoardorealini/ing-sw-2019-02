package weapons;


import java.util.*;

public abstract class Weapon {
    private String name;
    private Color color;
    private ArrayList<Color> cost;
    private WeaponAmmoStatus ammoStatus;
    private int basicDamage;
    private int basicTargets;
    private int involvedPlayers;

    public Weapon(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Color> getCost() {
        return cost;
    }

    public void setCost(Color one, Color two, Color three) {
        this.cost = new ArrayList<Color>()   //continue
        this.getCost().add(one);
        this.getCost().add(two);
        this.getCost().add(three);
    }

    public int getBasicDamage() {
        return basicDamage;
    }

    public void setBasicDamage(int basicDamage) {
        this.basicDamage = basicDamage;
    }

    public int getBasicTargets() {
        return basicTargets;
    }

    public void setBasicTargets(int basicTargets) {
        this.basicTargets = basicTargets;
    }

    public int getInvolvedPlayers() {
        return involvedPlayers;
    }

    public WeaponAmmoStatus getAmmoStatus() {
        return ammoStatus;
    }

    public void setAmmoStatus(WeaponAmmoStatus ammoStatus) {
        this.ammoStatus = ammoStatus;
    }
}
