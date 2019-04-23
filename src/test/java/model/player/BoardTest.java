package model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Player p1 = new Player("Test", 8);

    @BeforeEach
    void setUp() {
    }

    @Test
    void board() {
    }

    @Test
    void initializeBoard() {
        int[] b = p1.getBoard().getLifePoints();
        for (int i=0; i<12; i++) {
            assertEquals(9, b[i]);
        }
    }

    @Test
    void updateLife() {
        p1.getBoard().updateLife(7,p1.getId());

        // creo array contenente la vita che mi aspetto, cosi da far la equal
        int[] life = {p1.getId(),p1.getId(),p1.getId(),p1.getId(),p1.getId(),p1.getId(),p1.getId(),9,9,9,9,9};

        assertEquals(p1.getBoard().getLifePoints(), life);
    }

    @Test
    void updateTarget() {
        p1.getBoard().updateTarget(4, p1.getId());
        // creo arrayList in cui il target è ciò che mi aspetto
        ArrayList<Integer> target = new ArrayList<>();
        target.add(0,p1.getId());
        target.add(1,p1.getId());
        target.add(2,p1.getId());

        assertEquals(target, p1.getBoard().getTarget());

    }

    @Test
    void removeTarget() {
        p1.getBoard().updateTarget(4, p1.getId());
        p1.getBoard().removeTarget(4, p1.getId());
        assertEquals(0,p1.getBoard().getTarget().size());
    }

    @Test
    void getTarget() {
    }

    @Test
    void getLifePoints() {
    }

    @Test
    void isFullLife() {
        p1.getBoard().updateLife(12,p1.getId());
        assertTrue(p1.getBoard().isFullLife());
    }
}