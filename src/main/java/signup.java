import java.io.IOException;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class signup {

    @FXML
    private TextField email;
    @FXML
    private RadioButton faculty;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginError;
    @FXML
    private TextField name;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;
    @FXML
    private TextField prn;
    @FXML
    private RadioButton student;
    @FXML
    private Hyperlink toLogin;

    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @FXML
    void navToLogin(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            Stage stage = (Stage) toLogin.getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSignupMutation(String username, String password, String email, String prn, String role) {
        String url = "http://localhost:4000";
        
        String mutation = new JSONObject()
            .put("query", "mutation CreateUser($user: Cuser!) { createUser(user: $user) { user_id username email prn role } }")
            .put("variables", new JSONObject()
            .put("user", new JSONObject()
            .put("username", username)
            .put("password", password)
            .put("email", email)
            .put("prn", prn)
            .put("role", role)))
            .toString();

        RequestBody body = RequestBody.create(mutation, JSON);
        Request request = new Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/json")
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Signup mutation sent successfully: " + response.body().string());
                navigateToLogin();
            } else {
                System.out.println("Failed to send signup mutation. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignup(ActionEvent event) {
        String username = name.getText();
        String password = password1.getText();
        String confirmPassword = password2.getText();
        String emailText = email.getText();
        String prnText = prn.getText();
        String role = student.isSelected() ? "student" : "faculty";

        if (!password.equals(confirmPassword)) {
            loginError.setText("Passwords do not match.");
            return;
        }

        sendSignupMutation(username, password, emailText, prnText, role);
    }

    private void navigateToLogin() {
        try {
            Parent homeRoot = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
