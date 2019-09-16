package pl.sda.kanbanBoard.user.server_logic;

public interface UserHandlerInterface {
    void createTask();
    void moveTask();
    void updateTask();
    void addComment();
    void deleteTask();
}
