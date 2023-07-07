package consultancy_services_for_job_hunt;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class JobPosting extends JFrame {
    private JLabel lblJobPostingId;
    private JTextField txtJobPostingId;
    private JLabel lblJobTitle;
    private JTextField txtJobTitle;
    private JLabel lblCompanyId;
    private JTextField txtCompanyId;
    private JLabel lblSalary;
    private JTextField txtSalary;
    private JLabel lblDeadline;
    private JTextField txtDeadline;
    private JButton btnInsert;
    private JButton btnModify;
    private JButton btnDelete;
    private JButton btnDisplay;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private DefaultTableModel tableModel;
    private JTable table;

    public JobPosting() {
        initializeUI();
        connectToDatabase();
    }

    private void initializeUI() {
        setTitle("Job Postings");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        lblJobPostingId = new JLabel("Job Posting ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(lblJobPostingId, constraints);

        txtJobPostingId = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(txtJobPostingId, constraints);

        lblJobTitle = new JLabel("Job Title:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(lblJobTitle, constraints);

        txtJobTitle = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(txtJobTitle, constraints);

        lblCompanyId = new JLabel("Company ID:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(lblCompanyId, constraints);

        txtCompanyId = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(txtCompanyId, constraints);

        lblSalary = new JLabel("Salary:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(lblSalary, constraints);

        txtSalary = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(txtSalary, constraints);

        lblDeadline = new JLabel("Application Deadline:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(lblDeadline, constraints);

        txtDeadline = new JTextField(10);
        constraints.gridx = 1;
        constraints.gridy = 4;
        add(txtDeadline, constraints);

        btnInsert = new JButton("Insert");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        add(btnInsert, constraints);

        btnModify = new JButton("Modify");
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.gridwidth = 1;
        add(btnModify, constraints);

        btnDelete = new JButton("Delete");
        constraints.gridx = 1;
        constraints.gridy = 6;
        add(btnDelete, constraints);

        btnDisplay = new JButton("Display");
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 2;
        add(btnDisplay, constraints);

        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertJobPosting();
            }
        });

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyJobPosting();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteJobPosting();
            }
        });

        btnDisplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayJobPostings();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "system";
            String password = "nuha";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertJobPosting() {
        try {
            String query = "INSERT INTO job_postings (job_posting_id, job_title, company_id, salary, application_deadline) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(txtJobPostingId.getText()));
            preparedStatement.setString(2, txtJobTitle.getText());
            preparedStatement.setInt(3, Integer.parseInt(txtCompanyId.getText()));
            preparedStatement.setDouble(4, Double.parseDouble(txtSalary.getText()));
            preparedStatement.setString(5, txtDeadline.getText());
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Job Posting inserted successfully!");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void modifyJobPosting() {
        try {
            String query = "UPDATE job_postings SET job_title = ?, company_id = ?, salary = ?, application_deadline = ? WHERE job_posting_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, txtJobTitle.getText());
            preparedStatement.setInt(2, Integer.parseInt(txtCompanyId.getText()));
            preparedStatement.setDouble(3, Double.parseDouble(txtSalary.getText()));
            preparedStatement.setString(4, txtDeadline.getText());
            preparedStatement.setInt(5, Integer.parseInt(txtJobPostingId.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Job Posting modified successfully!");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteJobPosting() {
        try {
            String query = "DELETE FROM job_postings WHERE job_posting_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(txtJobPostingId.getText()));
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Job Posting deleted successfully!");
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayJobPostings() {
        try {
            String query = "SELECT * FROM job_postings";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            tableModel = new DefaultTableModel();
            tableModel.addColumn("Job Posting ID");
            tableModel.addColumn("Job Title");
            tableModel.addColumn("Company ID");
            tableModel.addColumn("Salary");
            tableModel.addColumn("Application Deadline");

            while (resultSet.next()) {
                int jobPostingId = resultSet.getInt("job_posting_id");
                String jobTitle = resultSet.getString("job_title");
                int companyId = resultSet.getInt("company_id");
                double salary = resultSet.getDouble("salary");
                String deadline = resultSet.getString("application_deadline");

                Object[] rowData = { jobPostingId, jobTitle, companyId, salary, deadline };
                tableModel.addRow(rowData);
            }

            table = new JTable(tableModel);
            JOptionPane.showMessageDialog(this, new JScrollPane(table), "Job Postings", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtJobPostingId.setText("");
        txtJobTitle.setText("");
        txtCompanyId.setText("");
        txtSalary.setText("");
        txtDeadline.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JobPosting();
            }
        });
    }
}
