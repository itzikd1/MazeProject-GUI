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
    private boolean gameFinsih;
    private int characterPositionRow;
    private int characterPositionColumn;
    private Position endposition;
    private int[][] mazeSolutionArr;
    private KeyEvent keyEvent;
    private ExecutorService threadPool = Executors.newCachedThreadPool();


    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public Position getEndpositionl() {
        return endposition;
    }

    @Override
    public boolean gameFinsih() {
        return gameFinsih;
    }

    public void MazeToArr(Maze m) { //TODO from int to byte
        int row = m.numOfRows();
        int col = m.numOfColumns();
        maze = new int[m.numOfRows()][m.numOfColumns()];
        for (int i = 0; i < m.numOfRows(); i++)
            for (int j = 0; j < m.numOfColumns(); j++)
                maze[i][j] = m.getCellValue(i, j);
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
        endposition = newMazeGenerate.getGoalPosition();
        gameFinsih=false;
        setChanged();
        notifyObservers();
        return maze;
    }

    private boolean isNotLegalMove(int x, int y) {
        if (x < 0 || y < 0 || x > maze.length - 1 || y > maze[0].length - 1)
            return true;
        if (maze[x][y] == 1)
            return true;
        return false;
    }

    @Override
    public void moveCharacter(KeyCode movement) { //TODO do we need to do this in controler? beacuse then we double the code
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
        if (endposition.getColumnIndex() == getCharacterPositionColumn() && endposition.getRowIndex()==getCharacterPositionRow())
            gameFinsih=true;
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


    public void setCharacterPositionRow(int row){
        this.characterPositionRow=row;
    }

    @Override
    public void setMaze(int[][] maze) {
        this.maze=maze;
    }

    public void setCharacterPositionCol(int col){
        this.characterPositionColumn=col;
    }
}
