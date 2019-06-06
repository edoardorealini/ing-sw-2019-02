package server.RMIHandler;

import commons.InterfaceServerControllerRMI;
import controller.MatchController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ServerControllerRMITest {

    private MatchController matchController;
    private InterfaceServerControllerRMI controller;

    @BeforeEach
    void setUp() {
        matchController = new MatchController();
        try {
            controller = new ServerControllerRMI(matchController);
        }catch (RemoteException e){
            e.printStackTrace();
        }

    }

    //impossibile fare test senza simulare l'esecuzione completa! (NOT GOOD)
    @Test
    void lobbyTests(){
        try {
            controller.addPlayer("Edoardo");
            int edo = controller.hashNickname("Edoardo");
            System.out.println(edo);

            controller.addPlayer("Johnny");
            int johnny = controller.hashNickname("Johnny");
            System.out.println(johnny);

            controller.addPlayer("Ricky");
            int ricks = controller.hashNickname("Ricky");
            System.out.println(ricks);

            controller.addPlayer("Sims");
            int sims = controller.hashNickname("Sims");
            System.out.println(sims);

            controller.addPlayer("GGWP");
            int gg = controller.hashNickname("GGWP");
            System.out.println(gg);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}