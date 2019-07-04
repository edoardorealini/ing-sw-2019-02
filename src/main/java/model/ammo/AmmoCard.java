package model.ammo;

import java.util.*;
import java.io.Serializable;

/**
 * This class represents the ammo tiles that are present on the map
 */
public class AmmoCard  implements Serializable {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;
    private boolean isTherePowerUp;

    public AmmoCard(int redAmmo, int blueAmmo, int yellowAmmo, boolean isTherePowerUp){
        this.redAmmo =redAmmo;
        this.blueAmmo =blueAmmo;
        this.yellowAmmo =yellowAmmo;
        this.isTherePowerUp= isTherePowerUp;
    }

    public int getBlueAmmo() {
        return blueAmmo;
    }

    public int getRedAmmo() {
        return redAmmo;
    }

    public int getYellowAmmo() {
        return yellowAmmo;
    }

    public boolean isTherePowerUp() {
        return isTherePowerUp;
    }

    public ArrayList<Character> getAmmo(){
        ArrayList<Character> ammo = new ArrayList<>();
        for (int i = 1 ; i<= redAmmo; i++){
            ammo.add('R');
        }
        for (int i = 1 ; i<= blueAmmo; i++){
            ammo.add('B');
        }
        for (int i = 1 ; i<= yellowAmmo; i++){
            ammo.add('Y');
        }
        if (isTherePowerUp) ammo.add('P');

        return ammo;
    }
}
