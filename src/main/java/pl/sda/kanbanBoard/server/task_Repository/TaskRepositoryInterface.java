package pl.sda.kanbanBoard.server.task_Repository;

import java.io.FileNotFoundException;

public interface TaskRepositoryInterface {
    void writeDataToFile(String message) throws FileNotFoundException;
    void deleteDataFromFile();
}
