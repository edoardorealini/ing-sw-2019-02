package client.remoteController;

import client.GUI.FirstPage;
import model.map.*;
import model.player.*;
import model.Match;
import model.powerup.*;

import javax.security.auth.login.FailedLoginException;

public abstract class SenderClientRemoteController {

    abstract Match getMatch(String nicknamePlayer);

    public abstract Map getMap(String nicknamePlayer);

    public abstract void buildMap(int mapID, String nicknamePlayer) throws Exception;

    //metodi derivanti da classe moveController
    abstract void move(Player player, int iDestination, int jDestination, int maxDistanceAllowed, String nicknamePlayer);

    abstract boolean isAllowedMove(Square startingPoint, int iDestination, int jDestination, int maxDistance, String nicknamePlayer);

    abstract void moveOneSquare(String movement, String nicknamePlayer);

    abstract void moveOneSquare(String movement, Player player, String nicknamePlayer);

    //metodi da grab controller
    abstract void grabAmmoCard(String nicknamePlayer);

    abstract void grabWeapon(int indexOfWeapon, String nicknamePlayer);

    //metodi di powerUpController
    abstract void usePowerUpAsAmmo(int indexOfPowerUp, String nicknamePlayer);

    abstract void useTeleporter(PowerUp teleporter, Square destination, String nicknamePlayer);

    abstract void useNewton(PowerUp newton, Player affectedPlayer, Square destination, String nicknamePlayer);

    abstract void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer, String nicknamePlayer);

    abstract void useTargetingScope(PowerUp targetingScope, Player affectedPlayer, String nicknamePlayer);

    public abstract String checkConnection(String IP, String nicknamePlayer);

    public abstract void addPlayer(String nickName) throws FailedLoginException;

    public abstract int connectedPlayers();

    public abstract PlayerStatusHandler getPlayerStatus(int idPlayer, String nicknamePlayer);

    public abstract boolean getMatchStatus();

    public abstract void setFirstPage(FirstPage firstPage);

}
