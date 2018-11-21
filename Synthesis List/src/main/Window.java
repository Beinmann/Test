package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {
	
	private static final long serialVersionUID = -4810618286807932601L;
	
	public Window(Synthesis synthesis) {
		javax.swing.JFrame frame = new JFrame(Synthesis.TITLE);
		
		frame.setPreferredSize(new Dimension(Synthesis.WIDTH,Synthesis.HEIGHT));
		frame.setMaximumSize(new Dimension(Synthesis.WIDTH,Synthesis.HEIGHT));
		frame.setMinimumSize(new Dimension(Synthesis.WIDTH,Synthesis.HEIGHT));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(synthesis);
		frame.setVisible(true);
	}
	
}
