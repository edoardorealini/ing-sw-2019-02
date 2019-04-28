package model.map;
import model.ammo.*;
import model.weapons.*;
import com.google.gson.*;

import java.util.*;

public class Map {

    private Square[][] squaresMatrix;
    private int mapID;

    /*
        allowedMoves tells me which moves i can do from a specific square.
        returns empty list if square not found in squaresMatrix.
    */
    public List<Directions> getAllowedMoves(Square square) {

        for (Square[] row : squaresMatrix) {
            for (Square sq: row) {
                if(sq.equals(square)){
                    return sq.getAllowedMoves();
                }
            }

        }

        return Collections.emptyList();
    }

    /*
        getVisibleRooms returns a list containing the colours of the visible rooms
        with this information the player is able to know who can see.
    */
    public List<Color> getVisibileRooms(Square square){

        int i = searchIndex(square).get(0);
        int j = searchIndex(square).get(1);

        List<Color> visibleColors = new ArrayList<>();
        visibleColors.add(square.getColor());

        if (!square.getDoors().isEmpty()){
            if(square.getDoors().contains(Directions.UP)){
                visibleColors.add(squaresMatrix[i][j + 1].getColor());
            }
            if(square.getDoors().contains(Directions.DOWN)){
                visibleColors.add(squaresMatrix[i][j - 1].getColor());
            }
            if(square.getDoors().contains(Directions.RIGHT)){
                visibleColors.add(squaresMatrix[i + 1][j].getColor());
            }
            if(square.getDoors().contains(Directions.LEFT)){
                visibleColors.add(squaresMatrix[i - 1][j].getColor());
            }
        }

        return visibleColors;
    }

    /*
        private method used to get the indexes of a square in the matrix.
        the indexes are returned as a list of integers.
    */
    private List<Integer> searchIndex(Square square){
        int i;
        int j;

        List<Integer> result = new ArrayList<>();

        for(i = 0; i < 4; i++){
            for(j = 0; j < 3; j++){
                if(square.equals(squaresMatrix[i][j])){
                    result.add(i);
                    result.add(j);
                    return result;
                }
            }
        }
        return Collections.emptyList();
    }

    public int getMapID() {
        return mapID;
    }

    public Square[][] getSquaresMatrix() {
        return squaresMatrix;
    }

}