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
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
import model.weapons.Weapon;

import java.io.File;
import java.rmi.RemoteException;

import static model.map.SquareType.SPAWN;

public class Action2Lower extends Application {

    private Match match;
    private Weapon wp;
    private SenderClientRemoteController senderRemoteController;
    private int numberOfWeapon=0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Frenzy Action");
        VBox vbox = new VBox(5);
        setWp(null);

        //move
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

        Label label3 = new Label("Chose the index of the weapon to relplace");
        ChoiceBox<Integer> indexOfWeapon = new ChoiceBox();
        indexOfWeapon.getItems().addAll(0,1,2);
        indexOfWeapon.setValue(0);
        for (int i = 0; i<3; i++){
            if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[i]!=null) numberOfWeapon++;
        }
        if (numberOfWeapon==3){
            vbox.getChildren().addAll(label3,indexOfWeapon);
        }

        // grab
        Label title2 = new Label(" Grab Section ");
        Button buttonGrab = new Button(" Grab ");
        buttonGrab.setOnAction(e -> {
            if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue())!=null){
                if (match.getMap().getSquareFromIndex(posX.getValue(),posY.getValue()).getType()==SPAWN){
                    choseWeaponGood(posX.getValue(),posY.getValue());
                    try {
                        if (numberOfWeapon==3){
                            senderRemoteController.makeAction2FrenzyLower(posX.getValue(),posY.getValue(),wp,indexOfWeapon.getValue());
                        }
                        else senderRemoteController.makeAction2FrenzyLower(posX.getValue(),posY.getValue(),wp,-1);
                        primaryStage.close();
                    } catch (NotAllowedMoveException ex) {
                        ex.printStackTrace();
                        PopUpSceneMethod.display("SHOOTING ERROR", ex.getMessage());
                        primaryStage.close();
                    } catch (RemoteException ex) {
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
                }
                else {
                    try {
                        senderRemoteController.makeAction2FrenzyLower(posX.getValue(),posY.getValue(),wp,-1);
                    } catch (NotAllowedMoveException ex) {
                        ex.printStackTrace();
                        PopUpSceneMethod.display("SHOOTING ERROR", ex.getMessage());
                        primaryStage.close();
                    } catch (RemoteException ex) {
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
                }

            }
        });
        vbox.getChildren().addAll(title2,buttonGrab);

        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,200,300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void choseWeaponGood(int x, int y){
        Stage stage = new Stage();
        stage.setTitle("Chose your weapon");
        SplitPane splitPane = new SplitPane();

        VBox vBox = new VBox(6);
        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(0).getName() + ".png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv = new ImageView(image0);
        iv.setFitHeight(350);
        iv.setFitWidth(300);
        iv.setPreserveRatio(true);
        Button choseThisButton = new Button(" Chose this ");
        choseThisButton.setOnAction(e -> {
            setWp(match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(0));
            stage.close();
        });
        vBox.getChildren().addAll(iv,choseThisButton);
        splitPane.getItems().add(vBox);

        VBox vBox1 = new VBox(6);
        File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(1).getName() + ".png");
        Image image1 = new Image(file1.toURI().toString());
        ImageView iv0 = new ImageView(image1);
        iv0.setFitHeight(350);
        iv0.setFitWidth(300);
        iv0.setPreserveRatio(true);
        Button choseThisButton1 = new Button(" Chose this ");
        choseThisButton1.setOnAction(e -> {
            setWp(match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(1));
            stage.close();
        });
        vBox1.getChildren().addAll(iv0,choseThisButton1);
        splitPane.getItems().add(vBox1);

        VBox vBox2 = new VBox(6);
        File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(2).getName() + ".png");
        Image image2 = new Image(file2.toURI().toString());
        ImageView iv1 = new ImageView(image2);
        iv1.setFitHeight(350);
        iv1.setFitWidth(300);
        iv1.setPreserveRatio(true);
        Button choseThisButton2 = new Button(" Chose this ");
        choseThisButton2.setOnAction(e -> {
            setWp(match.getMap().getSquareFromIndex(x,y).getAvailableWeapons().get(2));
            stage.close();
        });
        vBox2.getChildren().addAll(iv1,choseThisButton2);
        splitPane.getItems().add(vBox2);

        splitPane.setMinHeight(400);
        splitPane.setMinWidth(300);
        splitPane.setStyle("-fx-background-color: #191a17");
        Scene scene= new Scene(splitPane,670,350);
        stage.setScene(scene);
        stage.show();
    }

    public void setWp(Weapon wp) {
        this.wp = wp;
    }

    public Weapon getWp() {
        return wp;
    }

    public void setSenderRemoteController(SenderClientRemoteController senderRemoteController) {
        this.senderRemoteController = senderRemoteController;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
