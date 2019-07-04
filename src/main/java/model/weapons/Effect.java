package model.weapons;


import controller.MoveController;
import exception.NotAllowedMoveException;
import model.Match;
import model.ShootingParametersInput;

import java.io.Serializable;

/**
 * This is an abstract class that is used to create four children: {@link EffectDamage}, {@link EffectMark}, {@link EffectMoveYourself}, {@link EffectMoveTarget},
 * in which it is implemented the abstract method  {@link #executeEffect(Match, MoveController, ShootingParametersInput)}.
 * This class represents the possible effects a weapon can have.
 * @author Riccardo
 */

public abstract class Effect implements Serializable {
	private int damage;
	private int mark;
	private int moveYourself;
	private int moveTarget;
	private int involvedPlayers;
	private boolean needVisibleTarget;
	private int minShootDistance;
	private int indexOfTarget;

	/**
	 * Constructor of the class. It sets every private field its default value (0 for int, true for boolean)
	 */
	public Effect(){
		damage = 0;
		mark = 0;
		moveYourself = 0;
		moveTarget = 0;
		involvedPlayers = 0;   //-1 means every player
		needVisibleTarget = true;
		minShootDistance = 0;   //-1 means the same square in which the striker is
		indexOfTarget = 0;  //index of the array of players chosen as targets, -1 means all targets
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

	public int getIndexOfTarget() {
		return indexOfTarget;
	}

	public void setIndexOfTarget(int indexOfTarget) {
		this.indexOfTarget = indexOfTarget;
	}

	/**
	 * This method will execute the particular effect on which it will be called.
	 * @param match	is the reference to the match
	 * @param ctrl	is the reference to the moveController
	 * @param input	is an instance of {@link ShootingParametersInput}
	 * @throws NotAllowedMoveException	thrown only in the implementation of {@link EffectMoveYourself} and {@link EffectMoveTarget}
	 */

	public abstract void executeEffect(Match match, MoveController ctrl, ShootingParametersInput input) throws NotAllowedMoveException;

}
