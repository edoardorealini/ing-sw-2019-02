package model.powerup;

import model.Color;

import java.io.Serializable;

public class TargetingScope extends PowerUp  implements Serializable {

    public TargetingScope(Color color){
        super(color, PowerUpName.TARGETING_SCOPE);
    }
}
