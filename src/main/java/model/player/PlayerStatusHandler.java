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

    public void setSpecialAbility(AbilityStatus specialAbility) {
        this.specialAbility = specialAbility;
    }

    public void setTurnStatus(RoundStatus turnStatus) {
        this.turnStatus = turnStatus;
    }
}
