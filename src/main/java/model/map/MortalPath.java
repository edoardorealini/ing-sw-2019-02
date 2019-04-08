package model.map;

import java.util.ArrayList;
import java.util.List;	  //NB: we have to use a List because we need to know the order of kills in case of draw

public class MortalPath {
	private List<Integer> mortalShots;  //id players or should we use an array of Color?
	private int skulls;
	private List<Integer> doubleKill;    //same comment as above

	public MortalPath() {
		mortalShots = new ArrayList<>();
		doubleKill = new ArrayList<>();
		//we have to initialize "skulls" with the number choosen by the first model.player (e.g. 5 or 8)
	}

	public List<Integer> getMortalShots() {
		return mortalShots;
	}

	public List<Integer> getDoubleKill() {
		return doubleKill;
	}

	public int getSkulls() {
		return skulls;
	}

	public void startLastRound() {}

	public void kill(int shots, int idPlayer) {		//shots can be 1 or 2, depending on the rage damage
		mortalShots.add(idPlayer);
		if (shots == 2) mortalShots.add(idPlayer);
	}

}