package model.map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MapBuilderTest {

    private MapBuilder builderTest;
    private Map mapTest;

    @BeforeEach
    void setUp() {
        builderTest = new MapBuilder();
        mapTest = new Map();
    }

    @Test
    void makeMap(){
        try {
            mapTest = builderTest.makeMap(1);
        }catch(IOException e){
            e.printStackTrace();
        }

        assertEquals(mapTest.getMapID(), 1);
        assertEquals(mapTest.getSquaresMatrix()[0][0].getActiveStatus(), false);
        assertEquals(mapTest.getSquaresMatrix()[0][1].getActiveStatus(), true);
        assertEquals(mapTest.getSquaresMatrix()[0][2].getActiveStatus(), true);

        assertEquals(mapTest.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest.getSquaresMatrix()[0][2].getColor(), Color.BLUE);

        assertEquals(mapTest.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest.getSquaresMatrix()[1][1].getColor(), Color.RED);
        assertEquals(mapTest.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertNotEquals(mapTest.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

    }

    @Test
    void getMap1Path(){
        assertEquals(builderTest.getMap1Path(), "." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "map1.json");
    }
}