package model.ammo;

import model.powerUp.PowerUp;

public class AmmoCard {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;
    private boolean isTherePowerUp;
    private PowerUp powerUp;

    public AmmoCard(int redAmmo, int blueAmmo, int yellowAmmo, boolean isTherePowerUp, PowerUp powerUp){
        this.redAmmo =redAmmo;
        this.blueAmmo =blueAmmo;
        this.yellowAmmo =yellowAmmo;
        this.isTherePowerUp= isTherePowerUp;
        this.powerUp= powerUp;
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
