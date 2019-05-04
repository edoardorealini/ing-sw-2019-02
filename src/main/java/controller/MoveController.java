package controller;

import model.Match;
import model.map.Directions;
import model.player.Player;
import model.map.*;

public class MoveController {
    private Match match;

    /*
        Con il costruttore genero l'oggetto con già dentro il riferimento al match che viene istanziato a livelo di
        match controller
    */
    public MoveController(Match realMatch){
        this.match = realMatch;
    }

    /*
        moveOneSquare changes the player position according to the input direction
        throws an exception - NotAllowedMoveException

    */

    public void moveOneSquare(Directions direction, Player player){
        //TODO
    }


}
