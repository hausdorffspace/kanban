package pl.sda.kanbanBoard.server.server_Logic;

import pl.sda.kanbanBoard.server.BasicServer;
import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryImplementation;
import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

import static pl.sda.kanbanBoard.common.ServerRequests.CREATE_TASK;
import static pl.sda.kanbanBoard.common.ServerRequests.GET_ALL_TASKS;
import static pl.sda.kanbanBoard.common.ServerResponses.ALL_TASKS;
import static pl.sda.kanbanBoard.common.ServerResponses.TASK_CREATED;

public class ClientHandler implements Runnable {
    BufferedReader reader;
    Socket socket;
    BasicServer server;

    Random random = new Random();

    public Integer id() {
        return random.nextInt();
    }
    public void setServer(BasicServer server) {
        this.server = server;
    }

    public ClientHandler(Socket clientSocket, BasicServer server) {
        try {
            setServer(server);
            socket = clientSocket;
            InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(isReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        String message;
        TaskRepositoryInterface fileHandler = new TaskRepositoryImplementation();
        try {
            while ((message = reader.readLine()) != null) {
                if (message.contains(CREATE_TASK)) {
                    Integer id = id();
                    if (fileHandler.writeDataToFile(message, id)) {
                        server.send(TASK_CREATED + id + "," + message.split(":")[1]);
                    } else {
                        server.send("Task isn't created!!!!!");
                    }
                } else if (message.contains(GET_ALL_TASKS)) {
                    String dataFromFile = fileHandler.takeDataFromFile();
                    server.send(ALL_TASKS + dataFromFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
