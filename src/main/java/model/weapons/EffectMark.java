package model.weapons;

import controller.MoveController;
import model.ShootingParametersInput;
import model.Match;
import model.player.Player;

import java.io.Serializable;

public class EffectMark extends Effect implements Serializable {

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
		if (getSameTarget() == -1) {
			for (Player player : input.getTargets()){
				player.getBoard().updateMarks(this.getMark(), match.getCurrentPlayer().getId(), player.getId());              //updating marks of the target
			}
		} else {
		Player target = input.getTargets().get(getSameTarget());
		target.getBoard().updateMarks(this.getMark(), match.getCurrentPlayer().getId(), target.getId());              //updating marks of the target
		}
	}
}
