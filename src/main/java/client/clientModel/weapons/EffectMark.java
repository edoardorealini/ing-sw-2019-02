package client.clientModel.weapons;

import client.clientModel.Match;
import client.clientModel.player.Player;
import controller.MoveController;
import controller.ShootingParametersInput;

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
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) {
		Player target = input.getTargets().get(getSameTarget());
		target.getBoard().updateMarks(this.getMark(), match.getCurrentPlayer().getId());              //updating marks of the target
	}
}
