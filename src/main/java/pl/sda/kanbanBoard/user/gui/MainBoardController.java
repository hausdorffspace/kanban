package pl.sda.kanbanBoard.user.gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;


public class MainBoardController {
    MainBoardController mainBoardController;
    PrintWriter writer;
    Socket socket;
    DataInputStream reader;

    @FXML
    public void initialize() {
        configureCommunication();
        MainBoardController mainBoardController = this.mainBoardController;
        Thread recieverThread = new Thread(new Reciever());
        recieverThread.start();
    }


    @FXML
    private Label label;

    @FXML
    private TextField message;

    @FXML
    private Button createTaskButton;
    setter setter;

    public void createTask(ActionEvent actionEvent) {
        try{
            writer.println(message.getText());
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        message.setText("");
        message.requestFocus();
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public TextField getMessage() {
        return message;
    }

    public void setMessage(TextField message) {
        this.message = message;
    }

    public Button getCreateTaskButton() {
        return createTaskButton;
    }

    public void setCreateTaskButton(Button createTaskButton) {
        this.createTaskButton = createTaskButton;
    }
    private void configureCommunication(){
        try{
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader readerStream = new InputStreamReader(socket.getInputStream());
            reader = new DataInputStream(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("web handing ready to use...");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public class Reciever implements Runnable{
        public void run(){
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    System.out.println("Readed " + line);

                    final String newLabel = line;
                    Platform.runLater(() -> label.setText(newLabel));

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public  class setter{
        Label label;
        String message;

        setter(Label label, String message){
            this.label = label;
            this.message = message;


        }

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

   /* public class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
            try{
                writer.println(message.getText());
                writer.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
            message.setText("");
            message.requestFocus();
        }
    }*/

}
