package model.powerUps;

import model.Color;
import java.util.*;

public class PowerUpsDeck {
    private LinkedList<PowerUps> powerUpsDeck;

    public PowerUpsDeck(){

        powerUpsDeck.add(new PowerUps(Color.BLUE, "TagBack Grenade"));
        powerUpsDeck.add(new PowerUps(Color.RED, "TagBack Grenade"));
        powerUpsDeck.add(new PowerUps(Color.YELLOW, "TagBack Grenade"));
        powerUpsDeck.add(new PowerUps(Color.BLUE, "Targeting Scope"));
        powerUpsDeck.add(new PowerUps(Color.RED, "Targeting Scope"));
        powerUpsDeck.add(new PowerUps(Color.YELLOW, "Targeting Scope"));
        powerUpsDeck.add(new PowerUps(Color.BLUE, "Newton"));
        powerUpsDeck.add(new PowerUps(Color.RED, "Newton"));
        powerUpsDeck.add(new PowerUps(Color.YELLOW, "Newton"));
        powerUpsDeck.add(new PowerUps(Color.BLUE, "Teleporter"));
        powerUpsDeck.add(new PowerUps(Color.RED, "Teleporter"));
        powerUpsDeck.add(new PowerUps(Color.YELLOW, "Teleporter"));

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
