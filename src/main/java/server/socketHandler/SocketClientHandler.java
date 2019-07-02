package server.socketHandler;

import java.net.Socket;

// !!  DRAFT  !!


public class SocketClientHandler implements Runnable{

    private Socket socket;

    public SocketClientHandler(Socket s){
        this.socket = s;
    }

    public void run(){
        System.out.println("TEST");
    }
}
