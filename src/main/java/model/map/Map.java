package model.map;
import model.ammo.*;
public class Map {
    private Square[][] squaresMatrix;
    private int mapID;
    private AmmoDeck ammoDeck;

    //TODO inserire tutta la logica (metodi) per capire dato uno square chi posso vedere e che spostamenti posso fare
    public Map(){
        this.mapID = 0;
        this.squaresMatrix = null;
    }
    public Map(int mapID){
        this.mapID = mapID;
        this.squaresMatrix = new Square[4][3];
    }

    public int getMapID(){
        return mapID;
    }

    public void setSquareColor(int x, int y, Color color){
        squaresMatrix[x][y].setColor(color);
    }


}
