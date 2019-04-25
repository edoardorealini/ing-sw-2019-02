package model.ammo;

import model.powerUps.PowerUps;

public class AmmoCard {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;
    private boolean isTherePowerUp;
    private PowerUps powerUps;

    public AmmoCard(){
        redAmmo = 1;
        blueAmmo = 1;
        yellowAmmo = 1;
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

    public PowerUps getPowerUps() {
        return powerUps;
    }

    //TODO
}
