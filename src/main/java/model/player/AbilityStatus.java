package model.player;

import java.io.Serializable;

/**
 * This enumeration in used to identify all the possible ability statuses for a player,
 * the value of the field AbiltyStatus determines if a player can do certain actions
 */
public enum AbilityStatus  implements Serializable {
    NORMAL,
    ADRENALINE_PICK,
    ADRENALINE_SHOOT,

    FRENZY_LOWER,
    FRENZY
}
