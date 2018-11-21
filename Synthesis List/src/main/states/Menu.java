package main.states;

import java.awt.Color;
import java.awt.Graphics;

import main.Synthesis;
import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.adapters.buttons.BackButton;
import main.adapters.buttons.FwdButton;
import main.adapters.buttons.MyButton;
import main.adapters.buttons.TextField;
import main.monsterList.Monster;
import main.monsterList.Recipe;
import main.visual.extensions.MonsterListOverlay;

public class Menu extends State {

	private BackButton back = new BackButton();
	private FwdButton fwd = new FwdButton();
	private MyButton button1 = new MyButton(100,100,200,200,10,80,Color.gray, Color.white, "Hello World",0,0);
	private MyButton button2 = new MyButton(400,100,200,200,10,80,Color.gray, Color.white, "Hello World",0,0);
	
	private TextField testTextField = new TextField(400,400,200,200,10,80,Color.gray, Color.white, "A:", "Hello World",0,0,10);
	
	public Menu(KeyInput keyInput, MouseInput mouseInput, Synthesis syn) {
		super(StateID.Menu, keyInput, mouseInput, syn);
		
		buttons.add(back);
		buttons.add(fwd);
		buttons.add(button1);
		buttons.add(button2);
		
		textFields.add(testTextField);
	}
	@Override
	public void tick() {
		if(back.pressed()) {
			System.out.println("Monsters " + syn.getMonsters().size());
			for(Monster cur : syn.getMonsters()) {
				System.out.println("Name: " + cur.getName() + "   Rank: " + cur.getRank() + "   Type: " + cur.getType());
			}
			System.out.println();
		}
		if(fwd.pressed()) {
			System.out.println("Recipes " + syn.getRecipes().size());
			for(Recipe cur : syn.getRecipes()) {
				System.out.println(cur.getFst().getName() + " + " + cur.getSnd().getName() + " -> " + cur.getRes().getName());
			}
			System.out.println();
			fwd.printButtonCode();
		}
		if(button1.pressed()) {
			changeState(StateID.List);
		}
		if(button2.pressed()) {
			changeState(StateID.SynthesisTree);
		}
	}

	@Override
	public void render(Graphics g) {
		drawBackground(g);
		g.setColor(Color.gray);
		renderButtons(g);
		testTextField.render(g);
	}

}
