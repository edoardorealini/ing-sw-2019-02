package model.weapons;

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
}
