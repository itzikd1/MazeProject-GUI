package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
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
                "This is a game of maze and you need to get the charcter "+
                "to the end of the maze where the flag is.\n"+
                "Hope you enjoy our game! good luck");
    }
}