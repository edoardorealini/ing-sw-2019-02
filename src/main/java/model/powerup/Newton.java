package model.powerup;

import model.Color;

import java.io.Serializable;

/**
 * Newton powerUp
 */
public class Newton extends PowerUp  implements Serializable {

    public Newton(Color color){
        super(color, PowerUpName.NEWTON);
    }
}
