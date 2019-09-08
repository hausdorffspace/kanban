package pl.sda.kanbanBoard.server;

import pl.sda.kanbanBoard.server.task_Repository.TaskRepositoryImplementation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class BasicServer {
    ArrayList outputStreams;

    public  class ClientHandler implements Runnable {
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
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("Readed" + message);
                    send(message);
                    TaskRepositoryImplementation taskRepositoryImplementation = new TaskRepositoryImplementation();
                    taskRepositoryImplementation.writeDataToFile(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message){
            Iterator it = outputStreams.iterator();
            while (it.hasNext()){
                try{
                    PrintWriter writer = (PrintWriter) it.next();
                    writer.println(message);
                    writer.flush();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

    }
    public void start(){
        outputStreams = new ArrayList();
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true){
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                outputStreams.add(writer);
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                System.out.println("We have connection");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new BasicServer().start();
    }

}

