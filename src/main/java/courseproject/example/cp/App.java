package courseproject.example.cp;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file with the correct path
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("loginPage.fxml"));

            primaryStage.setTitle("JavaFX Login Application");
            primaryStage.setScene(new Scene(root, 640, 400));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
