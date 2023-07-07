package consultancy_services_for_job_hunt;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Companies extends JFrame {
    private JTextField companyIdField, companyNameField, websiteField, descriptionField, locationField;
    private JButton insertButton, modifyButton, deleteButton, displayButton;
    private JComboBox<String> companyIdComboBox;

    private List<String> companyIds; // Store the company IDs

    public Companies() {
        setTitle("Company Management App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Initialize components
        companyIdField = new JTextField(10);
        companyNameField = new JTextField(20);
        websiteField = new JTextField(20);
        descriptionField = new JTextField(50);
        locationField = new JTextField(20);

        insertButton = new JButton("Insert");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");
        displayButton = new JButton("Display");
        companyIdComboBox = new JComboBox<>();

        // Set up the layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Company ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(companyIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Company Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(companyNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Website:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(websiteField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Location:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(locationField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(insertButton, gbc);

        gbc.gridx = 1;
        add(modifyButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(deleteButton, gbc);

        gbc.gridx = 1;
        add(displayButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(new JLabel("Select a company ID:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(companyIdComboBox, gbc);

        // Add action listeners
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertCompany();
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modifyCompany();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCompany();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayCompanies();
            }
        });

        companyIdComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedId = (String) companyIdComboBox.getSelectedItem();
                loadCompanyData(selectedId);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        loadData(); // Load the company IDs
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "system";
        String password = "nuha";
        return DriverManager.getConnection(url, username, password);
    }

    private void loadData() {
        companyIds = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String query = "SELECT company_id FROM companies";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("company_id");
                companyIds.add(id);
            }

            loadComboBoxData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadComboBoxData() {
        companyIdComboBox.removeAllItems();

        for (String id : companyIds) {
            companyIdComboBox.addItem(id);
        }
    }

    private void loadCompanyData(String selectedId) {
        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM companies WHERE company_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, selectedId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String companyName = resultSet.getString("company_name");
                String website = resultSet.getString("website");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");

                // Set the text field values
                companyIdField.setText(selectedId);
                companyNameField.setText(companyName);
                websiteField.setText(website);
                descriptionField.setText(description);
                locationField.setText(location);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertCompany() {
        String companyId = companyIdField.getText();
        String companyName = companyNameField.getText();
        String website = websiteField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();

        try (Connection conn = getConnection()) {
            String query = "INSERT INTO companies (company_id, company_name, website, description, location) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, companyId);
            statement.setString(2, companyName);
            statement.setString(3, website);
            statement.setString(4, description);
            statement.setString(5, location);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Company inserted successfully.");
            clearFields();
            loadData(); // Update the drop-down menu with new data
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while inserting company.");
        }
    }

    private void modifyCompany() {
        String companyId = companyIdField.getText();
        String companyName = companyNameField.getText();
        String website = websiteField.getText();
        String description = descriptionField.getText();
        String location =locationField.getText();

        try (Connection conn = getConnection()) {
            String query = "UPDATE companies SET company_name = ?, website = ?, description = ?, location = ? WHERE company_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, companyName);
            statement.setString(2, website);
            statement.setString(3, description);
            statement.setString(4, location);
            statement.setString(5, companyId);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Company modified successfully.");
            clearFields();
            loadData(); // Update the drop-down menu with modified data
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while modifying company.");
        }
    }

    private void deleteCompany() {
        String companyId = (String) companyIdComboBox.getSelectedItem();

        try (Connection conn = getConnection()) {
            // Check if there are any job postings associated with the company
            String checkQuery = "SELECT * FROM job_postings WHERE company_id = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, companyId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(this, "Cannot delete the company. Job postings exist for the company.");
            } else {
                String deleteQuery = "DELETE FROM companies WHERE company_id = ?";
                PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
                deleteStatement.setString(1, companyId);
                int rowsDeleted = deleteStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Company deleted successfully.");
                    clearFields();
                    loadData(); // Update the drop-down menu with updated data
                } else {
                    JOptionPane.showMessageDialog(this, "No company found with the selected ID.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while deleting company.");
        }
    }

    private void displayCompanies() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Company ID");
        tableModel.addColumn("Company Name");
        tableModel.addColumn("Website");
        tableModel.addColumn("Description");
        tableModel.addColumn("Location");

        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM companies";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String companyId = resultSet.getString("company_id");
                String companyName = resultSet.getString("company_name");
                String website = resultSet.getString("website");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");

                tableModel.addRow(new Object[]{companyId, companyName, website, description, location});
            }

            JTable companyTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(companyTable);

            JDialog dialog = new JDialog(this, "Company List", true);
            dialog.getContentPane().add(scrollPane);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while displaying companies.");
        }
    }

    private void clearFields() {
        companyIdField.setText("");
        companyNameField.setText("");
        websiteField.setText("");
        descriptionField.setText("");
        locationField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Companies();
            }
        });
    }
}
