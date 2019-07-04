package model.powerup;

import model.Color;

import java.io.Serializable;

/**
 * Tagback granade powerUp
 */
public class TagbackGrenade extends PowerUp  implements Serializable {

    public TagbackGrenade(Color color){
        super(color, PowerUpName.TAGBACK_GRENADE);
    }
}
