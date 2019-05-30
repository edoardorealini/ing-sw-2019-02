package client.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javafx.scene.image.Image;

import java.io.File;

public class ImageViewer extends Application {

    public static void main(String[] args) {
        launch(args);
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

        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "AdrenalineBackground.png");
        Image image = new Image(file.toURI().toString());

        BackgroundImage myBI= new BackgroundImage(new Image(file.toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
//then you set to your node
        grid.setBackground(new Background(myBI));

        Label nameLabel = new Label("Username: ");
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setStyle("-fx-font-weight: bold");
        GridPane.setConstraints(nameLabel, 50,20);

        TextField inputName = new TextField();
        inputName.setPromptText("Name");
        GridPane.setConstraints(inputName, 50,21);



        Scene scene = new Scene(grid,996,698);

        grid.getChildren().addAll(nameLabel,inputName);
        primaryStage.setScene(scene);
        primaryStage.setMaxWidth(996);
        primaryStage.setMaxHeight(698);
        primaryStage.show();
    }
}