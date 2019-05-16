package server;

import controller.MatchController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdrenalineSocketServer implements Runnable{
    private MatchController matchController;
    private int port;
    public AdrenalineSocketServer(MatchController controller, int port) {
        matchController = controller;
        this.port = port;
    }
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // porta non disponibile
            return;
        }

        System.out.println("SocketServer ready");

        //TODO: questo è un mock preso dalle slide per dare un po di forma, gestire bene come serve

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new SocketClientHandler(socket));
            } catch(IOException e) {
                break; // entrerei qui se serverSocket venisse chiuso
            }
        }
        executor.shutdown();
    }
    /*
    public static void main(String[] args) {
        AdrenalineSocketServer echoServer = new AdrenalineSocketServer(1337);
        echoServer.startServer();
    }

     */
}