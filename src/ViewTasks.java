import javax.swing.*;

public class ViewTasks extends JFrame
{
    private JPanel MainPanel;

    //this is our constructor
    public ViewTasks()
    {
        setContentPane(MainPanel);
        setTitle( "View Tasks");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600);
        setLocation(225 , 165);
        setVisible( true );
    }
}
