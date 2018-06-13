package Model;

import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

public interface IModel {

    int[][] generateMaze(int width, int height);

    void moveCharacter(KeyCode movement);

    int[][] getMaze();

    Solution generateSolution();

    boolean isSolved();

    int getCharacterPositionRow();

    int getCharacterPositionColumn();

    void setMaze(int [][] maze);

    void setCharacterPositionRow(int row);

    void setCharacterPositionCol(int col);
}
