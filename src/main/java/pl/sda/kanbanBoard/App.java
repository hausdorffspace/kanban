package pl.sda.kanbanBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class App extends Application {
    public static void main(String[] args) {
        launch(args);

    }


    public void start(Stage primaryStage) throws Exception {

        Pane mainMenu = FXMLLoader.load(getClass().getResource("/fxml/main_board.fxml"));
        Scene scene = new Scene(mainMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Kanban Board");
        primaryStage.show();

    }
}
