package model.powerUp;

import model.Color;

public class PowerUp {
    private Color color;
    private String name;

    public PowerUp(Color color, String name){
        this.color=color;
        this.name=name;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }



}
