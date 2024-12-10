import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
    private JTextField textField4;
    private JButton Confirm;
    private JButton Reset_form;

    //this is our constructor
    public AddTasks()
    {
        setContentPane(MainPanel);
        setTitle( "Add Tasks");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600);
        setLocation(225 , 165);
        setVisible( true );
        Reset_form.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                textField1.setText(" ");
                textField2.setText(" ");
                textField3.setText(" ");
                textField4.setText(" ");
            }
        });
    }
}
