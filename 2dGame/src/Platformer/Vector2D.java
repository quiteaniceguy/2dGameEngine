package Platformer;

import java.awt.Point;

public class Vector2D {
	int x;
	int y ;
	public Vector2D(){
		x=0;
		y=0;
	}
	public Vector2D(int x, int y){
		this.x=x;
		this.y=y;
	}
	public double dotProduct(Point point){
		return x*point.getX()+y*point.getY();
	}
	public void normalize(){
		double d=Math.sqrt(x^2+y^2);
		x/=d;
		y/=d;
	}
}
