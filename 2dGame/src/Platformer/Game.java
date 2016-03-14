package Platformer;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Game {
	public static void main(String args[]){
		JFrame frame=new JFrame();
		GameComponent gp=new MainGame();
		frame.add(gp);
		frame.pack();
		frame.setSize(1920, 1080);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
