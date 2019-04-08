package weapons;



public class WeaponPrimary extends Weapon {

	public WeaponPrimary(String weaponName){

		if (weaponName.equals("Whisper")) {
			this.setName("Whisper");
			this.setColor(Color.BLUE);
			this.setCost();
			this.setAmmoStatus(WeaponAmmoStatus.PARTIALLYLOADED);
			this.setBasicDamage(3);
			this.setBasicTargets(1);
		}
	}

}
s