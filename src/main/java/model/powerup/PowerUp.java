package model.powerup;

import model.Color;
import java.io.Serializable;

/**
 * Descriprion of a powerUp
 */
public class PowerUp  implements Serializable {
    private Color color;
    private PowerUpName name;


    public PowerUp(Color color, PowerUpName name){
        this.color = color;     //color rappresenta il colore della carta ma anche il colore della munizione associata alla carta
        this.name = name;
    }

    public  Color getColor(){
        return color;
    }

    public  PowerUpName getName(){
        return name;
    }

    @Override
    public String toString() {
        StringBuilder name = new StringBuilder();
        name.append(this.name);
        name.append(this.color);
        name.append("PowerUp");
        return name.toString();
    }
}
