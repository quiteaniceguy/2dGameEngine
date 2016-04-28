package Platformer;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Vector;


public class SolidEntity extends Entity{
	Entity entity;
	private double mass=Double.POSITIVE_INFINITY;
	private boolean gravity=false;
	
	public SolidEntity(String name, String image, int x, int y){
		super(name, image, x, y);
	}

	
}
