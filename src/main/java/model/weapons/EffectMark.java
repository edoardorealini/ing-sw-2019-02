package model.weapons;

import controller.MoveController;
import model.Match;
import model.ShootingParametersInput;
import model.player.Player;

import java.io.Serializable;

/**
 * This class represents the mark effect that a weapon can have.
 * Her valid fields (inherited from the father {@link Effect}) are mark, involvedPlayers, minShootDistance, needVisibleTarget, indexOfTarget.
 * @author Riccardo
 */

public class EffectMark extends Effect implements Serializable {

	public EffectMark (int mark, int targets, int sameTarget, boolean visible, int distance) {
		this.setDamage(0);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(mark);
		this.setMoveTarget(0);
		this.setMoveYourself(0);
		this.setIndexOfTarget(sameTarget);
	}


	/**
	 * Returns the toString implementation of effect.
	 * @return "Mark of x", where x represents the marks made.
	 */

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Mark of: ");
		name.append(this.getMark());
		return name.toString();
	}


	/**
	 *
	 * @param match	is the reference to the match
	 * @param ctrl	is the reference to the moveController
	 * @param input	is an instance of {@link ShootingParametersInput}
	 */

	@Override
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) {
		if (getIndexOfTarget() == -1) {
			for (Player player : input.getTargets()){
				player.getBoard().updateMarks(this.getMark(), match.getCurrentPlayer().getId(), player.getId());              //updating marks of the target
			}
		} else {
			if (input.getTargets().size() > getIndexOfTarget()) {
				Player target = input.getTargets().get(getIndexOfTarget());
				target.getBoard().updateMarks(this.getMark(), match.getCurrentPlayer().getId(), target.getId());              //updating marks of the target
			}
		}
	}
}
