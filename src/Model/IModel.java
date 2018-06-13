package Model;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

public interface IModel {

    int[][] generateMaze(int width, int height);

    void moveCharacter(KeyCode movement);

    int[][] getMaze();

    int[][] getMazeSolutionArr();

    Solution generateSolution();

    boolean isSolved();

    int getCharacterPositionRow();

    int getCharacterPositionColumn();

    Position getEndpositionl();

    boolean gameFinsih();

    void setCharacterPositionCol(int col);

    void setCharacterPositionRow(int row);

    void setMaze(int[][] maze);
}
