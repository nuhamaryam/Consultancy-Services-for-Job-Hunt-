package consultancy_services_for_job_hunt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Interviews extends JFrame {
    private JLabel idLabel, applicationIdLabel, dateLabel, locationLabel;
    private JTextField idField, applicationIdField, dateField, locationField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;

    public Interviews() {
        initializeComponents();
        setupLayout();
        setupListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        idLabel = new JLabel("Interview ID:");
        applicationIdLabel = new JLabel("Application ID:");
        dateLabel = new JLabel("Interview Date:");
        locationLabel = new JLabel("Interview Location:");

        idField = new JTextField(10);
        applicationIdField = new JTextField(10);
        dateField = new JTextField(10);
        locationField = new JTextField(20);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        // Adding components to the layout
        c.gridx = 0;
        c.gridy = 0;
        add(idLabel, c);

        c.gridx = 1;
        add(idField, c);

        c.gridx = 0;
        c.gridy = 1;
        add(applicationIdLabel, c);

        c.gridx = 1;
        add(applicationIdField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(dateLabel, c);

        c.gridx = 1;
        add(dateField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(locationLabel, c);

        c.gridx = 1;
        add(locationField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 2;
        add(deleteButton, c);

        c.gridx = 2;
        c.gridy = 4;
        add(displayButton, c);
    }

    private void setupListeners() {
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertRecord();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyRecord();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRecord();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayRecords();
            }
        });
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";
        String password = "nuha";
        return DriverManager.getConnection(url, username, password);
    }

    private void insertRecord() {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO interviews (interview_id, job_application_id, interview_date, interview_location) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.setInt(2, Integer.parseInt(applicationIdField.getText()));
            statement.setString(3, dateField.getText());
            statement.setString(4, locationField.getText());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record inserted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyRecord() {
        try (Connection conn = getConnection()) {
            String query = "UPDATE interviews SET job_application_id = ?, interview_date = ?, interview_location = ? WHERE interview_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(applicationIdField.getText()));
            statement.setString(2, dateField.getText());
            statement.setString(3, locationField.getText());
            statement.setInt(4, Integer.parseInt(idField.getText()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record modified successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteRecord() {
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM interviews WHERE interview_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayRecords() {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM interviews";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Create a StringBuilder to store the records
            StringBuilder sb = new StringBuilder();
            sb.append("Interview ID\tApplication ID\tInterview Date\tInterview Location\n");

            // Iterate over the result set and append each record to the StringBuilder
            while (resultSet.next()) {
                int interviewId = resultSet.getInt("interview_id");
                int applicationId = resultSet.getInt("job_application_id");
                String date = resultSet.getString("interview_date");
                String location = resultSet.getString("interview_location");

                sb.append(interviewId).append("\t")
                        .append(applicationId).append("\t")
                        .append(date).append("\t")
                        .append(location).append("\n");
            }

            // Display the records using a JOptionPane
            JOptionPane.showMessageDialog(null, sb.toString(), "Interviews", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Interviews();
            }
        });
    }
}

