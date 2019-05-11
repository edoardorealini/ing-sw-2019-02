package controller;

import model.Color;
import model.Match;
import exception.*;
import model.player.Player;
import model.weapons.Effect;
import model.weapons.Weapon;
import java.util.*;

public class ShootController extends ActionController {

	//attributes

	private Match match;
	private Player currentPlayer = getMatch().getCurrentPlayer(); //local variable to increase readability
	private MoveController moveController;
	private ArrayList<Effect> effectsOrder;

	//methods

	public ShootController(Match match, MoveController moveController) {
		this.match = match;
		this.moveController = moveController;
		this.effectsOrder = new ArrayList<>();
	}

	public Player getPlayer() {
		return match.getCurrentPlayer();
	}

	public Match getMatch() {
		//implementation of the abstract method inheritated from the father
		return match;
	}

	public void setEffectsOrder(Effect a) {
		this.effectsOrder.add(a);
	}

	public List<Effect> getEffectsOrder() {
		return effectsOrder;
	}

	public boolean isVisibleTarget (Player player1, Player player2) {
		//this method return true if player2 can be seen by player1

		if (match.getMap().getVisibileRooms(player1.getPosition()).contains(player2.getPosition().getColor())) {
			return true;
		} else {
			return false;
		}

	}

	public void payAmmo(List<Color> cost) throws NotEnoughAmmoException{
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

	public void shootLockRifle (Weapon weapon, List<ShootMode> modes, List<Player> targets) throws NotAllowedTarget, NotEnoughAmmoException{
		//this method is valid only for LOCK RIFLE

		//BASIC MODE
		for (Effect eff: weapon.getBasicMode()) {
			if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, targets.get(eff.getSameTarget()))
					&& moveController.minDistBetweenSquares(currentPlayer.getPosition(), targets.get(eff.getSameTarget()).getPosition()) >= eff.getMinShootDistance()) {
				//check if the player is visible and in an allowed square
				eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
			} else {
				throw new NotAllowedTarget(){};
			}
		}

		//OPTIONAL MODE
		if (modes.size()>1 && targets.size()>1) {
			try {
				payAmmo(weapon.getCostOpt1());
				for (Effect eff : weapon.getOptionalModeOne()) {
					if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, targets.get(eff.getSameTarget()))) {
						//check if the player is visible
						eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
					} else {
						throw new NotAllowedTarget() {
						};
					}
				}
			} catch (NotEnoughAmmoException e) {
				//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
			}
		}
	}

	public void shootElectroScythe (Weapon weapon, ShootMode mode) throws NotAllowedTarget, NotEnoughAmmoException{
		//this method is valid only for ELECTRO SCYTHE
		Effect eff;

		switch (mode) {
			case BASIC:
				eff = weapon.getBasicMode().get(0);
				for (Player player: getMatch().getPlayers()) {
					if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, player)
							&& moveController.minDistBetweenSquares(currentPlayer.getPosition(), player.getPosition()) == 0) {
						//check if the player is visible and in an allowed square
						eff.executeEffect(match, moveController, player);
					} else {
						throw new NotAllowedTarget(){};
					}
				}
				break;

			case ALTERNATE:
				eff = weapon.getAlternateMode().get(0);
				try {
					payAmmo(weapon.getCostAlternate());
					for (Player player: getMatch().getPlayers()) {
						if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, player)
								&& moveController.minDistBetweenSquares(currentPlayer.getPosition(), player.getPosition()) == 0) {
							//check if the player is visible and in an allowed square
							eff.executeEffect(match, moveController, player);
						} else {
							throw new NotAllowedTarget(){};
						}
					}
					break;
				} catch (NotEnoughAmmoException e) {
					//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
				}
		}
	}

	public void shootMachineGun (Weapon weapon, List<ShootMode> modes, List<Player> targets) throws NotAllowedTarget, NotEnoughAmmoException{
		//this method is valid only for MACHINE GUN

		for (ShootMode mode : modes) {
			switch (mode) {

				case BASIC:
					for (Effect eff: weapon.getBasicMode()) {
						if (eff.getSameTarget()<targets.size()) {		//check if
							if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, targets.get(eff.getSameTarget()))) {
								//check if the player is visible
								eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
							} else {
								throw new NotAllowedTarget() {
								};
							}
						}
					}
					break;

				case OPTIONAL1:
					try {
						payAmmo(weapon.getCostOpt1());
						Effect eff = weapon.getOptionalModeOne().get(0);
						if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, targets.get(0))) {
							//check if the player is visible
							eff.executeEffect(match, moveController, targets.get(0));
						} else {
							throw new NotAllowedTarget() {
							};
						}
					} catch (NotEnoughAmmoException e) {
						//TODO write the catch part, prolly calling the view with a pop-up, maybe re-throw the exception
					}
					break;

				case OPTIONAL2:
					try {
						payAmmo(weapon.getCostOpt2());
						for (Effect eff: weapon.getOptionalModeTwo()) {
							if (eff.getSameTarget()<targets.size()) {		//check if
								if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, targets.get(eff.getSameTarget()))) {
									//check if the player is visible
									eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
								} else {
									throw new NotAllowedTarget() {
									};
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


}
