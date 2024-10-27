package courseproject.example.cp;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private Label prn;

    @FXML
    private Button result;

    @FXML
    private Button timetable;

    @FXML
    private Label username;

    
    private void loadScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile)); // Load the new scene
            Stage stage = (Stage) assign.getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(root); // Create a new scene with the loaded FXML
            stage.setScene(scene); // Set the new scene on the stage
            stage.show(); // Show the updated stage
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    @FXML
    void navToAssign(ActionEvent event) {
        loadScene("assign.fxml");
    }

    @FXML
    void navToAttendance(ActionEvent event) {
        loadScene("attendance.fxml");
    }

    @FXML
    void navToCourses(ActionEvent event) {
        loadScene("courses.fxml");
    }

    @FXML
    void navToGrievance(ActionEvent event) {
        loadScene("grievance.fxml");
    }

    @FXML
    void navToLesson(ActionEvent event) {
        loadScene("lessonPlan.fxml");
    }

    @FXML
    void navToResult(ActionEvent event) {
        loadScene("result.fxml");
    }

    @FXML
    void navToTimetable(ActionEvent event) {
        loadScene("timetable.fxml");
    }

    @FXML
    void navToFeedback(ActionEvent event) {
        loadScene("feedback.fxml");
    }
    
}
