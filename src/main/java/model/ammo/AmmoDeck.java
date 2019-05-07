package model.ammo;

import java.util.*;
import model.powerup.PowerUpDeck;

public class AmmoDeck {
    private LinkedList<AmmoCard> ammoDeck;

    public AmmoDeck(){

        ammoDeck = new LinkedList<>();

        ammoDeck.add(new AmmoCard(0,2,1,false));
        ammoDeck.add(new AmmoCard(2,0,1,false));
        ammoDeck.add(new AmmoCard(1,2,0,false));
        ammoDeck.add(new AmmoCard(1,0,2,false));
        ammoDeck.add(new AmmoCard(0,1,2,false));
        ammoDeck.add(new AmmoCard(2,1,0,false));
        ammoDeck.add(new AmmoCard(0,2,1,false));
        ammoDeck.add(new AmmoCard(2,0,1,false));
        ammoDeck.add(new AmmoCard(1,2,0,false));
        ammoDeck.add(new AmmoCard(0,1,2,false));
        ammoDeck.add(new AmmoCard(0,1,2,false));
        ammoDeck.add(new AmmoCard(2,1,0,false));
        ammoDeck.add(new AmmoCard(0,2,1,false));
        ammoDeck.add(new AmmoCard(2,0,1,false));
        ammoDeck.add(new AmmoCard(1,2,0,false));
        ammoDeck.add(new AmmoCard(1,0,2,false));
        ammoDeck.add(new AmmoCard(0,1,2,false));
        ammoDeck.add(new AmmoCard(2,1,0,false));

        ammoDeck.add(new AmmoCard(2,0,0,true));
        ammoDeck.add(new AmmoCard(0,2,0,true));
        ammoDeck.add(new AmmoCard(0,0,2,true));
        ammoDeck.add(new AmmoCard(1,0,1,true));
        ammoDeck.add(new AmmoCard(0,1,1,true));
        ammoDeck.add(new AmmoCard(1,1,0,true));
        ammoDeck.add(new AmmoCard(1,0,1,true));
        ammoDeck.add(new AmmoCard(1,1,0,true));
        ammoDeck.add(new AmmoCard(0,1,1,true));
        ammoDeck.add(new AmmoCard(2,0,0,true));
        ammoDeck.add(new AmmoCard(0,2,0,true));
        ammoDeck.add(new AmmoCard(0,0,2,true));
        ammoDeck.add(new AmmoCard(1,0,1,true));
        ammoDeck.add(new AmmoCard(0,1,1,true));
        ammoDeck.add(new AmmoCard(1,1,0,true));
        ammoDeck.add(new AmmoCard(1,0,1,true));
        ammoDeck.add(new AmmoCard(1,1,0,true));
        ammoDeck.add(new AmmoCard(0,1,1,true));

    }

    public void shuffleDeck(){
        Collections.shuffle(ammoDeck);
    }

    public AmmoCard pickFirstCard(){
        AmmoCard AmmoCardTemp;
        AmmoCardTemp = ammoDeck.getFirst();
        ammoDeck.removeFirst();
        return AmmoCardTemp;
    }

    public void addAmmoCard(AmmoCard ammoCard){
        ammoDeck.addLast(ammoCard);
    }
}
