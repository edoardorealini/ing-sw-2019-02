package commons;

import model.ShootMode;
import model.map.Directions;
import model.weapons.WeaponName;

import java.util.*;

public class ShootingParametersClient extends Object{

    private WeaponName name;
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

    public List<ShootMode> getShootModes() {
        return shootModes;
    }

    public List<Integer> getSquaresCoordinates() {
        return squaresCoordinates;
    }

    public List<String> getTargetPlayers() {
        return targetPlayers;
    }

    public boolean getMakeDamageBeforeMove() {
        return makeDamageBeforeMove;
    }

    public WeaponName getName() {
        return name;
    }

    public void setName(WeaponName name) {
        this.name = name;
    }
}
