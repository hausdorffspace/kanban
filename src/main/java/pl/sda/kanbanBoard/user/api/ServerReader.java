package pl.sda.kanbanBoard.user.api;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pl.sda.kanbanBoard.user.gui.MainBoardController;

import java.io.*;
import java.net.Socket;

import static pl.sda.kanbanBoard.common.ServerResponses.ALL_TASKS;
import static pl.sda.kanbanBoard.common.ServerResponses.TASK_CREATED;

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
                if (split[0].contains(TASK_CREATED)) {
                    controller.handleTaskCreated(split[1]);
                }else if(split[0].contains(ALL_TASKS)){
                    controller.handleAllTasks(split[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    }
