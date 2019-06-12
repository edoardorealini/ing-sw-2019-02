package client.GUI;

import client.remoteController.SenderClientRemoteController;
import commons.ShootingParametersClient;
import exception.*;
import model.ShootMode;
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
import java.rmi.RemoteException;
import java.util.ArrayList;


public class GeneralWeaponPopUp extends Application {

    private Match match;
    private Weapon weapon;
    private SenderClientRemoteController senderRemoteController;
    private ShootingParametersClient input;


    @Override
    public void start(Stage primaryStage) throws Exception {
        short a = 0;
        short b = 0;
        short c = 0;
        primaryStage.setTitle("Owned weapons");
        Label text = new Label("Choose the weapon to shoot with:");
        HBox hBoxWeapon = new HBox();
        HBox hBoxButtons = new HBox();
        VBox vBox = new VBox();
        Button useWeapon1 = new Button();
        Button useWeapon2 = new Button();
        Button useWeapon3 = new Button();
        useWeapon1.setTextFill(Color.DARKRED);
        useWeapon2.setTextFill(Color.DARKRED);
        useWeapon3.setTextFill(Color.DARKRED);
        useWeapon1.setAlignment(Pos.CENTER);
        useWeapon2.setAlignment(Pos.CENTER);
        useWeapon3.setAlignment(Pos.CENTER);


        if (match.getCurrentPlayer().getWeapons()[0]  != null){
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getCurrentPlayer().getWeapons()[0].getName() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(350);
            iv0.setFitWidth(300);
            iv0.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv0);
            useWeapon1.setText("Use " + match.getCurrentPlayer().getWeapons()[0].getName());
            hBoxButtons.getChildren().add(useWeapon1);
            a = 1;
        }

        if (match.getCurrentPlayer().getWeapons()[1] != null){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getCurrentPlayer().getWeapons()[1].getName() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(350);
            iv1.setFitWidth(300);
            iv1.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv1);
            useWeapon2.setText("Use " + match.getCurrentPlayer().getWeapons()[1].getName());
            hBoxButtons.getChildren().add(useWeapon2);
            b = 1;
        }

        if (match.getCurrentPlayer().getWeapons()[2] != null){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getCurrentPlayer().getWeapons()[2].getName() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(350);
            iv2.setFitWidth(300);
            iv2.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv2);
            useWeapon3.setText("Use " + match.getCurrentPlayer().getWeapons()[2].getName());
            hBoxButtons.getChildren().add(useWeapon3);
            c = 1;
        }

        hBoxWeapon.setMinHeight(350);
        hBoxWeapon.setMinWidth(300);
        hBoxButtons.setMinHeight(80);
        hBoxButtons.setMinWidth(100);
        hBoxWeapon.setAlignment(Pos.CENTER);
        hBoxWeapon.setSpacing(8);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(250);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        //setting the action linked to the button
        useWeapon1.setOnAction(event -> {
                this.weapon = match.getCurrentPlayer().getWeapons()[0];
                shoot(new Stage());
                });

        useWeapon2.setOnAction(event -> {
                this.weapon = match.getCurrentPlayer().getWeapons()[1];
                shoot(new Stage());
                });

        useWeapon3.setOnAction(event -> {
                this.weapon = match.getCurrentPlayer().getWeapons()[2];
                shoot(new Stage());
                });

        vBox.getChildren().addAll(hBoxWeapon, text, hBoxButtons);


        Scene scene = new Scene(vBox,(300*(a+b+c) + 100),450);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void shoot (Stage shootStage) {

        shootStage.setTitle("Shoot");
        SplitPane splitPane = new SplitPane();
        this.input = new ShootingParametersClient();

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
        HBox squaresHbox1 = new HBox();

        Label shootingMode = new Label("Select the shooting modes: ");
        Label targets = new Label("Select the targets: ");
        Label squares = new Label("Select the squares by indexes (in case of more than one, the first is where you want to move the target): ");
        Label direction = new Label("Select the direction: ");
        Label damageBeforeMove = new Label("Do you want to execute the optional effect before moving the target? ");
        Label x = new Label("X: ");
        Label y = new Label("Y: ");
        Label x2 = new Label("X: ");
        Label y2 = new Label("Y: ");
        Label emptySpace = new Label(" ");
        Label emptySpace1 = new Label(" ");

        ChoiceBox<ShootMode> choiceBoxEffect = new ChoiceBox<>();
        ChoiceBox<ShootMode> choiceBoxEffectOpt1 = new ChoiceBox<>();
        ChoiceBox<ShootMode> choiceBoxEffectOpt2 = new ChoiceBox<>();
        ChoiceBox<String> target1 = new ChoiceBox<>();
        ChoiceBox<String> target2 = new ChoiceBox<>();
        ChoiceBox<String> target3 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare2 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare2 = new ChoiceBox<>();
        ChoiceBox<Directions> directionBox = new ChoiceBox<>();
        ChoiceBox<Boolean> damageBeforeMoveBox = new ChoiceBox<>();
        ArrayList<ChoiceBox<ShootMode>> modes = new ArrayList<>();
        ArrayList<ChoiceBox<String>> targetPlayers = new ArrayList<>();
        ArrayList<ChoiceBox<Integer>> arraySquares = new ArrayList<>();

        //start filling the vbox
        vBox.getChildren().add(shootingMode);

        //here it is build the hbox containing the shooting modes
        switch (weapon.getRequiredParameters().getShootModeType()) {

            case 1:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                modesHbox.getChildren().add(choiceBoxEffect);
                modes.add(choiceBoxEffect);
                break;

            case 2:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.getItems().add(ShootMode.ALTERNATE);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                modesHbox.getChildren().add(choiceBoxEffect);
                modes.add(choiceBoxEffect);
                break;

            case 3:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.OPTIONAL1);
                modesHbox.getChildren().addAll(choiceBoxEffect, choiceBoxEffectOpt1);
                modes.add(choiceBoxEffect);
                modes.add(choiceBoxEffectOpt1);
                break;

            case 4:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffect.getItems().add(ShootMode.OPTIONAL2);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffectOpt1.getItems().add(ShootMode.OPTIONAL2);
                choiceBoxEffectOpt2.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt2.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffectOpt2.getItems().add(ShootMode.OPTIONAL2);
                modesHbox.getChildren().addAll(choiceBoxEffect, choiceBoxEffectOpt1, choiceBoxEffectOpt2);
                modes.add(choiceBoxEffect);
                modes.add(choiceBoxEffectOpt1);
                modes.add(choiceBoxEffectOpt2);
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
                targetPlayers.add(target1);
                break;

            case 2:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                targetHbox.getChildren().addAll(target1, target2);
                targetPlayers.add(target1);
                targetPlayers.add(target2);
                break;

            case 3:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                fillChoiceBoxName(target3);
                targetHbox.getChildren().addAll(target1, target2, target3);
                targetPlayers.add(target1);
                targetPlayers.add(target2);
                targetPlayers.add(target3);
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
                squaresHbox.getChildren().addAll(x, xSquare1, y, ySquare1);
                arraySquares.add(xSquare1);
                arraySquares.add(ySquare1);
                break;

            case 2:
                vBox.getChildren().add(squares);
                setX(xSquare1);
                setY(ySquare1);
                setX(xSquare2);
                setY(ySquare2);
                squaresHbox.getChildren().addAll(x, xSquare1, y, ySquare1);
                squaresHbox1.getChildren().addAll(x2, xSquare2, y2, ySquare2);
                arraySquares.add(xSquare1);
                arraySquares.add(ySquare1);
                arraySquares.add(xSquare2);
                arraySquares.add(ySquare2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(squaresHbox);

        if (weapon.getRequiredParameters().getNumberOfSquares() == 2)
            vBox.getChildren().add(squaresHbox1);

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
        shootButton.setOnAction(event -> {
            try {
                fillInput(modes, targetPlayers, arraySquares, directionBox, damageBeforeMoveBox);

            } catch (Exception e) {
                e.printStackTrace();
                PopUpSceneMethod.display("SHOOTING ERROR", e.getMessage());
            }
        });

        vBox.getChildren().addAll(emptySpace, emptySpace1, shootButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        targetHbox.setAlignment(Pos.CENTER);
        modesHbox.setAlignment(Pos.CENTER);
        squaresHbox.setAlignment(Pos.CENTER);
        squaresHbox1.setAlignment(Pos.CENTER);
        targetHbox.setSpacing(8);
        modesHbox.setSpacing(8);
        squaresHbox.setSpacing(8);
        squaresHbox1.setSpacing(8);

        Scene scene = new Scene(splitPane,700,400);
        shootStage.setScene(scene);
        shootStage.show();
    }


    private void fillChoiceBoxName(ChoiceBox<String> choiceBoxName){
        for (Player p: match.getPlayers()) {
            if (! p.getNickname().equals(match.getCurrentPlayer().getNickname())) {
                choiceBoxName.getItems().add(p.getNickname());
            }
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public void setSenderRemoteController(SenderClientRemoteController senderClientRemoteController) {
        this.senderRemoteController = senderClientRemoteController;
    }

    private void setX(ChoiceBox<Integer> xChoiceBox) {
        xChoiceBox.getItems().addAll(0, 1, 2, 3);
        xChoiceBox.setValue(0);
    }

    private void setY(ChoiceBox<Integer> yChoiceBox) {
        yChoiceBox.getItems().addAll(0, 1, 2);
        yChoiceBox.setValue(0);
    }

    private void fillInput(ArrayList<ChoiceBox<ShootMode>> modes, ArrayList<ChoiceBox<String>> targetPlayers, ArrayList<ChoiceBox<Integer>> arraySquares,
                          ChoiceBox<Directions> direction, ChoiceBox<Boolean> damageBeforeMoveBox) throws NotAllowedShootingModeException, NotAllowedTargetException {

        input.setName(weapon.getName());

        boolean basicModeChosen = false;
        boolean alternateModeChosen = false;

        for (ChoiceBox<ShootMode> choiceBox : modes) {
            if (choiceBox.getValue() == ShootMode.BASIC)
                basicModeChosen = true;
            if (choiceBox.getValue() == ShootMode.ALTERNATE)
                alternateModeChosen = true;
        }

        if (! (basicModeChosen || alternateModeChosen))
            throw new NotAllowedShootingModeException("Not allowed shooting mode, please try again");

        //setting shooting modes
        for (ChoiceBox<ShootMode> choiceBox : modes) {
            input.setShootModes(choiceBox.getValue());
        }

        for (ChoiceBox<String> choiceBox : targetPlayers) {
            if (!match.getCurrentPlayer().getNickname().equals(choiceBox.getValue()))
                input.setTargetPlayers(choiceBox.getValue());
        }

        //checking no duplication in targets
        for (int i = 0; i < input.getTargetPlayers().size() - 1; i++) {
            for (int j = i + 1; j < input.getTargetPlayers().size(); j++)
                if (input.getTargetPlayers().get(i).equals(input.getTargetPlayers().get(j)))
                    throw new NotAllowedTargetException("You selected the same target more than once");
        }

        int x = 0;
        int y = 0;

        if (! arraySquares.isEmpty()) {
            x = arraySquares.get(0).getValue();
            y = arraySquares.get(1).getValue();
            input.setSquaresCoordinates(x, y);
        }
        
        if (arraySquares.size() > 2) {
            x = arraySquares.get(2).getValue();
            y = arraySquares.get(3).getValue();
            input.setSquaresCoordinates(x, y);
        }

        if(direction != null)
            input.setDirection(direction.getValue());

        /*
        if(damageBeforeMoveBox != null && input != null)
            input.setMakeDamageBeforeMove(damageBeforeMoveBox.getValue());

         */

        try {
            senderRemoteController.shoot(input);
        } catch (RemoteException e) {
            e.printStackTrace();
            PopUpSceneMethod.display("NETWORK ERROR", e.getMessage());
        } catch (NotAllowedCallException e) {
            e.printStackTrace();
            PopUpSceneMethod.display("CALL ERROR", e.getMessage());
        }  catch (NotAllowedTargetException |NotAllowedShootingModeException | NotEnoughAmmoException | NotAllowedMoveException | WrongStatusException | InvalidInputException e) {
            e.printStackTrace();
            PopUpSceneMethod.display("SHOOT ERROR", e.getMessage());
        }

    }

}
