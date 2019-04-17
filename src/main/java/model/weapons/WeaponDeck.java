package model.weapons;

import java.util.ArrayList;
import java.util.Collections;

//it's a singletone, how do I declare it?  Fix it in the next days

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
        weaponDeck.get(0).setBasicEffect(new EffectDamage(2, 1, true, -1), new EffectMark(1, 1, true, -1));
        weaponDeck.get(0).setOptionalEffectOne(new EffectMark(1,1,true, -1));
        weaponDeck.get(0).setOptionalEffectTwo(null);
        weaponDeck.get(0).setAlternateEffect(null);

        //remember to set the cost of the optional effects (think about it)

        //ELECTROSCYTHE
        weaponDeck.add(new Weapon());
        weaponDeck.get(1).setName(WeaponName.ELECTROSCYTHE);
        weaponDeck.get(1).setColor(Color.BLUE);
        weaponDeck.get(1).setCost(Color.BLUE);
        weaponDeck.get(1).setBasicEffect(new EffectDamage(2, 1, true, -1), new EffectMark(1, 1, true, -1));
        weaponDeck.get(1).setOptionalEffectOne(new EffectMark(1,1,true, -1));
        weaponDeck.get(1).setOptionalEffectTwo(null);
        weaponDeck.get(1).setAlternateEffect(null);

    }

    public boolean isInDeck(Weapon weapon) {
        return weaponDeck.contains(weapon);
    }

    public void shuffleDeck() {
        Collections.shuffle(this.weaponDeck);
    }


    //TODO
}
