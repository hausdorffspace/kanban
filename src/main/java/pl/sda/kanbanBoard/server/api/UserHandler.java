package pl.sda.kanbanBoard.server.api;

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
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equalsIgnoreCase("ping")) {
                    printWriter.println("pong");
                    printWriter.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
