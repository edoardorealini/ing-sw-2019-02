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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
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

import static model.map.SquareType.*;

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

        if (!match.getPlayers().get(0).isInStatusSpawn() && !match.getPlayers().get(0).isInStatusWaitFirstTurn()) {
            pos1.setText("Position of " + match.getPlayers().get(0).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(0).getPosition()));
        }
        else pos1.setText("Not already spawned");
        if (!match.getPlayers().get(1).isInStatusSpawn() && !match.getPlayers().get(1).isInStatusWaitFirstTurn()) {
            pos2.setText("Position of " + match.getPlayers().get(1).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(1).getPosition()));
        }
        else pos2.setText("Not already spawned");
        if (!match.getPlayers().get(2).isInStatusSpawn() && !match.getPlayers().get(2).isInStatusWaitFirstTurn()) {
            pos3.setText("Position of " + match.getPlayers().get(2).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(2).getPosition()));
        }
        else pos3.setText("Not already spawned");
        if (match.getPlayers().size()>=4) {
            if (!match.getPlayers().get(3).isInStatusSpawn() && !match.getPlayers().get(3).isInStatusWaitFirstTurn()) {
                pos4.setText("Position of " + match.getPlayers().get(3).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(3).getPosition()));
            }
            else pos4.setText("Not already spawned");
        }
        if (match.getPlayers().size()>=5) {
            if (!match.getPlayers().get(4).isInStatusSpawn() && !match.getPlayers().get(4).isInStatusWaitFirstTurn()) {
                pos5.setText("Position of " + match.getPlayers().get(4).getNickname() + " is X,Y :" + match.getMap().getIndex(match.getPlayers().get(4).getPosition()));
            }
            else pos5.setText("Not already spawned");
        }

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
        Label points = new Label("My Points : "+match.getPlayer(remoteController.getNickname()).getPoints()+" ");
        Button showGoodsInPlace = new Button(" Show Goods In Place ");
        showGoodsInPlace.setOnAction(e -> showGoods() );
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
        Button skipTurn = new Button(" Skip Turn ");
        //TODO metodo che cambia il turno
        Label empty1 = new Label("                 ");
        Label empty2 = new Label("                 ");
        Button moveButton = new Button(" MOVE ");
        moveButton.setOnAction(e -> moveButton());
        Button grabButton = new Button(" GRAB ");
        //TODO grabButton.setOnAction(e -> GrabButton());
        Button shootButton = new Button(" SHOOT ");
        //TODO shootButton.setOnAction(e -> ShootButton());
        Button chargeWeapons = new Button(" Charge Weapons");
        //TODO charge wepons

        hboxTop.getChildren().addAll(points,showGoodsInPlace,showMyWeapons,showMyPowerUps,empty1,skipTurn,empty2,moveButton,grabButton,shootButton,chargeWeapons);

        SplitPane VsplitPane = new SplitPane();
        VsplitPane.setOrientation(Orientation.VERTICAL);
        VsplitPane.getItems().addAll(hboxTop,splitPane);
        //TODO VsplitPane.setBackground(Color.rgb(40,44,52));

        Scene scene = new Scene(VsplitPane,1110,650);
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

    public void moveButton(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle("Move");
        stage.setMinWidth(250);
        stage.setMinHeight(90);
        VBox vBoxMove = new VBox(10);

        HBox Hbox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        TextField posX = new TextField();
        Hbox1.getChildren().addAll(label1,posX);

        HBox Hbox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        TextField posY = new TextField();
        Hbox2.getChildren().addAll(label2,posY);

        Button move = new Button(" Move ");
        move.setOnAction(e -> {
            //TODO chiamare il metodo move
            //TODO remoteController.move();
        } );


        vBoxMove.getChildren().addAll(Hbox1,Hbox2,move);
        vBoxMove.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxMove);

        stage.setScene(scene);
        stage.showAndWait(); // non torna al chiamante fino a quando non si è chiusa la finestra
    }

    public void showGoods(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle("Move");
        stage.setMinWidth(250);
        stage.setMinHeight(90);
        VBox vBoxMove = new VBox(10);

        HBox Hbox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        TextField posX = new TextField();
        Hbox1.getChildren().addAll(label1,posX);

        HBox Hbox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        TextField posY = new TextField();
        Hbox2.getChildren().addAll(label2,posY);

        Button show = new Button(" Show Goods ");
        show.setOnAction(e -> {
            int x = Integer.parseInt(posX.getText());
            int y = Integer.parseInt(posY.getText());
            if (match.getMap().getSquareFromIndex(x,y)!=null){
                if (match.getMap().getSquareFromIndex(x,y).getType()==SPAWN){
                    showWeaponsGoods(x,y);
                }
                else showAmmoGoods(x,y);
                stage.close();
            }
        } );

        vBoxMove.getChildren().addAll(Hbox1,Hbox2,show);
        vBoxMove.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxMove);

        stage.setScene(scene);
        stage.showAndWait(); // non torna al chiamante fino a quando non si è chiusa la finestra
    }

    public void grab(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle("Move");
        stage.setMinWidth(250);
        stage.setMinHeight(90);
        VBox vBoxMove = new VBox(10);

        HBox Hbox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        TextField posX = new TextField();
        Hbox1.getChildren().addAll(label1,posX);

        HBox Hbox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        TextField posY = new TextField();
        Hbox2.getChildren().addAll(label2,posY);

        Label titlePosition = new Label(" Choose where you want to grab : ");
        Label titleWhichWeapon = new Label(" Choose which weapon (1,2,3 or empty) : ");
        TextField weaponNumber = new TextField();

        Button grab = new Button(" Grab ");
        grab.setOnAction(e -> {
            int x = Integer.parseInt(posX.getText());
            int y = Integer.parseInt(posY.getText());
            if (match.getMap().getSquareFromIndex(x,y).getType() == SPAWN) {
                int numberOfAmmo = Integer.parseInt(weaponNumber.getText());
                //TODO grab Weapon
            }
            else ;//TODO grab ammo card
        } );


        vBoxMove.getChildren().addAll(titlePosition,Hbox1,Hbox2,titleWhichWeapon,weaponNumber,grab);
        vBoxMove.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxMove);

        stage.setScene(scene);
        stage.showAndWait(); // non torna al chiamante fino a quando non si è chiusa la finestra
    }

    public void showWeaponsGoods(int x,int y){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Goods In Place");
        SplitPane splitPane = new SplitPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(0).getName() + ".png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(350);
        iv0.setFitWidth(300);
        iv0.setPreserveRatio(true);
        splitPane.getItems().add(iv0);

        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(1).getName() + ".png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(350);
        iv1.setFitWidth(300);
        iv1.setPreserveRatio(true);
        splitPane.getItems().add(iv1);

        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(2).getName() + ".png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(350);
        iv2.setFitWidth(300);
        iv2.setPreserveRatio(true);
        splitPane.getItems().add(iv2);


        splitPane.setMinHeight(350);
        splitPane.setMinWidth(300);
        Scene scene= new Scene(splitPane,(900),(900));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showAmmoGoods(int x,int y){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Goods In Place");
        HBox hBox = new HBox(5);

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(0) + ".png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(10);
        iv0.setFitWidth(10);
        iv0.setPreserveRatio(true);
        hBox.getChildren().add(iv0);

        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(1) + ".png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(10);
        iv1.setFitWidth(10);
        iv1.setPreserveRatio(true);
        hBox.getChildren().add(iv1);

        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(2) + ".png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(10);
        iv2.setFitWidth(10);
        iv2.setPreserveRatio(true);
        hBox.getChildren().add(iv1);


        //TODO hBox.setBackground(Color.rgb(25,26,23));
        Scene scene= new Scene(hBox,(200),(100));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}


