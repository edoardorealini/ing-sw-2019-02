package model.player;

public class PlayerStatusHandler {
    private RoundStatus turnStatus;
    private AbilityStatus specialAbility;

    public RoundStatus getTurnStatus(){
        return turnStatus;
    }

    public AbilityStatus getSpecialAbility(){
        return specialAbility;
    }


// metodi set per SpecialAbility

    public void setSpecialAbilityNormal() {
        this.specialAbility = AbilityStatus.normal;
    }

    public void setSpecialAbilityAdrenalinePick() {
        this.specialAbility = AbilityStatus.adrenalinePick;
    }

    public void setSpecialAbilityFrenzy() {
        this.specialAbility = AbilityStatus.frenzy;
    }

    public void setSpecialAbilityAdrenalineShoot() {
        this.specialAbility = AbilityStatus.adrenalinePick;
    }

// metodi set per TurnStatus

    public void setTurnStatusMaster(){
        this.turnStatus = RoundStatus.Master;
    }

    public void setTurnStatusWaitTurn() {

        this.turnStatus = RoundStatus.waitTurn;
    }

    public void setTurnStatusFirstAction() {
        this.turnStatus = RoundStatus.firstAction;
    }

    public void setTurnStatusSecondAction() {
        this.turnStatus = RoundStatus.secondAction;
    }

    public void setTurnStatusReloading() {
        this.turnStatus = RoundStatus.reloading;
    }

    public void setTurnStatusEndTurn() {
        this.turnStatus = RoundStatus.endTurn;
    }
}
