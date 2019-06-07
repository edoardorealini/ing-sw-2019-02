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
import model.player.Player;
import model.weapons.Weapon;

import java.io.File;


public class GeneralWeaponPopUp extends Application {

    private Match match;
    private Weapon weapon;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Shoot");
        SplitPane splitPane = new SplitPane();

        //image
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + weapon.toString() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView iv = new ImageView(image);
        iv.setX(0);
        iv.setY(0);
        iv.setFitHeight(250);
        iv.setFitWidth(200);
        iv.setPreserveRatio(true);

        VBox vBox = new VBox();
        splitPane.getItems().addAll(iv,vBox);

        Label shootingMode = new Label("Shooting mode: ");
        ChoiceBox<String> choiceBoxEffect = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxEffectOpt1 = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxEffectOpt2 = new ChoiceBox<>();

        switch (weapon.getRequiredParameters().getShootModeType()) {

            case 1:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.setValue("Basic");
                break;

            case 2:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.getItems().add("Alternate");
                choiceBoxEffect.setValue("Basic");
                break;

            case 3:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.getItems().add("Optional one");
                choiceBoxEffect.setValue("Basic");
                choiceBoxEffectOpt1.getItems().add("Basic");
                choiceBoxEffectOpt1.getItems().add("Optional one");
                // choiceBoxEffectOpt1.setValue(null);  TODO how to set it as default?
                break;

            case 4:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.getItems().add("Optional one");
                choiceBoxEffect.getItems().add("Optional two");
                choiceBoxEffect.setValue("Basic");
                choiceBoxEffectOpt1.getItems().add("Basic");
                choiceBoxEffectOpt1.getItems().add("Optional one");
                choiceBoxEffectOpt1.getItems().add("Optional two");
                choiceBoxEffectOpt2.getItems().add("Basic");
                choiceBoxEffectOpt2.getItems().add("Optional one");
                choiceBoxEffectOpt2.getItems().add("Optional two");
                break;

            default:
                break;
        }

        Label target = new Label("Chose the target: ");
        ChoiceBox<String> choiceBoxName = new ChoiceBox<>();
      //  fillChoiceBoxName(choiceBoxName);

        Button shootButton = new Button(" SHOOT ");
        shootButton.setTextFill(Color.RED);
        shootButton.setAlignment(Pos.BOTTOM_CENTER);
        shootButton.setOnAction(e -> Shoot(choiceBoxEffect, choiceBoxName));

        Label emptySpace= new Label(" ");
        Label emptySpace1= new Label(" ");

        vBox.getChildren().addAll(shootingMode, choiceBoxEffect, choiceBoxEffectOpt1, choiceBoxEffectOpt2, target, choiceBoxName, emptySpace, emptySpace1, shootButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(5);

        Scene scene= new Scene(splitPane,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void fillChoiceBoxName(ChoiceBox<String> choiceBoxName){
        for (Player p: match.getPlayers()) {
            if (! p.getNickname().equals(match.getCurrentPlayer().getNickname())) {
                choiceBoxName.getItems().add(p.getNickname());
            }
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void Shoot(ChoiceBox<String> choiceBoxEffect, ChoiceBox<String> choiceBoxName) {
        //TODO aggiungere come attributo alla classe il remote controller (per poter chiamare un metodo)
        //TODO aggiungere il metodo shoot al senderControllerRMI
    }

    public void setMatch(Match match) {
        this.match = match;
    }

}
