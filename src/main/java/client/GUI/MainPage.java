package client.GUI;

import client.remoteController.SenderClientRemoteController;
import exception.*;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;
import javafx.scene.image.ImageView;

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
        mainStage.setTitle("Adrenaline "+remoteController.getNickname());
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
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "0.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);
        vBoxLife.getChildren().add(iv0);
        // seconda board
        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "1.png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(200);
        iv1.setFitWidth(400);
        iv1.setPreserveRatio(true);
        vBoxLife.getChildren().add(iv1);
        // terza board
        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "2.png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(200);
        iv2.setFitWidth(400);
        iv2.setPreserveRatio(true);
        vBoxLife.getChildren().add(iv2);
        // quarta board
        if (match.getPlayers().size()>=4){
            File file3 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "4.png");
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
                    + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + "3.png");
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
        buttonLife1.setOnAction(e -> {
            LifeBoard life = new LifeBoard();
            life.setMatch(this.match);
            life.setPlayerClicked(match.getPlayers().get(0));
            try {
                life.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        vBoxLife.getChildren().add(buttonLife1);
        Button buttonLife2 = new Button();
        buttonLife2.setText(" Show "+match.getPlayers().get(1).getNickname()+"'s life ");
        buttonLife2.setOnAction(e -> {
            LifeBoard life = new LifeBoard();
            life.setMatch(this.match);
            life.setPlayerClicked(match.getPlayers().get(1));
            try {
                life.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        vBoxLife.getChildren().add(buttonLife2);
        Button buttonLife3 = new Button();
        buttonLife3.setText(" Show "+match.getPlayers().get(2).getNickname()+"'s life ");
        buttonLife3.setOnAction(e -> {
            LifeBoard life = new LifeBoard();
            life.setMatch(this.match);
            life.setPlayerClicked(match.getPlayers().get(2));
            try {
                life.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        vBoxLife.getChildren().add(buttonLife3);
        if (match.getPlayers().size()>=4){
            Button buttonLife4 = new Button();
            buttonLife4.setText(" Show "+match.getPlayers().get(3).getNickname()+"'s life ");
            buttonLife4.setOnAction(e -> {
                LifeBoard life = new LifeBoard();
                life.setMatch(this.match);
                life.setPlayerClicked(match.getPlayers().get(3));
                try {
                    life.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            vBoxLife.getChildren().add(buttonLife4);
        }
        if (match.getPlayers().size()>=5){
            Button buttonLife5 = new Button();
            buttonLife5.setText(" Show "+match.getPlayers().get(4).getNickname()+"'s life ");
            buttonLife5.setOnAction(e -> {
                LifeBoard life = new LifeBoard();
                life.setMatch(this.match);
                life.setPlayerClicked(match.getPlayers().get(4));
                try {
                    life.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            vBoxLife.getChildren().add(buttonLife5);
        }

        Button myAmmo = new Button(" Show My Ammo");
        myAmmo.setOnAction(e -> ShowMyAmmo());
        vBoxLife.getChildren().add(myAmmo);

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
        HBox hBoxTop = new HBox();
        hBoxTop.setMaxHeight(15);
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
        Button skipTurn = new Button(" Skip Action ");
        skipTurn.setOnAction(e -> {
            try {
                remoteController.skipAction();
            }catch (RemoteException ex){
                PopUpSceneMethod.display("Network Error", "A RemoteException was called");
            }catch (WrongStatusException ex){
                PopUpSceneMethod.display(" Status Error ", ex.getMessage());
            }
        });
        Label empty1 = new Label("                 ");
        Label empty2 = new Label("                 ");
        Button moveButton = new Button(" MOVE ");
        moveButton.setOnAction(e -> moveButton());
        Button grabButton = new Button(" GRAB ");
        grabButton.setOnAction(e -> grab());
        Button shootButton = new Button(" SHOOT ");
        shootButton.setOnAction(event -> {
            try {
                GeneralWeaponPopUp shootPopUp = new GeneralWeaponPopUp();
                shootPopUp.setMatch(match);
                shootPopUp.setSenderRemoteController(remoteController);
                shootPopUp.start(new Stage());
            } catch (Exception e) {
                PopUpSceneMethod.display("SOMETHING WENT WRONG", e.getMessage());
                //TODO errore che dice che hbox è già settata e non può essere usata come root
            }
        });
        Button chargeWeapons = new Button(" Charge Weapons");
        //TODO charge wepons

        hBoxTop.getChildren().addAll(points,showGoodsInPlace,showMyWeapons,showMyPowerUps,empty1,skipTurn,empty2,moveButton,grabButton,shootButton,chargeWeapons);

        SplitPane vSplitPane = new SplitPane();
        vSplitPane.setOrientation(Orientation.VERTICAL);
        vSplitPane.getItems().addAll(hBoxTop,splitPane);

        Scene scene = new Scene(vSplitPane,1110,650);
        mainStage.setScene(scene);
        mainStage.show();


    }

    /*
        FOR TEST USE  ONLY,  REMEMBER TO REMOVE BEFORE PACKAGING THE JAR
    */
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
        stage.setMinHeight(150);
        VBox vBoxMove = new VBox(10);

        HBox hBox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        hBox1.getChildren().addAll(label1,posX);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        hBox2.getChildren().addAll(label2,posY);

        Button move = new Button(" Move ");
        move.setOnAction(e -> {
            try {
                remoteController.move(posX.getValue(),posY.getValue());
            } catch (NotAllowedMoveException ex) {
                PopUpSceneMethod.display("Move Error", ex.getMessage());
            } catch (RemoteException ex) {
                PopUpSceneMethod.display("Network Error", ex.getMessage());
            } catch (InvalidInputException ex) {
                PopUpSceneMethod.display("Invalid Input Error", ex.getMessage());
            } catch (WrongStatusException ex) {
                PopUpSceneMethod.display("Wrong Status Error", ex.getMessage());
            } catch (NotAllowedCallException ex) {
                PopUpSceneMethod.display("Not Allowed Call Error", ex.getMessage());
            }
            stage.close();
        } );


        vBoxMove.getChildren().addAll(hBox1,hBox2,move);
        vBoxMove.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxMove);

        stage.setScene(scene);
        stage.showAndWait();
    }

    public void showGoods(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle("Show Goods");
        stage.setMinWidth(250);
        stage.setMinHeight(150);
        VBox vBoxMove = new VBox(10);

        HBox hBox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        hBox1.getChildren().addAll(label1,posX);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        hBox2.getChildren().addAll(label2,posY);

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

        vBoxMove.getChildren().addAll(hBox1, hBox2,show);
        vBoxMove.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxMove);

        stage.setScene(scene);
        stage.showAndWait(); // non torna al chiamante fino a quando non si è chiusa la finestra
    }

    public void grab(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle("Grab");
        stage.setMinWidth(250);
        stage.setMinHeight(250);
        VBox vBoxGrab = new VBox(10);

        HBox hBox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        hBox1.getChildren().addAll(label1,posX);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        hBox2.getChildren().addAll(label2,posY);

        Label titlePosition = new Label(" Choose where you want to grab : ");
        Label titleWhichWeapon = new Label(" Choose which weapon (1,2,3 or empty) : ");
        ChoiceBox<Integer> numberWeapon = new ChoiceBox<>();
        numberWeapon.getItems().addAll(1,2,3);

        Button grab = new Button(" Grab ");
        grab.setOnAction(e -> {
            if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue()).getType() == SPAWN) {
                try {
                    remoteController.grabWeapon(posX.getValue(), posY.getValue(), numberWeapon.getValue() - 1);
                    stage.close();
                } catch (NotAllowedCallException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                } catch (WrongStatusException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                } catch (RemoteException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                } catch (NotEnoughAmmoException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                } catch (WrongPositionException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                } catch (InvalidInputException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                } catch (NotAllowedMoveException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                }
            }
            else ;//TODO grab ammo card in x,y
        } );


        vBoxGrab.getChildren().addAll(titlePosition,hBox1,hBox2,titleWhichWeapon,numberWeapon,grab);
        vBoxGrab.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBoxGrab);

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
        iv0.setFitHeight(60);
        iv0.setFitWidth(60);
        iv0.setPreserveRatio(true);
        hBox.getChildren().add(iv0);

        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(1) + ".png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(60);
        iv1.setFitWidth(60);
        iv1.setPreserveRatio(true);
        hBox.getChildren().add(iv1);

        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAmmoTile().getAmmo().get(2) + ".png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(60);
        iv2.setFitWidth(60);
        iv2.setPreserveRatio(true);
        hBox.getChildren().add(iv2);

        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(8);
        hBox.setStyle("-fx-background-color: #191a17");
        Scene scene = new Scene(hBox,(220),(100));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void refreshPlayersPosition(){
        List<Integer> pos1;
        List<Integer> pos2;
        List<Integer> pos3;
        List<Integer> pos4;
        List<Integer> pos5;

        if (!match.getPlayers().get(0).isInStatusSpawn() && !match.getPlayers().get(0).isInStatusWaitFirstTurn()) {
            pos1 = match.getMap().getIndex(match.getPlayers().get(0).getPosition());
            labelpos1.setText("Position of " + match.getPlayers().get(0).getNickname() + " is X,Y :" + pos1 + " \n Status: " + match.getPlayers().get(0).getStatus().getTurnStatus());
        }
        else labelpos1.setText("Not already spawned");
        if (!match.getPlayers().get(1).isInStatusSpawn() && !match.getPlayers().get(1).isInStatusWaitFirstTurn()) {
            pos2 = match.getMap().getIndex(match.getPlayers().get(1).getPosition());
            labelpos2.setText("Position of " + match.getPlayers().get(1).getNickname() + " is X,Y :" + pos2  + " \n Status: " + match.getPlayers().get(1).getStatus().getTurnStatus());
        }
        else labelpos2.setText("Not already spawned");
        if (!match.getPlayers().get(2).isInStatusSpawn() && !match.getPlayers().get(2).isInStatusWaitFirstTurn()) {
            pos3 = match.getMap().getIndex(match.getPlayers().get(2).getPosition());
            labelpos3.setText("Position of " + match.getPlayers().get(2).getNickname() + " is X,Y :" + pos3  + " \n Status: " + match.getPlayers().get(2).getStatus().getTurnStatus());
        }
        else labelpos3.setText("Not already spawned");
        if (match.getPlayers().size()>=4) {
            if (!match.getPlayers().get(3).isInStatusSpawn() && !match.getPlayers().get(3).isInStatusWaitFirstTurn()) {
                pos4 = match.getMap().getIndex(match.getPlayers().get(3).getPosition());
                labelpos4.setText("Position of " + match.getPlayers().get(3).getNickname() + " is X,Y :" +pos4  + " \n Status: " + match.getPlayers().get(3).getStatus().getTurnStatus());
            }
            else labelpos4.setText("Not already spawned");
        }
        if (match.getPlayers().size()>=5) {
            if (!match.getPlayers().get(4).isInStatusSpawn() && !match.getPlayers().get(4).isInStatusWaitFirstTurn()) {
                pos5 = match.getMap().getIndex(match.getPlayers().get(4).getPosition());
                labelpos5.setText("Position of " + match.getPlayers().get(4).getNickname() + " is X,Y :" + pos5  + " \n Status: " + match.getPlayers().get(4).getStatus().getTurnStatus());
            }
            else labelpos5.setText("Not already spawned");
        }
    }

    public void ShowMyAmmo(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("My Ammo");

        VBox vBox = new VBox(10);
        HBox hBox1 = new HBox(5);
        HBox hBox2 = new HBox(5);
        HBox hBox3 = new HBox(5);

        // BLUE AMMO
        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "B.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(60);
        iv0.setFitWidth(60);
        iv0.setPreserveRatio(true);

        for (int i=0; i<match.getPlayer(remoteController.getNickname()).getAmmo().getBlueAmmo();i++){
            hBox1.getChildren().add(iv0);
        }

        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "R.png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv1 = new ImageView(image1);
        iv1.setFitHeight(60);
        iv1.setFitWidth(60);
        iv1.setPreserveRatio(true);

        for (int i=0; i<match.getPlayer(remoteController.getNickname()).getAmmo().getRedAmmo();i++){
            hBox2.getChildren().add(iv1);
        }

        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "Y.png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv2 = new ImageView(image2);
        iv2.setFitHeight(60);
        iv2.setFitWidth(60);
        iv2.setPreserveRatio(true);

        for (int i=0; i<match.getPlayer(remoteController.getNickname()).getAmmo().getRedAmmo();i++){
            hBox3.getChildren().add(iv2);
        }

        vBox.getChildren().addAll(hBox1,hBox2,hBox3);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #191a17");
        Scene scene = new Scene(vBox,(400),(400));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


