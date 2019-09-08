package pl.sda.kanbanBoard.server.task_Repository;

import jdk.nashorn.internal.runtime.JSONFunctions;

import java.io.*;

public class TaskRepositoryImplementation implements TaskRepositoryInterface {
    public static Integer ID = 0;

    @Override
    public boolean writeDataToFile(String message) throws IOException {
        BufferedWriter saveToFileObject = null;
        try {
            saveToFileObject = new BufferedWriter(new FileWriter("baseData.txt", true));
            saveToFileObject.write(ID + ", " + message.split(":")[1] + "| ");
            ID++;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        } finally {
            saveToFileObject.close();
            return true;
        }
    }

    @Override
    public void deleteDataFromFile() {

    }

    @Override
    public String takeDataFromFile() {
        return null;
    }
}
