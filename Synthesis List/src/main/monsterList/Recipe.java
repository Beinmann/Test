package main.monsterList;

public class Recipe {

	private Monster monster1, monster2, res;
	private boolean preferredRecipe;
	
	public Recipe(Monster res, Monster monster1, Monster monster2, boolean preferredRecipe) {
		this.res = res;
		this.monster1 = monster1;
		this.monster2 = monster2;
		this.preferredRecipe = preferredRecipe;
	}
	
	public Monster getFst() {
		return monster1;
	}
	
	public Monster getSnd() {
		return monster2;
	}
	
	public void setFst(Monster monster1) {
		this.monster1 = monster1;
	}
	
	public void setSnd(Monster monster2) {
		this.monster2 = monster2;
	}
	
	public boolean isPreferredRecipe() {
		return preferredRecipe;
	}

	public void setPreferredRecipe(boolean preferredRecipe) {
		this.preferredRecipe = preferredRecipe;
	}

	public Monster getRes() {
		return res;
	}

	public void setRes(Monster res) {
		this.res = res;
	}
}
