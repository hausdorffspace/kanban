package pl.sda.kanbanBoard.user;

import pl.sda.kanbanBoard.user.api.ServerReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class serverHandler {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 54345);

        new ServerReader(socket.getInputStream()).start();

        Scanner sc = new Scanner(System.in);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        while (true) {
            printWriter.println(sc.nextLine());
            printWriter.flush();
        }
    }

}
