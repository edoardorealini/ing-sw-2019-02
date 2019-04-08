package model.map;

public class Square {

    private Boolean activeStatus; //in una mappa possono esserci degli square non attivi.
    private Color color;           //indica il colore della stanza.
    private Directions[] allowedMoves;//Directions of the allowed moves in the single Square.
    private Directions[] doors;       //Direction of door (if a direction is present, that is the door).

    public Square(){
        this.color = null;
        this.activeStatus = null;
        this.allowedMoves = null;
        this.doors = null;
    }


    public Square(Color color, Boolean activeStatus, Directions[] allowedMoves, Directions[] doors){
        this.color = color;
        this.activeStatus = activeStatus;
        this.allowedMoves = new Directions[4];
        this.allowedMoves[0] = allowedMoves[0];
        this.allowedMoves[1] = allowedMoves[1];
        this.allowedMoves[2] = allowedMoves[2];
        this.allowedMoves[3] = allowedMoves[3];
        this.doors = new Directions[4];
        this.doors[0] = doors[0];
        this.doors[1] = doors[1];
        this.doors[2] = doors[2];
        this.doors[3] = doors[3];
    }

    public Square(Boolean activeStatus){ //secondo costruttore per costruire square inattivi
        this.activeStatus = activeStatus;
    }

    public boolean getActiveStatus() {
        return activeStatus;
    }

    public Color getColor() {
        return color;
    }

    public Directions[] getAllowedMoves() {
        //TODO correggere: non posso dare in return direttamente il riferimento all'array!
        return allowedMoves;
    }

    public Directions[] getDoors() {
        //TODO correggere: non posso dare in return direttamente il riferimento all'array!
        return doors;
    }
}
