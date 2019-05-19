package model.weapons;


import controller.MoveController;
import controller.ShootingParametersInput;
import exception.NotAllowedMoveException;
import model.Match;

public abstract class Effect {
	private int damage;
	private int mark;
	private int moveYourself;
	private int moveTarget;
	private int involvedPlayers;
	private boolean needVisibleTarget;
	private int minShootDistance;
	private int sameTarget;
	private boolean sameSquare;

	//constructor
	public Effect(){
		damage = 0;
		mark = 0;
		moveYourself = 0;
		moveTarget = 0;
		involvedPlayers = 0;   //-1 means every player
		needVisibleTarget = true;
		minShootDistance = 0;   //-1 means the same square in which the striker is
		sameTarget = 0;  //index of the array of players choosen as targets, -1 means all targets
		sameSquare = false;
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

	public boolean needVisibleTarget() {
		return needVisibleTarget;
	}

	public int getSameTarget() {
		return sameTarget;
	}

	public void setSameTarget(int sameTarget) {
		this.sameTarget = sameTarget;
	}

	public abstract void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) throws NotAllowedMoveException;

	public boolean isSameSquare() {
		return sameSquare;
	}

	public void setSameSquare(boolean sameSquare) {
		this.sameSquare = sameSquare;
	}
}
