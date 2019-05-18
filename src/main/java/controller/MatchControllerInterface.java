package controller;

import exception.NotAllowedMoveException;
import model.Match;
import model.map.Directions;
import model.map.Map;
import model.map.MapBuilder;
import model.map.Square;
import model.player.Player;
import model.powerup.PowerUp;
import model.weapons.Weapon;

import java.rmi.Remote;

 public interface MatchControllerInterface extends Remote {
	 Match getMatch();

	 Map getMap();

	 void buildMap(int mapID);

	//metodi derivanti da classe moveController
	 void move(Player player, Square destination, int maxDistanceAllowed) throws Exception;

	 boolean isAllowedMove(Square startingPoint, Square destination, int maxDistance);

	 void moveOneSquare(Directions direction) throws  Exception;

	 void moveOneSquare(Directions direction, Player player) throws Exception;

	 int minDistBetweenSquares(Square startingPoint, Square destination);

	//metodi da grab controller
	 void grabAmmoCard() throws Exception;

	 void grabWeapon(Weapon weapon) throws Exception;

	//metodi di powerUpController
	 void usePowerUpAsAmmo(PowerUp powerUp) throws Exception;

	 void useTeleporter(PowerUp teleporter, Square destination);

	 void useNewton(PowerUp newton, Player affectedPlayer, Square destination) throws NotAllowedMoveException;

	 void useTagbackGrenade(PowerUp tagbackGrenade, Player user, Player affectedPlayer);

	 void useTargetingScope(PowerUp targetingScope, Player affectedPlayer);

	 MoveController getMoveController();

	 ShootController getShootController();

}
