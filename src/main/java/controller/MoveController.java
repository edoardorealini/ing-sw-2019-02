package controller;

import exception.NotAllowedMoveException;
import model.Match;
import model.map.Directions;
import model.player.Player;
import model.map.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This controller manages all the move actions, controls and stuff
 * @author edoardo
 */
public class MoveController {
    private Match match;

    /**
     * The constructor needs in input the match, to be linked with all the other objects in the game
     * @param realMatch single and only match created by match controller
     */
    public MoveController(Match realMatch){
        this.match = realMatch;
    }

    /**
     * moveOneSquare changes the specified player position position according to the input direction
     *         Example: used for special effects in weapons
     * @param direction direction to move to
     * @param player affected player for the move
     * @throws NotAllowedMoveException if the move is not allowed (see rules)
     */
    @Deprecated
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
    @Deprecated
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

    /**
     *Given a Player, a destination Square and the maxDistanceAllowed,
     *this method changes the player position only if the move is allowed by the map configuration.
     * @param player player to be moved
     * @param destination destination of the move action
     * @param maxDistanceAllowed integer representing the maximum distance of move allowed measured in squares
     * @throws NotAllowedMoveException if you cannot make such move with such input paramenters
     */
    public void move(Player player, Square destination, int maxDistanceAllowed) throws  NotAllowedMoveException{
        if(! isAllowedMove(player.getPosition(), destination, maxDistanceAllowed))
            throw new NotAllowedMoveException("You cannot do this move");

        else {
            player.setPosition(destination);
            System.out.println("[MOVE]: Player " + player.getNickname() + " moved to position: X = " + match.getMap().getIndex(destination).get(0) + " - Y = " + match.getMap().getIndex(destination).get(1));
        }
    }

    /**
     * This method verifies that the minimum distance between the 2 input squares
     * is less than the maxDistance setted as parameter.
     *
     * How: exploring the map.
     *     - form the starting point i explore all the squares directly linked. (dist = 1)
     *     - then i explore all linked squares to the squares thar were linked to the starting point (list = 2)
     * @param startingPoint starting point sqaure of the control
     * @param destination destination square to analize
     * @param maxDistance max distance allowed
     * @return returns true if the move is allowed, false if not
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

    /**
     * This method returns the minimum distance between 2 given squares
     * @param startingPoint sqaure starting point of evaluation
     * @param destination destination for evaluation
     * @return minimum percurrible distance
     */
    public int minDistBetweenSquares(Square startingPoint, Square destination){
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
