package model.weapons;

public class Effect {
	private int damage;
	private int mark;
	private int moveYourself;
	private int moveTarget;

	//constructor
	public Effect(){
		damage = 0;
		mark = 0;
		moveYourself = 0;
		moveTarget = 0;
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

}
