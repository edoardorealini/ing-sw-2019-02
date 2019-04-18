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

    public List<Directions> allowedMoves(Square square) { //questo metodo dovrebbe lanciare una SquareNotValidException ? (controllo)
    //TODO qui c'è la logica che verifica quali spostamenti posso fare a partire da uno square;
        ArrayList<Directions> alwdMoves = new ArrayList<>();

        return alwdMoves;
    }

    public String toJson(){
        Gson json = new Gson();
        return json.toJson(this);
    }

}
