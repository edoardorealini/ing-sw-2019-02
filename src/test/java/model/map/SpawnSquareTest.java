package model.map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpawnSquareTest {

    private SpawnSquare sq;

    @BeforeEach
    void setUp(){
        sq = new SpawnSquare(false);
    }

    @Test
    void getActiveStatus() {
        assertEquals(sq.getActiveStatus(), false);
    }
}