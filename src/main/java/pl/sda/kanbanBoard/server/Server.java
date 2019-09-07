package pl.sda.kanbanBoard.server;

import pl.sda.kanbanBoard.server.api.UserHandler;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(5000);

        while(true) {
            new UserHandler(serverSocket.accept()).start();
        }
    }
}