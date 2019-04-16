package model.weapons;


import java.util.*;


public class Weapon {

    //attributes
    private WeaponName name;
    private Color color;
    private ArrayList<Color> cost;
    private WeaponAmmoStatus ammoStatus;

    private ArrayList<Effect> basicEffect;
    private ArrayList<Effect> optionalEffectOne;
    private ArrayList<Effect> optionalEffectTwo;
    private ArrayList<Effect> alternateEffect;



    //constructor
    public Weapon() {
        this.basicEffect = new ArrayList<>();
        this.optionalEffectOne = new ArrayList<>();
        this.optionalEffectTwo = new ArrayList<>();
        this.alternateEffect = new ArrayList<>();
        this.cost = new ArrayList<>();
        this.ammoStatus = WeaponAmmoStatus.PARTIALLYLOADED;
    }


    //other methods
    public WeaponName getName() {
        return this.name;
    }

    public void setName(WeaponName name) {
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

    public void setCost(Color one){
        this.cost.add(one);
    }

    public void setCost(Color one, Color two){
        this.cost.add(one);
        this.cost.add(two);
    }

    public void setCost(Color one, Color two, Color three){
        this.cost.add(one);
        this.cost.add(two);
        this.cost.add(three);
    }

    public WeaponAmmoStatus getAmmoStatus() {
        return ammoStatus;
    }

    public void setAmmoStatus(WeaponAmmoStatus ammoStatus) {
        this.ammoStatus = ammoStatus;
    }

    public void setBasicEffect(Effect a, Effect b, Effect c, Effect d) {
        this.basicEffect.add(a);
        this.basicEffect.add(b);
        this.basicEffect.add(c);
        this.basicEffect.add(d);
    }

    //inserisci metodi per settare gli effetti oppure cerca di capire se posso direttamente chiamare
    //i metodi degli effetti quando istanzio le armi in Deck

}