package courseproject.example.cp.loginandsignup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AssignmentsAWT {

    private final Frame frame;
    private final Panel mainPanel;
    private final TextArea pendingAssignmentsArea;
    private final TextArea completedAssignmentsArea;

    public AssignmentsAWT() {
        frame = new Frame("Assignments");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        mainPanel = new Panel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245)); // Light background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Increased padding for better spacing
        gbc.fill = GridBagConstraints.BOTH;

        // Header label
        Label headerLabel = new Label("Assignments", Label.CENTER);
        headerLabel.setFont(new Font("Sitka Text", Font.BOLD, 36));
        headerLabel.setForeground(new Color(0, 102, 102));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        mainPanel.add(headerLabel, gbc);

        // Pending Assignments Label
        Label pendingLabel = new Label("Pending Assignments:");
        pendingLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(pendingLabel, gbc);

        // Pending Assignments Area
        pendingAssignmentsArea = new TextArea();
        pendingAssignmentsArea.setEditable(false);
        pendingAssignmentsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        pendingAssignmentsArea.setBackground(Color.WHITE); // White background for a cleaner look
        pendingAssignmentsArea.setForeground(new Color(50, 50, 50)); // Darker text color
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        mainPanel.add(pendingAssignmentsArea, gbc);

        // Completed Assignments Label
        Label completedLabel = new Label("Completed Assignments:");
        completedLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridy = 3;
        mainPanel.add(completedLabel, gbc);

        // Completed Assignments Area
        completedAssignmentsArea = new TextArea();
        completedAssignmentsArea.setEditable(false);
        completedAssignmentsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        completedAssignmentsArea.setBackground(Color.WHITE); // White background for a cleaner look
        completedAssignmentsArea.setForeground(new Color(50, 50, 50)); // Darker text color
        gbc.gridy = 4;
        gbc.weighty = 0.5;
        mainPanel.add(completedAssignmentsArea, gbc);

        // Refresh Button
        Button refreshButton = new Button("Refresh");
        refreshButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        refreshButton.setBackground(new Color(0, 102, 102));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gbc.gridy = 5;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(refreshButton, gbc);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action here
                loadAssignments();
            }
        });

        // Adding main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Load assignments initially
        loadAssignments();

        // Add window listener for closing the frame
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        // Display the frame
        frame.setVisible(true);
    }

    private void loadAssignments() {
        // Dummy data for illustration
        String[] pendingAssignments = getPendingAssignments();
        String[] completedAssignments = getCompletedAssignments();

        // Clear previous text
        pendingAssignmentsArea.setText("");
        completedAssignmentsArea.setText("");

        // Display pending assignments
        if (pendingAssignments.length > 0) {
            for (String assignment : pendingAssignments) {
                pendingAssignmentsArea.append(assignment + "\n");
            }
        } else {
            pendingAssignmentsArea.append("No pending assignments.");
        }

        // Display completed assignments
        if (completedAssignments.length > 0) {
            for (String assignment : completedAssignments) {
                completedAssignmentsArea.append(assignment + "\n");
            }
        } else {
            completedAssignmentsArea.append("No completed assignments.");
        }
    }

    private String[] getPendingAssignments() {
        // Placeholder logic for getting pending assignments with deadlines
        // Replace with actual data retrieval logic
        return new String[]{
            "Assignment 1: Math Homework - Deadline: 2024-08-30",
            "Assignment 2: Science Project - Deadline: 2024-09-05"
        };
    }

    private String[] getCompletedAssignments() {
        // Placeholder logic for getting completed assignments with deadlines
        // Replace with actual data retrieval logic
        return new String[]{
            "Assignment 1: History Essay - Deadline: 2024-08-15",
            "Assignment 2: Programming Lab - Deadline: 2024-08-20"
        };
    }

    public static void main(String[] args) {
        new AssignmentsAWT();
    }
}
