package client.GUI.popUpWeapons;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Match;

public class RazzoTermico extends Application {
    Match match;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Shoot");
        SplitPane splitPane = new SplitPane();

        Label imageFake = new Label("      Image HERE     ");
        imageFake.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        splitPane.getItems().addAll(imageFake,vBox);
        Label effectType = new Label("Effect Type: ");
        ChoiceBox<String> choiceBoxEffect = new ChoiceBox<>();
        choiceBoxEffect.getItems().add("Basic");
        choiceBoxEffect.setValue("Basic");
        Label target = new Label("Chose the target: ");
        ChoiceBox<String> choiceBoxName = new ChoiceBox<>();
        fillChoiceBoxName(choiceBoxName);
        Button shootButton = new Button(" SHOOT ");
        shootButton.setTextFill(Color.RED);
        shootButton.setAlignment(Pos.BOTTOM_CENTER);
        shootButton.setOnAction(e -> Shoot(choiceBoxEffect, choiceBoxName));
        Label emptySpace= new Label(" ");
        Label emptySpace1= new Label(" ");
        vBox.getChildren().addAll(effectType, choiceBoxEffect,target, choiceBoxName,emptySpace,emptySpace1, shootButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Scene scene= new Scene(splitPane,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void fillChoiceBoxName(ChoiceBox<String> choiceBoxName){
        for (int i=0; i<match.getPlayers().size();i++){
            if (match.getPlayers().get(i).getNickname()!= match.getCurrentPlayer().getNickname()) {
                choiceBoxName.getItems().add(match.getPlayers().get(i).getNickname());
            }
        }
    }

    public void Shoot(ChoiceBox<String> choiceBoxEffect, ChoiceBox<String> choiceBoxName){
        //TODO aggiungere come attributo alla classe il remote controller (per poter chiamare un metodo)
        //TODO aggiungere il metodo shoot al senderControllerRMI
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}