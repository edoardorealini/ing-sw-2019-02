package model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player p1;

    @BeforeEach
    void setUp() {
        p1 = new Player();
    }

    @Test
    void getNickname() {
        assertEquals(p1.getNickname(), "johnny");
    }

    @Test
    void setNickname() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void getPosition() {
    }
}