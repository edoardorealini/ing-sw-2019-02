package controller;

import java.util.*;
import model.Color;
import model.player.Player;
import model.powerup.PowerUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

class GrabControllerTest {
    private MatchController matchController;
    //parametri dell'ammo card
    private int blueAmmo;
    private int redAmmo;
    private int yellowAmmo;
    private boolean isTherePowerUp;
    private List<Color> weaponCost;

    @BeforeEach
    void setUp() throws Exception{
        matchController = new MatchController();
        matchController.buildMapForTest(1);
        matchController.getMap().fillAmmo(matchController.getMatch().getAmmoDeck());
        matchController.getMap().fillWeaponBox(matchController.getMatch().getWeaponDeck());
        matchController.addPlayer("Johnny",1);
        matchController.getMatch().setCurrentPlayer(matchController.getMatch().getPlayers().get(0));
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
        matchController.getMatch().getCurrentPlayer().setPosition(matchController.getMap().getSquareFromIndex(1,0));

        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getBlueAmmo(),0);
        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getRedAmmo(), 0);
        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getYellowAmmo(),0);
        assertEquals(matchController.getMatch().getCurrentPlayer().getPowerUps()[0],null);
        assertEquals(matchController.getMatch().getCurrentPlayer().getPowerUps()[1],null);
        assertEquals(matchController.getMatch().getCurrentPlayer().getPowerUps()[2],null);

        blueAmmo = matchController.getMatch().getCurrentPlayer().getPosition().getAmmoTile().getBlueAmmo();
        redAmmo = matchController.getMatch().getCurrentPlayer().getPosition().getAmmoTile().getRedAmmo();
        yellowAmmo = matchController.getMatch().getCurrentPlayer().getPosition().getAmmoTile().getYellowAmmo();
        isTherePowerUp = matchController.getMatch().getCurrentPlayer().getPosition().getAmmoTile().isTherePowerUp();

        try {
            matchController.grabAmmoCard();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getBlueAmmo(),blueAmmo);
        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getRedAmmo(),redAmmo);
        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getYellowAmmo(),yellowAmmo);
        if (isTherePowerUp){
            assertNotNull(matchController.getMatch().getCurrentPlayer().getPowerUps()[0]);
        }

        }

    @Test
    void grabWeapon() {
        // carico le munizioni per comprare
        matchController.getMatch().getCurrentPlayer().setPosition(matchController.getMap().getSquareFromIndex(0,1));
        matchController.getMatch().getCurrentPlayer().getAmmo().setBlueAmmo(3);
        matchController.getMatch().getCurrentPlayer().getAmmo().setRedAmmo(3);
        matchController.getMatch().getCurrentPlayer().getAmmo().setYellowAmmo(3);
        // salvo il costo dell'arma da comprare
        weaponCost =  matchController.getMatch().getCurrentPlayer().getPosition().getAvailableWeapons().get(0).getCost();

        try{
            matchController.grabWeapon(matchController.getMatch().getCurrentPlayer().getPosition().getAvailableWeapons().get(0));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // verifico che le munizioni rimaste siano giuste
        blueAmmo=0;
        redAmmo=0;
        yellowAmmo=0;

        for (int i=1; i<weaponCost.size(); i++) {
            switch (weaponCost.get(i)) {
                case BLUE:
                    blueAmmo++;
                    break;
                case RED:
                    redAmmo++;
                    break;
                case YELLOW:
                    yellowAmmo++;
                    break;
            }
        }

        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getYellowAmmo(),(3-yellowAmmo));
        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getRedAmmo(),(3-redAmmo));
        assertEquals(matchController.getMatch().getCurrentPlayer().getAmmo().getBlueAmmo(),(3-blueAmmo));

        assertNotNull(matchController.getMatch().getCurrentPlayer().getWeapons()[0]);





    }
}