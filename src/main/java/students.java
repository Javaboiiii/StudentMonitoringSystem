import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class students {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> userIdCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> roleCol;

    private ObservableList<User> userData = FXCollections.observableArrayList();

    // This method should be called once you have the data from the GraphQL query
    public void loadUserData(List<User> users) {
        userData.addAll(users);

        // Set up the columns
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Add data to the table
        userTable.setItems(userData);
    }

    // Simulate fetching data from GraphQL and displaying it in the TableView
    public void fetchAndDisplayUsers() {
        
        // graphql query
        // loadUserData(users);
    }
}


class User {
    private String userId;
    private String username;
    private String email;
    private String role;

    public User(String userId, String username, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
