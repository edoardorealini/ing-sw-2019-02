package client.GUI;

import com.sun.javafx.scene.control.SelectedCellsMap;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Match;
import model.player.Player;

import java.util.*;



public class Ranking extends Application {
    Match match;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(" Final Ranking ");

        VBox vBox = new VBox(6);

        ArrayList<Player> arraySorted = new ArrayList<>(match.getPlayers());

        Collections.sort(arraySorted, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                int points1 = o1.getPoints();
                int points2 = o2.getPoints();

                if (points1 == points2)
                    return 0;
                else if (points1 < points2)
                    return 1;
                else
                    return -1;
            }
        });

        //Collections.reverse(arraySorted);

        Label player1 = new Label(" ");
        Label player2 = new Label(" ");
        Label player3 = new Label(" ");
        Label player4 = new Label(" ");
        Label player5 = new Label(" ");
        vBox.getChildren().addAll(player1,player2,player3,player4,player5);
        ArrayList<Label> listPlayersLabel = new ArrayList<>();
        listPlayersLabel.add(player1);
        listPlayersLabel.add(player2);
        listPlayersLabel.add(player3);
        listPlayersLabel.add(player4);
        listPlayersLabel.add(player5);

        for (int i=0; i<match.getPlayers().size();i++){
            listPlayersLabel.get(i).setText(" " + (i+1) + ". Player " + arraySorted.get(i).getNickname() + " -->  total score : " + arraySorted.get(i).getPoints());
        }

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,300,300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
