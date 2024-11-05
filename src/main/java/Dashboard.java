import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Dashboard {

    @FXML
    private Button assign;

    @FXML
    private Button attendance;

    @FXML
    private Button courses;

    @FXML
    private Button feedback;

    @FXML
    private Button grievance;

    @FXML
    private Button lessonPlan;

    @FXML
    private Button result;

    @FXML
    private Button timetable;

    @FXML
    private Label username;

    @FXML
    private Label prn;
 
    private String user_id;

    public void setUserDetails(String usernameText, String prnText, String user_id) {
        username.setText(usernameText);
        prn.setText(prnText);
        this.user_id = user_id;
        System.out.println(this.user_id);
    }
    
    private void loadScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) assign.getScene().getWindow(); 
            Scene scene = new Scene(root); 
            stage.setScene(scene); 
            stage.show(); 
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    @FXML
    void navToAssign(ActionEvent event) {
        loadScene("assignment.fxml");
    }

   
    @FXML
    void navToCourses(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("course.fxml"));
            Parent root = loader.load();
            
            Course controller = loader.getController();
            controller.setUserId(user_id);
            
            // Load the scene
            Stage stage = (Stage) courses.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
