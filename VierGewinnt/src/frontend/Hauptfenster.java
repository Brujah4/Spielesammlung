package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
    
    public static void main(String[] args) 
    {
        Hauptfenster frame=new Hauptfenster();
        frame.setSize(new Dimension(449, 410));
        frame.setTitle("Vier Gewinnt");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
