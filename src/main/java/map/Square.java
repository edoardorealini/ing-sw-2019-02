package map;
import java.util.*;

public class Square {

    private boolean activeStatus;
    private Color color;
    private Directions[] allowedMoves;//Directions of the allowed moves in the single Square.
    private Door[] doors;       //Direction of door



    public Square(Color color){
        //TODO costruttore
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

    public Directions[] getDoors() {
        //TODO correggere: non posso dare in return direttamente il riferimento all'array!
        return doors;
    }
}
