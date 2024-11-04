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
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("signup.fxml"));

            primaryStage.setTitle("JavaFX Login Application");
            primaryStage.setScene(new Scene(root, 640, 400));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
