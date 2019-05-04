package controller;

import controller.ActionController;
import model.Match;
import model.player.Player;

import java.util.ArrayList;

public class ShootController extends ActionController {

	private Match match;
	private ArrayList<Player> targetPlayers;

	public void setMatch(Match match) {
		this.match = match;
	}

	public Player getPlayer() {
		return match.getCurrentPlayer();
	}

	//implementazione del metodo astratto dalla classe abstract.
	public Match getMatch() {
		return match;
	}



}
