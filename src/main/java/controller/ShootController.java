package controller;

import model.Color;
import model.Match;
import model.player.Player;
import model.weapons.Effect;
import model.weapons.Weapon;
import java.util.*;

public class ShootController extends ActionController {

	//attributes

	private Match match;
	private ArrayList<Player> targetPlayers;
	private MoveController moveController;
	private ArrayList<Effect> effectsOrder;

	//methods

	public ShootController(Match match, MoveController moveController) {
		this.match = match;
		this.targetPlayers = new ArrayList<>();
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

	public void shoot(Weapon weapon, ShootEffect weapEffect, List<Player> targets) throws IllegalArgumentException{
		/* ShootEffect is an enum used to choose which effect of the weapon the players wants to use
		   In this method the choice is between BASIC EFFECT OR ALTERNATE EFFECT
		*/

		if (weapEffect.equals(ShootEffect.OPTIONAL1) || weapEffect.equals(ShootEffect.OPTIONAL2)) {
			throw new IllegalArgumentException(); }
		else {
			switch (weapEffect) {

				case ALTERNATE:				//check if the player wants to use the ALTERNATE effect and, if so, try to remove his ammos
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
					if (match.getCurrentPlayer().getAmmo().getRedAmmo()-r<0 || match.getCurrentPlayer().getAmmo().getBlueAmmo()-b<0 || match.getCurrentPlayer().getAmmo().getYellowAmmo()-y<0) {
						//TODO throw new OutOfAmmoException
					} else {
						match.getCurrentPlayer().removeAmmo(r, b, y);
					}
					for (Effect eff: weapon.getAlternateEffect()) {
						this.executeEffect(eff);
					}
					break;

				case BASIC:
					for (Effect eff: weapon.getAlternateEffect()) {
						this.executeEffect(eff);
					}
					break;

				default:
					break;

			}		//end switch
		} 	 	//end else
	}		//end method

	public void shoot(Weapon weapon, ShootEffect weapEffect1, ShootEffect weapEffect2, List<Player> targets) throws IllegalArgumentException{
		/* ShootEffect is an enum used to choose which effect of the weapon the players wants to use
		   In this method the choice is between BASIC EFFECT AND OPTIONAL 1 OR OPTIONAL 2
		*/

		if ((weapEffect1.equals(ShootEffect.BASIC) || weapEffect1.equals(ShootEffect.OPTIONAL1) || weapEffect1.equals(ShootEffect.OPTIONAL2)) &&
				(weapEffect2.equals(ShootEffect.BASIC) || weapEffect2.equals(ShootEffect.OPTIONAL1) || weapEffect2.equals(ShootEffect.OPTIONAL2)) &&
					weapEffect1 != weapEffect2) {  	//this condition check if weapEffect1 and weapEffect2 have the right values


			 }
		else {
			throw new IllegalArgumentException();
		}
	}

	public void executeEffect(Effect effect) {
		/*	This method execute the effect according to its type

		switch (effect.getType()) {

			case DAMAGE:
				if ((moveController.minDistBetweenSquares(match.getCurrentPlayer().getPosition(), targetPlayers.get(effect.getSameTarget()).getPosition()) <= effect.getMinShootDistance()) && isVisibleTarget(match.getCurrentPlayer(), targetPlayers.get(effect.getSameTarget())) ) {
					targetPlayers.get(effect.getSameTarget()).getBoard().updateLife(effect.getDamage(), match.getCurrentPlayer().getId());   //updating life points of the target
					if (targetPlayers.get(effect.getSameTarget()).getBoard().isDead())            //check if the target is dead
						targetPlayers.get(effect.getSameTarget()).trueDead();
					int transferringMarks = targetPlayers.get(effect.getSameTarget()).getBoard().getSpecificMarks(match.getCurrentPlayer().getId());  //local variable to increase understandability
					while (transferringMarks > 0 && !targetPlayers.get(effect.getSameTarget()).getBoard().isOverKilled()) {
						targetPlayers.get(effect.getSameTarget()).getBoard().updateLife(1, match.getCurrentPlayer().getId());   //converting marks to life points
						targetPlayers.get(effect.getSameTarget()).getBoard().removeMarks(1, match.getCurrentPlayer().getId());    //removing the converted mark
						if (targetPlayers.get(effect.getSameTarget()).getBoard().isDead())        //check if the target is dead
							targetPlayers.get(effect.getSameTarget()).trueDead();
						transferringMarks--;
					}
				}
				break;

			case MARK:
				targetPlayers.get(effect.getSameTarget()).getBoard().updateMarks(effect.getMark(), match.getCurrentPlayer().getId());   //updating life points of the target
				break;

			case MOVETARGET:
				//TODO chiedi a Edo che giro devo fare per poter accedere al suo metodo perchè non riesco a trovare il moveController
				break;

			case MOVEYOURSELF:
				//TODO
				break;

			default:
				break;
		}
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
