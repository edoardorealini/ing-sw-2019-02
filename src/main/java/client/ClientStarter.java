package client;

import controller.MatchControllerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientStarter{

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(1338);
            MatchControllerInterface matchController = (MatchControllerInterface) registry.lookup("matchController");

            Scanner input = new Scanner(System.in);

            while(true) {
                System.out.println("\nInsert message to send to the server:");
                String message = input.nextLine();
                String response = matchController.RMICallTest(message);
                System.out.println("Response from server: " + response);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
