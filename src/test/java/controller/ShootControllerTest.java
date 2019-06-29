package controller;

import model.ShootMode;
import model.ShootingParametersInput;
import model.ammo.AmmoCard;
import model.map.Directions;
import model.player.Player;
import model.weapons.Weapon;
import model.weapons.WeaponName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShootControllerTest {

	private MatchController matchCtrl;
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	private AmmoCard ammo = new AmmoCard(3, 3, 3, false);
	private ShootingParametersInput input = new ShootingParametersInput();

	@BeforeEach
	void setUp() {
		try {
			matchCtrl = new MatchController();
			matchCtrl.buildMapForTest(1);
			p1 = new Player("MADSOMMA", 1, matchCtrl.getMatch());
			p2 = new Player("REALNGNEER", 2, matchCtrl.getMatch());
			p3 = new Player("JOHNNYCA$H", 3, matchCtrl.getMatch());
			p4 = new Player("AHHHH", 4, matchCtrl.getMatch());
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
	void payAmmo() {
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
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
	//WEAPON TEST

	@Test
	void shootLockRifle() {

		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 2));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of JOHNNTYCA$H

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.LOCK_RIFLE));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setTargets(p2);
		input.setTargets(p3);


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootLockRifle();
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
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.ELECTROSCYTHE));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);
		//System.out.println(input.getTargets().size());


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootElectroScythe();
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
	void shootMachineGun()  {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
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
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootMachineGun();
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
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootTHOR();
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
	void shootPlasmaGun() {	//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of REALNGNEER

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.PLASMA_GUN));
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL2);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1));


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootPlasmaGun();
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p1.printPosition());
			assertNotEquals(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0), p1.getPosition());
			assertEquals(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1), p1.getPosition());
		}catch (Exception e){
			System.out.println(p1.printPosition());
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootWhisper(){
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of REALNGNEER

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.WHISPER));
		input.setShootModes(ShootMode.BASIC);
		input.setTargets(p2);


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootWhisper();
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootTractorBeam() {
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of REALNGNEER

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.TRACTOR_BEAM));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootTractorBeam();
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p2.printPosition());
			//assertNotEquals(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0), p1.getPosition());
			//assertEquals(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1), p1.getPosition());
		}catch (Exception e){
			System.out.println(p2.printPosition());
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootCannonVortex() {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.VORTEX_CANNON));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootCannonVortex();
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringMarks());
			System.out.println(p2.printPosition());
			System.out.println(p3.printPosition());
			System.out.println(p4.printPosition());

		}catch (Exception e){
			System.out.println(p2.printPosition());
			System.out.println(p3.printPosition());
			System.out.println(p4.printPosition());
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootFurnace() {
		//setting players and map
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of AHHHHH

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.FURNACE));
		input.setShootModes(ShootMode.BASIC);
		//input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootFurnace();
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
	void shootHeatseeker() {
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 1));        //set position of REALNGNEER

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.HEATSEEKER));
		input.setShootModes(ShootMode.BASIC);
		input.setTargets(p2);


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootHeatseeker();
			System.out.println("\n");
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringMarks());
		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootHellion() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.HELLION));
		input.setShootModes(ShootMode.BASIC);
		//input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootHellion();
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
	void shootFlameThrower() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 1));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.FLAMETHROWER));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setDirection(Directions.LEFT);


		//executing code
		try{
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootFlameThrower();
			System.out.println("\n");
			System.out.println(p1.getNickname()+ "'s " + p1.getBoard().toStringLP());
			System.out.println(p2.getNickname()+ "'s " + p2.getBoard().toStringLP());
			System.out.println(p3.getNickname()+ "'s " + p3.getBoard().toStringLP());
			System.out.println(p4.getNickname()+ "'s " + p4.getBoard().toStringLP());
		}catch (Exception e){
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootGrenadeLauncher() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.GRENADE_LAUNCHER));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));		//move the target here
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));		//bomb here
		input.setMakeDamageBeforeMove(false);


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootGrenadeLauncher();
			System.out.println("\n");
			System.out.println(p1.getNickname() + "'s " + p1.getBoard().toStringLP());
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p4.getNickname() + "'s " + p4.getBoard().toStringLP());
			System.out.println("\n");
			System.out.println(p2.printPosition());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootRocketLauncher() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.ROCKET_LAUNCHER));
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL2);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 1));		//move the target here
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 2));		//move yourself here


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootRocketLauncher();
			System.out.println("\n");
			System.out.println(p1.getNickname() + "'s " + p1.getBoard().toStringLP());
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p4.getNickname() + "'s " + p4.getBoard().toStringLP());
			System.out.println("\n");
			System.out.println(p1.printPosition());
			System.out.println(p2.printPosition());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootRailgun() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 1));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.RAILGUN));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p4);
		input.setTargets(p3);
		input.setDirection(Directions.LEFT);


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootRailGun();
			System.out.println("\n");
			System.out.println(p1.getNickname() + "'s " + p1.getBoard().toStringLP());
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p4.getNickname() + "'s " + p4.getBoard().toStringLP());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootCyberblade() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 1));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.CYBERBLADE));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.OPTIONAL1);
		input.setShootModes(ShootMode.OPTIONAL2);
		input.setTargets(p3);
		input.setTargets(p4);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootCyberblade();
			System.out.println("\n");
			System.out.println(p1.getNickname() + "'s " + p1.getBoard().toStringLP());
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p4.getNickname() + "'s " + p4.getBoard().toStringLP());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootZX2() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.ZX_2));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootZX2();
			System.out.println("\n");
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname() + "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getBoard().toStringMarks());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootShotgun() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 2));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.SHOTGUN));
		input.setShootModes(ShootMode.BASIC);
		//input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootShotgun();
			System.out.println("\n");
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p2.printPosition());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootPowerGlove() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of REALNGNEER
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(0, 2));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.POWER_GLOVE));
		input.setShootModes(ShootMode.BASIC);
		//input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		//input.setTargets(p3);
		input.setDirection(Directions.DOWN);


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootPowerGlove();
			System.out.println("\n");
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());
			System.out.println(p1.printPosition());
		} catch (Exception e) {
			System.out.println("\n");
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());
			System.out.println(p1.printPosition());
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootSchockWave() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 0));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 1));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.SCHOCKWAVE));
		input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setTargets(p3);
		input.setTargets(p4);


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootSchockWave();
			System.out.println("\n");
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p3.getNickname() + "'s " + p3.getBoard().toStringLP());
			System.out.println(p3.getBoard().toStringMarks());
			System.out.println(p4.getNickname() + "'s " + p4.getBoard().toStringLP());
			System.out.println(p4.getBoard().toStringMarks());
		} catch (Exception e) {
			System.out.println("\n");
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

	@Test
	void shootSledgehammer() {
		//setting players
		matchCtrl.getMatch().setCurrentPlayer(p1);
		matchCtrl.getMatch().setPlayers(p1);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p4);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
		matchCtrl.getShootController().getCurrPlayer().setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of MADSOMMA
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 0));        //set position of REALNGNERR
		matchCtrl.getMatch().getPlayers().get(2).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 0));        //set position of JOHNNYCA$H
		matchCtrl.getMatch().getPlayers().get(3).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(1, 2));        //set position of AHHHHH


		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.SLEDGEHAMMER));
		//input.setShootModes(ShootMode.BASIC);
		input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));


		//executing code
		try {
			matchCtrl.getShootController().setInput(input);
			matchCtrl.getShootController().shootSledgehammer();
			System.out.println("\n");
			System.out.println(p2.getNickname() + "'s " + p2.getBoard().toStringLP());
			System.out.println(p2.getBoard().toStringMarks());
			System.out.println(p2.printPosition());
		} catch (Exception e) {
			System.out.println("shit happened");
			e.printStackTrace();
		}
	}

}