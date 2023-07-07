package consultancy_services_for_job_hunt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JobApplications extends JFrame {
    private JLabel idLabel, seekerLabel, postingLabel, dateLabel, statusLabel;
    private JTextField idField, seekerField, postingField, dateField, statusField;
    private JButton insertButton, modifyButton, deleteButton;

    public JobApplications() {
        initializeComponents();
        setupLayout();
        setupListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        idLabel = new JLabel("Application ID:");
        seekerLabel = new JLabel("Seeker ID:");
        postingLabel = new JLabel("Posting ID:");
        dateLabel = new JLabel("Application Date:");
        statusLabel = new JLabel("Application Status:");

        idField = new JTextField(10);
        seekerField = new JTextField(10);
        postingField = new JTextField(10);
        dateField = new JTextField(20);
        statusField = new JTextField(20);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
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
        add(seekerLabel, c);

        c.gridx = 1;
        add(seekerField, c);

        c.gridx = 0;
        c.gridy = 2;
        add(postingLabel, c);

        c.gridx = 1;
        add(postingField, c);

        c.gridx = 0;
        c.gridy = 3;
        add(dateLabel, c);

        c.gridx = 1;
        add(dateField, c);

        c.gridx = 0;
        c.gridy = 4;
        add(statusLabel, c);

        c.gridx = 1;
        add(statusField, c);

        c.gridx = 0;
        c.gridy = 5;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 2;
        add(deleteButton, c);
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
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";
        String password = "nuha";
        return DriverManager.getConnection(url, username, password);
    }

    private void insertRecord() {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO job_applications (job_application_id, job_seeker_id, job_posting_id, application_date, application_status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.setInt(2, Integer.parseInt(seekerField.getText()));
            statement.setInt(3, Integer.parseInt(postingField.getText()));
            statement.setString(4, dateField.getText());
            statement.setString(5, statusField.getText());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record inserted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyRecord() {
        try (Connection conn = getConnection()) {
            String query = "UPDATE job_applications SET job_seeker_id = ?, job_posting_id = ?, application_date = ?, application_status = ? WHERE job_application_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(seekerField.getText()));
            statement.setInt(2, Integer.parseInt(postingField.getText()));
            statement.setString(3, dateField.getText());
            statement.setString(4, statusField.getText());
            statement.setInt(5, Integer.parseInt(idField.getText()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record modified successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteRecord() {
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM job_applications WHERE job_application_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JobApplications();
            }
        });
    }
}
