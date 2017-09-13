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
	
	// return: -2 = error ; -1 = invalid ; 0 = usualMove ; 1 = captureMove
	public static int validDraughts(Field field, Token token) {
		if (field.getToken() == null && token.getField() != field) {
			Field tokenField = token.getField();
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
					return -2;
			}
			
			if (validUsualMove(field, tokenField, whiteDown, token.isBlack())) {
				return 0;
			} else if (validCaptureMove(field, token, whiteDown, token.isBlack())) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	private static boolean validUsualMove(Field field, Field tokenField, boolean whiteDown, boolean isBlack) {
		int value = 0;
		
		if (whiteDown == isBlack) {
			value = 1;
		} else {
			value = -1;
		}
		
		value = checkValue(field, tokenField, value);
		
		if (value == 0 || value == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean validCaptureMove(Field field, Token token, boolean whiteDown, boolean isBlack) {
		int value = 0;
		int valueCap = 0;
		
		if (whiteDown == isBlack) {
			value = 2;
			valueCap = 1;
		} else {
			value = -2;
			valueCap = -1;
		}
		
		value = checkValue(field, token.getField(), value);
		return checkCaptured(token, valueCap, value);
	}
	
	// return: -1 = invalid ; 0 = valid (one side) ; 1 = valid (other side)
	private static int checkValue(Field field, Field tokenField, int value) {
		if (tokenField.getPosRow() + value == field.getPosRow()) {
			if (tokenField.getPosColumn() + value == field.getPosColumn()) {
				return 0;
			} else if (tokenField.getPosColumn() - value == field.getPosColumn()) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	private static boolean checkCaptured(Token token, int valueCap, int value) {
		int posRow = token.getField().getPosRow() + valueCap;
		int posColumn0 = token.getField().getPosColumn() + valueCap;
		int posColumn1 = token.getField().getPosColumn() - valueCap;
		Field field0 = null;
		Field field1 = null;
		
		try {
			field0 = token.getField().getBoard().getPlayfield()[posRow][posColumn0];
			field1 = token.getField().getBoard().getPlayfield()[posRow][posColumn1];
		} catch (Exception e) {
			
		}
		
		if (field0 != null && value == 0 && field0.getToken() != null && token.isBlack() != field0.getToken().isBlack()) {
			field0.reset();
			return true;
		} else if (field1 != null && value == 1 && field1.getToken() != null && token.isBlack() != field1.getToken().isBlack()) {
			field1.reset();
			return true;
		} else {
			return false;
		}
	}
}
