package model.weapons;

public class Effect {
	private int damage;
	private int mark;
	private int moveYourself;
	private int moveTarget;
	private int involvedPlayers;
	private boolean needVisibleTarget;
	private int minShootDistance;

	//constructor
	public Effect(){
		damage = 0;
		mark = 0;
		moveYourself = 0;
		moveTarget = 0;
		involvedPlayers = 0;
		needVisibleTarget = true;
		minShootDistance = 0;
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
}
