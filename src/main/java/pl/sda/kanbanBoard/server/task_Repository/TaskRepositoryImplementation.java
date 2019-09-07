package pl.sda.kanbanBoard.server.task_Repository;

import java.io.*;

public class TaskRepositoryImplementation implements TaskRepositoryInterface {
    @Override
    public void writeDataToFile(String message) throws FileNotFoundException {
        try{
            BufferedWriter saveToFileObject = new BufferedWriter(new FileWriter("baseData.txt"));
            saveToFileObject.write(message);
            saveToFileObject.newLine();
        } catch (Exception e){
            e.getStackTrace();
        }

        //PrintWriter saveToFileMessage = new PrintWriter("baseData.txt");
    }

    @Override
    public void deleteDataFromFile() {
                    
    }
}
