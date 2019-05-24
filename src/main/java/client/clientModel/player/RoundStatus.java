package client.clientModel.player;

import java.io.Serializable;

public enum RoundStatus implements Serializable {
    MASTER,
    WAIT_TURN,
    FIRST_ACTION,
    SECOND_ACTION,
    RELOADING,
    END_TURN,
}
