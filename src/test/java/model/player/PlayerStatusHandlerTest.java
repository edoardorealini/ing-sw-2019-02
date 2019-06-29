package model.player;

import controller.MatchController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatusHandlerTest {

    private MatchController matchController = new MatchController();
    private Player p1 = new Player("Test", 8, matchController.getMatch());

    @Test
    void PlayerStatusHandler(){
        assertNotEquals(RoundStatus.WAIT_TURN, p1.getStatus().getTurnStatus());
        assertEquals(AbilityStatus.NORMAL, p1.getStatus().getSpecialAbility());
    }

    @Test
    void setSpecialAbilityAdrenalinePick() {
        p1.getStatus().setSpecialAbilityAdrenalinePick();
        assertEquals(AbilityStatus.ADRENALINE_PICK,p1.getStatus().getSpecialAbility());
    }

}