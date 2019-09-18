package pl.sda.kanbanBoard.user.gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import pl.sda.kanbanBoard.user.api.ServerWriter;

import static pl.sda.kanbanBoard.common.ServerRequests.*;

public class MainBoardController {

    private ServerWriter serverWriter;
    @FXML
    private VBox toDoPane;
    @FXML
    private VBox doingPane;
    @FXML
    private VBox donePane;
    @FXML
    private TextField newTaskName;

    Dragboard db;
    Dragboard db1;

    void moveTasktoDoing(Dragboard db) {
        serverWriter.write(MOVE_TASK + db.getString() + ".1");
    }

    void moveTasktoDone(Dragboard db) {
        serverWriter.write(MOVE_TASK + db.getString() + ".2");
    }

    @FXML
    void createTask() {
        serverWriter.write(CREATE_TASK + newTaskName.getText() + ".0");
        newTaskName.clear();
    }

    @FXML
    public void initialize() {

        // Drag and drop handling for panes.
        doingPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != doingPane &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });
        doingPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    moveTasktoDoing(db);
                    db = null;
                    /*TaskButton task = new TaskButton(1, db.getString());
                    task.setStyle("-fx-background-color:yellow; -fx-opacity: 0.8;");
                    task.setPrefWidth(200);
                    task.setPrefHeight(100);
                    doingPane.getChildren().add(task);*/
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
        donePane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != donePane &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });
        donePane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    moveTasktoDone(db);
                    db = null;
                    /*TaskButton task = new TaskButton(1, db.getString());
                    task.setStyle("-fx-background-color:yellow; -fx-opacity: 0.8;");
                    task.setPrefWidth(200);
                    task.setPrefHeight(100);
                    doingPane.getChildren().add(task);*/
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

    }

    public void getAllTasks() {
        serverWriter.write(GET_ALL_TASKS);
    }

    public void setServerWriter(ServerWriter serverWriter) {
        this.serverWriter = serverWriter;
    }
    //adding to task string the number of pane where it belnogs... 0 ->ToDO, 1->Doing, 2->done

    public void handleTaskMoved(String s) {
        TaskButton newTask = new TaskButton(Integer.parseInt(s.split(",")[0].trim()), s.split(",")[1].split(".")[0], Integer.parseInt(s.split(".")[1]));
        newTask.setStyle("-fx-background-color:yellow; -fx-opacity: 0.8;");
        newTask.setPrefWidth(100);
        newTask.setPrefHeight(100);
        doingPane.getChildren().add(newTask);
        System.out.println("handled move task (added)");
    }
    //IF THIS WORKS FOR CREATION OF NEW TASK WHY IT DOESNT WORKS FOR MOVING?? IF ATER RESTART ALL MOVED TASKS ARE VISIBLE AND ARE HANDLED...????

    public void handleTask(String s) {
        Integer taskId = Integer.parseInt(s.split(",")[0].trim());
        String taskTxt = s.split(",")[1].split("\\.")[0];
        System.out.println(s.split("\\.")[1]);
        String taskStatusS  = s.split("\\.")[1];
        Integer taskStatus = Integer.parseInt(taskStatusS);
        TaskButton newTask = new TaskButton(taskId, taskTxt, taskStatus);
        if (taskStatus == 0) {
            newTask.setStyle("-fx-background-color:red; -fx-opacity: 0.8;");
            newTask.setPrefWidth(200);
            newTask.setPrefHeight(100);
            toDoPane.getChildren().add(newTask);
            System.out.println("handled task created(added)");
        } else if (taskStatus == 1) {
            newTask.setStyle("-fx-background-color:yellow; -fx-opacity: 0.8;");
            newTask.setPrefWidth(200);
            newTask.setPrefHeight(100);
            doingPane.getChildren().add(newTask);
            System.out.println("handled move task to doing(added)");
        } else if (taskStatus == 2) {
            newTask.setStyle("-fx-background-color:green; -fx-opacity: 0.8;");
            newTask.setPrefWidth(200);
            newTask.setPrefHeight(100);
            donePane.getChildren().add(newTask);
            System.out.println("handled move task to done (added)");
            //Drag and drop button handling
        }
            newTask.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    db1 = newTask.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(newTask.getText());
                    db1.setContent(content);
                    System.out.println("mouse dragged");
                    event.consume();
                }
            });
        }

        public void handleAllTasks (String s){
            String[] tasksArray = s.split("\\|");
            for (String task : tasksArray) {
                // handleTaskMoved(task);
                // System.out.println("handled move task");
                handleTask(task);
                System.out.println("handled some task");
            }
        }

    }


