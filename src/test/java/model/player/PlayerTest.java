package model.player;

import model.ammo.AmmoCard;
import model.powerUps.PowerUps;
import model.weapons.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player p1;
    private Player p2;

    @BeforeEach
    void setUp() {

        p1 = new Player("johnny", 0);
        p2 = new Player("edoz", 1);
    }

    @Test
    void getNickname() {

        assertEquals(p1.getNickname(), "johnny");
        assertEquals(p2.getNickname(), "edoz");
    }


    @Test
    void getId() {
        assertEquals(p1.getId(), 0);
        assertEquals(p2.getId(), 1);
    }


    @Test
    void getPosition() {
        assertNull(p1.getPosition());
        assertNull(p2.getPosition());
    }


    @Test
    void setPosition() {
    }

    @Test
    void getBoard(){
        assertNotNull(p1.getBoard());
        assertNotNull(p2.getBoard());
    }

    @Test
    void getWeapons() {
        assert(p1.getWeapons()!=null);
        assert((p1.getWeapons()).length==3);
        for (int i=0; i<3;i++){
            assert ((p1.getWeapons())[i]==null);
        }
    }

    @Test
    void addWeapons() {
        Weapon w1 = new Weapon();
        Weapon w2 = new Weapon();
        Weapon w3 = new Weapon();
        Weapon w4 = new Weapon();
        p1.addWeapons(w1);
        p1.addWeapons(w2);
        p1.addWeapons(w3);
        for (int i=0; i<3;i++){
            assert ((p1.getWeapons())[i]!=null);
        }
        assert(p1.getWeapons().length==3);
        // Cerco di fregarlo e aggiungo un'altra arma
        p1.addWeapons(w4);
        assert(p1.getWeapons().length==3); // perfetto non ci è cascato

    }

    @Test
    void removeWeapons() {
        Weapon w1 = new Weapon();
        Weapon w2 = new Weapon();
        Weapon w3 = new Weapon();
        p1.addWeapons(w1);
        p1.addWeapons(w2);
        p1.addWeapons(w3);
        for (int i=0; i<3;i++){
            assert ((p1.getWeapons())[i]!=null);
        }
        p1.removeWeapons(0);
        p1.removeWeapons(2);
        p1.removeWeapons(3);
        assertNull(p1.getWeapons()[0]);
        assertNull(p1.getWeapons()[2]);
        assertNotNull(p1.getWeapons()[1]);
        p1.removeWeapons(1);
        assertNull(p1.getWeapons()[1]);

    }

    @Test
    void getAmmo() {
        assertNotNull(p1.getAmmo());
    }

    @Test
    void addAmmo() {
        AmmoCard a1 = new AmmoCard();
        AmmoCard a2 = new AmmoCard();
        AmmoCard a3 = new AmmoCard();
        AmmoCard a4 = new AmmoCard();

        p1.addAmmo(a1);
        p1.addAmmo(a2);

        assert(p1.getAmmo().getBlueAmmo()==2);
        assert(p1.getAmmo().getRedAmmo()==2);
        assert(p1.getAmmo().getYellowAmmo()==2);

        p1.addAmmo(a3);
        // cerco di fregarlo aggiungendone più del possibile
        p1.addAmmo(a4);

        assert(p1.getAmmo().getBlueAmmo()==3);
        assert(p1.getAmmo().getRedAmmo()==3);
        assert(p1.getAmmo().getYellowAmmo()==3);


    }

    @Test
    void removeAmmo() {
        AmmoCard a1 = new AmmoCard();
        AmmoCard a2 = new AmmoCard();
        p1.addAmmo(a1);
        p1.addAmmo(a2);
        assert(p1.getAmmo().getBlueAmmo()==2);
        assert(p1.getAmmo().getRedAmmo()==2);
        //TODO da pensare bene per via del costo che potrebbero avere le armi

    }

    @Test
    void getPowerUps() {
        for (int i=0; i<3;i++){
            assert ((p1.getPowerUps())[i]==null);
        }
    }

    @Test
    void addPowerUpsCard() {
        PowerUps c1 = new PowerUps('Y',"cazzotto rotante");
        PowerUps c2 = new PowerUps('B',"granata sensei");
        PowerUps c3 = new PowerUps('R',"super trotamissile");
        p1.addPowerUpsCard(c1);
        p1.addPowerUpsCard(c2);
        p1.addPowerUpsCard(c3);
        p1.addPowerUpsCard(c1);
        for (int i=0; i<3;i++){
            assert ((p1.getPowerUps())[i]!=null);
        }
        assert (p1.getPowerUps().length==3);

    }

    @Test
    void removePowerUps() {
        PowerUps c1 = new PowerUps('Y',"cazzotto rotante");
        PowerUps c2 = new PowerUps('B',"granata sensei");
        PowerUps c3 = new PowerUps('R',"super trotamissile");
        p1.addPowerUpsCard(c1);
        p1.addPowerUpsCard(c2);
        p1.addPowerUpsCard(c3);
        p1.removePowerUps(1);
        assertNull(p1.getPowerUps()[1]);
        p1.removePowerUps(0);
        p1.removePowerUps(2);
        for (int i=0; i<3;i++){
            assert ((p1.getPowerUps())[i]==null);
        }

    }

    @Test
    void getPoints() {
        assert(p1.getPoints()==0);
    }

    @Test
    void addPoints() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void setStatus() {
    }

    @Test
    void trueDead() {
    }

    @Test
    void falseDead() {
    }
}