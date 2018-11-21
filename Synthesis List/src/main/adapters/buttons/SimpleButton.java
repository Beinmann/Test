package main.adapters.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class SimpleButton {
	
	protected int x, y, width, height;
	protected Color outerColor;
	protected boolean pressed = false;
	protected boolean active = true;
	
	public SimpleButton(int x, int y, int width, int height, Color outerColor) {
		
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		if(this.width < 0) {
			this.x += this.width;
			this.width *= -1;
		}
		if(this.height < 0) {
			this.y += this.height;
			this.height *= -1;
		}
		
		this.outerColor = outerColor;
	}
	
	public void render(Graphics g) {
		if(active) {
			g.setColor(outerColor);
			g.fillRect(x,y,width,height);
		}
	}
	
	public boolean pressed() {
		if(pressed && active) {
			pressed = false;
			return true;
		} else {
			return false;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(active) {
			if(mouseOver(e,x,y,width,height)) {
				pressed = true;
			}
		}
	}
	
	public boolean mouseOver(MouseEvent e, int x, int y, int width, int height) {
		int mx = e.getX();
		int my = e.getY();
		
		if((mx >= x && mx <= x + width) && (my >= y && my <= y + height)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void activate() {
		active = true;
	}
	
	public void deactivate() {
		active = false;
	}
	
	public boolean isActive() {
		return active;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	public void printButtonCode() {
		//public SimpleButton(int x, int y, int width, int height, Color outerColor)
		String buttonCode = "new SimpleButton(" + getArguments() + ",outerColor)";
		System.out.println(buttonCode);
	}
	public String getArguments() {
		String arguments = argumentList(x,y,width,height) + ",outerColor";
		return arguments;
	}
	public static String argumentList(Object... args) {
		if(args.length == 1) {
			return "";
		} else {
			String result = args[0] + "";
			for(int i = 1; i < args.length; i++) {
				result += "," + args[i];
			}
			return result;
		}
	}
	
}
