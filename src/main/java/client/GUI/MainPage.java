package client.GUI;

import client.remoteController.SenderClientRemoteController;
import com.sun.javafx.scene.SceneEventDispatcher;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Match;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;

public class MainPage extends Application {
    private Match match;
    // TODO RICORDARSI DI SETTARE IL SENDERCONTROLLER
    SenderClientRemoteController remoteController;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Adrenaline");
        SplitPane splitPane = new SplitPane();
        //left (life)
        VBox vBoxLife = new VBox();
        splitPane.getItems().add(vBoxLife);
        //centre (map)
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map" + match.getMap().getMapID() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(500);
        iv.setFitWidth(500);
        iv.setPreserveRatio(true);
        splitPane.getItems().add(iv);
        //right
        //mettere prima le pos dei giocatori
        // TODO da fare caso particolare in cui i player non sono ancora spounati
        VBox vBoxRight = new VBox();
        Label pos1 = new Label("");
        Label pos2 = new Label("");
        Label pos3 = new Label("");
        Label pos4 = new Label("");
        Label pos5 = new Label("");
        /*
        pos1.setText("Position of "+match.getPlayers().get(0).getNickname()+" is X,Y :"+match.getMap().getIndex(match.getPlayers().get(0).getPosition()));
        pos2.setText("Position of "+match.getPlayers().get(1).getNickname()+" is X,Y :"+match.getMap().getIndex(match.getPlayers().get(1).getPosition()));
        pos3.setText("Position of "+match.getPlayers().get(2).getNickname()+" is X,Y :"+match.getMap().getIndex(match.getPlayers().get(2).getPosition()));
        
         */
        if (match.getPlayers().size()>=4) {
            pos4.setText("Position of "+match.getPlayers().get(3).getNickname()+" is X,Y :"+match.getMap().getIndex(match.getPlayers().get(3).getPosition()));
            if (match.getPlayers().size()>=5) { pos5.setText("Position of "+match.getPlayers().get(4).getNickname()+" is X,Y :"+match.getMap().getIndex(match.getPlayers().get(4).getPosition()));}
        }
        Label empty1 = new Label("");
        Label empty2 = new Label("");
        Button moveButton = new Button(" MOVE ");
        //TODO moveButton.setOnAction(e -> MoveButton());
        Button grabButton = new Button(" GRAB ");
        //TODO grabButton.setOnAction(e -> GrabButton());
        Button shootButton = new Button(" SHOOT ");
        //TODO shootButton.setOnAction(e -> ShootButton());
        vBoxRight.getChildren().addAll(pos1,pos2,pos3,pos4,pos5,empty1,empty2,moveButton,grabButton,shootButton);
        splitPane.getItems().add(vBoxRight);

        // Top (buttons)
        HBox hboxTop = new HBox();
        //TODO aggiungere anche i punti del giocatore ?
        Button showAmmoInPlace = new Button(" Show Goods In Place ");
        //TODO set on action
        Button showMyWeapons = new Button(" Show My Weapons ");
        showMyWeapons.setOnAction(e -> {
            try {
            WeaponsOwned wp = new WeaponsOwned();
            wp.setMatch(match);
            wp.setPlayerWhoClickButton(match.getPlayer(remoteController.getNickname()));
            wp.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        });
        Button showMyPowerUps = new Button(" Show My PowerUps");
        showMyPowerUps.setOnAction(e -> {
            try {
                PowerUpsOwned pu = new PowerUpsOwned();
                pu.setMatch(match);
                pu.setPlayerWhoClickButton(match.getPlayer(remoteController.getNickname()));
                pu.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            });

        hboxTop.getChildren().addAll(showAmmoInPlace,showMyWeapons,showMyPowerUps);

        SplitPane VsplitPane = new SplitPane();
        VsplitPane.setOrientation(Orientation.VERTICAL);
        VsplitPane.getItems().addAll(hboxTop,splitPane);

        Scene scene = new Scene(VsplitPane,900,900);
        mainStage.setScene(scene);


    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setRemoteController(SenderClientRemoteController remoteController) {
        this.remoteController = remoteController;
    }
}


