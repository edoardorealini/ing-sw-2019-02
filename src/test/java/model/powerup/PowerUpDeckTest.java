package model.powerup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PowerUpDeckTest {

    private PowerUpDeck powerUpDeck;

    @BeforeEach
    void setUp() {
        powerUpDeck = new PowerUpDeck();
    }

    @Test
    void shuffle() {
    }

    @Test
    void pickFirstCard(){
        System.out.println(powerUpDeck.pickFirstCard().toString());
    }

    @Test
    void addPowerUps(){
    }
}