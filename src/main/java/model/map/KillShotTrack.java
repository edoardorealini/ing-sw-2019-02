package model.map;

import java.io.Serializable;
import java.util.*;	  //NB: we have to use a List because we need to know the order of kills in case of tie

public class KillShotTrack implements Serializable {
	private List<Integer> mortalShots;
	private int skulls;
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
		//TODO tutta la classe +  aggiornare valore al primo turno
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
}