package courseproject.example.cp.loginandsignup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Academics {
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Lesson Plan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null); // Center the frame

        // Create the table model and columns
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Sr. No.");
        model.addColumn("Course Code");
        model.addColumn("Course Name");
        model.addColumn("Course Type");
        model.addColumn("Attendance");
        model.addColumn("Syllabus");

        // Add some example data
        model.addRow(new Object[]{"1", "CS101", "Intro to Computer Science", "Theory", "80%", "Basic programming concepts"});
        model.addRow(new Object[]{"2", "CS102", "Data Structures", "Theory", "75%", "Arrays, Linked Lists, Trees"});
        model.addRow(new Object[]{"3", "CS103", "Database Management", "Lab", "90%", "SQL, ER Diagrams"});
        model.addRow(new Object[]{"4", "CS104", "Operating Systems", "Theory", "85%", "Process Management, Threads"});
        model.addRow(new Object[]{"5", "CS105", "Software Engineering", "Theory", "78%", "Software Development Life Cycle"});
        model.addRow(new Object[]{"6", "CS106", "Computer Networks", "Theory", "82%", "Networking Basics, Protocols"});
        model.addRow(new Object[]{"7", "CS107", "Artificial Intelligence", "Lab", "88%", "Machine Learning, AI Algorithms"});
        model.addRow(new Object[]{"8", "CS108", "Web Development", "Lab", "92%", "HTML, CSS, JavaScript"});

        // Create the table
        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);

        // Customize table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(50, 150, 250)); // Header background color
        header.setForeground(Color.WHITE); // Header text color
        header.setFont(new Font("Arial", Font.BOLD, 14)); // Header font

        // Customize table cell rendering
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        // Customize cell rendering for the table
        table.setFont(new Font("Arial", Font.PLAIN, 12)); // Table font
        table.setRowHeight(50); // Row height
        table.setIntercellSpacing(new Dimension(10, 5)); // Cell spacing

        // Set cell alignment
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around the table

        // Create a panel for the frame content
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        // Create a heading label
        JLabel heading = new JLabel("Lesson Plan", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24)); // Font for heading
        heading.setForeground(new Color(50, 150, 250)); // Heading color
        panel.add(heading, BorderLayout.NORTH); // Add heading to the top

        panel.add(scrollPane, BorderLayout.CENTER); // Add table to the center

        // Add the panel to the frame
        frame.add(panel);

        // Set frame visibility
        frame.setVisible(true);
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void showFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
