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

public class Board extends JFrame {
	
	private enum Game {
		DRAUGHTS, CHESS
	}

	private static final long serialVersionUID = 1L;
	private static final int FIELDSIZE = 8;
	private static final Dimension MINSIZE = new Dimension(500, 500);
	protected final Image DRAUGHTSBLACK = new ImageIcon(getClass().getResource("../images/draughts_black.jpg")).getImage();
	protected final Image DRAUGHTSWHITE = new ImageIcon(getClass().getResource("../images/draughts_white.jpg")).getImage();
	
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
	
	private void initPlayfield(Game game) {
		Field field;
		
		switch (game) {
			case DRAUGHTS:
				for(int row=0 ; row<3 ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						field = playfield[row][column];
						
						if (field.isBlack()) {
							field.setToken(new BasicToken(field, true));
						}
					}
				}
				
				for(int row=5 ; row<playfield.length ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						field = playfield[row][column];
						
						if (field.isBlack()) {
							field.setToken(new BasicToken(field, false));
						}
					}
				}
				break;
			
			case CHESS:
				break;
			
			default:
				break;
		}
	}
	
	private class FieldListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			System.out.println("Klick!");
		}
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == startButton) {
				initPlayfield(Game.DRAUGHTS);
			} else if (ae.getSource() == resetButton) {
				for(int row=0 ; row<playfield.length ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						playfield[row][column].reset();
					}
				}
			}
		}
	}
}
