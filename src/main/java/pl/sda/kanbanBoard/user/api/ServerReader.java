package pl.sda.kanbanBoard.user.api;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ServerReader extends Thread{

    BufferedReader reader;

    public ServerReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream));
    }

    public void run() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
