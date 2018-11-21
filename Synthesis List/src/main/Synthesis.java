package main;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.files.MonsterListReader;
import main.monsterList.Monster;
import main.monsterList.Recipe;
import main.states.Menu;
import main.states.MonsterList;
import main.states.Saving;
import main.states.State;
import main.states.StateID;
import main.states.SynthesisTree;

public class Synthesis extends Canvas implements Runnable {

        public static String MONSTER_LIST_LOCATION = "res/monsterList.txt";
        public static String SYNTHESIS_LIST_LOCATION = "res/synthesisList.txt";

        public static int WIDTH = 1280;
        public static int HEIGHT = 720;
        public static String TITLE = "Synthesis List";


        private Window window;
        private boolean running = true;
        private boolean visibleStats = false;

        private LinkedList<State> states = new LinkedList<State>();
        private LinkedList<Monster> monsters;
        private LinkedList<Recipe> recipes;
        private StateID curID = StateID.Menu;
        private KeyInput keyInput = new KeyInput();
        private MouseInput mouseInput = new MouseInput();
        private Saving saveState;


        private int x = 200;


        public static void main(String[] args) {
                EventQueue.invokeLater(() -> {
                        Synthesis synthesis = new Synthesis();
                        Thread thread = new Thread(synthesis);
                        thread.start();
                });
        }

        public Synthesis() {
                window = new Window(this);

                setFocusTraversalKeysEnabled(false);

                MonsterListReader reader = new MonsterListReader();
                monsters = reader.readMonsterList();
                recipes = reader.readRecipes();


                add(new Menu(keyInput, mouseInput, this));
                saveState = new Saving(keyInput,mouseInput,this);
                add(saveState);
                add(new SynthesisTree(keyInput,mouseInput,this));
                add(new MonsterList(keyInput, mouseInput, this));

                this.addKeyListener(keyInput);
                this.addMouseListener(mouseInput);

        }

        @Override
        public void run() {
                long lastTimeT = System.nanoTime(), lastTimeF = System.nanoTime(),
lastTimeStats = System.nanoTime(), now;
                double deltaT, deltaF;
                double amountOfTicks = 60;
                double amountOfFrames = 120;
                long oneSecond = 1000000000;
                double nsT = amountOfTicks / oneSecond;
                double nsF = amountOfFrames / oneSecond;
                int frames = 0;


                while(running) {
                        now = System.nanoTime();
                        deltaT = (now - lastTimeT) * nsT;
                        if(deltaT > 1) {
                                tick();
                                lastTimeT = System.nanoTime();
                                deltaT--;
                        }
                        deltaF = (now - lastTimeF) * nsF;
                        if(deltaF > 1) {
                                render();
                                lastTimeF = System.nanoTime();
                                deltaF--;
                                frames++;
                                lastTimeF = System.nanoTime();
                        }
                        if(visibleStats) {
                                if((now - lastTimeStats) > 1000000000) {
                                        System.out.println("fps: " + frames);
                                        frames = 0;
                                        lastTimeStats += 1000000000;
                                }
                        }
                }
        }

        public void render() {
                BufferStrategy bs = this.getBufferStrategy();
                if(bs == null) {
                        this.createBufferStrategy(3);
                        return;
                }
                java.awt.Graphics g = bs.getDrawGraphics();


                for(State cur : getActiveStates()) {
                        cur.render(g);
                }



                g.dispose();
                bs.show();
        }







        public void tick() {
                setActiveStates();
                removeDeletedStates();
                for(State cur : getActiveStates()) {
                        cur.tick();
                }
        }












        public void removeDeletedStates() {
                LinkedList<State> temp = new LinkedList<State>();
                for(State cur : states) {
                        if(cur.markedForDeletion()) {
                                temp.add(cur);
                        }
                }
                for(State cur : temp) {
                        states.remove(cur);
                        System.out.println("Removed State: " + cur.getId());
                }
        }

        public void setActiveStates() {
                for(State cur : states) {
                        if(cur.getId() == curID) {
                                cur.setActive(true);
                        } else {
                                cur.setActive(false);
                        }
                }
        }

        public LinkedList<State> getActiveStates() {
                LinkedList<State> temp = new LinkedList<State>();
                for(State cur : states) {
                        if(cur.active()) {
                                temp.add(cur);
                        }
                }
                return temp;
        }

        public void add(State state) {
                states.add(state);
                System.out.println("Added State: " + state.getId());
        }

        public void setState(StateID stateID) {
                curID = stateID;
        }

        public LinkedList<Monster> getMonsters() {
                return monsters;
        }

        public LinkedList<Recipe> getRecipes() {
                return recipes;
        }
        public void save() {
                StateID temp = curID;
                curID = StateID.Saving;
                
                
                
                BufferStrategy bs = this.getBufferStrategy();
                if(bs == null) {
                        this.createBufferStrategy(3);
                        return;
                }
                java.awt.Graphics g = bs.getDrawGraphics();

                g.setColor(java.awt.Color.BLACK);
                Font fnt = new Font("Arial",1,100);
                g.setFont(fnt);
                g.drawString("Saving...",420,320);



                g.dispose();
                bs.show();
                saveState.save();
                curID = temp;
        }
}