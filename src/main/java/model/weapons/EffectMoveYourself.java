package model.weapons;

public class EffectMoveYourself extends Effect{

	public EffectMoveYourself (int move) {
		this.setType(EffectType.MOVEYOURSELF);
		this.setDamage(0);
		this.setInvolvedPlayers(0);
		this.setMinShootDistance(0);
		this.setNeedVisibleTarget(false);
		this.setMark(0);
		this.setMoveTarget(0);
		this.setMoveYourself(move);
		this.setSameTarget(0);		//is it correct as "escape flag"?
	}

}
