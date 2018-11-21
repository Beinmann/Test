package main.adapters.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class TextField extends MyButton {

	private boolean currentlyWriting = false;
	private int maxLength;
	private String prefix;
	private boolean suffix = false;
	private boolean shift = false;
	private long tStart = System.currentTimeMillis();
	
	
	public TextField(int x, int y, int width, int height, int edge, int fontSize, Color outerColor, Color innerColor, String prefix, String text, int xOffset, int yOffset, int maxLength) {
		super(x, y, width, height, edge, fontSize, outerColor, innerColor, text, xOffset, yOffset);
		this.maxLength = maxLength;
		this.prefix = prefix;
	}
	
	@Override
	public void render(Graphics g) {
		if(active) {
			if(currentlyWriting) {
				long tNow = System.currentTimeMillis();
				if(tNow - tStart > 350) {
					suffix ^= true;
					tStart = tNow;
				}
			}
			g.setColor(outerColor);
			g.fillRect(x,y,width,height);
			
			g.setColor(innerColor);
			g.fillRect(x+edge,y+edge,width-2*edge,height-2*edge);
			
			g.setColor(Color.darkGray);
			g.setFont(fnt);
			String tempText = prefix + text;
			if(currentlyWriting) {
				if(suffix) {
					if(text.length() < maxLength) {
						tempText += "|";
					}
				}
			}
			g.drawString(tempText,x+xOffset,y+yOffset);
		}
	}
	
		
	@Override public void mousePressed(MouseEvent e) {
		if(active) {
			if(javax.swing.SwingUtilities.isLeftMouseButton(e)) {
				if(mouseOver(e,x,y,width,height)) {
					currentlyWriting = true;
				} else {
					currentlyWriting = false;
				}
			}
		}
	}
	
	public boolean currentlyWriting() {
		return currentlyWriting;
	}
	
	
	@Override public boolean pressed() {
		if(pressed) {
			pressed = false;
			return true;
		}
		return false;
	}
	
	public void setCurrentlyWriting(boolean currentlyWriting) {
		this.currentlyWriting = currentlyWriting;
	}
	
	public void resetShift() {
		shift = false;
	}
	
	public boolean notEmpty() {
		return text.length() > 0;
	}
	
	public void keyPressed(KeyEvent e) {
		if(active) {
			if(currentlyWriting) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_BACK_SPACE) {
					if(text.length() > 0) {
						text = text.substring(0,text.length()-1);
					}
				} else if(key == KeyEvent.VK_SHIFT) {
					shift = true;
				} else if (key == KeyEvent.VK_ENTER) {
					currentlyWriting = false;
					pressed = true;
				} else if(key == KeyEvent.VK_ESCAPE) {
					text = "";
					currentlyWriting = false;
				} else {
					if(text.length() < maxLength) {
						char keyPressed = e.getKeyChar();
						if(shift) {
							text += Character.toUpperCase(keyPressed);
						} else {
							text += keyPressed;
						}
					}
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(active) {
			if(currentlyWriting) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_SHIFT) {
					shift = false;
				}
			}
		}
	}

}
