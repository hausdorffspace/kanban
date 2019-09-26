package pl.sda.kanbanBoard.user.api;

import javafx.application.Platform;
import pl.sda.kanbanBoard.user.gui.MainBoardController;

import java.io.*;

public class ServerReader implements Runnable {
    MainBoardController controller;
    BufferedReader reader;
    public ServerReader(MainBoardController controller, InputStream stream) throws IOException {
        this.controller = controller;
        this.reader = new BufferedReader(new InputStreamReader(stream));

    }

    public BufferedReader getReader() {
        return reader;
    }

    public void run() {
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println("Readed " + line);
                String[] split = line.split(":");
                if (split[0].contains("TASK_CREATED")) {

                    Platform.runLater(() ->controller.handleTask(split[1]));
                }else if(split[0].contains("ALL_TASKS")){

                    System.out.println();
                    Platform.runLater(()->controller.handleAllTasks(split[1]));
                }else if(split[0].contains("TASK_MOVED")){

                    Platform.runLater(() -> controller.handleTask(split[1]));
                }
                else if(split[0].contains("TASK_DELETED")) {
                    System.out.println("TASK DELETED coś");
                    Platform.runLater(() -> controller.getDoingPane().getChildren().clear());
                    Platform.runLater(() -> controller.getDonePane().getChildren().clear());
                    Platform.runLater(() -> controller.getToDoPane().getChildren().clear());
                    Platform.runLater(()->controller.handleAllTasks(split[1]));
                    System.out.println(split[1]);
                }

                   // Platform.runLater(() -> controller.handleAllTasks(split[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    }
