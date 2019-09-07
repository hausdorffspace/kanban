package pl.sda.kanbanBoard.user.api;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerHandler extends Thread{
    PrintWriter writer;
    Socket socket;
    BufferedReader reader;
    @FXML
    TextField message;
    @FXML
    Label label;

    public ServerHandler(PrintWriter writer, Socket socket, BufferedReader reader, TextField message, Label label) {
        this.writer = writer;
        this.socket = socket;
        this.reader = reader;
        this.message = message;
        this.label = label;

    }

    public void createTask(){
        try{
            writer.println(message.getText());
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        message.setText("");
        message.requestFocus();
    }
    public void configureCommunication(){
        try{
            socket = new Socket("127.0.0.1", 5000);
            InputStreamReader readerStream = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(readerStream);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("web handing ready to use...");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    /*public void labelSetter(Label label, String message){
        final String newLabel = message;
        Platform.runLater(() -> label.setText(newLabel));
    }*/
    public Reciever getReciever(){
        return new Reciever();
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
}
