package main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import main.Synthesis;
import main.adapters.Extension;
import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.adapters.buttons.BackButton;
import main.adapters.buttons.MyButton;
import main.adapters.buttons.SimpleButton;
import main.adapters.buttons.TextField;
import main.monsterList.Monster;
import main.visual.Camera;
import main.visual.MonsterNode;
import main.visual.extensions.MonsterListOverlayWithAdd;
import main.visual.extensions.SynthesisListOverlayWithAdd;

public class SynthesisTree extends State {

        private SynthesisListOverlayWithAdd synthesisOverlay;
        private MonsterListOverlayWithAdd overlay;
        private LinkedList<MonsterNode> lastNodes = new LinkedList<MonsterNode>();

        private Synthesis syn;
        private MonsterNode node, curNode = null;
        private BackButton back = new BackButton();
        private MyButton start, reset, makeTop, autoCreateTree, save;
        private Camera camera = new Camera();
        private LinkedList<Boolean> wasLeft = new LinkedList<Boolean>();

        private boolean[] keyDown = new boolean[4];
        //private boolean wasLeft = false;
        int x = 580, y = 550;

        public SynthesisTree(KeyInput keyInput, MouseInput mouseInput, Synthesis syn) {
                super(StateID.SynthesisTree, keyInput, mouseInput, syn);
                this.syn = syn;
                synthesisOverlay = new
SynthesisListOverlayWithAdd(920,20,1,syn.getMonsters(),syn.getRecipes());
                overlay = new MonsterListOverlayWithAdd(0,360,0.5,syn.getMonsters());
                overlay.deactivate();
                Monster temp = syn.getMonsters().getFirst();
                extensions.add(overlay);
                extensions.add(synthesisOverlay);
                addButtons(back);
                reset = new MyButton(x+200,y+80,
                                80,30,4,26,
                                Color.gray, Color.white,"reset",
                                8,22);
                makeTop = new MyButton(x+200,y+20,
                                80,30,4,26,
                                Color.gray, Color.white,"make top",
                                8,22);
                autoCreateTree = new MyButton(x+200,y+50,
                                80,30,4,26,
                                Color.gray, Color.white,"auto",
                                8,22);
                save = new MyButton(x+200,y-10,
                                80,30,4,26,
                                Color.gray, Color.white,"save",
                                8,22);
                start = new MyButton(x,y,
                                80,80,4,110,
                                Color.gray, Color.white,"+",
                                8,78);
                addButtons(start,reset,makeTop,autoCreateTree,save);
        }

        @Override
        public void tick() {
                overlay.tick();
                camera.tick();
                if(!isEmpty()) {
                        node.tick();
                        if(node.pressed()) {
                                curNode = node.getPressed();
                                synthesisOverlay.setMonster(curNode.getMonster());
                        }
                } else {
                        start.setX(x-camera.getX());
                        start.setY(y-camera.getY());
                }
                if(start.pressed()) {
                        overlay.activate();
                }
                if(save.pressed()) {
                        syn.save();
                }
                
                if(keyDown[2]) {
                    if(!isEmpty()) {
                    	if(node.hasChildren()) {
		                	curNode = node.getLeft();
		                    lastNodes.add(node);
		                    node = curNode;
		                    curNode.makeTop();
		                    camera.reset();
		                    wasLeft.add(true);
	                        synthesisOverlay.setMonster(curNode.getMonster());
                    	} else {
                    		if(!wasLeft.isEmpty()) {
	                    		if(!wasLeft.getLast()) {
	                    			keyDown[1] = true;
	                    		}
                    		}
                    	}
                        keyDown[2] = false;
                    }
                }
                if(keyDown[3]) {
                    if(!isEmpty()) {
                    	if(node.hasChildren()) {
	                    	curNode = node.getRight();
	                        lastNodes.add(node);
	                        node = curNode;
	                        curNode.makeTop(); 
	                        camera.reset();
	                        wasLeft.add(false);
	                        synthesisOverlay.setMonster(curNode.getMonster());
                    	} else {
                    		if(!wasLeft.isEmpty()) {
                    			if(wasLeft.getLast()) {
                    				keyDown[1] = true;
                    			}
                    		}
                    	}
                        keyDown[3] = false;
                    }
                }

                if(keyDown[1]) {
                        if(!lastNodes.isEmpty()) {
                                node.resetPosition();
                                node = lastNodes.removeLast();
    	                        synthesisOverlay.setMonster(node.getMonster());
    	                        wasLeft.removeLast();
                        } else {
                        	
                        }
                        keyDown[1] = false;
                }
                if(reset.pressed()) {
                    node = null;
                    curNode = null;
                    overlay.deactivate();
                    start.activate();
                    lastNodes = new LinkedList<MonsterNode>();
                    wasLeft = new LinkedList<Boolean>();
                    synthesisOverlay.reset();
                }

                if(makeTop.pressed()) {
                        if(!isEmpty()) {
                                if(curNode != null) {
                                        if(curNode != node) {
                                                lastNodes.add(node);
                                                node = curNode;
                                                curNode.makeTop();
                                                camera.reset();
                                        }
                                }
                        }
                }

                if(autoCreateTree.pressed()) {
                        if(curNode == null) {
                                node.autoCreateTree();
                        } else {
                                curNode.autoCreateTree();
                        }
                }

                if(overlay.hasMonster()) {
                        start.deactivate();
                        overlay.deactivate();
                        node = new MonsterNode("",overlay.getMonster(), camera);
                }

                if(synthesisOverlay.lookingAtSomething()) {
                        if(curNode != null) {
                        		main.monsterList.Recipe temp = synthesisOverlay.getRecipe();
                        		temp.getRes().resetPreferred();
                        		temp.setPreferredRecipe(true);
                                curNode.addRecipe(temp);
                                //curNode = null;
                                //synthesisOverlay.reset();
                        }
                }

                synthesisOverlay.tick();
                /*if(overlay.hasMonster()) {
                        synthesisOverlay.setMonster(overlay.getMonster());
                }*/
                if(back.pressed()) {
                        changeState(StateID.Menu);
                }
                // TODO Auto-generated method stub

        }

        @Override
        public void render(Graphics g) {
                drawBackground(g);
                if(!isEmpty()) {
                    node.render(g);
                    if(!wasLeft.isEmpty()) {
                		g.setColor(Color.black);
                    	if(wasLeft.getLast()) {
                    		g.drawLine(x-110,y+80,640,700);
                    	} else {
                    		g.drawLine(x-110,y+80,300,700);
                    	}
                    }
                }
                renderAll(g);
                // TODO Auto-generated method stub

        }

        @Override
        public void drawBackground(Graphics g) {
                g.setColor(Color.gray);
                g.fillRect(0,0,Synthesis.WIDTH,Synthesis.WIDTH);
        }

        @Override
        public void mousePressed(MouseEvent e) {
                if(active) {
                        for(SimpleButton cur : buttons) {
                                cur.mousePressed(e);
                        }
                        for(TextField cur : textFields) {
                                cur.mousePressed(e);
                        }
                        for(Extension cur : extensions) {
                                cur.mousePressed(e);
                        }
                        if(!isEmpty()) {
                                node.mousePressed(e);
                        }
                }
        }

        @Override
        public void keyPressed(KeyEvent e) {
                if(active) {
                        int key = e.getKeyCode();
                        if(key == KeyEvent.VK_SHIFT) {
                                shiftPressed = true;
                        }
                        if(key == KeyEvent.VK_UP) {
                                keyDown[0] = true;
                                //camera.setVelY(-5);
                        } else if(key == KeyEvent.VK_DOWN) {
                                keyDown[1] = true;
                                //camera.setVelY(5);
                        } else if(key == KeyEvent.VK_LEFT) {
                                keyDown[2] = true;
                                //camera.setVelX(-5);
                        } else if(key == KeyEvent.VK_RIGHT) {
                                keyDown[3] = true;
                                //camera.setVelX(5);
                        } else if(key == KeyEvent.VK_TAB) {
                                if(!shiftPressed) {
                                        switchToNextTextField();
                                } else {
                                        switchToPreviousTextField();
                                }
                        } else {
                                for(TextField cur : textFields) {
                                        cur.keyPressed(e);
                                }
                                for(Extension cur : extensions) {
                                        cur.keyPressed(e);
                                }
                        }
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                if(active) {
                        int key = e.getKeyCode();
                        if(key == KeyEvent.VK_SHIFT) {
                                shiftPressed = false;
                        }/*
                        if(key == KeyEvent.VK_UP) {
                                keyDown[0] = false;
                        } else if(key == KeyEvent.VK_DOWN) {
                                keyDown[1] = false;
                        } else if(key == KeyEvent.VK_LEFT) {
                                keyDown[2] = false;
                        } else if(key == KeyEvent.VK_RIGHT) {
                                keyDown[3] = false;
                        } else {*/
                                for(TextField cur : textFields) {
                                        cur.keyReleased(e);
                                }
                                for(Extension cur : extensions) {
                                        cur.keyReleased(e);
                                }
                        //}
                        if(!keyDown[0] && !keyDown[1]) {
                                camera.setVelY(0);
                        }
                        if(!keyDown[2] && !keyDown[3]) {
                                camera.setVelX(0);
                        }
                }
        }

        public boolean isEmpty() {
                return node == null;
        }

}