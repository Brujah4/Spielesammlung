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
	
	public void setToken(Token inputToken, boolean flagged) {
		if (inputToken == null) {
			System.out.println("FEHLER: Nullpointer Token gesetzt!");
			return;
		}
		
		token = inputToken;
		flag(flagged);
	}
	
	public void removeTokenFlagged() {
		flag(true);
		token = null;
		board.moveStarted();
	}
	
	public void removeToken() {
		token = null;
		setIcon(null);
		board.moveStarted();
	}
	
	// überarbeiten !!
	public void setTokenOnValidField(Token inputToken) {
		switch (board.getGame()) {
			case DRAUGHTS:
				int value = Rules.validDraughts(this, inputToken);
				
				if (value >= 0 && value <= 1) {
					inputToken.getField().setIcon(null);
					board.moveFinished();
					
					if (value == 0) {
						setToken(inputToken, false);
						token.setField(this);
						board.nextTurn();
					} else if (value == 1) {
						int help = inputToken.getField().getPosRow();
						int delRow;
						int delColumn;
						
						if (posRow > help) {
							delRow = posRow - 1;
						} else {
							delRow = posRow + 1;
						}
						help = inputToken.getField().getPosColumn();
						if (posColumn > help) {
							delColumn = posColumn - 1;
						} else {
							delColumn = posColumn + 1;
						}
						
						board.getPlayfield()[delRow][delColumn].removeToken();
						board.moveFinished();
						setToken(inputToken, false);
						token.setField(this);
						Rules.setTargetFields(token);
						
						if (token.getCaptureTargets().isEmpty()) {
							board.nextTurn();
						} else {
							token.resetUsualTargets();
							removeTokenFlagged();
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
	
	public boolean isBlack() {
		return isBlack;
	}
	
	public int getPosRow() {
		return posRow;
	}
	
	public int getPosColumn() {
		return posColumn;
	}
	
	private void flag(boolean isFlagged) {
		if (token instanceof BasicToken) {
			ImageIcon icon;
			
			if (isFlagged) {
				if (token.isBlack()) {
					icon = new ImageIcon(board.DRAUGHTSBLACKFLAGGED.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
				} else {
					icon = new ImageIcon(board.DRAUGHTSWHITEFLAGGED.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
				}
			} else {
				if (token.isBlack()) {
					icon = new ImageIcon(board.DRAUGHTSBLACK.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
				} else {
					icon = new ImageIcon(board.DRAUGHTSWHITE.getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST));
				}
			}
			setIcon(icon);
		} else {
			setIcon(null);
		}
	}
	
	public void reset() {
		token = null;
		setIcon(null);
	}
}
