package consultancy_services_for_job_hunt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class HomePage {
    private static JFrame mainFrame;
    private static JPanel mainPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        mainFrame = new JFrame("Consultancy Services for Job Hunt");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the background image
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("C:\\Users\\marya\\OneDrive\\Desktop\\images.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a JLabel to display the background image
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));

        // Set the content pane of the main frame to the background label
        mainFrame.setContentPane(backgroundLabel);
        mainFrame.setLayout(null);

        // Create a JLabel for the "Welcome" text
        JLabel welcomeLabel = new JLabel("Welcome");
        welcomeLabel.setBounds(150, 50, 200, 50);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        backgroundLabel.add(welcomeLabel);

        // Create a main panel with CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        backgroundLabel.add(mainPanel);

        // Create panels for each table's content
        JPanel jobSeekersPanel = new JPanel();
        jobSeekersPanel.setBackground(Color.WHITE);
        jobSeekersPanel.add(new JLabel("Job Seekers Content"));
        mainPanel.add(jobSeekersPanel, "Job Seekers");

        JPanel companiesPanel = new JPanel();
        companiesPanel.setBackground(Color.WHITE);
        companiesPanel.add(new JLabel("Companies Content"));
        mainPanel.add(companiesPanel, "Companies");

        JPanel jobPostingsPanel = new JPanel();
        jobPostingsPanel.setBackground(Color.WHITE);
        jobPostingsPanel.add(new JLabel("Job Postings Content"));
        mainPanel.add(jobPostingsPanel, "Job Postings");

        JPanel jobApplicationsPanel = new JPanel();
        jobApplicationsPanel.setBackground(Color.WHITE);
        jobApplicationsPanel.add(new JLabel("Job Applications Content"));
        mainPanel.add(jobApplicationsPanel, "Job Applications");

        JPanel interviewsPanel = new JPanel();
        interviewsPanel.setBackground(Color.WHITE);
        interviewsPanel.add(new JLabel("Interviews Content"));
        mainPanel.add(interviewsPanel, "Interviews");

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create a "File" menu
        JMenu fileMenu = new JMenu("File");

        // Create menu items for the tables
        JMenuItem jobSeekersMenuItem = new JMenuItem("Job Seekers");
        JMenuItem companiesMenuItem = new JMenuItem("Companies");
        JMenuItem jobPostingsMenuItem = new JMenuItem("Job Postings");
        JMenuItem jobApplicationsMenuItem = new JMenuItem("Job Applications");
        JMenuItem interviewsMenuItem = new JMenuItem("Interviews");

        // Add action listeners to the table menu items
        jobSeekersMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Job Seekers frame
                showTableFrame("Job Seekers");
                mainFrame.setVisible(false);
                JobSeeker jobSeekerWindow = new JobSeeker();
                jobSeekerWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainFrame.setVisible(true);
                    }
                });
            }
        });

        companiesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Companies frame
                showTableFrame("Companies");
                mainFrame.setVisible(false);
                Companies companiesWindow = new Companies();
                companiesWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainFrame.setVisible(true);
                    }
                });
            }
        });

        jobPostingsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Job Postings frame
                showTableFrame("Job Postings");
                mainFrame.setVisible(false);
                JobPosting jobPostingWindow = new JobPosting();
                jobPostingWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainFrame.setVisible(true);
                    }
                });
            }
        });

        jobApplicationsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Job Applications frame
                showTableFrame("Job Applications");
                mainFrame.setVisible(false);
                JobApplications jobApplicationsWindow = new JobApplications();
                jobApplicationsWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainFrame.setVisible(true);
                    }
                });
            }
        });

        interviewsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show Interviews frame
                showTableFrame("Interviews");
                mainFrame.setVisible(false);
                Interviews interviewsWindow = new Interviews();
                interviewsWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        mainFrame.setVisible(true);
                    }
                });
            }
        });

        // Add the table menu items to the "File" menu
        fileMenu.add(jobSeekersMenuItem);
        fileMenu.add(companiesMenuItem);
        fileMenu.add(jobPostingsMenuItem);
        fileMenu.add(jobApplicationsMenuItem);
        fileMenu.add(interviewsMenuItem);

        // Add the "File" menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar for the main frame
        mainFrame.setJMenuBar(menuBar);

        // Display the main frame
        mainFrame.setSize(400, 300);
        mainFrame.setVisible(true);
    }

    private static void showTableFrame(String tableName) {
        // Create a new frame for the selected table
        JFrame tableFrame = new JFrame(tableName);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel for the table content
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(new JLabel(tableName + " Content"));

        // Set the panel as the content pane of the table frame
        tableFrame.setContentPane(tablePanel);

        // Adjust the size of the table frame
        tableFrame.pack();

        // Center the table frame on the screen
        tableFrame.setLocationRelativeTo(null);

        // Add a window listener to the table frame
        tableFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // When the table frame is closed, make the main frame visible again
                mainFrame.setVisible(true);
            }
        });

        // Show the table frame
        tableFrame.setVisible(true);
    }
}
