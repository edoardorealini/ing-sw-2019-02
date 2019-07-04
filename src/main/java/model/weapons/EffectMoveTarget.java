package model.weapons;

import controller.MoveController;
import exception.NotAllowedMoveException;
import model.Match;
import model.ShootingParametersInput;
import model.map.Square;
import model.player.Player;

import java.io.Serializable;

/**
 * This class represents the moving a target effect that a weapon can have.
 * Her valid fields (inherited from the father {@link Effect}) are moveTarget, involvedPlayers, minShootDistance, needVisibleTarget, indexOfTarget.
 * @author Riccardo
 */

public class EffectMoveTarget extends Effect implements Serializable {

	public EffectMoveTarget (int move, int targets, int sameTarget, boolean visible, int distance) {
		this.setDamage(0);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(0);
		this.setMoveTarget(move);
		this.setMoveYourself(0);
		this.setIndexOfTarget(sameTarget);
	}


	/**
	 * Returns the toString implementation of effect.
	 * @return "Move the target of x", where x is the maximum allowed movement.
	 */

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Move the target of: ");
		name.append(this.getMoveTarget());
		return name.toString();
	}


	/**
	 *
	 * @param match	is the reference to the match
	 * @param ctrl	is the reference to the moveController
	 * @param input	is an instance of {@link ShootingParametersInput}
	 * @throws NotAllowedMoveException is thrown if the move is not allowed (e.g.: the destination square is too far)
	 */

	@Override
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) throws NotAllowedMoveException {
		if (input.getTargets().size() > getIndexOfTarget() && input.getSquares().get(0) != null) {
			Player target = input.getTargets().get(getIndexOfTarget());
			Square destination = input.getSquares().get(0);

			try {
				ctrl.move(target, destination, getMoveTarget());
			} catch (Exception e) {
				throw new NotAllowedMoveException("Not allowed move in executing effect (move target)");
			}
		}
	}

}
