package client.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.image.Image ;
import java.io.File;

public class ImageViewer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Adrenaline");

        Image image = new Image(File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "Adrenaline_1024x1024@2x.png");
        GridPane grid = new GridPane();
        grid.getChildren().add(new ImageView(image));

        Scene scene = new Scene(grid,1024,1024);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}