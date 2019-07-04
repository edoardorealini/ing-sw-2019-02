package model;

import java.io.Serializable;

/**
 * This enum gives all the possible colors in the game. It is largely used across a lot of classes
 */

public enum Color  implements Serializable {
    BLUE,
    RED,
    YELLOW,
    PURPLE,
    WHITE,
    GREEN        //added purple, white and green for players and rooms
}
