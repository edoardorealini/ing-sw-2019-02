package model.map;

import java.io.Serializable;

/**
 * This enumeration represents the directions that are allowed in a general case from a square in a map.
 * It can have the following values: UP, DOWN, RIGHT, LEFT
 */
public enum Directions implements Serializable {
    UP,
    DOWN,
    RIGHT,
    LEFT
}
