package model.weapons;


import java.util.*;


public class Weapon {

    //attributes
    private WeaponName name;
    private Color color;
    private ArrayList<Color> cost;
    private ArrayList<Color> costOpt1;
    private ArrayList<Color> costOpt2;
    private ArrayList<Color> costAlternate;
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
        this.costOpt1 = new ArrayList<>();
        this.costOpt2 = new ArrayList<>();
        this.costAlternate = new ArrayList<>();
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

    public List<Color> getCostOpt1() {
        return costOpt1;
    }

    public List<Color> getCostOpt2() {
        return costOpt2;
    }

    public List<Color> getCostAlternate() {
        return costAlternate;
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

    public void setCostOpt1(Color one){
        this.costOpt1.add(one);
    }

    public void setCostOpt1(Color one, Color two){
        this.costOpt1.add(one);
        this.costOpt1.add(two);
    }

    public void setCostOpt2(Color one){
        this.costOpt2.add(one);
    }

    public void setCostOpt2(Color one, Color two){
        this.costOpt2.add(one);
        this.costOpt2.add(two);
    }

    public void setCostAlternate(Color one){
        this.costAlternate.add(one);
    }

    public void setCostAlternate(Color one, Color two){
        this.costAlternate.add(one);
        this.costAlternate.add(two);
    }

    public WeaponAmmoStatus getAmmoStatus() {
        return ammoStatus;
    }

    public void setAmmoStatus(WeaponAmmoStatus ammoStatus) {
        this.ammoStatus = ammoStatus;
    }

    //overloading setBasicEffect

    public void setBasicEffect(Effect a) {
        this.basicEffect.add(a);
    }

    public void setBasicEffect(Effect a, Effect b) {
        this.basicEffect.add(a);
        this.basicEffect.add(b);
    }

    public void setBasicEffect(Effect a, Effect b, Effect c) {
        this.basicEffect.add(a);
        this.basicEffect.add(b);
        this.basicEffect.add(c);
    }

    public void setBasicEffect(Effect a, Effect b, Effect c, Effect d) {
        this.basicEffect.add(a);
        this.basicEffect.add(b);
        this.basicEffect.add(c);
        this.basicEffect.add(d);
    }

    //overloading setOptionalEffectOne

    public void setOptionalEffectOne(Effect a) {
        this.optionalEffectOne.add(a);
    }

    public void setOptionalEffectOne(Effect a, Effect b) {
        this.optionalEffectOne.add(a);
        this.optionalEffectOne.add(b);
    }

    public void setOptionalEffectOne(Effect a, Effect b, Effect c) {
        this.optionalEffectOne.add(a);
        this.optionalEffectOne.add(b);
        this.optionalEffectOne.add(c);
    }

    //overloading setOptionalEffectTwo

    public void setOptionalEffectTwo(Effect a) {
        this.optionalEffectTwo.add(a);
    }

    public void setOptionalEffectTwo(Effect a, Effect b) {
        this.optionalEffectTwo.add(a);
        this.optionalEffectTwo.add(b);
    }

    public void setOptionalEffectTwo(Effect a, Effect b, Effect c) {
        this.optionalEffectTwo.add(a);
        this.optionalEffectTwo.add(b);
        this.optionalEffectTwo.add(c);
    }

    //overloading setAlternateEffect

    public void setAlternateEffect(Effect a) {
        this.alternateEffect.add(a);
    }

    public void setAlternateEffect(Effect a, Effect b) {
        this.alternateEffect.add(a);
        this.alternateEffect.add(b);
    }

    public void setAlternateEffect(Effect a, Effect b, Effect c) {
        this.alternateEffect.add(a);
        this.alternateEffect.add(b);
        this.alternateEffect.add(c);
    }



    //inserisci metodi per settare gli effetti oppure cerca di capire se posso direttamente chiamare
    //i metodi degli effetti quando istanzio le armi in Deck

}