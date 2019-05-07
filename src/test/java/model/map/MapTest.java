package model.map;

import model.map.MapBuilder;
import  model.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    private Map mapTest;
    private MapBuilder builder;
    @BeforeEach
    void setUp(){
        builder = new MapBuilder();
        try {
            mapTest = builder.makeMap(1);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void getAllowedMoves(){
        assertEquals(mapTest.getAllowedMoves(mapTest.getSquaresMatrix()[1][0]), Arrays.asList(Directions.UP, Directions.RIGHT));
    }

    @Test
    void getVisibileRooms(){
        assertEquals(mapTest.getVisibileRooms(mapTest.getSquaresMatrix()[1][0]), Arrays.asList(Color.WHITE, Color.RED, Color.YELLOW));
    }

    @Test
    void getMapID(){
        assertEquals(mapTest.getMapID(), 1);
    }

    @Test
    void getSquare(){
        //TODO
    }

    @Test
    void isAllowedMove(){
        //TODO controllare tutti i json delle mappe, ci sono errori nelle allowedMoves

        Square startingPoint = mapTest.getSquare(0,2);
        Square destination = mapTest.getSquare(3,0);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 3) , false);

        startingPoint = mapTest.getSquare(0,2);
        destination = mapTest.getSquare(2,1);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 3) , true);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 2) , false);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 1) , false);

        //System.out.println(mapTest.getSquare(1,1).getAllowedMoves().toString());
        startingPoint = mapTest.getSquare(0,2);
        destination = mapTest.getSquare(1,0);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 3) , true);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 2) , false);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 1) , false);

        startingPoint = mapTest.getSquare(0,1);
        destination = mapTest.getSquare(2,0);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 3) , true);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 2) , false);
        assertEquals(mapTest.isAllowedMove(startingPoint, destination, 1) , false);

    }
}