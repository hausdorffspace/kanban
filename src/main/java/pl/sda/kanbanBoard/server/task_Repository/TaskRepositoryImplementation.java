package pl.sda.kanbanBoard.server.task_Repository;

import java.io.*;
import java.util.Random;

public class TaskRepositoryImplementation implements TaskRepositoryInterface {


    @Override
    public boolean writeDataToFile(String message, Integer id) throws IOException {
        BufferedWriter saveToFileObject = null;
        try {
            saveToFileObject = new BufferedWriter(new FileWriter("baseData.txt", true));
            saveToFileObject.write(id + "," + message.split(":")[1] + "|");
            System.out.println("task written to file ");
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
