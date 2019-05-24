package model.powerup;

import model.Color;

import java.io.Serializable;

public class Teleporter extends PowerUp  implements Serializable {

    public Teleporter(Color color){
        super(color, PowerUpName.TELEPORTER);
    }
}
