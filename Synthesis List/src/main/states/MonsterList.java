package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import main.S;
import main.Synthesis;
import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.adapters.buttons.FwdButton;
import main.adapters.buttons.MyButton;
import main.adapters.buttons.SimpleButton;
import main.adapters.buttons.TextField;
import main.monsterList.Monster;

public class MonsterList extends State {

	private LinkedList<Monster> monsters;
	private int firstElement = 0;
	private String[] monsterNames = new String[10];
	private MyButton[] listButtons = new MyButton[10];
	private int numberOfEntries;
	private boolean lookingAtMonster = false, addingMonster = false, changesMade = false, somethingInSearchField = false;
	
	private MyButton up = new MyButton(900,60,80,45,4,30,Color.gray,Color.white,"up",22,31);
	private MyButton down = new MyButton(900,560,80,45,4,25,Color.gray,Color.white,"down",8,32);
	private MyButton save = new MyButton(60,600,120,40,4,30,Color.gray,Color.white,"save",18,28);
	private MyButton remove = new MyButton(240,375,140,40,4,36,Color.gray,Color.white,"remove",6,31);
	private MyButton searchButton = new MyButton(1040,15,220,75,4,40,Color.gray,Color.white,"Search...",24,52);
	private MyButton back= new MyButton(1120,630,140,45,4,40,Color.gray,Color.white,"back",24,35);
	
	private MyButton plusButton = new MyButton(100,100,200,200,10,300,Color.gray,Color.white,"+",13,205);
	private MyButton add = new MyButton(80,375,140,40,4,38,Color.gray,Color.white,"add",36,32);
	private MyButton change = new MyButton(80,375,140,40,4,34,Color.gray,Color.white,"change",11,31);
	private TextField name = new TextField(80,75,300,80,7,34,Color.gray,Color.white,"Name: ","",11,46,20);
	private TextField type = new TextField(80,175,300,80,7,34,Color.gray,Color.white,"Type: ","",11,46,20);
	private TextField rank = new TextField(80,275,300,80,7,34,Color.gray,Color.white,"Rank: ","",11,46,20);
	private TextField searchField = new TextField(1040,15,220,75,4,40,Color.gray,Color.white,"","",24,52,15);
	private int lookingAt = 0;
	private boolean keyPressed[] = new boolean[2];
	
	
	public MonsterList(KeyInput keyInput, MouseInput mouseInput, Synthesis syn) {
		super(StateID.List, keyInput, mouseInput, syn);
		addButtons(plusButton,save,add,change,remove,searchButton,back);
		textFields.add(name);
		textFields.add(rank);
		textFields.add(type);
		textFields.add(searchField);
		monsters = syn.getMonsters();
		

		

		listButtons[0] = new MyButton(480,15,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[1] = new MyButton(480,80,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[2] = new MyButton(480,145,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[3] = new MyButton(480,210,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[4] = new MyButton(480,275,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[5] = new MyButton(480,340,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[6] = new MyButton(480,405,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[7] = new MyButton(480,470,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[8] = new MyButton(480,535,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);
		listButtons[9] = new MyButton(480,600,400,55,10,30,Color.gray, Color.white, "Hello World",20,38);

		buttons.add(up);
		buttons.add(down);
	}

	@Override
	public void tick() {
		if(back.pressed()) {
			changeState(StateID.Menu);
		}
		
		somethingInSearchField = searchField.getText().length() > 0;
		
		LinkedList<Monster> tempMonsters;
		if(somethingInSearchField) {
			tempMonsters = search(searchField.getText());
		} else {
			tempMonsters = monsters;
		}
		
		
		if(keyPressed[1]) {
			changeState(new ScaleTest(keyInput, mouseInput, syn, id));
			keyPressed[1] = false;
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
			Monster tempMonster = tempMonsters.get(i+firstElement);
			//listButtons[i].setOuterColor(S.getRankColor(tempMonster));
			listButtons[i].setInnerColor(S.getTypeColor(tempMonster));
		}
		
		
		
		if(plusButton.pressed()) {
			plusButton.deactivate();
			addingMonster = true;
			name.setText("");
			type.setText("");
			rank.setText("");
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
				name.setText(cur.getName());
				type.setText(cur.getType());
				rank.setText(cur.getRank());
				
				plusButton.deactivate();
				changesMade = false;
			}
		}
		
		if(lookingAtMonster || addingMonster) {

			name.activate();
			rank.activate();
			type.activate();
			
			if(lookingAtMonster) {
				remove.activate();
				if(changesMade) {
					change.activate();
					if(change.pressed()) {
						if(textFieldsFilledIn()) {
							Monster cur = tempMonsters.get(lookingAt);
							cur.setName(name.getText());
							cur.setType(type.getText());
							cur.setRank(rank.getText());
		
							backToList();
						}
					}
				}
			}
			
			if(addingMonster) {
				add.activate();
				if(add.pressed()) {
					if(textFieldsFilledIn()) {
						Monster temp = new Monster(name.getText(),type.getText(),rank.getText());
						tempMonsters.add(temp);
		
						name.setText("");
						type.setText("");
						rank.setText("");
						
						backToList();
					}
				}
			}
		} else {
			name.deactivate();
			type.deactivate();
			rank.deactivate();
			add.deactivate();
			change.deactivate();
			remove.deactivate();
		}
		
		if(save.pressed()) {
			syn.save();
		}
		
		if(keyPressed[0] == true) {
			backToList();
			keyPressed[0] = false;
		}
		
		if(remove.pressed()) {
			Monster cur = tempMonsters.get(lookingAt);
			monsters.remove(cur);
			backToList();
		}
	}
	
	public boolean textFieldsFilledIn() {
		return name.notEmpty() && rank.notEmpty() && type.notEmpty();
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

	@Override
	public void render(Graphics g) {
		drawBackground(g);
		for(TextField cur : textFields) {
			cur.render(g);
		}
		for(SimpleButton cur : buttons) {
			cur.render(g);
		}
		for(int i = 0; i < numberOfEntries; i++) {
			listButtons[i].render(g);
		}
		
		//MyButton test = new MyButton(1120,630,140,45,4,40,Color.gray,Color.white,"back",24,35);
		//test.render(g);
		
	}
	
	public void backToList() {
		lookingAtMonster = false;
		addingMonster = false;
		changesMade = false;
		plusButton.activate();
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
			changesMade = true;
			for(TextField cur : textFields) {
				cur.keyPressed(e);
			}
			int key = e.getKeyCode();
			if(key == java.awt.event.KeyEvent.VK_ESCAPE) {
				keyPressed[0] = true;
			} else if(key == java.awt.event.KeyEvent.VK_TAB) {
				keyPressed[1] = true;
			}
		}
	}

}
