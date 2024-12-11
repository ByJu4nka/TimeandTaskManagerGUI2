import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class ViewTasks extends JFrame
{

    private JPanel MainPanel;
    private JTable tasksTable;
    private JButton refreshButton;
    private JLabel View_Tasks_Title;
    private JScrollPane tableScrollPane;

    //this is our constructor
    public ViewTasks()
    {
        setContentPane(MainPanel);
        setTitle( "View Tasks");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600);
        setLocation(225 , 165);
        setVisible( true );

        DefaultTableModel tableModel = new DefaultTableModel();
        tasksTable.setModel(tableModel);
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Deadline");
        tableModel.addColumn("Priority");
        tableModel.addColumn("Created By");
        tableModel.addColumn("Created Date");

        // Load tasks on launch
        loadTasks(tableModel);

        // Refresh button action
        refreshButton.addActionListener(e -> loadTasks(tableModel));
    }

    private void loadTasks(DefaultTableModel tableModel) {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Database credentials
        String dbUrl = "jdbc:mysql://localhost:3306/Task_Database";
        String dbUser = "root";
        String dbPassword = "rootroot";

        // SQL query
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

    // Main method to run the viewer
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewTasks());
    }
}

