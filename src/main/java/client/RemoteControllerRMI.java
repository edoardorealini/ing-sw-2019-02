package client;

import client.clientModel.Match;
import client.clientModel.*;
import client.clientModel.map.Map;
import client.clientModel.map.Square;
import client.clientModel.player.Player;
import client.clientModel.powerup.PowerUp;

public class RemoteControllerRMI extends RemoteController{

    private Match match;

    @Override
    Map getMap() {
        return null;
    }

    @Override
    Match getMatch() {
        return null;
    }

    @Override
    void buildMap(int mapID) {

    }

    @Override
    void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed) {

    }

    @Override
    boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance) {
        return false;
    }

    @Override
    void moveOneSquare(String movement) {

    }

    @Override
    void moveOneSquare(String movement, Player player) {

    }

    @Override
    void grabAmmoCard() {

    }

    @Override
    void grabWeapon(int indexOfWeapon) {

    }

    @Override
    void usePowerUpAsAmmo(int indexOfPowerUp) {

    }

    @Override
    void useTeleporter(PowerUp teleporter, Square destination) {

    }

    @Override
    void useNewton(PowerUp newton, Player affectedPlayer, Square destination) {

    }

    @Override
    void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer) {

    }

    @Override
    void useTargetingScope(PowerUp targetingScope, Player affectedPlayer) {

    }
}
