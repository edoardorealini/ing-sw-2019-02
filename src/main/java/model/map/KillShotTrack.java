package model.map;

import java.io.Serializable;
import java.util.*;	  //NB: we have to use a List because we need to know the order of kills in case of tie

public class KillShotTrack implements Serializable {
	private List<Integer> mortalShots;
	private int skulls;
	private int totalSkulls;
	private List<Integer> doubleKill;

	public KillShotTrack() {
		mortalShots = new ArrayList<>();
		doubleKill = new ArrayList<>();
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

	public void setSkulls(int skulls) {
		this.skulls = skulls;
	}

	public void decreaseSkulls() {
		this.skulls--;
	}

	public void setMortalShots(int idPlayer){
		mortalShots.add(idPlayer);
	}

	public void setDoubleKill(int idPlayer) {
		this.doubleKill.add(idPlayer);
	}

	public void setTotalSkulls(int totalSkulls) {
		this.totalSkulls = totalSkulls;
	}

	public int getTotalSkulls() {
		return totalSkulls;
	}

	public int howManyMortalShotsForPlayer (int idPlayer) {
		int cont = 0;
		for (int shot : mortalShots) {
			if (shot == idPlayer)
				cont++;
		}
		return cont;
	}

	public int whoMadeDamageBefore(List<Integer> arrayIDPlayer) {
		for (int shot : mortalShots) {
			if (arrayIDPlayer.contains(shot))
				return shot;		//remember shot is the id of the player
		}
		return -1;
	}


}