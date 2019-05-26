package controller;

import model.Match;
import model.map.Directions;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;
import model.weapons.Weapon;

public class InputConverter {
    private String inputString;
    private int inputInt;
    private Match match;

    public InputConverter(Match match){
        this.match = match;
    }

    //TODO eccezioni per input sbagliati

    // prende in input le coordinate e restituisce loggetto sqare corrispondente
    public Square indexToSquare(int i, int j){
        return (match.getMap().getSquareFromIndex(i,j));
    }

    // da utilizzare quando si vuole prendere un arma da WeaponBox
    public Weapon intToWeapon(int numberOfWeapon){
       return match.getCurrentPlayer().getPosition().getAvailableWeapons().get(numberOfWeapon);
    }

    // metodo che riceve una stringa che indica il movimento voluto e lo trasforma
    public Directions stringToDirections (String movement){
        // TODO da cambiare assolutamente questi input
        switch (movement){
            case "su":
                return Directions.UP;
            case "giù":
                return Directions.DOWN;
            case "destra":
                return Directions.RIGHT;
            case "sinistra":
                return Directions.LEFT;

        }
        // TODO da togliere questo return (messo solo per non dare errore)
        //return Directions.DOWN;
        return null;
    }

    // restituisce il powerUp del giocatore in posizione indexOfPowerUp
    public PowerUp indexToPowerUp(int indexOfPowerUp){
        return match.getCurrentPlayer().getPowerUps()[indexOfPowerUp];
    }



    // sereva trovare il player passando come parametro l'ID
    public Player idToPlayer(int id){
        for (int i=0; i<match.getPlayers().size(); i++){
            if (match.getPlayers().get(i).getId()==id) return match.getPlayers().get(i);
        }
        return null;
    }


}
