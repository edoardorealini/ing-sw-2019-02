package model.weapons;

public class EffectMark extends Effect {

	public EffectMark (int mark, int targets, boolean visible, int distance) {
		this.setDamage(0);
		this.setInvolvedPlayers(targets);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(mark);
		this.setMoveTarget(0);
		this.setMoveYourself(0);

	}
}