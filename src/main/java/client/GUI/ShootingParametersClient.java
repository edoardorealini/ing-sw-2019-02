package client.GUI;

import model.ShootMode;
import model.map.Directions;

import java.util.ArrayList;

public class ShootingParametersClient {
    private ArrayList<String> targetPlayers;
    private ArrayList<ShootMode> shootModes;
    private ArrayList<Integer> squaresCoordinates;
    private Directions direction;
    private boolean makeDamageBeforeMove;

    public ShootingParametersClient() {
        this.shootModes = new ArrayList<>();
        this.targetPlayers = new ArrayList<>();
        this.squaresCoordinates = new ArrayList<>();
    }

    public void setShootModes(ShootMode shootMode) {
        this.shootModes.add(shootMode);
    }

    public void setTargetPlayers(String nick) {
        this.targetPlayers.add(nick);
    }

    public void setSquaresCoordinates(int x, int y) {
        this.squaresCoordinates.add(x);
        this.squaresCoordinates.add(y);
    }

    public void setDirection(Directions direction) {
        this.direction = direction;
    }

    public void setMakeDamageBeforeMove(boolean makeDamageBeforeMove) {
        this.makeDamageBeforeMove = makeDamageBeforeMove;
    }

    public Directions getDirection() {
        return direction;
    }

    public ArrayList<ShootMode> getShootModes() {
        return shootModes;
    }

    public ArrayList<Integer> getSquaresCoordinates() {
        return squaresCoordinates;
    }

    public ArrayList<String> getTargetPlayers() {
        return targetPlayers;
    }

    public boolean getMakeDamageBeforeMove() {
        return makeDamageBeforeMove;
    }
}
