package model.ammo;

import java.util.*;
import model.powerUp.PowerUpDeck;

public class AmmoDeck {
    private LinkedList<AmmoCard> ammoDeck;
    private PowerUpDeck powerUpDeck;

    public AmmoDeck(){
        ammoDeck.add(new AmmoCard(0,2,1,false,null));
        ammoDeck.add(new AmmoCard(2,0,1,false,null));
        ammoDeck.add(new AmmoCard(1,2,0,false,null));
        ammoDeck.add(new AmmoCard(1,0,2,false,null));
        ammoDeck.add(new AmmoCard(0,1,2,false,null));
        ammoDeck.add(new AmmoCard(2,1,0,false,null));
        ammoDeck.add(new AmmoCard(0,2,1,false,null));
        ammoDeck.add(new AmmoCard(2,0,1,false,null));
        ammoDeck.add(new AmmoCard(1,2,0,false,null));
        ammoDeck.add(new AmmoCard(0,1,2,false,null));
        ammoDeck.add(new AmmoCard(0,1,2,false,null));
        ammoDeck.add(new AmmoCard(2,1,0,false,null));
        ammoDeck.add(new AmmoCard(0,2,1,false,null));
        ammoDeck.add(new AmmoCard(2,0,1,false,null));
        ammoDeck.add(new AmmoCard(1,2,0,false,null));
        ammoDeck.add(new AmmoCard(1,0,2,false,null));
        ammoDeck.add(new AmmoCard(0,1,2,false,null));
        ammoDeck.add(new AmmoCard(2,1,0,false,null));

        //TODO non si possono creare tutti subito perchè i powerUp totali sono 12, qua ne avrei bisogno 18
        // risolvere con meccanismo produttore consumatore
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));
        ammoDeck.add(new AmmoCard(0,0,0,true,powerUpDeck.removePowerUps()));


    }

    public void shuffle(){
        Collections.shuffle(ammoDeck);
    }

    public AmmoCard removeAmmoCard(){
        AmmoCard AmmoCardTemp;
        AmmoCardTemp = ammoDeck.getFirst();
        ammoDeck.removeFirst();
        return AmmoCardTemp;
    }

    public void addAmmoCard(AmmoCard ammoCard){
        ammoDeck.addLast(ammoCard);
    }
}
