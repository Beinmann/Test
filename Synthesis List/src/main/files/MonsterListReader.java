package main.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import main.Synthesis;
import main.monsterList.Monster;
import main.monsterList.Recipe;

public class MonsterListReader {

	private String monsterListLocation = Synthesis.MONSTER_LIST_LOCATION;
	private String synthesisListLocation = Synthesis.SYNTHESIS_LIST_LOCATION;
	
	private LinkedList<Recipe> recipes = new LinkedList<Recipe>();
	private LinkedList<main.monsterList.Monster> monsters = new LinkedList<Monster>();
	
	public LinkedList<Monster> readMonsterList() {
		FileReader fr = null;
		BufferedReader br = null;
		StringTokenizer st = null;
		
		try {
			fr = new FileReader(monsterListLocation);
			br = new BufferedReader(fr);
			
			
			String curLine = null;
			String name, type, rank;
			
			
			try {
				while((curLine = br.readLine()) != null) {
					//System.out.println("Reading Line");
					st = new StringTokenizer(curLine,"|");
					
					name = st.nextToken();
					type = st.nextToken();
					rank = st.nextToken();
					
					addMonster(new Monster(name,type,rank));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		try {
			if(fr != null) {
				fr.close();
			}
			if(br != null) {
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return monsters;
	}
	
	
	public LinkedList<Recipe> readRecipes() {
		//System.out.println("Reading Synthesis List");
		FileReader fr = null;
		BufferedReader br = null;
		StringTokenizer st = null;
		
		try {
			fr = new FileReader(synthesisListLocation);
			br = new BufferedReader(fr);
			
			
			String curLine = null;
			String name1, name2, name3, isPreferredRecipe;
			
			Monster res, m1, m2;
			
			try {
				while((curLine = br.readLine()) != null) {
					//System.out.println("Reading Line");
					st = new StringTokenizer(curLine,"|");
					
					name1 = st.nextToken();
					name2 = st.nextToken();
					name3 = st.nextToken();
					isPreferredRecipe = st.nextToken();
					
					try {
					res = findMonster(name1);
					m1 = findMonster(name2);
					m2 = findMonster(name3);


					addRecipe(new Recipe(res,m1,m2,(isPreferredRecipe.equals("true"))));
					} catch (IllegalArgumentException e) {
						System.err.println("Not found");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		try {
			if(fr != null) {
				fr.close();
			}
			if(br != null) {
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		addRecipes();
		
		return recipes;
	
	}
	
	public void addRecipes() {
		for(Recipe curR : recipes) {
			String mName = curR.getRes().getName();
			Monster temp = findMonster(mName);
			temp.add(curR);
			//System.out.println("Added Recipe " + curR.getFst().getName() + " + " + curR.getSnd().getName() + " -> " + mName + "   to " + temp.getName());
		}
	}
	
	public Monster findMonster(String name) {
		for(Monster cur : monsters) {
			if(cur.getName().toUpperCase().equals(name.toUpperCase())) {
				return cur;
			}
		}
		System.err.println("Monster not in Monster List: " + name);
		throw new IllegalArgumentException();
	}
	
	public void addMonster(Monster monster) {
		boolean notInList = true;
		for(Monster cur : monsters) {
			if(cur.getName().toUpperCase().equals(monster.getName().toUpperCase())) {
				notInList = false;
				break;
			}
		}
		if(notInList) {
			monsters.add(monster);
		}
	}
	
	public void addRecipe(Recipe recipe) {
		boolean notInList = true;
		 
		
		for(Recipe cur : recipes) {
			boolean sameRes = cur.getRes().getName().equalsIgnoreCase(recipe.getRes().getName());
			boolean fstEqFst = cur.getFst().getName().equalsIgnoreCase(recipe.getFst().getName());
			boolean fstEqSnd = cur.getFst().getName().equalsIgnoreCase(recipe.getSnd().getName());
			boolean sndEqFst = cur.getSnd().getName().equalsIgnoreCase(recipe.getFst().getName());
			boolean sndEqSnd = cur.getSnd().getName().equalsIgnoreCase(recipe.getSnd().getName());
			if(sameRes) {
				if((fstEqFst && sndEqSnd) || (fstEqSnd && sndEqFst)) {
					notInList = false;
					break;
				}
			}
		}
		if(notInList) {
			recipes.add(recipe);
		}
	}
	
	public LinkedList<Monster> getMonsters() {
		return monsters;
	}
	
	public LinkedList<Recipe> getRecipes() {
		return recipes;
	}
	
}
