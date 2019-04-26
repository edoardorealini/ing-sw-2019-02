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


        //VORTEX CANNON
        weaponDeck.add(new Weapon());
        weaponDeck.get(7).setName(WeaponName.VORTEX_CANNON);
        weaponDeck.get(7).setColor(Color.RED);
        weaponDeck.get(7).setCost(Color.RED, Color.BLUE);
        weaponDeck.get(7).setCostOpt1(Color.RED);
        weaponDeck.get(7).setCostOpt2(null);
        weaponDeck.get(7).setCostAlternate(null);
        weaponDeck.get(7).setBasicEffect(new EffectMoveTarget(1, 1, false, 0), new EffectDamage(2, 1, true, 1));  //same target
        weaponDeck.get(7).setOptionalEffectOne(new EffectMoveTarget(1, 2, false, 0), new EffectDamage(1, 2, true, 1));  //same targets
        weaponDeck.get(7).setOptionalEffectTwo(null);
        weaponDeck.get(7).setAlternateEffect(null);


        //FURNACE
        weaponDeck.add(new Weapon());
        weaponDeck.get(8).setName(WeaponName.FURNACE);
        weaponDeck.get(8).setColor(Color.RED);
        weaponDeck.get(8).setCost(Color.RED, Color.BLUE);
        weaponDeck.get(8).setCostOpt1(null);
        weaponDeck.get(8).setCostOpt2(null);
        weaponDeck.get(8).setCostAlternate(null);
        weaponDeck.get(8).setBasicEffect(new EffectDamage(1, -1, true, 1));
        weaponDeck.get(8).setOptionalEffectOne(null);
        weaponDeck.get(8).setOptionalEffectTwo(null);
        weaponDeck.get(8).setAlternateEffect(new EffectDamage(1, -1, true, 1), new EffectMark(1, -1, true, 1));  //same targets


        //HEATSEEKER
        weaponDeck.add(new Weapon());
        weaponDeck.get(9).setName(WeaponName.HEATSEEKER);
        weaponDeck.get(9).setColor(Color.RED);
        weaponDeck.get(9).setCost(Color.RED, Color.RED, Color.YELLOW);
        weaponDeck.get(9).setCostOpt1(null);
        weaponDeck.get(9).setCostOpt2(null);
        weaponDeck.get(9).setCostAlternate(null);
        weaponDeck.get(9).setBasicEffect(new EffectDamage(3, 1, false, 2));  //only non visible targets
        weaponDeck.get(9).setOptionalEffectOne(null);
        weaponDeck.get(9).setOptionalEffectTwo(null);
        weaponDeck.get(9).setAlternateEffect(null);


        //HELLION
        weaponDeck.add(new Weapon());
        weaponDeck.get(10).setName(WeaponName.HELLION);
        weaponDeck.get(10).setColor(Color.RED);
        weaponDeck.get(10).setCost(Color.RED, Color.YELLOW);
        weaponDeck.get(10).setCostOpt1(null);
        weaponDeck.get(10).setCostOpt2(null);
        weaponDeck.get(10).setCostAlternate(Color.RED);
        weaponDeck.get(10).setBasicEffect(new EffectDamage(1, 1, true, 1), new EffectMark(1, -1, true, 1)); //same square, mark every player
        weaponDeck.get(10).setOptionalEffectOne(null);
        weaponDeck.get(10).setOptionalEffectTwo(null);
        weaponDeck.get(10).setAlternateEffect(new EffectDamage(1, 1, true, 1), new EffectMark(1, -1, true, 1)); //same square, mark every player


        //FLAMETHROWER
        weaponDeck.add(new Weapon());
        weaponDeck.get(11).setName(WeaponName.FLAMETHROWER);
        weaponDeck.get(11).setColor(Color.RED);
        weaponDeck.get(11).setCost(Color.RED);
        weaponDeck.get(11).setCostOpt1(null);
        weaponDeck.get(11).setCostOpt2(null);
        weaponDeck.get(11).setCostAlternate(Color.YELLOW, Color.YELLOW);
        weaponDeck.get(11).setBasicEffect(new EffectDamage(1, 1, true, 1), new EffectDamage(1, 1, false, 2));  //same direction
        weaponDeck.get(11).setOptionalEffectOne(null);
        weaponDeck.get(11).setOptionalEffectTwo(null);
        weaponDeck.get(11).setAlternateEffect(new EffectDamage(2, -1, true, 1),new EffectDamage(1, -1, false, 2));  //same direction


        //GRENADE LAUNCHER
        weaponDeck.add(new Weapon());
        weaponDeck.get(12).setName(WeaponName.GRENADE_LAUNCHER);
        weaponDeck.get(12).setColor(Color.RED);
        weaponDeck.get(12).setCost(Color.RED);
        weaponDeck.get(12).setCostOpt1(Color.RED);
        weaponDeck.get(12).setCostOpt2(null);
        weaponDeck.get(12).setCostAlternate(null);
        weaponDeck.get(12).setBasicEffect(new EffectDamage(1, 1, true, 0), new EffectMoveTarget(1, 1, true, 0));  //same target
        weaponDeck.get(12).setOptionalEffectOne(new EffectDamage(1, -1, true, 0));  //can be used before or after the EffectMove of basic mode
        weaponDeck.get(12).setOptionalEffectTwo(null);
        weaponDeck.get(12).setAlternateEffect(null);


        //ROCKET LAUNCHER
        weaponDeck.add(new Weapon());
        weaponDeck.get(13).setName(WeaponName.ROCKET_LAUNCHER);
        weaponDeck.get(13).setColor(Color.RED);
        weaponDeck.get(13).setCost(Color.RED, Color.RED);
        weaponDeck.get(13).setCostOpt1(Color.BLUE);
        weaponDeck.get(13).setCostOpt2(Color.YELLOW);
        weaponDeck.get(13).setCostAlternate(null);
        weaponDeck.get(13).setBasicEffect(new EffectDamage(2, 1, true, 1), new EffectMoveTarget(1, 1, true, 1));  //same target
        weaponDeck.get(13).setOptionalEffectOne(new EffectMoveYourself(2));
        weaponDeck.get(13).setOptionalEffectTwo(new EffectDamage(1, -1, false, 1));  //same square as EffectDamage of BasicEffect
        weaponDeck.get(13).setAlternateEffect(null);


        //RAILGUN
        weaponDeck.add(new Weapon());
        weaponDeck.get(14).setName(WeaponName.RAILGUN);
        weaponDeck.get(14).setColor(Color.YELLOW);
        weaponDeck.get(14).setCost(Color.YELLOW, Color.YELLOW, Color.BLUE);
        weaponDeck.get(14).setCostOpt1(null);
        weaponDeck.get(14).setCostOpt2(null);
        weaponDeck.get(14).setCostAlternate(null);
        weaponDeck.get(14).setBasicEffect(new EffectDamage(3, 1, false, 0));  //cardinal direction
        weaponDeck.get(14).setOptionalEffectOne(null);
        weaponDeck.get(14).setOptionalEffectTwo(null);
        weaponDeck.get(14).setAlternateEffect(new EffectDamage(2, 2, false, 0));  //same direction, remember you can always split them into two new


        //CYBERBLADE
        weaponDeck.add(new Weapon());
        weaponDeck.get(15).setName(WeaponName.CYBERBLADE);
        weaponDeck.get(15).setColor(Color.YELLOW);
        weaponDeck.get(15).setCost(Color.YELLOW, Color.RED);
        weaponDeck.get(15).setCostOpt1(null);
        weaponDeck.get(15).setCostOpt2(Color.YELLOW);
        weaponDeck.get(15).setCostAlternate(null);
        weaponDeck.get(15).setBasicEffect(new EffectDamage(2, 1, true, -1));
        weaponDeck.get(15).setOptionalEffectOne(new EffectMoveYourself(1));   //before or after the basic effect
        weaponDeck.get(15).setOptionalEffectTwo(new EffectDamage(2, 1, true, -1));   //different target from basic effect
        weaponDeck.get(15).setAlternateEffect(null);


        //ZX-2
        weaponDeck.add(new Weapon());
        weaponDeck.get(16).setName(WeaponName.ZX_2);
        weaponDeck.get(16).setColor(Color.YELLOW);
        weaponDeck.get(16).setCost(Color.YELLOW, Color.RED);
        weaponDeck.get(16).setCostOpt1(null);
        weaponDeck.get(16).setCostOpt2(null);
        weaponDeck.get(16).setCostAlternate(null);
        weaponDeck.get(16).setBasicEffect(new EffectDamage(1, 1, true, 0), new EffectMark(2, 1, true, 0));  //same target
        weaponDeck.get(16).setOptionalEffectOne(null);
        weaponDeck.get(16).setOptionalEffectTwo(null);
        weaponDeck.get(16).setAlternateEffect(new EffectMark(1, 3, true, 0));


        //SHOTGUN
        weaponDeck.add(new Weapon());
        weaponDeck.get(17).setName(WeaponName.SHOTGUN);
        weaponDeck.get(17).setColor(Color.YELLOW);
        weaponDeck.get(17).setCost(Color.YELLOW, Color.YELLOW);
        weaponDeck.get(17).setCostOpt1(null);
        weaponDeck.get(17).setCostOpt2(null);
        weaponDeck.get(17).setCostAlternate(null);
        weaponDeck.get(17).setBasicEffect(new EffectDamage(3, 1, true, -1), new EffectMoveTarget(1, 1, true, -1));  //same target
        weaponDeck.get(17).setOptionalEffectOne(null);
        weaponDeck.get(17).setOptionalEffectTwo(null);
        weaponDeck.get(17).setAlternateEffect(new EffectDamage(2, 1, true, 1));   //exactly one move away


        //POWER GLOVE
        weaponDeck.add(new Weapon());
        weaponDeck.get(18).setName(WeaponName.POWER_GLOVE);
        weaponDeck.get(18).setColor(Color.YELLOW);
        weaponDeck.get(18).setCost(Color.YELLOW, Color.BLUE);
        weaponDeck.get(18).setCostOpt1(null);
        weaponDeck.get(18).setCostOpt2(null);
        weaponDeck.get(18).setCostAlternate(Color.BLUE);
        weaponDeck.get(18).setBasicEffect(new EffectMoveYourself(1), new EffectDamage(1, 1, true, -1), new EffectMark(2, 1, true, -1));  //same target
        weaponDeck.get(18).setOptionalEffectOne(null);
        weaponDeck.get(18).setOptionalEffectTwo(null);
        weaponDeck.get(18).setAlternateEffect(new EffectMoveYourself(1), new EffectDamage(2, 1, true, -1), new EffectMoveYourself(1), new EffectDamage(2, 1, true, -1));


        //SHOCKWAVE
        weaponDeck.add(new Weapon());
        weaponDeck.get(19).setName(WeaponName.SCHOCKWAVE);
        weaponDeck.get(19).setColor(Color.YELLOW);
        weaponDeck.get(19).setCost(Color.YELLOW);
        weaponDeck.get(19).setCostOpt1(null);
        weaponDeck.get(19).setCostOpt2(null);
        weaponDeck.get(19).setCostAlternate(Color.YELLOW);
        weaponDeck.get(19).setBasicEffect(new EffectDamage(1, 1, true, 1), new EffectDamage(1, 1, true, 1), new EffectDamage(1, 1, true, 1));  //different targets exactly one move away
        weaponDeck.get(19).setOptionalEffectOne(null);
        weaponDeck.get(19).setOptionalEffectTwo(null);
        weaponDeck.get(19).setAlternateEffect(new EffectDamage(1, -1, true, 1));   //exactly one move away


        //SLEDGEHAMMER
        weaponDeck.add(new Weapon());
        weaponDeck.get(20).setName(WeaponName.SLEDGEHAMMER);
        weaponDeck.get(20).setColor(Color.YELLOW);
        weaponDeck.get(20).setCost(Color.YELLOW);
        weaponDeck.get(20).setCostOpt1(null);
        weaponDeck.get(20).setCostOpt2(null);
        weaponDeck.get(20).setCostAlternate(Color.RED);
        weaponDeck.get(20).setBasicEffect(new EffectDamage(2, 1, true, -1));
        weaponDeck.get(20).setOptionalEffectOne(null);
        weaponDeck.get(20).setOptionalEffectTwo(null);
        weaponDeck.get(20).setAlternateEffect(new EffectDamage(3, 11, true, -1), new EffectMoveTarget(2, 1, true,-1)); //same target
    }


    public boolean isInDeck(Weapon weapon) {
        return weaponDeck.contains(weapon);
    }

    public void shuffleDeck() {
        Collections.shuffle(this.weaponDeck);
    }


    //TODO
}
