package model.weapons;

public class EffectMoveTarget extends Effect{

	public EffectMoveTarget (int move, boolean visible, int distance) {
		this.setDamage(0);
		this.setInvolvedPlayers(0);
		this.setMinShootDistance(distance);
		this.setNeedVisibleTarget(visible);
		this.setMark(0);
		this.setMoveTarget(move);
		this.setMoveYourself(0);
	}

}
