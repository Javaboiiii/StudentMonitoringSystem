import java.io.IOException;

import org.json.JSONObject;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.*;

public class courseList extends ListCell<String> {
    private final HBox hBox = new HBox();
    private final Button plusButton = new Button("+");
    private final String userId;

    public courseList(String user_id, String courseId) {
        this.userId = user_id;
            // Set button action
            plusButton.setOnAction(event -> {
                String courseInfo = getItem();
                if (courseInfo != null) {
                    System.out.println("Plus button clicked for: " + courseInfo);
                    saveEnrollment(userId, courseId);
                }
            });
    
            // Ensure the HBox contains the button
            hBox.getChildren().add(plusButton);
            HBox.setHgrow(plusButton, Priority.ALWAYS);
        }

    @Override
protected void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null) {
        setGraphic(null);
        setText(null);
    } else {
        // Create a new HBox for each item
        HBox hBox = new HBox();
        Button plusButton = new Button("+");

        // Set the button action for the new button
        plusButton.setOnAction(event -> {
            String courseInfo = item; // Use the current item's information
            System.out.println("Plus button clicked for: " + courseInfo);
            // Implement the functionality you want here
        });

        hBox.getChildren().add(plusButton);
        HBox.setHgrow(plusButton, Priority.ALWAYS);

        setText(item);
        setGraphic(hBox);
    }
}

private final OkHttpClient client = new OkHttpClient();
private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

public void saveEnrollment(String userId, String courseId) {
        String url = "http://localhost:4000"; 

        JSONObject mutation = new JSONObject()
            .put("query", "mutation AddEnrollment($userId: String!, $courseId: String) { addEnrollment(userId: $userId, courseId: $courseId) { course_id  user_id  } }")
            .put("variables", new JSONObject()
                .put("userId", userId)
                .put("courseId", courseId));        
        
        RequestBody body = RequestBody.create(mutation.toString(), JSON);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        System.err.println("Failed to enroll: " + response.message());
                    } else {
                        System.out.println("Successfully enrolled in course: " + courseId);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    
}
