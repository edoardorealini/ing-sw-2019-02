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
        int i;
        int j;

        for(i = 0; i < 4; i++){
            for(j = 0; j < 3; j++){
                if(square.equals(squaresMatrix[i][j])){
                    return squaresMatrix[i][j].getAllowedMoves();
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

    public String printSquare(int i, int j){
        return squaresMatrix[i][j].toString();
    }

    /*
    test method to understand the json structure of the class
     */
}