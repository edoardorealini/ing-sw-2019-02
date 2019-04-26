package model.map;
import model.ammo.*;
import model.weapons.WeaponDeck;
import com.google.gson.*;

import java.util.*;

public class Map {

    private Square[][] squaresMatrix;
    private int mapID;
    private AmmoDeck ammoDeck;
    private WeaponDeck weaponDeck;

    //TODO inserire tutta la logica (metodi) per capire dato uno square chi posso vedere e che spostamenti posso fare

    public int getMapID(){
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public void setSquareColor(int x, int y, Color color){
        squaresMatrix[x][y].setColor(color);
    }

    /*
        allowedMoves tells me which moves i can do from a specific square.
     */
    public List<Directions> allowedMoves(Square square) {
        //questo metodo dovrebbe lanciare una SquareNotValidException ? (controllo)
        ArrayList<Directions> alwdMoves = new ArrayList<>();

        //TODO qui c'è la logica che verifica quali spostamenti posso fare a partire da uno square;

        return alwdMoves;
    }

    /*
    test method to understand the json structure of the class
     */
    public String toJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }
}