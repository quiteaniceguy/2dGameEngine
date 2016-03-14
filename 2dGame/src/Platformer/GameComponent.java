package Platformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class GameComponent extends JPanel{
	//delete this i think
	public static int ticks=0;
	public Rectangle box;

	private final int FPS=60;
	public int xMovement=0;
	public int yMovement=0;
	public final int A=0,D=1,S=2,W=3,SPACE=4;
	public boolean[] keyPressed=new boolean[5];
	private InputController keyListener;
	public ArrayList<Entity> Entities=new ArrayList<Entity>();
	HashMap sounds=new HashMap();
	
	public boolean drawLines=false;
	public int xAxisLines=40;
	public int yAxisLines=27;
	
	Quadtree quad=new Quadtree(0,new Rectangle(0,0,1920,1080));
	
	
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
				//movement();
				
				gameLoop();
				collisions();
				repaint();
				for(int i=0;i<4;i++){
					///System.out.println("size of node"+String.valueOf(i)+"  "+String.valueOf(quad.nodes[i].objects.size()));
				}
				ticks++;
				System.out.println(ticks);
				if(ticks==360 || ticks==1000){
					try{
						playSound("spun");
						
						
								
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			
		});
		timer.start();
	}
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d=(Graphics2D)g.create();
		//g2d.setColor(Color.RED);
		//g2d.fill(box);
		
		
		
		
		for(Entity entity: Entities){
			g2d.drawImage(entity.IMAGE, entity.getX(),entity.getY(), entity.IMAGE.getWidth(), entity.IMAGE.getHeight(), null);
			g2d.drawString(String.valueOf(entity.NAME), entity.getX(), entity.getY());
		}
		///draw lines in window if enabiled
		if(drawLines){
			drawLines(g2d);
		}
		///do collisions
			
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
			case KeyEvent.VK_SPACE:
				keyPressed[SPACE]=true;
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
			case KeyEvent.VK_SPACE:
				keyPressed[SPACE]=false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public abstract void gameLoop();
	public void drawLines(Graphics2D g2d){
		for(int i=0;i<1920;i+=1920/xAxisLines){
			g2d.drawLine(i, 0, i, 1080);
		}
		for(int i=0;i<1080;i+=1080/yAxisLines){
			g2d.drawLine(0, i, 1920, i);
		}
	}
	public void collisions(){
	////collisions!
	
		ArrayList<Entity> returnEntities=new ArrayList<Entity>();
		quad.clear();
		for(int i=0;i<Entities.size();i++){
			quad.insert(Entities.get(i));
		}

		for(int i=0;i <Entities.size();i++){
			returnEntities.clear();
			returnEntities=quad.retrieve(returnEntities,Entities.get(i));
			
			for(Entity e:returnEntities){
				//System.out.println(Entities.get(i).NAME+" could collide with "+e.NAME);
				if(Math.abs(Entities.get(i).getX()-e.getX())<= Entities.get(i).IMAGE.getWidth()/2+e.IMAGE.getWidth()/2 && Math.abs(Entities.get(i).getY()-e.getY())<= Entities.get(i).IMAGE.getHeight()/2+e.IMAGE.getHeight()/2 && Entities.get(i)!=e){
					System.out.println(Entities.get(i).NAME+" is colliding with "+e.NAME);
				}
			}
		}
			////collisions!
		/*
		System.out.println(String.valueOf(quad.getIndex(Entities.get(0))));
		System.out.println(Entities.get(0).bounds.x);
		System.out.println(Entities.get(0).bounds.y);
		*/
	}
	public void playSound(String fileName) throws IOException, 
	  UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		File clipFile=new File("assets\\"+fileName+".wav");
		class AudioListener implements LineListener {
		    private boolean done = false;
		    @Override public synchronized void update(LineEvent event) {
		      Type eventType = event.getType();
		      if (eventType == Type.STOP || eventType == Type.CLOSE) {
		        done = true;
		        notifyAll();
		      }
		    }
		    public synchronized void waitUntilDone() throws InterruptedException {
		      while (!done) {}
		    }
		}
		class SoundThread implements Runnable  {
			AudioListener listener = new AudioListener();
		    AudioInputStream audioInputStream;
		    public SoundThread() throws UnsupportedAudioFileException, IOException{
			  audioInputStream = AudioSystem.getAudioInputStream(clipFile);
		    }

		  @Override
		  public void run() {
			  try{
				  Clip clip=AudioSystem.getClip();
				  clip.addLineListener(listener);
				  clip.open(audioInputStream);
				  try{
					  clip.start();
					  listener.waitUntilDone();
				  }
				  finally{
					  clip.close();
				  }
			  }
			  catch(Exception e){
				  e.printStackTrace();
			  }
			  finally{
				  try {
			  		  audioInputStream.close();
				  } catch (IOException e) {
					  e.printStackTrace();
				  }
			  }
		  }
		  
	    }
		Thread thread=new Thread(new SoundThread());
		thread.start();
	}
	
}
