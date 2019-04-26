package model.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MapBuilderTest {

    private MapBuilder builderTest;
    private Map mapTest1;
    private Map mapTest2;

    @BeforeEach
    void setUp() {
        builderTest = new MapBuilder();
        mapTest1 = new Map();
        mapTest2 = new Map();
    }

    @Test
    void makeMap(){
        try {
            mapTest1 = builderTest.makeMap(1);
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            mapTest2 = builderTest.makeMap(2);
        }catch(IOException e){
            e.printStackTrace();
        }

        assertEquals(mapTest1.getMapID(), 1);
        assertEquals(mapTest1.getSquaresMatrix()[0][0].getActiveStatus(), false);
        assertEquals(mapTest1.getSquaresMatrix()[0][1].getActiveStatus(), true);
        assertEquals(mapTest1.getSquaresMatrix()[0][2].getActiveStatus(), true);

        assertEquals(mapTest1.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest1.getSquaresMatrix()[0][2].getColor(), Color.BLUE);

        assertEquals(mapTest1.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest1.getSquaresMatrix()[1][1].getColor(), Color.RED);
        assertEquals(mapTest1.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest1.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertNotEquals(mapTest1.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

        assertEquals(mapTest2.getMapID(), 2);
        assertEquals(mapTest2.getSquaresMatrix()[0][0].getActiveStatus(), true);
        assertEquals(mapTest2.getSquaresMatrix()[0][1].getActiveStatus(), true);
        assertEquals(mapTest2.getSquaresMatrix()[0][2].getActiveStatus(), true);

        assertEquals(mapTest2.getSquaresMatrix()[0][0].getColor(), Color.WHITE);
        assertEquals(mapTest2.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest2.getSquaresMatrix()[0][2].getColor(), Color.RED);

        assertEquals(mapTest2.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest2.getSquaresMatrix()[1][1].getColor(), Color.PURPLE);
        assertEquals(mapTest2.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest2.getSquaresMatrix()[0][0].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertEquals(mapTest2.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.DOWN));
        assertNotEquals(mapTest2.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

    }

    @Test
    void getMap1Path(){
        assertEquals(builderTest.getMap1Path(), "." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "map1.json");

        System.out.println(builderTest.getMap1Path());
    }

    @Test
    void getMap2Path(){
        assertEquals(builderTest.getMap2Path(), "." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "map2.json");

        System.out.println(builderTest.getMap2Path());

    }
}