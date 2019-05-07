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
        this.moveController = moveCtrl;
    }

    public void usePowerUp(PowerUp powerUp){
        //TODO
    }

    private void useTeleporter(PowerUp teleporter){
        //TODO
    }
}
