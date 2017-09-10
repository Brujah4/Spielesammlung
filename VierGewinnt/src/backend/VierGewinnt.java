package backend;

import java.awt.Dimension;
import javax.swing.JFrame;
import frontend.Hauptfenster;

public class VierGewinnt {

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
