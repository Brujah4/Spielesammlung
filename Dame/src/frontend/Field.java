package frontend;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import backend.BasicToken;
import backend.Rules;
import backend.Token;

public class Field extends JButton {

	private static final long serialVersionUID = 1L;
	
	private Board board;
	private Token token = null;
	private boolean isBlack;
	private int posRow;
	private int posColumn;
	private static final Color LIGHT_BROWN = new Color(227, 197, 140);
	private static final Color DARK_BROWN = new Color(149, 95, 34);
	
	public Field(Board board, boolean black, int row, int column) {
		this.board = board;
		isBlack = black;
		setBackground(isBlack ? DARK_BROWN : LIGHT_BROWN);
		posRow = row;
		posColumn = column;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token inputToken) {
		if (inputToken == null) {
			System.out.println("FEHLER: Nullpointer Token gesetzt!");
			return;
		}
		
		token = inputToken;
		
		if (token instanceof BasicToken) {
			if (token.isBlack()) {
				ImageIcon icon = new ImageIcon(board.DRAUGHTSBLACK.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
				this.setIcon(icon);
			} else {
				ImageIcon icon = new ImageIcon(board.DRAUGHTSWHITE.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
				this.setIcon(icon);
			}
		}
	}
	
	public void removeToken() {
		reset();
		board.moveStarted();
	}
	
	public void setTokenOnValidField(Token inputToken) {
		switch (board.getGame()) {
			case DRAUGHTS:
				int value = Rules.validDraughts(this, inputToken);
				
				if (value >= 0 && value <= 1) {
					setToken(inputToken);
					token.setField(this);
					board.moveFinished();
					
					if (value == 0) {
						board.nextTurn();
					} else if (value == 1) {
						// ToDo:	Prüfe, ob weiter geschlagen werden kann,
						//			wenn nein, dann nextTurn(),
						//			wenn ja, dann MUSS weitergeschlagen werden.
						board.nextTurn();
					}
				}
				break;
			case CHESS:
				break;
			default:
				break;
		}
	}
	
	public boolean isBlack() {
		return isBlack;
	}
	
	public int getPosRow() {
		return posRow;
	}
	
	public int getPosColumn() {
		return posColumn;
	}
	
	public void reset() {
		token = null;
		setIcon(null);
	}
}
