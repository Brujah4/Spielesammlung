// ToDo
// button für initPlayfield()
// draughtsblack -> basicTokenBlack

package backend;

import frontend.Board;
import frontend.Board.Colour;
import frontend.Field;

public class Rules {

	public static void main(String[] args) {
		
		Board board = new Board();
		board.setVisible(true);
	}
	
	// return: -1 = error ; 0 = valid ; 1 = invalid
	public static int validDraughts(Field field, Token token) {
		int output = -1;
		Colour colour = field.getBoard().getColour();
		boolean whiteDown;
		
		switch (colour) {
			case WHITE:
				whiteDown = true;
				break;
			case BLACK:
				whiteDown = false;
				break;
			default:
				return output;
		}
		
		if (field.getToken() == null && token.getField() != field) {
			Field tokenField = token.getField();
			
			if (validUsualMove(field, tokenField, whiteDown, token.isBlack())) {
				output = 0;
			} else { // schlagen kommt hier rein.
				output = 1;
			}
		} else {
			output = 1;
		}
		return output;
	}
	
	private static boolean validUsualMove(Field field, Field tokenField, boolean whiteDown, boolean isBlack) {
		int value = 0;
		
		if (whiteDown) {
			if (isBlack) {
				value = 1;
			} else {
				value = -1;
			}
		} else {
			if (isBlack) {
				value = 1;
			} else {
				value = -1;
			}
		}
		
		if (
				tokenField.getPosRow() + value == field.getPosRow() &&
				(tokenField.getPosColumn() + value == field.getPosColumn() ||
				tokenField.getPosColumn() - value == field.getPosColumn())) {
			return true;
		} else {
			return false;
		}
	}
}
