package main.visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import main.S;
import main.adapters.buttons.MyButton;
import main.monsterList.Monster;
import main.monsterList.Recipe;

public class MonsterNode {

        private int x,y;
        private Monster monster;
        private boolean hasChildren = false, pressed = false;
        private MonsterNode left = null, right = null;
        private MyButton button;
        private Camera camera;
        private String position;
        private LinkedList<String> lastPositions = new LinkedList<String>();
        int xOffset;
        int lChildxOffset, rChildxOffset;

        public MonsterNode(String position, Monster monster, Camera camera) {
                this.position = position;
                updatePosition();
                this.monster = monster;
                this.camera = camera;
                button = new MyButton(x,y,
                                60,30,4,12,
                                S.getRankColor(monster), Color.gray,monster.getName(),
                                8,20);
        }

        public void tick() {
                button.setX(x-camera.getX());
                button.setY(y-camera.getY());
                if(button.pressed()) {
                        pressed = true;
                }
                if(hasChildren) {
                        left.tick();
                        right.tick();
                }
        }

        public boolean pressed() {
                if(pressed) {
                        return true;
                } else         if(hasChildren) {
                        return left.pressed() || right.pressed();
                }
                return false;
        }

        public void autoCreateTree() {
                LinkedList<String> temp = new LinkedList<String>();
                autoCreateTree(temp);
                /*if(depth > 0) {
                        LinkedList<Recipe> curRecipes = monster.getRecipes();
                        if(!curRecipes.isEmpty()) {
                                addRecipe(S.getCheapestRecipe(curRecipes));
                                left.autoCreateTree(depth-1);
                                right.autoCreateTree(depth-1);
                        }
                } */
        }
        public void autoCreateTree(LinkedList<String> dontInclude) {
                        dontInclude.add(monster.getName());
                        LinkedList<Recipe> curRecipes = S.filterRecipes(monster.getRecipes(),dontInclude);
                        if(!curRecipes.isEmpty()) {
                                addRecipe(S.getCheapestRecipe(curRecipes));

                                LinkedList<String> dontIncludeR = new LinkedList<String>();
                                dontIncludeR.addAll(dontInclude);

                                left.autoCreateTree(dontInclude);
                                right.autoCreateTree(dontIncludeR);
                        }
        }

        public MonsterNode getPressed() {
                if(pressed) {
                        pressed = false;
                        return this;
                } else if(hasChildren) {
                        if(left.pressed()) {
                                return left.getPressed();
                        } else if(right.pressed()) {
                                return right.getPressed();
                        }
                }
                throw new IllegalStateException();
        }

        public void addRecipe(Recipe recipe) {
                if(recipe.getRes().getName().equalsIgnoreCase(monster.getName())) {
                        left = new MonsterNode(position + "l",recipe.getFst(),camera);
                        right = new MonsterNode(position + "r",recipe.getSnd(),camera);
                        hasChildren = true;
                } else {
                        throw new IllegalArgumentException();
                }
        }

        public void render(Graphics g) {
                button.render(g);
                if(hasChildren) {
                        left.render(g);
                        right.render(g);
                        g.setColor(Color.black);
                        int cameraX = camera.getX();
                        int cameraY = camera.getY();
                        g.drawLine(30+x-cameraX, y-cameraY, x+lChildxOffset-cameraX+30,
y-70-cameraY);
                        g.drawLine(30+x-cameraX, y-cameraY, x+rChildxOffset-cameraX+30,
y-70-cameraY);
                }
        }

        public void updatePosition() {
                //x = 580    y = 550
                //calculate xOffset
                xOffset = 0;
                for(int i = 0; i < position.length(); i++) {
                        char curChar = position.charAt(i);
                        double pow = Math.pow(2,(i));
                        int dist = (int)(255/pow);
                        //System.out.println("Power of 2: " + pow);
                        if(curChar == 'l') {
                                xOffset -= (int)dist;
                        } else {
                                xOffset += (int)dist;
                        }
                }
                x = 580 + xOffset;

                int yOffset = (int)(-50 - 100 * (position.length()-1));
                y = 550 + yOffset;
                //System.out.println("yOffset: " + yOffset);

                calculateChildPositions();
        }
        public void calculateChildPositions() {
                double pow = Math.pow(2,position.length());
                int dist = (int)(255/pow);
                lChildxOffset = -(int)dist;
                rChildxOffset = (int)dist;
        }
        
        public MonsterNode getLeft() {
        	return left;
        }
        
        public MonsterNode getRight() {
        	return right;
        }
        
        public boolean hasChildren() {
        	return left != null && right != null;
        }


        public void mousePressed(MouseEvent e) {
                button.mousePressed(e);
                if(hasChildren) {
                        left.mousePressed(e);
                        right.mousePressed(e);
                }
        }

        public Monster getMonster() {
                return monster;
        }

        public void makeTop() {
                lastPositions.add(position);
                position = "";
                updatePosition(position);
        }

        public void resetPosition() {
                position = lastPositions.removeLast();
                updatePosition(position);
        }

        public void updatePosition(String curPosition) {
                position = curPosition;
                updatePosition();
                if(hasChildren) {
                        left.updatePosition(curPosition + "l");
                        right.updatePosition(curPosition + "r");
                }
        }
}