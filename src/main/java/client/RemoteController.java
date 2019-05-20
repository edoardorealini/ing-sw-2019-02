package client;

import exception.NotAllowedMoveException;
import exception.NotAllowedTargetException;
import client.clientModel.Match;
import client.clientModel.map.Map;
import client.clientModel.map.Square;
import client.clientModel.player.Player;
import client.clientModel.powerup.PowerUp;

import java.rmi.RemoteException;

public abstract class RemoteController {

    abstract Match getMatch();

    abstract  Map getMap();

    abstract void buildMap(int mapID);

    //metodi derivanti da classe moveController
    abstract void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed);
    abstract boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance);
    abstract void moveOneSquare(String movement);
    abstract void moveOneSquare(String movement, Player player);

    //metodi da grab controller
    abstract void grabAmmoCard();
    abstract void grabWeapon(int indexOfWeapon);

    //metodi di powerUpController
    abstract void usePowerUpAsAmmo(int indexOfPowerUp);
    abstract void useTeleporter(PowerUp teleporter, Square destination);
    abstract void useNewton(PowerUp newton, Player affectedPlayer, Square destination);
    abstract void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer);
    abstract void useTargetingScope(PowerUp targetingScope, Player affectedPlayer);


}
