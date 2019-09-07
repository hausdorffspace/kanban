package pl.sda.kanbanBoard.user.api;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ServerWriter {
    private final PrintWriter writer;

    public ServerWriter(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
    }

    public void createTask(String text) {
        writer.println(text);
        writer.flush();

    }
}
