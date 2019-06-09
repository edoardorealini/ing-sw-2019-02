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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Match;
import model.map.Directions;
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
        iv.setFitHeight(300);
        iv.setFitWidth(250);
        iv.setPreserveRatio(true);


        //creating all labels and choice boxes
        VBox vBox = new VBox();
        splitPane.getItems().addAll(iv,vBox);

        HBox modesHbox = new HBox();
        HBox targetHbox = new HBox();
        HBox squaresHbox = new HBox();

        Label shootingMode = new Label("Select the shooting modes: ");
        Label targets = new Label("Select the targets: ");
        Label squares = new Label("Select the squares by indexes (in case of more than one, the first is where you want to move the target): ");
        Label direction = new Label("Select the direction: ");
        Label damageBeforeMove = new Label("Do you want to execute the optional effect before moving the target? ");
        Label emptySpace= new Label(" ");
        Label emptySpace1= new Label(" ");

        ChoiceBox<String> choiceBoxEffect = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxEffectOpt1 = new ChoiceBox<>();
        ChoiceBox<String> choiceBoxEffectOpt2 = new ChoiceBox<>();
        ChoiceBox<String> target1 = new ChoiceBox<>();
        ChoiceBox<String> target2 = new ChoiceBox<>();
        ChoiceBox<String> target3 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare2 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare2 = new ChoiceBox<>();
        ChoiceBox<Directions> directionBox = new ChoiceBox<>();
        ChoiceBox<Boolean> damageBeforeMoveBox = new ChoiceBox<>();

        //start filling the vbox
        vBox.getChildren().add(shootingMode);

        //here it is build the hbox containing the shooting modes
        switch (weapon.getRequiredParameters().getShootModeType()) {

            case 1:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.setValue("Basic");
                modesHbox.getChildren().add(choiceBoxEffect);
                break;

            case 2:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.getItems().add("Alternate");
                choiceBoxEffect.setValue("Basic");
                modesHbox.getChildren().add(choiceBoxEffect);
                break;

            case 3:
                choiceBoxEffect.getItems().add("Basic");
                choiceBoxEffect.getItems().add("Optional one");
                choiceBoxEffect.setValue("Basic");
                choiceBoxEffectOpt1.getItems().add("Basic");
                choiceBoxEffectOpt1.getItems().add("Optional one");
                modesHbox.getChildren().addAll(choiceBoxEffect, choiceBoxEffectOpt1);
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
                modesHbox.getChildren().addAll(choiceBoxEffect, choiceBoxEffectOpt1, choiceBoxEffectOpt2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(modesHbox);


        //here it is build the hbox containing the targets
        switch (weapon.getRequiredParameters().getNumberOfTargets()) {
            case 0:
                break;

            case 1:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                targetHbox.getChildren().add(target1);
                break;

            case 2:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                targetHbox.getChildren().addAll(target1, target2);
                break;

            case 3:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                fillChoiceBoxName(target3);
                targetHbox.getChildren().addAll(target1, target2, target3);
                break;

            default:
                break;
        }

        vBox.getChildren().add(targetHbox);


        //here it is build the hbox containing the squares
        switch (weapon.getRequiredParameters().getNumberOfSquares()) {
            case 0:
                break;

            case 1:
                vBox.getChildren().add(squares);
                setX(xSquare1);
                setY(ySquare1);
                squaresHbox.getChildren().addAll(xSquare1, ySquare1);
                break;

            case 2:
                vBox.getChildren().add(squares);
                setX(xSquare1);
                setY(ySquare1);
                setX(xSquare2);
                setY(ySquare2);
                squaresHbox.getChildren().addAll(xSquare1, ySquare1, xSquare2, ySquare2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(squaresHbox);

        if (weapon.getRequiredParameters().needToKnowDirection()) {
            vBox.getChildren().add(direction);
            directionBox.getItems().addAll(Directions.UP, Directions.DOWN, Directions.LEFT, Directions.RIGHT);
            directionBox.setValue(Directions.UP);
            vBox.getChildren().add(directionBox);
        }

        if (weapon.getRequiredParameters().needToKnowDamageBeforeMove()) {
            vBox.getChildren().add(damageBeforeMove);
            damageBeforeMoveBox.getItems().addAll(true, false);
            damageBeforeMoveBox.setValue(false);
            vBox.getChildren().add(damageBeforeMoveBox);
        }


        //building the button and setting the action
        Button shootButton = new Button(" SHOOT ");
        shootButton.setTextFill(Color.BLUE);
        shootButton.setAlignment(Pos.CENTER);
        //shootButton.setOnAction(e -> Shoot(choiceBoxEffect, ));

        vBox.getChildren().addAll(emptySpace, emptySpace1, shootButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        targetHbox.setAlignment(Pos.CENTER);
        modesHbox.setAlignment(Pos.CENTER);
        squaresHbox.setAlignment(Pos.CENTER);
        targetHbox.setSpacing(5);
        modesHbox.setSpacing(5);
        squaresHbox.setSpacing(5);

        Scene scene = new Scene(splitPane,500,300);
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

    private void setX(ChoiceBox<Integer> xChoiceBox) {
        xChoiceBox.getItems().addAll(0, 1, 2, 3);
        xChoiceBox.setValue(0);
    }

    private void setY(ChoiceBox<Integer> yChoiceBox) {
        yChoiceBox.getItems().addAll(0, 1, 2);
        yChoiceBox.setValue(0);
    }


}
