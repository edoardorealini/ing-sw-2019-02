package client.GUI;

import client.remoteController.SenderClientRemoteController;
import com.sun.javafx.scene.SceneEventDispatcher;
import exception.InvalidInputException;
import exception.NotAllowedCallException;
import exception.NotAllowedMoveException;
import exception.WrongStatusException;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
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
import java.rmi.RemoteException;
import java.util.*;
import static model.map.SquareType.*;

public class MainPage extends Application {
    private Match match;
    SenderClientRemoteController remoteController;

    Label labelpos1;
    Label labelpos2;
    Label labelpos3;
    Label labelpos4;
    Label labelpos5;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Adrenaline");
        SplitPane splitPane = new SplitPane();
        //left (life)
        VBox vBoxLife = new VBox();
        vBoxLife.setMinWidth(Region.USE_PREF_SIZE);
        vBoxLife.setSpacing(3);
        vBoxLife.setMaxWidth(250);
        vBoxLife.setAlignment(Pos.CENTER);
        /*
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
        */
        // inserisco le posizioni sotto
        labelpos1 = new Label("");
        labelpos2 = new Label("");
        labelpos3 = new Label("");
        labelpos4 = new Label("");
        labelpos5 = new Label("");
        refreshPlayersPosition();
        vBoxLife.getChildren().addAll(labelpos1,labelpos2,labelpos3,labelpos4,labelpos5);

        Button buttonLife1 = new Button();
        buttonLife1.setText(" Show "+match.getPlayers().get(0).getNickname()+"'s life ");
        buttonLife1.setOnAction(e -> showLifePlayer1());
        vBoxLife.getChildren().add(buttonLife1);
        Button buttonLife2 = new Button();
        buttonLife2.setText(" Show "+match.getPlayers().get(1).getNickname()+"'s life ");
        buttonLife2.setOnAction(e -> showLifePlayer2());
        vBoxLife.getChildren().add(buttonLife2);
        Button buttonLife3 = new Button();
        buttonLife3.setText(" Show "+match.getPlayers().get(2).getNickname()+"'s life ");
        buttonLife3.setOnAction(e -> showLifePlayer3());
        vBoxLife.getChildren().add(buttonLife3);
        if (match.getPlayers().size()>=4){
            Button buttonLife4 = new Button();
            buttonLife4.setText(" Show "+match.getPlayers().get(3).getNickname()+"'s life ");
            buttonLife4.setOnAction(e -> showLifePlayer4());
            vBoxLife.getChildren().add(buttonLife4);
        }
        if (match.getPlayers().size()>=5){
            Button buttonLife5 = new Button();
            buttonLife5.setText(" Show "+match.getPlayers().get(4).getNickname()+"'s life ");
            buttonLife5.setOnAction(e -> showLifePlayer5());
            vBoxLife.getChildren().add(buttonLife5);
        }

        splitPane.getItems().add(vBoxLife);

        //right (map)
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map" + match.getMap().getMapID() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(875);
        iv.setFitWidth(750);
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
        grabButton.setOnAction(e -> grab());
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

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Move");
        stage.setMinWidth(250);
        stage.setMinHeight(90);
        VBox vBoxMove = new VBox(10);

        HBox Hbox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        Hbox1.getChildren().addAll(label1,posX);

        HBox Hbox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        Hbox2.getChildren().addAll(label2,posY);

        Button move = new Button(" Move ");
        move.setOnAction(e -> {
            try {
                remoteController.move(posX.getValue(),posY.getValue());
            } catch (NotAllowedMoveException ex) {
                ex.printStackTrace();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (InvalidInputException ex) {
                ex.printStackTrace();
            } catch (WrongStatusException ex) {
                ex.printStackTrace();
            } catch (NotAllowedCallException ex) {
                ex.printStackTrace();
            }
            stage.close();
        } );


        vBoxMove.getChildren().addAll(Hbox1,Hbox2,move);
        vBoxMove.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxMove);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showGoods(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle("Show Goods");
        stage.setMinWidth(250);
        stage.setMinHeight(90);
        VBox vBoxMove = new VBox(10);

        HBox Hbox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        Hbox1.getChildren().addAll(label1,posX);

        HBox Hbox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        Hbox2.getChildren().addAll(label2,posY);

        Button show = new Button(" Show Goods ");
        show.setOnAction(e -> {
            if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue())!=null){
                if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue()).getType()==SPAWN){
                    showWeaponsGoods(posX.getValue(),posY.getValue());
                    stage.close();
                }
                else {
                    showAmmoGoods(posX.getValue(),posY.getValue());
                    stage.close();
                }

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
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        Hbox1.getChildren().addAll(label1,posX);

        HBox Hbox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        Hbox2.getChildren().addAll(label2,posY);

        Label titlePosition = new Label(" Choose where you want to grab : ");
        Label titleWhichWeapon = new Label(" Choose which weapon (1,2,3 or empty) : ");
        ChoiceBox<Integer> numberWeapon = new ChoiceBox<>();
        posY.getItems().addAll(1,2,3);

        Button grab = new Button(" Grab ");
        grab.setOnAction(e -> {
            if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue()).getType() == SPAWN) {
                //TODO grab Weapon in x,y
            }
            else ;//TODO grab ammo card in x,y
        } );


        vBoxMove.getChildren().addAll(titlePosition,Hbox1,Hbox2,titleWhichWeapon,numberWeapon,grab);
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
        splitPane.setStyle("-fx-background-color: #191a17");
        Scene scene= new Scene(splitPane,670,350);
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
        iv0.setFitHeight(30);
        iv0.setFitWidth(30);
        iv0.setPreserveRatio(true);
        hBox.getChildren().add(iv0);

        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(1) + ".png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(30);
        iv1.setFitWidth(30);
        iv1.setPreserveRatio(true);
        hBox.getChildren().add(iv1);

        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(2) + ".png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(30);
        iv2.setFitWidth(30);
        iv2.setPreserveRatio(true);
        hBox.getChildren().add(iv2);

        hBox.setAlignment(Pos.CENTER);
        hBox.setStyle("-fx-background-color: #191a17");
        Scene scene= new Scene(hBox,(200),(50));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showLifePlayer1(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player 1");
        stage.setMinWidth(410);
        stage.setMinHeight(210);
        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaGiallo.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

        stackPane.getChildren().addAll(iv0);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #191a17");

        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showLifePlayer2(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player 1");
        stage.setMinWidth(410);
        stage.setMinHeight(210);
        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaVerde.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

        stackPane.getChildren().addAll(iv0);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #191a17");
        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showLifePlayer3(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player 1");
        stage.setMinWidth(410);
        stage.setMinHeight(210);
        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaGrigio.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

        stackPane.getChildren().addAll(iv0);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #191a17");
        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showLifePlayer4(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player 1");
        stage.setMinWidth(410);
        stage.setMinHeight(210);
        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaRosso.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

        stackPane.getChildren().addAll(iv0);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #191a17");
        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showLifePlayer5(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player 1");
        stage.setMinWidth(410);
        stage.setMinHeight(210);
        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "VitaBlu.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

        stackPane.getChildren().addAll(iv0);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setStyle("-fx-background-color: #191a17");
        Scene scene = new Scene(stackPane);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void refreshPlayersPosition(){
        List<Integer> pos1 = new ArrayList<>();
        List<Integer> pos2 = new ArrayList<>();
        List<Integer> pos3 = new ArrayList<>();
        List<Integer> pos4 = new ArrayList<>();
        List<Integer> pos5 = new ArrayList<>();

        if (!match.getPlayers().get(0).isInStatusSpawn() && !match.getPlayers().get(0).isInStatusWaitFirstTurn()) {
            pos1 = match.getMap().getIndex(match.getPlayers().get(0).getPosition());
            labelpos1.setText("Position of " + match.getPlayers().get(0).getNickname() + " is X,Y :" + pos1);
        }
        else labelpos1.setText("Not already spawned");
        if (!match.getPlayers().get(1).isInStatusSpawn() && !match.getPlayers().get(1).isInStatusWaitFirstTurn()) {
            pos2 = match.getMap().getIndex(match.getPlayers().get(1).getPosition());
            labelpos2.setText("Position of " + match.getPlayers().get(1).getNickname() + " is X,Y :" + pos2);
        }
        else labelpos2.setText("Not already spawned");
        if (!match.getPlayers().get(2).isInStatusSpawn() && !match.getPlayers().get(2).isInStatusWaitFirstTurn()) {
            pos3 = match.getMap().getIndex(match.getPlayers().get(2).getPosition());
            labelpos3.setText("Position of " + match.getPlayers().get(2).getNickname() + " is X,Y :" + pos3);
        }
        else labelpos3.setText("Not already spawned");
        if (match.getPlayers().size()>=4) {
            if (!match.getPlayers().get(3).isInStatusSpawn() && !match.getPlayers().get(3).isInStatusWaitFirstTurn()) {
                pos4 = match.getMap().getIndex(match.getPlayers().get(3).getPosition());
                labelpos4.setText("Position of " + match.getPlayers().get(3).getNickname() + " is X,Y :" +pos4);
            }
            else labelpos4.setText("Not already spawned");
        }
        if (match.getPlayers().size()>=5) {
            if (!match.getPlayers().get(4).isInStatusSpawn() && !match.getPlayers().get(4).isInStatusWaitFirstTurn()) {
                pos5 = match.getMap().getIndex(match.getPlayers().get(4).getPosition());
                labelpos5.setText("Position of " + match.getPlayers().get(4).getNickname() + " is X,Y :" + pos5);
            }
            else labelpos5.setText("Not already spawned");
        }
    }

}


