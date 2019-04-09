package model.map;
import model.ammo.*;
import model.weapons.WeaponDeck;
import com.google.gson.*;

public class Map {
    private Square[][] squaresMatrix;
    private int mapID;
    private AmmoDeck ammoDeck;
    private WeaponDeck weaponDeck;

    //TODO inserire tutta la logica (metodi) per capire dato uno square chi posso vedere e che spostamenti posso fare
    /*public Map(){
        this.mapID = 0;
        this.squaresMatrix = null;
    }*/
    public Map(int mapID){
        this.mapID = mapID;
        this.squaresMatrix = new Square[4][3];
        this.ammoDeck = new AmmoDeck();

    }

    public int getMapID(){
        return mapID;
    }

    public void setSquareColor(int x, int y, Color color){
        squaresMatrix[x][y].setColor(color);

    }

}
