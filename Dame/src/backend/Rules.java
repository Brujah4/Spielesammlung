// ToDo
// button für initPlayfield()
// draughtsblack -> basicTokenBlack

package backend;

import java.util.LinkedList;

import frontend.Board;
import frontend.Field;

public class Rules {

	public static void main(String[] args) {
		
		Board board = new Board();
		board.setVisible(true);
	}
	
	// return: -1 = invalid ; 0 = usualMove ; 1 = captureMove
	public static int validDraughts(Field field, Token token) {
		if (field.getToken() == null && token.getField() != field) {
			LinkedList<Field> usualTargets = token.getUsualTargets();
			LinkedList<Field> captureTargets = token.getCaptureTargets();
			
			if (captureTargets.isEmpty()) {
				for(int i=0 ; i<usualTargets.size() ; i++) {
					if (field == usualTargets.get(i)) {
						return 0;
					}
				}
			}
			for(int i=0 ; i<captureTargets.size() ; i++) {
				if (field == captureTargets.get(i)) {
					return 1;
				}
			}
			return -1;
		} else {
			return -1;
		}
	}
	
	public static void setTargetFields(Token token) {
		if (token == null) {
			return;
		}
		
		Field[][] playfield = token.getField().getBoard().getPlayfield();
		int row = token.getField().getPosRow();
		int column = token.getField().getPosColumn();
		int value = 0;
		
		if (token.getField().getBoard().isWhiteDown() == token.isBlack()) {
			value = 1;
		} else {
			value = -1;
		}
		
		token.resetUsualTargets();
		token.resetCaptureTargets();
		
		try {
			if (playfield[row+value][column+value].getToken() == null) {
				token.addUsualTarget(playfield[row+value][column+value]);
			} else if (playfield[row+value][column+value].getToken().isBlack() != token.isBlack()
					&& playfield[row+2*value][column+2*value].getToken() == null) {
				token.addCaptureTarget(playfield[row+2*value][column+2*value]);
			}
		} catch (Exception e) {
			
		}
		
		try {
			if (playfield[row+value][column-value].getToken() == null) {
				token.addUsualTarget(playfield[row+value][column-value]);
			} else if (playfield[row+value][column-value].getToken().isBlack() != token.isBlack()
					&& playfield[row+2*value][column-2*value].getToken() == null) {
				token.addCaptureTarget(playfield[row+2*value][column-2*value]);
			}
		} catch (Exception e) {
			
		}
	}
	
	public static void setAllTargetFields(Board board) {
		Field[][] playfield = board.getPlayfield();
		
		for(int row=0 ; row<playfield.length ; row++) {
			for(int column=0 ; column<playfield[row].length ; column++) {
				setTargetFields(playfield[row][column].getToken());
			}
		}
	}
	
	public static void resetAllTargetFields(Board board) {
		Field[][] playfield = board.getPlayfield();
		
		for(int row=0 ; row<playfield.length ; row++) {
			for(int column=0 ; column<playfield[row].length ; column++) {
				if (playfield[row][column].getToken() != null) {
					playfield[row][column].getToken().resetUsualTargets();
					playfield[row][column].getToken().resetCaptureTargets();
				}
			}
		}
	}
}
