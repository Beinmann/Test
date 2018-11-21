package main.states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import main.Synthesis;
import main.adapters.Adapter;
import main.adapters.Extension;
import main.adapters.KeyInput;
import main.adapters.MouseInput;
import main.adapters.buttons.SimpleButton;
import main.adapters.buttons.TextField;

public abstract class State extends Adapter {

        protected StateID id;
        protected Synthesis syn;
        protected boolean active = true;
        protected boolean marked = false;
        protected boolean shiftPressed = false;
        protected LinkedList<Extension> extensions = new LinkedList<Extension>();
        protected LinkedList<SimpleButton> buttons = new LinkedList<SimpleButton>();
        protected LinkedList<TextField> textFields = new LinkedList<TextField>();

        public State(StateID id, KeyInput keyInput, MouseInput mouseInput,
Synthesis syn) {
                super(keyInput, mouseInput);
                this.id = id;
                this.syn = syn;
        }

        public abstract void tick();
        public abstract void render(Graphics g);
        public boolean markedForDeletion() {
                return marked;
        }





        //Getter Setter
        public boolean active() {
                return active;
        }
        public StateID getId() {
                return id;
        }
        public void setActive(boolean active) {
                this.active = active;
        }
        public void delete() {
                marked = true;
                keyInput.remove(this);
                mouseInput.remove(this);
        }
        public void renderButtons(Graphics g) {
                for(main.adapters.buttons.SimpleButton cur : buttons) {
                        cur.render(g);
                }
        }

        public void renderExtensions(Graphics g) {
                for(Extension cur : extensions) {
                        cur.render(g);
                }
        }

        public void renderTextFields(Graphics g) {
                for(TextField cur : textFields) {
                        cur.render(g);
                }
        }

        public void renderAll(Graphics g) {
                renderButtons(g);
                renderTextFields(g);
                renderExtensions(g);
        }

        @Override
        public void mousePressed(MouseEvent e) {
                if(active) {
                        if(javax.swing.SwingUtilities.isLeftMouseButton(e)) {
                                for(SimpleButton cur : buttons) {
                                        cur.mousePressed(e);
                                }
                                for(TextField cur : textFields) {
                                        cur.mousePressed(e);
                                }
                                for(Extension cur : extensions) {
                                        cur.mousePressed(e);
                                }
                        }
                }
        }

        public void switchToNextTextField() {

                //Add all this States buttons and buttons of extensions to a temporary list
                LinkedList<TextField> temp = new LinkedList<TextField>();
                temp.addAll(textFields);
                for(Extension cur : extensions) {
                        temp.addAll(cur.getTextFields());
                }


                //go through list and if a button is active then activate the next Button
                boolean lastFieldActive = false;
                for(int i = 0; i < temp.size(); i++) {
                        TextField cur = temp.get(i);
                        if(cur.isActive()) {
                                if(lastFieldActive) {
                                        cur.setCurrentlyWriting(true);
                                        lastFieldActive = false;
                                        break;
                                } else {
                                        if(cur.currentlyWriting()) {
                                                cur.setCurrentlyWriting(false);
                                                lastFieldActive = true;
                                        }
                                }
                        }
                }


                //Start from beginning of the list if the last Element was active
                if(lastFieldActive) {
                        for(int i = 0; i < temp.size(); i++) {
                                TextField cur = temp.get(i);
                                if(cur.isActive()) {
                                        cur.setCurrentlyWriting(true);
                                        lastFieldActive = false;
                                        break;
                                }
                        }
                }

        }


        public void switchToPreviousTextField() {

                //Add all this States buttons and buttons of extensions to a temporary list
                LinkedList<TextField> temp = new LinkedList<TextField>();
                temp.addAll(textFields);
                for(Extension cur : extensions) {
                        temp.addAll(cur.getTextFields());
                }


                //go through list and if a button is active then activate the next Button
                boolean lastFieldActive = false;
                for(int i = temp.size()-1; i >= 0; i--) {
                        TextField cur = temp.get(i);
                        if(cur.isActive()) {
                                if(lastFieldActive) {
                                        cur.setCurrentlyWriting(true);
                                        lastFieldActive = false;
                                        break;
                                } else {
                                        if(cur.currentlyWriting()) {
                                                cur.setCurrentlyWriting(false);
                                                lastFieldActive = true;
                                        }
                                }
                        }
                }


                //Start from beginning of the list if the last Element was active
                if(lastFieldActive) {
                        for(int i = temp.size()-1; i >= 0; i--) {
                                TextField cur = temp.get(i);
                                if(cur.isActive()) {
                                        cur.setCurrentlyWriting(true);
                                        lastFieldActive = false;
                                        break;
                                }
                        }
                }
        }

        @Override
        public void keyPressed(KeyEvent e) {
                if(active) {
                        for(TextField cur : textFields) {
                                cur.keyPressed(e);
                        }
                        for(Extension cur : extensions) {
                                cur.keyPressed(e);
                        }
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                if(active) {
                        for(TextField cur : textFields) {
                                cur.keyReleased(e);
                        }
                        for(Extension cur : extensions) {
                                cur.keyReleased(e);
                        }
                }
        }

        public void changeState(State newState) {
                syn.add(newState);
                syn.setState(newState.getId());
        }

        public void changeState(StateID newState) {
                syn.setState(newState);
        }

        public void drawBackground(Graphics g) {
                g.setColor(java.awt.Color.black);
                g.fillRect(0,0,Synthesis.WIDTH,Synthesis.HEIGHT);
        }

        public void addButtons(SimpleButton... buttons) {
                for(int i = 0; i < buttons.length; i++) {
                        this.buttons.add(buttons[i]);
                }
        }
}