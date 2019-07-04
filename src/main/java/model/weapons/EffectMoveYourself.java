package model.weapons;

import controller.MoveController;
import exception.NotAllowedMoveException;
import model.Match;
import model.ShootingParametersInput;
import model.map.Square;
import model.player.Player;

import java.io.Serializable;

/**
 * This class represents the moving oneself effect that a weapon can have.
 * Her valid field (inherited from the father {@link Effect}) is only moveYourself.
 * @author Riccardo
 */

public class EffectMoveYourself extends Effect implements Serializable {

	public EffectMoveYourself (int move) {
		this.setDamage(0);
		this.setInvolvedPlayers(0);
		this.setMinShootDistance(0);
		this.setNeedVisibleTarget(false);
		this.setMark(0);
		this.setMoveTarget(0);
		this.setMoveYourself(move);
		this.setIndexOfTarget(0);
	}


	/**
	 * Returns the toString implementation of effect.
	 * @return "Move yourself of x", where x is the maximum allowed movement.
	 */

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Move yourself of: ");
		name.append(this.getMoveYourself());
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
		Player target = match.getCurrentPlayer();
		Square destination = input.getSquares().get(0);

		try {
			ctrl.move(target, destination, getMoveYourself());
		} catch (Exception e){
			throw new NotAllowedMoveException("Not allowed move in executing effect (move yourself)");
		}
	}
}
