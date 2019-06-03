package client.GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Match;

import java.io.File;

public class MainPage extends Application {
    private Match match;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Adrenaline");
        BorderPane borderPane = new BorderPane();
        VBox vbox= new VBox();
        borderPane.setLeft(vbox);
        GridPane gridPane = new GridPane();
        borderPane.setCenter(gridPane);


        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "Vita1.png");

        BackgroundImage myBI= new BackgroundImage(new Image(file.toURI().toString()),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        vbox.setBackground(new Background(myBI));

        Scene scene = new Scene(borderPane,996,698);
        mainStage.setScene(scene);
        mainStage.setMaxWidth(996);
        mainStage.setMaxHeight(698);
        mainStage.show();
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}


