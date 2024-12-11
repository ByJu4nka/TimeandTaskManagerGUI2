import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class MarkTaskAsCompleted extends JFrame
{
    private JPanel MainPanel;
    private JButton btnMarkCompleted;
    private JTable tasksCompletionTable;
    private JButton btnRefresh;
    private JLabel MarkCompleteTitle;
    private JScrollPane scrollPaneCompletion;

    //this is our constructor
    public MarkTaskAsCompleted () {
        setContentPane(MainPanel);
        setTitle("Complete Tasks");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        // Set up the table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tasksCompletionTable.setModel(tableModel);
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Deadline");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Created By");
        tableModel.addColumn("Created Date");

        // Load tasks on launch
        loadTasks(tableModel);

        // Refresh button action
        btnRefresh.addActionListener(e -> loadTasks(tableModel));

        // Mark as Completed button action
        btnMarkCompleted.addActionListener(e -> markTaskAsCompleted(tableModel));
    }

    private void loadTasks(DefaultTableModel tableModel) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Database credentials
        String dbUrl = "jdbc:mysql://localhost:3306/Task_Database";
        String dbUser = "root";
        String dbPassword = "rootroot";

        // SQL query to fetch all tasks
        String query = "SELECT Task_Name, Category, Deadline, Priority, created_by, created_date FROM tasks";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Populate the table with data from the database
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("Task_Name"));
                row.add(rs.getString("Category"));
                row.add(rs.getString("Deadline"));
                row.add(rs.getString("Priority"));
                row.add(rs.getString("created_by"));
                row.add(rs.getTimestamp("created_date").toString());
                tableModel.addRow(row);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    private void markTaskAsCompleted(DefaultTableModel tableModel) {
        // Ensure a row is selected
        int selectedRow = tasksCompletionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as completed.");
            return;
        }

        // Get task name (assuming it uniquely identifies the task)
        String taskName = tableModel.getValueAt(selectedRow, 0).toString();

        // Database credentials
        String dbUrl = "jdbc:mysql://localhost:3306/Task_Database";
        String dbUser = "root";
        String dbPassword = "rootroot";

        // SQL query to delete the task
        String query = "DELETE FROM tasks WHERE Task_Name = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameter and execute the query
            stmt.setString(1, taskName);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Task marked as completed and removed.");
                tableModel.removeRow(selectedRow); // Update table UI
            } else {
                JOptionPane.showMessageDialog(this, "Error: Task not found or could not be deleted.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    // Main method to run the task completer
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MarkTaskAsCompleted());
    }
}
