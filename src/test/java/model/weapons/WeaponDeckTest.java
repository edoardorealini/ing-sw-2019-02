package model.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponDeckTest {

	private WeaponDeck deckTest = new WeaponDeck();

	@Test
	void isInDeck() {
		assertEquals(true, deckTest.isInDeck(WeaponName.ELECTROSCYTHE));
		assertEquals(true, deckTest.isInDeck(WeaponName.SLEDGEHAMMER));
		assertEquals(true, deckTest.isInDeck(WeaponName.ROCKET_LAUNCHER));
		assertEquals(true, deckTest.isInDeck(WeaponName.LOCK_RIFLE));
		// assertEquals(false, deckTest.isInDeck(WeaponName.TEST_NAME));

	}

	@Test
	void shuffleDeck() {
		System.out.println(deckTest.toString());
		deckTest.shuffleDeck();
		System.out.println(deckTest.toString());
		deckTest.shuffleDeck();
		System.out.println(deckTest.toString());


	}

	@Test
	void pickFirstCard() {
		Weapon temp;
		System.out.println(deckTest.toString());
		deckTest.shuffleDeck();
		System.out.println(deckTest.toString());
		temp = deckTest.pickFirstCard();
		System.out.println(temp.toString());
		System.out.println(deckTest.toString());
	}

}