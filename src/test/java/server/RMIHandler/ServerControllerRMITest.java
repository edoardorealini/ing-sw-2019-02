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
        //fare a manella
    }
}