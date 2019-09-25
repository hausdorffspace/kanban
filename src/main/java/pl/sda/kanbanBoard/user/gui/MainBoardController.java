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
    void moveTasktoToDo(Dragboard db) {
        serverWriter.write(MOVE_TASK + db.getString() + ".0");
    }
    void moveTasktoDoing(Dragboard db) {
        serverWriter.write(MOVE_TASK + db.getString() + ".1");
    }

    void moveTasktoDone(Dragboard db) {
        serverWriter.write(MOVE_TASK + db.getString() + ".2");
    }
    void deleteTaskFromToDo(Dragboard db){
        serverWriter.write(DELETE_TASK + db.getString() + ".0");
        System.out.println("widzi TODO PANE");
    }
    void deleteTaskFromDoing(Dragboard db){
        serverWriter.write(DELETE_TASK + db.getString() + ".1");
    }
    void deleteTaskFromDone(Dragboard db){
        serverWriter.write(DELETE_TASK + db.getString() + ".2");
    }

    @FXML
    void createTask() {
        serverWriter.write(CREATE_TASK + newTaskName.getText() + ".0");
        newTaskName.clear();
    }

    @FXML
    public void initialize() {

        // Drag and drop handling for panes.
        toDoPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != doingPane &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });
        toDoPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    moveTasktoToDo(db);
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
                    if (newTask.getParent()==toDoPane)
                        deleteTaskFromToDo(db1);
                    else if(newTask.getParent()==doingPane)
                        deleteTaskFromDoing(db1);
                    else if(newTask.getParent()==donePane)
                        deleteTaskFromDone(db1);

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


