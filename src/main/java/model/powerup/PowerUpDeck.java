package model.powerup;

import model.Color;
import java.util.*;

public class PowerUpDeck {
    private LinkedList<PowerUp> powerUpDeck;

    public PowerUpDeck(){

        powerUpDeck = new LinkedList<>();

        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.TAGBACK_GRENADE));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.TAGBACK_GRENADE));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.TAGBACK_GRENADE));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.TAGBACK_GRENADE));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.TAGBACK_GRENADE));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.TAGBACK_GRENADE));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.TARGETING_SCOPE));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.TARGETING_SCOPE));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.TARGETING_SCOPE));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.TARGETING_SCOPE));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.TARGETING_SCOPE));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.TARGETING_SCOPE));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.NEWTON));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.NEWTON));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.NEWTON));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.NEWTON));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.NEWTON));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.NEWTON));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.TELEPORTER));
        powerUpDeck.add(new PowerUp(Color.BLUE, PowerUpName.TELEPORTER));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.TELEPORTER));
        powerUpDeck.add(new PowerUp(Color.RED, PowerUpName.TELEPORTER));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.TELEPORTER));
        powerUpDeck.add(new PowerUp(Color.YELLOW, PowerUpName.TELEPORTER));

    }

    public void shuffle(){
        Collections.shuffle(powerUpDeck);
    }


    public PowerUp pickFirstCard(){
         PowerUp powerUpTemp;
         powerUpTemp = powerUpDeck.getFirst();
         powerUpDeck.removeFirst();
         return powerUpTemp;


    }

    public void addPowerUps(PowerUp powerUp){
        powerUpDeck.addLast(powerUp);
    }

}
