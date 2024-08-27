package courseproject.example.cp.loginandsignup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Courses {

    private final Frame frame;
    private final Panel mainPanel;
    private final Panel coursesPanel; // New panel to display courses

    private final Map<String, String[]> coursesData; // Store courses data

    public Courses() {
        frame = new Frame("Your Courses");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        mainPanel = new Panel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Initialize courses data
        coursesData = new HashMap<>();
        coursesData.put("1st Year_Semester 1", new String[]{"Math 101", "Physics 101", "Chemistry 101"});
        coursesData.put("1st Year_Semester 2", new String[]{"Math 102", "Physics 102", "Chemistry 102"});
        coursesData.put("2nd Year_Semester 1", new String[]{"Math 201", "Electronics 201", "Mechanics 201"});
        coursesData.put("2nd Year_Semester 2", new String[]{"Math 202", "Electronics 202", "Mechanics 202"});
        coursesData.put("3rd Year_Semester 1", new String[]{"Software Engineering 301", "Data Structures 301"});
        coursesData.put("3rd Year_Semester 2", new String[]{"Operating Systems 302", "Algorithms 302"});
        coursesData.put("4th Year_Semester 1", new String[]{"Machine Learning 401", "Artificial Intelligence 401"});
        coursesData.put("4th Year_Semester 2", new String[]{"Project 402", "Advanced Topics 402"});

        // Header label
        Label headerLabel = new Label("Courses", Label.CENTER);
        headerLabel.setFont(new Font("Sitka Text", Font.BOLD, 36));
        headerLabel.setForeground(new Color(0, 102, 102));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(headerLabel, gbc);

        // Academic Year Spinner
        Label academicYearLabel = new Label("Academic Year:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(academicYearLabel, gbc);

        SpinnerChoice academicYearChoice = new SpinnerChoice(new String[]{"1st Year", "2nd Year", "3rd Year", "4th Year"});
        gbc.gridx = 1;
        mainPanel.add(academicYearChoice.getSpinner(), gbc);

        // Semester Spinner
        Label semesterLabel = new Label("Semester:");
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(semesterLabel, gbc);

        SpinnerChoice semesterChoice = new SpinnerChoice(new String[]{"Semester 1", "Semester 2"});
        gbc.gridx = 1;
        mainPanel.add(semesterChoice.getSpinner(), gbc);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        submitButton.setBackground(new Color(0, 102, 102));
        submitButton.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(submitButton, gbc);

        // Panel to display courses
        coursesPanel = new Panel();
        coursesPanel.setLayout(new GridBagLayout());
        coursesPanel.setBackground(Color.WHITE);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(coursesPanel, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the action here
                // Retrieve selected academic year and semester
                String selectedYear = academicYearChoice.getSelectedValue();
                String selectedSemester = semesterChoice.getSelectedValue();

                // Construct the key to look up courses
                String key = selectedYear + "_" + selectedSemester;

                // Get the courses for the selected academic year and semester
                String[] courses = coursesData.get(key);

                // Clear the previous content in the courses panel
                coursesPanel.removeAll();

                if (courses != null) {
                    // Display courses in the coursesPanel
                    GridBagConstraints c = new GridBagConstraints();
                    c.insets = new Insets(5, 5, 5, 5);
                    c.gridx = 0;
                    c.gridy = 0;
                    for (String course : courses) {
                        Label courseLabel = new Label(course);
                        courseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                        coursesPanel.add(courseLabel, c);
                        c.gridy++;
                    }
                } else {
                    Label noCoursesLabel = new Label("No courses available for the selected year and semester.");
                    noCoursesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
                    coursesPanel.add(noCoursesLabel);
                }

                // Revalidate and repaint the courses panel to display updated content
                coursesPanel.revalidate();
                coursesPanel.repaint();
            }
        });

        // Adding main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Courses courses = new Courses();
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Helper class for Spinner
    private class SpinnerChoice {
        private final Choice choice;

        public SpinnerChoice(String[] options) {
            choice = new Choice();
            for (String option : options) {
                choice.add(option);
            }
        }

        public Choice getSpinner() {
            return choice;
        }

        public String getSelectedValue() {
            return choice.getSelectedItem();
        }
    }
}
