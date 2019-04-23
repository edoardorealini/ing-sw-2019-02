package model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {

        p1 = new Player("johnny", 0);
        p2 = new Player("edoz", 1);
    }

    @Test
    void getNickname() {

        assertEquals(p1.getNickname(), "johnny");
        assertEquals(p2.getNickname(), "edoz");
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
        assertNull(p1.getPosition());
        assertNull(p2.getPosition());
    }

    @Test
    void setNickname1() {
    }

    @Test
    void getId1() {
        assertEquals(p1.getId(), 0);
        assertEquals(p2.getId(), 1);
    }

    @Test
    void setId1() {
    }


    @Test
    void setPosition() {
    }

    @Test
    void getWeapons() {

    }

    @Test
    void addWeapons() {
    }

    @Test
    void removeWeapons() {
    }

    @Test
    void getAmmo() {
    }

    @Test
    void addAmmo() {
    }

    @Test
    void removeAmmo() {
    }

    @Test
    void getPowerUps() {
    }

    @Test
    void addPowerUps() {
    }

    @Test
    void removePowerUps() {
    }

    @Test
    void getPoints() {
    }

    @Test
    void addPoints() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void setStatus() {
    }

    @Test
    void trueDead() {
    }

    @Test
    void falseDead() {
    }
}