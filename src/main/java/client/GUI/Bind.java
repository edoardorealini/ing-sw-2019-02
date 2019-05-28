package client.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.awt.*;

public class Bind extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Binding");
        TextField userInput = new TextField();
        userInput.setMaxWidth(200);

        Label firstLabel = new Label("Welcome to the site");
        Label secondtLabel = new Label();

        HBox bottonText = new HBox(firstLabel,secondtLabel);
        bottonText.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10,userInput,bottonText);
        vbox.setAlignment(Pos.CENTER);

        secondtLabel.textProperty().bind(userInput.textProperty());

        Scene scene = new Scene(vbox,300,300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
