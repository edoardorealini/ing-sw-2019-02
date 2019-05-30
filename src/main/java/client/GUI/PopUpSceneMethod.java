package client.GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;

public class PopUpSceneMethod {

    public static void display(String title, String message){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL); // la finestra che si apre è l'unica cosa che puoi toccare se non la chiudi
        stage.setTitle(title);
        stage.setMinWidth(250);
        stage.setMinHeight(90);

        Label label = new Label();
        label.setText(message);
        label.setTextFill(Color.RED);

        Button closeButton = new Button(" Close ");
        closeButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        stage.setScene(scene);
        stage.showAndWait(); // non torna al chiamante fino a quando non si è chiusa la finestra

    }
}
