package controller;

import exception.NotEnoughAmmoException;
import model.Color;
import model.ammo.AmmoCard;
import model.player.Player;
import model.powerup.PowerUp;
import model.powerup.PowerUpName;
import model.weapons.Weapon;
import model.weapons.WeaponAmmoStatus;
import model.weapons.WeaponName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchControllerTest {

    private MatchController matchController;
    private Player p0;
    private Player p1;
    private Player p2;
    private Player p3;
    private AmmoCard ammo = new AmmoCard(3, 3, 3, false);
    private ShootingParametersInput input = new ShootingParametersInput();

    @BeforeEach
    void setUp() {
        try {
            matchController = new MatchController();
            matchController.buildMapForTest(1);
            p0 = new Player("MADSOMMA", 0, matchController.getMatch());
            p1 = new Player("REALNGNEER", 1, matchController.getMatch());
            p2 = new Player("JOHNNYCA$H", 2, matchController.getMatch());
            p3 = new Player("AHHHH", 3, matchController.getMatch());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    @Test
    void getMap() {
    }

    @Test
    void shootTestLockRifle() {
        //setting players and map
        matchController.getMatch().setCurrentPlayer(p0);
        p0.getStatus().setTurnStatusFirstAction();
        matchController.getMatch().setPlayers(p0);
        matchController.getMatch().setPlayers(p2);
        matchController.getMatch().setPlayers(p1);
        matchController.getMatch().getCurrentPlayer().addAmmoCard(ammo);
        matchController.getShootController().getCurrPlayer().setPosition(matchController.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of MADSOMMA
        matchController.getMatch().getPlayers().get(1).setPosition(matchController.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of JOHNNTYCA$H
        matchController.getMatch().getPlayers().get(2).setPosition(matchController.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNTYCA$H

        //setting the input
        input.setWeapon(matchController.getMatch().getWeaponDeck().getWeapon(WeaponName.LOCK_RIFLE));
        input.getWeapon().setWeaponStatus(WeaponAmmoStatus.LOADED);
        input.setShootModes(ShootMode.BASIC);
        input.setShootModes(ShootMode.OPTIONAL1);
        input.setTargets(p1);
        input.setTargets(p2);


        //executing code
        try {
            matchController.shoot(input);
            System.out.println(p1.getBoard().toStringLP());
            System.out.println(p1.getBoard().toStringMarks());
            System.out.println(p2.getBoard().toStringLP());
            System.out.println(p2.getBoard().toStringMarks());

        } catch (Exception e){
            System.out.println("shit happened");
            e.printStackTrace();
        }
    }

    @Test
    void reloadWeapon() {
        matchController.getMatch().setCurrentPlayer(p0);
        //p0.getStatus().setTurnStatusFirstAction();
        matchController.getMatch().setPlayers(p0);
        matchController.getMatch().getCurrentPlayer().addAmmoCard(ammo);
        PowerUp pow1 = new PowerUp(Color.RED, PowerUpName.TELEPORTER);
        PowerUp pow2 = new PowerUp(Color.YELLOW, PowerUpName.TAGBACK_GRENADE);
        PowerUp pow3 = new PowerUp(Color.BLUE, PowerUpName.TAGBACK_GRENADE);

        try {
            Weapon weap = matchController.getMatch().getWeaponDeck().getWeapon(WeaponName.LOCK_RIFLE);
            weap.setWeaponStatus(WeaponAmmoStatus.UNLOADED);
            matchController.reloadWeapon(weap);
        } catch (NotEnoughAmmoException e) {
            e.printStackTrace();
        }
    }

    @Test
    void endOfTurn() {
        matchController.getMatch().setCurrentPlayer(p0);
        matchController.getMatch().setPlayers(p0);
        matchController.getMatch().setPlayers(p1);
        matchController.getMatch().setPlayers(p2);
        matchController.getMatch().setPlayers(p3);
        p1.getBoard().updateLife(2, 0, 1);
        p1.getBoard().updateLife(5, 2, 1);
        p1.getBoard().updateLife(5, 3, 1);
        //p1.getBoard().updateLife(1, 0, 1);
        //p1.getBoard().updateLife(2, 3, 1);
        p1.trueDead();

        System.out.println(p1.getNickname()+ "'s " + p1.getBoard().toStringLP());
        System.out.println(p1.isDead());
        System.out.println("\n");

        for (Player p : matchController.getMatch().getPlayers()) {
            System.out.println(p.getNickname()+ "'s points:" + p.getPoints());
        }

        matchController.endOfTurn();
        System.out.println("\n");

        for (Player p : matchController.getMatch().getPlayers()) {
            System.out.println(p.getNickname()+ "'s points:" + p.getPoints());
        }

        System.out.println("\n");
        System.out.println(p1.getNickname()+ "'s " + p1.getBoard().toStringLP());
        System.out.println(p1.isDead());
    }


}