package Platformer;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

//import Platformer.SolidEntity.PolygonCollisionResult;

public class Entity {
	public final String NAME;
	public BufferedImage IMAGE;
	private int x=0;
	private int y=0;
	private int lasty=0;
	private int lastx=0;
	private Polygon2D polygon;
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
		int[] xpoints={x,x+IMAGE.getWidth(), x+IMAGE.getWidth(),x};
		int[] ypoints={y,y,y+IMAGE.getHeight(),y+IMAGE.getHeight()};
		polygon=new Polygon2D(xpoints, ypoints, xpoints.length);
		
		
		
		
		
	}
	
	private void updatePolygon(int x, int y){
		polygon.xpoints[0]=x;
		polygon.xpoints[1]=x+IMAGE.getWidth();
		polygon.xpoints[2]=x+IMAGE.getWidth();
		polygon.xpoints[3]=x;
		
		polygon.ypoints[0]=y;
		polygon.ypoints[1]=y;
		polygon.ypoints[2]=y+IMAGE.getHeight();
		polygon.ypoints[3]=y+IMAGE.getHeight();
	}
	
	///this is bad, should use vectors, too much repeat
	public void changeX(int x){
		lastx=this.x;
		this.x+=x;
		updatePolygon(this.x, this.y);
		
	}
	public void changeY(int y){
		lasty=this.y;
		this.y+=y;
		updatePolygon(this.x, this.y);
	}
	public void setX(int x){
		lastx=this.x;
		this.x=x;
		updatePolygon(this.x, this.y);
	}
	public void setY(int y){
		lasty=this.y;
		this.y=y;
		updatePolygon(this.x, this.y);
	}
	public int getY(){
		return y;
	}
	public int getX(){
		return x;
	}
	public Polygon2D getPolygon(){
		return polygon;
	}
	public int getLastY(){
		return lasty;
	}
	public int getLastX(){
		return lastx;
	}
	
	public class PolygonCollisionResult{
		public boolean WillIntersect;
		public boolean Intersect;
		public Vector2D minimumTranslationVector;
		
	}
	///loops through all the vertexes own the polygon and projects onto given axis
	public double[] ProjectPolygon(Vector2D axis, Polygon polygon, double d){
		double min;
		double max;
		//first vertex
		Point point=new Point(polygon.xpoints[0],polygon.ypoints[0]);
		double dotProduct = axis.dotProduct(point);
	    min = dotProduct;
	    max = dotProduct;
	    //loops through vertxes
	    for (int i = 0; i < polygon.npoints; i++) {
	    	point.setLocation(polygon.xpoints[i], polygon.ypoints[i]);
	        dotProduct=axis.dotProduct(point);
	        ///d is defined elsewhere
	        if (d < min) {
	            min = dotProduct;
	        } else {
	            if (dotProduct> max) {
	                max = dotProduct;
	            }
	        }
	    }
	    double[] returnValue=new double[2];
	    returnValue[0]=min;
	    returnValue[1]=max;
	    
	    return returnValue;
	}
	
	public float IntervalDistance(float minA, float maxA, float minB, float maxB) {
		///if greater than 0, isn't colliding
	    if (minA < minB) {
	        return minB - maxA;
	    } else {
	        return minA - maxB;
	    }
	}
	
	public PolygonCollisionResult PolygonCollision( Polygon2D polygonB) {
		Polygon2D polygonA=getPolygon();
		double d=Math.sqrt((polygonA.getCenter().y-polygonB.getCenter().y)^2+(polygonA.getCenter().x-polygonB.getCenter().x)^2);
		PolygonCollisionResult result = new PolygonCollisionResult();
		result.Intersect = true;
		result.WillIntersect = true;
			
		int edgeCountA = polygonA.npoints;
		int edgeCountB = polygonB.npoints;
			
		double minIntervalDistance = Double.POSITIVE_INFINITY;
			
		Vector2D translationAxis = new Vector2D();
		Vector2D edge;
			
		// Loop through all the edges of both polygons
		for (int edgeIndex = 0; edgeIndex < edgeCountA + edgeCountB; edgeIndex++) {
			if (edgeIndex < edgeCountA) {
				edge = polygonA.returnEdge(edgeIndex);
			} else {
				edge = polygonB.returnEdge(edgeIndex - edgeCountA);
			}
			// ===== 1. Find if the polygons are currently intersecting =====
				
			// Find the axis perpendicular to the current edge
			Vector2D axis = new Vector2D(-edge.y, edge.x);
			axis.normalize();
				
			// Find the projection of the polygon on the current axis
			float minA = 0; float minB = 0; float maxA = 0; float maxB = 0;
			
			double[] minmaxA=ProjectPolygon(axis, polygonA, d);
			minA=(int)minmaxA[0];
			maxA=(int)minmaxA[1];
							
			double[] minmaxB=ProjectPolygon(axis, polygonB,  d);
			minB=(int)minmaxB[0];
			maxB=(int)minmaxB[1];
				
			// Check if the polygon projections are currentlty intersecting
			if (IntervalDistance(minA, maxA, minB, maxB) > 0)result.Intersect = false;
		}
				
		return result;
	}
	
	
	
	
}
