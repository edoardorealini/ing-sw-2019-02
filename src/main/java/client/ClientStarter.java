package client;

import controller.MatchControllerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientStarter {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1338);
            MatchControllerInterface matchController = (MatchControllerInterface) registry.lookup("matchController");

            String response = matchController.RMICallTest("Hello");

            System.out.println("Response from server: " + response);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
