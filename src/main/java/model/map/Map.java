package model.map;
import java.util.*;
import  model.Color;
import model.ammo.AmmoDeck;
import model.weapons.WeaponDeck;

public class Map {

    private Square[][] squaresMatrix;
    private int mapID;

    /*
        NB: non fare mai la new di Map, va costruito con MapBuilder
    */

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

    /*
        getSquare returns a square reference to the square near the input square
        in the specified direction
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

    /*
        private method used to get the indexes of a square in the matrix.
        the indexes are returned as a list of integers.
    */
    private List<Integer> getIndex(Square square){
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

    /*
        Riempie la WeaponBox con le armi prese a caso dal deck
     */
    public void fillWeaponBox(WeaponDeck deck){
        int i;
        int j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 3; j++){
                if(squaresMatrix[i][j].getType() == SquareType.SPAWN){
                    squaresMatrix[i][j].getWeaponBox().add(deck.pickFirstCard());
                    squaresMatrix[i][j].getWeaponBox().add(deck.pickFirstCard());
                    squaresMatrix[i][j].getWeaponBox().add(deck.pickFirstCard());
                }
            }
        }
    }

    /*
        Riempie le ammo di tutta la mappa prendendo dal deck di ammo
    */
    public void fillAmmo(AmmoDeck deck){
        int i;
        int j;
        for(i = 0; i < 4; i++){
            for(j = 0; j < 3; j++){
                if(squaresMatrix[i][j].getType() == SquareType.NOSPAWN){
                    squaresMatrix[i][j].setAmmoTile(deck.pickFirstCard());
                    squaresMatrix[i][j].setAmmoTile(deck.pickFirstCard());
                    squaresMatrix[i][j].setAmmoTile(deck.pickFirstCard());
                }
            }
        }
    }

    /*
        getAllSquaresInDirection gives in return a list of all the squares in a direction
        from the starting point given in input, giving no fucks about the walls
    */
    public List<Square> getAllSquaresInDirection(Directions direction, Square square){

        List<Square> result = new ArrayList<>();
        result = Collections.emptyList();

        int x = getIndex(square).get(0);
        int y = getIndex(square).get(1);

        switch(direction){
            case UP:
                for(int k = x; k < 4; k++){
                    result.add(squaresMatrix[k][y]);
                }
                break;

            case DOWN:
                for(int k = x; k >= 0; k--){
                    result.add(squaresMatrix[k][y]);
                }
                break;

            case RIGHT:
                for(int k = y; k < 3; k++){
                    result.add(squaresMatrix[x][k]);
                }
                break;

            case LEFT:
                for(int k = x; k >= 0; k--){
                    result.add(squaresMatrix[x][k]);
                }
                break;
        }

        return result;
    }

    /*
        getAllSquaresInDirection gives in return a list of all the squares in a direction
        from the starting point given in input, walls BLOCK the direction
    */
    public List<Square> getAllowedSquaresInDirection(Directions direction, Square square){

        List<Square> result = new ArrayList<>();
        result = Collections.emptyList();

        int x = getIndex(square).get(0);
        int y = getIndex(square).get(1);

        switch(direction){
            case UP:
                for(int k = x; k < 4 && square.getAllowedMoves().contains(direction); k++){
                    if(squaresMatrix[k][y].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[k][y]);
                }
                break;

            case DOWN:
                for(int k = x; k >= 0 && square.getAllowedMoves().contains(direction); k--){
                    if(squaresMatrix[k][y].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[k][y]);
                }
                break;

            case RIGHT:
                for(int k = y; k < 3 && square.getAllowedMoves().contains(direction); k++){
                    if(squaresMatrix[x][k].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[x][k]);
                }
                break;

            case LEFT:
                for(int k = x; k >= 0 && square.getAllowedMoves().contains(direction); k--){
                    if(squaresMatrix[x][k].getAllowedMoves().contains(direction))
                        result.add(squaresMatrix[x][k]);
                }
                break;
        }

        return result;
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
                    if(!explored.contains(getSquare(dir, exploredSquare)))
                        toExplore.add(getSquare(dir, exploredSquare));
                }

                if(toExplore.contains(destination))
                    return true;
            }

            explored.addAll(toExplore);
            toExplore.clear();
            distance++;
        }

        return false; //uscito da while, eccesso di maxDistance
    }

    //FOR TEST USE ONLY for now
    public Square getSquare(int i, int j){
        return squaresMatrix[i][j];
    }


}