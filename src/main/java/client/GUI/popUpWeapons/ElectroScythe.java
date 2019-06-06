package client.GUI.popUpWeapons;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Match;

import java.io.File;

public class ElectroScythe extends Application {

    Match match;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Shoot");
        SplitPane splitPane = new SplitPane();

        //image
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "cards" + File.separatorChar + "ElectroScythe.png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(250);
        iv.setFitWidth(200);
        iv.setPreserveRatio(true);
        VBox vBox = new VBox();
        splitPane.getItems().addAll(iv,vBox);
        Label effectType = new Label("Effect Type: ");
        ChoiceBox<String> choiceBoxEffect = new ChoiceBox<>();
        choiceBoxEffect.getItems().add("Basic");
        choiceBoxEffect.getItems().add("Alternate");
        choiceBoxEffect.setValue("Basic");
        Button shootButton = new Button(" SHOOT ");
        shootButton.setTextFill(Color.RED);
        shootButton.setAlignment(Pos.BOTTOM_CENTER);
        shootButton.setOnAction(e -> Shoot(choiceBoxEffect));
        Label emptySpace= new Label(" ");
        Label emptySpace1= new Label(" ");
        vBox.getChildren().addAll(effectType, choiceBoxEffect,emptySpace,emptySpace1, shootButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Scene scene= new Scene(splitPane,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public void Shoot(ChoiceBox<String> choiceBoxEffect){
        //TODO aggiungere come attributo alla classe il remote controller (per poter chiamare un metodo)
        //TODO aggiungere il metodo shoot al senderControllerRMI
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
