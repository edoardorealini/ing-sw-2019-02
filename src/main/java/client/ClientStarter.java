package client;

import server.InterfaceRemoteObjectRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ClientStarter{

    public static void main(String[] args) {

        ClientStarter starter = new ClientStarter();
        try {

            switch (args[0]) {
                case "-rmi":
                    starter.launchRMIClient();
                    break;

                case "-socket":
                    starter.launchSocketClient();
                    break;

                default:
                    System.err.println("[ERROR] : Not admitted value " + args[0] + " as type of connection.");
                    return;

            }
        }catch(Exception e){
            System.err.println("[ERROR] : Connection method not set");
            e.printStackTrace();
        }

    }

    private void launchRMIClient(){
        System.out.println("[CLIENT] : RMI connection selected correctly");

    }

    private void launchSocketClient(){

    }

}
