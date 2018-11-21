package main.adapters.buttons;

import java.awt.Color;

public class BackButton extends MyButton {

	public BackButton() {
		super(20, 20, 45, 45, 4, 50, Color.gray, Color.white, "<", 7, 39);
	}
	public BackButton(int x, int y) {
		super(x, y, 45, 45, 4, 50, Color.gray, Color.white, "<", 7, 39);
	}
	
}
