package Model;

import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

public interface IModel {

    int [][] generateMaze(int width,int height);
    void moveCharacter(KeyCode movement);
    int[][] getMaze();
    Solution generateSolution();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
}
