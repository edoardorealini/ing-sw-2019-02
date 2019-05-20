package client.clientModel.weapons;

import client.clientModel.Match;
import client.clientModel.map.Square;
import client.clientModel.player.Player;
import controller.MoveController;
import controller.ShootingParametersInput;

public class EffectMoveYourself extends Effect {

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
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) {
		Player target = match.getCurrentPlayer();
		Square destination = input.getSquares().get(0);

		try {
			ctrl.move(target, destination, getMoveYourself());
		} catch (Exception e){
			//TODO fill this field if you catch NotAllowedMoveException
		}
	}
}
