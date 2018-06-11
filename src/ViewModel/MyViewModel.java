package ViewModel;

import Model.IModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;


public class MyViewModel extends Observable implements Observer {

    private IModel model;

    private int characterPositionRowIndex = 1;
    private int characterPositionColumnIndex = 1;

    public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding

    public MyViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            setChanged();
            notifyObservers();
        }
    }

    public int[][] generateMaze(int width, int height) {
        return model.generateMaze(width, height);

    }

    public void moveCharacter(KeyCode movement) {
        model.moveCharacter(movement);
    }

    public int[][] getMaze() {
        return model.getMaze();
    }

    public int getCharacterPositionRow() {
        return characterPositionRowIndex;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumnIndex;
    }
}
