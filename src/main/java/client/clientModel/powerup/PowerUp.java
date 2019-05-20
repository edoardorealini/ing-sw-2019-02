package client.clientModel.powerup;

import client.clientModel.Color;

public class PowerUp {
    private Color color;
    private PowerUpName name;


    public PowerUp(Color color, PowerUpName name){
        this.color = color;     //color rappresenta il colore della carta ma anche il colore della munizione associata alla carta
        this.name = name;
    }

    public Color getColor(){
        return color;
    }
    public PowerUpName getName(){
        return name;
    }
}
