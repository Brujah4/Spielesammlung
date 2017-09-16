package backend;

import java.util.LinkedList;

import frontend.Field;

public abstract class Token {

	private Field field;
	private boolean isBlack;
	private LinkedList<Field> usualTargets;
	private LinkedList<Field> captureTargets;
	
	public Token(Field field, boolean black) {
		this.field = field;
		isBlack = black;
		usualTargets = new LinkedList<Field>();
		captureTargets = new LinkedList<Field>();
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
	
	public LinkedList<Field> getUsualTargets() {
		return usualTargets;
	}
	
	public void addUsualTarget(Field field) {
		usualTargets.add(field);
	}
	
	public void resetUsualTargets() {
		usualTargets.clear();
	}
	
	public LinkedList<Field> getCaptureTargets() {
		return captureTargets;
	}
	
	public void addCaptureTarget(Field field) {
		captureTargets.add(field);
	}
	
	public void resetCaptureTargets() {
		captureTargets.clear();
	}
}
