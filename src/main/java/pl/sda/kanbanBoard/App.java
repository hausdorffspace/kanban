package pl.sda.kanbanBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.sda.kanbanBoard.user.api.ServerReader;
import pl.sda.kanbanBoard.user.gui.MainBoardController;
import pl.sda.kanbanBoard.user.api.ServerWriter;

import java.net.Socket;


public class App extends Application {
    public static void main(String[] args) {
        launch(args);

    }


    public void start(Stage primaryStage) throws Exception {

        Socket socket = new Socket("localhost", 5000);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_board.fxml"));
        Pane mainMenu = loader.load();
        MainBoardController controller = loader.getController();
        ServerWriter serverWriter = new ServerWriter(socket.getOutputStream());
        controller.setServerWriter(serverWriter);
        controller.getAllTasks();
        ServerReader serverReader = new ServerReader(controller, socket.getInputStream());
        new Thread(serverReader).start();
        Scene scene = new Scene(mainMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Kanban Board");
        primaryStage.show();

    }
}
