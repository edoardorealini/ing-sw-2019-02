package model.weapons;

import controller.MoveController;
import controller.ShootingParametersInput;
import exception.NotAllowedMoveException;
import model.Match;
import model.map.Square;
import model.player.Player;

import java.io.Serializable;

public class EffectMoveTarget extends Effect  implements Serializable {

	public EffectMoveTarget (int move, int targets, int sameTarget, boolean visible, int distance) {
		this.setDamage(0);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(0);
		this.setMoveTarget(move);
		this.setMoveYourself(0);
		this.setSameTarget(sameTarget);
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Move the target of: ");
		name.append(this.getMoveTarget());
		return name.toString();
	}

	@Override
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) throws NotAllowedMoveException {
		Player target = input.getTargets().get(getSameTarget());
		Square destination = input.getSquares().get(0);

		try {
			ctrl.move(target, destination, getMoveTarget());
		} catch (Exception e){
			throw new NotAllowedMoveException();
		}
	}

}
