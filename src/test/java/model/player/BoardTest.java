package model.player;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Player p1 = new Player("Test", 8);
    private Board b1 = new Board();

    @Test
    void board() {
    }

    @Test
    void initializeBoard() {

        for (int i=0; i<12; i++) {
            assertEquals(9, b1.getLifePoints()[i]);
        }
    }

    @Test
    void updateLife() {
        b1.updateLife(7,p1.getId());

        // creo array contenente la vita che mi aspetto, cosi da far la equal
        int[] life = {p1.getId(),p1.getId(),p1.getId(),p1.getId(),p1.getId(),p1.getId(),p1.getId(),9,9,9,9,9};
        System.out.println(Arrays.toString(life));
        System.out.println(Arrays.toString(b1.getLifePoints()));

        assertEquals(Arrays.toString(b1.getLifePoints()), Arrays.toString(life));
    }

    @Test
    void updateTarget() {
        System.out.println(b1.getMarks());
        b1.updateMarks(4, p1.getId());
        System.out.println(b1.getMarks());
        // è giusto che ne aggiunga solo 3 perchè è il massimo
        // creo arrayList in cui il target è ciò che mi aspetto
        ArrayList<Integer> target = new ArrayList<>();
        target.add(0,p1.getId());
        target.add(1,p1.getId());
        target.add(2,p1.getId());
        assertEquals(target, b1.getMarks());
        // cerco di fregarlo
        b1.updateMarks(1, p1.getId());
        assertEquals(b1.getMarks(), target);

    }

    @Test
    void removeTarget() {
        Player p2 = new Player("solo per questo test", 2);
        b1.updateMarks(2,p2.getId());
        b1.updateMarks(4, p1.getId());
        b1.updateMarks(2,p2.getId());
        b1.removeMarks(2, p1.getId());
        b1.removeMarks(3, p2.getId());
        System.out.println(b1.getMarks());
    }

    @Test
    void getTarget() {
    }

    @Test
    void getLifePoints() {
    }

    @Test
    void isFullLife() {
        System.out.println(Arrays.toString(b1.getLifePoints()));
        b1.updateLife(12,p1.getId());
        System.out.println(Arrays.toString(b1.getLifePoints()));
        assertTrue(b1.isDead());
    }
}