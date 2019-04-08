package map;
import java.util.*;

public class Square {

    private Boolean activeStatus; //in una mappa possono esserci degli square non attivi.
    private Color color;           //indica il colore della stanza.
    private Directions[] allowedMoves;//Directions of the allowed moves in the single Square.
    private Door[] doors;       //Direction of door



    public Square(Color color, Boolean activeStatus, Directions[] allowedMoves){
        this.color = color;
        this.activeStatus = activeStatus;
        this.allowedMoves = new Directions[4];
        this.allowedMoves[0] = allowedMoves[0];
        this.allowedMoves[1] = allowedMoves[1];
        this.allowedMoves[2] = allowedMoves[2];
        this.allowedMoves[3] = allowedMoves[3];
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public Color getColor() {
        return color;
    }

    public Directions[] getAllowedMoves() {
        //TODO correggere: non posso dare in return direttamente il riferimento all'array!
        return allowedMoves;
    }

    public Door[] getDoors() {
        //TODO correggere: non posso dare in return direttamente il riferimento all'array!
        return doors;
    }
}
