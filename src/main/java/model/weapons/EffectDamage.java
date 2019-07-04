package model.weapons;

import controller.MoveController;
import model.Match;
import model.ShootingParametersInput;
import model.player.AbilityStatus;
import model.player.Player;
import model.powerup.PowerUp;
import model.powerup.PowerUpName;

import java.io.Serializable;

/**
 * This class represents the damage effect that a weapon can have.
 * Her valid fields (inherited from the father {@link Effect}) are damage, involvedPlayers, minShootDistance, needVisibleTarget, indexOfTarget.
 * @author Riccardo
 */

public class EffectDamage extends Effect implements Serializable {

	public EffectDamage (int damage, int targets, int sameTarget, boolean visible, int distance) {
		this.setDamage(damage);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(0);
		this.setMoveTarget(0);
		this.setMoveYourself(0);
		this.setIndexOfTarget(sameTarget);
	}


	/**
	 * Returns the toString implementation of effect.
	 * @return "Damage of x", where x represents the damage made.
	 */
	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append("Damage of: ");
		name.append(this.getDamage());
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
		//local variables that increase readability
		Player currentPlayer = match.getCurrentPlayer();

		if (getIndexOfTarget() == -1) {
			for (Player player : input.getTargets()) {
				int transferringMarks = player.getBoard().getSpecificMarks(currentPlayer.getId());

				player.getBoard().updateLife(this.getDamage(), currentPlayer.getId(), player.getId());                    //updating life points of the target
				while (transferringMarks > 0 && !player.getBoard().isOverKilled()) {
					player.getBoard().updateLife(1, currentPlayer.getId(),  player.getId());                        //converting marks to life points
					player.getBoard().removeMarks(1, currentPlayer.getId());           					//removing the converted mark
					transferringMarks--;
				}

				if (player.getBoard().isDead())                                                            //check if the target is dead
					player.trueDead();

				if(!player.getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY) && !player.getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY_LOWER)) {

					if (player.getBoard().getTotalNumberOfDamages() > 2)
						player.getStatus().setSpecialAbilityAdrenalinePick();

					if (player.getBoard().getTotalNumberOfDamages() > 5)
						player.getStatus().setSpecialAbilityAdrenalineShoot();

				}

				player.setBeenDamaged(true);

				for (PowerUp pow : player.getPowerUps()) {
					if (pow != null && pow.getName() == PowerUpName.TAGBACK_GRENADE && match.getMap().getVisibileRooms(player.getPosition()).contains(match.getCurrentPlayer().getPosition().getColor())) {
						player.setAskForTagBackGrenade(true);
					}

				}
			}
		} else {
			if (input.getTargets().size() > getIndexOfTarget()) {
				Player target = input.getTargets().get(getIndexOfTarget());
				int transferringMarks = target.getBoard().getSpecificMarks(currentPlayer.getId());

				target.getBoard().updateLife(this.getDamage(), currentPlayer.getId(), target.getId());                    //updating life points of the target
				while (transferringMarks > 0 && !target.getBoard().isOverKilled()) {
					target.getBoard().updateLife(1, currentPlayer.getId(), target.getId());                        //converting marks to life points
					target.getBoard().removeMarks(1, currentPlayer.getId());           //removing the converted mark
					transferringMarks--;
				}

				if(!target.getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY) && !target.getStatus().getSpecialAbility().equals(AbilityStatus.FRENZY_LOWER)) {

					if (target.getBoard().getTotalNumberOfDamages() > 2)
						target.getStatus().setSpecialAbilityAdrenalinePick();

					if (target.getBoard().getTotalNumberOfDamages() > 5)
						target.getStatus().setSpecialAbilityAdrenalineShoot();
				}

				target.setBeenDamaged(true);

				if (target.getBoard().isDead())                                                            //check if the target is dead
					target.trueDead();

				for (PowerUp pow : target.getPowerUps()) {
					if (pow != null && pow.getName() == PowerUpName.TAGBACK_GRENADE && match.getMap().getVisibileRooms(target.getPosition()).contains(match.getCurrentPlayer().getPosition().getColor())) {
						target.setAskForTagBackGrenade(true);
					}
				}
			}
		}
	}
}
