package pl.sda.kanbanBoard.server.server_Logic;

import pl.sda.kanbanBoard.server.BasicServer;
import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryImplementation;
import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

import static pl.sda.kanbanBoard.common.ServerRequests.*;
import static pl.sda.kanbanBoard.common.ServerResponses.*;

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
                        System.out.println("sending to server");
                    } else {
                        server.send("Task isn't created!!!!!");
                    }
                } else if (message.contains(GET_ALL_TASKS)) {
                    String dataFromFile = fileHandler.takeDataFromFile();
                    server.send(ALL_TASKS + dataFromFile);
                }else if(message.contains(MOVE_TASK)){
                    Integer id = id();
                    if (fileHandler.writeDataToFile(message, id)){
                        server.send(TASK_MOVED + id + "," + message.split(":")[1]);
                      //  if (fileHandler.deleteDataFromFile(message)){

                       // }
                    }else{
                        server.send("Task NOT moved!");
                    }
                }else if(message.contains(DELETE_TASK)) {
                    if(fileHandler.deleteDataFromFile(message)){
                        server.send(TASK_DELETED + message.split(":")[1]);
                        System.out.println("task deleteeeeed");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
