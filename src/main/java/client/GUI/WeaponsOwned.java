package client.GUI;

import commons.FileLoader;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Cell;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Match;
import model.player.Player;

import java.io.File;
// NB. come invocare questa classe:
// 1) crea la classe
// 2) setMatch
// 3) setPlayerWhoClickButton
// 4) start della classe

public class WeaponsOwned extends Application {
    Match match;
    Player playerWhoClickButton;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Short a = 0;
        Short b = 0;
        Short c = 0;
        primaryStage.setTitle("My Weapons");
        HBox hBoxWeapon = new HBox();

        if (playerWhoClickButton.getWeapons()[0]!=null){
            File weapon0 = FileLoader.getResourceAsFile( "weapons" + "/" + playerWhoClickButton.getWeapons()[0].getName() + ".png");
            Image image0 = new Image(weapon0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(350);
            iv0.setFitWidth(300);
            iv0.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv0);
            a=1;
        }

        if (playerWhoClickButton.getWeapons()[1]!=null){
            File weapon1 = FileLoader.getResourceAsFile( "weapons" + "/" + playerWhoClickButton.getWeapons()[1].getName() + ".png");
            Image image1 = new Image(weapon1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(350);
            iv1.setFitWidth(300);
            iv1.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv1);
            b=1;
        }

        if (playerWhoClickButton.getWeapons()[2]!=null){
            File weapon2 = FileLoader.getResourceAsFile( "weapons" + "/" + playerWhoClickButton.getWeapons()[2].getName() + ".png");
            Image image2 = new Image(weapon2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(350);
            iv2.setFitWidth(300);
            iv2.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv2);
            c=1;
        }

        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(300);
        hBoxWeapon.setAlignment(Pos.CENTER);
        hBoxWeapon.setSpacing(8);
        Scene scene = new Scene(hBoxWeapon,(300*(a+b+c)),400);
        primaryStage.setScene(scene);
        primaryStage.show();



    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setPlayerWhoClickButton(Player playerWhoClickButton) {
        this.playerWhoClickButton = playerWhoClickButton;
    }
}
