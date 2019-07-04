package client.GUI;

import client.remoteController.SenderClientControllerRMI;
import client.remoteController.SenderClientRemoteController;
import commons.FileLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Match;

import java.io.File;


public class FirstPage extends Application implements Runnable{

    Stage primaryStage;
    SenderClientRemoteController remoteController;
    Scene sceneLobby;
    Scene scene;
    Match match;
    String[] playersBind = {"-","-","-","-","-"};

    //lista di Label che sono da passare nel metodo
    Label player1;
    Label player2;
    Label player3;
    Label player4;
    Label player5;


    public void run() {
        try {
            launch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Adrenaline");
        File icon = FileLoader.getResourceAsFile("icon" + "/" + "Icon.png");

        Image image0 = new Image(icon.toURI().toString());
        primaryStage.getIcons().add(image0);
        setPrimaryStage(primaryStage);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        this.match= new Match();

        GridPane grid = new GridPane();
        grid.setVgap(10); // sapzio verticale tra boxes
        grid.setHgap(8); // spazio orizzontale tra boxes

        File file = FileLoader.getResourceAsFile("AdrenalineBackground.png");

         BackgroundImage myBI= new BackgroundImage(new Image(file.toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        grid.setBackground(new Background(myBI));

        // inserisco i nodi
        Label nameLabel = new Label("NICKNAME: ");
        GridPane.setConstraints(nameLabel, 50,18);

        TextField inputName = new TextField();
        inputName.setPromptText("Name");
        GridPane.setConstraints(inputName, 50,19);


        Label ipLabel = new Label("IP: ");
        GridPane.setConstraints(ipLabel, 50,20);

        TextField inputIp = new TextField();
        inputIp.setPromptText("Insert IP here");
        GridPane.setConstraints(inputIp, 50,21);

        Label numberOfPort = new Label("PORT: ");
        GridPane.setConstraints(numberOfPort,50,22);

        TextField numerPortText = new TextField();
        numerPortText.setText("1338");
        GridPane.setConstraints(numerPortText,50,23);

        inputIp.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER))
                checkInput(inputName,numerPortText,inputIp,primaryStage);
        });

        numerPortText.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER))
                checkInput(inputName,numerPortText,inputIp,primaryStage);
        });

        Button playButton = new Button();
        playButton.setText("  PLAY  ");
        playButton.getStyleClass().add("button-play");
        GridPane.setConstraints(playButton,50,24);
        playButton.setOnAction(e ->  checkInput(inputName,numerPortText,inputIp,primaryStage)); /*
          {
            try {
                GeneralWeaponPopUp fp = new GeneralWeaponPopUp();
               fp.setMatch(this.getMatch());
               fp.setWeapon(match.getWeaponDeck().getWeapon(WeaponName.THOR));
               fp.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });  //checkInput(inputName,choiceBox,inputIp,primaryStage));
        // */
        // ++++++++++++++++++++++++++++++++++
        // codice per cambiare font
        nameLabel.setTextFill(Color.YELLOWGREEN);
        nameLabel.setStyle("-fx-font-weight: bold");
        numberOfPort.setTextFill(Color.YELLOWGREEN);
        numberOfPort.setStyle("-fx-font-weight: bold");
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
        numberOfPort.setEffect(blend);
        playButton.setEffect(blend);
        ipLabel.setEffect(blend);

        // +++++++++++++++++++++++++++++++++++++++

        Scene scene = new Scene(grid,996,698);

        File styleSheet = FileLoader.getResourceAsFile("Layout.css");
        scene.getStylesheets().add(styleSheet.toURI().toString());

        grid.getChildren().addAll(nameLabel,inputName, ipLabel, inputIp, numberOfPort,numerPortText, playButton);

        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(996);
        primaryStage.setMaxHeight(698);
        primaryStage.show();

        // **************************
        // LAYOUT SCENE

        VBox vbox = new VBox(8);
        vbox.setPadding(new Insets(10, 50, 50, 50));

        Label description = new Label();
        description.setText("Player connected to the match:");
        vbox.getChildren().add(description);

        // ***************************
        // lista dei players (inizialmente stringhe vuote)
        this.player1 = new Label();
        player1.setText(playersBind[0]);
        vbox.getChildren().add(player1);

        this.player2 = new Label();
        player2.setText(playersBind[1]);
        vbox.getChildren().add(player2);

        this.player3 = new Label();
        player3.setText(playersBind[2]);
        vbox.getChildren().add(player3);

        this.player4 = new Label();
        player4.setText(playersBind[3]);
        vbox.getChildren().add(player4);

        this.player5 = new Label();
        player5.setText(playersBind[4]);
        vbox.getChildren().add(player5);

        Button backButton = new Button(" Back ");
        backButton.setOnAction(e -> {
            backButtonMethod();
            primaryStage.setScene(scene);
        });
        vbox.getChildren().add(backButton);

        Scene sceneLobby = new Scene(vbox,300,350);
        settSceneLobby(sceneLobby);

        // **************************


    }

    private void checkInput(TextField inputName, TextField numerPortText, TextField inputIp, Stage primaryStage){
        if ((inputName.getText().isEmpty())){
            PopUpSceneMethod.display("Username Error", "Please insert a valid username");
        }
        else {
            try {
                    // TODO aggiungere numero porta (numerPortText)
                    SenderClientRemoteController remoteController = new SenderClientControllerRMI(inputIp.getText(), Integer.parseInt(numerPortText.getText()), inputName.getText(), match, this);
                    settRemoteController(remoteController);
                    primaryStage.setScene(sceneLobby);
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

    public void settRemoteController(SenderClientRemoteController remoteController){
        this.remoteController=remoteController;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void refreshPlayersInLobby(){
        for (int i=0;(i < 5);i++){
            playersBind[i] = "-";
        }
        for (int i=0; (i<match.getPlayers().size()) && (i < 5);i++){
            playersBind[i] = match.getPlayers().get(i).getNickname();
        }

        player1.setText(playersBind[0]);
        player2.setText(playersBind[1]);
        player3.setText(playersBind[2]);
        player4.setText(playersBind[3]);
        player5.setText(playersBind[4]);
    }

    public void backButtonMethod(){
        // disconnette ma non chiude first page
        remoteController.disconnectPlayer();
    }

    public void closePage(){
        if (remoteController!=null ){
            remoteController.disconnectPlayer();
            primaryStage.close();
        }
        else primaryStage.close();

    }

    public Match getMatch() {
        return match;
    }

    public void closePrimaryStage() {
        primaryStage.close();
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}