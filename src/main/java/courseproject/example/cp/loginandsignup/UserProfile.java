package courseproject.example.cp.loginandsignup;

import java.awt.*;
import java.awt.event.*;

public class UserProfile extends Frame implements ActionListener {

    Label lblWelcome, lblStudentName, lblStudentID;
    Button btnViewCourses, btnViewGrades, btnLogout;

    public UserProfile(String studentName, String studentID) {
        // Set up the frame
        setTitle("Student Dashboard");
        setSize(800, 500); // Increased size for better spacing
        setLayout(null); // No layout manager
        setBackground(new Color(245, 245, 245)); // Light background color

        // Welcome label
        lblWelcome = new Label("Welcome to the Student Dashboard", Label.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 18)); // Font styling
        lblWelcome.setForeground(new Color(34, 139, 34)); // Green color
        lblWelcome.setBounds(50, 50, 400, 30);
        add(lblWelcome);

        // Student Name label
        lblStudentName = new Label("Name: " + studentName);
        lblStudentName.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStudentName.setBounds(50, 120, 200, 20);
        lblStudentName.setForeground(new Color(0, 0, 128)); // Dark blue color
        add(lblStudentName);

        // Student ID label
        lblStudentID = new Label("Student ID: " + studentID);
        lblStudentID.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStudentID.setBounds(50, 150, 200, 20);
        lblStudentID.setForeground(new Color(0, 0, 128)); // Dark blue color
        add(lblStudentID);

        // View Courses button
        btnViewCourses = new Button("View Courses");
        btnViewCourses.setBounds(50, 200, 120, 40); // Increased button size
        btnViewCourses.setBackground(new Color(70, 130, 180)); // Steel blue color
        btnViewCourses.setForeground(Color.WHITE);
        btnViewCourses.setFont(new Font("Arial", Font.BOLD, 14));
        btnViewCourses.addActionListener(this);
        add(btnViewCourses);

        // View Grades button
        btnViewGrades = new Button("View Grades");
        btnViewGrades.setBounds(190, 200, 120, 40); // Increased button size
        btnViewGrades.setBackground(new Color(70, 130, 180)); // Steel blue color
        btnViewGrades.setForeground(Color.WHITE);
        btnViewGrades.setFont(new Font("Arial", Font.BOLD, 14));
        btnViewGrades.addActionListener(this);
        add(btnViewGrades);

        // Logout button
        btnLogout = new Button("Logout");
        btnLogout.setBounds(330, 200, 120, 40); // Increased button size
        btnLogout.setBackground(new Color(255, 69, 0)); // Orange red color
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogout.addActionListener(this);
        add(btnLogout);

        // Window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        switch (command) {
            case "View Courses" -> {
                // Navigate to Courses.java
                Courses coursesPage = new Courses();
                coursesPage.setVisible(true); // Show the Courses page
                this.dispose(); // Close the UserProfile page
            }
            case "View Grades" -> System.out.println("View Grades clicked");
            case "Logout" -> {
                System.out.println("Logout clicked");
                dispose();
            }
            default -> System.out.println("Unknown action: " + command);
        }
    }

    public static void main(String[] args) {
        // Example usage with a sample student
        UserProfile dashboard = new UserProfile("John Doe", "12345");
        dashboard.setVisible(true);
    }
}
