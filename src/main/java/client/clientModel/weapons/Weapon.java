package client.clientModel.weapons;

import client.clientModel.Color;

import java.util.ArrayList;
import java.util.List;


public class Weapon {

    //attributes
    private WeaponName name;
    private Color color;
    private ArrayList<Color> cost;
    private ArrayList<Color> costOpt1;
    private ArrayList<Color> costOpt2;
    private ArrayList<Color> costAlternate;
    private WeaponAmmoStatus ammoStatus;

    private ArrayList<Effect> basicMode;
    private ArrayList<Effect> optionalModeOne;
    private ArrayList<Effect> optionalModeTwo;
    private ArrayList<Effect> alternateMode;



    //constructor
    public Weapon() {
        this.basicMode = new ArrayList<>();
        this.optionalModeOne = new ArrayList<>();
        this.optionalModeTwo = new ArrayList<>();
        this.alternateMode = new ArrayList<>();
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

    public List<Color> getCost() { return this.cost; }

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

    //overloading setBasicMode

    public void setBasicMode(Effect a) {
        this.basicMode.add(a);
    }

    public void setBasicMode(Effect a, Effect b) {
        this.basicMode.add(a);
        this.basicMode.add(b);
    }

    public void setBasicMode(Effect a, Effect b, Effect c) {
        this.basicMode.add(a);
        this.basicMode.add(b);
        this.basicMode.add(c);
    }

    public void setBasicMode(Effect a, Effect b, Effect c, Effect d) {
        this.basicMode.add(a);
        this.basicMode.add(b);
        this.basicMode.add(c);
        this.basicMode.add(d);
    }

    //overloading setOptionalModeOne

    public void setOptionalModeOne(Effect a) {
        this.optionalModeOne.add(a);
    }

    public void setOptionalModeOne(Effect a, Effect b) {
        this.optionalModeOne.add(a);
        this.optionalModeOne.add(b);
    }

    public void setOptionalModeOne(Effect a, Effect b, Effect c) {
        this.optionalModeOne.add(a);
        this.optionalModeOne.add(b);
        this.optionalModeOne.add(c);
    }

    public void setOptionalModeOne(Effect a, Effect b, Effect c, Effect d) {
        this.optionalModeOne.add(a);
        this.optionalModeOne.add(b);
        this.optionalModeOne.add(c);
        this.optionalModeOne.add(d);
    }

    //overloading setOptionalModeTwo

    public void setOptionalModeTwo(Effect a) {
        this.optionalModeTwo.add(a);
    }

    public void setOptionalModeTwo(Effect a, Effect b) {
        this.optionalModeTwo.add(a);
        this.optionalModeTwo.add(b);
    }

    public void setOptionalModeTwo(Effect a, Effect b, Effect c) {
        this.optionalModeTwo.add(a);
        this.optionalModeTwo.add(b);
        this.optionalModeTwo.add(c);
    }

    //overloading setAlternateMode

    public void setAlternateMode(Effect a) {
        this.alternateMode.add(a);
    }

    public void setAlternateMode(Effect a, Effect b) {
        this.alternateMode.add(a);
        this.alternateMode.add(b);
    }

    public void setAlternateMode(Effect a, Effect b, Effect c) {
        this.alternateMode.add(a);
        this.alternateMode.add(b);
        this.alternateMode.add(c);
    }

    public void setAlternateMode(Effect a, Effect b, Effect c, Effect d) {
        this.alternateMode.add(a);
        this.alternateMode.add(b);
        this.alternateMode.add(c);
        this.alternateMode.add(d);
    }

    @Override
    public String toString() {
        StringBuilder nameWeap = new StringBuilder();
        nameWeap.append(this.name);
        return nameWeap.toString();
    }

	public List<Effect> getBasicMode() {
		return basicMode;
	}

	public List<Effect> getOptionalModeOne() {
		return optionalModeOne;
	}

	public List<Effect> getOptionalModeTwo() {
		return optionalModeTwo;
	}

	public List<Effect> getAlternateMode() {
		return alternateMode;
	}

}