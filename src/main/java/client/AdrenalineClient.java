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

        System.out.println("WELCOME TO ADRENALINE CLIENT v3.0.0");
        System.out.println("Developed by: Giovalca, MADSOMMA, RealNGneer");
        System.out.println("All rights reserved, July 2019\n\n");

        AdrenalineClient starter = new AdrenalineClient();
        System.out.println("[LAUNCHER]: Launching a new client . . .");

        starter.launchGUI();
        System.out.println("[LAUNCHER]: Starting GUI . . .");
    }

    private void launchGUI(){
        executor.submit(new client.GUI.FirstPage());
    }

}
