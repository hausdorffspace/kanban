package pl.sda.kanbanBoard.user.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.sda.kanbanBoard.user.api.ServerWriter;

public class MainBoardController {

    @FXML
    private Label label;

    @FXML
    private TextField message;

    @FXML
    private Button createTaskButton;

    private ServerWriter serverWriter;

      @FXML
    public void initialize() {
    }

    public void createTask() {
        serverWriter.createTask(message.getText());
    }

    public void taskCreated(String newLabel) {
          String oldLabel = label.getText();
          label.setText(oldLabel + '\n' +newLabel);
          message.clear();
    }


    public void setServerWriter(ServerWriter serverWriter) {
        this.serverWriter = serverWriter;
    }
}
