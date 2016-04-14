package Platformer;

import java.awt.Polygon;
import java.util.ArrayList;

public class Polygon2D extends Polygon{
	
	public Vector2D returnEdge(int i){
		if(i==npoints-1){
			return new Vector2D(xpoints[i]-xpoints[1],ypoints[i]-ypoints[1]);
			
		}
		if(i>npoints){
			return new Vector2D(0,0);
		}
		return new Vector2D(xpoints[i]-xpoints[i+1],ypoints[i]-ypoints[i+1]);
	}
}
