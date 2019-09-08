package pl.sda.kanbanBoard.user.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.sda.kanbanBoard.user.api.ServerHandler;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MainBoardController {

    MainBoardController mainBoardController;
    PrintWriter writer;
    Socket socket;
    BufferedReader reader;

    @FXML
    private Label label;

    @FXML
    private TextField message;

    @FXML
    private Button createTaskButton;

    ServerHandler serverHandler = new ServerHandler(writer, socket, reader, message, label);

    @FXML
    public void initialize() {
        serverHandler.configureCommunication();
        //MainBoardController mainBoardController = this.mainBoardController;
        Thread recieverThread = new Thread(serverHandler.getReciever());
        recieverThread.start();
    }

    public void createTask() {
        serverHandler.createTask();
    }





}
