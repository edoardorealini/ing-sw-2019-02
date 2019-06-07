package model.weapons;

public class RequiredParameters {
    //this class contains flags that allow to build popups for the weapons in the GUI
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
