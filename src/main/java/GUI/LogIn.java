package GUI;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.io.*;

public class LogIn extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("GridPane");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10)); // lascia i bordi laterali
        grid.setVgap(10); // sapzio verticale tra boxes
        grid.setHgap(8); // spazio orizzontale tra boxes

        Label nameLabel = new Label("Username: ");
        GridPane.setConstraints(nameLabel, 0,0);

        TextField inputName = new TextField("Cippa Lippa");
        GridPane.setConstraints(inputName, 1,0);

        Label passLabel = new Label("Password: ");
        GridPane.setConstraints(passLabel,0,1);

        TextField inputPass = new TextField();
        inputPass.setPromptText("password");
        GridPane.setConstraints(inputPass,1,1);

        Button logButton = new Button("Log in");
        GridPane.setConstraints(logButton,1,2);
        // Validazione Credenziali
        logButton.setOnAction(e -> checkInput(inputName,inputPass));

        grid.getChildren().addAll(nameLabel,inputName,passLabel,inputPass,logButton);

        Scene scene = new Scene(grid,300,200);
        scene.getStylesheets().add((new File("." + File.separatorChar + "src" + File.separatorChar + "main"
                + File.separatorChar + "resources" + File.separatorChar + "GridPanelLayout.css")).toURI().toString());
        stage.setScene(scene);
        stage.show();


    }

    private boolean checkInput(TextField inputName, TextField inputPass){
        if ((inputName.getText().equals("Giovanni")) && (inputPass.getText().equals("Bella"))){
            System.out.println("Accesso autorizzato");
            return true;
        }
        else {
            System.out.println("Accesso negato");
            return false;
        }
    }


}
