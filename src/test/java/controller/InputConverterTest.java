package controller;

import model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputConverterTest {
    Match match = new Match();
    InputConverter ic = new InputConverter(match);
    MatchController mc = new MatchController(match);

    //TODO
    @BeforeEach
    void setUp() {
        try {
            mc.buildMapForTest(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void indexToSquare() {
        try{
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 3; j++)
                    assertEquals(ic.indexToSquare(i, j), match.getMap().getSquaresMatrix()[i][j]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void nameToPlayer() {
        try {
            mc.addPlayer("ciccio");
            mc.addPlayer("bonzo");
            assertEquals(ic.nameToPlayer("ciccio"), mc.getMatch().getPlayer("ciccio"));
            assertEquals(ic.nameToPlayer("bonzo"), mc.getMatch().getPlayer("bonzo"));

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}