package model.ammo;

import model.Color;

import java.io.Serializable;

public class Ammo implements Serializable {
    private int redAmmo;
    private int blueAmmo;
    private int yellowAmmo;

    public Ammo (){
        redAmmo = 1;
        blueAmmo = 1;
        yellowAmmo = 1;

        //TODO metti a 1

        /*  TEST ONLY
            redAmmo = 2;
            blueAmmo = 2;
            yellowAmmo = 2;
        */
    }

    public synchronized int getBlueAmmo() {
        return blueAmmo;
    }

    public synchronized int getRedAmmo() {
        return redAmmo;
    }

    public synchronized int getYellowAmmo() {
        return yellowAmmo;
    }

    public synchronized void setBlueAmmo(int blueAmmo) {
        this.blueAmmo = blueAmmo;
    }

    public synchronized void setRedAmmo(int redAmmo) {
        this.redAmmo = redAmmo;
    }

    public synchronized void setYellowAmmo(int yellowAmmo) {
        this.yellowAmmo = yellowAmmo;
    }

    public synchronized void addSpecificAmmo(Color color, int number){
        switch (color){
            case BLUE:
                if ((this.getBlueAmmo()+number)>=3){
                    this.setBlueAmmo(3);
                }
                else this.setBlueAmmo(this.getBlueAmmo()+number);
                break;

            case YELLOW:
                if ((this.getYellowAmmo()+number)>=3){
                    this.setYellowAmmo(3);
                }
                else this.setYellowAmmo(this.getYellowAmmo()+number);
                break;

            case RED:
                if ((this.getRedAmmo()+number)>=3){
                    this.setRedAmmo(3);
                }
                else this.setRedAmmo(this.getRedAmmo()+number);
                break;

        }
    }


    //for tests
    @Override
    public synchronized String toString() {
        String information;
        information = "Blue Ammo: " +getBlueAmmo()+ "\nRed Ammo: " +getRedAmmo()+ "\nYellow Ammo: " +getYellowAmmo();
        return information;
    }
}
