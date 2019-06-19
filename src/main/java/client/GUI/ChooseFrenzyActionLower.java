package client.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChooseFrenzyActionLower extends Application {
    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("Frenzy Action");
        //mainStage.initModality(Modality.APPLICATION_MODAL);
        SplitPane splitPane = new SplitPane();
        VBox vbox1= new VBox(6);

        //action 1
        Label plus1 = new Label("    +    ");
        Label plus2 = new Label("    +    ");
        Label plus3 = new Label("    +    ");
        Label actionMove1 = new Label("  Move ");
        Label actionMove2 = new Label("  Move ");
        Label actionReload1 = new Label("  Reload  ");
        Label actionShoot1 = new Label("  Shoot ");
        Button buttonAction1 = new Button(" Choose this ");
        vbox1.getChildren().addAll(actionMove1,plus1,actionMove2,plus3,actionReload1,plus2,actionShoot1,buttonAction1);
        vbox1.setAlignment(Pos.CENTER);
        splitPane.getItems().addAll(vbox1);

        //action 2
        VBox vbox2= new VBox(6);
        Label plus4 = new Label("    +    ");
        Label plus5 = new Label("    +    ");
        Label plus6 = new Label("    +    ");
        Button buttonAction2 = new Button(" Choose this ");
        Label actionMove3 = new Label("  Move ");
        Label actionMove4 = new Label("  Move ");
        Label actionMove5 = new Label("  Move ");
        Label actionGrab = new Label("  Grab  ");
        vbox2.getChildren().addAll(actionMove3,plus4,actionMove4,plus5,actionMove5,plus6,actionGrab,buttonAction2);
        vbox2.setAlignment(Pos.CENTER);
        splitPane.getItems().addAll(vbox2);

        Scene scene = new Scene(splitPane,500,300);
        mainStage.setScene(scene);
        // mainStage.showAndWait();
        mainStage.show();
    }
}
