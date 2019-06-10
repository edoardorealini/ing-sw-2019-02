package model.weapons;
import model.ShootMode;
import  model.Color;

import java.io.Serializable;
import java.util.*;


public class Weapon  implements Serializable {

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

    private RequiredParameters requiredParameters;


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
        this.requiredParameters = new RequiredParameters();
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


    public List<Color> getModeCost(ShootMode mode) {
        switch (mode) {
            case BASIC:
                return Collections.emptyList();
            case OPTIONAL1:
                return costOpt1;
            case OPTIONAL2:
                return costOpt2;
            case ALTERNATE:
                return costAlternate;
        }
        return Collections.emptyList();
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



    void setCost(Color one){
        this.cost.add(one);
    }

    void setCost(Color one, Color two){
        this.cost.add(one);
        this.cost.add(two);
    }

    void setCost(Color one, Color two, Color three){
        this.cost.add(one);
        this.cost.add(two);
        this.cost.add(three);
    }

    void setCostOpt1(Color one){
        this.costOpt1.add(one);
    }

    void setCostOpt1(Color one, Color two){
        this.costOpt1.add(one);
        this.costOpt1.add(two);
    }

    void setCostOpt2(Color one){
        this.costOpt2.add(one);
    }

    void setCostOpt2(Color one, Color two){
        this.costOpt2.add(one);
        this.costOpt2.add(two);
    }

    void setCostAlternate(Color one){
        this.costAlternate.add(one);
    }

    void setCostAlternate(Color one, Color two){
        this.costAlternate.add(one);
        this.costAlternate.add(two);
    }



    public WeaponAmmoStatus getWeaponStatus() {
        return ammoStatus;
    }

    public void setWeaponStatus(WeaponAmmoStatus ammoStatus) {
        this.ammoStatus = ammoStatus;
    }


    //overloading setBasicMode

    void setBasicMode(Effect a) {
        this.basicMode.add(a);
    }

    void setBasicMode(Effect a, Effect b) {
        this.basicMode.add(a);
        this.basicMode.add(b);
    }

    void setBasicMode(Effect a, Effect b, Effect c) {
        this.basicMode.add(a);
        this.basicMode.add(b);
        this.basicMode.add(c);
    }

    void setBasicMode(Effect a, Effect b, Effect c, Effect d) {
        this.basicMode.add(a);
        this.basicMode.add(b);
        this.basicMode.add(c);
        this.basicMode.add(d);
    }


    //overloading setOptionalModeOne

    void setOptionalModeOne(Effect a) {
        this.optionalModeOne.add(a);
    }

    void setOptionalModeOne(Effect a, Effect b) {
        this.optionalModeOne.add(a);
        this.optionalModeOne.add(b);
    }

    void setOptionalModeOne(Effect a, Effect b, Effect c) {
        this.optionalModeOne.add(a);
        this.optionalModeOne.add(b);
        this.optionalModeOne.add(c);
    }

    void setOptionalModeOne(Effect a, Effect b, Effect c, Effect d) {
        this.optionalModeOne.add(a);
        this.optionalModeOne.add(b);
        this.optionalModeOne.add(c);
        this.optionalModeOne.add(d);
    }


    //overloading setOptionalModeTwo

    void setOptionalModeTwo(Effect a) {
        this.optionalModeTwo.add(a);
    }

    void setOptionalModeTwo(Effect a, Effect b) {
        this.optionalModeTwo.add(a);
        this.optionalModeTwo.add(b);
    }

    void setOptionalModeTwo(Effect a, Effect b, Effect c) {
        this.optionalModeTwo.add(a);
        this.optionalModeTwo.add(b);
        this.optionalModeTwo.add(c);
    }


    //overloading setAlternateMode

    void setAlternateMode(Effect a) {
        this.alternateMode.add(a);
    }

    void setAlternateMode(Effect a, Effect b) {
        this.alternateMode.add(a);
        this.alternateMode.add(b);
    }

    void setAlternateMode(Effect a, Effect b, Effect c) {
        this.alternateMode.add(a);
        this.alternateMode.add(b);
        this.alternateMode.add(c);
    }

    void setAlternateMode(Effect a, Effect b, Effect c, Effect d) {
        this.alternateMode.add(a);
        this.alternateMode.add(b);
        this.alternateMode.add(c);
        this.alternateMode.add(d);
    }


    @Override
    public String toString() {
        String nameWeapon;
        nameWeapon = this.name.toString();
        return nameWeapon;
    }

    public List<Effect> getMode(ShootMode mode) {
        switch (mode) {
            case BASIC:
                return basicMode;
            case OPTIONAL1:
                return optionalModeOne;
            case OPTIONAL2:
                return optionalModeTwo;
            case ALTERNATE:
                return alternateMode;
        }
        return Collections.emptyList();
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

	void setRequiredParameters(int shootModeType, int numberOfTargets, int numberOfSquares, boolean direction, boolean makeDamageBeforeMove) {
        this.requiredParameters.setShootModeType(shootModeType);
        this.requiredParameters.setNumberOfTargets(numberOfTargets);
        this.requiredParameters.setNumberOfSquares(numberOfSquares);
        this.requiredParameters.setDirection(direction);
        this.requiredParameters.setMakeDamageBeforeMove(makeDamageBeforeMove);
    }

    public RequiredParameters getRequiredParameters() {
        return requiredParameters;
    }
}