package model.map;

import  model.Color;
import model.ammo.AmmoDeck;
import model.weapons.WeaponDeck;

import java.io.Serializable;
import java.util.*;

/**
 * The class map represents the arena of the game.
 * There are 4 different map configurations, each one has different rooms, colors and spawn points
 * Each map is coded into the map*.json files
 * @author edoardo
 */
public class Map implements Serializable {

    private Square[][] squaresMatrix;
    private int mapID;

    /**
     * This method is used to get the spawn point as a square given the color of the room (each room has one spawn point)
     * @param color Color of the room
     * @return  Spawn point square in the given color's room
     * @see Color
     */
    public Square getSpawnSquareFromColor(Color color){
        for (Square[] row : squaresMatrix) {
            for (Square sq: row) {
                if(sq.isActive()) {
                    if (sq.getColor().equals(color) && sq.getType().equals(SquareType.SPAWN)) {
                        return sq;
                    }
                }
            }

        }
        return null;
    }

    /*
        allowedMoves tells me which moves i can do from a specific square
        returns empty list if square not found in squaresMatrix
    */

    /**
     * getAllowedMoves tells me which moves i can do from a specific square
     * @param square square is the starting point from the allowed moves are checked
     * @return Returns a list of squares, the ones in allowed directions from the input square. Return null if there are no allowed moves (never)
     * @see Square
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
    */

    /**
     * getVisibleRooms: with this information the player is able to know the rooms and then who can see.
     * @param square square is the starting point for the evaluation of the visible rooms
     * @return returns a list containing the colors of the visible rooms
     * @see Square
     */
    public List<Color> getVisibileRooms(Square square){

        int i = getIndex(square).get(0);
        int j = getIndex(square).get(1);

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

    /**
     * This method is used to get ad adiacent square from a starting point is a certain direction
     * @param direction direction is the wanted direction in which to search
     * @param startingSquare startingSquare is the starting point for the evaluation
     * @return returns a square reference to the square near the input square
     * @see Square
     * @see Directions
     */
    public Square getSquare(Directions direction, Square startingSquare){
        Square result = new Square();

        int i = getIndex(startingSquare).get(0);
        int j = getIndex(startingSquare).get(1);

        switch(direction){
            case UP:
                result = squaresMatrix[i][j + 1];
                break;

            case DOWN:
                result = squaresMatrix[i][j - 1];
                break;

            case LEFT:
                result = squaresMatrix[i - 1][j];
                break;

            case RIGHT:
                result = squaresMatrix[i + 1][j];
                break;
        }

        return result;

    }

    /**
     * This method gives back the coordinates of a square in the map
     * @param square square in the matrix, abstract representation of the map
     * @return the indexes are returned as a list of integers
     * @see Square
     */
    public List<Integer> getIndex(Square square){
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

    /**
     * Used to get the ID of a map (this)
     * @return returns the mapID of this
     */
    public int getMapID() {
        return mapID;
    }

    public Square[][] getSquaresMatrix() {
        return squaresMatrix;
    }

    /*
        Riempie la WeaponBox con le armi prese a caso dal deck
     */

    /**
     * fillWeaponBox fills all the weapon boxes in the map
     * @param deck deck is the WeaponDeck
     * @see WeaponDeck
     */
    public void fillWeaponBox(WeaponDeck deck){
        int i;
        int j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 3; j++){
                if(squaresMatrix[i][j].isActive()){
                    if(squaresMatrix[i][j].getType() == SquareType.SPAWN){
                        squaresMatrix[i][j].addWeapon(deck.pickFirstCard());
                        squaresMatrix[i][j].addWeapon(deck.pickFirstCard());
                        squaresMatrix[i][j].addWeapon(deck.pickFirstCard());
                    }
                }
            }
        }
    }

    /**
     * fillAmmo fills all the ammo boxes in the map
     * @param deck deck is the AmmoDeck
     * @see AmmoDeck
     */
    public void fillAmmo(AmmoDeck deck){
        int i;
        int j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 3; j++){
                if(squaresMatrix[i][j].isActive()){
                    if (squaresMatrix[i][j].getType() == SquareType.NOSPAWN) {
                        squaresMatrix[i][j].setAmmoTile(deck.pickFirstCard());
                        squaresMatrix[i][j].setAmmoTile(deck.pickFirstCard());
                        squaresMatrix[i][j].setAmmoTile(deck.pickFirstCard());
                    }
                }
            }
        }
    }

    /*
        getAllSquaresInDirection gives in
        from the starting point given in input, giving no fucks about the walls
    */

    /**
     *This method returns all (without using rules, such as moving through walls) the squares in a certain direction from a square starting point
     * @param direction direction to analyze
     * @param square square starting point
     * @return return a list of ALL the squares in a direction
     * @see Directions
     * @see Square
     */
    public List<Square> getAllSquaresInDirection(Directions direction, Square square){

        List<Square> result = new ArrayList<>();

        int x = getIndex(square).get(0);
        int y = getIndex(square).get(1);

        switch(direction){
            case UP:
                for(int k = y; k < 3; k++){
                    result.add(squaresMatrix[x][k]);
                }
                break;

            case DOWN:
                for(int k = y; k >= 0; k--){
                    result.add(squaresMatrix[x][k]);
                }
                break;

            case RIGHT:
                for(int k = x; k < 4; k++){
                    result.add(squaresMatrix[k][y]);
                }
                break;

            case LEFT:
                for(int k = x; k >= 0; k--){
                    result.add(squaresMatrix[k][y]);
                }
                break;
        }

        return result;
    }

    /*
        getAllSquaresInDirection
        from the starting point given in input, walls BLOCK the direction
    */

    /**
     * This method gives back all the allowed (if there is a wall the evaluation stops) in a certain direction
     * @param direction direction to analyze
     * @param square square is the starting point for the evaluation
     * @return gives in return a list of all allowed squares in a direction
     * @see Directions
     * @see Square
     */
    public List<Square> getAllowedSquaresInDirection(Directions direction, Square square){

        List<Square> result = new ArrayList<>();

        int x = getIndex(square).get(0);
        int y = getIndex(square).get(1);

        result.add(squaresMatrix[x][y]);

        switch(direction){
            case UP:
                for(int k = y; k < 3 && square.getAllowedMoves().contains(direction); k++){
                    if(squaresMatrix[x][k].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[x][k + 1]);
                }
                break;

            case DOWN:
                for(int k = y; k >= 0 && square.getAllowedMoves().contains(direction); k--){
                    if(squaresMatrix[x][k].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[x][k - 1]);
                }
                break;

            case RIGHT:
                for(int k = x; k < 4 && square.getAllowedMoves().contains(direction); k++){
                    if(squaresMatrix[k][y].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[k + 1][y]);
                }
                break;

            case LEFT:
                for(int k = x; k >= 0 && square.getAllowedMoves().contains(direction); k--){
                    if(squaresMatrix[k][y].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[k - 1][y]);
                }
                break;
        }

        return result;
    }


    /**
     *  This method returns a square given a couple of coordinates
     * @param x x coordinate in matrix
     * @param y y coordinate in matrix
     * @return returns the square object from the indexes
     */
    public Square getSquareFromIndex(int x, int y){
        return squaresMatrix[x][y];
    }


    //USELESS, CLI NOT IMPLEMENTED

    @Override
    public String toString(){

        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";

        String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        String ANSI_RED_BACKGROUND = "\u001B[41m";
        String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        String ANSI_WHITE_BACKGROUND = "\u001B[47m";

        String map = null;

        switch(this.mapID) {

            case 1:
                map = "===================================\n" +
                        "||" + ANSI_BLUE_BACKGROUND + "       :       :       " + ANSI_RESET + "|" + ANSI_GREEN_BACKGROUND + "       " + ANSI_RESET + "||\n" +
                        "||" + ANSI_BLUE_BACKGROUND + "       :       :       " + ANSI_RESET + " " + ANSI_GREEN_BACKGROUND + "       " + ANSI_RESET + "||\n" +
                        "||" + ANSI_BLUE_BACKGROUND + "       :       :       " + ANSI_RESET + "|" + ANSI_GREEN_BACKGROUND + "       " + ANSI_RESET + "||\n" +
                        "||--  ---------------  ------  --||\n" +
                        "||" + ANSI_RED_BACKGROUND + "       :       " + ANSI_RESET + "|" + ANSI_YELLOW_BACKGROUND + "       :       " + ANSI_RESET + "||\n" +
                        "||" + ANSI_RED_BACKGROUND + "       :       " + ANSI_RESET + "|" + ANSI_YELLOW_BACKGROUND + "       :       " + ANSI_RESET + "||\n" +
                        "||" + ANSI_RED_BACKGROUND + "       :       " + ANSI_RESET + "|" + ANSI_YELLOW_BACKGROUND + "       :       " + ANSI_RESET + "||\n" +
                        "=========---  ----"+ ANSI_YELLOW_BACKGROUND + "-------:-------" + ANSI_RESET + "||\n" +
                        "        ||" + ANSI_WHITE_BACKGROUND + "       " + ANSI_RESET + "|" + ANSI_YELLOW_BACKGROUND + "       :       " + ANSI_RESET + "||\n" +
                        "        ||" + ANSI_WHITE_BACKGROUND + "       " + ANSI_RESET + " " + ANSI_YELLOW_BACKGROUND + "       :       " + ANSI_RESET + "||\n" +
                        "        ||" + ANSI_WHITE_BACKGROUND + "       " + ANSI_RESET + "|" + ANSI_YELLOW_BACKGROUND + "       :       " + ANSI_RESET + "||\n" +
                        "        ===========================\n";
                break;

            case 2:
                map = "--------------------------\n" +
                        "|" + ANSI_RED_BACKGROUND + "       " + ANSI_RESET + "||"+ANSI_BLUE_BACKGROUND+"       :       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_RED_BACKGROUND + "       " + ANSI_RESET + "  "+ANSI_BLUE_BACKGROUND+"       :       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_RED_BACKGROUND + "       " + ANSI_RESET + "||"+ANSI_BLUE_BACKGROUND+"       :       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_RED_BACKGROUND +" - - - " + ANSI_RESET + "-----  ------  ------------\n" +
                        "|" + ANSI_RED_BACKGROUND + "       "+ ANSI_RESET + "||" + ANSI_PURPLE_BACKGROUND + "       " + ":" + "       " + ANSI_RESET + "||" + ANSI_YELLOW_BACKGROUND +"       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_RED_BACKGROUND + "       "+ ANSI_RESET + "||" + ANSI_PURPLE_BACKGROUND + "       " + ":" + "       " + ANSI_RESET + "  " + ANSI_YELLOW_BACKGROUND +"       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_RED_BACKGROUND + "       "+ ANSI_RESET + "||" + ANSI_PURPLE_BACKGROUND + "       " + ":" + "       " + ANSI_RESET + "||" + ANSI_YELLOW_BACKGROUND +"       " + ANSI_RESET + "|\n" +
                        "|--  --------  ------------" + ANSI_YELLOW_BACKGROUND + " - - - " + ANSI_RESET + "|\n" +
                        "|" + ANSI_WHITE_BACKGROUND + "       " + ": " + "       :       " + ANSI_RESET + "||" + ANSI_YELLOW_BACKGROUND + "       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_WHITE_BACKGROUND + "       " + ": " + "       :       " + ANSI_RESET + "||" + ANSI_YELLOW_BACKGROUND + "       " + ANSI_RESET + "|\n" +
                        "|" + ANSI_WHITE_BACKGROUND + "       " + ": " + "       :       " + ANSI_RESET + "||" + ANSI_YELLOW_BACKGROUND + "       " + ANSI_RESET + "|\n" +
                        "-----------------------------------\n";

                break;
        }
        return map;
    }


}