package frontend;

import java.awt.Color;

import javax.swing.JButton;

import backend.Token;

public class Field extends JButton {

	private static final long serialVersionUID = 1L;
	
	private Board board;
	private Token token = null;
	private boolean isBlack;
	
	public Field(Board board, boolean black) {
		this.board = board;
		isBlack = black;
		setBackground(isBlack ? Color.DARK_GRAY : Color.LIGHT_GRAY);
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
}
