package client;

import client.GUI.PopUpSceneMethod;
import client.remoteController.SenderClientControllerRMI;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
import model.player.Player;

import java.io.File;


//TODO NB: settare il player prima di chiamare questo popup

public class RespawnPopUp extends Application {

    // private Match match;
    private Player respawnPlayer;
    private SenderClientControllerRMI senderRemoteController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        int a = 0;
        int b = 0;
        int c = 0;

        primaryStage.setTitle("Respawn");
        SplitPane splitPane = new SplitPane();
        HBox hBoxImages = new HBox();
        VBox lowPage = new VBox();
        HBox hBoxButtons = new HBox();
        splitPane.setOrientation(Orientation.VERTICAL);

        Label text = new Label("Choose one power up to discard, you will spawn on the square with the same color");
        Button pow1 = new Button("DISCARD THIS");
        Button pow2 = new Button("DISCARD THIS");
        Button pow3 = new Button("DISCARD THIS");

        splitPane.getItems().addAll(hBoxImages, lowPage);

        lowPage.getChildren().setAll(text, hBoxButtons);

        lowPage.setSpacing(8);
        hBoxImages.setSpacing(5);
        hBoxButtons.setSpacing(100);

        pow1.setOnAction(event -> {
            try {
                senderRemoteController.spawn(1);
            } catch (Exception e) {
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });

        pow2.setOnAction(event -> {
            try {
                senderRemoteController.spawn(2);
            } catch (Exception e) {
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });

        pow3.setOnAction(event -> {
            try {
                senderRemoteController.spawn(3);
            } catch (Exception e) {
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });


        if (respawnPlayer.getPowerUps()[0]!=null){
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + respawnPlayer.getPowerUps()[0].getName() + "_" + respawnPlayer.getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow1);
            splitPane.getItems().add(iv0);
            a = 1;
        }

        if (respawnPlayer.getPowerUps()[1]!=null){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + respawnPlayer.getPowerUps()[1].getName() + "_" + respawnPlayer.getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow2);
            splitPane.getItems().add(iv1);
            b = 1;
        }

        if (respawnPlayer.getPowerUps()[2]!=null){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + respawnPlayer.getPowerUps()[2].getName() + "_" + respawnPlayer.getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow3);
            splitPane.getItems().add(iv2);
            c = 1;
        }

        splitPane.setMinHeight(300);
        splitPane.setMinWidth(250);

        Scene scene = new Scene(splitPane, (300*(a+b+c)), 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

  //  public void setMatch(Match match) {
  //      this.match = match;
  //  }

    public void setRespawnPlayer(Player respawnPlayer) {
        this.respawnPlayer = respawnPlayer;
    }

    public void setSenderRemoteController(SenderClientControllerRMI senderRemoteController) {
        this.senderRemoteController = senderRemoteController;
    }
}
