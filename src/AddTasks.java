import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AddTasks extends JFrame {
    private JPanel MainPanel;
    private JLabel Title;
    private JLabel AddTaskName;
    private JLabel Category;
    private JLabel Deadline;
    private JLabel Priority;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton Confirm;
    private JButton Reset_form;
    private JComboBox Combo_Box;

    //this is our constructor
    public AddTasks() {
        setContentPane(MainPanel);
        setTitle("Add Tasks");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocation(225, 165);
        setVisible(true);

        Combo_Box.addItem("Low");
        Combo_Box.addItem("Medium");
        Combo_Box.addItem("High");

        Reset_form.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText(" ");
                textField2.setText(" ");
                textField3.setText(" ");

                Combo_Box.setSelectedItem("Low");
            }
        });
        Confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Properties properties = new Properties();

                try (InputStream input = new FileInputStream("src/Config.properties")) {
                    properties.load(input);

                    // Retrieve database credentials
                    String db_url = properties.getProperty("db.url");
                    String db_user = properties.getProperty("db.user");
                    String db_password = properties.getProperty("db.password");

                    // Debug message showing the loaded properties
                    String message_string = "DB URL: " + db_url + "\nDB User: " + db_user + "\nDB Password: " + db_password;
                    JOptionPane.showMessageDialog(null, message_string);

                    // Establish connection and insert data
                    try (Connection conn = DriverManager.getConnection(db_url, db_user, db_password)) {
                        System.out.println("Connection successful");

                        String query = "INSERT INTO Task_Database.tasks(Task_Name, Category, Deadline, Priority, created_by) " +
                                "VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insert_statement = conn.prepareStatement(query)) {
                            insert_statement.setString(1, textField1.getText().trim());
                            insert_statement.setString(2, textField2.getText().trim());
                            insert_statement.setString(3, textField3.getText().trim());
                            insert_statement.setString(4, Combo_Box.getSelectedItem().toString());
                            insert_statement.setString(5, "Time and Task Manager GUI");

                            int rowsInserted = insert_statement.executeUpdate();
                            if (rowsInserted > 0) {
                                JOptionPane.showMessageDialog(null, "Insert successful!");
                            } else {
                                JOptionPane.showMessageDialog(null, "No rows inserted.");
                            }
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading config file: " + ex.getMessage());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        });
    }
}