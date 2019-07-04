package client.GUI;

import client.remoteController.SenderClientRemoteController;
import commons.FileLoader;
import commons.ShootingParametersClient;
import exception.*;
import javafx.stage.Modality;
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
import model.player.AbilityStatus;
import model.player.Player;
import model.weapons.Weapon;
import model.weapons.WeaponAmmoStatus;

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

        int a = 0;
        int b = 0;
        int c = 0;


        primaryStage.initModality(Modality.APPLICATION_MODAL);
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


        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0] != null && match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getWeaponStatus() == WeaponAmmoStatus.LOADED){
            File file0 = FileLoader.getResourceAsFile("weapons" + "/" + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getName() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView iv0 = new ImageView(image0);
            iv0.setFitHeight(350);
            iv0.setFitWidth(300);
            iv0.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv0);
            useWeapon1.setText("Use " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getName());
            hBoxButtons.getChildren().add(useWeapon1);
            a = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1] != null && match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getWeaponStatus() == WeaponAmmoStatus.LOADED){
            File file1 = FileLoader.getResourceAsFile("weapons" + "/" + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getName() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView iv1 = new ImageView(image1);
            iv1.setFitHeight(350);
            iv1.setFitWidth(300);
            iv1.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv1);
            useWeapon2.setText("Use " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getName());
            hBoxButtons.getChildren().add(useWeapon2);
            b = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2] != null && match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getWeaponStatus() == WeaponAmmoStatus.LOADED){
            File file2 = FileLoader.getResourceAsFile("weapons" + "/" + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getName() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView iv2 = new ImageView(image2);
            iv2.setFitHeight(350);
            iv2.setFitWidth(300);
            iv2.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(iv2);
            useWeapon3.setText("Use " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getName());
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
        hBoxButtons.setSpacing(150);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        //setting the action linked to the button
        useWeapon1.setOnAction(event -> {
                this.weapon = match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0];
                shoot(new Stage());
                primaryStage.close();
                });

        useWeapon2.setOnAction(event -> {
                this.weapon = match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1];
                shoot(new Stage());
                primaryStage.close();
                });

        useWeapon3.setOnAction(event -> {
                this.weapon = match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2];
                shoot(new Stage());
                primaryStage.close();
                });

        vBox.getChildren().addAll(hBoxWeapon, text, hBoxButtons);


        Scene scene = new Scene(vBox,(300*(a+b+c)),450);
        primaryStage.setMinWidth(300);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

    }

    private void shoot (Stage shootStage) {

        shootStage.setTitle("Shoot");
        shootStage.initModality(Modality.APPLICATION_MODAL);
        SplitPane splitPane = new SplitPane();
        this.input = new ShootingParametersClient();

        //image
        File file = FileLoader.getResourceAsFile("weapons" + "/" + weapon.toString() + ".png");
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

        HBox modesHBox = new HBox();
        HBox targetHBox = new HBox();
        HBox squaresHBox = new HBox();
        HBox squaresHBox1 = new HBox();
        HBox squaresAdrenaline = new HBox();

        Label shootingMode = new Label("Select the shooting modes: ");
        Label targets = new Label("Select the targets: ");
        Label squares = new Label("Select the squares by indexes (NB: in case of more than one, the first is where you want to move the target): ");
        Label direction = new Label("Select the direction: ");
        Label damageBeforeMove = new Label("Do you want to execute the optional effect before moving the target? ");
        Label adrenalineMode = new Label("Do you want to use the adrenaline power and move before shooting? ");
        Label x = new Label("X: ");
        Label y = new Label("Y: ");
        Label x2 = new Label("X: ");
        Label y2 = new Label("Y: ");
        Label xAdrenaline = new Label("X: ");
        Label yAdrenaline = new Label("Y: ");
        Label emptySpace = new Label(" ");
        Label emptySpace1 = new Label(" ");

        ChoiceBox<String> target1 = new ChoiceBox<>();
        ChoiceBox<String> target2 = new ChoiceBox<>();
        ChoiceBox<String> target3 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare2 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare2 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquareAdrenaline = new ChoiceBox<>();
        ChoiceBox<Integer> ySquareAdrenaline = new ChoiceBox<>();
        ChoiceBox<Directions> directionBox = new ChoiceBox<>();
        ChoiceBox<Boolean> damageBeforeMoveBox = new ChoiceBox<>();
        ArrayList<ChoiceBox<ShootMode>> modes = new ArrayList<>();
        ArrayList<ChoiceBox<String>> targetPlayers = new ArrayList<>();
        ArrayList<ChoiceBox<Integer>> arraySquares = new ArrayList<>();
        ChoiceBox<ShootMode> choiceBoxEffect = new ChoiceBox<>();
        ChoiceBox<ShootMode> choiceBoxEffectOpt1 = new ChoiceBox<>();
        ChoiceBox<ShootMode> choiceBoxEffectOpt2 = new ChoiceBox<>();

        //start filling the vbox
        vBox.getChildren().add(shootingMode);

        //here it is build the hbox containing the shooting modes
        switch (weapon.getRequiredParameters().getShootModeType()) {

            case 1:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                modesHBox.getChildren().add(choiceBoxEffect);
                modes.add(choiceBoxEffect);
                break;

            case 2:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.getItems().add(ShootMode.ALTERNATE);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                modesHBox.getChildren().add(choiceBoxEffect);
                modes.add(choiceBoxEffect);
                break;

            case 3:
                choiceBoxEffect.getItems().add(ShootMode.BASIC);
                choiceBoxEffect.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffect.setValue(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.OPTIONAL1);
                modesHBox.getChildren().addAll(choiceBoxEffect, choiceBoxEffectOpt1);
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
                modesHBox.getChildren().addAll(choiceBoxEffect, choiceBoxEffectOpt1, choiceBoxEffectOpt2);
                modes.add(choiceBoxEffect);
                modes.add(choiceBoxEffectOpt1);
                modes.add(choiceBoxEffectOpt2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(modesHBox);


        //here it is build the hbox containing the targets
        switch (weapon.getRequiredParameters().getNumberOfTargets()) {
            case 0:
                break;

            case 1:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                targetHBox.getChildren().add(target1);
                targetPlayers.add(target1);
                break;

            case 2:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                targetHBox.getChildren().addAll(target1, target2);
                targetPlayers.add(target1);
                targetPlayers.add(target2);
                break;

            case 3:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                fillChoiceBoxName(target3);
                targetHBox.getChildren().addAll(target1, target2, target3);
                targetPlayers.add(target1);
                targetPlayers.add(target2);
                targetPlayers.add(target3);
                break;

            default:
                break;
        }

        vBox.getChildren().add(targetHBox);


        //here it is build the hbox containing the squares
        switch (weapon.getRequiredParameters().getNumberOfSquares()) {
            case 0:
                break;

            case 1:
                vBox.getChildren().add(squares);
                setX(xSquare1);
                setY(ySquare1);
                squaresHBox.getChildren().addAll(x, xSquare1, y, ySquare1);
                arraySquares.add(xSquare1);
                arraySquares.add(ySquare1);
                break;

            case 2:
                vBox.getChildren().add(squares);
                setX(xSquare1);
                setY(ySquare1);
                setX(xSquare2);
                setY(ySquare2);
                squaresHBox.getChildren().addAll(x, xSquare1, y, ySquare1);
                squaresHBox1.getChildren().addAll(x2, xSquare2, y2, ySquare2);
                arraySquares.add(xSquare1);
                arraySquares.add(ySquare1);
                arraySquares.add(xSquare2);
                arraySquares.add(ySquare2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(squaresHBox);

        if (weapon.getRequiredParameters().getNumberOfSquares() == 2)
            vBox.getChildren().add(squaresHBox1);

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

        if (match.getPlayer(senderRemoteController.getNickname()).getStatus().getSpecialAbility() == AbilityStatus.ADRENALINE_SHOOT) {
            vBox.getChildren().add(adrenalineMode);
            setX(xSquareAdrenaline);
            setY(ySquareAdrenaline);
            squaresAdrenaline.getChildren().addAll(xAdrenaline, xSquareAdrenaline, yAdrenaline, ySquareAdrenaline);
            vBox.getChildren().add(squaresAdrenaline);
        }


        //building the button and setting the action
        Button shootButton = new Button(" SHOOT ");
        shootButton.setTextFill(Color.BLUE);
        shootButton.setAlignment(Pos.CENTER);
        shootButton.setOnAction(event -> {
            try {
                fillInput(modes, targetPlayers, arraySquares, directionBox, damageBeforeMoveBox, xSquareAdrenaline, ySquareAdrenaline);
                shootStage.close();
            } catch (Exception e) {
                e.printStackTrace();
                PopUpSceneMethod.display("SHOOTING ERROR", e.getMessage());
                shootStage.close();
            }
        });

        vBox.getChildren().addAll(emptySpace, emptySpace1, shootButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        targetHBox.setAlignment(Pos.CENTER);
        modesHBox.setAlignment(Pos.CENTER);
        squaresHBox.setAlignment(Pos.CENTER);
        squaresHBox1.setAlignment(Pos.CENTER);
        squaresAdrenaline.setAlignment(Pos.CENTER);
        targetHBox.setSpacing(8);
        modesHBox.setSpacing(8);
        squaresHBox.setSpacing(8);
        squaresHBox1.setSpacing(8);
        squaresAdrenaline.setSpacing(8);

        Scene scene = new Scene(splitPane,700,400);
        shootStage.setScene(scene);
        shootStage.showAndWait();
    }


    private void fillChoiceBoxName(ChoiceBox<String> choiceBoxName){
        for (Player p: match.getPlayers()) {
            if (! p.getNickname().equals(match.getPlayer(senderRemoteController.getNickname()).getNickname())) {
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
        xChoiceBox.setValue(match.getMap().getIndex(match.getPlayer(senderRemoteController.getNickname()).getPosition()).get(0));  //x of the current square
    }

    private void setY(ChoiceBox<Integer> yChoiceBox) {
        yChoiceBox.getItems().addAll(0, 1, 2);
        yChoiceBox.setValue(match.getMap().getIndex(match.getPlayer(senderRemoteController.getNickname()).getPosition()).get(1));   //y of the current square
    }

    private void fillInput(ArrayList<ChoiceBox<ShootMode>> modes, ArrayList<ChoiceBox<String>> targetPlayers, ArrayList<ChoiceBox<Integer>> arraySquares,
                          ChoiceBox<Directions> direction, ChoiceBox<Boolean> damageBeforeMoveBox, ChoiceBox<Integer> xSquareAdrenaline, ChoiceBox<Integer> ySquareAdrenaline) throws NotAllowedShootingModeException, NotAllowedTargetException {

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
            if (choiceBox.getValue() != null)
                input.setShootModes(choiceBox.getValue());
        }

        for (ChoiceBox<String> choiceBox : targetPlayers) {
            if (!match.getPlayer(senderRemoteController.getNickname()).getNickname().equals(choiceBox.getValue()) && choiceBox.getValue() != null)
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

        if (direction != null)
            input.setDirection(direction.getValue());

        if (damageBeforeMoveBox != null && input != null)
            input.setMakeDamageBeforeMove(damageBeforeMoveBox.getValue());

        if (match.getPlayer(senderRemoteController.getNickname()).getStatus().getSpecialAbility() == AbilityStatus.ADRENALINE_SHOOT) {
            x = xSquareAdrenaline.getValue();
            y = ySquareAdrenaline.getValue();
            input.setAdrenalineMoveIndexes(x, y);
        }


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
