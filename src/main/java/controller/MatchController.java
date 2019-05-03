package controller;

import model.Match;
import model.map.*;

public class MatchController{
    private Match match;
    private GrabController grabController;

    //TODO ci sono altri attributi da mettere qui?


    /*
        required mapID in input in order to build the correct type of configuration.
        the constructor builds the object match that contains all the objects useful for the game
        it also initializes all the values, for example filling the weaponBoxes with the references to the weapons
        that are taken directly from the WeaponDeck structure declared in the match object
    */
    public MatchController(int mapID){
        this.match = new Match(mapID);
        this.grabController = new GrabController();

        match.getMap().fillWeaponBox(match.getWeaponDeck());
        match.getMap().fillAmmo(match.getAmmoDeck());
    }

    public Map getMap(){
        return match.getMap();
    }

}