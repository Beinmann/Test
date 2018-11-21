package main.adapters;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;


public class MouseInput extends MouseAdapter{
	
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
	
	@Override
	public void mousePressed(MouseEvent e) {
		while(comodification) {}
		comodification = true;
		LinkedList<Adapter> temp = new LinkedList<Adapter>();
		comodification = false;
		temp.addAll(adapters);
		for(Adapter cur : temp) {
				cur.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		while(comodification) {}
		comodification = true;
		LinkedList<Adapter> temp = new LinkedList<Adapter>();
		comodification = false;
		temp.addAll(adapters);
		for(Adapter cur : temp) {
				cur.mouseReleased(e);
		}
		
	}
	
}
