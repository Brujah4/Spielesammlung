package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.BasicToken;
import backend.Token;

public class Board extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int FIELDSIZE = 8;
	private static final Dimension MINSIZE = new Dimension(500, 500);
	private final Image DRAUGHTSBLACK = new ImageIcon(getClass().getResource("../images/draughts_black.jpg")).getImage();
	private final Image DRAUGHTSWHITE = new ImageIcon(getClass().getResource("../images/draughts_white.jpg")).getImage();
	
	private JPanel playPanel;
	private Field[][] playfield = new Field[FIELDSIZE][FIELDSIZE];
	private JPanel buttonPanel;
	private JButton startButton;
	private JButton resetButton;
	
	public FieldListener fl = new FieldListener();
	public ButtonListener bl = new ButtonListener();
	
	public Board() {
		frameInit();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(MINSIZE);
		setResizable(false);
		setTitle("Dame");
		setLayout(new BorderLayout());
		
		playPanel = new JPanel(new GridLayout(FIELDSIZE, FIELDSIZE));
		
		boolean black = false;
		
		for(int row=0 ; row<playfield.length ; row++) {
			for(int column=0 ; column<playfield[row].length ; column++) {
				Field field = new Field(this, black);
				field.addActionListener(fl);
				
				if (black) {
					if (row < 3) {
						field.setToken(new BasicToken(field, true));
					} else if (row > 4) {
						field.setToken(new BasicToken(field, false));
					}
				}
				
				playfield[row][column] = field;
				playPanel.add(field);
				black = !black;
			}
			black = !black;
		}
		
		buttonPanel = new JPanel(new GridLayout(0, 6));
		startButton = new JButton("Start");
		startButton.addActionListener(bl);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(bl);
		buttonPanel.add(new JLabel());
		buttonPanel.add(startButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(new JLabel());
		buttonPanel.add(resetButton);
		buttonPanel.add(new JLabel());
		
		getContentPane().add(playPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}
	
	private void initPlayfield() {
		Field field;
		Token token;
		
		for(int row=0 ; row<playfield.length ; row++) {
			for(int column=0 ; column<playfield[row].length ; column++) {
				field = playfield[row][column];
				token = field.getToken();
				
				if (token != null) {
					if (token.isBlack()) {
						ImageIcon icon = new ImageIcon(DRAUGHTSBLACK.getScaledInstance(field.getWidth(), field.getHeight(), Image.SCALE_FAST));
						field.setIcon(icon);
					} else {
						ImageIcon icon = new ImageIcon(DRAUGHTSWHITE.getScaledInstance(field.getWidth(), field.getHeight(), Image.SCALE_FAST));
						field.setIcon(icon);
					}
				}
			}
		}
	}
	
	private class FieldListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Klick!");
		}
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Klick Button!");
		}
	}
}
