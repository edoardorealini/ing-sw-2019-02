package model.map;
import  model.Color;

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
    private Map mapTest3;
    private Map mapTest4;

    @BeforeEach
    void setUp() {
        builderTest = new MapBuilder();
        mapTest1 = new Map();
        mapTest2 = new Map();
        mapTest3 = new Map();
        mapTest4 = new Map();
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

        try {
            mapTest3 = builderTest.makeMap(3);
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            mapTest4 = builderTest.makeMap(4);
        }catch(IOException e){
            e.printStackTrace();
        }


        assertEquals(mapTest1.getMapID(), 1);
        assertEquals(mapTest1.getSquaresMatrix()[0][0].isActive(), false);
        assertEquals(mapTest1.getSquaresMatrix()[0][1].isActive(), true);
        assertEquals(mapTest1.getSquaresMatrix()[0][2].isActive(), true);

        assertEquals(mapTest1.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest1.getSquaresMatrix()[0][2].getColor(), Color.BLUE);

        assertEquals(mapTest1.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest1.getSquaresMatrix()[1][1].getColor(), Color.RED);
        assertEquals(mapTest1.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest1.getSquaresMatrix()[0][1].getType(), SquareType.SPAWN);
        assertEquals(mapTest1.getSquaresMatrix()[0][2].getType(), SquareType.NOSPAWN);

        System.out.println(mapTest1.toString());


        assertEquals(mapTest1.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertNotEquals(mapTest1.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

        System.out.println("map1 tested OK");

        assertEquals(mapTest2.getMapID(), 2);
        assertEquals(mapTest2.getSquaresMatrix()[0][0].isActive(), true);
        assertEquals(mapTest2.getSquaresMatrix()[0][1].isActive(), true);
        assertEquals(mapTest2.getSquaresMatrix()[0][2].isActive(), true);

        assertEquals(mapTest2.getSquaresMatrix()[0][0].getColor(), Color.WHITE);
        assertEquals(mapTest2.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest2.getSquaresMatrix()[0][2].getColor(), Color.RED);

        assertEquals(mapTest2.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest2.getSquaresMatrix()[1][1].getColor(), Color.PURPLE);
        assertEquals(mapTest2.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest2.getSquaresMatrix()[0][0].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertEquals(mapTest2.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.DOWN));
        assertNotEquals(mapTest2.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

        System.out.println(mapTest2.toString());
        System.out.println("map2 tested OK");

        assertEquals(mapTest3.getMapID(), 3);
        assertEquals(mapTest3.getSquaresMatrix()[0][0].isActive(), false);
        assertEquals(mapTest3.getSquaresMatrix()[0][1].isActive(), true);
        assertEquals(mapTest3.getSquaresMatrix()[0][2].isActive(), true);

        assertEquals(mapTest3.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest3.getSquaresMatrix()[0][2].getColor(), Color.BLUE);

        assertEquals(mapTest3.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest3.getSquaresMatrix()[1][1].getColor(), Color.RED);
        assertEquals(mapTest3.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest3.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertNotEquals(mapTest3.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

        System.out.println("map3 tested OK");

        assertEquals(mapTest3.getMapID(), 3);
        assertEquals(mapTest3.getSquaresMatrix()[0][0].isActive(), false);
        assertEquals(mapTest3.getSquaresMatrix()[0][1].isActive(), true);
        assertEquals(mapTest3.getSquaresMatrix()[0][2].isActive(), true);

        assertEquals(mapTest3.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest3.getSquaresMatrix()[0][2].getColor(), Color.BLUE);

        assertEquals(mapTest3.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest3.getSquaresMatrix()[1][1].getColor(), Color.RED);
        assertEquals(mapTest3.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest3.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.RIGHT));
        assertNotEquals(mapTest3.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

        System.out.println("map4 tested OK");

        assertEquals(mapTest4.getMapID(), 4);
        assertEquals(mapTest4.getSquaresMatrix()[0][0].isActive(), true);
        assertEquals(mapTest4.getSquaresMatrix()[0][1].isActive(), true);
        assertEquals(mapTest4.getSquaresMatrix()[0][2].isActive(), true);

        assertEquals(mapTest4.getSquaresMatrix()[0][1].getColor(), Color.RED);
        assertEquals(mapTest4.getSquaresMatrix()[0][0].getColor(), Color.WHITE);

        assertEquals(mapTest4.getSquaresMatrix()[1][0].getColor(), Color.WHITE);
        assertEquals(mapTest4.getSquaresMatrix()[1][1].getColor(), Color.PURPLE);
        assertEquals(mapTest4.getSquaresMatrix()[1][2].getColor(), Color.BLUE);

        assertEquals(mapTest4.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.UP, Directions.DOWN));
        assertNotEquals(mapTest4.getSquaresMatrix()[0][1].getAllowedMoves(),Arrays.asList(Directions.LEFT, Directions.RIGHT));

    }

    @Test
    void getMap1Path(){
        assertNotEquals(builderTest.getMap1Path(), "." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "map1.json");

        System.out.println(builderTest.getMap1Path());
    }

    @Test
    void getMap2Path(){
        assertNotEquals(builderTest.getMap2Path(), "." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "map2.json");

        System.out.println(builderTest.getMap2Path());

    }
}