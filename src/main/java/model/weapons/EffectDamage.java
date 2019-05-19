package model.weapons;

import controller.MoveController;
import controller.ShootingParametersInput;
import model.Match;
import model.player.Player;

public class EffectDamage extends Effect {

	public EffectDamage (int damage, int targets, int sameTarget, boolean visible, int distance) {
		this.setDamage(damage);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(0);
		this.setMoveTarget(0);
		this.setMoveYourself(0);
		this.setSameTarget(sameTarget);
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Damage of: ");
		name.append(this.getDamage());
		return name.toString();
	}

	@Override
	public void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) {
		//local variables that increase readability
		Player currentPlayer = match.getCurrentPlayer();
		Player target = input.getTargets().get(getSameTarget());
		int transferringMarks = target.getBoard().getSpecificMarks(currentPlayer.getId());
		int i = 0;

		target.getBoard().updateLife(this.getDamage(), currentPlayer.getId());                    //updating life points of the target
		while (transferringMarks > 0 && !target.getBoard().isOverKilled()) {
			target.getBoard().updateLife(1, currentPlayer.getId());                        //converting marks to life points
			target.getBoard().removeMarks(1, currentPlayer.getId());           //removing the converted mark
			transferringMarks--;
		}

		if (target.getBoard().isDead())                                                            //check if the target is dead
			target.trueDead();
	}
}
