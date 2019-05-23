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
	void getMatch() {
	}

	@Test
	void payAmmo() throws Exception{
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
		matchCtrl.getMatch().setPlayers(p3);
		matchCtrl.getMatch().setPlayers(p2);
		matchCtrl.getMatch().getCurrentPlayer().addAmmoCard(ammo);
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
			matchCtrl.getShootController().shootPlasmaGun(input);
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
			matchCtrl.getShootController().shootWhisper(input);
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
		matchCtrl.getMatch().getPlayers().get(1).setPosition(matchCtrl.getMatch().getMap().getSquareFromIndex(2, 2));        //set position of REALNGNEER

		//setting the input
		input.setWeapon(matchCtrl.getMatch().getWeaponDeck().getWeapon(WeaponName.TRACTOR_BEAM));
		input.setShootModes(ShootMode.BASIC);
		//input.setShootModes(ShootMode.ALTERNATE);
		input.setTargets(p2);
		input.setSquares(matchCtrl.getMatch().getMap().getSquareFromIndex(3, 1));


		//executing code
		try{
			matchCtrl.getShootController().shootTractorBeam(input);
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
			matchCtrl.getShootController().shootCannonVortex(input);
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
			matchCtrl.getShootController().shootFurnace(input);
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
			matchCtrl.getShootController().shootHeatseeker(input);
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
			matchCtrl.getShootController().shootHellion(input);
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