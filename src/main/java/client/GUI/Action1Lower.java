package client.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Action1Lower extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Frenzy Action");
        VBox vbox = new VBox(5);

        // move
        Label title1 = new Label(" Move Section ");
        HBox hBox1 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        hBox1.getChildren().addAll(label1,posX);
        hBox1.setAlignment(Pos.CENTER);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        hBox2.getChildren().addAll(label2,posY);
        hBox2.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title1,hBox1,hBox2);

        // Reload
        Label title2 = new Label(" Reload Section ");
        Button reloadButton = new Button(" Reload ");
        // TODO setOnAction
        vbox.getChildren().addAll(title2,reloadButton);

        // Shoot
        Label title3 = new Label(" Shoot Section");
        Button shootButton = new Button(" Shoot");
        //TODO setOnAction
        vbox.getChildren().addAll(title3,shootButton);

        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,300,300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
