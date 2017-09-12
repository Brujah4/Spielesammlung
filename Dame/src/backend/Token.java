package backend;

import frontend.Field;

public abstract class Token {

	private Field field;
	private boolean isBlack;
	
	public Token(Field field, boolean black) {
		this.field = field;
		isBlack = black;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field inputField) {
		field = inputField;
	}
	
	public boolean isBlack() {
		return isBlack;
	}
}
