package controller;

import model.Color;
import model.Match;
import exception.*;
import model.map.Directions;
import model.map.Square;
import model.player.Player;
import model.weapons.Effect;
import model.weapons.Weapon;

import java.util.*;

public class ShootController extends ActionController {

	//attributes

	private Match match;
	private Player currentPlayer = getMatch().getCurrentPlayer(); //local variable to increase readability
	private MoveController moveController;


	//getter methods

	public ShootController(Match match, MoveController moveController) {
		this.match = match;
		this.moveController = moveController;
	}

	public Player getPlayer() {
		return match.getCurrentPlayer();
	}

	public Match getMatch() {
		//implementation of the abstract method inheritated from the father
		return match;
	}


	//check methods

	private boolean visibilityBetweenPlayers(Player player1, Player player2) {
		//this method returns true if player2 can be seen by player1

		if (match.getMap().getVisibileRooms(player1.getPosition()).contains(player2.getPosition().getColor())) {
			return true;
		} else {
			return false;
		}

	}

	private void payAmmo(List<Color> cost) throws NotEnoughAmmoException {
		//this method makes the player pay ammo for the optional effects
		int r = 0;
		int b = 0;
		int y = 0;
		if (cost != null) {
			for (Color color : cost) {
				switch (color) {
					case RED:
						r++;
						break;
					case BLUE:
						b++;
						break;
					case YELLOW:
						y++;
						break;
					default:
						break;
				}
			}
		}
		if (match.getCurrentPlayer().getAmmo().getRedAmmo() - r < 0 || match.getCurrentPlayer().getAmmo().getBlueAmmo() - b < 0 || match.getCurrentPlayer().getAmmo().getYellowAmmo() - y < 0) {
			throw new NotEnoughAmmoException("It seems you don't have enough ammo");
		} else {
			match.getCurrentPlayer().removeAmmo(r, b, y);
		}
	}

	private void checkCorrectVisibility(Effect eff, Player player1, Player player2) throws NotAllowedTarget {
		//this method checks if the visibility required by the weapon is respected
		if (eff.needVisibleTarget() != visibilityBetweenPlayers(player1, player2))
			throw new NotAllowedTarget();
	}

	private void checkAllowedDistance(Effect eff, Player player1, Player player2) throws NotAllowedTarget {
		//this method checks if the distance required by the weapon is respected
		if (moveController.minDistBetweenSquares(player1.getPosition(), player2.getPosition()) < eff.getMinShootDistance())
			throw new NotAllowedTarget();

	}

	private void checkExactDistance(Effect eff, Player player1, Player player2) throws NotAllowedTarget {
		//this method checks if the distance required by the weapon is the same that separate the two players
		int k;

		if (eff.getMinShootDistance() == -1){
			k = 0;
		} else {
			k = eff.getMinShootDistance();
		}

		if (moveController.minDistBetweenSquares(player1.getPosition(), player2.getPosition()) != k)
			throw new NotAllowedTarget();
	}

	private void checkSameDirectionAllowed(Effect eff, Player player1, Square square, Directions direction) throws  NotAllowedTarget{
		//this method checks if the square and the position of the player are on the same line (walls cannot be passed)
		if(direction!=null) {
			if (match.getMap().getAllowedSquaresInDirection(direction, player1.getPosition()).contains(square)) {
				return;
			} else {
				throw new NotAllowedTarget();
			}
		}

		ArrayList<Directions> cardinalDirections = new ArrayList<>();
		cardinalDirections.add(Directions.UP);
		cardinalDirections.add(Directions.DOWN);
		cardinalDirections.add(Directions.LEFT);
		cardinalDirections.add(Directions.RIGHT);

		for (Directions dir: cardinalDirections) {
			if (! match.getMap().getAllowedSquaresInDirection(dir, player1.getPosition()).contains(square))  //condition verified if the two squares are not on the same line
				throw new NotAllowedTarget();
		}
	}

	private void checkSameDirectionThroughWalls(Effect eff, Player player1, Square square, Directions direction) throws  NotAllowedTarget{
		//this method checks if the square and the position of the player are on the same line (don't care about the walls)
		if(direction!=null) {
			if (match.getMap().getAllSquaresInDirection(direction, player1.getPosition()).contains(square)) {
				return;
			} else {
				throw new NotAllowedTarget();
			}
		}

		ArrayList<Directions> cardinalDirections = new ArrayList<>();
		cardinalDirections.add(Directions.UP);
		cardinalDirections.add(Directions.DOWN);
		cardinalDirections.add(Directions.LEFT);
		cardinalDirections.add(Directions.RIGHT);

		for (Directions dir: cardinalDirections) {
			if (! match.getMap().getAllSquaresInDirection(dir, player1.getPosition()).contains(square))  //condition verified if the two squares are not on the same line
				throw new NotAllowedTarget();
		}
	}


	//shoot methods

	public void shootLockRifle (Input input) throws NotAllowedTarget, NotEnoughAmmoException{
		//this method is valid only for LOCK RIFLE

		//BASIC MODE
		for (Effect eff: input.getWeapon().getBasicMode()) {
			try {
				checkCorrectVisibility(eff, currentPlayer, input.getTargets().get(eff.getSameTarget()));
				eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
			} catch (Exception e) {
				//TODO
			}

		}

		//OPTIONAL MODE
		if (input.getShootModes().size()>1 && input.getTargets().size()>1) {
			try {
				payAmmo(input.getWeapon().getCostOpt1());
				for (Effect eff : input.getWeapon().getOptionalModeOne()) {
					try {
					checkCorrectVisibility(eff, currentPlayer, input.getTargets().get(eff.getSameTarget()));
					eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
					} catch (Exception e) {
						//TODO
					}
				}
			} catch (NotEnoughAmmoException e) {
				//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
			}
		}
	}

	public void shootElectroScythe (Input input) throws NotAllowedTarget, NotEnoughAmmoException{
		//this method is valid only for ELECTRO SCYTHE
		Effect eff;

		switch (input.getShootModes().get(0)) {
			case BASIC:
				eff = input.getWeapon().getBasicMode().get(0);
				for (Player player: getMatch().getPlayers()) {
					try {
					checkCorrectVisibility(eff, currentPlayer, player);
					checkExactDistance(eff, currentPlayer, player);
					eff.executeEffect(match, moveController, player, null);
					} catch (Exception e) {
						throw new NotAllowedTarget();
					}
				}
				break;

			case ALTERNATE:
				eff = input.getWeapon().getAlternateMode().get(0);
				try {
					payAmmo(input.getWeapon().getCostAlternate());
					for (Player player: getMatch().getPlayers()) {
						try {
							checkCorrectVisibility(eff, currentPlayer, player);
							checkExactDistance(eff, currentPlayer, player);
							eff.executeEffect(match, moveController, player, null);
						} catch (Exception e) {
							throw new NotAllowedTarget();
						}
					}
					break;
				} catch (NotEnoughAmmoException e) {
					//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
				}
		}
	}

	public void shootMachineGun (Input input) throws NotAllowedTarget, NotEnoughAmmoException {
		//this method is valid only for MACHINE GUN

		for (ShootMode mode : input.getShootModes()) {
			switch (mode) {

				case BASIC:
					for (Effect eff: input.getWeapon().getBasicMode()) {
						if (eff.getSameTarget()<input.getTargets().size()) {	//check if the user has set more than one target
							try {
								checkCorrectVisibility(eff, currentPlayer, input.getTargets().get(eff.getSameTarget()));
								eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
							} catch (Exception e) {
								throw new NotAllowedTarget();
							}
						}
					}
					break;

				case OPTIONAL1:
					try {
						payAmmo(input.getWeapon().getCostOpt1());
						Effect eff = input.getWeapon().getOptionalModeOne().get(0);
						try {
							checkCorrectVisibility(eff, currentPlayer, input.getTargets().get(eff.getSameTarget()));
							eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
						} catch (Exception e) {
							throw new NotAllowedTarget();
						}
					} catch (NotEnoughAmmoException e) {
						//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
					}
					break;

				case OPTIONAL2:
					try {
						payAmmo(input.getWeapon().getCostOpt2());
						for (Effect eff: input.getWeapon().getOptionalModeTwo()) {
							if (eff.getSameTarget()<input.getTargets().size()) {		//check if
								try {
									checkCorrectVisibility(eff, currentPlayer, input.getTargets().get(eff.getSameTarget()));
									eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
								} catch (Exception e) {
									throw new NotAllowedTarget();
								}
							}
						}
					} catch (NotEnoughAmmoException e) {
						//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
					}
					break;
			}
		}
	}

	public void shootTHOR (Input input) throws NotAllowedTarget, NotEnoughAmmoException {
		//this method is valid only for T.H.O.R.
		Effect eff;

		//BASIC MODE
		eff = input.getWeapon().getBasicMode().get(0);
		try {
			checkCorrectVisibility(eff, currentPlayer, input.getTargets().get(eff.getSameTarget()));
			eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
		} catch (Exception e) {
			throw new NotAllowedTarget();
		}

	    //OPTIONAL ONE
		if (input.getShootModes().size()>1) {
			try {
				payAmmo(input.getWeapon().getCostOpt1());
				eff = input.getWeapon().getOptionalModeOne().get(0);
				try {
					checkCorrectVisibility(eff, input.getTargets().get(eff.getSameTarget()-1), input.getTargets().get(eff.getSameTarget()));  //change the striker with the target
					eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
				} catch (Exception e) {
					throw new NotAllowedTarget();
				}
			} catch (NotEnoughAmmoException e) {
				//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
			}
		}

		//OPTIONAL TWO
		if (input.getShootModes().size()>2) {
			try {
				payAmmo(input.getWeapon().getCostOpt2());
				eff = input.getWeapon().getOptionalModeTwo().get(0);
				try {
					checkCorrectVisibility(eff, input.getTargets().get(eff.getSameTarget()-1), input.getTargets().get(eff.getSameTarget()));  //change the striker with the target
					eff.executeEffect(match, moveController, input.getTargets().get(eff.getSameTarget()), null);
				} catch (Exception e) {
					throw new NotAllowedTarget();
				}
			} catch (NotEnoughAmmoException e) {
				//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
			}
		}

	}
}
