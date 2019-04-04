package weapons;

import java.util.ArrayList;

public class WeaponDeck {
    private ArrayList<Weapon> weaponDeck;

    public boolean isInDeck(Weapon weapon) {
        return weaponDeck.contains(weapon);
    }

    //TODO
}
