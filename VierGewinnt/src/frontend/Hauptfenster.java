package frontend;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hauptfenster extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane = (JPanel) getContentPane();
    
    public Hauptfenster()
    {
        contentPane.setLayout(new BorderLayout());
        
        contentPane.add(new Spielfeld(), BorderLayout.CENTER);
    }
    
}
