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
}