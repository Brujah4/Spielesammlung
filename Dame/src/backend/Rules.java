// ToDo
// button für initPlayfield()
// draughtsblack -> basicTokenBlack

package backend;

import frontend.Board;
import frontend.Field;

public class Rules {

	public static void main(String[] args) {
		
		Board board = new Board();
		board.setVisible(true);
	}
	
	public static boolean validDraughts(Field field, Token token) {
		if (field.getToken() == null && token.getField() != field) {
			return true;
		} else {
			return false;
		}
	}
}
