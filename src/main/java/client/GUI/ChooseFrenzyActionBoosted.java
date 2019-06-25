package client.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseFrenzyActionBoosted extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Frenzy Action");
        mainStage.initModality(Modality.APPLICATION_MODAL);
        SplitPane splitPane = new SplitPane();
        VBox vbox1= new VBox(6);

        //action 1
        Label title1 = new Label(" Action 1 Frenzy :");
        Label titleEmpty1 = new Label(" ");
        Label plus1 = new Label("    +    ");
        Label plus2 = new Label("    +    ");
        Label actionMove1 = new Label("  Move ");
        Label actionReload1 = new Label("  Reload  ");
        Label actionShoot1 = new Label("  Shoot ");
        vbox1.getChildren().addAll(title1,titleEmpty1,actionMove1,plus1,actionReload1,plus2,actionShoot1);
        vbox1.setAlignment(Pos.CENTER);
        splitPane.getItems().addAll(vbox1);

        //action 2
        VBox vbox2= new VBox(6);
        Label title2 = new Label(" Action 2 Frenzy :");
        Label titleEmpty2 = new Label(" ");
        Label plus3 = new Label("    +    ");
        Label plus4 = new Label("    +    ");
        Label plus5 = new Label("    +    ");
        Label actionMove2 = new Label("  Move ");
        Label actionMove3 = new Label("  Move ");
        Label actionMove4 = new Label("  Move ");
        Label actionMove5 = new Label("  Move ");
        vbox2.getChildren().addAll(title2,titleEmpty2,actionMove2,plus3,actionMove3,plus4,actionMove4,plus5,actionMove5);
        vbox2.setAlignment(Pos.CENTER);
        splitPane.getItems().addAll(vbox2);

        // action 3
        VBox vbox3 = new VBox(6);
        Label title3 = new Label(" Action 3 Frenzy :");
        Label titleEmpty3 = new Label(" ");
        Label plus6 = new Label("    +    ");
        Label plus7 = new Label("    +    ");
        Label actionMove6 = new Label("  Move ");
        Label actionMove7 = new Label("  Move ");
        Label actionGrab = new Label("  Grab  ");
        vbox3.getChildren().addAll(title3,titleEmpty3,actionMove6,plus6,actionMove7,plus7,actionGrab);
        vbox3.setAlignment(Pos.CENTER);
        splitPane.getItems().addAll(vbox3);

        splitPane.setDividerPositions(0.3f, 0.6f, 0.6f);

        Scene scene = new Scene(splitPane,600,370);
        mainStage.setScene(scene);
        mainStage.showAndWait();
    }
}