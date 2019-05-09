package controller;

import exception.NotAllowedMoveException;
import model.Match;
import model.Color;
import model.powerup.PowerUp;
import model.map.*;
import model.player.*;
import model.powerup.PowerUpName;

import java.util.ArrayList;

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

        it can only be used when the player has to pay a cost (picking a weapon)
        //fatto lato johnny, invocare quel metodo. - me pias no
        //fornire metodo lo stesso, devo effettuarei i controlli! (lanciare eccezioni eventualmente)
    */
    public void usePowerUpAsAmmo(PowerUp powerUp) throws IllegalArgumentException{
        //TODO bisogna gestire il fatto che si puo usare solo per pagare un costo
        for(int i = 0; i < 3; i++){
            if(match.getCurrentPlayer().getPowerUps()[i].equals(powerUp))
                match.getCurrentPlayer().trasformPowerUpToAmmo(powerUp);
            else throw new IllegalArgumentException("Not a valid powerUp to be converted to ammo");
        }

    }

    /*
        Use of teleporter powerup
    */
    public void useTeleporter(PowerUp teleporter, Square destination){
        if(teleporter.getName().equals(PowerUpName.TELEPORTER)) {
            for (int i = 0; i < 3; i++) {
                if (match.getCurrentPlayer().getPowerUps()[i].equals(teleporter)) {
                    if (destination.isActive()) {
                        match.getCurrentPlayer().setPosition(destination);
                    } else throw new IllegalArgumentException("Not valid destination Square, not active");
                } else throw new IllegalArgumentException("Not a valid powerUp, not in your hand");
            }
        } else throw new IllegalArgumentException("Not valid Powerup");
    }

    /*
        Use of newton powerup
    */
    public void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws NotAllowedMoveException{
        if(newton.getName().equals(PowerUpName.NEWTON)) {
            for (int i = 0; i < 3; i++) {
                if (match.getCurrentPlayer().getPowerUps()[i].equals(newton)) { //controllo che il giocatore abbia il PU
                    for (Directions d : affectedPlayer.getPosition().getAllowedMoves()) {
                        if (match.getMap().getAllowedSquaresInDirection(d, affectedPlayer.getPosition()).contains(destination)) { //controllo che lo spostamento avvenga in una sola direzione
                            try {
                                moveController.move(affectedPlayer, destination, 2); //effettuo spostamento con max distance 2 (da regolamento)

                            } catch (NotAllowedMoveException e) {
                                e.printStackTrace();
                            }
                        } else
                            throw new NotAllowedMoveException("Not allowed to move in more than one direction with Newton");
                    }
                }
            }
            throw new IllegalArgumentException("Not a valid PowerUp, not in your hand");
        }else throw new IllegalArgumentException("Not valid PowerUp");
    }

    public void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer){
        if(tagbackGrenade.getName().equals(PowerUpName.TAGBACK_GRENADE)){
            //TODO
        }
        else throw new IllegalArgumentException("Not valid PowerUp");
    }

    public void useTargetingScope(PowerUp teleporter){
        //TODO imlementare, sisteemare signature del metodo
    }

}
