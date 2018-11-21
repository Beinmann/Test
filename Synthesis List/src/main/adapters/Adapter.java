package main.adapters;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Adapter {

	protected KeyInput keyInput;
	protected MouseInput mouseInput;
	
	public Adapter(KeyInput keyInput, MouseInput mouseInput) {
		keyInput.add(this);
		mouseInput.add(this);
		
		this.keyInput = keyInput;
		this.mouseInput = mouseInput;
	}
	
	
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
