import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class forgotPassword {

    @FXML
    private TextField email;
 
    @FXML
    private Button sendOTP;

    @FXML
    private Label emailError;

    // Store the OTP to verify it later if needed
    private int generatedOTP;

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

    @FXML
    void sendOTP(ActionEvent event) {
        String recipientEmail = email.getText();

        if (recipientEmail.isEmpty()) {
            emailError.setText("Please enter your email address.");
            return;
        }

        // Validate the email format
        if (!isValidEmail(recipientEmail)) {
            emailError.setText("Invalid email format. Please enter a valid email address.");
            return;
        }

        // Generate a random OTP
        generatedOTP = generateOTP();
        System.out.println("Generated OTP: " + generatedOTP);

        // Send the OTP to the specified email address
        sendEmail(recipientEmail, generatedOTP);
    }

    private void sendEmail(String recipientEmail, int otp) {
        // Configure email settings
        String host = "smtp.gmail.com"; 
        String senderEmail = "manasi.mhatre23@vit.edu"; 
        String senderPassword = System.getenv("EMAIL_PASSWORD"); 

        // Set the properties for the email
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        // Create a session with an authenticator
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a message with the OTP
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your One-Time Password (OTP) is: " + otp);

            // Send the message
            Transport.send(message);
            System.out.println("OTP sent successfully to " + recipientEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            emailError.setText("Failed to send OTP. Please check your email configuration.");
        }
    }

    // Utility method to generate a random 6-digit OTP
    private int generateOTP() {
        Random random = new Random();
        return 100000 + random.nextInt(900000); // Generate a 6-digit number
    }

    // Utility method to validate email format using regex
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
}
