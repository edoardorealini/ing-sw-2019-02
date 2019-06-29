package client.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Match;

import java.io.File;
import java.util.ArrayList;

public class Skulls extends Application {
    private Match match;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(" Skulls ");

        primaryStage.setMinWidth(410);
        primaryStage.setMinHeight(210);


        StackPane stackPane = new StackPane();
        // normal board
        File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "skulls" + File.separatorChar + "skullsBoard.png");
        Image image0 = new Image(file0.toURI().toString());
        ImageView iv0 = new ImageView(image0);
        iv0.setFitHeight(200);
        iv0.setFitWidth(400);
        iv0.setPreserveRatio(true);
        stackPane.getChildren().add(iv0);
        stackPane.setAlignment(Pos.CENTER);

        Circle circle1 = new Circle(40,40,7);
        Circle circle2 = new Circle(40,40,7);
        Circle circle3 = new Circle(40,40,7);
        Circle circle4 = new Circle(40,40,7);
        Circle circle5 = new Circle(40,40,7);
        Circle circle6 = new Circle(40,40,7);
        Circle circle7 = new Circle(40,40,7);
        Circle circle8 = new Circle(40,40,7);

        Circle circle1down = new Circle(40,40,7);
        Circle circle2down = new Circle(40,40,7);
        Circle circle3down = new Circle(40,40,7);
        Circle circle4down = new Circle(40,40,7);
        Circle circle5down = new Circle(40,40,7);
        Circle circle6down = new Circle(40,40,7);
        Circle circle7down = new Circle(40,40,7);
        Circle circle8down = new Circle(40,40,7);

        /*ArrayList<Circle> listCircle = new ArrayList<>();
        listCircle.add(circle1);
        listCircle.add(circle2);
        listCircle.add(circle3);
        listCircle.add(circle4);
        listCircle.add(circle5);
        listCircle.add(circle6);
        listCircle.add(circle7);
        listCircle.add(circle8);
        */


        HBox hBox = new HBox(27);
        VBox vBox1 = new VBox(3);
        VBox vBox2 = new VBox(3);
        VBox vBox3 = new VBox(3);
        VBox vBox4 = new VBox(3);
        VBox vBox5 = new VBox(3);
        VBox vBox6 = new VBox(3);
        VBox vBox7 = new VBox(3);
        VBox vBox8 = new VBox(3);


        circle1.setFill(Color.TRANSPARENT);
        circle2.setFill(Color.TRANSPARENT);
        circle3.setFill(Color.TRANSPARENT);
        circle4.setFill(Color.TRANSPARENT);
        circle5.setFill(Color.TRANSPARENT);
        circle6.setFill(Color.TRANSPARENT);
        circle7.setFill(Color.TRANSPARENT);
        circle8.setFill(Color.TRANSPARENT);

        vBox1.getChildren().addAll(circle1);
        vBox2.getChildren().addAll(circle2);
        vBox3.getChildren().addAll(circle3);
        vBox4.getChildren().addAll(circle4);
        vBox5.getChildren().addAll(circle5);
        vBox6.getChildren().addAll(circle6);
        vBox7.getChildren().addAll(circle7);
        vBox8.getChildren().addAll(circle8);


        circle1down.setFill(Color.TRANSPARENT);
        circle2down.setFill(Color.TRANSPARENT);
        circle3down.setFill(Color.TRANSPARENT);
        circle4down.setFill(Color.TRANSPARENT);
        circle5down.setFill(Color.TRANSPARENT);
        circle6down.setFill(Color.TRANSPARENT);
        circle7down.setFill(Color.TRANSPARENT);
        circle8down.setFill(Color.TRANSPARENT);

        vBox1.getChildren().addAll(circle1down);
        vBox2.getChildren().addAll(circle2down);
        vBox3.getChildren().addAll(circle3down);
        vBox4.getChildren().addAll(circle4down);
        vBox5.getChildren().addAll(circle5down);
        vBox6.getChildren().addAll(circle6down);
        vBox7.getChildren().addAll(circle7down);
        vBox8.getChildren().addAll(circle8down);

        hBox.getChildren().addAll(vBox1,vBox2,vBox3,vBox4,vBox5,vBox6,vBox7,vBox8);
        hBox.setPadding(new Insets(80, 0, 0, 29));
        stackPane.setStyle("-fx-background-color: #191a17");


        ArrayList<Circle> arrayCircleTop = new ArrayList<>();
        arrayCircleTop.add(circle1);
        arrayCircleTop.add(circle2);
        arrayCircleTop.add(circle3);
        arrayCircleTop.add(circle4);
        arrayCircleTop.add(circle5);
        arrayCircleTop.add(circle6);
        arrayCircleTop.add(circle7);
        arrayCircleTop.add(circle8);

        ArrayList<Circle> arrayCircleDown = new ArrayList<>();
        arrayCircleDown.add(circle1down);
        arrayCircleDown.add(circle2down);
        arrayCircleDown.add(circle3down);
        arrayCircleDown.add(circle4down);
        arrayCircleDown.add(circle5down);
        arrayCircleDown.add(circle6down);
        arrayCircleDown.add(circle7down);
        arrayCircleDown.add(circle8down);

        // inizializzo i pallini rossi
        for (int i = 1; i<=match.getKillShotTrack().getTotalSkulls(); i++){
            arrayCircleTop.get(arrayCircleTop.size()-i).setFill(Color.RED);
        }

        System.out.println(match.getKillShotTrack().getMortalShots().toString());
        // giocatore 0 giallo
        // giocatore 1 verde
        // giocatore 2 grigio
        // giocatore 3 blu
        // giocatore 4 viola
        int i=0;
        int j=0;
        int indexOfSkull = 8 - (match.getKillShotTrack().getTotalSkulls());
        if (indexOfSkull>0 && indexOfSkull<8) {
            while (i < match.getKillShotTrack().getMortalShots().size()) {
                if (j == 0) {
                    switch (match.getKillShotTrack().getMortalShots().get(i)) {
                        case 0:
                            arrayCircleTop.get(indexOfSkull).setFill(Color.YELLOW);
                            i++;
                            j = 1;
                            break;
                        case 1:
                            arrayCircleTop.get(indexOfSkull).setFill(Color.GREEN);
                            i++;
                            j = 1;
                            break;
                        case 2:
                            arrayCircleTop.get(indexOfSkull).setFill(Color.LIGHTGRAY);
                            i++;
                            j = 1;
                            break;
                        case 3:
                            arrayCircleTop.get(indexOfSkull).setFill(Color.BLUE);
                            i++;
                            j = 1;
                            break;
                        case 4:
                            arrayCircleTop.get(indexOfSkull).setFill(Color.VIOLET);
                            i++;
                            j = 1;
                            break;
                        case 9:
                            j = 0;
                            i++;
                            break;
                    }
                } else {
                    switch (match.getKillShotTrack().getMortalShots().get(i)) {
                        case 0:
                            arrayCircleDown.get(indexOfSkull).setFill(Color.YELLOW);
                            j = 0;
                            i++;
                            indexOfSkull++;
                            break;
                        case 1:
                            arrayCircleDown.get(indexOfSkull).setFill(Color.GREEN);
                            j = 0;
                            i++;
                            indexOfSkull++;
                            break;
                        case 2:
                            arrayCircleDown.get(indexOfSkull).setFill(Color.LIGHTGRAY);
                            j = 0;
                            i++;
                            indexOfSkull++;
                            break;
                        case 3:
                            arrayCircleDown.get(indexOfSkull).setFill(Color.BLUE);
                            j = 0;
                            i++;
                            indexOfSkull++;
                            break;
                        case 4:
                            arrayCircleDown.get(indexOfSkull).setFill(Color.VIOLET);
                            j = 0;
                            i++;
                            indexOfSkull++;
                            break;
                        case 9:
                            j = 0;
                            i++;
                            indexOfSkull++;
                    }
                }
            }
        }

        stackPane.getChildren().addAll(hBox);
        Scene scene = new Scene(stackPane);
        primaryStage.setMaxHeight(210);
        primaryStage.setMaxWidth(410);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
