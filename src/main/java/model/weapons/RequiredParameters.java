package model.weapons;

import java.io.Serializable;

/**
 * This class contains some flags that allow to build popups for the weapons in the GUI. More specifically it contains ShootModeType,
 * numberOfTargets, numberOfSquares, direction and a boolean used to know if the player wants to make damage before of the move effect.
 * The generated popup will be {@link client.GUI.GeneralWeaponPopUp}.
 * @author Riccardo
 */

public class RequiredParameters implements Serializable {

    private int shootModeType;
    private int numberOfTargets;
    private int numberOfSquares;
    private boolean direction;
    private boolean makeDamageBeforeMove;

    public RequiredParameters(){
        this.shootModeType = 0;
        this.numberOfSquares = 0;
        this.numberOfTargets = 0;
        this.direction = false;
        this.makeDamageBeforeMove = false;
    }

    public void setMakeDamageBeforeMove(boolean makeDamageBeforeMove) {
        this.makeDamageBeforeMove = makeDamageBeforeMove;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public void setNumberOfSquares(int numberOfSquares) {
        this.numberOfSquares = numberOfSquares;
    }

    public void setNumberOfTargets(int numberOfTargets) {
        this.numberOfTargets = numberOfTargets;
    }

    public void setShootModeType(int shootModeType) {
        this.shootModeType = shootModeType;
    }

    public int getNumberOfSquares() {
        return numberOfSquares;
    }

    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    public int getShootModeType() {
        return shootModeType;
    }

    public boolean needToKnowDamageBeforeMove() {
        return makeDamageBeforeMove;
    }

    public boolean needToKnowDirection() {
        return direction;
    }
}
