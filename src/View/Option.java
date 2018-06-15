package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Option implements Initializable {
    public javafx.scene.control.Button exit;
    public ChoiceBox algo;
    public ChoiceBox maze;
    public ChoiceBox thread;


    public void close() {
        Platform.exit();
    }

    public void closew() {
        Stage s = (Stage) exit.getScene().getWindow();
        s.close();
    }

    public void save() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        algo.getItems().addAll("BFS", "DFS");
        maze.getItems().addAll("Simple Maze", "MyMaze");
        thread.getItems().addAll("1", "2", "3", "4");

    }
}