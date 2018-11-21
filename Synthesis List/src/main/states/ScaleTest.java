package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import main.Synthesis;
import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.adapters.buttons.BackButton;

public class ScaleTest extends State {
	
	private boolean firstPress = false, draw = false, delete = false;
	private int x, y, width, height;
	private StateID lastState;
	private boolean goBack = false;
	
	
	public ScaleTest(KeyInput keyInput, MouseInput mouseInput, Synthesis syn, StateID lastState) {
		super(StateID.Test,keyInput,mouseInput,syn);
		this.lastState = lastState;
		delete = true;
	}

	@Override
	public void tick() {
		if(goBack) {
			changeState(lastState);
			delete();
		}
	}

	@Override
	public void render(Graphics g) {
		if(delete) {
			//drawBackground(g);
		}
	
		g.setColor(Color.blue);
		if(draw)
			g.fillRect(x,y,width,height);
		renderButtons(g);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
			if(!firstPress) {
				draw = false;
				
				x = e.getX();
				y = e.getY();
				
				firstPress = true;
				
			} else {
				width = e.getX() -x;
				height = e.getY() - y;
				
				if(width < 0) {
					width *= -1;
					x -= width;
				}
				if(height < 0) {
					height *= -1;
					y -= height;
				}
				
				System.out.println("(" + x + "," + y + ")"  +  "   width: " + width + "   height: " + height);
				System.out.println();
				firstPress = false;
				draw = true;
			}
			if(e.getButton() == 1) {
				delete = true;
			} else {
				delete = false;
			}
	}
	
	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		int key = e.getKeyCode();
		if(key == java.awt.event.KeyEvent.VK_ESCAPE) {
			goBack = true;
		}
	}

}
