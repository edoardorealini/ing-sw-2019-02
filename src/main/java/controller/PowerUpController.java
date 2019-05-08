package controller;

import model.Match;
import model.Color;
import model.powerup.PowerUp;

/*
    This Class is used to manage all the possibilities from the PowerUps
    in the basic implementation of PowerUpCard there is no logic

    here i want to give game logic to each card based on its name
 */

public class PowerUpController{
    private Match match;
    private MoveController moveController;

    public PowerUpController(Match m, MoveController moveCtrl){
        this. match = m;
        this.moveController = moveCtrl; //serve per teleporter e per newton
    }

    /*
        this method has the powerup in input.
        the result will be the update of the status of ammo in the player object
        and the update of the powerup card position (returning to deck)

    public void usePowerUpAsAmmo(PowerUp powerUp){
        //fatto lato johnny, invocare quel metodo.
        fornire metodo lo stesso, devo effettuarei i controlli! (lanciare eccezioni eventualmente)

    }
    */

    public void useTeleporter(PowerUp teleporter){
        //TODO imlementare, sisteemare signature del metodo
    }

    public void useNewton(PowerUp teleporter){
        //TODO imlementare, sisteemare signature del metodo
    }

    public void useTagbackGrenade(PowerUp teleporter){
        //TODO imlementare, sisteemare signature del metodo
    }

    public void useTargetingScope(PowerUp teleporter){
        //TODO imlementare, sisteemare signature del metodo
    }
}
