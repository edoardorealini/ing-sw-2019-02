package controller;

import controller.ActionController;
import model.Match;
import model.player.Player;
import model.weapons.Weapon;
import java.util.*;

public class ShootController extends ActionController {

	//attributes

	private Match match;
	private ArrayList<Player> targetPlayers;


	//methods

	public void setMatch(Match match) {
		this.match = match;
	}

	public Player getPlayer() {
		return match.getCurrentPlayer();
	}

	public Match getMatch() {
		//implementation of the abstract method inheritated from the father
		return match;
	}

	public void shoot (Weapon weapon, ShootEffect effect , List<Player> targets) throws IllegalArgumentException{
		/* ShootEffect is an enum used to choose which effect of the weapon the players wants to use
		   In this method the choice is between BASIC EFFECT OR ALTERNATE EFFECT
		*/

		if (!effect.equals(ShootEffect.BASIC) || !effect.equals(ShootEffect.ALTERNATE))
			throw new IllegalArgumentException();
		else {
			switch (effect) {

				case BASIC:

					break;

				case ALTERNATE:

					break;
			}
		}

	}


}
