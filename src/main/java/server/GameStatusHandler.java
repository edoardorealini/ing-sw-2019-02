package server;

import controller.MatchController;
import java.util.Timer;

public class GameStatusHandler implements Runnable{

    //IDEA: thread to run always in order to manage the status of the game.
    // this thread notifies the players when their turn start (TDB how)

    private MatchController controller;

    public GameStatusHandler(MatchController controller){
        this.controller = controller;
    }

    @Override
    public void run(){
        //qui inserire la logica di attesa
        //finchè i player connessi sono < 3 aspetta
        //quando diventano 3 parte timer
        //quando diventano 5 starta la partita (capire ancora cosa vuol dire startare la partita)


        //TODO, implementazione brutta, cambiare con observer o cose simili!! (evitare wait eccessive nei while che osservano)!

        while(controller.getMatchStatus()) {
            waitFirstThreePlayers();
            waitMoreplayers();
        }
    }

    public void waitFirstThreePlayers(){
        try {
            //qui devo attendere che ci siano 3 giocatori connessi !
            while (controller.connectedPlayers() < 3) {
                wait(3000);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void waitMoreplayers(){

        //qui devo far partire countdown

        try {
            while (controller.connectedPlayers() < 5) {
                wait(3000);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}
