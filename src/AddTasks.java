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

public class AddTasks extends JFrame
{
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
    public AddTasks()
    {
        setContentPane(MainPanel);
        setTitle( "Add Tasks");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600);
        setLocation(225 , 165);
        setVisible( true );

        Combo_Box.addItem("Low");
        Combo_Box.addItem("Medium");
        Combo_Box.addItem("High");

        Reset_form.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField1.setText(" ");
                textField2.setText(" ");
                textField3.setText(" ");

                Combo_Box.setSelectedItem("Low");
            }
        });
        Confirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Properties properties = new Properties();

                try ( InputStream input = new FileInputStream ("src/Config.properties"))
                {
                    properties.load( input );

                    String db_name = properties.getProperty("db.url");
                    String db_user = properties.getProperty("db.user");
                    String db_password = properties.getProperty("db.password");

                    String message_string = " DB name is " + db_name + " DB User is " + db_user + " DB password is " + db_password;
                    JOptionPane.showMessageDialog(null, message_string);

                    try (Connection conn = DriverManager.getConnection(db_name, db_user, db_password))
                    {
                        System.out.println("Connection succesfull");

                        PreparedStatement insert_statement;
                        insert_statement = conn.prepareStatement("insert into Task_Database.tasks(Task_Name, Category, Deadline, Priority, created_by) values (?, ?, ?, ?, ?)");

                        insert_statement.setString(1, textField1.getText());
                        insert_statement.setString(2, textField2.getText());
                        insert_statement.setString(3, textField3.getText());
                        insert_statement.setString(4, Combo_Box.getSelectedItem().toString());
                        insert_statement.setString(5, ("Time and Task Maneger GUI"));

                        int n = insert_statement.executeUpdate();
                        if (n > 0)
                        {
                            JOptionPane.showMessageDialog("insert successful");
                        }

                    }
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }
}
