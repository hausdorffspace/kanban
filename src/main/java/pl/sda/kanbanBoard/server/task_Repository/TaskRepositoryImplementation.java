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
        BufferedReader takeFromFile = null;
        try {
            takeFromFile = new BufferedReader(new FileReader("baseData.txt" ));
            String fileContent = takeFromFile.readLine();
            return fileContent;

        } catch (Exception e) {
            e.getStackTrace();
        }
        finally {
            try {
                takeFromFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
