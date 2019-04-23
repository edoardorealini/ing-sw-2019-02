package model.player;

public class PlayerStatusHandler {

    private RoundStatus turnStatus;
    private AbilityStatus specialAbility;


    //TODO da rivedere il costruttore perchè il primo giocatore dovrebbe avere RoundStatus.master
    public void PlayerStatusHandler(){
        turnStatus = RoundStatus.waitTurn;
        specialAbility = AbilityStatus.normal;
    }

// metodi set per SpecialAbility

    public AbilityStatus getSpecialAbility(){
        return specialAbility;
    }

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

    public RoundStatus getTurnStatus(){
        return turnStatus;
    }

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
