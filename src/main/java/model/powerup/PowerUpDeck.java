package model.powerup;

import model.Color;
import java.util.*;

public class PowerUpDeck {
    private LinkedList<PowerUp> powerUpDeck;

    public PowerUpDeck(){

        powerUpDeck = new LinkedList<>();

        powerUpDeck.add(new PowerUp(Color.BLUE, "TagBack Grenade"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "TagBack Grenade"));
        powerUpDeck.add(new PowerUp(Color.RED, "TagBack Grenade"));
        powerUpDeck.add(new PowerUp(Color.RED, "TagBack Grenade"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "TagBack Grenade"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "TagBack Grenade"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "Targeting Scope"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "Targeting Scope"));
        powerUpDeck.add(new PowerUp(Color.RED, "Targeting Scope"));
        powerUpDeck.add(new PowerUp(Color.RED, "Targeting Scope"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "Targeting Scope"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "Targeting Scope"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "Newton"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "Newton"));
        powerUpDeck.add(new PowerUp(Color.RED, "Newton"));
        powerUpDeck.add(new PowerUp(Color.RED, "Newton"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "Newton"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "Newton"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "Teleporter"));
        powerUpDeck.add(new PowerUp(Color.BLUE, "Teleporter"));
        powerUpDeck.add(new PowerUp(Color.RED, "Teleporter"));
        powerUpDeck.add(new PowerUp(Color.RED, "Teleporter"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "Teleporter"));
        powerUpDeck.add(new PowerUp(Color.YELLOW, "Teleporter"));

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
