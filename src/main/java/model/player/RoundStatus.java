package model.player;

import java.io.Serializable;

public enum RoundStatus implements Serializable {
    LOBBY,
    LOBBY_MASTER,
    MASTER,
    WAIT_TURN,
    FIRST_ACTION,
    SECOND_ACTION,
    RELOADING,
    END_TURN,
    DISCONNECTED
}
