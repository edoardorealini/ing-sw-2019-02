package model.powerUps;

import java.util.*;

public class PowerUpsDeck {
    private LinkedList<PowerUps> powerUpsDeck;

    public PowerUpsDeck(){

        powerUpsDeck.add(new PowerUps('B', "TagBack Grenade"));
        powerUpsDeck.add(new PowerUps('R', "TagBack Grenade"));
        powerUpsDeck.add(new PowerUps('Y', "TagBack Grenade"));
        powerUpsDeck.add(new PowerUps('B', "Targeting Scope"));
        powerUpsDeck.add(new PowerUps('R', "Targeting Scope"));
        powerUpsDeck.add(new PowerUps('Y', "Targeting Scope"));
        powerUpsDeck.add(new PowerUps('B', "Newton"));
        powerUpsDeck.add(new PowerUps('R', "Newton"));
        powerUpsDeck.add(new PowerUps('Y', "Newton"));
        powerUpsDeck.add(new PowerUps('B', "Teleporter"));
        powerUpsDeck.add(new PowerUps('R', "Teleporter"));
        powerUpsDeck.add(new PowerUps('Y', "Teleporter"));

    }

    public void shuffle(){
        Collections.shuffle(powerUpsDeck);
    }


    public PowerUps removePowerUps(){
         PowerUps powerUpsTemp;
         powerUpsTemp = powerUpsDeck.getFirst();
         powerUpsDeck.removeFirst();
         return powerUpsTemp;


    }

    public void addPowerUps(PowerUps powerUps){
        powerUpsDeck.addLast(powerUps);
    }

}
