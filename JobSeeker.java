package consultancy_services_for_job_hunt;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobSeeker extends JFrame {
    private JLabel idLabel, nameLabel, emailLabel, phoneLabel, qualificationsLabel;
    private JTextField idField, nameField, emailField, phoneField, qualificationsField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private JComboBox<String> idComboBox, nameComboBox, emailComboBox, phoneComboBox, qualificationsComboBox;

    private List<String> jobSeekerIds; // Store the job seeker IDs

    public JobSeeker() {
        initializeComponents();
        setupLayout();
        setupListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadData(); // Load the job seekers data
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        idLabel = new JLabel("Job Seeker ID:");
        nameLabel = new JLabel("Name:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Phone Number:");
        qualificationsLabel = new JLabel("Qualifications:");

        idField = new JTextField(10);
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(10);
        qualificationsField = new JTextField(50);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");

        idComboBox = new JComboBox<>();
        nameComboBox = new JComboBox<>();
        emailComboBox = new JComboBox<>();
        phoneComboBox = new JComboBox<>();
        qualificationsComboBox = new JComboBox<>();
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

        c.gridx = 2;
        add(idComboBox, c);

        c.gridx = 0;
        c.gridy = 1;
        add(nameLabel, c);

        c.gridx = 1;
        add(nameField, c);

        c.gridx = 2;
        add(nameComboBox, c);

        c.gridx = 0;
        c.gridy = 2;
        add(emailLabel, c);

        c.gridx = 1;
        add(emailField, c);

        c.gridx = 2;
        add(emailComboBox, c);

        c.gridx = 0;
        c.gridy = 3;
        add(phoneLabel, c);

        c.gridx = 1;
        add(phoneField, c);

        c.gridx = 2;
        add(phoneComboBox, c);

        c.gridx = 0;
        c.gridy = 4;
        add(qualificationsLabel, c);

        c.gridx = 1;
        add(qualificationsField, c);

        c.gridx = 0;
        c.gridy = 5;
        add(insertButton, c);

        c.gridx = 1;
        add(modifyButton, c);

        c.gridx = 0;
        c.gridy = 6;
        add(deleteButton, c);

        c.gridx = 1;
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

        idComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedId = (String) idComboBox.getSelectedItem();
                loadJobSeekerData(selectedId);
            }
        });
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";
        String password = "nuha";
        return DriverManager.getConnection(url, username, password);
    }

    private void loadData() {
        jobSeekerIds = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String query = "SELECT job_seeker_id FROM job_seekers";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("job_seeker_id");
                jobSeekerIds.add(id);
            }

            loadComboBoxData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadComboBoxData() {
        idComboBox.removeAllItems();
        nameComboBox.removeAllItems();
        emailComboBox.removeAllItems();
        phoneComboBox.removeAllItems();
        qualificationsComboBox.removeAllItems();

        for (String id : jobSeekerIds) {
            idComboBox.addItem(id);
        }
    }

    private void loadJobSeekerData(String selectedId) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM job_seekers WHERE job_seeker_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, selectedId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phoneNum = resultSet.getString("phone_num");
                String qualifications = resultSet.getString("qualifications");

                // Set the text field values
                idField.setText(selectedId);
                nameField.setText(name);
                emailField.setText(email);
                phoneField.setText(phoneNum);
                qualificationsField.setText(qualifications);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertRecord() {
        try (Connection conn = getConnection()) {
            String query = "INSERT INTO job_seekers (job_seeker_id, name, email, phone_num, qualifications) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.setString(2, nameField.getText());
            statement.setString(3, emailField.getText());
            statement.setString(4, phoneField.getText());
            statement.setString(5, qualificationsField.getText());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record inserted successfully!");
            clearFields();
            loadData(); // Update the drop-down menu with new data
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void modifyRecord() {
        try (Connection conn = getConnection()) {
            String query = "UPDATE job_seekers SET name = ?, email = ?, phone_num = ?, qualifications = ? WHERE job_seeker_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nameField.getText());
            statement.setString(2, emailField.getText());
            statement.setString(3, phoneField.getText());
            statement.setString(4, qualificationsField.getText());
            statement.setInt(5, Integer.parseInt(idField.getText()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record modified successfully!");
            clearFields();
            loadData(); // Update the drop-down menu with modified data
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteRecord() {
        try (Connection conn = getConnection()) {
            String query = "DELETE FROM job_seekers WHERE job_seeker_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(idField.getText()));
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");
            clearFields();
            loadData(); // Update the drop-down menu with updated data
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayRecords() {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM job_seekers";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Job Seeker ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Email");
            tableModel.addColumn("Phone Number");
            tableModel.addColumn("Qualifications");

            // Populate the table model with data from the result set
            while (resultSet.next()) {
                int seekerId = resultSet.getInt("job_seeker_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phoneNum = resultSet.getString("phone_num");
                String qualifications = resultSet.getString("qualifications");

                Object[] rowData = { seekerId, name, email, phoneNum, qualifications };
                tableModel.addRow(rowData);
            }

            JTable recordsTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(recordsTable);

            // Create and show the dialog box with the table
            JDialog dialog = new JDialog();
            dialog.setTitle("Job Seekers");
            dialog.getContentPane().add(scrollPane);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        qualificationsField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JobSeeker();
            }
        });
    }
}

