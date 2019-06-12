package model.player;

import controller.MatchController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private MatchController matchController = new MatchController();
    private Player currPlayer = new Player("JJ", 1 , matchController.getMatch());
    private Player targetPlayer = new Player("MAD", 2, matchController.getMatch());
    private Board b1 = new Board();

    @Test
    void board() {
    }

    @Test
    void initializeBoard() {

        for (int i=0; i<11; i++) {
            assertEquals(9, b1.getLifePoints()[i]);
        }
    }

    @Test
    void updateLife() {
        b1.updateLife(7,currPlayer.getId(), targetPlayer.getId());

        // creo array contenente la vita che mi aspetto, cosi da far la equal
        int[] life = {currPlayer.getId(),currPlayer.getId(),currPlayer.getId(),currPlayer.getId(),currPlayer.getId(),currPlayer.getId(),currPlayer.getId(),9,9,9,9};
        System.out.println(Arrays.toString(life));
        System.out.println(Arrays.toString(b1.getLifePoints()));

        //assertEquals(Arrays.toString(b1.getLifePoints()), Arrays.toString(life));
    }

    @Test
    void updateTarget() {
        System.out.println(b1.getMarks());
        b1.updateMarks(4, currPlayer.getId(), targetPlayer.getId());
        System.out.println(b1.getMarks());
        // è giusto che ne aggiunga solo 3 perchè è il massimo
        // creo arrayList in cui il target è ciò che mi aspetto
        ArrayList<Integer> target = new ArrayList<>();
        target.add(0,currPlayer.getId());
        target.add(1,currPlayer.getId());
        target.add(2,currPlayer.getId());
        assertEquals(target, b1.getMarks());
        // cerco di fregarlo
        b1.updateMarks(1, currPlayer.getId(), targetPlayer.getId());
        assertEquals(b1.getMarks(), target);

    }

    @Test
    void removeTarget() {
        Player p2 = new Player("solo per questo test", 2, matchController.getMatch());
        b1.updateMarks(2,p2.getId(), targetPlayer.getId());
        b1.updateMarks(4, currPlayer.getId(), targetPlayer.getId());
        b1.updateMarks(2,p2.getId(), targetPlayer.getId());
        b1.removeMarks(2, currPlayer.getId());
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
        b1.updateLife(12,currPlayer.getId(), targetPlayer.getId());
        System.out.println(Arrays.toString(b1.getLifePoints()));
        assertTrue(b1.isDead());
    }
}