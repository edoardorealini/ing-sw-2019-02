package client.GUI;

import com.sun.javafx.scene.SceneEventDispatcher;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
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
        VBox mainVbox = new VBox();
        SplitPane splitPane = new SplitPane();
        //left
        VBox vBoxLife = new VBox();
        splitPane.getItems().add(vBoxLife);
        //centre
        //image (map)
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "maps" + File.separatorChar + "map" + match.getMap().getMapID() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(500);
        iv.setFitWidth(500);
        iv.setPreserveRatio(true);
        splitPane.getItems().add(iv);
        //right
        VBox vBoxRight = new VBox();
        Button moveButton = new Button(" MOVE ");
        //moveButton.setOnAction(e -> MoveButton());
        Button grabButton = new Button(" GRAB ");
        //grabButton.setOnAction(e -> GrabButton());
        Button shootButton = new Button(" SHOOT ");
        //shootButton.setOnAction(e -> ShootButton());
        vBoxRight.getChildren().addAll(moveButton,grabButton,shootButton);
        splitPane.getItems().add(vBoxRight);

        //
        mainVbox.getChildren().add(splitPane);

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMatch(Match match) {
        this.match = match;
    }

}


