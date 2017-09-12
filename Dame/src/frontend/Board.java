package frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import backend.BasicToken;
import backend.Token;

public class Board extends JFrame {

	private enum Game {
		DRAUGHTS, CHESS
	}
	
	private enum Color {
		WHITE, BLACK
	}
	
	private static final long serialVersionUID = 1L;
	private static final int FIELDSIZE = 8;
	private static final Dimension PREFERREDSIZE = new Dimension(500, 500);
	protected final Image DRAUGHTSBLACK = new ImageIcon(getClass().getResource("../images/draughts_black.jpg")).getImage();
	protected final Image DRAUGHTSWHITE = new ImageIcon(getClass().getResource("../images/draughts_white.jpg")).getImage();
	
	private boolean isRunning = false;
	private boolean moveStart = true;
	private boolean turn = false;
	
	private JPanel playPanel;
	private Field[][] playfield = new Field[FIELDSIZE][FIELDSIZE];
	
	private JPanel buttonPanel;
	private JButton startButton;
	private JButton resetButton;
	private JLabel turnLabel;
	
	private JPanel optionPanel;
	private JLabel gameLabel;
	private JPanel gamePanel;
	private ButtonGroup gameGroup;
	private JRadioButton draughtsButton;
	private JRadioButton chessButton;
	private JLabel colorLabel;
	private JPanel colorPanel;
	private ButtonGroup colorGroup;
	private JRadioButton whiteButton;
	private JRadioButton blackButton;
	
	public FieldListener fl = new FieldListener();
	public ButtonListener bl = new ButtonListener();
	
	public Board() {
		frameInit();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Dame");
		setLayout(new BorderLayout());
		
		isRunning = false;
		
		playPanel = new JPanel(new GridLayout(FIELDSIZE, FIELDSIZE));
		playPanel.setPreferredSize(PREFERREDSIZE);
		
		boolean black = false;
		
		for(int row=0 ; row<playfield.length ; row++) {
			for(int column=0 ; column<playfield[row].length ; column++) {
				Field field = new Field(this, black);
				field.addMouseListener(fl);
				playfield[row][column] = field;
				playPanel.add(field);
				black = !black;
			}
			black = !black;
		}
		
		buttonPanel = new JPanel(new GridLayout(1, 6));
		startButton = new JButton("Start");
		startButton.addActionListener(bl);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(bl);
		turnLabel = new JLabel();
		buttonPanel.add(new JLabel());
		buttonPanel.add(startButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(resetButton);
		buttonPanel.add(new JLabel());
		buttonPanel.add(turnLabel);
		
		optionPanel = new JPanel(new GridLayout(4, 1));
		
		gameLabel = new JLabel("Spiel", JLabel.CENTER);
		gamePanel = new JPanel(new FlowLayout());
		draughtsButton = new JRadioButton("Dame");
		chessButton = new JRadioButton("Schach");
		gameGroup = new ButtonGroup();
		gameGroup.add(draughtsButton);
		gameGroup.add(chessButton);
		gamePanel.add(draughtsButton);
		gamePanel.add(chessButton);
		
		colorLabel = new JLabel("Farbe", JLabel.CENTER);
		colorPanel = new JPanel(new FlowLayout());
		whiteButton = new JRadioButton("weiß");
		blackButton = new JRadioButton("schwarz");
		colorGroup = new ButtonGroup();
		colorGroup.add(whiteButton);
		colorGroup.add(blackButton);
		colorPanel.add(whiteButton);
		colorPanel.add(blackButton);
		
		optionPanel.add(gameLabel);
		optionPanel.add(gamePanel);
		optionPanel.add(colorLabel);
		optionPanel.add(colorPanel);
		
		getContentPane().add(playPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(optionPanel, BorderLayout.EAST);
		pack();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void startRunning() {
		turnLabel.setText("Zug: \"weiß\"");
		isRunning = true;
	}
	
	public void endRunning() {
		resetTurn();
		moveFinished();
		isRunning = false;
	}
	
	public boolean isMoveStart() {
		return moveStart;
	}
	
	public void moveStarted() {
		moveStart = false;
	}
	
	public void moveFinished() {
		moveStart = true;
	}
	
	public boolean getTurn() {
		return turn;
	}
	
	private void resetTurn() {
		turn = false;
		turnLabel.setText("");
	}
	
	protected void nextTurn() {
		turn = !turn;
		
		if (turn) {
			turnLabel.setText("Zug: \"schwarz\"");
		} else {
			turnLabel.setText("Zug: \"weiß\"");
		}
	}
	
	private void initPlayfield(Game game, Color color) {
		Field field;
		boolean isWhite;
		
		if (color == Color.WHITE) {
			isWhite = true;
		} else {
			isWhite = false;
		}
		
		finishPlayfield();
		
		switch (game) {
			case DRAUGHTS:
				for(int row=0 ; row<3 ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						field = playfield[row][column];
						
						if (field.isBlack()) {
							field.setToken(new BasicToken(field, isWhite));
						}
					}
				}
				
				for(int row=5 ; row<playfield.length ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						field = playfield[row][column];
						
						if (field.isBlack()) {
							field.setToken(new BasicToken(field, !isWhite));
						}
					}
				}
				startRunning();
				break;
			
			case CHESS:
				break;
			
			default:
				break;
		}
	}
	
	private void finishPlayfield() {
		for(int row=0 ; row<playfield.length ; row++) {
			for(int column=0 ; column<playfield[row].length ; column++) {
				playfield[row][column].reset();
			}
		}
		endRunning();
	}
	
	private class FieldListener implements MouseListener {

		public Token token = null;
		
		public void mouseClicked(MouseEvent click) {
			
			Field field = null;
			
			if (click.getButton() == 1) {
				for(int row=0 ; row<playfield.length ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						if (click.getSource() == playfield[row][column]) {
							System.out.println("Klick auf Feld (" + row + "," + column + ") !!");
							field = playfield[row][column];
							row = playfield.length;
							break;
						}
					}
				}
				
				if (isRunning()) {
					if (isMoveStart()) {
						if (field.getToken() != null) {
							token = field.getToken();
							if (token.isBlack() == turn) {
								field.removeToken();
							}
						}
					} else {
						field.setTokenOnValidField(token);
					}
				}
			} else if (click.getButton() == 3 || MouseInfo.getNumberOfButtons() == 2 && click.getButton() == 2) {
				if (isRunning() && !isMoveStart()) {
					token.getField().setToken(token);
					moveFinished();
				}
			}
		}
		
		public void mouseEntered(MouseEvent enter) {
			
		}
		
		public void mouseExited(MouseEvent exit) {
			
		}
		
		public void mousePressed(MouseEvent press) {
			
		}
		
		public void mouseReleased(MouseEvent release) {
			
		}
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == startButton) {
				if (draughtsButton.isSelected()) {
					if (whiteButton.isSelected()) {
						initPlayfield(Game.DRAUGHTS, Color.WHITE);
					} else if (blackButton.isSelected()) {
						initPlayfield(Game.DRAUGHTS, Color.BLACK);
					}
				} else if (chessButton.isSelected()) {
					if (whiteButton.isSelected()) {
						initPlayfield(Game.CHESS, Color.WHITE);
					} else if (blackButton.isSelected()) {
						initPlayfield(Game.CHESS, Color.BLACK);
					}
				}
			} else if (ae.getSource() == resetButton) {
				finishPlayfield();
			}
		}
	}
}
