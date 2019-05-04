package controller;

import controller.ActionController;
import model.Match;
import model.player.Player;

public class ShootController extends ActionController {

	private Match match;

	public Player getPlayer() {
		return match.getCurrentPlayer();
	}

	/*
	implementazione del metodo astratto dalla classe abstract.
 	*/
	public Match getMatch() {
		return match;
	}

	//TODO where should I put the array of the target players and the relatives methods? Here or in Match (model class)?
}
