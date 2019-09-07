package pl.sda.kanbanBoard.user.server_logic;

public interface ServerHandlerInterface {
    void createTask();
    void moveTask();
    void updateTask();
    void addComment();
    void deleteTask();
}
