package Platformer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameComponent extends JPanel{
	private Rectangle box;

	private final int SPEED=5;
	private final int FPS=60;
	public int xMovement=0;
	public int yMovement=0;
	private final int A=0,D=1,S=2,W=3;
	private boolean[] keyPressed=new boolean[4];
	private InputController keyListener;
	
	public GameComponent(){
		keyListener=new InputController();
		box=new Rectangle(90,90,20,20);
		setFocusable(true);
		requestFocusInWindow(true);
		run();
		
	}
	public void run(){
		addKeyListener(keyListener);
		
		Timer timer=new Timer(1000/FPS, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				movement();
				repaint();
				
			}
			
		});
		timer.start();
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g.create();
		g2d.setColor(Color.RED);
		g2d.fill(box);
		g2d.dispose();
	}
	class InputController implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()){
			case KeyEvent.VK_W:
				keyPressed[W]=true;
				break;
			case KeyEvent.VK_S:
				keyPressed[S]=true;
				break;
			case KeyEvent.VK_A:
				keyPressed[A]=true;
				break;
			case KeyEvent.VK_D:
				keyPressed[D]=true;
				break;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			switch(e.getKeyCode()){
			case KeyEvent.VK_W:
				keyPressed[W]=false;
				break;
			case KeyEvent.VK_S:
				keyPressed[S]=false;
				break;
			case KeyEvent.VK_A:
				keyPressed[A]=false;
				break;
			case KeyEvent.VK_D:
				keyPressed[D]=false;
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public void movement(){
		if(keyPressed[D]){
			box.x+=SPEED;
		}
		if(keyPressed[A]){
			box.x-=SPEED;
		}
		if(keyPressed[W]){
			box.y-=SPEED;
		}
		if(keyPressed[S]){
			box.y+=SPEED;
		}
	}
}
