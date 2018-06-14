package View;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class About implements Initializable {
    public javafx.scene.control.Button exit;
    public javafx.scene.control.Label text;

    public void close() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        text.setWrapText(true);
        text.setText("Hello and welcome to Itzik and Raanan's project.\n" +
                "Here you will see our project we made in java.\n" +
                "This is a game of maze and you need to get the character " +
                "to the end of the maze where the flag is.\n" +
                "Hope you enjoy our game! good luck");
    }
}