package Platformer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Entity {
	public final String NAME;
	public BufferedImage IMAGE;
	public boolean gravity;
	private int x=0;
	private int y=0;
	public Entity(String name, String image,boolean gravity,int x, int y){
		NAME=name;
		this.gravity=gravity;
		this.x=x;
		this.y=y;
		try{
			IMAGE=ImageIO.read(new File(image));
		}
		catch(Exception ex){
			IMAGE=null;
			ex.printStackTrace();
		}
		
	}
	
	public void changeX(int x){
		this.x+=x;
	}
	public void changeY(int y){
		this.y+=y;
	}
	public void setX(int x){
		this.x=x;
	}
	public void setY(int y){
		this.y=y;
	}
	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	
	
}
