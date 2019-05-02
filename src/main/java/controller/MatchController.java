package controller;

import model.player.Match;

public class MatchController{
    public Match match;

    //TODO ci sono altri attributi da mettere qui?

    public MatchController(int mapID){
        this.match = new Match(mapID);
    }



}