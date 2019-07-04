package model.player;

import java.io.Serializable;


/**
 * This class is the container for the 2 player status fields: RoundStatus and AbilityStatus
 */
public class PlayerStatusHandler  implements Serializable {

    private RoundStatus turnStatus;
    private AbilityStatus specialAbility;


    public PlayerStatusHandler(){
        this.turnStatus = RoundStatus.LOBBY;       // in modo che quando viene generato un giocatore esso sia direttamente in stato LOBBY
        this.specialAbility = AbilityStatus.NORMAL;// come si gestisce questa roba qui ?!
    }

// metodi set per SpecialAbility

    public AbilityStatus getSpecialAbility() {
        return specialAbility;
    }

    public void setSpecialAbilityNormal() {
        this.specialAbility = AbilityStatus.NORMAL;
    }

    public void setSpecialAbilityAdrenalinePick() {
        this.specialAbility = AbilityStatus.ADRENALINE_PICK;
    }

    public void setSpecialAbilityFrenzy() {
        this.specialAbility = AbilityStatus.FRENZY;
    }

    public void setSpecialAbilityFrenzyLower() {
        this.specialAbility = AbilityStatus.FRENZY_LOWER;
    }

    public void setSpecialAbilityAdrenalineShoot() {
        this.specialAbility = AbilityStatus.ADRENALINE_SHOOT;
    }

// metodi set per TurnStatus

    public RoundStatus getTurnStatus() {
        return turnStatus;
    }

    public void setTurnStatusMaster(){
        this.turnStatus = RoundStatus.MASTER;
    }
    public void setTurnStatusDisconnected(){
        this.turnStatus = RoundStatus.DISCONNECTED;
    }

    public void setTurnStatusWaitTurn() {
        this.turnStatus = RoundStatus.WAIT_TURN;
    }
    public void setTurnStatusLobbyMaster() {
        this.turnStatus = RoundStatus.LOBBY_MASTER;
    }

    public void setTurnStatusLobby(){
        this.turnStatus = RoundStatus.LOBBY;
    }

    public void setTurnStatusFirstAction() {
        this.turnStatus = RoundStatus.FIRST_ACTION;
    }

    public void setTurnStatusSecondAction() {
        this.turnStatus = RoundStatus.SECOND_ACTION;
    }

    public void setTurnStatusReloading() {
        this.turnStatus = RoundStatus.RELOADING;
    }

    public void setTurnStatusEndTurn() {
        this.turnStatus = RoundStatus.END_TURN;
    }

    public void setTurnStatusSpawn(){
        this.turnStatus = RoundStatus.SPAWN;
    }

    public void setTurnStatusWaitFirstTurn(){
        this.turnStatus = RoundStatus.WAIT_FIRST_TURN;
    }

    public void setTurnStatusRespawn(){ this.turnStatus = RoundStatus.RESPAWN; }

    public void setTurnStatusWaitTurnFrenzy(){
        this.turnStatus = RoundStatus.WAIT_TURN_FRENZY;
    }

    public void setTurnStatusFirstActionFrenzy(){
        this.turnStatus = RoundStatus.FIRST_ACTION_FRENZY;
    }

    public void setTurnStatusSecondActionFrenzy(){
        this.turnStatus = RoundStatus.SECOND_ACTION_FRENZY;
    }

    public void setTurnStatusFirstActionLowerFrenzy(){
        this.turnStatus = RoundStatus.FIRST_ACTION_LOWER_FRENZY;
    }

    public void setTurnStatusEndGame(){
        this.turnStatus = RoundStatus.END_GAME;
    }


//metodi isInStatus ...

    public boolean isInStatusWaitTurn(){
        if(turnStatus.equals(RoundStatus.WAIT_TURN))
            return true;

        return false;
    }

    public boolean isInStatusMaster(){
        if(turnStatus.equals(RoundStatus.MASTER))
            return true;

        return false;
    }

    public boolean isInStatusLobbyMaster(){
        if(turnStatus.equals(RoundStatus.LOBBY_MASTER))
            return true;

        return false;
    }

    public boolean isInStatusLobby(){
        if(turnStatus.equals(RoundStatus.LOBBY))
            return true;

        return false;
    }

    public boolean isInStatusFirstAction(){
        if(turnStatus.equals(RoundStatus.FIRST_ACTION))
            return true;

        return false;
    }

    public boolean isInStatusSecondAction(){
        if(turnStatus.equals(RoundStatus.SECOND_ACTION))
            return true;

        return false;
    }

    public boolean isInStatusSpawn(){
        if(turnStatus.equals(RoundStatus.SPAWN))
            return true;

        return false;
    }

    public boolean isInStatusReloading(){
        if(turnStatus.equals(RoundStatus.RELOADING))
            return true;

        return false;
    }

    public boolean isInStatusEndTurn(){
        if(turnStatus.equals(RoundStatus.END_TURN))
            return true;

        return false;
    }

    public boolean isInStatusWaitFirstTurn(){
        if(turnStatus.equals(RoundStatus.WAIT_FIRST_TURN))
            return true;

        return false;
    }

}
