package client.GUI;

import client.remoteController.SenderClientRemoteController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;

import java.io.File;

public class LifeBoard extends Application {
    private Match match;
    SenderClientRemoteController remoteController;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        //stage.initModality(Modality.APPLICATION_MODAL);
       // stage.setTitle("Life Player '"+remoteController.getNickname()+"'");
        stage.setTitle("Prova");
        stage.setMinWidth(410);
        stage.setMinHeight(210);

        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + /*match.getPlayer(remoteController.getNickname()).getId() +*/ "1.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

        stackPane.setAlignment(Pos.CENTER);

        Circle empty1 = new Circle(40,40,7);
        empty1.setFill(Color.TRANSPARENT);

        Circle empty2 = new Circle(40,40,7);
        empty2.setFill(Color.TRANSPARENT);

        Circle yCircle = new Circle(40,40,7);
        yCircle.setFill(Color.YELLOW);
        Circle rCircle = new Circle(40,40,7);
        rCircle.setFill(Color.YELLOW);
        Circle aCircle = new Circle(40,40,7);
        aCircle.setFill(Color.YELLOW);
        Circle bCircle = new Circle(40,40,7);
        bCircle.setFill(Color.YELLOW);
        Circle cCircle = new Circle(40,40,7);
        cCircle.setFill(Color.YELLOW);
        Circle dCircle = new Circle(40,40,7);
        dCircle.setFill(Color.YELLOW);
        Circle eCircle = new Circle(40,40,7);
        eCircle.setFill(Color.YELLOW);
        Circle fCircle = new Circle(40,40,7);
        fCircle.setFill(Color.YELLOW);
        Circle qCircle = new Circle(40,40,7);
        qCircle.setFill(Color.YELLOW);
        Circle wCircle = new Circle(40,40,7);
        wCircle.setFill(Color.YELLOW);
        Circle tCircle = new Circle(40,40,7);
        tCircle.setFill(Color.YELLOW);
        Circle uCircle = new Circle(40,40,7);
        uCircle.setFill(Color.YELLOW);

        HBox testLife = new HBox(8.3);
        testLife.getChildren().addAll(empty1,empty2,yCircle, rCircle,aCircle,bCircle,cCircle,dCircle,eCircle,fCircle,qCircle,wCircle,tCircle,uCircle);
        testLife.setAlignment(Pos.CENTER_LEFT);
        stackPane.setStyle("-fx-background-color: #191a17");
        stackPane.getChildren().addAll(iv0, testLife);

        Scene scene = new Scene(stackPane);
        stage.setMaxHeight(210);
        stage.setMaxWidth(410);
        stage.setScene(scene);
        //stage.showAndWait();
        stage.show();
    }

    public void setRemoteController(SenderClientRemoteController remoteController) {
        this.remoteController = remoteController;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
