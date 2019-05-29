package client.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
        Image image = new Image("https://picsum.photos/id/117/200/300");
        */

        //qui sotto ti metto il modo che devi usare per prendere l'immagine da file
        //per prima cosa devi aprire il file con new File, poi renderlo leggibile (.toURI().toString())

        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "AdrenalineBackground.png");
        Image image = new Image(file.toURI().toString());

        GridPane grid = new GridPane();
        grid.getChildren().add(new ImageView(image));

        Scene scene = new Scene(grid,996,698);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}