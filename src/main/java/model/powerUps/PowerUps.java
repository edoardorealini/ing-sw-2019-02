package model.powerUps;

import model.Color;

public class PowerUps {
    private Color color;
    private String name;

    public PowerUps (Color color, String name){
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
