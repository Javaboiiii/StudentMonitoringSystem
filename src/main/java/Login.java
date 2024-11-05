import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import okhttp3.*;
import org.json.JSONObject;
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
    private Hyperlink signupPage;

    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @FXML
    void onLogin(ActionEvent event) {
        String userEmail = email.getText();
        String userPassword = password.getText();

        if (userEmail.isEmpty() || userPassword.isEmpty()) {
            loginError.setText("Please enter both email and password.");
            return;
        }

        if (!isValidEmail(userEmail)) {
            loginError.setText("Invalid email format. Please enter a valid email address.");
            return;
        }

        sendLoginQuery(userEmail, userPassword);
    }

    private void sendLoginQuery(String email, String password) {
        String url = "http://localhost:4000";
        String query = new JSONObject()
                .put("query",
                        "mutation CheckPassword($password: String!, $email: String!) { checkPassword(password: $password, email: $email) { email role username prn user_id} }")
                .put("variables", new JSONObject()
                        .put("password", password)
                        .put("email", email))
                .toString();

        RequestBody body = RequestBody.create(query, okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JSONObject responseBody = new JSONObject(response.body().string());
                if (responseBody.has("errors")) {
                    String errorMessage = responseBody.getJSONArray("errors")
                            .getJSONObject(0)
                            .getString("message");
                    System.out.println("Error: " + errorMessage);
                } else {
                    JSONObject data = responseBody.getJSONObject("data");
                    JSONObject checkPassword = data.getJSONObject("checkPassword");
                    String username = checkPassword.getString("username");
                    String prn = checkPassword.getString("prn");
                    String role = checkPassword.getString("role");
                    String user_id = checkPassword.getString("user_id");
                    System.out.println("Username: " + username);
                    System.out.println("Login successful: " + responseBody.toString());
                    navigateToHomePage(username, prn, role, user_id);
                }
            } else {
                System.out.println("Failed to connect to the server. Response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred. Please try again.");
        }

        // Create a new Task for the login operation
        Task<Void> loginTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Add delay before making the network call
                Thread.sleep(2000); // Delay for 2000 milliseconds (2 seconds)

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        JSONObject responseBody = new JSONObject(response.body().string());

                        if (responseBody.has("errors")) {
                            String errorMessage = responseBody.getJSONArray("errors")
                                    .getJSONObject(0)
                                    .getString("message");
                            updateMessage(errorMessage);
                        } else {
                            JSONObject user = responseBody.getJSONObject("user");
                            String username = user.getString("username");
                            // String prn = "123456";
                            String prn = user.getString("prn");
                            String role = user.getString("role");
                            String user_id = user.getString("user_id");
                            System.out.println("Username : " + username);
                            // System.out.println("Prn = " + prn);
                            System.out.println("Login successful: " + responseBody.toString());
                            navigateToHomePage(username, prn, role, user_id);
                        }
                    } else {
                        updateMessage("Failed to connect to the server. Try again.");
                        System.out.println("Failed to send login query. Response code: " + response.code());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    updateMessage("An error occurred. Please try again.");
                }
                return null;
            }
        };

        // Bind the task's message to the loginError label
        loginError.textProperty().bind(loginTask.messageProperty());

        // Run the task in a new thread
        new Thread(loginTask).start();
    }

    private void navigateToHomePage(String username, String prn, String role, String user_id) {
        try {
            FXMLLoader loader;
            if (role.equals("none")) {
                loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            } else if (role.equals("faculty")) {
                loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            } else if (role.equals("student")) {
                loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            } else {
                System.out.println("Invalid role: " + role);
                return; 
            }
            
            Parent homeRoot = loader.load();
            Dashboard controller = loader.getController();
            controller.setUserDetails(username, prn, user_id);
            // controller.navToCourses(user_id);
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            Parent signupRoot = FXMLLoader.load(getClass().getResource("signup.fxml"));
            Stage stage = (Stage) signupPage.getScene().getWindow();
            Scene scene = new Scene(signupRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailRegex);
    }
}
