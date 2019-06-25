package model.weapons;

import controller.MoveController;
import model.ShootingParametersInput;
import exception.NotAllowedMoveException;
import model.Match;
import model.map.Square;
import model.player.Player;

import java.io.Serializable;

public class EffectMoveYourself extends Effect  implements Serializable {

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
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) throws NotAllowedMoveException {
		Player target = match.getCurrentPlayer();
		Square destination = input.getSquares().get(0);

		try {
			ctrl.move(target, destination, getMoveYourself());
		} catch (Exception e){
			throw new NotAllowedMoveException("Not allowed move in executing effect (move yourself)");
		}
	}
}
