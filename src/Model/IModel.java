package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.File;

public interface IModel {

    int[][] generateMaze(int width, int height);

    void moveCharacter(KeyCode movement);

    int[][] getMaze();

    void setGoalPosition(Position goalPosition);

    int[][] getMazeSolutionArr();

    void generateSolution();

    boolean isSolved();

    int getCharacterPositionRow();

    int getCharacterPositionColumn();

    Position getEndPosition();

    boolean gameFinish();

    void setCharacterPositionCol(int col);

    void setCharacterPositionRow(int row);

    void setMaze(int[][] maze);

    void saveCurrentMaze(File file, String name);

    void saveOriginalMaze(File file, String name);

    void loadMaze(File file);

    Maze getOriginal();

}
