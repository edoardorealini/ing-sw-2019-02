package client.GUI;

import com.sun.javafx.scene.SceneEventDispatcher;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Match;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;

public class MainPage extends Application {
    private Match match;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Adrenaline");
        /*// load images
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" +File.separatorChar + "Vita1.png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        StackPane stackPane = new StackPane(iv);
        stackPane.setMaxHeight(100);
        stackPane.setMaxWidth(20);
        iv.fitWidthProperty().bind(stackPane.widthProperty());
        VBox vBox = new VBox();
        vBox.getChildren().add(stackPane);
        Scene scene = new Scene(vBox,1400,825);
        mainStage.setScene(scene);
        mainStage.setScene(scene);
        mainStage.sizeToScene();
        mainStage.show();  */
        

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMatch(Match match) {
        this.match = match;
    }

}


