package model.ammo;

import model.powerUps.PowerUps;

public class AmmoCard {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;
    private boolean isTherepowerUp;
    private PowerUps powerUps;


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
        return isTherepowerUp;
    }

    public PowerUps getPowerUps() {
        return powerUps;
    }

    //TODO
}
