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
    public boolean deleteDataFromFile(String s) {
        Random rd = new Random();

        String data = takeDataFromFile();
        String taskToDelete = s.split(":")[1].trim();
        String[] taskArray = data.split("\\|", 1000);
        for (int i = 0; i < taskArray.length ; i++) {
            if(taskArray[i].contains(taskToDelete)){
                taskArray[i] ="";
                System.out.println(" TASK DELITED");
            }else {
                try {
                    writeDataToFile(taskArray[i], rd.nextInt() );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String newData = convertArrayToString(taskArray);
        System.out.println(newData);
        PrintWriter pw;
        // BufferedReader object for input.txt
        BufferedReader br1;
        try {
           /* File file = new File("baseData.txt");
            file.delete();*/


           /*   pw = new PrintWriter("baseData.txt");
            pw.println(newData);

            pw.close();*/
        } catch (Exception e) {
            e.printStackTrace();
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
