package pl.sda.kanbanBoard.user.api;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class ServerWriter {
    private final PrintWriter writer;
    Scanner sc = new Scanner(System.in);
    public ServerWriter(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
    }

    public void write(String text) {
        writer.println(text);
        writer.flush();
    }
    public void sendMessage(){
        writer.println(sc);
        System.out.println(" printed " + sc);
        writer.flush();
    }

}
