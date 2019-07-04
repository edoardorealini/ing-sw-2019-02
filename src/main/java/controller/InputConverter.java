package controller;

import exception.InvalidInputException;
import exception.NotInYourPossessException;
import exception.WrongValueException;
import model.Match;
import model.map.Directions;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;
import model.weapons.Weapon;

/**
 * This class is helpful to convert the input that is received from the client to a readable one for the controller classes
 */
public class InputConverter {
    private Match match;

    public InputConverter(Match match){
        this.match = match;
    }

    /**
     * This method transforms two indexes in the corresponding square in the match
     * @param i index of the abscissa
     * @param j index of the order
     * @throws InvalidInputException if the square that you selected is not an active one
     * @return the requested square
     */
    public Square indexToSquare(int i, int j) throws InvalidInputException{
        if(!(i >= 0 && i <= 3 && j >= 0 && j <= 2))
            throw new InvalidInputException("Error converting the input integer coordinates to square, not a valid input");

        if(!match.getMap().getSquareFromIndex(i,j).isActive())
            throw new InvalidInputException("The square that you selected is not an active one, please retry");

        return (match.getMap().getSquareFromIndex(i,j));
    }

    /**
     * This method returns the object weapon associated to the index numberOfWeapon contained in the weapon box in the position of the player
     * @param numberOfWeapon is the index of the weapon in the weapon Box
     * @return the object weapon
     */
    public Weapon intToWeapon(int numberOfWeapon){
       return match.getCurrentPlayer().getPosition().getAvailableWeapons().get(numberOfWeapon);
    }

    /**
     * This method returns the object weapon associated to the index numberOfWeapon contained in the player's weapons
     * @param numberOfWeapon is the index of the weapon in player
     * @return the object weapon
     */

    public Weapon intToWeaponInHand(int numberOfWeapon){
        return match.getCurrentPlayer().getWeapons()[numberOfWeapon];
    }


    /**
     * This method returns the object power Up associated to the index indexOfPowerUp contained in the player's power Up
     * @param indexOfPowerUp is the index of the power Up in player
     * @param user is the object player where to search
     * @return the object power Up
     * @throws NotInYourPossessException if the power Up in that position not exist
     * @see model.player.Player
     */
    public PowerUp indexToPowerUp(int indexOfPowerUp, Player user) throws NotInYourPossessException {
        if(user.getPowerUps()[indexOfPowerUp] != null)
            return user.getPowerUps()[indexOfPowerUp];
        else
            throw new NotInYourPossessException("The powerUp you selected is not in your hand");
    }


    /**
     * This method returns the player associated to the nikname
     * @param name is the nikname of the player to search
     * @return the object player
     * @throws WrongValueException if the nikname is not associated with any players
     */
    public Player nameToPlayer(String name) throws WrongValueException {
        for(Player p: match.getPlayers()){
            if(p.getNickname().equals(name))
                return p;
        }
        throw new WrongValueException("The player you are searching for does not exist in this match");
    }

}
