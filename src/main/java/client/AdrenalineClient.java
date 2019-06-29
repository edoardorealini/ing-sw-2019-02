package client;

//TODOimport client.CLI.CLI;
import model.Match;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdrenalineClient {

    private ExecutorService executor;
    private Match match;

    public AdrenalineClient(){
        executor = Executors.newCachedThreadPool();
        match = new Match();
    }

    public static void main(String[] args) {

        AdrenalineClient starter = new AdrenalineClient();

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

    private void launchGUI(){
        executor.submit(new client.GUI.FirstPage());
    }

}
