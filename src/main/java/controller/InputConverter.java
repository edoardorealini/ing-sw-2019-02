package controller;

import model.Match;
import model.map.Square;
import model.player.Player;
import model.weapons.Weapon;

public class InputConverter {
    private String inputString;
    private int inputInt;
    private Match match;

    public InputConverter(Match match){
        this.match = match;
    }

    // prende in input le coordinate e restituisce loggetto sqare corrispondente
    public Square indexToSquare(int i, int j){
        return (match.getMap().getSquareFromIndex(i,j));
    }

    // da utilizzare quando si vuole prendere un arma da WeaponBox
    public Weapon intToWeapon(int numberOfWeapon){
       return match.getCurrentPlayer().getPosition().getAvailableWeapons().get(numberOfWeapon);
    }

    // sereva trovare il player passando come parametro l'ID
    public Player idToPlayer(int id){
        for (int i=0; i<match.getPlayers().size(); i++){
            if (match.getPlayers().get(i).getId()==id) return match.getPlayers().get(i);
        }
        return null;
    }


}
