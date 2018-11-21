package main.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import main.Synthesis;
import main.monsterList.Monster;
import main.monsterList.Recipe;

public class MonsterListWriter {

	private String monsterListLocation = Synthesis.MONSTER_LIST_LOCATION;
	private String synthesisListLocation = Synthesis.SYNTHESIS_LIST_LOCATION;
	private Synthesis syn;
	
	public MonsterListWriter(Synthesis syn) {
		this.syn = syn;
	}
	
	public void writeMonsterList() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(monsterListLocation,false);
			bw = new BufferedWriter(fw);
			
			
			for(Monster cur : syn.getMonsters()) {
				bw.write(cur.getName() + "|" + cur.getType() + "|" + cur.getRank());
				bw.newLine();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(bw != null) {
				bw.close();
			}
			if(fw != null) {
				fw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

	public void writeRecipes() {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(synthesisListLocation,false);
			bw = new BufferedWriter(fw);
			
			
			for(Recipe cur : syn.getRecipes()) {
				bw.write(cur.getRes().getName() + "|" + cur.getFst().getName() + "|" + cur.getSnd().getName() + "|" + cur.isPreferredRecipe());
				bw.newLine();
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(bw != null) {
				bw.close();
			}
			if(fw != null) {
				fw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
