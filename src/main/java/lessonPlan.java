import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class lessonPlan {

    @FXML
    private TableColumn<?, ?> attendanceCol;

    @FXML
    private TableColumn<?, ?> courseCodeCol;

    @FXML
    private TableColumn<?, ?> courseNameCol;

    @FXML
    private TableColumn<?, ?> courseTypeCol;

    @FXML
    private TableView<?> lessonTable;

    @FXML
    private TableColumn<?, ?> srNoCol;

    @FXML
    private TableColumn<?, ?> syllabusCol;

}