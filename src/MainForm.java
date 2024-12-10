import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
    private JPanel MainPanel;
    private JButton button_2;
    private JButton button_1;
    private JButton button_3;
    private JButton button_exit;

    //this is our constructor
    public MainForm() {
        setContentPane(MainPanel);
        setTitle("Main Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);


        button_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0); //Close the application
            }
        });


        button_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new AddTasks();
            }
        });


        button_2.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new ViewTasks();
            }
        });


        button_3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new MarkTaskAsCompleted();
            }
        });
    }

    public static void main(String[] args )
    {
        //this is our entry point into the program
        new MainForm ();
    }
}
