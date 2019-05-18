package controller;

import model.*;
import model.ammo.AmmoCard;
import model.player.Player;
import model.weapons.Weapon;
import model.weapons.WeaponName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShootControllerTest {

	private MatchController matchCtrl = new MatchController();
	private Player p1 = new Player("MADSOMMA", 1);
	private Player p2 = new Player("REALNGNEER", 2);
	private Player p3 = new Player("JOHNNYCA$H", 3);
	private AmmoCard ammo = new AmmoCard(3, 3, 3, false);
	private Input input = new Input();

	@Test
	void getPlayer() {
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		assertEquals(p1, matchCtrl.getShootController().getCurrPlayer());
		for (Player p: matchCtrl.getMatch().getPlayers()) {
			System.out.println(p.getNickname());
		}
	}

	@Test
	void getMatch() {
	}

	@Test
	void payAmmo() {
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of MADSOMMA
		System.out.println(matchCtrl.getMatch().getWeaponDeck().toString());
		Weapon temp = matchCtrl.getMatch().getWeaponDeck().pickFirstCard();
		System.out.println(temp.toString());
		System.out.println(matchCtrl.getMatch().getCurrentPlayer().getAmmo().toString());
		try {
			matchCtrl.getShootController().payAmmo(temp.getCostOpt1());
			System.out.println("After payment");
			System.out.println(matchCtrl.getMatch().getCurrentPlayer().getAmmo().toString());
		} catch (Exception e) {
			System.out.println("oh oh, something has gone wrong, pagaaaa");
		}
	}

	@Test
	void shootLockRifle() {

		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of JOHNNTYCA$H
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNTYCA$H

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.LOCK_RIFLE));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setTargets(p2);
		input.setTargets(p3);
		System.out.println(input.getTargets().size());


		//executing code
		try{
			System.out.println(p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());
			matchCtrl.getShootController().shootLockRifle(input);
			System.out.println(p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());

		}catch (Exception e){
			System.out.println("shit happened");
		}

	}

	@Test
	void shootElectroScythe() {
	}

	@Test
	void shootMachineGun() {
	}


	@Test
	void shootTHOR() {
	}
}