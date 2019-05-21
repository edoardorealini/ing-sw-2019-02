package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class CLI implements Runnable{

    RemoteController controller;

    @Override
    public void run(){

        selectConnectionTechnolgy();
        askName();

        // qui aggiungere la logica che aspetta i giocatori e attende conferma dal server.
        //idea: esporre un metodo su RMI che attende e fa return solo quando ho 3 giocatori / 5
        //questo metodo viene chiamato da ogni client in attesa sullo stesso server!!

        startApplication();

    }

    public void selectConnectionTechnolgy(){
        System.out.println("\nInsert the communication method you want to use:");
        System.out.println("\t 1. RMI");
        System.out.println("\t 2. Socket");
        System.out.println("Value:");

        String input = new Scanner(System.in).nextLine();

        switch (input){
            case "1":
                launchRMIConnection();
                break;
            case "rmi":
                launchRMIConnection();
                break;
            case "RMI":
                launchRMIConnection();
                break;

            case "2":
                launchSocketConnection();
                break;
            case "socket":
                launchSocketConnection();
                break;
            case "Socket":
                launchSocketConnection();
                break;

        }
    }

    public void startApplication(){

    }

    public void askName(){
        System.out.println("\nInsert the nickname you want to use:");
        String name = new Scanner(System.in).nextLine();

        System.out.println("Hello " + name + "!");
        //TODO gestire il fatto che il nickname deve essere loggato. (password e user)
        controller.addPlayer(name);
        System.out.println("You have been added to the queue. We are waiting for other players");


    }

    private void launchRMIConnection(){
        boolean passed = false;

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("\nRMI CONNECTION SELCTED: Please insert the following requested parameters");

        while(!passed) {

            try {
                System.out.println("\nInsert the IP of the server you want to be connected to:");
                String IP = new Scanner(System.in).nextLine();
                System.out.println("Insert the PORT to which you want to be connected to:");
                int port = new Scanner(System.in).nextInt();

                //here happens the connection try
                controller = new RemoteControllerRMI(IP, port);

                passed = true;

                System.out.println(controller.checkConnection(InetAddress.getLocalHost().getHostAddress()));

            }
            catch (UnknownHostException e){
                System.out.println(e.getMessage());
            }
            catch(RemoteException e) {
                System.out.println(e.getMessage());
            }

        }

        System.out.println("[INFO]: RMI connection selected correctly");

    }

    private void launchSocketConnection(){

    }
}
