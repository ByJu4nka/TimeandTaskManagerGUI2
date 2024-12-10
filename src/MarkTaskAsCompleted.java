import javax.swing.*;

public class MarkTaskAsCompleted extends JFrame
{
    private JPanel MainPanel;

    //this is our constructor
    public MarkTaskAsCompleted()
    {
        setContentPane(MainPanel);
        setTitle( "Mark Task As Completed");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600);
        setLocation(225 , 165);
        setVisible( true );
    }
}
