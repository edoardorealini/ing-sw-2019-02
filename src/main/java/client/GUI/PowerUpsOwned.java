package client.GUI;


import client.remoteController.SenderClientRemoteController;
import commons.FileLoader;
import exception.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;
import model.player.Player;
import model.powerup.PowerUp;
import model.powerup.PowerUpName;
import model.powerup.Teleporter;


import java.io.File;
import java.rmi.RemoteException;
// NB. come invocare questa classe:
// 1) crea la classe
// 2) setMatch
// 3) setPlayerWhoClickButton
// 4) start della classe

public class PowerUpsOwned extends Application {

    private Match match;
    private Player playerWhoClickButton;
    private PowerUp powerUp;
    private int indexOfPowerUp;
    private SenderClientRemoteController senderClientController;


    @Override
    public void start(Stage primaryStage) throws Exception {
        short a = 0;
        short b = 0;
        short c = 0;
        primaryStage.setTitle("My PowerUps");
        VBox vBox = new VBox();
        HBox hBoxImages = new HBox();
        HBox hBoxButtons = new HBox();
        Label empty1 = new Label(" ");
        Label empty2 = new Label(" ");
        Label empty3 = new Label(" ");
        Button usePow1 = new Button();
        Button usePow2 = new Button();
        Button usePow3 = new Button();

        if (playerWhoClickButton.getPowerUps()[0] != null){
            File powerUp0 = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + playerWhoClickButton.getPowerUps()[0].getName() + "_" + playerWhoClickButton.getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(powerUp0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            hBoxImages.getChildren().add(iv0);
            usePow1.setText("USE " + playerWhoClickButton.getPowerUps()[0].getName());
            if (playerWhoClickButton.getPowerUps()[0].getName() == PowerUpName.NEWTON || playerWhoClickButton.getPowerUps()[0].getName() == PowerUpName.TELEPORTER)
                hBoxButtons.getChildren().add(usePow1);
            else hBoxButtons.getChildren().add(empty1);
            a = 1;
        }

        if (playerWhoClickButton.getPowerUps()[1] != null){
            File powerUp1 = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + playerWhoClickButton.getPowerUps()[1].getName() + "_" + playerWhoClickButton.getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(powerUp1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            hBoxImages.getChildren().add(iv1);
            usePow2.setText("USE " + playerWhoClickButton.getPowerUps()[1].getName());
            if (playerWhoClickButton.getPowerUps()[1].getName() == PowerUpName.NEWTON || playerWhoClickButton.getPowerUps()[1].getName() == PowerUpName.TELEPORTER)
                hBoxButtons.getChildren().add(usePow2);
            else hBoxButtons.getChildren().add(empty2);
            b = 1;
        }

        if (playerWhoClickButton.getPowerUps()[2] != null){
            File powerUp2 = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + playerWhoClickButton.getPowerUps()[2].getName() + "_" + playerWhoClickButton.getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(powerUp2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            hBoxImages.getChildren().add(iv2);
            usePow3.setText("USE " + playerWhoClickButton.getPowerUps()[2].getName());
            if (playerWhoClickButton.getPowerUps()[2].getName() == PowerUpName.NEWTON || playerWhoClickButton.getPowerUps()[2].getName() == PowerUpName.TELEPORTER)
                hBoxButtons.getChildren().add(usePow3);
            else hBoxButtons.getChildren().add(empty3);
            c = 1;
        }

        usePow1.setOnAction(event -> {
            this.powerUp = playerWhoClickButton.getPowerUps()[0];
            this.indexOfPowerUp = 0;
            usePowerUp(new Stage());
            primaryStage.close();
        });

        usePow2.setOnAction(event -> {
            this.powerUp = playerWhoClickButton.getPowerUps()[1];
            this.indexOfPowerUp = 1;
            usePowerUp(new Stage());
            primaryStage.close();
        });

        usePow3.setOnAction(event -> {
            this.powerUp = playerWhoClickButton.getPowerUps()[2];
            this.indexOfPowerUp = 2;
            usePowerUp(new Stage());
            primaryStage.close();
        });


        hBoxImages.setMinHeight(350);
        hBoxImages.setMinWidth(300);
        hBoxImages.setAlignment(Pos.CENTER);
        hBoxImages.setSpacing(8);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(100);
        vBox.getChildren().addAll(hBoxImages, hBoxButtons);
        Scene scene = new Scene(vBox,(306*(a+b+c)),400);
        primaryStage.setMinWidth(300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setPlayerWhoClickButton(Player playerWhoClickButton) {
        this.playerWhoClickButton = playerWhoClickButton;
    }

    public void setSenderClientController(SenderClientRemoteController senderClientRemoteController) {
        this.senderClientController = senderClientRemoteController;
    }

    public void usePowerUp(Stage usePowerUp) {

        usePowerUp.setTitle("PowerUp");
        usePowerUp.initModality(Modality.APPLICATION_MODAL);
        SplitPane splitpane = new SplitPane();
        VBox vBox = new VBox();
        HBox hBoxSquare = new HBox();
        Label chooseSquare = new Label("Choose the square: ");
        Label chooseTarget = new Label("Choose the target: ");
        Label x = new Label("X: ");
        Label y = new Label("Y: ");
        ChoiceBox<String> targetName = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare1 = new ChoiceBox<>();
        Button usePower = new Button("USE " + powerUp.getName());

        File powerUpToUse = FileLoader.getResourceAsFile("powerUpCards" + File.separatorChar + powerUp.getName() + "_" + powerUp.getColor() + ".png");
        Image image = new Image(powerUpToUse.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setFitHeight(300);
        iv.setFitWidth(250);
        iv.setPreserveRatio(true);
        splitpane.getItems().addAll(iv, vBox);

        vBox.getChildren().addAll(chooseSquare, hBoxSquare);

        if (powerUp.getName() == PowerUpName.NEWTON)
            vBox.getChildren().addAll(chooseTarget, targetName);

        vBox.getChildren().add(usePower);

        setX(xSquare1);
        setY(ySquare1);
        fillChoiceBoxName(targetName);

        usePower.setOnAction(actionEvent -> {
            try {
                 if (powerUp.getName() == PowerUpName.NEWTON) {
                     senderClientController.useNewton(indexOfPowerUp, targetName.getValue(), xSquare1.getValue(), ySquare1.getValue());
                 } else if (powerUp.getName() == PowerUpName.TELEPORTER) {
                     senderClientController.useTeleporter(indexOfPowerUp, xSquare1.getValue(), ySquare1.getValue());
                 }
            } catch (RemoteException | WrongStatusException | NotInYourPossessException | NotAllowedMoveException | NotAllowedCallException | WrongPowerUpException | WrongValueException | InvalidInputException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR IN USING POWER UP", e.getMessage());
            }
            usePowerUp.close();
        });

        hBoxSquare.getChildren().addAll(x, xSquare1, y, ySquare1);
        hBoxSquare.setSpacing(8);
        hBoxSquare.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        Scene scene = new Scene(splitpane, 450, 400);
        usePowerUp.setScene(scene);
        usePowerUp.show();

    }

    private void setX(ChoiceBox<Integer> xChoiceBox) {
        xChoiceBox.getItems().addAll(0, 1, 2, 3);
        xChoiceBox.setValue(match.getMap().getIndex(match.getPlayer(senderClientController.getNickname()).getPosition()).get(0));  //x of the current square
    }

    private void setY(ChoiceBox<Integer> yChoiceBox) {
        yChoiceBox.getItems().addAll(0, 1, 2);
        yChoiceBox.setValue(match.getMap().getIndex(match.getPlayer(senderClientController.getNickname()).getPosition()).get(1));   //y of the current square
    }

    private void fillChoiceBoxName(ChoiceBox<String> choiceBoxName) {
        for (Player p: match.getPlayers()) {
            if (! p.getNickname().equals(match.getPlayer(senderClientController.getNickname()).getNickname())) {
                choiceBoxName.getItems().add(p.getNickname());
                choiceBoxName.setValue(p.getNickname());
            }
        }
    }
}
