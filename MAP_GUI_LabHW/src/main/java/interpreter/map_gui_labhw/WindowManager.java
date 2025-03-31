package interpreter.map_gui_labhw;

import Model.HardcodedPrograms;
import Model.Stmt.IStmt;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager extends Application {
    SelectProgWindowController selectProgWindowController;
    RunningProgWindowController runningProgWindowController;

    Stage runningProgStage;
    Stage selectProgStage;

    @Override
    public void start(Stage stage) throws IOException {
        {
            FXMLLoader fxmlLoader = new FXMLLoader(Interpreter.class.getResource("SelectProg.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Program Window");
            stage.setScene(scene);
            stage.show();

            selectProgStage = stage;
            selectProgWindowController = fxmlLoader.getController();

            stage.onCloseRequestProperty().set(event -> {
                if (runningProgStage != null)
                    runningProgStage.close();
            });
        }

        {
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Interpreter.class.getResource("RunningProg.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Running window");
            stage.setScene(scene);
            stage.show();

            runningProgStage = stage;
            runningProgWindowController = fxmlLoader.getController();

            stage.onCloseRequestProperty().set(event -> {
                selectProgStage.close();
            });

            selectProgWindowController.setRunningProgController(runningProgWindowController);
        }
    }

    public void main() {
        launch();
    }
}
