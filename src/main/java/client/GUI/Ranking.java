package client.GUI;

import com.sun.javafx.scene.control.SelectedCellsMap;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
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
            listPlayersLabel.get(i).setText("Player "+match.getPlayers().get(i).getNickname()+"->  total score : "+match.getPlayers().get(i).getPoints());
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
