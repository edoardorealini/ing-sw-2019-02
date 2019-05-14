package controller;

import model.map.Directions;
import model.map.Square;
import model.player.Player;

import java.util.*;

public class Input {
	//this class is a container of fields that will be setted by the user
	private ArrayList<ShootMode> shootModes;
	private ArrayList<Player> targets;
	private ArrayList<Square> squares;
	private Directions direction;

	public Input(){
		//constructor of Input Class
		this.shootModes = new ArrayList<>();
		this.targets = new ArrayList<>();
		this.squares = new ArrayList<>();
	}

	public List<Player> getTargets() {
		return targets;
	}

	public List<ShootMode> getShootModes() {
		return shootModes;
	}

	public List<Square> getSquares() {
		return squares;
	}

	public Directions getDirection() {
		return direction;
	}

	public void setShootModes(ShootMode shootMode) {
		this.shootModes.add(shootMode);
	}

	public void setTargets(Player target) {
		this.targets.add(target);
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}

	public void setSquares(Square square) {
		this.squares.add(square);
	}
}
