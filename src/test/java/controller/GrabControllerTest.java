package controller;

import model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrabControllerTest {
    private MatchController matchController;


    @BeforeEach
    void setUp() {
        matchController = new MatchController();
        matchController.buildMap(1);
        matchController.getMap().fillAmmo(matchController.getMatch().getAmmoDeck());
        matchController.getMap().fillWeaponBox(matchController.getMatch().getWeaponDeck());
        matchController.addPlayer("Johnny",1);
        matchController.getMatch().setCurrentPlayer(matchController.getMatch().getPlayers().get(0));
        matchController.getMatch().getCurrentPlayer().setPosition(matchController.getMap().getSquareFromIndex(1,0));

    }

    @Test
    void getPlayer() {
    }

    @Test
    void setPlayer() {
    }

    @Test
    void getMatch() {
    }

    @Test
    void setMatch() {
    }

    @Test
    void grabAmmoCard(){

        try {
            matchController.grabAmmoCard();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        }

    @Test
    void grabWeapon() {
    }
}