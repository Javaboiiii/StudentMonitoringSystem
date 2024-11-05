import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Model class representing an assignment
class Assignment {
    private final String name;
    private final String dateTime;
    private final boolean completed;
    private final String due_date;

    public Assignment(String name, String dateTime, boolean completed, String due_date) {
        this.name = name;
        this.dateTime = dateTime;
        this.completed = completed;
        this.due_date = due_date;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDueDate(){
        return due_date;
    }

    @Override
    public String toString() {
        return name + " - " + dateTime +  " (Due: " + due_date + ")";
    }
}

public class AssignmentController {

    @FXML
    private ListView<Assignment> pendingAssign; 
    @FXML
    private ListView<Assignment> submittedAssign; 
    @FXML
    private Button submitButton;

    private final OkHttpClient client = new OkHttpClient();
    private final String url = "http://localhost:4000"; // Adjust as needed

    @FXML
    void initialize() {
        fetchAssignments();
    }

    private void fetchAssignments() {
        String query = "{ user_assignments { assignment_id title completed due_date } }";

        JSONObject jsonQuery = new JSONObject();
        jsonQuery.put("query", query);

        RequestBody body = RequestBody.create(
                jsonQuery.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to fetch assignments.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    System.out.println(responseBody);
                    parseAndDisplayAssignments(responseBody);
                } else {
                    showAlert("Error", "Could not retrieve assignments.");
                }
            }
        });
    }

    private void parseAndDisplayAssignments(String responseBody) {
        List<Assignment> pendingList = new ArrayList<>();
        List<Assignment> completedList = new ArrayList<>();

        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray assignmentsArray = jsonResponse.getJSONObject("data").getJSONArray("user_assignments");

        for (int i = 0; i < assignmentsArray.length(); i++) {
            JSONObject assignmentObj = assignmentsArray.getJSONObject(i);
            String title = assignmentObj.getString("title");
            String dateTime = assignmentObj.optString("dateTime", "No due date provided"); // Use optString for optional fields
            boolean completed = assignmentObj.getBoolean("completed");
            String due_date = assignmentObj.getString("due_date");

            Assignment assignment = new Assignment(title, dateTime, completed, due_date);
            if (completed) {
                completedList.add(assignment);
            } else {
                pendingList.add(assignment);
            }
        }

        // Update the ListViews on the JavaFX Application Thread
        Platform.runLater(() -> {
            pendingAssign.setItems(FXCollections.observableArrayList(pendingList));
            submittedAssign.setItems(FXCollections.observableArrayList(completedList));
        });
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
