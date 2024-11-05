import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Course {

    @FXML
    private ListView<String> course;

    private final OkHttpClient client = new OkHttpClient();
    private final String url = "http://localhost:4000";
    private String user_id;
    private String course_id;

    public void initialize() {
        if( user_id != null) {
            course.setCellFactory(param -> new courseList(user_id, course_id));
        }
        fetchCourses();
    }
    
    public void setUserId(String userId) {
        this.user_id = userId;
        course.setCellFactory(param -> new courseList(user_id)));

    }

    private void fetchCourses() {
        String query = "{ courses { course_id title description start_date end_date user { user_id username email } } }";
    
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
    
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println(responseBody);
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray courseArray = jsonResponse.getJSONObject("data").getJSONArray("courses");
    
                List<String> courseListt = new ArrayList<>();
                for (int i = 0; i < courseArray.length(); i++) {
                    JSONObject courseObject = courseArray.getJSONObject(i);
                    String title = courseObject.getString("title");
                    String description = courseObject.getString("description");
                    String startDate = courseObject.getString("start_date");
                    String endDate = courseObject.getString("end_date");
    
                    courseListt.add(title + ": " + description + " (" + startDate + " to " + endDate + ")");
                }
    
                Platform.runLater(() -> {
                    course.setItems(FXCollections.observableArrayList(courseListt));
                });
            } else {
                showAlert("Error", "Could not retrieve courses.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred. Please try again.");
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
