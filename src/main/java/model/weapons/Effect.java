package model.weapons;

public class Effect {
	private EffectType type;
	private int damage;
	private int mark;
	private int moveYourself;
	private int moveTarget;
	private int involvedPlayers;
	private boolean needVisibleTarget;
	private int minShootDistance;
	private int sameTarget;

	//constructor
	public Effect(){
		type = EffectType.NOEFFECT;
		damage = 0;
		mark = 0;
		moveYourself = 0;
		moveTarget = 0;
		involvedPlayers = 0;   //-1 means every player
		needVisibleTarget = true;
		minShootDistance = 0;   //-1 means the same square in which the striker is
		sameTarget = 0;  //index of the array of players choosen as targets, -1 means all targets
	}

	public EffectType getType() {
		return type;
	}

	public int getDamage() {
		return this.damage;
	}

	public int getMark() {
		return this.mark;
	}

	public int getMoveTarget() {
		return this.moveTarget;
	}

	public int getMoveYourself() {
		return this.moveYourself;
	}

	public void setType(EffectType type) {
		this.type = type;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public void setMoveTarget(int moveTarget) {
		this.moveTarget = moveTarget;
	}

	public void setMoveYourself(int moveYourself) {
		this.moveYourself = moveYourself;
	}

	public void setInvolvedPlayers(int involvedPlayers) {
		this.involvedPlayers = involvedPlayers;
	}

	public int getInvolvedPlayers() {
		return involvedPlayers;
	}

	public int getMinShootDistance() {
		return minShootDistance;
	}

	public void setMinShootDistance(int minShootDistance) {
		this.minShootDistance = minShootDistance;
	}

	public void setNeedVisibleTarget(boolean needVisibleTarget) {
		this.needVisibleTarget = needVisibleTarget;
	}

	public boolean isNeedVisibleTarget() {
		return needVisibleTarget;
	}

	public int getSameTarget() {
		return sameTarget;
	}

	public void setSameTarget(int sameTarget) {
		this.sameTarget = sameTarget;
	}

	@Override
	public String toString() {
		StringBuilder name = new StringBuilder();
		name.append(this.type);
		switch (type) {
			case DAMAGE:
				name.append(" of ");
				name.append(damage);
				break;
			case MARK:
				name.append(" of ");
				name.append(mark);
				break;
			case MOVETARGET:
				name.append(" of ");
				name.append(moveTarget);
				break;
			case MOVEYOURSELF:
				name.append(" of ");
				name.append(moveYourself);
				break;
		}
		return name.toString();
	}
}
