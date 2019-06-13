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
import model.player.Player;

import java.util.*;
import java.io.File;
import java.lang.reflect.Array;

public class LifeBoard extends Application {
    private Match match;
    Player playerClicked;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player '"+playerClicked.getNickname()+"'");
        stage.setMinWidth(410);
        stage.setMinHeight(210);

        StackPane stackPane = new StackPane();

        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + match.getPlayer(playerClicked.getNickname()).getId() + ".png");
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

        Circle circle1 = new Circle(40,40,7);
        Circle circle2 = new Circle(40,40,7);
        Circle circle3 = new Circle(40,40,7);
        Circle circle4 = new Circle(40,40,7);
        Circle circle5 = new Circle(40,40,7);
        Circle circle6 = new Circle(40,40,7);
        Circle circle7 = new Circle(40,40,7);
        Circle circle8 = new Circle(40,40,7);
        Circle circle9 = new Circle(40,40,7);
        Circle circle10 = new Circle(40,40,7);
        Circle circle11 = new Circle(40,40,7);
        Circle circle12 = new Circle(40,40,7);
        ArrayList<Circle> listCircle = new ArrayList<>();
        listCircle.add(circle1);
        listCircle.add(circle2);
        listCircle.add(circle3);
        listCircle.add(circle4);
        listCircle.add(circle5);
        listCircle.add(circle6);
        listCircle.add(circle7);
        listCircle.add(circle8);
        listCircle.add(circle9);
        listCircle.add(circle10);
        listCircle.add(circle11);
        listCircle.add(circle12);
        // giocatore 0 giallo
        // giocatore 1 verde
        // giocatore 2 grigio
        // giocatore 3 blu
        // giocatore 4 viola

        for (int i=0; i<listCircle.size(); i++){
            switch (match.getPlayer(playerClicked.getNickname()).getBoard().getLifePoints()[i]){
                case 0:
                    listCircle.get(i).setFill(Color.YELLOW);
                    break;
                case 1:
                    listCircle.get(i).setFill(Color.GREEN);
                    break;
                case 2:
                    listCircle.get(i).setFill(Color.GRAY);
                    break;
                case 3:
                    listCircle.get(i).setFill(Color.BLUE);
                    break;
                case 4:
                    listCircle.get(i).setFill(Color.VIOLET);
                    break;
                default:
                    listCircle.get(i).setFill(Color.TRANSPARENT);
                    break;
            }
        }

        Circle a = new Circle(40,40,7);
        a.setFill(Color.YELLOW);
        Circle b = new Circle(40,40,7);
        b.setFill(Color.YELLOW);
        Circle c = new Circle(40,40,7);
        c.setFill(Color.YELLOW);
        Circle d = new Circle(40,40,7);
        d.setFill(Color.YELLOW);
        Circle e = new Circle(40,40,7);
        e.setFill(Color.YELLOW);
        Circle f = new Circle(40,40,7);
        f.setFill(Color.YELLOW);
        Circle g = new Circle(40,40,7);
        g.setFill(Color.YELLOW);
        Circle h = new Circle(40,40,7);
        h.setFill(Color.YELLOW);
        Circle i = new Circle(40,40,7);
        i.setFill(Color.YELLOW);
        Circle l = new Circle(40,40,7);
        l.setFill(Color.YELLOW);
        Circle m = new Circle(40,40,7);
        m.setFill(Color.YELLOW);
        Circle n = new Circle(40,40,7);
        n.setFill(Color.YELLOW);
        Circle o = new Circle(40,40,7);
        o.setFill(Color.YELLOW);
        Circle p = new Circle(40,40,7);
        p.setFill(Color.YELLOW);


        HBox lifeHbox = new HBox(8.3);
        lifeHbox.getChildren().addAll(empty1,empty2,circle1, circle2,circle3,circle4,circle5,circle6,circle7,circle8,circle9,circle10,circle11,circle12);
        lifeHbox.setAlignment(Pos.CENTER_LEFT);
        HBox markHbox = new HBox(8.3);
        markHbox.getChildren().addAll(a,b,c,d,e,f,g,h,i,l,m,n,o,p);
        markHbox.setAlignment(Pos.TOP_LEFT);
        stackPane.setStyle("-fx-background-color: #191a17");
        stackPane.getChildren().addAll(iv0, lifeHbox,markHbox);

        Scene scene = new Scene(stackPane);
        stage.setMaxHeight(210);
        stage.setMaxWidth(410);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void setPlayerClicked(Player playerClicked) {
        this.playerClicked = playerClicked;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
