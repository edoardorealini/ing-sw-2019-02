package client;

import client.CLI.CLI;
import client.GUI.FirstPage;
import client.clientController.ClientController;
import client.clientController.ClientControllerRMI;
import model.Match;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientStarter{

    private ExecutorService executor;
    private Match match;

    public ClientStarter(){
        executor = Executors.newCachedThreadPool();
        match = new Match();
    }

    public static void main(String[] args) {

        ClientStarter starter = new ClientStarter();

        try {

            if (args == null){
                System.out.println("[ERROR] : Interface type not selected, please restart the application");
                return;
            }

            switch (args[0]) {
                case "-CLI":
                    starter.launchCLI();
                    break;

                case "-GUI":
                    starter.launchGUI();
                    break;

                default:
                    System.out.println("[ERROR] : Wrong Interface type, please restart the application");
                    return;

            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    private void launchCLI(){
        executor.submit(new CLI());
        System.out.println("[INFO] : ADRENALINE CLI INTERFACE LAUNCHED");

    }

    private void launchGUI(){
        executor.submit(new client.GUI.FirstPage(match));
    }


    //in teoria questo metodo va chiamato dopo che l'utente ha deciso che tipo di connessione utilizzare, quindi si lancia il controller corretto.
    //TODO spostare lato RemoteControllerRMI
    /*
    private void launchClientController(){
        try {
            executor.submit(new ClientControllerRMI());
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

     */

}
