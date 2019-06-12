package model.player;

import controller.MatchController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatusHandlerTest {

    private MatchController matchController = new MatchController();
    private Player p1 = new Player("Test", 8, matchController.getMatch());

    @BeforeEach
    void setUp() {
    }

    @Test
    void PlayerStatusHandler(){
        assertNotEquals(RoundStatus.WAIT_TURN, p1.getStatus().getTurnStatus());
        assertEquals(AbilityStatus.NORMAL, p1.getStatus().getSpecialAbility());
    }
    @Test
    void getTurnStatus() {
    }

    @Test
    void getSpecialAbility() {
    }

    @Test
    void setSpecialAbilityNormal() {
    }

    @Test
    void setSpecialAbilityAdrenalinePick() {
        p1.getStatus().setSpecialAbilityAdrenalinePick();
        assertEquals(AbilityStatus.ADRENALINE_PICK,p1.getStatus().getSpecialAbility());
    }

    @Test
    void setSpecialAbilityFrenzy() {
    }

    @Test
    void setSpecialAbilityAdrenalineShoot() {
    }

    @Test
    void setTurnStatusMaster() {
    }

    @Test
    void setTurnStatusWaitTurn() {
    }

    @Test
    void setTurnStatusFirstAction() {
    }

    @Test
    void setTurnStatusSecondAction() {
    }

    @Test
    void setTurnStatusReloading() {
    }

    @Test
    void setTurnStatusEndTurn() {
    }
}