package model.weapons;


import java.util.*;


public abstract class Weapon {

    //attributes
    private String name;
    private Color color;
    private ArrayList<Color> cost;
    private WeaponAmmoStatus ammoStatus;
    private int basicDamage;
    private int basicMarks;
    private int involvedPlayersPrimary;  //number of target players (-1 means "not limited")
    private int minShootingDistance;
    private boolean visibleTarget;


    //constructor
    public Weapon(){
        this.cost = new ArrayList<>();
        this.ammoStatus = WeaponAmmoStatus.PARTIALLYLOADED;
    }

    //other methods
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Color> getCost() {
        return this.cost;
    }

    public abstract void setCost(Color one, Color two, Color three);

    public int getBasicDamage() {
        return this.basicDamage;
    }

    void setBasicDamage(int basicDamage) {
        this.basicDamage = basicDamage;
    }

    public int getBasicMarks() {
        return basicMarks;
    }

    void setBasicMarks(int basicMarks) {
        this.basicMarks = basicMarks;
    }

    public int getInvolvedPlayers() {
        return involvedPlayersPrimary;
    }

    public void setInvolvedPlayersPrimary(int involvedPlayersPrimary) {
        this.involvedPlayersPrimary = involvedPlayersPrimary;
    }

    public WeaponAmmoStatus getAmmoStatus() {
        return ammoStatus;
    }

    public void setAmmoStatus(WeaponAmmoStatus ammoStatus) {
        this.ammoStatus = ammoStatus;
    }

    public int getMinShootingDistance() {
        return minShootingDistance;
    }

    void setMinShootingDistance(int minShootingDistance) {
        this.minShootingDistance = minShootingDistance;
    }

    void setVisibleTarget(boolean visibleTarget) {
        this.visibleTarget = visibleTarget;
    }

    public boolean isVisibleTarget() {
        return visibleTarget;
    }
}
