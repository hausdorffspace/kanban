package pl.sda.kanbanBoard.user.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.sda.kanbanBoard.user.api.ServerWriter;

import static pl.sda.kanbanBoard.common.ServerRequests.CREATE_TASK;
import static pl.sda.kanbanBoard.common.ServerRequests.GET_ALL_TASKS;

public class MainBoardController {

    @FXML
    private VBox toDoPane;

    @FXML
    private TextField newTaskName;

    @FXML
    void createTask() {
        serverWriter.write(CREATE_TASK + newTaskName.getText());
    }

    private ServerWriter serverWriter;

      @FXML
    public void initialize() {
          getAllTasks();
    }

    private void getAllTasks(){
          serverWriter.write(GET_ALL_TASKS);
    }

    public void setServerWriter(ServerWriter serverWriter) {
        this.serverWriter = serverWriter;
    }

    public void handleTaskCreated(String s) {
          Button newTask = new Button(s.split(",")[1]);
          toDoPane.getChildren().add(newTask);
    }

    public void handleAllTasks(String s) {
        String[] tasksArray = s.split("|");
        for (String task : tasksArray) {
            handleTaskCreated(task);
        }
        }
    }

