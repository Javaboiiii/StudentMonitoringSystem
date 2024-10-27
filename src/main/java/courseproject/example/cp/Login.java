package courseproject.example.cp;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    
        // If the email format is correct, proceed with login
        // System.out.println("Email: " + userEmail + ", Password: " + userPassword);

        LoginCredentials credentials = new LoginCredentials(userEmail, userPassword);
        // validateUser(credentials); 
    }
    

    // Event handler for the "Forgot Password?" hyperlink
    @FXML
    void navigateToForgotPassword(ActionEvent event) {
        try {
            Parent forgotPasswordRoot = FXMLLoader.load(getClass().getResource("forgotPass.fxml")); // Ensure the path is correct
            Stage stage = (Stage) forgotPass.getScene().getWindow(); // Use the hyperlink's scene
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

    public LoginCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
