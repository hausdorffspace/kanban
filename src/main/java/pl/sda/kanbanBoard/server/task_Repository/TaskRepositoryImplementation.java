package pl.sda.kanbanBoard.server.task_Repository;

import java.io.*;
import java.util.Random;

import static pl.sda.kanbanBoard.common.ServerRequests.*;

public class TaskRepositoryImplementation implements TaskRepositoryInterface {


    @Override
    public boolean writeDataToFile(String message, Integer id) throws IOException {
        BufferedWriter saveToFileObject = null;
        try {
            saveToFileObject = new BufferedWriter(new FileWriter("baseData.txt", true));
            String task = message.split(":")[1];
            saveToFileObject.write(id + "," + task + "|");
            System.out.println("task written to file ");
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        } finally {
            saveToFileObject.close();
            return true;
        }
    }
    public boolean deleteData() throws IOException {
        BufferedWriter saveToFileObject = null;
        try {
            saveToFileObject = new BufferedWriter(new FileWriter("newData.txt"));

            saveToFileObject.write("");
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
    public boolean deleteDataFromFile(String s) {
        Random rd = new Random();

        String data = takeDataFromFile();
        String taskToDelete = s.split(":")[1].trim();
        String[] taskArray = data.split("\\|", 1000);
        for (int i = 0; i < taskArray.length -1 ; i++) {
            if(taskArray[i].contains(taskToDelete)){
                taskArray[i] = null;
                System.out.println(" TASK DELITED");
                if(i ==0 ){
                    File baseData = new File("baseData.txt");
                    baseData.delete();
                }
            } else {
                try {

                    String taskToWrite = taskArray[i].split(",")[1].trim();


                       System.out.println("deleted");
                    writeDataToFile(CREATE_TASK + taskToWrite, rd.nextInt() );
                    System.out.println("writting task nr: " + taskToWrite );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("File operation performed successfully");
        return true;

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
    public static String convertArrayToString(String[] strArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            stringBuilder.append(strArray[i]);

        }
        return stringBuilder.toString();
    }

}
