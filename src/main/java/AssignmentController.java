import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

// Model class representing an assignment
class Assignment {
    private String name;
    private String dateTime;

    public Assignment(String name, String dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return name + " - " + dateTime; // Display format in ListView
    }
}


public class AssignmentController {

    @FXML
    private ListView<Assignment> pendingAssign; 
    @FXML
    private ListView<Assignment> submittedAssign; 
    @FXML
    private Button submitButton;

    @FXML
    void initialize() {
        // fetchAssignments(); // Call to fetch assignments on initialization
    }

    // private void fetchAssignments() {
    //     // Use your GraphQL client to fetch assignments from the Apollo Server
    //     // Example query to fetch assignments (adjust according to your schema)
    //     String query = "query { assignments { name dateTime } }";
        
    //     // Assuming you have a method to execute the GraphQL query
    //     // Replace with your actual GraphQL client logic
    //     GraphQLClient client = new GraphQLClient("YOUR_GRAPHQL_ENDPOINT");
    //     client.execute(query, new GraphQLCallback<List<Assignment>>() {
    //         @Override
    //         public void onResponse(List<Assignment> assignments) {
    //             pendingAssign.getItems().addAll(assignments);
    //         }

    //         @Override
    //         public void onFailure(Throwable t) {
    //             System.out.println("Error fetching assignments: " + t.getMessage());
    //         }
    //     });
    // }

    @FXML
    void submitDocument() {
        // Open a file chooser to select a document to submit
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.doc", "*.docx"));
        File selectedFile = fileChooser.showOpenDialog((Stage) submitButton.getScene().getWindow());

        if (selectedFile != null) {
            // Implement the logic to submit the document
            submitAssignmentDocument(selectedFile);
        }
    }

    private void submitAssignmentDocument(File file) {
        // Implement your logic to submit the document to the backend
        // Use your GraphQL mutation for document submission
        String mutation = "mutation SubmitAssignment($file: Upload!) { submitAssignment(file: $file) { success message } }";
        
        // Replace with actual file upload logic
        // Use GraphQL client to execute the mutation
    }
}
