package model.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatusHandlerTest {

    private Player p1 = new Player("Test", 8, null);

    @BeforeEach
    void setUp() {
    }

    @Test
    void PlayerStatusHandler(){
        assertEquals(RoundStatus.waitTurn, p1.getStatus().getTurnStatus());
        assertEquals(AbilityStatus.normal, p1.getStatus().getSpecialAbility());
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
        assertEquals(AbilityStatus.adrenalinePick,p1.getStatus().getSpecialAbility());
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