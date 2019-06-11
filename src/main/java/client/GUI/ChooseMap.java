package client.GUI;

import client.remoteController.SenderClientRemoteController;
import com.sun.prism.paint.Gradient;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

public class ChooseMap extends Application {

    private SenderClientRemoteController remoteController;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void setRemoteController(SenderClientRemoteController remoteController) {
        this.remoteController = remoteController;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Choose map");
        primaryStage.setOnCloseRequest(event -> {});

        //splitting the pane vertically
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        //importing the image
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "ChooseMapBackground.png");
        Image image = new Image(file.toURI().toString());


        //if we need to resize the image
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(650);
        iv.setFitWidth(864);
        iv.setPreserveRatio(true);


        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        VBox vbox = new VBox();

        // the next line changes the background color, but unfortunately the divider is still visible and white
        //  splitPane.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        vbox.getChildren().addAll(hBox1, hBox2);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        splitPane.getItems().addAll(iv, vbox);


        Label chooseMapText = new Label("Select the map:");
        ChoiceBox<Integer> selectMap = new ChoiceBox<>();

        Label numberOfSkulls = new Label("Select the number of skulls:");
        ChoiceBox<Integer> nSkulls = new ChoiceBox<>();

        Label ready = new Label("Are you ready?");


        // chooseMapText.setTextFill(Color.RED);
        // numberOfSkulls.setTextFill(Color.RED);
        // ready.setTextFill(Color.RED);

        Button play = new Button(" PLAY ");
        play.setTextFill(Color.BLUE);
        play.setAlignment(Pos.CENTER);
        play.setOnAction(event -> {
                try {
                    remoteController.setSkulls(nSkulls.getValue());
                    remoteController.buildMap(selectMap.getValue());

                } catch (Exception e) {
                    PopUpSceneMethod.display("Something went wrong", e.getMessage());
                }});

        //TODO set on action


        //filling the choice boxes
        selectMap.getItems().addAll(1, 2, 3, 4);
        selectMap.setValue(1);
        nSkulls.getItems().addAll(5, 6, 7, 8);
        nSkulls.setValue(5);


        hBox1.getChildren().addAll(chooseMapText, numberOfSkulls, ready);
        hBox2.getChildren().addAll(selectMap, nSkulls, play);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox1.setSpacing(50);
        hBox2.setSpacing(120);

        Scene scene = new Scene(splitPane,864,750);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void closePrimaryStage() {
        this.primaryStage.close();
    }

}
