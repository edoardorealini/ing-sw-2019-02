package client;

import server.InterfaceRemoteObjectRMI;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientStarter{

    private ExecutorService executor;

    public ClientStarter(){
        executor = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {

        ClientStarter starter = new ClientStarter();

        try {

            switch (args[0]) {
                case "-CLI":
                    starter.launchCLI();
                    break;

                case "-GUI":
                    starter.launchGUI();
                    break;

                default:
                    System.out.println("[ERROR] : Interface type not selected, please restart the application");
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
        //TODO launchGUI
    }

}
