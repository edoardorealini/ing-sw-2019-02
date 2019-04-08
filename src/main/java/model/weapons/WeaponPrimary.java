package model.weapons;



public class WeaponPrimary extends Weapon {

	public WeaponPrimary(String weaponName){


		//constructor for Whisper
		if (weaponName.equals("Whisper")) {
			this.setName("Whisper");
			this.setColor(Color.BLUE);
			this.setCost(Color.BLUE, Color.BLUE, Color.YELLOW);
			this.setBasicDamage(3);
			this.setBasicMarks(1);
			this.setMinShootingDistance(2);
			this.setVisibleTarget(true);
			this.setInvolvedPlayers(1);
		}

		//constructor for Heatseeker
		if (weaponName.equals("Heatseeker")) {
			this.setName("Heatseeker");
			this.setColor(Color.RED);
			this.setCost(Color.RED, Color.RED, Color.YELLOW);
			this.setBasicDamage(3);
			this.setBasicMarks(0);
			this.setMinShootingDistance(2);
			this.setVisibleTarget(false);
			this.setInvolvedPlayers(1);
		}
	}


	public void setCost(Color one, Color two, Color three) {
		this.getCost().add(one);
		this.getCost().add(two);
		this.getCost().add(three);
	}


}

