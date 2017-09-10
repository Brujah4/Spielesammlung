package frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

public class Feld extends JComponent
{
	private static final long serialVersionUID = 1L;
	private Rectangle2D.Float rechteck = new Rectangle2D.Float(0,0,60,60);
    private Ellipse2D.Float kreis = new Ellipse2D.Float(5,5,50,50);

    public void paint (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));
        g2.setPaint(Color.BLUE);
        g2.fill(rechteck);
        g2.draw(rechteck);
        g2.setPaint(Color.RED);
        g2.fill(kreis);
        g2.draw(kreis);
    }
}
