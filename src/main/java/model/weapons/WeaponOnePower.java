package model.weapons;


import java.util.*;


public class WeaponOnePower extends  Weapon {

	private ArrayList<Color> powerCost;
	private int additionalDamage;
	private int additionalMark;
	private int involvedPlayersOptional;  //number of target players for optional effect (-1 means "not limited")
	private boolean visibleTargetOptional;

	public WeaponOnePower(String weaponName){

		super();
		this.powerCost = new ArrayList<>();

		//constructor for Lock Rifle
		if (weaponName.equals("Lock Rifle")) {
			this.setName("Lock Rifle");
			this.setColor(Color.BLUE);
			this.setCost(Color.BLUE, Color.BLUE, Color.EMPTY);
			this.setPowerCost(Color.RED);
			this.setBasicDamage(2);
			this.setBasicMarks(1);
			this.setMinShootingDistance(0);
			this.setVisibleTarget(true);
			this.setInvolvedPlayersPrimary(1);
			this.setAdditionalMark(1);
			this.setAdditionalDamage(0);
			this.setInvolvedPlayersOptional(1);
			this.setVisibleTargetOptional(true);
		}

		//constructor for Heatseeker
		if (weaponName.equals("Heatseeker")) {
			this.setName("Heatseeker");
			this.setColor(Color.RED);
			this.setCost(Color.RED, Color.RED, Color.YELLOW);
			this.setPowerCost(Color.RED);
			this.setBasicDamage(3);
			this.setBasicMarks(0);
			this.setMinShootingDistance(2);
			this.setVisibleTarget(true);
			this.setInvolvedPlayersPrimary(1);
		}

		//constructor for GrenadeLauncher
		if (weaponName.equals("GrenadeLauncher")) {
			this.setName("GrenadeLauncher");
			this.setColor(Color.RED);
			this.setCost(Color.RED);
			this.setPowerCost(Color.RED);
			this.setBasicDamage(1);
			this.setBasicMarks(0);
			this.setMinShootingDistance(0);
			this.setVisibleTarget(true);
			this.setInvolvedPlayersPrimary(1);
			this.setAdditionalMark(1);
			this.setAdditionalDamage(1);
			this.setInvolvedPlayersOptional(-1);
			this.setVisibleTargetOptional(true);
		}
	}

	//overload of setCost()
	public void setCost(Color one) {
		this.getCost().add(one);
	}

	public void setCost(Color one, Color two, Color three) {
		this.getCost().add(one);
		this.getCost().add(two);
	}

	public void setPowerCost(Color additionalCost) {
		this.powerCost.add(additionalCost);
	}

	public void setInvolvedPlayersOptional(int involvedPlayersOptional) {
		this.involvedPlayersOptional = involvedPlayersOptional;
	}

	public void setAdditionalDamage(int additionalDamage) {
		this.additionalDamage = additionalDamage;
	}

	public void setAdditionalMark(int additionalMark) {
		this.additionalMark = additionalMark;
	}

	public void setVisibleTargetOptional(boolean visibleTargetOptional) {
		this.visibleTargetOptional = visibleTargetOptional;
	}

	public List<Color> getPowerCost() {
		return this.powerCost;
	}

	public int getInvolvedPlayersOptional() {
		return involvedPlayersOptional;
	}

	public int getAdditionalDamage() {
		return additionalDamage;
	}

	public int getAdditionalMark() {
		return additionalMark;
	}

	public boolean isVisibleTargetOptional() {
		return visibleTargetOptional;
	}
}
