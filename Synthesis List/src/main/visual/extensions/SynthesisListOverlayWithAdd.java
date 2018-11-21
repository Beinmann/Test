package main.visual.extensions;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import main.S;
import main.adapters.Extension;
import main.adapters.buttons.MyButton;
import main.adapters.buttons.SimpleButton;
import main.adapters.buttons.TextField;
import main.monsterList.Monster;
import main.monsterList.Recipe;

public class SynthesisListOverlayWithAdd extends Extension{


        private Monster curMonster = null;
        private LinkedList<Recipe> recipes = new LinkedList<Recipe>();
        private LinkedList<SimpleButton> buttons = new LinkedList<SimpleButton>();
        private LinkedList<TextField> textFields = new LinkedList<TextField>();
        private MonsterListOverlayWithAdd overlay;
        private int firstElement = 0;
        private String[] monsterNames1 = new String[10];
        private String[] monsterNames2 = new String[10];


        private LinkedList<Monster> monsters = new LinkedList<Monster>();
        private LinkedList<Recipe> allRecipes = new LinkedList<Recipe>();



        //Buttons and Text fields
        private MyButton[] listButtons1 = new MyButton[10];
        private MyButton[] listButtons2 = new MyButton[10];

        private MyButton plusButton;
        private MyButton add;
        private TextField plusField1;
        private TextField plusField2;





        private int numberOfEntries;
        private boolean somethingInSearchField = false, addingRecipe = false,
lookingAtRecipe = false, writingL = false, writingR = false;
        private boolean enterPressed = false, readyToAdd = false;
        private int x, y, width = 670, height = 800;
        private double scale;
        private LinkedList<Recipe> tempRecipes;

        private MyButton up, down, searchButton, remove;

        private TextField searchField;
        private int lookingAt = 0;
        private Font fnt = new Font("Arial",1,20), fnt2 = new Font("Arial",1,14);


        public SynthesisListOverlayWithAdd(int x, int y, double scale,
LinkedList<Monster> monsters, LinkedList<Recipe> allRecipes) {
                this.x = x;
                this.y = y;
                this.scale = scale * 0.5;

                this.allRecipes = allRecipes;
                this.monsters = monsters;
                overlay = new
MonsterListOverlayWithAdd(x+(int)(100*this.scale),y+(int)(820*this.scale),this.scale/1.4,monsters);

                //initializing buttons and Text fields
                searchField = new TextField((int)(x + (520*this.scale)),(int)(y + ((250)
* this.scale)),
                (int)(130*this.scale),(int)(50*this.scale),(int)(6*this.scale),(int)(26*this.scale),
                Color.gray, Color.white, "","",
                (int)(10*this.scale),(int)(35*this.scale),20);

                up = new MyButton((int)(x + (550*this.scale)),(int)(y + ((310) *
this.scale)),
                                (int)(70*this.scale),(int)(35*this.scale),(int)(6*this.scale),(int)(26*this.scale),
                                Color.gray, Color.white, "up",
                                (int)(18*this.scale),(int)(25*this.scale));

                down = new MyButton((int)(x + (550*this.scale)),(int)(y + ((350) *
this.scale)),
                                (int)(70*this.scale),(int)(35*this.scale),(int)(6*this.scale),(int)(22*this.scale),
                                Color.gray, Color.white, "down",
                                (int)(6*this.scale),(int)(26*this.scale));


                searchButton = new MyButton((int)(x + (520*this.scale)),(int)(y + ((250)
* this.scale)),
                                (int)(130*this.scale),(int)(50*this.scale),(int)(6*this.scale),(int)(26*this.scale),
                                Color.gray, Color.white, "search...",
                                (int)(10*this.scale),(int)(35*this.scale));


                for(int i = 0; i < 10; i++) {
                        listButtons1[i] = new MyButton((int)(x + (30*this.scale)),(int)(y +
((30 + 65*i) * this.scale)),
                                        (int)(220*this.scale),(int)(60*this.scale),(int)(6*this.scale),(int)(20*this.scale),
                                        Color.gray, Color.white, "Hello World",
                                        (int)(10*this.scale),(int)(40*this.scale));



                        listButtons2[i] = new MyButton((int)(x + (280*this.scale)),(int)(y +
((30 + 65*i) * this.scale)),
                                        (int)(220*this.scale),(int)(60*this.scale),(int)(6*this.scale),(int)(20*this.scale),
                                        Color.gray, Color.white, "Hello World",
                                        (int)(10*this.scale),(int)(40*this.scale));
                }

                plusButton = new MyButton((int)(x + (30*this.scale)),(int)(y + (720 *
this.scale)),
                                                (int)(60*this.scale),(int)(60*this.scale),(int)(6*this.scale),(int)(80*this.scale),
                                                Color.gray, Color.white, "+",
                                                (int)(8*this.scale),(int)(56*this.scale));

                add = new MyButton((int)(x + (550*this.scale)),(int)(y + (730 *
this.scale)),
                                                (int)(70*this.scale),(int)(40*this.scale),(int)(4*this.scale),(int)(28*this.scale),
                                                Color.gray, Color.white, "add",
                                                (int)(8*this.scale),(int)(30*this.scale));

                plusField1 = new TextField((int)(x + (30*this.scale)),(int)(y + ((720) *
this.scale)),
                                                (int)(220*this.scale),(int)(60*this.scale),(int)(6*this.scale),(int)(35*this.scale),
                                                Color.gray, Color.white, "","",
                                                (int)(10*this.scale),(int)(40*this.scale),20);


                plusField2 = new TextField((int)(x + (280*this.scale)),(int)(y + ((720)
* this.scale)),
                                                (int)(220*this.scale),(int)(60*this.scale),(int)(6*this.scale),(int)(35*this.scale),
                                                Color.gray, Color.white, "","",
                                                (int)(10*this.scale),(int)(40*this.scale),20);




                //Adding buttons
                addButtons(up, down, searchButton,plusButton,add);
                addButtons(listButtons1);
                addButtons(listButtons2);
                textFields.add(searchField);
                textFields.add(plusField1);
                textFields.add(plusField2);
        }

        public void tick() {
                if(active) {
                        if(hasMonster()) {
                                overlay.show();
                                overlay.tick();

                                somethingInSearchField = searchField.getText().length() > 0;



                                if(somethingInSearchField) {
                                        tempRecipes = search(searchField.getText());
                                        //System.out.println("Searching for " + searchField.getText());
                                } else {
                                        tempRecipes = recipes;
                                }
                                if(searchField.currentlyWriting() || somethingInSearchField) {
                                        searchButton.deactivate();
                                } else {
                                        searchButton.activate();
                                }


                                if(down.pressed()) {
                                        firstElement++;
                                }
                                if(up.pressed()) {
                                        firstElement--;
                                }
                                firstElement = Math.max(S.clamp(firstElement,0,tempRecipes.size()-1),0);
                                numberOfEntries = Math.min(tempRecipes.size() - firstElement, 10);


                                for(int i = 0; i < numberOfEntries; i++) {
                                        listButtons1[i].setText(tempRecipes.get(Math.max(i+firstElement,0)).getFst().getName());
                                        listButtons1[i].activate();
                                        listButtons2[i].setText(tempRecipes.get(Math.max(i+firstElement,0)).getSnd().getName());
                                        listButtons2[i].activate();
                                }
                                for(int i = numberOfEntries; i < 10; i++) {
                                        listButtons1[i].deactivate();
                                        listButtons2[i].deactivate();
                                }


                                if(plusField1.currentlyWriting()) {
                                        writingL = true;
                                        writingR = false;
                                } else if(plusField2.currentlyWriting()) {
                                        writingR = true;
                                        writingL = false;
                                }


                                if(!addingRecipe) {
                                        plusField1.deactivate();
                                        plusField2.deactivate();
                                        add.deactivate();
                                        plusButton.activate();
                                } else {
                                        if(writingL) {
                                                overlay.setSearch(plusField1.getText());
                                        } else if(writingR) {
                                                overlay.setSearch(plusField2.getText());
                                        }
                                }

                                if(overlay.hasMonster()) {
                                        if(writingL) {
                                                plusField1.setText(overlay.getMonster().getName());
                                                writingL = false;
                                        } else if(writingR) {
                                                plusField2.setText(overlay.getMonster().getName());
                                                writingR = false;
                                        } else {
                                                overlay.getMonster();
                                        }
                                }

                                if(enterPressed) {
                                        LinkedList<Monster> curMonsters = overlay.getCurMonsters();
                                        if(!curMonsters.isEmpty()) {
                                                if(!plusField1.currentlyWriting() && !plusField2.currentlyWriting()) {
                                                        readyToAdd = true;
                                                } else if(writingL && plusField1.notEmpty()) {
                                                        plusField1.setText(curMonsters.getFirst().getName());
                                                        plusField1.setCurrentlyWriting(false);
                                                        plusField2.setCurrentlyWriting(true);
                                                } else if(writingR && plusField2.notEmpty()) {
                                                        plusField2.setText(curMonsters.getFirst().getName());
                                                        plusField2.setCurrentlyWriting(false);
                                                }
                                        }
                                        enterPressed = false;
                                }

                                if(plusButton.pressed()) {
                                        addingRecipe = true;
                                        add.activate();
                                        plusButton.deactivate();
                                        plusField1.activate();
                                        plusField2.activate();
                                        plusField1.setCurrentlyWriting(true);
                                }

                                if(add.pressed()) {
                                        readyToAdd = true;
                                }

                                if(readyToAdd) {
                                        //add new recipe
                                        if(inList(plusField1.getText()) && inList(plusField2.getText())) {
                                                Recipe temp = new Recipe(curMonster,getMonster(plusField1.getText()),getMonster(plusField2.getText()),true);
                                                curMonster.resetPreferred();
                                                curMonster.add(temp);
                                                allRecipes.add(temp);
                                                addingRecipe = false;
                                                writingL = false;
                                                writingR = false;
                                                plusField1.setText("");
                                                plusField2.setText("");
                                                plusButton.activate();
                                                overlay.setSearch("");
                                                lookingAt = recipes.size()-1;
                                                lookingAtRecipe = true;
                                        }
                                        readyToAdd = false;
                                }





                                //if a list button is pressed the current recipe is set to the recipe of that button
                                for(int i = 0; i < numberOfEntries; i++) {
                                        if(listButtons1[i].pressed() || listButtons2[i].pressed()) {
                                                lookingAtRecipe = true;
                                                lookingAt = i + firstElement;
                                        }
                                }
                        } else {
                                overlay.hide();
                        }
                }

        }


        public void reset() {
                curMonster = null;
                lookingAtRecipe = false;
        }

        public boolean hasMonster() {
                return curMonster != null;
        }

        public boolean lookingAtSomething() {
                return lookingAtRecipe;
        }

        public Recipe getRecipe() {
                lookingAtRecipe = false;
                return recipes.get(lookingAt);
        }

        public boolean inList(String name) {
                for(Monster cur : monsters) {
                        if(cur.getName().equalsIgnoreCase(name)) {
                                return true;
                        }
                }
                return false;
        }

        public Monster getMonster(String name) {
                for(Monster cur : monsters) {
                        if(cur.getName().equalsIgnoreCase(name)) {
                                return cur;
                        }
                }
                throw new IllegalArgumentException();
        }

        public LinkedList<Recipe> search(String name) {
                LinkedList<Recipe> temp = new LinkedList<Recipe>();
                for(Recipe cur : recipes) {
                        String fst = cur.getFst().getName();
                        String snd = cur.getSnd().getName();
                        if(fst.toUpperCase().contains(name.toUpperCase()) || snd.toUpperCase().contains(name.toUpperCase())) {
                                temp.add(cur);
                        }
                }
                return temp;
        }

        public void setMonster(Monster monster) {
                curMonster = monster;
                recipes = curMonster.getRecipes();
        }

        public void render(Graphics g) {
                if(active) {
                        g.setColor(Color.black);
                        g.fillRect(x-10,y-10,355,670);
                        g.setColor(Color.lightGray);
                        g.fillRect(x,y,(int)(width*scale),(int)(height*scale));
                        g.setColor(Color.black);
                        g.fillRect(x,y+(int)(700*scale),(int)(width*scale),(int)(5*scale));
                        searchButton.render(g);
                        up.render(g);
                        down.render(g);
                        if(hasMonster()) {
                                g.setFont(fnt2);
                                g.drawString(curMonster.getName(),x+(int)(510 * scale),y + (int)(140*scale));
                                for(int i  = 0; i < numberOfEntries; i++) {
                                        g.setFont(fnt);
                                        g.setColor(Color.black);
                                        g.drawString("+",x+(int)(252*scale),y + (int)(74*scale) + (int)(66*i*scale));
                                }
                                for(TextField cur : textFields) {
                                        cur.render(g);
                                }
                                for(SimpleButton cur : buttons) {
                                        cur.render(g);
                                }
                        }
                        overlay.render(g);
                }

        }












        //Inputs
        @Override
        public void mousePressed(MouseEvent e) {
                if(active) {
                        if(hasMonster()) {
                                overlay.mousePressed(e);
                                if(SwingUtilities.isMiddleMouseButton(e)) {

                                } else if(SwingUtilities.isLeftMouseButton(e)) {
                                        for(SimpleButton cur : buttons) {
                                                cur.mousePressed(e);
                                        }
                                        for(TextField cur : textFields) {
                                                cur.mousePressed(e);
                                        }
                                }
                        }

                }
        }

        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
                if(active) {
                        if(hasMonster()) {
                                int key = e.getKeyCode();
                                if(key == KeyEvent.VK_ESCAPE) {
                                        addingRecipe = false;
                                }
                                if(key == KeyEvent.VK_ENTER) {
                                        enterPressed = true;
                                } else {
                                        for(TextField cur : textFields) {
                                                cur.keyPressed(e);
                                        }
                                }
                                overlay.keyPressed(e);
                        }
                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
                if(active) {
                        if(hasMonster()) {
                                overlay.keyReleased(e);
                                for(TextField cur : textFields) {
                                        cur.keyReleased(e);
                                }
                        }
                }
        }





        @Override
        public LinkedList<TextField> getTextFields() {
                LinkedList<TextField> temp = new LinkedList<TextField>();
                if(active) {
                        temp.addAll(textFields);
                        temp.addAll(overlay.getTextFields());
                }
                return temp;
        }






        //Helpful functions
        public void addButtons(SimpleButton... buttons) {
                for(int i = 0; i < buttons.length; i++) {
                        this.buttons.add(buttons[i]);
                }
        }

}