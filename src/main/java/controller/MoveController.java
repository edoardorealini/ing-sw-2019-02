package controller;

import exception.NotAllowedMoveException;
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
        moveOneSquare changes the specified player position position according to the input direction
        throws an exception - NotAllowedMoveException

        Example: used for special effects in weapons

    */
    public void moveOneSquare(Directions direction, Player player) throws NotAllowedMoveException {
        //la verifica di fattibilità della mossa avviene qui
        if( ! player.getPosition().getAllowedMoves().contains(direction)){
            throw new NotAllowedMoveException();
        }
        // qui una volta che ho fatto il controllo posso procedere senza problemi senza dover
        // inserire controlli all'interno della classe map.
        else{
            player.setPosition(match.getMap().getSquare(direction, player.getPosition()));
        }
    }

    /*
        moveOneSquare 2 changes the currentPlayer position according to the input direction
        throws an exception - NotAllowedMoveException

        Example: used to move yourself during your turn
    */
    public void moveOneSquare(Directions direction) throws NotAllowedMoveException {
        //la verifica di fattibilità della mossa avviene qui
        if( ! match.getCurrentPlayer().getPosition().getAllowedMoves().contains(direction)){
            throw new NotAllowedMoveException();
        }
        // qui una volta che ho fatto il controllo posso procedere senza problemi senza dover
        // inserire controlli all'interno della classe map.
        else{
            match.getCurrentPlayer().setPosition(match.getMap().getSquare(direction, match.getCurrentPlayer().getPosition()));
        }
    }

    public void move(Player player, Square destination) throws  NotAllowedMoveException{

    }


}
