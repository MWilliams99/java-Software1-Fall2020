package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The Main class is used to launch the program and open the Main Form first.
 * @author Mary Williams
 */
public class Main extends Application {

    /**
     * The start method starts the program by showing mainFormScreen.fxml.
     * @param primaryStage The Stage to set at the beginning of the program execution.
     * @exception IOException Failed to load mainFormScreen.fxml.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
       Parent parent = FXMLLoader.load(getClass().getResource("../view/mainFormScreen.fxml"));
       primaryStage.setScene(new Scene(parent));
       primaryStage.setTitle("Main Form");
       primaryStage.setResizable(false);
       primaryStage.show();
    }

    /**
     * The main method starts the program.
     * @param args String array used by main. 
     */
    public static void main(String[] args) {
        launch(args);
    }
}
