/*package client.GUI;

import client.remoteController.RemoteController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LobbyPage extends Application implements Runnable{

    RemoteController remoteController;


    public LobbyPage(RemoteController remoteController){
        this.remoteController = remoteController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Lobby");

        VBox vbox = new VBox(20);

        Label description = new Label();
        description.setText("Player connected to the match:");

        vbox.getChildren().add(description);

        Scene sceneLobby = new Scene(vbox,250,250);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void run() {
        launch();
    }
}
*/