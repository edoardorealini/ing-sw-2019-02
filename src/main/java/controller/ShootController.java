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

	public void shoot(Weapon weapon, ShootMode mode, List<Player> targets) throws NotEnoughAmmoException{
		/* ShootMode is an enum used to choose which effect of the weapon the players wants to use
		   In this method the choice is between BASIC EFFECT OR ALTERNATE EFFECT
		*/

		if (mode == ShootMode.ALTERNATE) {        //check if the player wants to use the ALTERNATE effect and, if so, try to remove his ammos
			List<Color> cost = weapon.getCostAlternate();
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
			for (Effect eff : weapon.getAlternateMode()) {   //apply all the effects of the chosen ShootMode
				if (eff.getInvolvedPlayers() == -1) {
					for (int i = 0; i < targets.size(); i++) {
						eff.executeEffect(match, moveController, targets.get(i));
					}
				} else {
					eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
				}
			}
		}

		if (mode == ShootMode.BASIC) {
			for (Effect eff : weapon.getBasicMode()) {  	 //apply all the effects of the chosen ShootMode
				if (eff.getInvolvedPlayers() == -1) {
					for (int i = 0; i < targets.size(); i++) {
						eff.executeEffect(match, moveController, targets.get(i));
					}
				} else {
					eff.executeEffect(match, moveController, targets.get(eff.getSameTarget()));
				}
			}
		}

	} //end method

	public void shoot(Weapon weapon, ShootMode[] modes, List<Player> targets) throws IllegalArgumentException {
		/* ShootMode is an enum used to choose which effect of the weapon the players wants to use
		   In this method the choice is between BASIC EFFECT AND OPTIONAL 1 OR OPTIONAL 2
		*/

	}

	public boolean isVisibleTarget (Player player1, Player player2) {
		if (match.getMap().getVisibileRooms(player1.getPosition()).contains(player2.getPosition().getColor())) {
			return true;
		} else {
			return false;
		}

	}
}
