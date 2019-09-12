package pl.sda.kanbanBoard.user.gui;


import javafx.scene.control.Button;

public class TaskButton extends Button {
    private final int id;

    public int getTaskId() {
        return id;
    }

    public TaskButton(int i, String s) {
        super(s);
        this.id = i;



    }
}
