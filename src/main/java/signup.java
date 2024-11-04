import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.HttpURLConnection;

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

    @FXML
    void navToLogin(ActionEvent event) {
        try {
            Parent forgotPasswordRoot = FXMLLoader.load(getClass().getResource("loginPage.fxml")); 
            Stage stage = (Stage) toLogin.getScene().getWindow(); 
            Scene scene = new Scene(forgotPasswordRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendSignupMutation(String username, String password, String email, String role) {
        try {
            URL url = new URL("http://localhost:4000/graphql"); 
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // GraphQL mutation
            String mutation = String.format(
                "{\"query\": \"mutation { signup(username: \\\"%s\\\", password: \\\"%s\\\", email: \\\"%s\\\", role: \\\"%s\\\") { user_id username email role } }\"}",
                username, password, email, role
            );

            // Write data
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = mutation.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Check response code
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                System.out.println("Signup mutation sent successfully.");
            } else {
                System.out.println("Failed to send signup mutation: " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onSignup(ActionEvent event) {
        String username = name.getText();
        String password = password1.getText();
        String confirmPassword = password2.getText();
        String emailText = email.getText();
        String role = student.isSelected() ? "student" : "faculty";

        if (!password.equals(confirmPassword)) {
            loginError.setText("Passwords do not match.");
            return;
        }

        sendSignupMutation(username, password, emailText, role);
    }
}
