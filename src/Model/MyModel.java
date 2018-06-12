package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
responsible for all the function part
 */
public class MyModel extends Observable implements IModel {
    private int[][] maze;
    private Solution solve;
    private boolean solved;
    private int characterPositionRow;
    private int characterPositionColumn;
    private int[][] mazeSolutionArr;
    private KeyEvent keyEvent;
    private ExecutorService threadPool = Executors.newCachedThreadPool();


    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public void MazeToArr(Maze m) {
        int row = m.numOfRows();
        int col = m.numOfColumns();
        maze = new int[m.numOfRows()][m.numOfColumns()];
        for (int i = 0; i < m.numOfRows(); i++)
            for (int j = 0; j < m.numOfColumns(); j++)
                maze[i][j] = m.getCellValue(i, j); //TODO rannan check if this is good rows\col
    }

    @Override
    public int[][] generateMaze(int width, int height) {
        //Generate maze
        solved = false;
        MyMazeGenerator newMaze = new MyMazeGenerator();
        Maze newMazeGenerate = newMaze.generate(width, height);
        MazeToArr(newMazeGenerate);
        Position UpdatePos = new Position(1, 1);
        UpdatePos = newMazeGenerate.getStartPosition();
        characterPositionColumn = UpdatePos.getColumnIndex();
        characterPositionRow = UpdatePos.getRowIndex();
        setChanged();
        notifyObservers();
        return maze;
    }

    private boolean isNotLegalMove(int x, int y) {
        if (x < 0 || y < 0 || x > maze.length - 1 || y > maze.length - 1)
            return true;
        if (maze[x][y] == 1)
            return true;
        return false;
    }

    @Override
    public void moveCharacter(KeyCode movement) {
        int y = characterPositionRow;
        int x = characterPositionColumn;
        switch (movement) {
            case NUMPAD8:
                if (isNotLegalMove(x, y - 1) == false)
                    characterPositionRow--;
                break;
            case NUMPAD2:
                if (isNotLegalMove(x, y + 1) == false)
                    characterPositionRow++;
                break;
            case NUMPAD6:
                if (isNotLegalMove(x + 1, y) == false)
                    characterPositionColumn++;
                break;
            case NUMPAD4:
                if (isNotLegalMove(x - 1, y) == false)
                    characterPositionColumn--;
                break;
            case NUMPAD3:
                if (isNotLegalMove(x + 1, y + 1) == false)
                    if (isNotLegalMove(x, y + 1) == false || isNotLegalMove(x + 1, y) == false) {
                        characterPositionColumn++;
                        characterPositionRow++;
                    }
                break;
            case NUMPAD1:
                if (isNotLegalMove(x - 1, y + 1) == false)
                    if (isNotLegalMove(x, y + 1) == false || isNotLegalMove(x - 1, y) == false) {
                        characterPositionColumn--;
                        characterPositionRow++;
                    }
                break;
            case NUMPAD9:
                if (isNotLegalMove(x + 1, y - 1) == false)
                    if (isNotLegalMove(x, y - 1) == false || isNotLegalMove(x + 1, y) == false) {
                        characterPositionColumn++;
                        characterPositionRow--;
                    }
                break;
            case NUMPAD7:
                if (isNotLegalMove(x - 1, y - 1) == false)
                    if (isNotLegalMove(x, y - 1) == false || isNotLegalMove(x - 1, y) == false) {
                        characterPositionColumn--;
                        characterPositionRow--;
                    }
                break;
        }
        setChanged();
        notifyObservers();

    }

    @Override
    public int[][] getMaze() {
        return maze;
    }

    public boolean isSolved() {
        return this.solved;
    }


    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
    }

    @Override
    public Solution generateSolution() {
        Solution keepSolution = new Solution();
        keepSolution.getSolutionPath();
        solve = keepSolution;
        solved = true;
        setChanged();
        notifyObservers();
        return solve;
    }

    public int[][] getMazeSolutionArr() {
        return mazeSolutionArr;
    }
}
