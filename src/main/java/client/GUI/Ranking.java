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

        // ++++++++++++++++++++++++++++++++++
        // codice per cambiare font
        player1.setTextFill(Color.YELLOWGREEN);
        player1.setStyle("-fx-font-weight: bold");
        player2.setTextFill(Color.YELLOWGREEN);
        player2.setStyle("-fx-font-weight: bold");
        player3.setTextFill(Color.YELLOWGREEN);
        player3.setStyle("-fx-font-weight: bold");
        player4.setTextFill(Color.YELLOWGREEN);
        player4.setStyle("-fx-font-weight: bold");
        player5.setTextFill(Color.YELLOWGREEN);
        player5.setStyle("-fx-font-weight: bold");

        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        player1.setEffect(blend);
        player2.setEffect(blend);
        player3.setEffect(blend);
        player4.setEffect(blend);
        player5.setEffect(blend);

        // +++++++++++++++++++++++++++++++++++++++

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox,300,300);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
