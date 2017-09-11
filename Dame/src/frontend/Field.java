package frontend;

import java.awt.Color;

import javax.swing.JButton;

import backend.Token;

public class Field extends JButton {

	private static final long serialVersionUID = 1L;
	
	private Board board;
	private Token token = null;
	private boolean isBlack;
	private static final Color LIGHT_BROWN = new Color(227, 197, 140);
	private static final Color DARK_BROWN = new Color(149, 95, 34);
	
	public Field(Board board, boolean black) {
		this.board = board;
		isBlack = black;
		setBackground(isBlack ? DARK_BROWN : LIGHT_BROWN);
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}
	
	public boolean isBlack() {
		return isBlack;
	}
}
