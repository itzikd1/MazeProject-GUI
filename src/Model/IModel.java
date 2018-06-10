package Model;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;

public interface IModel {

    void generateMaze(int width,int height);
    void moveCharacter(KeyCode movement);
    void generateSolution();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
}
