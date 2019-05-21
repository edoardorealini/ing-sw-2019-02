package controller;

import model.ammo.AmmoCard;
import model.player.Player;
import model.weapons.Weapon;
import model.weapons.WeaponName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShootControllerTest {

	private MatchController matchCtrl;
	private Player p1 = new Player("MADSOMMA", 1, null);
	private Player p2 = new Player("REALNGNEER", 2, null);
	private Player p3 = new Player("JOHNNYCA$H", 3, null);
	private Player p4 = new Player("AHHHH", 4, null);
	private AmmoCard ammo = new AmmoCard(3, 3, 3, false);
	private ShootingParametersInput input = new ShootingParametersInput();

	@BeforeEach
	void setUp() {
		try {
			matchCtrl = new MatchController();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

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
			matchCtrl.getShootController().shootLockRifle(input);
			System.out.println(p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());

		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}

	}

	@Test
	void shootElectroScythe() {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.ELECTROSCYTHE));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		//input.setTargets(p2);
		//input.setTargets(p3);
		//input.setTargets(p4);
		System.out.println(input.getTargets().size());


		//executing code
		try{
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());
			matchCtrl.getShootController().shootElectroScythe(input);
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());

		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootMachineGun() {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.MACHINE_GUN));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setShootModes(ShootMode.OPTIONAL2);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);
		//input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));
		System.out.println(input.getTargets().size());


		//executing code
		try{
			matchCtrl.getShootController().shootMachineGun(input);
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());

		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}


	@Test
	void shootTHOR() {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 1));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.THOR));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setShootModes(ShootMode.OPTIONAL2);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);
		//input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));
		System.out.println(input.getTargets().size());


		//executing code
		try{
			matchCtrl.getShootController().shootTHOR(input);
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());

		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootPlasmaGun() {
	}

	@Test
	void shootWhisper() {
	}

	@Test
	void shootTractorBeam() {
	}

	@Test
	void shootCannonVortex() {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.buildMap(1);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.VORTEX_CANNON));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));
		System.out.println(input.getTargets().size());


		//executing code
		try{
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());
			matchCtrl.getShootController().shootCannonVortex(input);
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());

		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}
}