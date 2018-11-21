package main.visual.extensions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import main.S;
import main.adapters.Extension;
import main.adapters.buttons.MyButton;
import main.adapters.buttons.SimpleButton;
import main.adapters.buttons.TextField;
import main.monsterList.Monster;

public class MonsterListOverlay extends Extension{

	private LinkedList<Monster> monsters;
	private LinkedList<SimpleButton> buttons = new LinkedList<SimpleButton>();
	private LinkedList<TextField> textFields = new LinkedList<TextField>();
	private int firstElement = 0;
	private String[] monsterNames = new String[10];
	private MyButton[] listButtons = new MyButton[10];
	private int numberOfEntries;
	private boolean lookingAtMonster = false, somethingInSearchField = false;
	private int x, y, width = 670, height = 670;
	private double scale;
	private LinkedList<Monster> tempMonsters;
	
	MyButton up, down, searchButton;
	
	private TextField searchField;
	private int lookingAt = 0;
	
	
	public MonsterListOverlay(int x, int y, double scale, LinkedList<Monster> monsters) {
		this.monsters = monsters;
		tempMonsters = monsters;
		this.x = x;
		this.y = y;
		this.scale = scale;
		

		searchField = new TextField((int)(x+(470*scale)),y + (int)(220*scale),(int)(140*scale),(int)(40*scale),(int)(4*scale),(int)(20*scale),Color.gray,Color.white,"","",(int)(30*scale),(int)(27*scale),15);
		up = new MyButton((int)(x+(500*scale)),y + (int)(300*scale),(int)(80*scale),(int)(45*scale),(int)(4*scale),(int)(30*scale),Color.gray,Color.white,"up",(int)(22*scale),(int)(31*scale));
		down = new MyButton((int)(x+(500*scale)),y + (int)(360*scale),(int)(80*scale),(int)(45*scale),(int)(4*scale),(int)(25*scale),Color.gray,Color.white,"down",(int)(8*scale),(int)(32*scale));
		searchButton = new MyButton((int)(x+(470*scale)),y + (int)(220*scale),(int)(140*scale),(int)(40*scale),(int)(4*scale),(int)(20*scale),Color.gray,Color.white,"Search...",(int)(30*scale),(int)(27*scale));

		addButtons(up, down, searchButton);
		textFields.add(searchField);

		int listButtonEdge = (int)(10 * scale);
		int listButtonWidth = (int)(400 * scale);
		int listButtonHeight = (int)(55 * scale);
		int listButtonFontSize = (int)(30 * scale);
		int listButtonXOffset = (int)(20 * scale);
		int listButtonYOffset = (int)(38 * scale);
		for(int i = 0; i < 10; i++) {
			listButtons[i] = new MyButton((int)(x + (30*scale)),(int)(y + ((15 + 65*i) * scale)),listButtonWidth,listButtonHeight,
					listButtonEdge,listButtonFontSize,Color.gray, Color.white, "Hello World",listButtonXOffset,listButtonYOffset);
		}
		/*
		listButtons[0] = new MyButton((int)(450 + (30*scale)),(int)(y + (15 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[1] = new MyButton((int)(450 + (30*scale)),(int)(y + (80 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[2] = new MyButton((int)(450 + (30*scale)),(int)(y + (145 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[3] = new MyButton((int)(450 + (30*scale)),(int)(y + (210 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[4] = new MyButton((int)(450 + (30*scale)),(int)(y + (275 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[5] = new MyButton((int)(450 + (30*scale)),(int)(y + (340 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[6] = new MyButton((int)(450 + (30*scale)),(int)(y + (405 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[7] = new MyButton((int)(450 + (30*scale)),(int)(y + (470 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[8] = new MyButton((int)(450 + (30*scale)),(int)(y + (535 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[9] = new MyButton((int)(450 + (30*scale)),(int)(y + (535 * scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray, Color.white, "Hello World",20,38);
		*/
	}

	public void tick() {
		if(active) {
			somethingInSearchField = searchField.getText().length() > 0;
			
			if(somethingInSearchField) {
				tempMonsters = search(searchField.getText());
			} else {
				tempMonsters = monsters;
			}
			
			
			if(down.pressed()) {
				firstElement++;
			}
			if(up.pressed()) {
				firstElement--;
			}
			firstElement = Math.max(S.clamp(firstElement,0,tempMonsters.size()-1),0);
			numberOfEntries = Math.min(tempMonsters.size() - firstElement, 10);
			
			
			for(int i = 0; i < numberOfEntries; i++) {
				monsterNames[i] = tempMonsters.get(i+firstElement).getName();
				listButtons[i].setText(monsterNames[i]);
			}
			
			
			
			
			if(searchField.currentlyWriting() || somethingInSearchField) {
				searchButton.deactivate();
			} else {
				searchButton.activate();
			}
	
			
			
			for(int i = 0; i < numberOfEntries; i++) {
				if(listButtons[i].pressed()) {
					lookingAtMonster = true;
					lookingAt = i + firstElement;
	
					Monster cur = tempMonsters.get(lookingAt);
				}
			}
		}
		
	}
	
	public boolean hasMonster() {
		return lookingAtMonster;
	}
	
	public Monster getMonster() {
		reset();
		return tempMonsters.get(lookingAt);
	}
	
	public void reset() {
		lookingAtMonster = false;
	}
	
	public LinkedList<Monster> search(String name) {
		LinkedList<Monster> temp = new LinkedList<Monster>();
		for(Monster cur : monsters) {
			String curName = cur.getName();
			if(curName.toUpperCase().contains(name.toUpperCase())) {
				temp.add(cur);
			}
		}
		return temp;
	}
	public void render(Graphics g) {
		if(active) {
			g.setColor(Color.lightGray);
			g.fillRect(x,y,(int)(width*scale),(int)(height*scale));
			for(TextField cur : textFields) {
				cur.render(g);
			}
			for(SimpleButton cur : buttons) {
				cur.render(g);
			}
			for(int i = 0; i < numberOfEntries; i++) {
				listButtons[i].render(g);
			}

		}
		//MyButton test = new MyButton(1120,630,140,45,4,40,Color.gray,Color.white,"back",24,35);
		//test.render(g);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(active) {
			if(SwingUtilities.isMiddleMouseButton(e)) {
				
			} else if(SwingUtilities.isLeftMouseButton(e)) {
				for(SimpleButton cur : buttons) {
					cur.mousePressed(e);
				}
				for(TextField cur : textFields) {
					cur.mousePressed(e);
				}
				for(int i = 0; i < numberOfEntries;i++) {
					listButtons[i].mousePressed(e);
				}
			}
		}
	}
	
	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		if(active) {
			for(TextField cur : textFields) {
				cur.keyPressed(e);
			}
		}
	}
	
	public void addButtons(main.adapters.buttons.SimpleButton... buttons) {
		for(int i = 0; i < buttons.length; i++) {
			this.buttons.add(buttons[i]);
		}
	}

}
