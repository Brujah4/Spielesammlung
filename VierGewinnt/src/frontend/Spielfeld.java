package frontend;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class Spielfeld extends JPanel
{
    
    private Feld[][] felder=new Feld[7][6];
    private GridBagLayout gbl=new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    
    public Spielfeld()
    {
        setLayout(gbl);

        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                felder[i][j]=new Feld();
                
                gbc.gridx=i;
                gbc.gridy=j;
                gbl.setConstraints(felder[i][j], gbc);
                add(felder[i][j], gbc);
            }
        }
    }
}
