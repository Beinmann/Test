package main.adapters;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public class KeyInput extends KeyAdapter{
	
	private LinkedList<Adapter> adapters = new LinkedList<Adapter>();
	private boolean comodification = false;
	
	
	public void add(Adapter object) {
		while(comodification) {}
		comodification = true;
		adapters.add(object);
		comodification = false;
	}
	
	public void remove(Adapter object) {
		while(comodification) {}
		comodification = true;
		adapters.remove(object);
		comodification = false;
	}
	
	public void keyPressed(KeyEvent e) {
		while(comodification) {}
		comodification = true;
		LinkedList<Adapter> temp = new LinkedList<Adapter>();
		comodification = false;
		temp.addAll(adapters);
		for(Adapter cur : temp) {
				cur.keyPressed(e);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		while(comodification) {}
		comodification = true;
		LinkedList<Adapter> temp = new LinkedList<Adapter>();
		comodification = false;
		temp.addAll(adapters);
		for(Adapter cur : temp) {
				cur.keyReleased(e);
		}
		
	}
	
}
