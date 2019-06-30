package client.GUI;

import client.GUI.PopUpSceneMethod;
import client.remoteController.SenderClientRemoteController;
import commons.FileLoader;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;

import java.io.File;


//TODO NB: settare il player prima di chiamare questo popup

public class RespawnPopUp extends Application {

    private Match match;
    private SenderClientRemoteController senderRemoteController;

    @Override
    public void start(Stage stage) throws Exception {

        int a = 0;
        int b = 0;
        int c = 0;

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Respawn");
        HBox hBoxImages = new HBox();
        VBox vBoxPage = new VBox();
        HBox hBoxButtons = new HBox();

        Label text = new Label("Choose one power up to discard, you will spawn on the square with the same color");
        Button pow1 = new Button("DISCARD THIS");
        Button pow2 = new Button("DISCARD THIS");
        Button pow3 = new Button("DISCARD THIS");


        vBoxPage.getChildren().setAll(hBoxImages, text, hBoxButtons);

        vBoxPage.setAlignment(Pos.CENTER);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxImages.setAlignment(Pos.CENTER);
        vBoxPage.setSpacing(8);
        hBoxImages.setSpacing(10);
        hBoxButtons.setSpacing(100);

        pow1.setOnAction(event -> {
            try {
                senderRemoteController.spawn(0);
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });

        pow2.setOnAction(event -> {
            try {
                senderRemoteController.spawn(1);
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });

        pow3.setOnAction(event -> {
            try {
                senderRemoteController.spawn(2);
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });


        if (match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[0]!=null){
            File powerUp0 = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[0].getName() + "_" + match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(powerUp0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow1);
            hBoxImages.getChildren().add(iv0);
            a = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[1]!=null){
            File powerUp1 = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[1].getName() + "_" + match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(powerUp1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow2);
            hBoxImages.getChildren().add(iv1);
            b = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[2]!=null){
            File powerUp2 = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[2].getName() + "_" + match.getPlayer(senderRemoteController.getNickname()).getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(powerUp2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow3);
            hBoxImages.getChildren().add(iv2);
            c = 1;
        }


        Scene scene = new Scene(vBoxPage, (300*(a+b+c)), 400);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            int i = 0;
            for (i = 0; i < 3; i++) {
                if (match.getCurrentPlayer().getPowerUps()[i] != null)
                    break;
            }
            try {
                senderRemoteController.spawn(i);
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                PopUpSceneMethod.display("RESPAWN ERROR", e.getMessage());
            }
        });
        stage.showAndWait();
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setSenderRemoteController(SenderClientRemoteController senderRemoteController) {
        this.senderRemoteController = senderRemoteController;
    }
}
