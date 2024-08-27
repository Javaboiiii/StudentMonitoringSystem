package courseproject.example.cp.loginandsignup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class Home_page extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel backgroundLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home_page frame = new Home_page();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Home_page() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Setting up the background image with full-screen adjustment
        backgroundLabel = new JLabel("");
        backgroundLabel.setBounds(0, 0, 800, 600);
        contentPane.add(backgroundLabel);

        // Ensure the background image scales with the window size
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeBackgroundImage();
            }
        });

        // Creating a transparent panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent panel
        buttonPanel.setLayout(new GridBagLayout());  // Using GridBagLayout for more control
        buttonPanel.setBounds(150, 200, 500, 200);
        backgroundLabel.add(buttonPanel); // Adding button panel on top of background image

        // GridBagConstraints for proper positioning of buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Adding padding between buttons
        gbc.ipady = 40; // Increase button height
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Creating buttons with hover effects
        JButton btnCourses = createStyledButton("Courses");
        buttonPanel.add(btnCourses, gbc);

        gbc.gridx = 1;
        JButton btnAccount = createStyledButton("Account");
        buttonPanel.add(btnAccount, gbc);

        gbc.gridx = 2;
        JButton btnAssignment = createStyledButton("Assignment");
        buttonPanel.add(btnAssignment, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JButton btnDashboard = createStyledButton("Dashboard");
        buttonPanel.add(btnDashboard, gbc);

        gbc.gridx = 1;
        JButton btnSettings = createStyledButton("Settings");
        buttonPanel.add(btnSettings, gbc);
    }

    /**
     * Creates a styled button with custom design and hover effect.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 50));
        button.setFont(new Font("Verdana", Font.BOLD, 18)); // Stylish font with increased size
        button.setBackground(new Color(30, 144, 255)); // Dodger blue color
        button.setForeground(Color.WHITE); // White text color
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(100, 149, 237)); // Lighter blue when hovered
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(30, 144, 255)); // Original color when not hovered
            }
        });

        return button;
    }

    /**
     * Resizes the background image to fit the full window size.
     */
    private void resizeBackgroundImage() {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Images/Home.jpeg"));
        Image img = originalIcon.getImage();
        Image scaledImage = img.getScaledInstance(backgroundLabel.getWidth(), backgroundLabel.getHeight(), Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledImage));
    }
}
