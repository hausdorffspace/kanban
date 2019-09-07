package pl.sda.kanbanBoard.server.task_Repository;

import jdk.nashorn.internal.runtime.JSONFunctions;

import java.io.*;

public class TaskRepositoryImplementation implements TaskRepositoryInterface {
    @Override
    public void writeDataToFile(String message) throws IOException {
        BufferedWriter saveToFileObject = null;
        try {
            saveToFileObject = new BufferedWriter(new FileWriter("baseData.txt"));
            saveToFileObject.write(message);
            saveToFileObject.newLine();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            saveToFileObject.close();
        }
    }

    @Override
    public void deleteDataFromFile() {

    }
}
