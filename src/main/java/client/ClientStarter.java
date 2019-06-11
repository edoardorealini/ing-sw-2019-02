package client;

//TODOimport client.CLI.CLI;
import model.Match;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientStarter{

    private ExecutorService executor;
    private Match match;            //TODO capiamo bene dove metterlo e se passarlo alle varie classi

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
                    //TODO starter.launchCLI();
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


  /* TODO private void launchCLI(){
        executor.submit(new CLI());
        System.out.println("[INFO] : ADRENALINE CLI INTERFACE LAUNCHED");

    }
*/
    private void launchGUI(){
        executor.submit(new client.GUI.FirstPage());
    }


    //in teoria questo metodo va chiamato dopo che l'utente ha deciso che tipo di connessione utilizzare, quindi si lancia il controller corretto.
    //TODO spostare lato SenderClientControllerRMI
    /*
    private void launchClientController(){
        try {
            executor.submit(new ReceiverClientControllerRMI());
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

     */

}
