package main;

import java.awt.Color;
import java.util.LinkedList;

import main.monsterList.Monster;
import main.monsterList.Recipe;

//Klasse f&#65533;r statische methoden
public class S {
        public static int clamp(int cur, int min, int max) {
                int actMin = Math.min(min,max);
                int actMax = Math.max(min,max);
                if(cur < min) {
                        return min;
                }
                if(cur > max) {
                        return max;
                }
                return cur;
        }

        public static Color getRankColor(Monster monster) {
                String rank = monster.getRank().toUpperCase();
                Color tempColor;
                switch(rank) {
                case "X": tempColor = Color.MAGENTA;
                break;
                case "S": tempColor = Color.yellow;
                break;
                case "A": tempColor = Color.red;
                break;
                case "B": tempColor = Color.orange;
                break;
                case "C": tempColor = Color.GREEN;
                break;
                case "D": tempColor = Color.blue;
                break;
                case "E": tempColor = Color.white;
                break;
                case "F": tempColor = Color.lightGray;
                break;
                default: tempColor = Color.darkGray;
                break;
                }
                return tempColor;
        }

        public static Recipe getCheapestRecipe(LinkedList<Recipe> recipes) {
                if(recipes.isEmpty()) {
                        System.err.println("Error: Trying to get the cheapest recipe out of an empty list");
                        throw new IllegalArgumentException();
                } else {
                		boolean preferredRecipeFound = false;
                        int min = S.RankToInt("X") + S.RankToInt("X") + 1;
                        int minIndex = 0;

                        Recipe cur;
                        for(int i = 0; i < recipes.size(); i++) {
                                cur = recipes.get(i);
                            	if(!preferredRecipeFound) {
                                	if(cur.isPreferredRecipe()) {
                                    	preferredRecipeFound = true;
                                    	minIndex = i;
                                    } else {
		                                int curValue = 0;
		                                curValue += S.RankToInt(cur.getFst().getRank());
		                                curValue += S.RankToInt(cur.getSnd().getRank());
		
		                                if(curValue < min) {
	                                        min = curValue;
	                                        minIndex = i;
		                            	}
                                    }
                            	}

                        }
                    	return recipes.get(minIndex);
                }
        }

        public static LinkedList<Recipe> filterRecipes(LinkedList<Recipe>
recipes, LinkedList<String> dontInclude) {
                LinkedList<Recipe> temp = new LinkedList<Recipe>();
                for(Recipe cur : recipes) {
                        String fst = cur.getFst().getName();
                        String snd = cur.getSnd().getName();
                        if(notIn(fst,dontInclude) && notIn(snd,dontInclude)) {
                                temp.add(cur);
                                //dontInclude.add(fst);
                                //dontInclude.add(snd);
                        }
                }
                return temp;
        }

        public static boolean notIn(String string, LinkedList<String> strings) {
                for(String cur : strings) {
                        if(cur.equals(string)) {
                                return false;
                        }
                }
                return true;
        }

        public static int RankToInt(String rank) {
                int value;
                switch (rank.toUpperCase()) {
                case "F" : value = 8;
                break;

                case "E" : value = 4;
                break;

                case "D" : value = 2;

                case "C" : value = 1;
                break;

                case "B" : value = 2;
                break;

                case "A" : value = 2;
                break;

                case "S" : value = 10;
                break;

                case "X" : value = 30;
                break;

                default : value = 1;
                break;
                }
                return value;
        }

        public static Color getTypeColor(Monster monster) {
                String type = monster.getType().toLowerCase();
                Color tempColor;
                switch(type) {
                case "slime" : tempColor = Color.blue;
                break;
                case "nature" : tempColor = Color.green;
                break;
                case "material" : tempColor = Color.lightGray;
                break;
                case "dragon" : tempColor = Color.yellow;
                break;
                case "undead" : tempColor = Color.MAGENTA;
                break;
                case "demon" : tempColor = Color.darkGray;
                break;
                case "incarnus" : tempColor = Color.white;
                break;
                case "beast" : tempColor = Color.gray;
                break;
                default : tempColor = Color.white;
                }
                return tempColor;
        }
}
