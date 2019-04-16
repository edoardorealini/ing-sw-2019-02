package model.weapons;

import java.util.ArrayList;
import java.util.Collections;

public class WeaponDeck {
    private ArrayList<Weapon> weaponDeck;

    //constructor
    public WeaponDeck(){
        this.weaponDeck = new ArrayList<>();

        weaponDeck.add(new Weapon());
        weaponDeck.get(0).setBasicEffect(new EffectDamage(2, 1, true, -1), new EffectDamage(2, 1, true, -1), new EffectDamage(2, 1, true, -1), new EffectDamage(2, 1, true, -1));
    }

    public boolean isInDeck(Weapon weapon) {
        return weaponDeck.contains(weapon);
    }

    public void shuffleDeck() {
        Collections.shuffle(this.weaponDeck);
    }


    //TODO
}
