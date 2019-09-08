package pl.sda.kanbanBoard.server;

import pl.sda.kanbanBoard.common.ServerRequests;
import pl.sda.kanbanBoard.common.ServerResponses;
import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryImplementation;
import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryInterface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import static pl.sda.kanbanBoard.common.ServerRequests.CREATE_TASK;
import static pl.sda.kanbanBoard.common.ServerRequests.GET_ALL_TASKS;
import static pl.sda.kanbanBoard.common.ServerResponses.ALL_TASKS;
import static pl.sda.kanbanBoard.common.ServerResponses.TASK_CREATED;

public class BasicServer {
    ArrayList outputStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket socket;

        public ClientHandler(Socket clientSocket) {
            try {
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
                        if (fileHandler.writeDataToFile(message)) {
                            send(TASK_CREATED +message.split(":")[1]);
                        } else {
                            send("Task isn't creat!!!!!");
                        }
                    } else if (message.contains(GET_ALL_TASKS)) {
                        String dataFromFile = fileHandler.takeDataFromFile();
                        send(ALL_TASKS +dataFromFile);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        Iterator it = outputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        outputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                outputStreams.add(writer);
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
                System.out.println("We have connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //TODO
    //mylna metoda z klasy Thread,  zmienic nazwe
    public static void main(String[] args) {
        new BasicServer().start();
    }

}

