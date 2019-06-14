package model.powerup;

import model.Color;

import java.io.Serializable;
import java.util.*;

public class PowerUpDeck  implements Serializable {
    private List<PowerUp> powerUpDeck;

    public PowerUpDeck(){

        powerUpDeck = new ArrayList<>();

        powerUpDeck.add(new TagbackGrenade(Color.BLUE));
        powerUpDeck.add(new TagbackGrenade(Color.BLUE));
        powerUpDeck.add(new TagbackGrenade(Color.RED));
        powerUpDeck.add(new TagbackGrenade(Color.RED));
        powerUpDeck.add(new TagbackGrenade(Color.YELLOW));
        powerUpDeck.add(new TagbackGrenade(Color.YELLOW));

        powerUpDeck.add(new TargetingScope(Color.BLUE));
        powerUpDeck.add(new TargetingScope(Color.BLUE));
        powerUpDeck.add(new TargetingScope(Color.RED));
        powerUpDeck.add(new TargetingScope(Color.RED));
        powerUpDeck.add(new TargetingScope(Color.YELLOW));
        powerUpDeck.add(new TargetingScope(Color.YELLOW));

        powerUpDeck.add(new Newton(Color.BLUE));
        powerUpDeck.add(new Newton(Color.BLUE));
        powerUpDeck.add(new Newton(Color.RED));
        powerUpDeck.add(new Newton(Color.RED));
        powerUpDeck.add(new Newton(Color.YELLOW));
        powerUpDeck.add(new Newton(Color.YELLOW));

        powerUpDeck.add(new Teleporter(Color.BLUE));
        powerUpDeck.add(new Teleporter(Color.BLUE));
        powerUpDeck.add(new Teleporter(Color.RED));
        powerUpDeck.add(new Teleporter(Color.RED));
        powerUpDeck.add(new Teleporter(Color.YELLOW));
        powerUpDeck.add(new Teleporter(Color.YELLOW));

    }

    public void shuffle(){
        Collections.shuffle(powerUpDeck);
    }


    public PowerUp pickFirstCard(){
        /*
         PowerUp powerUpTemp;
         powerUpTemp = powerUpDeck.getFirst();
         powerUpDeck.removeFirst();
         return powerUpTemp;
         */
        return powerUpDeck.remove(0);
    }

    public void addPowerUps(PowerUp powerUp){
        powerUpDeck.add(powerUp);
    }

}
