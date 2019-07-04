package model.powerup;

import model.Color;

import java.io.Serializable;

/**
 * Teleporter powerUp
 */
public class Teleporter extends PowerUp  implements Serializable {

    public Teleporter(Color color){
        super(color, PowerUpName.TELEPORTER);
    }
}
