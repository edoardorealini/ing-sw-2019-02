package model.weapons;

import java.io.Serializable;
import java.util.*;
import  model.Color;


public class WeaponDeck implements Serializable {
    private LinkedList<Weapon> weaponDeck;

    //constructor
    public WeaponDeck(){
        this.weaponDeck = new LinkedList<>();

        //LOCK RIFLE
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(0).setName(WeaponName.LOCK_RIFLE);
        weaponDeck.get(0).setColor(Color.BLUE);
        weaponDeck.get(0).setCost(Color.BLUE, Color.BLUE);
        weaponDeck.get(0).setCostOpt1(Color.RED);
        weaponDeck.get(0).setCostOpt2(null);
        weaponDeck.get(0).setCostAlternate(null);
        weaponDeck.get(0).setBasicMode(new EffectDamage(2, 1, 0, true, 0), new EffectMark(1, 1, 0, true, 0));  //same target
        weaponDeck.get(0).setOptionalModeOne(new EffectMark(1, 1, 1,true, 0));
        weaponDeck.get(0).setOptionalModeTwo(null);
        weaponDeck.get(0).setAlternateMode(null);
        weaponDeck.get(0).setRequiredParameters(3, 2, 0, false, false);


        //ELECTROSCYTHE
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(1).setName(WeaponName.ELECTROSCYTHE);
        weaponDeck.get(1).setColor(Color.BLUE);
        weaponDeck.get(1).setCost(Color.BLUE);
        weaponDeck.get(1).setCostOpt1(null);
        weaponDeck.get(1).setCostOpt2(null);
        weaponDeck.get(1).setCostAlternate(Color.BLUE, Color.RED);
        weaponDeck.get(1).setBasicMode(new EffectDamage(1, -1, -1, true, -1));
        weaponDeck.get(1).setOptionalModeOne(null);
        weaponDeck.get(1).setOptionalModeTwo(null);
        weaponDeck.get(1).setAlternateMode(new EffectDamage(2, -1, -1, true, -1));
        weaponDeck.get(1).setRequiredParameters(2, 0, 0, false, false);


        //MACHINE GUN
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(2).setName(WeaponName.MACHINE_GUN);
        weaponDeck.get(2).setColor(Color.BLUE);
        weaponDeck.get(2).setCost(Color.BLUE, Color.RED);
        weaponDeck.get(2).setCostOpt1(Color.YELLOW);
        weaponDeck.get(2).setCostOpt2(Color.BLUE);
        weaponDeck.get(2).setCostAlternate(null);
        weaponDeck.get(2).setBasicMode(new EffectDamage(1, 1, 0, true, 0), new EffectDamage(1, 1, 1, true, 0));
        weaponDeck.get(2).setOptionalModeOne(new EffectDamage(1, 1, 0, true, 0));
        weaponDeck.get(2).setOptionalModeTwo(new EffectDamage(1, 1, 1, true, 0), new EffectDamage(1, 1, 2, true, 0));
        weaponDeck.get(2).setAlternateMode(null);
        weaponDeck.get(2).setRequiredParameters(4, 3, 0, false, false);


        //TRACTOR BEAM
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(3).setName(WeaponName.TRACTOR_BEAM);
        weaponDeck.get(3).setColor(Color.BLUE);
        weaponDeck.get(3).setCost(Color.BLUE);
        weaponDeck.get(3).setCostOpt1(null);
        weaponDeck.get(3).setCostOpt2(null);
        weaponDeck.get(3).setCostAlternate(Color.RED, Color.YELLOW);
        weaponDeck.get(3).setBasicMode(new EffectMoveTarget(2, 1, 0, false, 0), new EffectDamage(1, 1, 0, true, 0));  //same target,  after the movement must be visible
        weaponDeck.get(3).setOptionalModeOne(null);
        weaponDeck.get(3).setOptionalModeTwo(null);
        weaponDeck.get(3).setAlternateMode(new EffectMoveTarget(2, 1, 0, false, 0), new EffectDamage(3, 1, 0, true, -1));  //same target, remember to check if the moved target is in your square
        weaponDeck.get(3).setRequiredParameters(2, 1, 1, false, false);


        //T.H.O.R.
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(4).setName(WeaponName.THOR);
        weaponDeck.get(4).setColor(Color.BLUE);
        weaponDeck.get(4).setCost(Color.BLUE, Color.RED);
        weaponDeck.get(4).setCostOpt1(Color.BLUE);
        weaponDeck.get(4).setCostOpt2(Color.BLUE);
        weaponDeck.get(4).setCostAlternate(null);
        weaponDeck.get(4).setBasicMode(new EffectDamage(2, 1, 1, true, 0));
        weaponDeck.get(4).setOptionalModeOne(new EffectDamage(1, 1, 2, true, 0));  //remember to change the striker with the defender to create the chain
        weaponDeck.get(4).setOptionalModeTwo(new EffectDamage(2, 1, 3, true, 0));  //visible is set true so because I assume to shift the reference to the striker
        weaponDeck.get(4).setAlternateMode(null);
        weaponDeck.get(4).setRequiredParameters(4, 3, 0, false, false);
        //remember that Opt2 is allowed only if Opt1 has been used


        //PLASMA GUN
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(5).setName(WeaponName.PLASMA_GUN);
        weaponDeck.get(5).setColor(Color.BLUE);
        weaponDeck.get(5).setCost(Color.BLUE, Color.YELLOW);
        weaponDeck.get(5).setCostOpt1(null);
        weaponDeck.get(5).setCostOpt2(Color.BLUE);
        weaponDeck.get(5).setCostAlternate(null);
        weaponDeck.get(5).setBasicMode(new EffectDamage(2, 1, 0, true, 0));
        weaponDeck.get(5).setOptionalModeOne(new EffectMoveYourself(2));
        weaponDeck.get(5).setOptionalModeTwo(new EffectDamage(1, 1, 0, true, 0));  //same target
        weaponDeck.get(5).setAlternateMode(null);
        weaponDeck.get(5).setRequiredParameters(4, 1, 1, false, false);


        //WHISPER
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(6).setName(WeaponName.WHISPER);
        weaponDeck.get(6).setColor(Color.BLUE);
        weaponDeck.get(6).setCost(Color.BLUE, Color.BLUE, Color.YELLOW);
        weaponDeck.get(6).setCostOpt1(null);
        weaponDeck.get(6).setCostOpt2(null);
        weaponDeck.get(6).setCostAlternate(null);
        weaponDeck.get(6).setBasicMode(new EffectDamage(3, 1, 0, true, 2), new EffectMark(1, 1, 0, true, 2));  //same target
        weaponDeck.get(6).setOptionalModeOne(null);
        weaponDeck.get(6).setOptionalModeTwo(null);
        weaponDeck.get(6).setAlternateMode(null);
        weaponDeck.get(6).setRequiredParameters(1, 1, 0, false, false);


        //VORTEX CANNON
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(7).setName(WeaponName.VORTEX_CANNON);
        weaponDeck.get(7).setColor(Color.RED);
        weaponDeck.get(7).setCost(Color.RED, Color.BLUE);
        weaponDeck.get(7).setCostOpt1(Color.RED);
        weaponDeck.get(7).setCostOpt2(null);
        weaponDeck.get(7).setCostAlternate(null);
        weaponDeck.get(7).setBasicMode(new EffectMoveTarget(1, 1, 0, false, 0), new EffectDamage(2, 1, 0, true, 1));  //same target
        weaponDeck.get(7).setOptionalModeOne(new EffectMoveTarget(1, 1, 1, false, 0), new EffectDamage(1, 1, 1, true, 1), new EffectMoveTarget(1, 1, 2, false, 0), new EffectDamage(1, 1, 2, true, 1));  //same targets
        weaponDeck.get(7).setOptionalModeTwo(null);
        weaponDeck.get(7).setAlternateMode(null);
        weaponDeck.get(7).setRequiredParameters(3, 3, 1, false, false);


        //FURNACE
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(8).setName(WeaponName.FURNACE);
        weaponDeck.get(8).setColor(Color.RED);
        weaponDeck.get(8).setCost(Color.RED, Color.BLUE);
        weaponDeck.get(8).setCostOpt1(null);
        weaponDeck.get(8).setCostOpt2(null);
        weaponDeck.get(8).setCostAlternate(null);
        weaponDeck.get(8).setBasicMode(new EffectDamage(1, -1, -1, true, 1));  //every person in a room, not mine, check it in controller
        weaponDeck.get(8).setOptionalModeOne(null);
        weaponDeck.get(8).setOptionalModeTwo(null);
        weaponDeck.get(8).setAlternateMode(new EffectDamage(1, -1, -1, true, 1), new EffectMark(1, -1, -1, true, 1));  //same targets
        weaponDeck.get(8).setRequiredParameters(2, 0, 1, false, false);


        //HEATSEEKER
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(9).setName(WeaponName.HEATSEEKER);
        weaponDeck.get(9).setColor(Color.RED);
        weaponDeck.get(9).setCost(Color.RED, Color.RED, Color.YELLOW);
        weaponDeck.get(9).setCostOpt1(null);
        weaponDeck.get(9).setCostOpt2(null);
        weaponDeck.get(9).setCostAlternate(null);
        weaponDeck.get(9).setBasicMode(new EffectDamage(3, 1, 0, false, 2));  //only non visible targets
        weaponDeck.get(9).setOptionalModeOne(null);
        weaponDeck.get(9).setOptionalModeTwo(null);
        weaponDeck.get(9).setAlternateMode(null);
        weaponDeck.get(9).setRequiredParameters(1, 1, 0, false, false);


        //HELLION
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(10).setName(WeaponName.HELLION);
        weaponDeck.get(10).setColor(Color.RED);
        weaponDeck.get(10).setCost(Color.RED, Color.YELLOW);
        weaponDeck.get(10).setCostOpt1(null);
        weaponDeck.get(10).setCostOpt2(null);
        weaponDeck.get(10).setCostAlternate(Color.RED);
        weaponDeck.get(10).setBasicMode(new EffectDamage(1, 1, 0, true, 1), new EffectMark(1, -1, -1, true, 1)); //same square, mark every player
        weaponDeck.get(10).setOptionalModeOne(null);
        weaponDeck.get(10).setOptionalModeTwo(null);
        weaponDeck.get(10).setAlternateMode(new EffectDamage(1, 1, 0, true, 1), new EffectMark(2, -1, -1, true, 1)); //same square, mark every player
        weaponDeck.get(10).setRequiredParameters(2, 1, 0, false, false);


        //FLAMETHROWER
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(11).setName(WeaponName.FLAMETHROWER);
        weaponDeck.get(11).setColor(Color.RED);
        weaponDeck.get(11).setCost(Color.RED);
        weaponDeck.get(11).setCostOpt1(null);
        weaponDeck.get(11).setCostOpt2(null);
        weaponDeck.get(11).setCostAlternate(Color.YELLOW, Color.YELLOW);
        weaponDeck.get(11).setBasicMode(new EffectDamage(1, 1, 0, true, 1), new EffectDamage(1, 1, 1, false, 2));  //same direction
        weaponDeck.get(11).setOptionalModeOne(null);
        weaponDeck.get(11).setOptionalModeTwo(null);
        weaponDeck.get(11).setAlternateMode(new EffectDamage(2, -1, -1, true, 1),new EffectDamage(1, -1, -1, false, 2));  //same direction
        weaponDeck.get(11).setRequiredParameters(2, 2, 0, true, false);

        //GRENADE LAUNCHER
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(12).setName(WeaponName.GRENADE_LAUNCHER);
        weaponDeck.get(12).setColor(Color.RED);
        weaponDeck.get(12).setCost(Color.RED);
        weaponDeck.get(12).setCostOpt1(Color.RED);
        weaponDeck.get(12).setCostOpt2(null);
        weaponDeck.get(12).setCostAlternate(null);
        weaponDeck.get(12).setBasicMode(new EffectDamage(1, 1, 0, true, 0), new EffectMoveTarget(1, 1, 0, true, 0));  //same target
        weaponDeck.get(12).setOptionalModeOne(new EffectDamage(1, -1, -1, true, 0));  //can be used before or after the EffectMove of basic mode
        weaponDeck.get(12).setOptionalModeTwo(null);
        weaponDeck.get(12).setAlternateMode(null);
        weaponDeck.get(12).setRequiredParameters(3, 1, 2, false, true);


        //ROCKET LAUNCHER
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(13).setName(WeaponName.ROCKET_LAUNCHER);
        weaponDeck.get(13).setColor(Color.RED);
        weaponDeck.get(13).setCost(Color.RED, Color.RED);
        weaponDeck.get(13).setCostOpt1(Color.BLUE);
        weaponDeck.get(13).setCostOpt2(Color.YELLOW);
        weaponDeck.get(13).setCostAlternate(null);
        weaponDeck.get(13).setBasicMode(new EffectDamage(2, 1, 0, true, 1), new EffectMoveTarget(1, 1, 0, true, 1));  //same target
        weaponDeck.get(13).setOptionalModeOne(new EffectMoveYourself(2));
        weaponDeck.get(13).setOptionalModeTwo(new EffectDamage(1, -1, -1, true, 1));  //same square as EffectDamage of BasicMode, to be executed during BasicMode
        weaponDeck.get(13).setAlternateMode(null);
        weaponDeck.get(13).setRequiredParameters(4, 1, 2, false, false);


        //RAILGUN
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(14).setName(WeaponName.RAILGUN);
        weaponDeck.get(14).setColor(Color.YELLOW);
        weaponDeck.get(14).setCost(Color.YELLOW, Color.YELLOW, Color.BLUE);
        weaponDeck.get(14).setCostOpt1(null);
        weaponDeck.get(14).setCostOpt2(null);
        weaponDeck.get(14).setCostAlternate(null);
        weaponDeck.get(14).setBasicMode(new EffectDamage(3, 1, 0, false, 0));  //cardinal direction
        weaponDeck.get(14).setOptionalModeOne(null);
        weaponDeck.get(14).setOptionalModeTwo(null);
        weaponDeck.get(14).setAlternateMode(new EffectDamage(2, 1, 0, false, 0), new EffectDamage(2, 1, 1, false, 0));  //same direction, remember you can always split them into two new
        weaponDeck.get(14).setRequiredParameters(2, 2, 0, true, false);


        //CYBERBLADE
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(15).setName(WeaponName.CYBERBLADE);
        weaponDeck.get(15).setColor(Color.YELLOW);
        weaponDeck.get(15).setCost(Color.YELLOW, Color.RED);
        weaponDeck.get(15).setCostOpt1(null);
        weaponDeck.get(15).setCostOpt2(Color.YELLOW);
        weaponDeck.get(15).setCostAlternate(null);
        weaponDeck.get(15).setBasicMode(new EffectDamage(2, 1, 0, true, -1));
        weaponDeck.get(15).setOptionalModeOne(new EffectMoveYourself(1));   //before or after the basic effect
        weaponDeck.get(15).setOptionalModeTwo(new EffectDamage(2, 1, 1, true, -1));   //different target from basic effect
        weaponDeck.get(15).setAlternateMode(null);
        weaponDeck.get(15).setRequiredParameters(4, 2, 1, false, false);


        //ZX-2
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(16).setName(WeaponName.ZX_2);
        weaponDeck.get(16).setColor(Color.YELLOW);
        weaponDeck.get(16).setCost(Color.YELLOW, Color.RED);
        weaponDeck.get(16).setCostOpt1(null);
        weaponDeck.get(16).setCostOpt2(null);
        weaponDeck.get(16).setCostAlternate(null);
        weaponDeck.get(16).setBasicMode(new EffectDamage(1, 1, 0, true, 0), new EffectMark(2, 1, 0, true, 0));  //same target
        weaponDeck.get(16).setOptionalModeOne(null);
        weaponDeck.get(16).setOptionalModeTwo(null);
        weaponDeck.get(16).setAlternateMode(new EffectMark(1, 1, 0, true, 0), new EffectMark(1, 1, 1, true, 0), new EffectMark(1, 1, 2, true, 0));
        weaponDeck.get(16).setRequiredParameters(2, 3, 0, false, false);


        //SHOTGUN
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(17).setName(WeaponName.SHOTGUN);
        weaponDeck.get(17).setColor(Color.YELLOW);
        weaponDeck.get(17).setCost(Color.YELLOW, Color.YELLOW);
        weaponDeck.get(17).setCostOpt1(null);
        weaponDeck.get(17).setCostOpt2(null);
        weaponDeck.get(17).setCostAlternate(null);
        weaponDeck.get(17).setBasicMode(new EffectDamage(3, 1, 0, true, -1), new EffectMoveTarget(1, 1, 0, true, -1));  //same target
        weaponDeck.get(17).setOptionalModeOne(null);
        weaponDeck.get(17).setOptionalModeTwo(null);
        weaponDeck.get(17).setAlternateMode(new EffectDamage(2, 1, 0, true, 1));   //exactly one move away
        weaponDeck.get(17).setRequiredParameters(2, 1, 1, false, false);


        //POWER GLOVE
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(18).setName(WeaponName.POWER_GLOVE);
        weaponDeck.get(18).setColor(Color.YELLOW);
        weaponDeck.get(18).setCost(Color.YELLOW, Color.BLUE);
        weaponDeck.get(18).setCostOpt1(null);
        weaponDeck.get(18).setCostOpt2(null);
        weaponDeck.get(18).setCostAlternate(Color.BLUE);
        weaponDeck.get(18).setBasicMode(new EffectMoveYourself(1), new EffectDamage(1, 1, 0, true, -1), new EffectMark(2, 1, 0, true, -1));  //same target
        weaponDeck.get(18).setOptionalModeOne(null);
        weaponDeck.get(18).setOptionalModeTwo(null);
        weaponDeck.get(18).setAlternateMode(new EffectMoveYourself(1), new EffectDamage(2, 1, 0, true, -1), new EffectMoveYourself(1), new EffectDamage(2, 1, 1, true, -1));    //same direction
        weaponDeck.get(18).setRequiredParameters(2, 2, 1, true, false);


        //SHOCKWAVE
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(19).setName(WeaponName.SCHOCKWAVE);
        weaponDeck.get(19).setColor(Color.YELLOW);
        weaponDeck.get(19).setCost(Color.YELLOW);
        weaponDeck.get(19).setCostOpt1(null);
        weaponDeck.get(19).setCostOpt2(null);
        weaponDeck.get(19).setCostAlternate(Color.YELLOW);
        weaponDeck.get(19).setBasicMode(new EffectDamage(1, 1, 0, true, 1), new EffectDamage(1, 1, 1, true, 1), new EffectDamage(1, 1, 2,  true, 1));  //different targets exactly one move away
        weaponDeck.get(19).setOptionalModeOne(null);
        weaponDeck.get(19).setOptionalModeTwo(null);
        weaponDeck.get(19).setAlternateMode(new EffectDamage(1, -1, -1, true, 1));   //exactly one move away
        weaponDeck.get(19).setRequiredParameters(2, 3, 0, false, false);


        //SLEDGEHAMMER
        weaponDeck.addLast(new Weapon());
        weaponDeck.get(20).setName(WeaponName.SLEDGEHAMMER);
        weaponDeck.get(20).setColor(Color.YELLOW);
        weaponDeck.get(20).setCost(Color.YELLOW);
        weaponDeck.get(20).setCostOpt1(null);
        weaponDeck.get(20).setCostOpt2(null);
        weaponDeck.get(20).setCostAlternate(Color.RED);
        weaponDeck.get(20).setBasicMode(new EffectDamage(2, 1, 0, true, -1));
        weaponDeck.get(20).setOptionalModeOne(null);
        weaponDeck.get(20).setOptionalModeTwo(null);
        weaponDeck.get(20).setAlternateMode(new EffectDamage(3, 1, 0, true, -1), new EffectMoveTarget(2, 1, 0, true,-1)); //same target
        weaponDeck.get(20).setRequiredParameters(2, 1, 1, false, false);
    }


    public boolean isInDeck(WeaponName weapon) {
        Iterator<Weapon> iterDeck = weaponDeck.iterator();
        while (iterDeck.hasNext()) {
           if(iterDeck.next().getName().equals(weapon)) {
               return true;
           }
        }
        return false;
    }

    public void shuffleDeck() {
        Collections.shuffle(this.weaponDeck);
    }

    public Weapon pickFirstCard() {
        Weapon temp = weaponDeck.getFirst();
        weaponDeck.removeFirst();
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder deck = new StringBuilder();
        for(int i=0; i<weaponDeck.size(); i++) {
            deck.append(weaponDeck.get(i).getName());
            deck.append(" - ");
        }
        return deck.toString();
    }

    //TODO ONLY FOR TEST, REMEMBER TO DELETE THIS
    public Weapon getWeapon(WeaponName name) {
        for (Weapon w: weaponDeck) {
            if (w.getName().equals(name)){
                return w;
            }
        }
        return null;
    }

}
