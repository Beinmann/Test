package main.states;

import java.awt.Color;
import java.awt.Graphics;

import main.Synthesis;
import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.adapters.buttons.BackButton;
import main.monsterList.Monster;
import main.visual.MonsterNode;
import main.visual.extensions.MonsterListOverlayWithAdd;
import main.visual.extensions.SynthesisListOverlayWithAdd;

public class TestState extends State {

	private SynthesisListOverlayWithAdd synthesisOverlay;
	private MonsterListOverlayWithAdd overlay;
	
	private Synthesis syn;
	
	private BackButton back = new BackButton();
	
	private MonsterNode node;
	
	
	
	public TestState(KeyInput keyInput, MouseInput mouseInput, Synthesis syn) {
		super(StateID.Test, keyInput, mouseInput, syn);
		this.syn = syn;
		
		
		synthesisOverlay = new SynthesisListOverlayWithAdd(920,20,1,syn.getMonsters(),syn.getRecipes());
		overlay = new MonsterListOverlayWithAdd(0,360,0.5,syn.getMonsters());
		Monster temp = syn.getMonsters().getFirst();
		extensions.add(overlay);
		extensions.add(synthesisOverlay);
		addButtons(back);
		//node = new MonsterNode(100,100,syn.getMonsters().getFirst());
	}

	@Override
	public void tick() {
		overlay.tick();
		synthesisOverlay.tick();
		if(overlay.hasMonster()) {
			synthesisOverlay.setMonster(overlay.getMonster());
		}
		if(back.pressed()) {
			changeState(StateID.Menu);
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		drawBackground(g);
		renderAll(g);
		node.render(g);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void drawBackground(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0,0,Synthesis.WIDTH,Synthesis.WIDTH);
	}

}
