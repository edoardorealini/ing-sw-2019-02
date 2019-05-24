package model.player;

import java.io.Serializable;

public class PlayerStatusHandler  implements Serializable {

    private RoundStatus turnStatus;
    private AbilityStatus specialAbility;


    public void PlayerStatusHandler(){
        turnStatus = RoundStatus.WAIT_TURN;
        specialAbility = AbilityStatus.NORMAL;
    }

// metodi set per SpecialAbility

    public AbilityStatus getSpecialAbility(){
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

    public void setSpecialAbilityAdrenalineShoot() {
        this.specialAbility = AbilityStatus.ADRENALINE_PICK;
    }

// metodi set per TurnStatus

    public RoundStatus getTurnStatus(){
        return turnStatus;
    }

    public void setTurnStatusMaster(){
        this.turnStatus = RoundStatus.MASTER;
    }

    public void setTurnStatusWaitTurn() {

        this.turnStatus = RoundStatus.WAIT_TURN;
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
}
