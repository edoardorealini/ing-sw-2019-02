package client.clientModel.map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MortalPath implements Serializable {
	private List<Integer> mortalShots;
	private int skulls;
	private List<Integer> doubleKill;

	public MortalPath() {
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
		//TODO aggiornare valore al primo turno
	}

	public void setMortalShots(int shots, int idPlayer){		//shots can be 1 or 2, depending on the rage damage
		mortalShots.add(idPlayer);
		if (shots == 2) mortalShots.add(idPlayer);
	}

}