package map;
import ammo.*;

public class NoSpawnSquare extends Square{
    private AmmoCard ammoTile;
    private AmmoDeck deckOfAmmo;

    public NoSpawnSquare(Color color, Boolean activeStatus, Directions[] allowedMoves, Directions[] doors, AmmoCard ammoTile, AmmoDeck deckOfAmmo) {
        super(color, activeStatus, allowedMoves, doors);
        this.ammoTile = ammoTile;
        this.deckOfAmmo = deckOfAmmo;
    }

    public NoSpawnSquare(Boolean activeStatus) {
        super(activeStatus);
    }

    public AmmoCard getAmmoTile() {
        return ammoTile;
    }

    public void setAmmoTile(AmmoCard ammoTile) {
        this.ammoTile = ammoTile;
    }

    public AmmoDeck getDeckOfAmmo() {
        return deckOfAmmo;
    }

    public void setDeckOfAmmo(AmmoDeck deckOfAmmo) {
        this.deckOfAmmo = deckOfAmmo;
    }
}
