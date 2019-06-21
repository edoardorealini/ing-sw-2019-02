package client.GUI;

import client.remoteController.SenderClientRemoteController;
import javafx.application.Application;
import javafx.geometry.Insets;
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
    private Player playerClicked;
    private Boolean frenzyActive;


    /*
        FOR TEST USE  ONLY,  REMEMBER TO REMOVE BEFORE PACKAGING THE JAR
    */
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        frenzyActive = false;

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Life Player '"+playerClicked.getNickname()+"'");
        stage.setMinWidth(410);
        stage.setMinHeight(210);

        StackPane stackPane = new StackPane();
        
        // normal board
        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeBoardNormal" + File.separatorChar + match.getPlayer(playerClicked.getNickname()).getId() + ".png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);

                // frenzy board
                File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                        + File.separatorChar + "resources" + File.separatorChar + "lifeBoards" + File.separatorChar + "LifeboardAdrenalina" + File.separatorChar + match.getPlayer(playerClicked.getNickname()).getId() + "F" + ".png");
                Image image1 = new Image(file1.toURI().toString());
                ImageView iv1 = new ImageView(image1);
                iv1.setFitHeight(200);
                iv1.setFitWidth(400);
                iv1.setPreserveRatio(true);


        stackPane.setAlignment(Pos.CENTER);

        // LIFE
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

        // TARGET
        Circle target1 = new Circle(40,40,5);
        target1.setFill(Color.TRANSPARENT);
        Circle target2 = new Circle(40,40,5);
        target2.setFill(Color.TRANSPARENT);
        Circle target3 = new Circle(40,40,5);
        target3.setFill(Color.TRANSPARENT);
        Circle target4 = new Circle(40,40,5);
        target4.setFill(Color.TRANSPARENT);
        Circle target5 = new Circle(40,40,5);
        target5.setFill(Color.TRANSPARENT);
        Circle target6 = new Circle(40,40,5);
        target6.setFill(Color.TRANSPARENT);
        Circle target7 = new Circle(40,40,5);
        target7.setFill(Color.TRANSPARENT);
        Circle target8 = new Circle(40,40,5);
        target8.setFill(Color.TRANSPARENT);
        Circle target9 = new Circle(40,40,5);
        target9.setFill(Color.TRANSPARENT);
        Circle target10 = new Circle(40,40,5);
        target10.setFill(Color.TRANSPARENT);
        Circle target11 = new Circle(40,40,5);
        target11.setFill(Color.TRANSPARENT);
        Circle target12 = new Circle(40,40,5);
        target12.setFill(Color.TRANSPARENT);
        Circle target13 = new Circle(40,40,5);
        target13.setFill(Color.TRANSPARENT);
        Circle target14 = new Circle(40,40,5);
        target14.setFill(Color.TRANSPARENT);
        Circle target15 = new Circle(40,40,5);
        target15.setFill(Color.TRANSPARENT);
        ArrayList<Circle> targetList = new ArrayList<>();
        targetList.add(target1);
        targetList.add(target2);
        targetList.add(target3);
        targetList.add(target4);
        targetList.add(target5);
        targetList.add(target6);
        targetList.add(target7);
        targetList.add(target8);
        targetList.add(target9);
        targetList.add(target10);
        targetList.add(target11);
        targetList.add(target12);
        targetList.add(target13);
        targetList.add(target14);
        targetList.add(target15);


        for (int i=0; i< match.getPlayer(playerClicked.getNickname()).getBoard().getMarks().size(); i++){
            switch (match.getPlayer(playerClicked.getNickname()).getBoard().getMarks().get(i)){
                case 0:
                    targetList.get(i).setFill(Color.YELLOW);
                    break;
                case 1:
                    targetList.get(i).setFill(Color.GREEN);
                    break;
                case 2:
                    targetList.get(i).setFill(Color.GRAY);
                    break;
                case 3:
                    targetList.get(i).setFill(Color.BLUE);
                    break;
                case 4:
                    targetList.get(i).setFill(Color.VIOLET);
                    break;
                default:
                    targetList.get(i).setFill(Color.TRANSPARENT);
                    break;
            }
        }

        HBox lifeHbox = new HBox(8.3);
        lifeHbox.getChildren().addAll(empty1,empty2,circle1, circle2,circle3,circle4,circle5,circle6,circle7,circle8,circle9,circle10,circle11,circle12);
        lifeHbox.setAlignment(Pos.CENTER_LEFT);
        HBox markHbox = new HBox(3);
        markHbox.getChildren().addAll(target1,target2,target3,target4,target5,target6,target7,target8,target9,target10,target11,target12,target13,target14,target15);
        markHbox.setAlignment(Pos.TOP_LEFT);
        markHbox.setPadding(new Insets(50, 0, 0, 200));
        stackPane.setStyle("-fx-background-color: #191a17");
        if (frenzyActive && (match.getPlayer(playerClicked.getNickname()).getPlayerMoodFrenzy())){
            stackPane.getChildren().add(iv1);
        }
        else stackPane.getChildren().add(iv0);

        stackPane.getChildren().addAll(lifeHbox,markHbox);

        Scene scene = new Scene(stackPane);
        stage.setMaxHeight(210);
        stage.setMaxWidth(410);
        stage.setScene(scene);
        stage.showAndWait();

    }

    public void setFrenzyActive(Boolean frenzyActive) {
        this.frenzyActive = frenzyActive;
    }

    public void setPlayerClicked(Player playerClicked) {
        this.playerClicked = playerClicked;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
