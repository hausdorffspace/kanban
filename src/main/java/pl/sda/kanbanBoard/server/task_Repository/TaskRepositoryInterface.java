package pl.sda.kanbanBoard.server.task_Repository;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface TaskRepositoryInterface {
    boolean writeDataToFile(String message) throws IOException;
    void deleteDataFromFile();
    String takeDataFromFile();
}
