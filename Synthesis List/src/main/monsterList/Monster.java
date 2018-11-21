package main.monsterList;

import java.util.LinkedList;

public class Monster {
	private String name;
	private String rank;
	private String type;
	
	private LinkedList<Recipe> recipes = new LinkedList<Recipe>();
	
	
	public Monster(String name, String type, String rank) {
		this.name = name;
		this.rank = rank;
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getRank() {
		return rank;
	}


	public void setRank(String rank) {
		this.rank = rank;
	}


	public String getType() {
		return type;
	}

	public void resetPreferred() {
		for(Recipe cur : recipes) {
			cur.setPreferredRecipe(false);
		}
	}

	public void setType(String type) {
		this.type = type;
	}


	public LinkedList<Recipe> getRecipes() {
		return recipes;
	}
	
	public void add(Recipe r) {
		recipes.add(r);
	}
}
