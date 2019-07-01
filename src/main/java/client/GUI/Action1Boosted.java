package client.GUI;

import client.remoteController.SenderClientRemoteController;
import commons.ShootingParametersClient;
import exception.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;
import model.ShootMode;
import model.map.Directions;
import model.player.Player;
import model.weapons.Weapon;
import model.weapons.WeaponAmmoStatus;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Action1Boosted extends Application {
    private Match match;
    private Weapon weapon;
    private SenderClientRemoteController senderRemoteController;
    private ShootingParametersClient input;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        short e = 0;
        short f = 0;
        short g = 0;


        primaryStage.setTitle("Owned weapons");
        Label text = new Label("Choose the weapon to shoot with:");
        VBox vBox = new VBox();
        Button useweapon1 = new Button();
        Button useweapon2 = new Button();
        Button useweapon3 = new Button();
        HBox hBoxWeapon = new HBox();
        HBox hBoxButtons = new HBox();
        useweapon1.setTextFill(Color.DARKRED);
        useweapon2.setTextFill(Color.DARKRED);
        useweapon3.setTextFill(Color.DARKRED);
        useweapon1.setAlignment(Pos.CENTER);
        useweapon2.setAlignment(Pos.CENTER);
        useweapon3.setAlignment(Pos.CENTER);


        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0]  != null){
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getName() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView ivReload0 = new ImageView(image0);
            ivReload0.setFitHeight(350);
            ivReload0.setFitWidth(300);
            ivReload0.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(ivReload0);
            useweapon1.setText("Use " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getName());
            hBoxButtons.getChildren().add(useweapon1);
            e = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1] != null){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getName() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView ivReload1 = new ImageView(image1);
            ivReload1.setFitHeight(350);
            ivReload1.setFitWidth(300);
            ivReload1.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(ivReload1);
            useweapon2.setText("Use " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getName());
            hBoxButtons.getChildren().add(useweapon2);
            f = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2] != null){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getName() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView ivReload2 = new ImageView(image2);
            ivReload2.setFitHeight(350);
            ivReload2.setFitWidth(300);
            ivReload2.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(ivReload2);
            useweapon3.setText("Use " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getName());
            hBoxButtons.getChildren().add(useweapon3);
            g = 1;
        }

        hBoxWeapon.setAlignment(Pos.CENTER);
        hBoxWeapon.setSpacing(8);
        hBoxWeapon.setMinHeight(350);
        hBoxWeapon.setMinWidth(300);
        hBoxButtons.setMinHeight(80);
        hBoxButtons.setMinWidth(100);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(250);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        //setting the action linked to the button
        useweapon3.setOnAction(event -> {

            this.weapon = match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2];
            wholeAction(new Stage());
            primaryStage.close();

        });

        useweapon2.setOnAction(event -> {

            this.weapon = match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1];
            wholeAction(new Stage());
            primaryStage.close();

        });

        useweapon1.setOnAction(event -> {

            this.weapon = match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0];
            wholeAction(new Stage());
            primaryStage.close();

        });


        vBox.getChildren().addAll(hBoxWeapon, text, hBoxButtons);


        Scene scene = new Scene(vBox,(300*(e+f+g) + 100),450);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void wholeAction(Stage wholeStage){
        wholeStage.setTitle("Frenzy Action");
        VBox vbox = new VBox(5);

        //+++++++++++// move
        Label title0 = new Label(" Move Section ");
        HBox hBox0 = new HBox(5);
        Label label1 = new Label();
        label1.setText("X : ");
        ChoiceBox<Integer> posX = new ChoiceBox<>();
        posX.getItems().addAll(0,1,2,3);
        hBox0.getChildren().addAll(label1,posX);
        hBox0.setAlignment(Pos.CENTER);
        setPositionX(posX);

        HBox hBox2 = new HBox(5);
        Label label2 = new Label();
        label2.setText("Y : ");
        ChoiceBox<Integer> posY = new ChoiceBox<>();
        posY.getItems().addAll(0,1,2);
        hBox2.getChildren().addAll(label2,posY);
        hBox2.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(title0,hBox0,hBox2);
        setPositionY(posY);


        //+++++++++++// reload
        Label title2 = new Label(" Reload Section ");
        Button reloadButton = new Button(" Reload ");
        reloadButton.setOnAction(e -> reloadPopup());

        vbox.getChildren().addAll(title2,reloadButton);

        //+++++++++++// Shoot
        //******************************************************

        SplitPane splitPane = new SplitPane();

        //image
        File file = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + weapon.toString() + ".png");
        Image image = new Image(file.toURI().toString());
        ImageView ivPrincipal = new ImageView(image);
        ivPrincipal.setX(0);
        ivPrincipal.setY(0);
        ivPrincipal.setFitHeight(300);
        ivPrincipal.setFitWidth(250);
        ivPrincipal.setPreserveRatio(true);


        //creating all labels and choice boxes
        VBox vBox = new VBox();
        splitPane.getItems().addAll(ivPrincipal,vBox);

        HBox modesHbox = new HBox();
        HBox targethBox = new HBox();
        HBox squaresHbox0 = new HBox();
        HBox squaresHbox1 = new HBox();

        Label shootingMode = new Label("Select the shooting modes: ");
        Label targets = new Label("Select the targets: ");
        Label x = new Label("X: ");
        Label y = new Label("Y: ");
        Label x2 = new Label("X: ");
        Label y2 = new Label("Y: ");
        Label squares = new Label("Select the squares by indexes (in case of more than one, the first is where you want to move the target): ");
        Label direction = new Label("Select the direction: ");
        Label damageBeforeMove = new Label("Do you want to execute the optional effect before moving the target? ");
        Label emptySpace = new Label(" ");
        Label emptySpace1 = new Label(" ");

        ChoiceBox<ShootMode> choiceBoxFirstEffect = new ChoiceBox<>();
        ChoiceBox<ShootMode> choiceBoxEffectOpt0 = new ChoiceBox<>();
        ChoiceBox<ShootMode> choiceBoxEffectOpt1 = new ChoiceBox<>();
        ChoiceBox<String> target0 = new ChoiceBox<>();
        ChoiceBox<String> target1 = new ChoiceBox<>();
        ChoiceBox<String> target2 = new ChoiceBox<>();
        ChoiceBox<Directions> directionBox = new ChoiceBox<>();
        ChoiceBox<Boolean> damageBeforeMoveBox = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> xSquare2 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare1 = new ChoiceBox<>();
        ChoiceBox<Integer> ySquare2 = new ChoiceBox<>();
        ArrayList<ChoiceBox<ShootMode>> modes = new ArrayList<>();
        ArrayList<ChoiceBox<String>> targetPlayers = new ArrayList<>();
        ArrayList<ChoiceBox<Integer>> arraySquare = new ArrayList<>();

        //start filling the vbox

        vBox.getChildren().add(shootingMode);

        //here it is build the hbox containing the shooting modes
        switch (weapon.getRequiredParameters().getShootModeType()) {

            case 1:
                choiceBoxFirstEffect.getItems().add(ShootMode.BASIC);
                choiceBoxFirstEffect.setValue(ShootMode.BASIC);
                modesHbox.getChildren().add(choiceBoxFirstEffect);
                modes.add(choiceBoxFirstEffect);
                break;

            case 2:
                choiceBoxFirstEffect.getItems().add(ShootMode.BASIC);
                choiceBoxFirstEffect.getItems().add(ShootMode.ALTERNATE);
                choiceBoxFirstEffect.setValue(ShootMode.BASIC);
                modesHbox.getChildren().add(choiceBoxFirstEffect);
                modes.add(choiceBoxFirstEffect);
                break;

            case 3:
                choiceBoxFirstEffect.getItems().add(ShootMode.BASIC);
                choiceBoxFirstEffect.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxFirstEffect.setValue(ShootMode.BASIC);
                choiceBoxEffectOpt0.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt0.getItems().add(ShootMode.OPTIONAL1);
                modesHbox.getChildren().addAll(choiceBoxFirstEffect, choiceBoxEffectOpt0);
                modes.add(choiceBoxFirstEffect);
                modes.add(choiceBoxEffectOpt0);
                break;

            case 4:
                choiceBoxFirstEffect.getItems().add(ShootMode.BASIC);
                choiceBoxFirstEffect.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxFirstEffect.getItems().add(ShootMode.OPTIONAL2);
                choiceBoxFirstEffect.setValue(ShootMode.BASIC);
                choiceBoxEffectOpt0.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt0.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffectOpt0.getItems().add(ShootMode.OPTIONAL2);
                choiceBoxEffectOpt1.getItems().add(ShootMode.BASIC);
                choiceBoxEffectOpt1.getItems().add(ShootMode.OPTIONAL1);
                choiceBoxEffectOpt1.getItems().add(ShootMode.OPTIONAL2);
                modesHbox.getChildren().addAll(choiceBoxFirstEffect, choiceBoxEffectOpt0, choiceBoxEffectOpt1);
                modes.add(choiceBoxFirstEffect);
                modes.add(choiceBoxEffectOpt0);
                modes.add(choiceBoxEffectOpt1);
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
                fillChoiceBoxName(target0);
                targethBox.getChildren().add(target0);
                targetPlayers.add(target0);
                break;

            case 2:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target0);
                fillChoiceBoxName(target1);
                targethBox.getChildren().addAll(target0, target1);
                targetPlayers.add(target0);
                targetPlayers.add(target1);
                break;

            case 3:
                vBox.getChildren().add(targets);
                fillChoiceBoxName(target0);
                fillChoiceBoxName(target1);
                fillChoiceBoxName(target2);
                targethBox.getChildren().addAll(target0, target1, target2);
                targetPlayers.add(target0);
                targetPlayers.add(target1);
                targetPlayers.add(target2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(targethBox);


        //here it is build the hbox containing the squares
        switch (weapon.getRequiredParameters().getNumberOfSquares()) {
            case 0:
                break;

            case 1:
                vBox.getChildren().add(squares);
                setPositionX(xSquare1);
                setPositionY(ySquare1);
                squaresHbox0.getChildren().addAll(x, xSquare1, y, ySquare1);
                arraySquare.add(xSquare1);
                arraySquare.add(ySquare1);
                break;

            case 2:
                vBox.getChildren().add(squares);
                setPositionX(xSquare1);
                setPositionY(ySquare1);
                setPositionX(xSquare2);
                setPositionY(ySquare2);
                squaresHbox0.getChildren().addAll(x, xSquare1, y, ySquare1);
                squaresHbox1.getChildren().addAll(x2, xSquare2, y2, ySquare2);
                arraySquare.add(xSquare1);
                arraySquare.add(ySquare1);
                arraySquare.add(xSquare2);
                arraySquare.add(ySquare2);
                break;

            default:
                break;
        }

        vBox.getChildren().add(squaresHbox0);

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


        vBox.getChildren().addAll(emptySpace, emptySpace1);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);
        squaresHbox0.setAlignment(Pos.CENTER);
        squaresHbox1.setAlignment(Pos.CENTER);
        targethBox.setAlignment(Pos.CENTER);
        modesHbox.setAlignment(Pos.CENTER);
        squaresHbox0.setSpacing(8);
        squaresHbox1.setSpacing(8);
        targethBox.setSpacing(8);
        modesHbox.setSpacing(8);

        //******************************************************
        vbox.getChildren().addAll(splitPane);

        Button actionButton = new Button(" Make Action ");
        actionButton.setOnAction(e -> {
            try {
                ShootingParametersClient in = new ShootingParametersClient();
                setInput(in);
                fillInput(modes, targetPlayers, arraySquare, directionBox, damageBeforeMoveBox);
                fillWholeInput(posX.getValue(),posY.getValue());
                wholeStage.close();
            } catch (Exception ei) {
                ei.printStackTrace();
                PopUpSceneMethod.display("SHOOTING ERROR", ei.getMessage());
                wholeStage.close();
            }
        });

        vbox.getChildren().add(actionButton);
        vbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vbox,500,600);
        wholeStage.setScene(scene);
        wholeStage.show();
    }

    private void fillChoiceBoxName(ChoiceBox<String> choiceBoxName){
        for (Player p: match.getPlayers()) {
            if (! p.getNickname().equals(match.getPlayer(senderRemoteController.getNickname()).getNickname())) {
                choiceBoxName.getItems().add(p.getNickname());
            }
        }
    }

    private void setPositionX(ChoiceBox<Integer> xChoiceBox) {
        xChoiceBox.getItems().addAll(0, 1, 2, 3);
        xChoiceBox.setValue(match.getMap().getIndex(match.getPlayer(senderRemoteController.getNickname()).getPosition()).get(0));
    }

    private void setPositionY(ChoiceBox<Integer> yChoiceBox) {
        yChoiceBox.getItems().addAll(0, 1, 2);
        yChoiceBox.setValue(match.getMap().getIndex(match.getPlayer(senderRemoteController.getNickname()).getPosition()).get(1));
    }

    public void reloadPopup() {

        int first = 0;
        int second = 0;
        int third = 0;

        Stage reloadStage = new Stage();

        reloadStage.initModality(Modality.APPLICATION_MODAL);
        reloadStage.setTitle("Owned weapons");
        Label text = new Label("Choose the weapon you want to reload:");
        HBox hBoxWeapon = new HBox();
        HBox hBoxButtons = new HBox();
        VBox vBox = new VBox();
        Button reloadWeapon0 = new Button();
        Button reloadWeapon2 = new Button();
        Button reloadWeapon3 = new Button();
        reloadWeapon0.setTextFill(Color.CADETBLUE);
        reloadWeapon2.setTextFill(Color.CADETBLUE);
        reloadWeapon3.setTextFill(Color.CADETBLUE);
        reloadWeapon0.setAlignment(Pos.CENTER);
        reloadWeapon2.setAlignment(Pos.CENTER);
        reloadWeapon3.setAlignment(Pos.CENTER);


        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0] != null && match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getWeaponStatus() == WeaponAmmoStatus.UNLOADED) {
            File file0 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getName() + ".png");
            Image image0 = new Image(file0.toURI().toString());
            ImageView ivReload0 = new ImageView(image0);
            ivReload0.setFitHeight(350);
            ivReload0.setFitWidth(300);
            ivReload0.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(ivReload0);
            reloadWeapon0.setText("Reload " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[0].getName());
            hBoxButtons.getChildren().add(reloadWeapon0);
            first = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1] != null && match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getWeaponStatus() == WeaponAmmoStatus.UNLOADED){
            File file1 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getName() + ".png");
            Image image1 = new Image(file1.toURI().toString());
            ImageView ivReload1 = new ImageView(image1);
            ivReload1.setFitHeight(350);
            ivReload1.setFitWidth(300);
            ivReload1.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(ivReload1);
            reloadWeapon2.setText("Reload " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[1].getName());
            hBoxButtons.getChildren().add(reloadWeapon2);
            second = 1;
        }

        if (match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2] != null && match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getWeaponStatus() == WeaponAmmoStatus.UNLOADED){
            File file2 = new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                    + File.separatorChar + "resources" + File.separatorChar + "weapons" + File.separatorChar + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getName() + ".png");
            Image image2 = new Image(file2.toURI().toString());
            ImageView ivReload2 = new ImageView(image2);
            ivReload2.setFitHeight(350);
            ivReload2.setFitWidth(300);
            ivReload2.setPreserveRatio(true);
            hBoxWeapon.getChildren().add(ivReload2);
            reloadWeapon3.setText("Reload " + match.getPlayer(senderRemoteController.getNickname()).getWeapons()[2].getName());
            hBoxButtons.getChildren().add(reloadWeapon3);
            third = 1;
        }

        hBoxWeapon.setAlignment(Pos.CENTER);
        hBoxWeapon.setSpacing(8);
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.setSpacing(250);
        hBoxWeapon.setMinHeight(350);
        hBoxWeapon.setMinWidth(300);
        hBoxButtons.setMinHeight(80);
        hBoxButtons.setMinWidth(100);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(8);

        //setting the action linked to the button
        reloadWeapon3.setOnAction(event -> {
            try {
                senderRemoteController.reload(2);
                reloadStage.close();
            } catch (RemoteException | NotEnoughAmmoException | NotAllowedCallException |WrongStatusException e) {
                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        reloadWeapon2.setOnAction(event -> {
            try {
                senderRemoteController.reload(1);
                reloadStage.close();
            } catch (RemoteException | NotEnoughAmmoException | NotAllowedCallException |WrongStatusException e) {

                e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        reloadWeapon0.setOnAction(event -> {
            try {
                senderRemoteController.reload(0);
                reloadStage.close();
            } catch (RemoteException | NotEnoughAmmoException | NotAllowedCallException |WrongStatusException e) {
                //e.printStackTrace();
                PopUpSceneMethod.display("ERROR", e.getMessage());
            }
        });

        vBox.getChildren().addAll(hBoxWeapon, text, hBoxButtons);



        Scene scene = new Scene(vBox, (300*(first+second+third) + 100), 450);
        reloadStage.setScene(scene);
        reloadStage.showAndWait();

    }

    private void fillWholeInput(int posX, int posY) throws RemoteException, WrongStatusException, NotAllowedTargetException, InvalidInputException, NotAllowedCallException, NotAllowedMoveException, NotEnoughAmmoException, NotAllowedShootingModeException {
        // input per la shoot già inpacchettato
        // e li passo direttamente le coordinate per la move
        senderRemoteController.makeAction1Frenzy(posX,posY,input);
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    private void fillInput(ArrayList<ChoiceBox<ShootMode>> modes, ArrayList<ChoiceBox<String>> targetPlayers, ArrayList<ChoiceBox<Integer>> arraySquare,
                           ChoiceBox<Directions> direction, ChoiceBox<Boolean> damageBeforeMoveBox) throws NotAllowedShootingModeException, NotAllowedTargetException {

        input.setName(weapon.getName());

        boolean basicMode = false;

        boolean alternateMode = false;

        for (ChoiceBox<ShootMode> choiceBox : modes) {
            if (choiceBox.getValue() == ShootMode.BASIC)
                basicMode = true;
            if (choiceBox.getValue() == ShootMode.ALTERNATE)
                alternateMode = true;
        }

        if (! (basicMode || alternateMode))
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

        if (! arraySquare.isEmpty()) {
            x = arraySquare.get(0).getValue();
            y = arraySquare.get(1).getValue();
            input.setSquaresCoordinates(x, y);
        }

        if (arraySquare.size() > 2) {
            x = arraySquare.get(2).getValue();
            y = arraySquare.get(3).getValue();
            input.setSquaresCoordinates(x, y);
        }

        if(direction != null)
            input.setDirection(direction.getValue());

        if(damageBeforeMoveBox != null && input != null)
            input.setMakeDamageBeforeMove(damageBeforeMoveBox.getValue());

    }

    public void setInput(ShootingParametersClient input) {
        this.input = input;
    }

    public void setSenderRemoteController(SenderClientRemoteController senderRemoteController) {
        this.senderRemoteController = senderRemoteController;
    }

}
