package model.weapons;

import model.Color;
import model.ShootMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the general weapon. It contains all the possible fields that describe a weapon (name, color, cost, cost of the effects,
 * the status of the weapon and the required parameters necessary to be used properly).
 * It also has four ArrayList of effects ({@link EffectDamage}, {@link EffectMark}, {@link EffectMoveYourself}, {@link EffectMoveTarget}) which,
 * combined together, constitute the possible ShootingMode ({@link ShootMode}).
 * @author Riccardo
 */

public class Weapon implements Serializable {

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

    public List<Color> getCost() {
        return this.cost;
    }

    public List<Color> getCostOpt1() {
        return costOpt1;
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

    void setCostOpt2(Color one){
        this.costOpt2.add(one);
    }

    //overloading setCostAlternate

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

    //overloading setOptionalModeOne

    void setOptionalModeOne(Effect a) {
        this.optionalModeOne.add(a);
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

    /**
     * It returns the toString implementation.
     * @return the name of the weapon
     */

    @Override
    public String toString() {
        String nameWeapon;
        nameWeapon = this.name.toString();
        return nameWeapon;
    }

    /**
     * This method returns the effects which build the specific ShootMode given in input.
     * @param mode is the enum that indicates the ShootMode the user is asking more about.
     * @return an ArraList<{@link Effect}>
     */

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