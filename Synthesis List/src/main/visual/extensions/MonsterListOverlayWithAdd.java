package main.visual.extensions;

import java.awt.Color;
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

public class MonsterListOverlayWithAdd extends Extension{

        private LinkedList<Monster> monsters;
        private int firstElement = 0;
        private String[] monsterNames = new String[10];
        private MyButton[] listButtons = new MyButton[10];
        private MyButton[] plusButtons = new MyButton[10];
        private TextField[] plusFields = new TextField[10];
        private int numberOfEntries;
        private boolean lookingAtMonster = false, somethingInSearchField = false;
        private int x, y, width = 670, height = 670;
        private double scale;
        private LinkedList<Monster> tempMonsters;
        private boolean hideButtons = false;

        MyButton up, down, searchButton;

        private TextField searchField;
        private int lookingAt = 0;


        public MonsterListOverlayWithAdd(int x, int y, double scale,
LinkedList<Monster> monsters) {
                this.monsters = monsters;
                tempMonsters = monsters;
                this.x = x;
                this.y = y;
                this.scale = scale;


                searchField = new TextField((int)(x+(470*scale)),y +
(int)(220*scale),(int)(140*scale),(int)(40*scale),(int)(4*scale),(int)(20*scale),Color.gray,Color.white,"","",(int)(30*scale),(int)(27*scale),15);
                up = new MyButton((int)(x+(500*scale)),y +
(int)(300*scale),(int)(80*scale),(int)(45*scale),(int)(4*scale),(int)(30*scale),Color.gray,Color.white,"up",(int)(22*scale),(int)(31*scale));
                down = new MyButton((int)(x+(500*scale)),y +
(int)(360*scale),(int)(80*scale),(int)(45*scale),(int)(4*scale),(int)(25*scale),Color.gray,Color.white,"down",(int)(8*scale),(int)(32*scale));
                searchButton = new MyButton((int)(x+(470*scale)),y +
(int)(220*scale),(int)(140*scale),(int)(40*scale),(int)(4*scale),(int)(20*scale),Color.gray,Color.white,"Search...",(int)(30*scale),(int)(27*scale));

                addButtons(up, down, searchButton);
                textFields.add(searchField);

                int listButtonEdge = (int)(10 * scale);
                int listButtonWidth = (int)(400 * scale);
                int listButtonHeight = (int)(55 * scale);
                int listButtonFontSize = (int)(30 * scale);
                int listButtonXOffset = (int)(20 * scale);
                int listButtonYOffset = (int)(38 * scale);
                for(int i = 0; i < 10; i++) {
                        listButtons[i] = new MyButton((int)(x + (30*scale)),(int)(y + ((15 +
65*i) * scale)),listButtonWidth,listButtonHeight,
                                        listButtonEdge,listButtonFontSize,Color.gray, Color.white, "Hello World",listButtonXOffset,listButtonYOffset);
                }


                int plusButtonSize = (int)(55 * scale);
                for(int i = 0; i < 10; i++) {
                        plusButtons[i] = new MyButton((int)(x + (30*scale)),(int)(y + ((15 + 65
* i) * scale)),
                                        plusButtonSize,plusButtonSize,(int)(6*scale),(int)(70*scale),Color.gray,
Color.white, "+",(int)(7*scale),(int)(52*scale));
                }

                int plusFieldEdge = (int)(10 * scale);
                int plusFieldWidth = (int)(400 * scale);
                int plusFieldHeight = (int)(55 * scale);
                int plusFieldFontSize = (int)(30 * scale);
                int plusFieldXOffset = (int)(20 * scale);
                int plusFieldYOffset = (int)(38 * scale);
                for(int i = 0; i < 10; i++) {
                        plusFields[i] = new TextField((int)(x + (30*scale)),(int)(y + ((15 + 65
* i) * scale)),
                                        plusFieldWidth,plusFieldHeight,plusFieldEdge,plusFieldFontSize,Color.gray,
Color.white,"", "",plusFieldXOffset,plusFieldYOffset,20);
                        plusFields[i].deactivate();
                        textFields.add(plusFields[i]);
                }

                addButtons(listButtons);
                addButtons(plusButtons);

                numberOfEntries = Math.min(monsters.size(),10);
                /*
                listButtons[0] = new MyButton((int)(450 + (30*scale)),(int)(y + (15 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[1] = new MyButton((int)(450 + (30*scale)),(int)(y + (80 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[2] = new MyButton((int)(450 + (30*scale)),(int)(y + (145 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[3] = new MyButton((int)(450 + (30*scale)),(int)(y + (210 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[4] = new MyButton((int)(450 + (30*scale)),(int)(y + (275 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[5] = new MyButton((int)(450 + (30*scale)),(int)(y + (340 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[6] = new MyButton((int)(450 + (30*scale)),(int)(y + (405 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[7] = new MyButton((int)(450 + (30*scale)),(int)(y + (470 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[8] = new MyButton((int)(450 + (30*scale)),(int)(y + (535 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
                listButtons[9] = new MyButton((int)(450 + (30*scale)),(int)(y + (535 *
scale)),listButtonWidth,listButtonHeight,listButtonEdge,30,Color.gray,
Color.white, "Hello World",20,38);
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
                        firstElement = Math.max(S.clamp(firstElement,0,tempMonsters.size()),0);
                        numberOfEntries = Math.min(tempMonsters.size() - firstElement, 10);


                        for(int i = 0; i < numberOfEntries; i++) {
                                listButtons[i].activate();
                                monsterNames[i] = tempMonsters.get(i+firstElement).getName();
                                listButtons[i].setText(monsterNames[i]);
                        }
                        for(int i = numberOfEntries; i < 10; i++) {
                                listButtons[i].deactivate();
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


                        //render a plus button if at end of list
                        for(int i = 0; i < 10; i++) {
                                plusButtons[i].deactivate();
                        }
                        if(numberOfEntries < 10) {
                                if(!plusFields[numberOfEntries].currentlyWriting()) {
                                        plusButtons[numberOfEntries].activate();
                                }
                        }


                        //Check if plus button is pressed
                        for(int i = 0; i < 10; i++) {
                                if(plusButtons[i].pressed()) {
                                        plusFields[i].activate();
                                        plusFields[i].setCurrentlyWriting(true);
                                }
                        }

                        //Check if a new Monster is being added and
                        //deactivate all not currently writing plus fields
                        for(int i = 0; i < 10; i++) {
                                if(plusFields[i].pressed()) {
                                        if(plusFields[i].notEmpty()) {
                                                String name = plusFields[i].getText();
                                                Monster temp = new Monster(name,"nA","nA");
                                                plusFields[i].setText("");
                                                monsters.add(temp);
                                                listButtons[i].setText(name);
                                                listButtons[i].activate();
                                        }
                                }
                                if(!plusFields[i].currentlyWriting()) {
                                        plusFields[i].deactivate();
                                }
                        }
                }

        }

        public LinkedList<Monster> getCurMonsters() {
                return tempMonsters;
        }

        public void setSearch(String word) {
                searchField.setText(word);
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
                        up.render(g);
                        down.render(g);
                        searchButton.render(g);
                        if(!hideButtons) {
                                for(TextField cur : textFields) {
                                        cur.render(g);
                                }
                                for(SimpleButton cur : buttons) {
                                        cur.render(g);
                                }
                        }/*
                        for(int i = 0; i < numberOfEntries; i++) {
                                listButtons[i].render(g);
                        }*/



                }
                //MyButton test = new MyButton(1120,630,140,45,4,40,Color.gray,Color.white,"back",24,35);
                //test.render(g);

        }

        public void hide() {
                hideButtons = true;
        }

        public void show() {
                hideButtons = false;
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
                                }/*
                                for(int i = 0; i < numberOfEntries;i++) {
                                        listButtons[i].mousePressed(e);
                                }*/
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

        @Override
        public void keyReleased(KeyEvent e) {
                if(active) {
                        for(TextField cur : textFields) {
                                cur.keyReleased(e);
                        }
                }
        }

        public void addButtons(main.adapters.buttons.SimpleButton... buttons) {
                for(int i = 0; i < buttons.length; i++) {
                        this.buttons.add(buttons[i]);
                }
        }

}