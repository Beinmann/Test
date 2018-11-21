package main.adapters.buttons;

import java.awt.Color;


public class FwdButton extends MyButton{

	public FwdButton() {
		super(1210, 20, 45, 45, 4, 50, Color.gray, Color.white, ">", 7, 39);
	}
	public FwdButton(int x, int y) {
		super(x, y, 45, 45, 4, 50, Color.gray, Color.white, ">", 7, 39);
	}
}
