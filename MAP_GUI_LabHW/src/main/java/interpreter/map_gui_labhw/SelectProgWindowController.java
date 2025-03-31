package interpreter.map_gui_labhw;

import Model.HardcodedPrograms;
import Model.MyException;
import Model.Stmt.IStmt;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class SelectProgWindowController {
	@FXML
	private ListView<String> listView;
	@FXML
	private Label errorText;

	private RunningProgWindowController runProgController;

	public void setRunningProgController(RunningProgWindowController runProgController) {
		this.runProgController = runProgController;
	}

    @FXML
	public void initialize() {
		listView.setItems(FXCollections.observableList(
				HardcodedPrograms.hardcodedPrograms.stream().map(IStmt::toString).toList()
		));
	}

	public void runSelectedProgram(ActionEvent actionEvent) {
		int index = listView.getSelectionModel().getSelectedIndex();

		// debugging with console prints
		if (index < 0) {
			System.out.println("No index selected");
		} else if (index >= HardcodedPrograms.hardcodedPrograms.size()) {
			System.out.println("No program at selected index");
		} else {
			System.out.println("Selected program " + (index+1));
		}

		try {
			errorText.setText("");
			runProgController.setProgram(index);
		} catch (MyException e) {
			cantRunProgram();
		}
	}

	@FXML
	private void cantRunProgram() {
		errorText.setText("Can't run program (type checker failed)");
		new Thread(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException _) {}

			Platform.runLater(() -> errorText.setText(""));
		}).start();
	}
}
