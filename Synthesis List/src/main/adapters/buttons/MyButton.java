package main.adapters.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MyButton extends SimpleButton {

	protected Color innerColor;
	protected int xOffset;
	protected int yOffset;
	protected int edge;
	protected String text;
	protected Font fnt;
	
	public MyButton(int x, int y, int width, int height, int edge, int fontSize, Color outerColor, Color innerColor, String text, int xOffset, int yOffset) {
		super(x,y,width,height,outerColor);
		this.innerColor = innerColor;
		this.edge = edge;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.text = text;
		fnt = new Font("Arial",1,fontSize);
	}
	
	@Override
	public void render(Graphics g) {
		if(active) {
			g.setColor(outerColor);
			g.fillRect(x,y,width,height);
			
			g.setColor(innerColor);
			g.fillRect(x+edge,y+edge,width-2*edge,height-2*edge);
			
			g.setColor(Color.black);
			g.setFont(fnt);
			g.drawString(text,x+xOffset,y+yOffset);
		}
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getEdge() {
		return edge;
	}

	public void setEdge(int edge) {
		this.edge = edge;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setFontSize(int fontSize) {
		fnt = new Font("Arial",1,fontSize);
	}
	@Override
	public void printButtonCode() {
		//MyButton(int x, int y, int width, int height, int edge, int fontSize, Color outerColor, Color innerColor, String text, int xOffset, int yOffset)
		
		String buttonCode = "new MyButton(" + getArguments() + ")";
		System.out.println(buttonCode);
	}
	@Override
	public String getArguments() {
		String arguments = argumentList(x,y,width,height,edge,fnt.getSize()) + ",Color.gray,Color.white,\"" + text + "\"," + argumentList(xOffset,yOffset);
		return arguments;
	}
	
	public void setOuterColor(Color color) {
		outerColor = color;
	}
	
	public void setInnerColor(Color color) {
		innerColor = color;
	}
}













