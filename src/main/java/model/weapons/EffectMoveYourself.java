package model.weapons;

import controller.MoveController;
import model.Match;
import model.player.Player;

public class EffectMoveYourself extends Effect{

	public EffectMoveYourself (int move) {
		this.setDamage(0);
		this.setInvolvedPlayers(0);
		this.setMinShootDistance(0);
		this.setNeedVisibleTarget(false);
		this.setMark(0);
		this.setMoveTarget(0);
		this.setMoveYourself(move);
		this.setSameTarget(0);		//is it correct as "escape flag"?
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Move yourself of: ");
		name.append(this.getMoveYourself());
		return name.toString();
	}

	@Override
	public void executeEffect(Match match, MoveController ctrl, Player targets) {

	}
}
