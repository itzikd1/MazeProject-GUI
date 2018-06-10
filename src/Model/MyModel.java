package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
/*
responsible for all the function part
 */
public class MyModel extends Observable implements IModel {
    private Maze maze;
    private Solution solve;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private KeyEvent keyEvent;

    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }


    @Override
    public void generateMaze(int width, int height) {
        MyMazeGenerator newMaze = new MyMazeGenerator();
        maze = newMaze.generate(width,height);
        Position UpdatePos = new Position(1,1);
        UpdatePos = maze.getStartPosition();
        characterPositionColumn = UpdatePos.getColumnIndex();
        characterPositionRow = UpdatePos.getRowIndex();
        setChanged();
        notifyObservers();
        }

    @Override
    public void moveCharacter(KeyCode movement) {
        int characterRow = getCharacterPositionRow();
        int characterColumn = getCharacterPositionColumn();
        int characterRowNewPosition = characterRow;
        int characterColumnNewPosition = characterColumn;


        if (keyEvent.getCode() == KeyCode.NUMPAD8) {
            characterRowNewPosition = characterRow - 1;
            characterColumnNewPosition = characterColumn;
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD2) {
            characterRowNewPosition = characterRow + 1;
            characterColumnNewPosition = characterColumn;
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD6) {
            characterRowNewPosition = characterRow;
            characterColumnNewPosition = characterColumn+1;
        }
        else if (keyEvent.getCode() == KeyCode.NUMPAD4) {
            characterRowNewPosition = characterRow;
            characterColumnNewPosition = characterColumn -1;
        }
        else if (keyEvent.getCode() == KeyCode.HOME){
            characterRowNewPosition = 0;
            characterColumnNewPosition = 0;
        }
        else if (keyEvent.getCode() == KeyCode.END){
            characterRowNewPosition = 0;
            characterColumnNewPosition = 0;
        }

        //Updates the MazeDisplayer
        setCharacterPosition(characterRowNewPosition, characterColumnNewPosition);

        //Updates the labels
//        CharacterRow.set(String.valueOf(mazeDisplayer.getCharacterPositionRow()));
//        CharacterColumn.set(String.valueOf(mazeDisplayer.getCharacterPositionColumn()));
        keyEvent.consume();
        setChanged();
        notifyObservers();
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
    }

    @Override
    public void generateSolution() {
        Solution keepSolution = new Solution();
        keepSolution.getSolutionPath();
        solve = keepSolution;
        setChanged();
        notifyObservers();
    }
}
