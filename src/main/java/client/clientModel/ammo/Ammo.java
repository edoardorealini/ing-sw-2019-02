package client.clientModel.ammo;

import client.clientModel.Color;

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

    public void addSpecificAmmo(Color color, int number){
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
    public String toString() {
        String information;
        information = "Blue Ammo: " +getBlueAmmo()+ "\nRed Ammo: " +getRedAmmo()+ "\nYellow Ammo: " +getYellowAmmo();
        return information;
    }
}
