package map;
import java.util.*;

public class Map {
    private ArrayList<ArrayList<Square>> squaresMatrix; //sta cosa va bene?!
    private int mapID;

    public int getMapID(){
        return mapID;
    }

    public boolean isFull(){
        if(squaresMatrix.isEmpty())
            return false;

        return true;
    }

}
