package model.powerUps;

import java.util.ArrayList;

public class PowerUpsDeck {
    private ArrayList<PowerUps> powerUpsDeck;

    public PowerUpsDeck(){

        powerUpsDeck.add(new PowerUps('B', "Granata Venom"));
        powerUpsDeck.add(new PowerUps('R', "Granata Venom"));
        powerUpsDeck.add(new PowerUps('Y', "Granata Venom"));
        powerUpsDeck.add(new PowerUps('B', "Mirino"));
        powerUpsDeck.add(new PowerUps('R', "Mirino"));
        powerUpsDeck.add(new PowerUps('Y', "Mirino"));
        powerUpsDeck.add(new PowerUps('B', "Raggio cinetico"));
        powerUpsDeck.add(new PowerUps('R', "Raggio cinetico"));
        powerUpsDeck.add(new PowerUps('Y', "Raggio cinetico"));
        powerUpsDeck.add(new PowerUps('B', "Teletrasporto"));
        powerUpsDeck.add(new PowerUps('R', "Teletrasporto"));
        powerUpsDeck.add(new PowerUps('Y', "Teletrasporto"));

    }


    public PowerUps removePowerUps(){
         return powerUpsDeck.get(0);
         //TODO come cancello la prima cella ?

    }

    public void addPowerUps(PowerUps powerUps){
        powerUpsDeck.add(powerUpsDeck.size()-1,powerUps);
    }
}
