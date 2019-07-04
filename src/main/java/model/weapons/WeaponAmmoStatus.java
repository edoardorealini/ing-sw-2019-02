package model.weapons;

import java.io.Serializable;

/**
 * It is an enumeration for the possible status of the weapons: {@link #LOADED}, {@link #PARTIALLYLOADED}, {@link #UNLOADED}
 * @author Riccardo
 */

public enum WeaponAmmoStatus  implements Serializable {
    LOADED,
    PARTIALLYLOADED,
    UNLOADED;
}
