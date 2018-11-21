package main.adapters;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import main.adapters.buttons.SimpleButton;
import main.adapters.buttons.TextField;

public abstract class Extension {

	protected boolean active = true;
	protected LinkedList<TextField> textFields = new LinkedList<TextField>();
	protected LinkedList<SimpleButton> buttons = new LinkedList<SimpleButton>();
	
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void tick() {}
	public void render(Graphics g) {}
	
	public void activate() { active = true; }
	public void deactivate() { active = false; }
	public LinkedList<TextField> getTextFields() {
		return textFields;
	}
	public void addButtons(SimpleButton... buttons) {
		for(int i = 0; i < buttons.length; i++) {
			this.buttons.add(buttons[i]);
		}
	}
	public void addTextFields(TextField... fields) {
		for(int i = 0; i < fields.length; i++) {
			textFields.add(fields[i]);
		}
	}
}
