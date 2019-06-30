package client;

//TODOimport client.CLI.CLI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdrenalineClient {

    private ExecutorService executor;

    public AdrenalineClient(){
        executor = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {

        AdrenalineClient starter = new AdrenalineClient();
        starter.launchGUI();
    }

    private void launchGUI(){
        executor.submit(new client.GUI.FirstPage());
    }

}
