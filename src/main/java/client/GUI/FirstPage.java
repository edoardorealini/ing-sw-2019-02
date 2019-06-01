package client.GUI;

import client.remoteController.RemoteController;
import client.remoteController.RemoteControllerRMI;
import client.remoteController.RemoteControllerSocket;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.*;

import javafx.scene.image.Image;
import model.Match;

import java.io.File;

public class FirstPage extends Application implements Runnable{

    Stage primaryStage;
    RemoteController remoteController;
    Scene sceneLobby;
    Scene scene;

    Match match;

    public FirstPage(Match match){
        this.match = match;
    }

    public void run() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Adrenaline");


        /*
        Bella johnny, qui ti metto esempio con URL da internet, funziona
        Per URL intendeva prorpio una URL vera, non un path, quando chiede path ti esce scritto pathfile
        Image image = new Image("https://picsum.photos/id/117/200/300");
        */

        //qui sotto ti metto il modo che devi usare per prendere l'immagine da file
        //per prima cosa devi aprire il file con new File, poi renderlo leggibile (.toURI().toString())
        //vedi qui:  https://stackoverflow.com/questions/7830951/how-can-i-load-computer-directory-images-in-javafx#8088561


        GridPane grid = new GridPane();
        grid.setVgap(10); // sapzio verticale tra boxes
        grid.setHgap(8); // spazio orizzontale tra boxes

        // Imposto immagine background
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "AdrenalineBackground.png");

        BackgroundImage myBI= new BackgroundImage(new Image(file.toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        grid.setBackground(new Background(myBI));

        // inserisco i nodi
        Label nameLabel = new Label("Username: ");
        GridPane.setConstraints(nameLabel, 50,18);

        TextField inputName = new TextField();
        inputName.setPromptText("Name");
        GridPane.setConstraints(inputName, 50,19);

        Label typeOfConnection = new Label("Type of connection: ");
        GridPane.setConstraints(typeOfConnection,50,20);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().add("RMI");
        choiceBox.getItems().add("Socket");
        choiceBox.setValue("RMI");
        GridPane.setConstraints(choiceBox,50,21);

        Label ipLabel = new Label("IP: ");
        GridPane.setConstraints(ipLabel, 50,22);

        TextField inputIp = new TextField();
        inputIp.setText("192.168.5.6");
        GridPane.setConstraints(inputIp, 50,23);


        Button playButton = new Button();
        playButton.setText("  PLAY  ");
        playButton.getStyleClass().add("button-play");
        GridPane.setConstraints(playButton,50,24);
        playButton.setOnAction(e -> checkInput(inputName,choiceBox,inputIp,primaryStage));

        // ++++++++++++++++++++++++++++++++++
        // codice per cambaire font
        nameLabel.setTextFill(Color.YELLOWGREEN);
        nameLabel.setStyle("-fx-font-weight: bold");
        typeOfConnection.setTextFill(Color.YELLOWGREEN);
        typeOfConnection.setStyle("-fx-font-weight: bold");
        ipLabel.setTextFill(Color.YELLOWGREEN);
        ipLabel.setStyle("-fx-font-weight: bold");

        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        nameLabel.setEffect(blend);
        typeOfConnection.setEffect(blend);
        playButton.setEffect(blend);
        ipLabel.setEffect(blend);

        // +++++++++++++++++++++++++++++++++++++++

        Scene scene = new Scene(grid,996,698);
        settScene(scene);
        scene.getStylesheets().add((new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "Layout.css")).toURI().toString());

        grid.getChildren().addAll(nameLabel,inputName,typeOfConnection,choiceBox,ipLabel, inputIp, playButton);

        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(996);
        primaryStage.setMaxHeight(698);
        primaryStage.show();

        // **************************
        // LAYOUT SCENE

        VBox vbox = new VBox(20);

        Label description = new Label();
        description.setText("Player connected to the match:");

        vbox.getChildren().add(description);

        Scene sceneLobby = new Scene(vbox,250,250);
        settSceneLobby(sceneLobby);

        // **************************


    }

    private void checkInput(TextField inputName, ChoiceBox<String> choiceBox, TextField inputIp, Stage primaryStage){
        if ((inputName.getText().isEmpty())){
            PopUpSceneMethod.display("Username Error", "Please insert a valid username");

        }
        else {

            try {
                if (choiceBox.getValue().equals("RMI")){
                    RemoteController remoteController = new RemoteControllerRMI(inputIp.getText(), inputName.getText(), match);
                    settRemoteController(remoteController);
                    primaryStage.setScene(sceneLobby);
                }
                else{
                    //TODO RemoteController remoteController = new RemoteControllerSocket(inputIp.toString(),1338);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                PopUpSceneMethod.display("Network error", e.getMessage());
            }
        }
    }


    public void settScene(Scene scene){
        this.scene= scene;
    }

    public void settSceneLobby(Scene scene){
        this.sceneLobby= scene;
    }

    public void settRemoteController(RemoteController remoteController){
        this.remoteController=remoteController;
    }

    public void createSceneLobby(ArrayList<String> players){
        VBox vbox = new VBox(20);

        Label description = new Label();
        description.setText("Player connected to the match:");

        vbox.getChildren().add(description);

        Scene sceneLobby = new Scene(vbox,250,250);
        settSceneLobby(sceneLobby);
    }


}