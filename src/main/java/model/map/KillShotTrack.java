package model.map;

import java.io.Serializable;
import java.util.*;	  //NB: we have to use a List because we need to know the order of kills in case of tie

/**
 * This class represents the killShotTrack, the object that contains the skulls (number of total deaths during the match)
 */
public class KillShotTrack implements Serializable {
	private List<Integer> mortalShots;
	private int skulls;
	private int totalSkulls;
	private List<Integer> doubleKill;

	/**
	 * Default constructor
	 * Builds the mortal shots and the double kill collections
	 */
	public KillShotTrack() {
		mortalShots = new ArrayList<>();
		doubleKill = new ArrayList<>();
	}

	/**
	 *
	 * @return returns the list of mortal shots (integers representing the players)
	 */
	public List<Integer> getMortalShots() {
		return mortalShots;
	}

	/**
	 *
	 * @return returns the list of double kills (integers representing the players)
	 */
	public List<Integer> getDoubleKill() {
		return doubleKill;
	}

	/**
	 *
	 * @return returns the number of skulls remaining in the match
	 */
	public int getSkulls() {
		return skulls;
	}


	public void setSkulls(int skulls) {
		this.skulls = skulls;
	}

	/**
	 * This method decreases of one unit the number of total skulls
	 */
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

	/**
	 * Used to calculate the points
	 * @return returns the total number of skulls
	 */
	public int getTotalSkulls() {
		return totalSkulls;
	}

	/**
	 * Used to calculate the points
	 * @param idPlayer id (int) that identifies the player
	 * @return	returns the number of mortal shots for the given player id
	 * @see model.player.Player
	 */
	public int howManyMortalShotsForPlayer (int idPlayer) {
		int cont = 0;
		for (int shot : mortalShots) {
			if (shot == idPlayer)
				cont++;
		}
		return cont;
	}

	/**
	 * Used to calculate the points
	 * @param arrayIDPlayer
	 * @return returns the id of the player who did the first damage
	 * @see model.player.Player
	 */
	public int whoMadeDamageBefore(List<Integer> arrayIDPlayer) {
		for (int shot : mortalShots) {
			if (arrayIDPlayer.contains(shot))
				return shot;		//remember shot is the id of the player
		}
		return -1;
	}


}