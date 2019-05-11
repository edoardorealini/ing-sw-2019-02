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
//	private ArrayList<Player> targetPlayers;
	private MoveController moveController;
	private ArrayList<Effect> effectsOrder;

	//methods

	public ShootController(Match match, MoveController moveController) {
		this.match = match;
//		this.targetPlayers = new ArrayList<>();
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
		if (modes.size()>1) {
			payAmmo(weapon.getCostOpt1()); //devo fare la try catch già qui?
			for (Effect eff: weapon.getOptionalModeOne()) {
				if (eff.needVisibleTarget() == isVisibleTarget(currentPlayer, targets.get(eff.getSameTarget()))
						&& moveController.minDistBetweenSquares(currentPlayer.getPosition(), targets.get(eff.getSameTarget()).getPosition()) >= eff.getMinShootDistance()) {
					//check if the player is visible and in an allowed square
					eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
				} else {
					throw new NotAllowedTarget(){};
				}
			}
		}

	}
}
