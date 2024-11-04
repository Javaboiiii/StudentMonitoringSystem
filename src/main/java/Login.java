import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML
    private TextField email;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginError;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink forgotPass;

    @FXML
    private RadioButton student;

    @FXML
    private RadioButton faculty;

    @FXML
    private Hyperlink signupPage;

    private ToggleGroup roleGroup;

    @FXML
    public void initialize() {
        // Initialize the ToggleGroup and assign it to the radio buttons
        roleGroup = new ToggleGroup();
        student.setToggleGroup(roleGroup);
        faculty.setToggleGroup(roleGroup);
    }

    // Event handler for the "Sign In" button
    @FXML
    void onLogin(ActionEvent event) {
        String userEmail = email.getText();
        String userPassword = password.getText();
    
        // Simple validation for empty fields
        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            loginError.setText("Please enter both email and password.");
            return;
        }
    
        // Email pattern validation
        if (!isValidEmail(userEmail)) {
            loginError.setText("Invalid email format. Please enter a valid email address.");
            return;
        }
    
        RadioButton selectedRole = (RadioButton) roleGroup.getSelectedToggle();
        if (selectedRole == null) {
            loginError.setText("Please select a role: student or faculty.");
            return;
        }

        String role = selectedRole.getText();

        LoginCredentials credentials = new LoginCredentials(userEmail, userPassword, role);
    }
    

    // Event handler for the "Forgot Password?" hyperlink
    @FXML
    void navigateToForgotPassword(ActionEvent event) {
        try {
            Parent forgotPasswordRoot = FXMLLoader.load(getClass().getResource("forgotPass.fxml"));
            Stage stage = (Stage) forgotPass.getScene().getWindow();
            Scene scene = new Scene(forgotPasswordRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void navigateToSignUp(ActionEvent event) {
        try {
            Parent forgotPasswordRoot = FXMLLoader.load(getClass().getResource("signup.fxml"));
            Stage stage = (Stage) forgotPass.getScene().getWindow();
            Scene scene = new Scene(forgotPasswordRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        // Regular expression for a basic email format validation
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
}

class LoginCredentials {
    private String email;
    private String password;
    private String role;

    public LoginCredentials(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}