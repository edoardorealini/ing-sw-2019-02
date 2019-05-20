package client.clientModel.ammo;

public class AmmoCard {
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
    
}
