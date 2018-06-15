package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {
@TODO
    //TODO load and save
    //TODO size of canvas isn't good
    //TODO hint show 1 step foward, and there is a counter so we know what is the next step
    //TODO option,add what algorithem to run and what type of maze to create
    //TODO change images to something nice, add backround
    //TODO add music
    //TODO add a clock to main screen, find on internet the code

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        model.addObserver(viewModel);
        //--------------
        primaryStage.setTitle("My Application!");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("MyView.fxml").openStream());

        Scene scene = new Scene(root, 800, 700);
//        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        //--------------
        MyViewController view = fxmlLoader.getController();
//        primaryStage.setFullScreen(true);
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        primaryStage.setScene(scene);

        //--------------
        SetStageCloseEvent(primaryStage);
        primaryStage.show();
    }

    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    primaryStage.close();
                    Platform.exit();
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
