package pl.sda.kanbanBoard.server.task_Repository;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface TaskRepositoryInterface {
    boolean writeDataToFile(String message, Integer id) throws IOException;
    boolean deleteDataFromFile(String s);
    String takeDataFromFile();
}
