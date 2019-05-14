package model.weapons;

import controller.MoveController;
import model.Match;
import model.map.Square;
import model.player.Player;

public class EffectMark extends Effect {

	public EffectMark (int mark, int targets, int sameTarget, boolean visible, int distance) {
		this.setDamage(0);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(mark);
		this.setMoveTarget(0);
		this.setMoveYourself(0);
		this.setSameTarget(sameTarget);
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Mark of: ");
		name.append(this.getMark());
		return name.toString();
	}

	@Override
	public void executeEffect(Match match, MoveController ctrl, Player target, Square destination) {
		target.getBoard().updateMarks(this.getDamage(), match.getCurrentPlayer().getId());              //updating marks of the target
	}
}
