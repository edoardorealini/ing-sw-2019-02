package model.ammo;

import model.powerUp.PowerUp;

public class AmmoCard {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;
    private boolean isTherePowerUp;
    private PowerUp powerUp;

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

    public PowerUp getPowerUp() {
        return powerUp;
    }

    //TODO
}
