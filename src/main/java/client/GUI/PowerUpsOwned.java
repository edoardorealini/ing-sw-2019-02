package client.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Match;
import model.player.Player;

import java.io.File;
// TODO da testare quando si riescono a far partire le partite
// NB. come invocare questa classe:
// 1) crea la classe
// 2) setMatch
// 3) setPlayerWhoClickButton
// 4) start della classe

public class PowerUpsOwned extends Application {
    Match match;
    Player playerWhoClickButton;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Short a = 0;
        Short b = 0;
        Short c = 0;
        primaryStage.setTitle("My PowerUps");
        HBox hBox = new HBox();

        if (playerWhoClickButton.getPowerUps()[0]!=null){
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + playerWhoClickButton.getPowerUps()[0].getName() + "_" + playerWhoClickButton.getPowerUps()[0].getColor() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(300);
            iv0.setFitWidth(250);
            iv0.setPreserveRatio(true);
            hBox.getChildren().add(iv0);
            a=1;
        }

        if (playerWhoClickButton.getPowerUps()[1]!=null){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + playerWhoClickButton.getPowerUps()[1].getName() + "_" + playerWhoClickButton.getPowerUps()[1].getColor() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(300);
            iv1.setFitWidth(250);
            iv1.setPreserveRatio(true);
            hBox.getChildren().add(iv1);
            b=1;
        }

        if (playerWhoClickButton.getPowerUps()[2]!=null){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "powerUpCards" + File.separatorChar + playerWhoClickButton.getPowerUps()[2].getName() + "_" + playerWhoClickButton.getPowerUps()[2].getColor() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(300);
            iv2.setFitWidth(250);
            iv2.setPreserveRatio(true);
            hBox.getChildren().add(iv2);
            c=1;
        }

        hBox.setMinHeight(350);
        hBox.setMinWidth(300);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(8);
        Scene scene = new Scene(hBox,(350*(a+b+c)+16),300);
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
