package controller;

import model.Match;

public class MatchController{
    public Match match;
    public GrabController grabController;

    //TODO ci sono altri attributi da mettere qui?


    /*
        required mapID in input in order to build the correct type of configuration.
        the constructor builds the object match that contains all the objects useful for the game
        it also initializes all the values, for example filling the weaponBoxes with the references to the weapons
        that are taken directly from the WeaponDeck structure declared in the match object
    */
    public MatchController(int mapID){
        this.match = new Match(mapID);

        match.getMap().fillWeaponBox(match.getWeaponDeck());
        match.getMap().fillAmmo(match.getAmmoDeck());

    }


}