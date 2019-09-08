package pl.sda.kanbanBoard.server.task_Repository;

import java.io.IOException;

public class TEST {
    public static void main(String[] args) {
        TaskRepositoryInterface taskRepositoryInterface = new TaskRepositoryImplementation();
        try {
            for (int i = 0; i < 5; i++){
                taskRepositoryInterface.writeDataToFile("(task"+i+")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
