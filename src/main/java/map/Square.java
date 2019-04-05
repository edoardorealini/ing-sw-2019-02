package map;
import java.util.*;

public class Square {
    private boolean activeStatus;
    private Color color;
    // private boolean door;       secondo me è una ripetizione, è già incluso in doors appena sotto RS
    private ArrayList<Boolean> allowedMoves;
    private ArrayList<Boolean> doors;

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Boolean> getDoors() {
        return doors;
    }

    public ArrayList<Boolean> getAllowedMoves() {
        return allowedMoves;
    }

}
