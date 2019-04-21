package model.weapons;

import java.util.ArrayList;
import java.util.Collections;

//it's a singleton, how do I declare it?  Fix it in the next days

public class WeaponDeck {
    private ArrayList<Weapon> weaponDeck;

    //constructor
    public WeaponDeck(){
        this.weaponDeck = new ArrayList<>();

        //LOCK RIFLE
        weaponDeck.add(new Weapon());
        weaponDeck.get(0).setName(WeaponName.LOCK_RIFLE);
        weaponDeck.get(0).setColor(Color.BLUE);
        weaponDeck.get(0).setCost(Color.BLUE, Color.BLUE);
        weaponDeck.get(0).setCostOpt1(Color.RED);
        weaponDeck.get(0).setCostOpt2(null);
        weaponDeck.get(0).setCostAlternate(null);
        weaponDeck.get(0).setBasicEffect(new EffectDamage(2, 1, true, 0), new EffectMark(1, 1, true, 0));  //same target
        weaponDeck.get(0).setOptionalEffectOne(new EffectMark(1, 1, true, 0));
        weaponDeck.get(0).setOptionalEffectTwo(null);
        weaponDeck.get(0).setAlternateEffect(null);


        //ELECTROSCYTHE
        weaponDeck.add(new Weapon());
        weaponDeck.get(1).setName(WeaponName.ELECTROSCYTHE);
        weaponDeck.get(1).setColor(Color.BLUE);
        weaponDeck.get(1).setCost(Color.BLUE);
        weaponDeck.get(1).setCostOpt1(null);
        weaponDeck.get(1).setCostOpt2(null);
        weaponDeck.get(1).setCostAlternate(Color.BLUE, Color.RED);
        weaponDeck.get(1).setBasicEffect(new EffectDamage(1, -1, true, -1));
        weaponDeck.get(1).setOptionalEffectOne(null);
        weaponDeck.get(1).setOptionalEffectTwo(null);
        weaponDeck.get(1).setAlternateEffect(new EffectDamage(2, -1, true, -1));


        //MACHINE GUN
        weaponDeck.add(new Weapon());
        weaponDeck.get(2).setName(WeaponName.MACHINE_GUN);
        weaponDeck.get(2).setColor(Color.BLUE);
        weaponDeck.get(2).setCost(Color.BLUE, Color.RED);
        weaponDeck.get(2).setCostOpt1(Color.YELLOW);
        weaponDeck.get(2).setCostOpt2(Color.BLUE);
        weaponDeck.get(2).setCostAlternate(null);
        weaponDeck.get(2).setBasicEffect(new EffectDamage(1, 1, true, 0), new EffectDamage(1, 1, true, 0));
        weaponDeck.get(2).setOptionalEffectOne(new EffectDamage(1, 1, true, 0));
        weaponDeck.get(2).setOptionalEffectTwo(new EffectDamage(1, 1, true, 0), new EffectDamage(1, 1, true, 0));
        weaponDeck.get(2).setAlternateEffect(null);
        /*note: the damage of OptionalEffectOne must be the same of the first target of BasicEffect, the same for the first effect of OptionalEffectTwo;
              moreover, the second effect of OptionalEffectTwo must target a completely different player, remember this in Controller
        */


        //TRaCTOR BEAM
        weaponDeck.add(new Weapon());
        weaponDeck.get(3).setName(WeaponName.TRACTOR_BEAM);
        weaponDeck.get(3).setColor(Color.BLUE);
        weaponDeck.get(3).setCost(Color.BLUE);
        weaponDeck.get(3).setCostOpt1(null);
        weaponDeck.get(3).setCostOpt2(null);
        weaponDeck.get(3).setCostAlternate(Color.RED, Color.YELLOW);
        weaponDeck.get(3).setBasicEffect(new EffectMoveTarget(2, 1, false, 0), new EffectDamage(1, 1, true, 0));  //same target,  after the movement must be visible
        weaponDeck.get(3).setOptionalEffectOne(null);
        weaponDeck.get(3).setOptionalEffectTwo(null);
        weaponDeck.get(3).setAlternateEffect(new EffectMoveTarget(2, 1, true, 0), new EffectDamage(3, 1, true, -1));  //same target, remember to check if the moved target is in your square


        //T.H.O.R.
        weaponDeck.add(new Weapon());
        weaponDeck.get(4).setName(WeaponName.THOR);
        weaponDeck.get(4).setColor(Color.BLUE);
        weaponDeck.get(4).setCost(Color.BLUE, Color.RED);
        weaponDeck.get(4).setCostOpt1(Color.BLUE);
        weaponDeck.get(4).setCostOpt2(Color.BLUE);
        weaponDeck.get(4).setCostAlternate(null);
        weaponDeck.get(4).setBasicEffect(new EffectDamage(2, 1, true, 0));
        weaponDeck.get(4).setOptionalEffectOne(new EffectDamage(1, 1, true, 0));  //remember to change the striker with the defender to create the chain
        weaponDeck.get(4).setOptionalEffectTwo(new EffectDamage(2, 1, true, 0));  //visible is set true so because I assume to shift the reference to the striker
        weaponDeck.get(4).setAlternateEffect(null);
        //remember that Opt2 is allowed only if Opt1 has been used


        //PLASMA GUN
        weaponDeck.add(new Weapon());
        weaponDeck.get(5).setName(WeaponName.PLASMA_GUN);
        weaponDeck.get(5).setColor(Color.BLUE);
        weaponDeck.get(5).setCost(Color.BLUE, Color.YELLOW);
        weaponDeck.get(5).setCostOpt1(null);
        weaponDeck.get(5).setCostOpt2(Color.BLUE);
        weaponDeck.get(5).setCostAlternate(null);
        weaponDeck.get(5).setBasicEffect(new EffectDamage(2, 1, true, 0));
        weaponDeck.get(5).setOptionalEffectOne(new EffectMoveYourself(2));
        weaponDeck.get(5).setOptionalEffectTwo(new EffectDamage(1, 1, true, 0));  //same target
        weaponDeck.get(5).setAlternateEffect(null);

        //WHISPER
        weaponDeck.add(new Weapon());
        weaponDeck.get(6).setName(WeaponName.WHISPER);
        weaponDeck.get(6).setColor(Color.BLUE);
        weaponDeck.get(6).setCost(Color.BLUE, Color.BLUE, Color.YELLOW);
        weaponDeck.get(6).setCostOpt1(null);
        weaponDeck.get(6).setCostOpt2(null);
        weaponDeck.get(6).setCostAlternate(null);
        weaponDeck.get(6).setBasicEffect(new EffectDamage(3, 1, true, 2), new EffectMark(1, 1, true, 2));  //same target
        weaponDeck.get(6).setOptionalEffectOne(null);
        weaponDeck.get(6).setOptionalEffectTwo(null);
        weaponDeck.get(6).setAlternateEffect(null);

    }


    public boolean isInDeck(Weapon weapon) {
        return weaponDeck.contains(weapon);
    }

    public void shuffleDeck() {
        Collections.shuffle(this.weaponDeck);
    }


    //TODO
}
