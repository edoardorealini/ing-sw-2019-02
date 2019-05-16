package controller;

import model.*;
import model.ammo.AmmoCard;
import model.player.Player;
import model.weapons.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShootControllerTest {

	private MatchController matchCtrl = new MatchController();
	private Player p1 = new Player("MADSOMMA", 1);
	private Player p2 = new Player("REALENGINEER", 2);
	private Player p3 = new Player("JOHNNYCA$H", 3);
	private ShootController shootCtrlTest = new ShootController(matchCtrl.getMatch(), matchCtrl.g;
	private AmmoCard ammo = new AmmoCard(3, 3, 3, false);

	@Test
	void getPlayer() {
		match.setCurrentPlayer(p1);
		match.setPlayers(p1);
		match.setPlayers(p3);
		match.setPlayers(p2);
		assertEquals(p1, shootCtrlTest.getCurrPlayer());
		for (Player p: match.getPlayers()) {
			System.out.println(p.getNickname());
		}
	}

	@Test
	void getMatch() {
	}

	@Test
	void payAmmo() {
		match.setCurrentPlayer(p1);
		match.getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
	//	shootCtrlTest.getCurrPlayer().setPosition(match.getMap().getSquareFromIndex(0, 0));        //set position of MADSOMMA
		System.out.println(match.getWeaponDeck().toString());
		Weapon temp = match.getWeaponDeck().pickFirstCard();
		System.out.println(temp.toString());
		System.out.println(match.getCurrentPlayer().getAmmo().toString());
		try {
			shootCtrlTest.payAmmo(temp.getCostAlternate());
			System.out.println("After payment");
			System.out.println(match.getCurrentPlayer().getAmmo().toString());
		} catch (Exception e) {
			System.out.println("oh oh, something has gone wrong, pagaaaa");
		}
	}

	@Test
	void shootLockRifle() {
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