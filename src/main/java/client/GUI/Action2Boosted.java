package client.GUI;

import client.remoteController.SenderClientRemoteController;
import exception.NotAllowedCallException;
import exception.NotAllowedMoveException;
import exception.WrongStatusException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;

import java.rmi.RemoteException;

public class Action2Boosted extends Application {
    private Match match;
    private SenderClientRemoteController senderRemoteController;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Frenzy Action");
        VBox vbox = new VBox(5);

        Label title1 = new Label(" Move Section ");
        HBox hBox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        hBox1.getChildren().addAll(label1,posX);
        hBox1.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        hBox2.getChildren().addAll(label2,posY);
        hBox2.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title1,hBox1,hBox2);

        Button move = new Button(" Move ");
        move.setOnAction(e -> {
            try {
                senderRemoteController.makeAction2Frenzy(posX.getValue(),posY.getValue());
                primaryStage.close();
            } catch (RemoteException ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("SHOOTING ERROR", ex.getMessage());
                primaryStage.close();
            } catch (NotAllowedMoveException ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("SHOOTING ERROR", ex.getMessage());
                primaryStage.close();
            } catch (NotAllowedCallException ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("SHOOTING ERROR", ex.getMessage());
                primaryStage.close();
            } catch (WrongStatusException ex) {
                ex.printStackTrace();
                primaryStage.close();
            }
        });
        vbox.getChildren().add(move);

        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,200,200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setSenderRemoteController(SenderClientRemoteController senderRemoteController) {
        this.senderRemoteController = senderRemoteController;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
