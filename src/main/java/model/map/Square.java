package model.map;

import java.util.*;

public class Square {

    private Boolean activeStatus; //in una mappa possono esserci degli square non attivi.
    private Color color;           //indica il colore della stanza.
    private ArrayList<Directions> allowedMoves;//Directions of the allowed moves in the single Square.
    private ArrayList<Directions> doors;       //Direction of door (if a direction is present, that is the door).

    public Square(){
        this.color = null;
        this.activeStatus = null;
        this.allowedMoves = null;
        this.doors = null;
    }

    public Square(Color color, Boolean activeStatus, ArrayList<Directions> allowedMoves, ArrayList<Directions> doors){
        this.color = color;
        this.activeStatus = activeStatus;
        this.allowedMoves = new ArrayList<>();
        this.allowedMoves.addAll(allowedMoves);
        this.doors = new ArrayList<>();
        this.doors.addAll(doors);
    }

    public Square(Boolean activeStatus){ //secondo costruttore per costruire square inattivi
        this.activeStatus = activeStatus;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public Color getColor() {
        return color;
    }

    public List<Directions> getAllowedMoves() {
        return allowedMoves;
    }

    public List<Directions> getDoors() {
        return doors;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void setAllowedMoves(ArrayList<Directions> allowedMoves) {
        this.allowedMoves = allowedMoves;
    }

    public void setDoors(ArrayList<Directions> doors) {
        this.doors = doors;
    }
}
