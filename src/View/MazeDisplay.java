package View;

import algorithms.mazeGenerators.Position;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplay extends Canvas {

    private int[][] maze;
    private int characterPositionRow;
    private int characterPositionColumn;
    private Position endPosition;
    private int[][] solved;
    private boolean isSolved;

    public void setMaze(int[][] maze) {
        this.maze = maze;
    }

    public void endposition(algorithms.mazeGenerators.Position end) {
        endPosition = end;
    }

    public void Solved(int[][] answer) {
        solved = answer;
    }


    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze[0].length;
            double cellWidth = canvasWidth / maze.length;

            try {
                GraphicsContext graphicsContext2D = getGraphicsContext2D();
                graphicsContext2D.clearRect(0, 0, getWidth(), getHeight()); //Clears the canvas
                Image wallImage = new Image(new FileInputStream("resources/images/wall.jpg"));

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            //graphicsContext2D.fillRect(i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                            graphicsContext2D.drawImage(wallImage, j * cellHeight, i * cellWidth, cellHeight, cellWidth);
                        }
                    }
                }

                //draw end point
                Image endPos = new Image(new FileInputStream("resources/images/end.jpg"));
                graphicsContext2D.drawImage(endPos, endPosition.getColumnIndex() * cellHeight, endPosition.getRowIndex() * cellWidth, cellHeight, cellWidth);

                //Draw solution
                if (isSolved) {
                    Image SolutionImage = new Image(new FileInputStream("resources/images/eat.png"));
                    for (int i = 0; i < solved[0].length; i++) {
                        int x = solved[0][i];
                        int y = solved[1][i];
                        graphicsContext2D.drawImage(SolutionImage, y * cellHeight, x * cellWidth, cellHeight, cellWidth);
                    }
                }


                //Draw Character
                //gc.setFill(Color.RED);
                //gc.fillOval(characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
                Image characterImage = new Image(new FileInputStream("resources/images/char.png"));
                graphicsContext2D.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
            } catch (FileNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(String.format("Image doesn't exist: %s", e.getMessage()));
                alert.show();
            }
        }
    }

    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public void isSolved(boolean solved) {
        this.isSolved = solved;
    }
    //endregion

}
