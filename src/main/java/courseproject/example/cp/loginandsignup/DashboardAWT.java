package courseproject.example.cp.loginandsignup;

import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardAWT {

    private Frame frame;
    private final Panel mainPanel;
    private final TextField searchField;
    private final Button coursesButton;
    private final Button assignmentsButton;
    private final Button quizButton;
    private final Button examinationButton;
    private final Button resultsButton;
    private final Button academicsButton;

    public DashboardAWT() {
        frame = new Frame("Dashboard");
        frame.setSize(1000, 600);
        frame.setLayout(new BorderLayout());

        mainPanel = new Panel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Header label at the top
        Label headerLabel = new Label("Dashboard", Label.CENTER);
        headerLabel.setFont(new Font("Sitka Text", Font.BOLD, 36));
        headerLabel.setForeground(new Color(0, 102, 102));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(headerLabel, gbc);

        // Search field and label
        Panel searchPanel = new Panel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        Label searchLabel = new Label("Search");
        searchField = new TextField(20);
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        gbc.gridy = 1;
        mainPanel.add(searchPanel, gbc);

        // Buttons
        academicsButton = new Button("Academics");
        coursesButton = new Button("Courses");
        assignmentsButton = new Button("Assignments");
        quizButton = new Button("Quiz");
        examinationButton = new Button("Examination");
        resultsButton = new Button("Results");

        Button[] buttons = {academicsButton, coursesButton, assignmentsButton, quizButton, examinationButton, resultsButton};
        for (Button button : buttons) {
            button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            button.setBackground(new Color(0, 102, 102));
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        }

        // Add buttons in 3x2 grid
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(academicsButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(coursesButton, gbc);
        gbc.gridx = 2;
        mainPanel.add(assignmentsButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        mainPanel.add(quizButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(examinationButton, gbc);
        gbc.gridx = 2;
        mainPanel.add(resultsButton, gbc);

        // Adding main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Button action listeners
        academicsButton.addActionListener((ActionEvent e) -> {
            Academics academicsFrame = new Academics(); // Create an instance of Academics
            academicsFrame.setVisible(true); // Display the Academics window
            frame.dispose(); // Close the current window
        });

        // Display the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        DashboardAWT dashboardAWT;
        dashboardAWT = new DashboardAWT();
    }
}
