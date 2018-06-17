package View;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Properties;
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

    public static void SetConf() throws IOException {
        OutputStream output = null;
        InputStream input = null;
        String text = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./resources/config.properties"));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line + ",");
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            text = stringBuilder.toString();
            bufferedReader.close();
        } catch (IOException e) {
        }
        if (input == null) {//check if file exthist
            output = new FileOutputStream("Resources/config.properties");
            Properties prop = new Properties(); //create new prop file

            // set the properties value
            prop.setProperty("MazeAlgoType", "test");
            prop.setProperty("numberCores", "2");
            prop.setProperty("MazeType", "MyMazeGenerator");

            // save properties to project root folder
            prop.store(output, null);
        }

        if (output != null) {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
