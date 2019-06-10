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
    SenderClientRemoteController remoteController;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Adrenaline");
        SplitPane splitPane = new SplitPane();
        //left (life)
        VBox vBoxLife = new VBox();
        vBoxLife.setMinWidth(Region.USE_PREF_SIZE);
        vBoxLife.setPrefWidth(400);
        //prima board
        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaGiallo.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);
        vBoxLife.getChildren().add(iv0);
        // seconda board
        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaVerde.png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(200);
        iv1.setFitWidth(400);
        iv1.setPreserveRatio(true);
        vBoxLife.getChildren().add(iv1);
        // terza board
        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaGrigio.png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(200);
        iv2.setFitWidth(400);
        iv2.setPreserveRatio(true);
        vBoxLife.getChildren().add(iv2);
        // quarta board
        if (match.getPlayers().size()>=4){
            File file3 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaRosso.png");
            Image image3 = new Image(file3.toURI().toString());
            ImageView iv3 = new ImageView(image3);
            iv3.setFitHeight(200);
            iv3.setFitWidth(400);
            iv3.setPreserveRatio(true);
            vBoxLife.getChildren().add(iv3);
        }
        // quinta board
        if (match.getPlayers().size()>=5){
            File file4 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaBlu.png");
            Image image4 = new Image(file4.toURI().toString());
            ImageView iv4 = new ImageView(image4);
            iv4.setFitHeight(200);
            iv4.setFitWidth(400);
            iv4.setPreserveRatio(true);
            vBoxLife.getChildren().add(iv4);
        }
        // inserisco le posizioni sotto
        Label pos1 = new Label("");
        Label pos2 = new Label("");
        Label pos3 = new Label("");
        Label pos4 = new Label("");
        Label pos5 = new Label("");
/*
        if (match.getPlayers().get(0).getStatus().isInStatusSpawn()==false) {
            pos1.setText("Position of " + match.getPlayers().get(0).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(0).getPosition()));
        }
        else pos1.setText("Not already spowned");
        if (match.getPlayers().get(1).getStatus().isInStatusSpawn()==false) {
            pos2.setText("Position of " + match.getPlayers().get(1).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(1).getPosition()));
        }
        else pos2.setText("Not already spowned");
        if (match.getPlayers().get(2).getStatus().isInStatusSpawn()==false) {
            pos3.setText("Position of " + match.getPlayers().get(2).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(2).getPosition()));
        }
        else pos3.setText("Not already spowned");
        if (match.getPlayers().size()>=4) {
            if (match.getPlayers().get(3).getStatus().isInStatusSpawn()==false) {
                pos4.setText("Position of " + match.getPlayers().get(3).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(3).getPosition()));
            }
            else pos4.setText("Not already spowned");
        }
        if (match.getPlayers().size()>=5) {
            if (match.getPlayers().get(4).getStatus().isInStatusSpawn() == false) {
                pos5.setText("Position of " + match.getPlayers().get(4).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(4).getPosition()));
            }
            else pos5.setText("Not already spowned");
        }
*/
        vBoxLife.getChildren().addAll(pos1,pos2,pos3,pos4,pos5);
        splitPane.getItems().add(vBoxLife);
        //right (map)
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map" + match.getMap().getMapID() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(825);
        iv.setFitWidth(700);
        iv.setPreserveRatio(true);
        splitPane.getItems().add(iv);

        // Top (buttons)
        HBox hboxTop = new HBox();
        hboxTop.setMaxHeight(15);
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

        Label empty1 = new Label("        ");
        Label empty2 = new Label("        ");
        Button moveButton = new Button(" MOVE ");
        //TODO moveButton.setOnAction(e -> MoveButton());
        Button grabButton = new Button(" GRAB ");
        //TODO grabButton.setOnAction(e -> GrabButton());
        Button shootButton = new Button(" SHOOT ");
        //TODO shootButton.setOnAction(e -> ShootButton());

        hboxTop.getChildren().addAll(showAmmoInPlace,showMyWeapons,showMyPowerUps,empty1,empty2,moveButton,grabButton,shootButton);

        SplitPane VsplitPane = new SplitPane();
        VsplitPane.setOrientation(Orientation.VERTICAL);
        VsplitPane.getItems().addAll(hboxTop,splitPane);

        Scene scene = new Scene(VsplitPane,1100,650);
        mainStage.setScene(scene);
        mainStage.show();


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


