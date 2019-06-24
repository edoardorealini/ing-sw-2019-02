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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;
import javafx.scene.image.ImageView;
import model.player.Player;
import model.weapons.Weapon;
import model.weapons.WeaponAmmoStatus;

import java.io.File;
import java.rmi.RemoteException;
import java.util.*;
import static model.map.SquareType.*;
import static model.powerup.PowerUpName.TAGBACK_GRENADE;
import static model.powerup.PowerUpName.TARGETING_SCOPE;

public class MainPage extends Application {

    private Match match;
    SenderClientRemoteController remoteController;
    private boolean powerUpAsAmmoActive = false;
    private boolean frenzyMode = false;

    Label labelpos1;
    Label labelpos2;
    Label labelpos3;
    Label labelpos4;
    Label labelpos5;
    Label point;

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    int points = 0;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Adrenaline " +remoteController.getNickname());
        SplitPane splitPane = new SplitPane();
        //left (life)
        VBox vBoxLife = new VBox();
        vBoxLife.setMinWidth(Region.USE_PREF_SIZE);
        vBoxLife.setSpacing(3);
        vBoxLife.setMaxWidth(250);
        vBoxLife.setAlignment(Pos.CENTER);

        // inserisco le posizioni sotto
        labelpos1 = new Label("");
        labelpos2 = new Label("");
        labelpos3 = new Label("");
        labelpos4 = new Label("");
        labelpos5 = new Label("");
        refreshPlayersPosition();
        vBoxLife.getChildren().addAll(labelpos1,labelpos2,labelpos3,labelpos4,labelpos5);

        Button buttonLife1 = new Button();
        buttonLife1.setText(" Show " + match.getPlayers().get(0).getNickname() + "'s life ");
        buttonLife1.setOnAction(e -> {
            LifeBoard life = new LifeBoard();
            life.setMatch(this.match);
            life.setPlayerClicked(match.getPlayers().get(0));
            System.out.println("Show life "+frenzyMode);
            if (frenzyMode){
                System.out.println("frenzy mode life");
                life.setFrenzyActive(true);
            }
            try {
                life.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        vBoxLife.getChildren().add(buttonLife1);

        Button buttonLife2 = new Button();
        buttonLife2.setText(" Show " + match.getPlayers().get(1).getNickname() + "'s life ");
        buttonLife2.setOnAction(e -> {
            LifeBoard life = new LifeBoard();
            life.setMatch(this.match);
            life.setPlayerClicked(match.getPlayers().get(1));
            if (frenzyMode){
                System.out.println("frenzy mode life");
                life.setFrenzyActive(true);
            }
            try {
                life.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        vBoxLife.getChildren().add(buttonLife2);

        Button buttonLife3 = new Button();
        buttonLife3.setText(" Show " + match.getPlayers().get(2).getNickname() + "'s life ");
        buttonLife3.setOnAction(e -> {
            LifeBoard life = new LifeBoard();
            life.setMatch(this.match);
            life.setPlayerClicked(match.getPlayers().get(2));
            if (frenzyMode){
                System.out.println("frenzy mode life");
                life.setFrenzyActive(true);
            }
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
                if (frenzyMode){
                    System.out.println("frenzy mode life");
                    life.setFrenzyActive(true);
                }
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
                if (frenzyMode){
                    System.out.println("frenzy mode life");
                    life.setFrenzyActive(true);
                }
                try {
                    life.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            vBoxLife.getChildren().add(buttonLife5);
        }

        Button myAmmo = new Button("Show My Ammo");
        myAmmo.setOnAction(e -> showMyAmmo());
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

        iv.setOnMouseClicked(mouseClicked -> {
                    //System.out.println("Click at X = " + mouseClicked.getX());
                    //(System.out.println("Click at Y = " + mouseClicked.getY());
                    showGoodsInCoordinates(mouseClicked.getX(), mouseClicked.getY());
                }
            );

        splitPane.getItems().add(iv);

        // Top (buttons)
        HBox hBoxTop = new HBox();
        hBoxTop.setMaxHeight(15);
        point = new Label("My Points : "+points+" ");
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

        hBoxTop.getChildren().addAll(point,showMyWeapons,showMyPowerUps,empty1,skipTurn,empty2);

        // normal Mode
            Button moveButton = new Button(" MOVE ");
            setButton1(moveButton);
            moveButton.setOnAction(e -> moveButton());

            Button grabButton = new Button(" GRAB ");
            setButton2(grabButton);
            grabButton.setOnAction(e -> grab());

            Button shootButton = new Button(" SHOOT ");
            setButton3(shootButton);
            shootButton.setOnAction(event -> {
                try {
                    GeneralWeaponPopUp shootPopUp = new GeneralWeaponPopUp();
                    shootPopUp.setMatch(match);
                    shootPopUp.setSenderRemoteController(remoteController);
                    shootPopUp.start(new Stage());
                } catch (Exception e) {
                    PopUpSceneMethod.display("SOMETHING WENT WRONG", e.getMessage());
                }
            });
            Button reloadWeapons = new Button("Reload Weapons");
            setButton4(reloadWeapons);
            reloadWeapons.setOnAction(event -> reloadPopup());

            hBoxTop.getChildren().addAll(moveButton,grabButton,shootButton,reloadWeapons);

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

    public void setFrenzyMode(boolean mode){
        this.frenzyMode = mode;
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

    private void showGoodsInCoordinates(double x, double y){
        //origine presa in alto a sx
        //non modificare MAI questi valori, servono per separare gli square nell'immagine
        //NB i valori DIPENDONO dalla dimensione che si imposta della mappa nella vista della GUI
        //se per caso dovessimo modificarla, andrebbero modificati solo queste costanti.
        double x0 = 122;
        double x1 = 237;
        double x2 = 376;
        double x3 = 509;
        double x4 = 626;

        double y0 = 127;
        double y1 = 253;
        double y2 = 372;
        double y3 = 501;

        //prima riga in alto
        if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
            if (match.getMap().getSquareFromIndex(0, 2) != null) {
                if (match.getMap().getSquareFromIndex(0, 2).getType() == SPAWN) {
                    showWeaponsGoods(0, 2);
                } else {
                    showAmmoGoods(0, 2);
                }
            }
        }
        if (x > x1 && x <= x2 && y >= y0 && y <= y1) {
            if (match.getMap().getSquareFromIndex(1, 2) != null) {
                if (match.getMap().getSquareFromIndex(1, 2).getType() == SPAWN) {
                    showWeaponsGoods(1, 2);
                } else {
                    showAmmoGoods(1, 2);
                }
            }
        }
        if (x > x2 && x <= x3 && y >= y0 && y <= y1) {
            if (match.getMap().getSquareFromIndex(2, 2) != null) {
                if (match.getMap().getSquareFromIndex(2, 2).getType() == SPAWN) {
                    showWeaponsGoods(2, 2);
                } else {
                    showAmmoGoods(2, 2);
                }
            }
        }
        if (x > x3 && x <= x4 && y >= y0 && y <= y1) {
            if (match.getMap().getSquareFromIndex(3, 2) != null) {
                if (match.getMap().getSquareFromIndex(3, 2).getType() == SPAWN) {
                    showWeaponsGoods(3, 2);
                } else {
                    showAmmoGoods(3, 2);
                }
            }
        }

        //riga centrale
        if (x >= x0 && x <= x1 && y >= y1 && y <= y2) {
            if (match.getMap().getSquareFromIndex(0, 1) != null) {
                if (match.getMap().getSquareFromIndex(0, 1).getType() == SPAWN) {
                    showWeaponsGoods(0, 1);
                } else {
                    showAmmoGoods(0, 1);
                }
            }
        }
        if (x > x1 && x <= x2 && y >= y1 && y <= y2) {
            if (match.getMap().getSquareFromIndex(1, 1) != null) {
                if (match.getMap().getSquareFromIndex(1, 1).getType() == SPAWN) {
                    showWeaponsGoods(1, 1);
                } else {
                    showAmmoGoods(1, 1);
                }
            }
        }
        if (x > x2 && x <= x3 && y >= y1 && y <= y2) {
            if (match.getMap().getSquareFromIndex(2, 1) != null) {
                if (match.getMap().getSquareFromIndex(2, 1).getType() == SPAWN) {
                    showWeaponsGoods(2, 1);
                } else {
                    showAmmoGoods(2, 1);
                }
            }
        }
        if (x > x3 && x <= x4 && y >= y1 && y <= y2) {
            if (match.getMap().getSquareFromIndex(3, 1) != null) {
                if (match.getMap().getSquareFromIndex(3, 1).getType() == SPAWN) {
                    showWeaponsGoods(3, 1);
                } else {
                    showAmmoGoods(3, 1);
                }
            }
        }

        //riga in basso
        if (x >= x0 && x <= x1 && y >= y2 && y <= y3) {
            if (match.getMap().getSquareFromIndex(0, 0) != null) {
                if (match.getMap().getSquareFromIndex(0, 0).getType() == SPAWN) {
                    showWeaponsGoods(0, 0);
                } else {
                    showAmmoGoods(0, 0);
                }
            }
        }
        if (x > x1 && x <= x2 && y >= y2 && y <= y3) {
            if (match.getMap().getSquareFromIndex(1, 0) != null) {
                if (match.getMap().getSquareFromIndex(1, 0).getType() == SPAWN) {
                    showWeaponsGoods(1, 0);
                } else {
                    showAmmoGoods(1, 0);
                }
            }
        }
        if (x > x2 && x <= x3 && y >= y2 && y <= y3) {
            if (match.getMap().getSquareFromIndex(2, 0) != null) {
                if (match.getMap().getSquareFromIndex(2, 0).getType() == SPAWN) {
                    showWeaponsGoods(2, 0);
                } else {
                    showAmmoGoods(2, 0);
                }
            }
        }
        if (x > x3 && x <= x4 && y >= y2 && y <= y3) {
            if (match.getMap().getSquareFromIndex(3, 0) != null) {
                if (match.getMap().getSquareFromIndex(3, 0).getType() == SPAWN) {
                    showWeaponsGoods(3, 0);
                } else {
                    showAmmoGoods(3, 0);
                }
            }
        }
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
        stage.setMinWidth(270);
        stage.setMinHeight(300);
        VBox vBoxGrab = new VBox(10);

        HBox hBox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        posX.setValue(match.getMap().getIndex(match.getPlayer(remoteController.getNickname()).getPosition()).get(0));
        hBox1.getChildren().addAll(label1,posX);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        posY.setValue(match.getMap().getIndex(match.getPlayer(remoteController.getNickname()).getPosition()).get(1));
        hBox2.getChildren().addAll(label2,posY);

        Label titlePosition = new Label(" Choose where you want to grab : ");
        Label titleWhichWeapon = new Label(" Choose which weapon (1,2,3 or empty) : ");
        Label swapWeapon = new Label("Choose the weapon to swap :");
        ChoiceBox<Integer> weaponToSwap = new ChoiceBox<>();
        ChoiceBox<Integer> numberWeapon = new ChoiceBox<>();
        weaponToSwap.getItems().addAll(1,2,3);
        weaponToSwap.setValue(1);
        numberWeapon.getItems().addAll(1,2,3);

        Button grab = new Button("GRAB");
        grab.setOnAction(e -> {
            if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue()).getType() == SPAWN) {
                try {
                    remoteController.grabWeapon(posX.getValue(), posY.getValue(), numberWeapon.getValue() - 1, weaponToSwap.getValue() -1);
                    stage.close();
                } catch (NotAllowedCallException | WrongStatusException |RemoteException |NotEnoughAmmoException | WrongPositionException | InvalidInputException | NotAllowedMoveException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                }
            }
            else {
                try {
                    remoteController.grabAmmoCard(posX.getValue(), posY.getValue());
                    stage.close();
                } catch (NotAllowedCallException | WrongStatusException |RemoteException | WrongPositionException | InvalidInputException | NotAllowedMoveException ex) {
                    PopUpSceneMethod.display("Error", ex.getMessage());
                }
            }
        } );

        int numberOfOwnedWeapons = 0;  //local variable

        for (Weapon weapon : match.getPlayer(remoteController.getNickname()).getWeapons()) {
            if (weapon != null)
                numberOfOwnedWeapons++;
        }

        if (numberOfOwnedWeapons == 3)
            vBoxGrab.getChildren().addAll(titlePosition, hBox1, hBox2, titleWhichWeapon, numberWeapon, swapWeapon, weaponToSwap, grab);
        else
            vBoxGrab.getChildren().addAll(titlePosition, hBox1, hBox2, titleWhichWeapon, numberWeapon, grab);

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

    public void refreshPoints(){
        points = match.getPlayer(remoteController.getNickname()).getPoints();
        point.setText("My Points : " + points + " ");
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
        else labelpos1.setText("Not spawned yet");
        if (!match.getPlayers().get(1).isInStatusSpawn() && !match.getPlayers().get(1).isInStatusWaitFirstTurn()) {
            pos2 = match.getMap().getIndex(match.getPlayers().get(1).getPosition());
            labelpos2.setText("Position of " + match.getPlayers().get(1).getNickname() + " is X,Y :" + pos2  + " \n Status: " + match.getPlayers().get(1).getStatus().getTurnStatus());
        }
        else labelpos2.setText("Not spawned yet");
        if (!match.getPlayers().get(2).isInStatusSpawn() && !match.getPlayers().get(2).isInStatusWaitFirstTurn()) {
            pos3 = match.getMap().getIndex(match.getPlayers().get(2).getPosition());
            labelpos3.setText("Position of " + match.getPlayers().get(2).getNickname() + " is X,Y :" + pos3  + " \n Status: " + match.getPlayers().get(2).getStatus().getTurnStatus());
        }
        else labelpos3.setText("Not spawned yet");
        if (match.getPlayers().size()>=4) {
            if (!match.getPlayers().get(3).isInStatusSpawn() && !match.getPlayers().get(3).isInStatusWaitFirstTurn()) {
                pos4 = match.getMap().getIndex(match.getPlayers().get(3).getPosition());
                labelpos4.setText("Position of " + match.getPlayers().get(3).getNickname() + " is X,Y :" +pos4  + " \n Status: " + match.getPlayers().get(3).getStatus().getTurnStatus());
            }
            else labelpos4.setText("Not spawned yet");
        }
        if (match.getPlayers().size()>=5) {
            if (!match.getPlayers().get(4).isInStatusSpawn() && !match.getPlayers().get(4).isInStatusWaitFirstTurn()) {
                pos5 = match.getMap().getIndex(match.getPlayers().get(4).getPosition());
                labelpos5.setText("Position of " + match.getPlayers().get(4).getNickname() + " is X,Y :" + pos5  + " \n Status: " + match.getPlayers().get(4).getStatus().getTurnStatus());
            }
            else labelpos5.setText("Not spawned yet");
        }
    }

    public void showMyAmmo(){
        Stage primaryStage = new Stage();
        primaryStage.setTitle("My Ammo");

        VBox vBox = new VBox(10);
        HBox hBox1 = new HBox(5);
        HBox hBox2 = new HBox(5);
        HBox hBox3 = new HBox(5);

        // BLUE AMMO
        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "B.png");
        Image blueAmmoImage1 = new Image(file0.toURI().toString());
        Image blueAmmoImage2 = new Image(file0.toURI().toString());
        Image blueAmmoImage3 = new Image(file0.toURI().toString());
        ImageView blueView1 = new ImageView(blueAmmoImage1);
        ImageView blueView2 = new ImageView(blueAmmoImage2);
        ImageView blueView3 = new ImageView(blueAmmoImage3);
        blueView1.setFitHeight(60);
        blueView1.setFitWidth(60);
        blueView1.setPreserveRatio(true);
        blueView2.setFitHeight(60);
        blueView2.setFitWidth(60);
        blueView2.setPreserveRatio(true);
        blueView3.setFitHeight(60);
        blueView3.setFitWidth(60);
        blueView3.setPreserveRatio(true);

        switch (match.getPlayer(remoteController.getNickname()).getAmmo().getBlueAmmo()) {
            case 1:
                hBox1.getChildren().add(blueView1);
                 break;
            case 2:
                hBox1.getChildren().add(blueView1);
                hBox1.getChildren().add(blueView2);
                break;
            case 3:
                hBox1.getChildren().add(blueView1);
                hBox1.getChildren().add(blueView2);
                hBox1.getChildren().add(blueView3);
                break;
            default:
                    break;
        }

        // RED AMMO
        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "R.png");
        Image redAmmoImage1 = new Image(file1.toURI().toString());
        Image redAmmoImage2 = new Image(file1.toURI().toString());
        Image redAmmoImage3 = new Image(file1.toURI().toString());
        ImageView redView1 = new ImageView(redAmmoImage1);
        ImageView redView2 = new ImageView(redAmmoImage2);
        ImageView redView3 = new ImageView(redAmmoImage3);
        redView1.setFitHeight(60);
        redView1.setFitWidth(60);
        redView1.setPreserveRatio(true);
        redView2.setFitHeight(60);
        redView2.setFitWidth(60);
        redView2.setPreserveRatio(true);
        redView3.setFitHeight(60);
        redView3.setFitWidth(60);
        redView3.setPreserveRatio(true);

        switch (match.getPlayer(remoteController.getNickname()).getAmmo().getRedAmmo()) {
            case 1:
                hBox2.getChildren().add(redView1);
                break;
            case 2:
                hBox2.getChildren().add(redView1);
                hBox2.getChildren().add(redView2);
                break;
            case 3:
                hBox2.getChildren().add(redView1);
                hBox2.getChildren().add(redView2);
                hBox2.getChildren().add(redView3);
                break;
            default:
                break;
        }

        // YELLOW AMMO
        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "Y.png");
        Image yellowAmmoImage1 = new Image(file2.toURI().toString());
        Image yellowAmmoImage2 = new Image(file2.toURI().toString());
        Image yellowAmmoImage3 = new Image(file2.toURI().toString());
        ImageView yellowView1 = new ImageView(yellowAmmoImage1);
        ImageView yellowView2 = new ImageView(yellowAmmoImage2);
        ImageView yellowView3 = new ImageView(yellowAmmoImage3);
        yellowView1.setFitHeight(60);
        yellowView1.setFitWidth(60);
        yellowView1.setPreserveRatio(true);
        yellowView2.setFitHeight(60);
        yellowView2.setFitWidth(60);
        yellowView2.setPreserveRatio(true);
        yellowView3.setFitHeight(60);
        yellowView3.setFitWidth(60);
        yellowView3.setPreserveRatio(true);

        switch (match.getPlayer(remoteController.getNickname()).getAmmo().getYellowAmmo()) {
            case 1:
                hBox3.getChildren().add(yellowView1);
                break;
            case 2:
                hBox3.getChildren().add(yellowView1);
                hBox3.getChildren().add(yellowView2);
                break;
            case 3:
                hBox3.getChildren().add(yellowView1);
                hBox3.getChildren().add(yellowView2);
                hBox3.getChildren().add(yellowView3);
                break;
            default:
                break;
        }

        vBox.getChildren().addAll(hBox1,hBox2,hBox3);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.setAlignment(Pos.CENTER);
        vBox.setStyle("-fx-background-color: #191a17");
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,(400),(400));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void askForPowerUpAsAmmo() {

        Stage powAsAmmoStage = new Stage();
        powerUpAsAmmoActive = true;

        int a = 0;
        int b = 0;
        int c = 0;

        powAsAmmoStage.initModality(Modality.APPLICATION_MODAL);
        powAsAmmoStage.setTitle("Use Power Up as Ammo");
        HBox hBoxImages = new HBox();
        VBox vBoxPage = new VBox();
        HBox hBoxButtons = new HBox();

        Label text = new Label("Choose one power up to use as ammo:");
        Button pow1 = new Button("PAY WITH THIS");
        Button pow2 = new Button("PAY WITH THIS");
        Button pow3 = new Button("PAY WITH THIS");


        vBoxPage.getChildren().setAll(hBoxImages, text, hBoxButtons);

        vBoxPage.setAlignment(Pos.CENTER);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxImages.setAlignment(Pos.CENTER);
        vBoxPage.setSpacing(8);
        hBoxImages.setSpacing(10);
        hBoxButtons.setSpacing(100);


        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[0]!=null){
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + match.getPlayer(remoteController.getNickname()).getPowerUps()[0].getName() + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow1);
            hBoxImages.getChildren().add(iv0);
            a = 1;
        }

        pow1.setOnAction(event -> {
            try {
                remoteController.usePowerUpAsAmmo(0);
                powAsAmmoStage.close();
                powerUpAsAmmoActive = false;
            } catch (NotInYourPossessException | RemoteException | NotAllowedCallException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[1]!=null){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + match.getPlayer(remoteController.getNickname()).getPowerUps()[1].getName() + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow2);
            hBoxImages.getChildren().add(iv1);
            b = 1;
        }

        pow2.setOnAction(event -> {
            try {
                remoteController.usePowerUpAsAmmo(1);
                powAsAmmoStage.close();
                powerUpAsAmmoActive = false;
            } catch (NotInYourPossessException | RemoteException | NotAllowedCallException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[2]!=null){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + match.getPlayer(remoteController.getNickname()).getPowerUps()[2].getName() + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            hBoxButtons.getChildren().add(pow3);
            hBoxImages.getChildren().add(iv2);
            c = 1;
        }

        pow3.setOnAction(event -> {
            try {
                remoteController.usePowerUpAsAmmo(2);
                powAsAmmoStage.close();
                powerUpAsAmmoActive = false;
            } catch (NotInYourPossessException | RemoteException | NotAllowedCallException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });



        Scene scene = new Scene(vBoxPage, (300*(a+b+c)), 400);
        powAsAmmoStage.setScene(scene);

        powAsAmmoStage.setOnCloseRequest(event -> {
            try {
                if (match.getPlayer(remoteController.getNickname()).isInStatusReloading())
                     remoteController.closeTimer("WaitForWeaponLoaded");
                else
                    remoteController.closeTimer("WaitForPayment");
                powerUpAsAmmoActive = false;
            } catch (RemoteException e) {
                PopUpSceneMethod.display("SOMETHING WENT WRONG", e.getMessage());
            }
            powAsAmmoStage.close();
        });

        powAsAmmoStage.show();
    }

    public boolean isPowerUpAsAmmoActive() {
        return powerUpAsAmmoActive;
    }

    public void reloadPopup() {

        int first = 0;
        int second = 0;
        int third = 0;

        Stage reloadStage = new Stage();

        reloadStage.initModality(Modality.APPLICATION_MODAL);
        reloadStage.setTitle("Owned weapons");
        Label text = new Label("Choose the weapon you want to reload:");
        HBox hBoxWeapon = new HBox();
        HBox hBoxButtons = new HBox();
        VBox vBox = new VBox();
        Button reloadWeapon1 = new Button();
        Button reloadWeapon2 = new Button();
        Button reloadWeapon3 = new Button();
        reloadWeapon1.setTextFill(Color.CADETBLUE);
        reloadWeapon2.setTextFill(Color.CADETBLUE);
        reloadWeapon3.setTextFill(Color.CADETBLUE);
        reloadWeapon1.setAlignment(Pos.CENTER);
        reloadWeapon2.setAlignment(Pos.CENTER);
        reloadWeapon3.setAlignment(Pos.CENTER);


        if (match.getPlayer(remoteController.getNickname()).getWeapons()[0] != null && match.getPlayer(remoteController.getNickname()).getWeapons()[0].getWeaponStatus() == WeaponAmmoStatus.UNLOADED) {
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(remoteController.getNickname()).getWeapons()[0].getName() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(350);
            iv0.setFitWidth(300);
            iv0.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv0);
            reloadWeapon1.setText("Reload " + match.getPlayer(remoteController.getNickname()).getWeapons()[0].getName());
            hBoxButtons.getChildren().add(reloadWeapon1);
            first = 1;
        }

        if (match.getPlayer(remoteController.getNickname()).getWeapons()[1] != null && match.getPlayer(remoteController.getNickname()).getWeapons()[1].getWeaponStatus() == WeaponAmmoStatus.UNLOADED){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(remoteController.getNickname()).getWeapons()[1].getName() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(350);
            iv1.setFitWidth(300);
            iv1.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv1);
            reloadWeapon2.setText("Reload " + match.getPlayer(remoteController.getNickname()).getWeapons()[1].getName());
            hBoxButtons.getChildren().add(reloadWeapon2);
            second = 1;
        }

        if (match.getPlayer(remoteController.getNickname()).getWeapons()[2] != null && match.getPlayer(remoteController.getNickname()).getWeapons()[2].getWeaponStatus() == WeaponAmmoStatus.UNLOADED){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(remoteController.getNickname()).getWeapons()[2].getName() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(350);
            iv2.setFitWidth(300);
            iv2.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv2);
            reloadWeapon3.setText("Reload " + match.getPlayer(remoteController.getNickname()).getWeapons()[2].getName());
            hBoxButtons.getChildren().add(reloadWeapon3);
            third = 1;
        }

        hBoxWeapon.setMinHeight(350);
        hBoxWeapon.setMinWidth(300);
        hBoxButtons.setMinHeight(80);
        hBoxButtons.setMinWidth(100);
        hBoxWeapon.setAlignment(Pos.CENTER);
        hBoxWeapon.setSpacing(8);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(250);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        //setting the action linked to the button
        reloadWeapon1.setOnAction(event -> {
            try {
                remoteController.reload(0);
                reloadStage.close();
            } catch (RemoteException | NotEnoughAmmoException | NotAllowedCallException |WrongStatusException e) {
                //e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        reloadWeapon2.setOnAction(event -> {
            try {
                remoteController.reload(1);
                reloadStage.close();
            } catch (RemoteException | NotEnoughAmmoException | NotAllowedCallException |WrongStatusException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        reloadWeapon3.setOnAction(event -> {
            try {
                remoteController.reload(2);
                reloadStage.close();
            } catch (RemoteException | NotEnoughAmmoException | NotAllowedCallException |WrongStatusException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        vBox.getChildren().addAll(hBoxWeapon, text, hBoxButtons);

        //TODO reloadStage.setOnCloseRequest(event -> );


        Scene scene = new Scene(vBox, (300*(first+second+third) + 100), 450);
        reloadStage.setScene(scene);
        reloadStage.showAndWait();

    }

    public void askForTagBackPopup() {

        Stage tagBackStage = new Stage();
        tagBackStage.initModality(Modality.APPLICATION_MODAL);
        tagBackStage.setTitle("TagBack Grenade use");

        int first = 0;
        int second = 0;
        int third = 0;

        HBox imagesHBox = new HBox();
        VBox vBoxPage = new VBox();
        HBox buttons = new HBox();
        Label text = new Label("You have been hit by " + match.getCurrentPlayer().getNickname() + ", do you want revenge?");
        Button buttonTagBack1 = new Button("YEAH!");
        Button buttonTagBack2 = new Button("YEAH!");
        Button buttonTagBack3 = new Button("YEAH!");

        buttonTagBack1.setOnAction(event -> {
            try {
                remoteController.useTagBackGrenade(0);
                tagBackStage.close();
            } catch (NotInYourPossessException | RemoteException | WrongStatusException | NotAllowedTargetException  e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        buttonTagBack2.setOnAction(event -> {
            try {
                remoteController.useTagBackGrenade(1);
                tagBackStage.close();
            } catch (NotInYourPossessException | RemoteException | WrongStatusException | NotAllowedTargetException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        buttonTagBack3.setOnAction(event -> {
            try {
                remoteController.useTagBackGrenade(2);
                tagBackStage.close();
            } catch (NotInYourPossessException | RemoteException | WrongStatusException | NotAllowedTargetException  e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        vBoxPage.getChildren().setAll(imagesHBox, text, buttons);

        vBoxPage.setAlignment(Pos.CENTER);
        vBoxPage.setSpacing(8);

        imagesHBox.setAlignment(Pos.CENTER);
        imagesHBox.setSpacing(10);

        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);


        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[0] != null && match.getPlayer(remoteController.getNickname()).getPowerUps()[0].getName() == TAGBACK_GRENADE){
            File firstFile = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + TAGBACK_GRENADE + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(firstFile.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            buttons.getChildren().add(buttonTagBack1);
            imagesHBox.getChildren().add(iv0);
            first = 1;
        }

        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[1] != null && match.getPlayer(remoteController.getNickname()).getPowerUps()[1].getName() == TAGBACK_GRENADE){
            File secondFile = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + TAGBACK_GRENADE + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(secondFile.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            buttons.getChildren().add(buttonTagBack2);
            imagesHBox.getChildren().add(iv1);
            second = 1;
        }

        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[2] != null && match.getPlayer(remoteController.getNickname()).getPowerUps()[2].getName() == TAGBACK_GRENADE){
            File thirdFile = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + TAGBACK_GRENADE + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(thirdFile.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            buttons.getChildren().add(buttonTagBack3);
            imagesHBox.getChildren().add(iv2);
            third = 1;
        }


        Scene scene = new Scene(vBoxPage, (300*(first+second+third)), 400);
        tagBackStage.setScene(scene);

        tagBackStage.setOnCloseRequest(event -> tagBackStage.close());

        tagBackStage.show();
    }

    public void askForTargetingScope() {

        int first = 0;
        int second = 0;
        int third = 0;

        Stage targetingScopeStage = new Stage();
        targetingScopeStage.initModality(Modality.APPLICATION_MODAL);
        targetingScopeStage.setTitle("Targeting Scope use");

        HBox imagesHBox = new HBox();
        VBox vBoxPage = new VBox();
        HBox buttons = new HBox();
        HBox target = new HBox();
        HBox ammo = new HBox();
        Button buttonTargeting1 = new Button("SELECT THIS");
        Button buttonTargeting2 = new Button("SELECT THIS");
        Button buttonTargeting3 = new Button("SELECT THIS");
        Label text = new Label("Do you want to use a targeting scope?");
        Label textTarget = new Label("Select the target you want hit: ");
        Label textAmmo = new Label("Choose the ammo you want to convert: ");
        ChoiceBox<String> targets = new ChoiceBox<>();
        ChoiceBox<model.Color> ammoColor = new ChoiceBox<>();

        buttonTargeting1.setOnAction(event -> {
            try {
                remoteController.useTargetingScope(0, targets.getValue(), ammoColor.getValue());
                targetingScopeStage.close();
            } catch (NotInYourPossessException | RemoteException | WrongStatusException | NotEnoughAmmoException | NotAllowedCallException | NotAllowedTargetException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        buttonTargeting2.setOnAction(event -> {
            try {
                remoteController.useTargetingScope(1, targets.getValue(), ammoColor.getValue());
                targetingScopeStage.close();
            } catch (NotInYourPossessException | RemoteException | WrongStatusException | NotEnoughAmmoException | NotAllowedCallException | NotAllowedTargetException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        buttonTargeting3.setOnAction(event -> {
            try {
                remoteController.useTargetingScope(2, targets.getValue(), ammoColor.getValue());
                targetingScopeStage.close();
            } catch (NotInYourPossessException | RemoteException | WrongStatusException | NotEnoughAmmoException | NotAllowedCallException | NotAllowedTargetException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        ammoColor.getItems().addAll(model.Color.RED, model.Color.BLUE, model.Color.YELLOW);
        ammoColor.setValue(model.Color.RED);
        for (Player p : match.getPlayers()) {       //setting the players that have been hit
            if (p.hasBeenDamaged())
                targets.getItems().add(p.getNickname());
        }

        vBoxPage.getChildren().setAll(imagesHBox, text, target, ammo, buttons);
        target.getChildren().addAll(textTarget, targets);
        ammo.getChildren().addAll(textAmmo, ammoColor);

        vBoxPage.setAlignment(Pos.CENTER);
        vBoxPage.setSpacing(8);
        imagesHBox.setAlignment(Pos.CENTER);
        imagesHBox.setSpacing(10);
        target.setAlignment(Pos.CENTER);
        target.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(100);
        ammo.setAlignment(Pos.CENTER);
        ammo.setSpacing(10);


        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[0] != null && match.getPlayer(remoteController.getNickname()).getPowerUps()[0].getName() == TARGETING_SCOPE){
            File firstFile = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + TARGETING_SCOPE + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(firstFile.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            first = 1;
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            buttons.getChildren().add(buttonTargeting1);
            imagesHBox.getChildren().add(iv0);
        }

        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[1] != null && match.getPlayer(remoteController.getNickname()).getPowerUps()[1].getName() == TARGETING_SCOPE){
            File secondFile = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + TARGETING_SCOPE + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(secondFile.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            second = 1;
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            buttons.getChildren().add(buttonTargeting2);
            imagesHBox.getChildren().add(iv1);
        }

        if (match.getPlayer(remoteController.getNickname()).getPowerUps()[2] != null && match.getPlayer(remoteController.getNickname()).getPowerUps()[2].getName() == TARGETING_SCOPE){
            File thirdFile = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + TARGETING_SCOPE + "_" + match.getPlayer(remoteController.getNickname()).getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(thirdFile.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            third = 1;
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            buttons.getChildren().add(buttonTargeting3);
            imagesHBox.getChildren().add(iv2);
        }

        Scene scene = new Scene(vBoxPage, (300 * (first + second + third)), 550);
        targetingScopeStage.setScene(scene);

        targetingScopeStage.setOnCloseRequest(event -> targetingScopeStage.close());

        targetingScopeStage.show();
    }


    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public void setButton3(Button button3) {
        this.button3 = button3;
    }

    public void setButton4(Button button4) {
        this.button4 = button4;
    }

    public void frenzyButtonBoosted(){
        button1.setText(" Action 1 Frenzy ");
        button1.setOnAction(e -> {
            Action1Boosted action1Boosted = new Action1Boosted();
            action1Boosted.setMatch(this.match);
            action1Boosted.setSenderRemoteController(this.remoteController);
            try {
                action1Boosted.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }
        });

        button2.setText(" Action 2 Frenzy ");
        button2.setOnAction(e -> {
            Action2Boosted action2Boosted = new Action2Boosted();
            action2Boosted.setSenderRemoteController(this.remoteController);
            action2Boosted.setMatch(this.match);
            try {
                action2Boosted.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }
        });

        button3.setText(" Action 3 Frenzy ");
        button3.setOnAction(e -> {
            Action3Boosted action3Boosted = new Action3Boosted();
            action3Boosted.setMatch(this.match);
            action3Boosted.setSenderRemoteController(this.remoteController);
            try {
                action3Boosted.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }
        });

        button4.setText(" Show Frenzy Actions ");
        button4.setOnAction(e -> {
            ChooseFrenzyActionBoosted choseAction = new ChooseFrenzyActionBoosted();
            try {
                choseAction.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }
        });
    }

    public void frenzyButtonLower(){
        button1.setText(" Action 1 Frenzy ");
        button1.setOnAction(e -> {
            Action1Lower action1Lower = new Action1Lower();
            action1Lower.setMatch(this.match);
            action1Lower.setSenderRemoteController(this.remoteController);
            try {
                action1Lower.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }

        });

        button2.setText(" Action 2 Frenzy ");
        button2.setOnAction(e -> {
            Action2Lower action2Lower = new Action2Lower();
            action2Lower.setMatch(this.match);
            action2Lower.setSenderRemoteController(this.remoteController);
            try {
                action2Lower.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }
        });

        button3.setVisible(false);

        button4.setText(" Show Frenzy Actions ");
        button4.setOnAction(e -> {
            ChooseFrenzyActionLower choseAction = new ChooseFrenzyActionLower();
            try {
                choseAction.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                PopUpSceneMethod.display("ERROR", ex.getMessage());
            }
        });
    }
}


