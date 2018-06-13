package ViewModel;

import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

//TODO fix generate maze, so end position not on wall (3X3 size)
public class MyViewModel extends Observable implements Observer {

    private IModel model;

    private int characterPositionRowIndex = 0;
    private int characterPositionColumnIndex = 0;

    public StringProperty characterPositionRow = new SimpleStringProperty("1"); //For Binding
    public StringProperty characterPositionColumn = new SimpleStringProperty("1"); //For Binding

    public MyViewModel(IModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            characterPositionRowIndex = model.getCharacterPositionRow();
            characterPositionRow.set(characterPositionRowIndex + "");
            characterPositionColumnIndex = model.getCharacterPositionColumn();
            characterPositionColumn.set(characterPositionColumnIndex + "");
            setChanged();
            notifyObservers();
        }
    }

    public int[][] generateMaze(int width, int height) {
        int [][] temp =  model.generateMaze(width, height);
        return temp;
    }

    public Position getendposition() {
        return model.getEndpositionl();

    }

    public void moveCharacter(KeyCode movement) {
        model.moveCharacter(movement);
    }

    public boolean gameFinsih (){
       return model.gameFinsih();
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

    public Solution getSolution() {
        return model.generateSolution();
    }

    public boolean isSolved() {
        return model.isSolved();
    }


    public void setCharacterPositionRow(int row) {
        characterPositionRowIndex=row;
        model.setCharacterPositionRow(row);


    }


    public void setCharacterPositionColumn(int col) {
        characterPositionColumnIndex=col;
        model.setCharacterPositionCol(col);
    }

    public void setMaze(int [][] maze){
        model.setMaze(maze);
    }
}
