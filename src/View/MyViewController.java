package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class MyViewController implements Observer, IView {

    @FXML
    private MyViewModel viewModel = new MyViewModel(new MyModel());
    public MazeDisplay mazeDisplayer = new MazeDisplay();
    boolean showOnce = false;
    public javafx.scene.control.TextField txt_row;
    public javafx.scene.control.TextField txt_col;
    public javafx.scene.control.Label lbl_rowsNum;
    public javafx.scene.control.Label lbl_columnsNum;//where user wants to go
    public javafx.scene.control.Button GenerateMaze;
    public javafx.scene.control.Button SolveMaze;

    public StringProperty characterPositionRow = new SimpleStringProperty();
    public StringProperty characterPositionColumn = new SimpleStringProperty();

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        bindProperties(viewModel);
    }

    private void bindProperties(MyViewModel viewModel) {
        lbl_rowsNum.textProperty().bind(viewModel.characterPositionRow);
        lbl_columnsNum.textProperty().bind(viewModel.characterPositionColumn);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
//            mazeDisplayer.setCharacterPosition(viewModel.getCharacterPositionRow(), viewModel.getCharacterPositionColumn());
            displayMaze(viewModel.getMaze());
            GenerateMaze.setDisable(false);
            if (viewModel.gameFinish() && !showOnce) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Game Done");
                alert.show();
                showOnce = true;
            }
            //mazeDisplayer.setCharacterPosition(mazeDisplayer.getCharacterPositionRow(),mazeDisplayer.getCharacterPositionColumn());
            mazeDisplayer.redraw();
        }
    }

    @Override
    public void displayMaze(int[][] maze) {
        int characterPositionRow = viewModel.getCharacterPositionRow();
        int characterPositionColumn = viewModel.getCharacterPositionColumn();
        mazeDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
        mazeDisplayer.endposition(viewModel.getEndPosition());
        mazeDisplayer.Solved(viewModel.getMazeSolutionArr());
        mazeDisplayer.isSolved(viewModel.isSolved());
        this.characterPositionRow.set(characterPositionRow + "");
        this.characterPositionColumn.set(characterPositionColumn + "");
        mazeDisplayer.redraw();
    }

    public void generateMaze() {
        showOnce = false;
        int height;
        int width;
        try {
            height = Integer.valueOf(txt_row.getText());
        } catch (Exception e) {
            height = 10;
        }
        try {
            width = Integer.valueOf(txt_col.getText());
        } catch (Exception e) {
            width = 10;
        }
        int[][] temp = viewModel.generateMaze(height, width);
        mazeDisplayer.setMaze(temp);
        mazeDisplayer.endposition(viewModel.getEndPosition());
        SolveMaze.setVisible(true);
        displayMaze(temp);
    }

    public void solveMaze(ActionEvent actionEvent) {
        showAlert("Solving maze..");
        System.out.println(viewModel.isSolved());
        viewModel.getSolution();
        SolveMaze.setVisible(false);
        System.out.println(viewModel.isSolved());
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }


    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public void KeyPressed(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    //region String Property for Binding

    public String getCharacterPositionRow() {
        return characterPositionRow.get();
    }

    public StringProperty characterPositionRowProperty() {
        return characterPositionRow;
    }

    public String getCharacterPositionColumn() {
        return characterPositionColumn.get();
    }

    public StringProperty characterPositionColumnProperty() {
        return characterPositionColumn;
    }

    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                mazeDisplayer.redraw();
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mazeDisplayer.redraw();
            }
        });
    }

    public void About(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setTitle("AboutController");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            Scene scene = new Scene(root, 300, 165);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            stage.show();
        } catch (Exception e) {
            System.out.println("Error About.fxml not found");
        }
    }


    public void saveGame() {
        int[][] currentMaze = viewModel.getMaze();
        int x1 = viewModel.getCharacterPositionRow();
        int y1 = viewModel.getCharacterPositionColumn();


        try {
            FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(currentMaze);
            o.writeObject(x1);
            o.writeObject(y1);
            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //endregion

    }

    public void loadGame() {
        try {
            FileInputStream fi = new FileInputStream(new File("myObjects.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects

            int[][] currentMaze = (int[][]) oi.readObject();
            int x1 = (int) oi.readObject();
            int y1 = (int) oi.readObject();
            viewModel.setCharacterPositionRow(x1);
            viewModel.setCharacterPositionColumn(y1);
            viewModel.setMaze(currentMaze);
            oi.close();
            fi.close();
            mazeDisplayer.setMaze(currentMaze);
            displayMaze(currentMaze);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    //endregion

}
