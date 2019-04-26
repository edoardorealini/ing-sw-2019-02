package model.map;
import model.ammo.*;
import model.weapons.WeaponDeck;
import com.google.gson.*;

import java.util.*;

public class Map {

    private Square[][] squaresMatrix;
    private int mapID;

    //TODO inserire tutta la logica (metodi) per capire dato uno square chi posso vedere e che spostamenti posso fare
    /*
        allowedMoves tells me which moves i can do from a specific square.
        returns empty list if square not found in squaresMatrix.
     */
    public List<Directions> allowedMoves(Square square) {

        for (Square[] row : squaresMatrix) {
            for (Square sq: row) {
                if(sq.equals(square)){
                    return sq.getAllowedMoves();
                }
            }

        }

        return Collections.emptyList();
    }

    public List<Color> getVisibileRooms(Square square){
        List<Integer> indexes = new ArrayList<>();
        indexes = searchIndex(square);

        List<Color> visibleColors = new ArrayList<>();
        visibleColors.add(square.getColor());

        if (!square.getDoors().isEmpty()){
            if(square.getDoors().contains(Directions.UP)){
                //TODO
            }
            if(square.getDoors().contains(Directions.DOWN)){
                //TODO
            }
            if(square.getDoors().contains(Directions.RIGHT)){
                //TODO
            }
            if(square.getDoors().contains(Directions.LEFT)){
                //TODO
            }
        }

        return visibleColors;
    }

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