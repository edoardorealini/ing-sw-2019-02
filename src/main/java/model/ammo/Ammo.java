package model.ammo;

public class Ammo {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;

    public Ammo (){
        redAmmo=0;
        blueAmmo=0;
        yellowAmmo=0;
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

    public void setBlueAmmo(int blueAmmo) {
        this.blueAmmo = blueAmmo;
    }

    public void setRedAmmo(int redAmmo) {
        this.redAmmo = redAmmo;
    }

    public void setYellowAmmo(int yellowAmmo) {
        this.yellowAmmo = yellowAmmo;
    }


}
