package model;

import controller.ActionController;
import model.player.Player;

public class ShootController extends ActionController {

	private Match match;

	@Override
	public Player getPlayer() {
		return match.getCurrentPlayer();
	}


	//TODO where should I put the array of the target players and the relatives methods? Here or in Match (model class)?
}
