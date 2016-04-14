package Platformer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Entity {
	public final String NAME;
	public BufferedImage IMAGE;
	private int x=0;
	private int y=0;
	private int lasty=0;
	private int lastx=0;
	public Entity(String name, String image,int x, int y){
		NAME=name;
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
		lastx=this.x;
		this.x+=x;
	}
	public void changeY(int y){
		lasty=this.y;
		this.y+=y;
	}
	public void setX(int x){
		lastx=this.x;
		this.x=x;
	}
	public void setY(int y){
		lasty=this.y;
		this.y=y;
	}
	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	public int getLastY(){
		return lasty;
	}
	public int getLastX(){
		return lastx;
	}
	
	
}
