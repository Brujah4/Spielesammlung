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

	protected enum Game {
		DRAUGHTS, CHESS
	}
	
	public enum Colour {
		WHITE, BLACK
	}
	
	private static final long serialVersionUID = 1L;
	private static final int FIELDSIZE = 8;
	private static final Dimension PREFERREDSIZE = new Dimension(500, 500);
	protected final Image DRAUGHTSBLACK = new ImageIcon(getClass().getResource("../images/draughts_black.jpg")).getImage();
	protected final Image DRAUGHTSWHITE = new ImageIcon(getClass().getResource("../images/draughts_white.jpg")).getImage();
	
	private Game game = null;
	private Colour colour = null;
	private boolean isRunning = false;
	private boolean moveStart = true;
	private boolean turn = true; // (turn == false => "weiß" ist am Zug) ; (turn == true => "schwarz" ist am Zug)
	
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
	private JLabel colourLabel;
	private JPanel colourPanel;
	private ButtonGroup colourGroup;
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
				Field field = new Field(this, black, row, column);
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
		
		colourLabel = new JLabel("Farbe", JLabel.CENTER);
		colourPanel = new JPanel(new FlowLayout());
		whiteButton = new JRadioButton("weiß");
		blackButton = new JRadioButton("schwarz");
		colourGroup = new ButtonGroup();
		colourGroup.add(whiteButton);
		colourGroup.add(blackButton);
		colourPanel.add(whiteButton);
		colourPanel.add(blackButton);
		
		optionPanel.add(gameLabel);
		optionPanel.add(gamePanel);
		optionPanel.add(colourLabel);
		optionPanel.add(colourPanel);
		
		getContentPane().add(playPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		getContentPane().add(optionPanel, BorderLayout.EAST);
		pack();
	}
	
	protected Game getGame() {
		return game;
	}
	
	private void setGame(Game game) {
		this.game = game;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	private void setColour(Colour colour) {
		this.colour = colour;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void startRunning() {
		turnLabel.setText("Zug: \"schwarz\"");
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
		turn = true;
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
	
	public Field[][] getPlayfield() {
		return playfield;
	}
	
	private void initPlayfield() {
		Field field;
		boolean isWhite;
		
		if (game == null || colour == null) {
			System.out.println("Bitte erst alle Optionen auswählen.");
			return;
		}
		
		if (colour == Colour.WHITE) {
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
			mouseCickedOrPressed(click);
		}
		
		public void mouseEntered(MouseEvent enter) {
			
		}
		
		public void mouseExited(MouseEvent exit) {
			
		}
		
		public void mousePressed(MouseEvent press) {
			mouseCickedOrPressed(press);
		}
		
		public void mouseReleased(MouseEvent release) {
			
		}
		
		private void mouseCickedOrPressed(MouseEvent event) {
			Field field = null;
			
			if (event.getButton() == 1) {
				for(int row=0 ; row<playfield.length ; row++) {
					for(int column=0 ; column<playfield[row].length ; column++) {
						if (event.getSource() == playfield[row][column]) {
							field = playfield[row][column];
							System.out.println("Klick auf Feld (" + field.getPosRow() + "," + field.getPosColumn() + ") !!");
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
			} else if (event.getButton() == 3 || MouseInfo.getNumberOfButtons() == 2 && event.getButton() == 2) {
				if (isRunning() && !isMoveStart()) {
					token.getField().setToken(token);
					moveFinished();
				}
			}
		}
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource() == startButton) {
				if (draughtsButton.isSelected()) {
					setGame(Game.DRAUGHTS);
				} else if (chessButton.isSelected()) {
					setGame(Game.CHESS);
				}
				if (whiteButton.isSelected()) {
					setColour(Colour.WHITE);
				} else if (blackButton.isSelected()) {
					setColour(Colour.BLACK);
				}
				initPlayfield();
			} else if (ae.getSource() == resetButton) {
				finishPlayfield();
			}
		}
	}
}
