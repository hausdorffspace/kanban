package pl.sda.kanbanBoard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.sda.kanbanBoard.user.gui.MainBoardController;
import pl.sda.kanbanBoard.user.api.ServerWriter;


public class App extends Application {
    public static void main(String[] args) {
        launch(args);

    }


    public void start(Stage primaryStage) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_board.fxml"));
        Pane mainMenu = loader.load();
        MainBoardController controller = loader.getController();
        ServerWriter serverWriter = new ServerWriter();
        controller.setServerWriter(serverWriter);
        ServerReader serverReader = new ServerReader(controller);
        serverReader.start();
        Scene scene = new Scene(mainMenu);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Kanban Board");
        primaryStage.show();

    }
}
