package controller;

import exception.NotAllowedMoveException;
import model.Match;
import model.map.Directions;
import model.player.Player;
import model.map.*;

import java.util.ArrayList;
import java.util.List;

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

    /*
        Given a Player, a destination Square and the maxDistanceAllowed,
        this method changes the player position only if the move is allowed by the map configuration.
    */
    public void move(Player player, Square destination, int maxDistanceAllowed) throws  NotAllowedMoveException{
        if(! isAllowedMove(player.getPosition(), destination, maxDistanceAllowed))
            throw new NotAllowedMoveException();

        else
            player.setPosition(destination);
    }

        /*
        This method verifies that the minimum distance between the 2 input squares
        is less than the maxDistance setted as parameter.

        How: exploring the map.
            - form the starting point i explore all the squares directly linked. (dist = 1)
            - then i explore all linked squares to the squares thar were linked to the starting point (list = 2)
     */

    public boolean isAllowedMove(Square startingPoint, Square destination, int maxDistance){
        List<Square> explored = new ArrayList<>(); //contiene gli squares esplorati
        List<Square> toExplore = new ArrayList<>(); //contiene gli square da esplorare
        int distance = 1;

        explored.add(startingPoint);

        if(startingPoint.equals(destination))
            return true;

        while(distance <= maxDistance){

            for(Square exploredSquare : explored){
                for(Directions dir: exploredSquare.getAllowedMoves()){
                    if(!explored.contains(match.getMap().getSquare(dir, exploredSquare)))
                        toExplore.add(match.getMap().getSquare(dir, exploredSquare));
                }
            }

            if(toExplore.contains(destination))
                return true;

            explored.addAll(toExplore);
            toExplore.clear();
            distance++;
        }

        return false; //uscito da while, eccesso di maxDistance
    }

    /*
        This method returns the minimum distance between 2 given squares
    */

    public int minDistBetweenSquares(Square startingPoint, Square destination){
        //TODO TEST
        List<Square> explored = new ArrayList<>(); //contiene gli squares esplorati
        List<Square> toExplore = new ArrayList<>(); //contiene gli square da esplorare
        int distance = 1;

        explored.add(startingPoint);

        if(startingPoint.equals(destination))
            return distance - 1;

        while(distance <= 10){

            for(Square exploredSquare : explored){
                for(Directions dir: exploredSquare.getAllowedMoves()){
                    if(!explored.contains(match.getMap().getSquare(dir, exploredSquare)))
                        toExplore.add(match.getMap().getSquare(dir, exploredSquare));
                }
            }

            if(toExplore.contains(destination))
                return distance;

            explored.addAll(toExplore);
            toExplore.clear();
            distance++;
        }

        return -1;
    }


}
