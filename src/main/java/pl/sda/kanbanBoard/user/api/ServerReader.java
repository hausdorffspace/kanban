package pl.sda.kanbanBoard.user.api;

import javafx.application.Platform;
import javafx.scene.control.Label;
import pl.sda.kanbanBoard.user.gui.MainBoardController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerReader implements Runnable {
    MainBoardController controller;
    BufferedReader reader;

    public ServerReader(MainBoardController controller, InputStream stream) {
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
                final String newLabel = line;
                Platform.runLater(() -> controller.taskCreated(newLabel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}