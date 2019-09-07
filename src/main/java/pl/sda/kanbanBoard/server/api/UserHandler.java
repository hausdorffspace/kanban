package pl.sda.kanbanBoard.server.api;

import javafx.fxml.FXMLLoader;
import pl.sda.kanbanBoard.user.gui.MainBoardController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserHandler extends Thread {
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public UserHandler(Socket client) {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter = new PrintWriter(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_board.fxml"));
        MainBoardController c = loader.getController();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {


                    printWriter.println(line);
                    printWriter.flush();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
