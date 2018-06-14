package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
responsible for all the function part
 */
public class MyModel extends Observable implements IModel {
    private int[][] maze;
    private Maze Original;
    private boolean solved;
    private boolean gameFinsih;
    private int characterPositionRow;
    private int characterPositionColumn;
    private Position endposition;
    private int[][] mazeSolutionArr;
    //    private KeyEvent keyEvent;
    private ExecutorService threadPool = Executors.newCachedThreadPool();


    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public void setCharacterPositionRow(int row) {
        this.characterPositionRow = row;
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

    private void MazeToArr(Maze m) { //TODO from int to byte
        int row = m.numOfRows();
        int col = m.numOfColumns();
        maze = new int[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
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
        this.Original = newMazeGenerate;
        characterPositionColumn = UpdatePos.getColumnIndex();
        characterPositionRow = UpdatePos.getRowIndex();
        endposition = newMazeGenerate.getGoalPosition();
        System.out.println(endposition);
        newMazeGenerate.print();
        gameFinsih = false;
        setChanged();
        notifyObservers();
        return maze;
    }

    private boolean isNotLegalMove(int x, int y) {
        if (x < 0 || y < 0 || x > maze.length - 1 || y > maze[0].length - 1)
            return true;
        return maze[x][y] == 1;
    }

    @Override
    public void moveCharacter(KeyCode movement) { //TODO do we need to do this in controler? beacuse then we double the code
        int x = characterPositionRow;
        int y = characterPositionColumn;
        switch (movement) {
            case NUMPAD8:
            case W:
                if (!isNotLegalMove(x - 1, y))
                    characterPositionRow--;
                break;
            case NUMPAD2:
            case S:
                if (!isNotLegalMove(x + 1, y))
                    characterPositionRow++;
                break;
            case NUMPAD6:
            case D:
                if (!isNotLegalMove(x, y + 1))
                    characterPositionColumn++;
                break;
            case A:
            case NUMPAD4:
                if (!isNotLegalMove(x, y - 1))
                    characterPositionColumn--;
                break;
            case NUMPAD3:
                if (!isNotLegalMove(x + 1, y + 1))
                    if (!isNotLegalMove(x, y + 1) || !isNotLegalMove(x + 1, y)) {
                        characterPositionColumn++;
                        characterPositionRow++;
                    }
                break;
            case NUMPAD1:
                if (!isNotLegalMove(x + 1, y - 1))
                    if (!isNotLegalMove(x, y - 1) || !isNotLegalMove(x + 1, y)) {
                        characterPositionColumn--;
                        characterPositionRow++;
                    }
                break;
            case NUMPAD9:
                if (!isNotLegalMove(x - 1, y + 1))
                    if (!isNotLegalMove(x - 1, y) || !isNotLegalMove(x, y + 1)) {
                        characterPositionColumn++;
                        characterPositionRow--;
                    }
                break;
            case NUMPAD7:
                if (!isNotLegalMove(x - 1, y - 1))
                    if (!isNotLegalMove(x, y - 1) || !isNotLegalMove(x - 1, y)) {
                        characterPositionColumn--;
                        characterPositionRow--;
                    }
                break;
        }
        if (endposition.getColumnIndex() == getCharacterPositionColumn() && endposition.getRowIndex() == getCharacterPositionRow())
            gameFinsih = true;
        setChanged();
        notifyObservers();

    }

    @Override
    public int[][] getMaze() {
        return maze;
    }


//    public void setCharacterPosition(int row, int column) {
//        characterPositionRow = row;
//        characterPositionColumn = column;
//    }

    @Override
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public boolean isSolved() {
        return this.solved;
    }

    @Override
    public Solution generateSolution() {
        SearchableMaze MazeToSolve = new SearchableMaze(Original);
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        Solution keepSolution = bfs.solve(MazeToSolve);
        ArrayList<AState> solutionPath = keepSolution.getSolutionPath();
        solved = true;
        for (int i = 0; i < solutionPath.size(); i++) {
            System.out.println(String.format("%s. %s", i, solutionPath.get(i)));
        }
        int sizeOfSolution = solutionPath.size();
        mazeSolutionArr = new int[2][sizeOfSolution];
        for (int i = 0; i < solutionPath.size(); i++) {
            mazeSolutionArr[0][i] = ((MazeState) (solutionPath.get(i))).getRow();
            mazeSolutionArr[1][i] = ((MazeState) (solutionPath.get(i))).getCol();
        }
        setChanged();
        notifyObservers();
        return keepSolution;
    }

    public int[][] getMazeSolutionArr() {
        return mazeSolutionArr;
    }

    public void setCharacterPositionCol(int col) {
        this.characterPositionColumn = col;
    }
}
