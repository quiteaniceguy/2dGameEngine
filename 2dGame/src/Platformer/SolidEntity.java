package Platformer;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.Vector;


public class SolidEntity {
	Entity entity;
	private double mass=Double.POSITIVE_INFINITY;
	private boolean gravity=false;
	public SolidEntity(String name, String image, int x, int y){
		entity=new Entity(null, null, 0, 0);
	}
	public boolean isColliding(Entity entity){
		
	}
	public class PolygonCollisionResult{
		public boolean WillIntersect;
		public boolean Intersect;
		public Vector minimumTranslationVector;
		
	}
	///loops through all the vertexes own the polygon and projects onto given axis
	public int[] ProjectPolygon(Vector2D axis, Polygon polygon){
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
	}
	
	public float IntervalDistance(float minA, float maxA, float minB, float maxB) {
		///same a total value
	    if (minA < minB) {
	        return minB - maxA;
	    } else {
	        return minA - maxB;
	    }
	}
	
	public PolygonCollisionResult PolygonCollision(Polygon2D polygonA, Polygon2D polygonB, Vector velocity) {
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
			
			int[] minmaxA=ProjectPolygon(axis, polygonA);
			minA=minmaxA[0];
			maxA=minmaxA[1];
			
			int[] minmaxB=ProjectPolygon(axis, polygonB);
			minB=minmaxB[0];
			maxB=minmaxB[1];
			
			// Check if the polygon projections are currentlty intersecting
			if (IntervalDistance(minA, maxA, minB, maxB) > 0)
				result.Intersect = false;
				
				// ===== 2. Now find if the polygons *will* intersect =====
				
				// Project the velocity on the current axis]
				//YOU CAN PROJECT A FLOAT ONTO A AXIS?!
				float velocityProjection = axis.dotProduct(velocity);
			
				// Get the projection of polygon A during the movement
				if (velocityProjection < 0) {
					
				minA += velocityProjection;
				} else {
				maxA += velocityProjection;
				}
			
				// Do the same test as above for the new projection
				float intervalDistance = IntervalDistance(minA, maxA, minB, maxB);
				if (intervalDistance > 0) result.WillIntersect = false;
				
				// If the polygons are not intersecting and won't intersect, exit the loop
				if (!result.Intersect && !result.WillIntersect) break;
				
				// Check if the current interval distance is the minimum one. If so store
				// the interval distance and the current distance.
				// This will be used to calculate the minimum translation vector
			intervalDistance = Math.Abs(intervalDistance);
			if (intervalDistance < minIntervalDistance) {
			minIntervalDistance = intervalDistance;
			translationAxis = axis;
			
			Vector d = polygonA.Center - polygonB.Center;
			if (d.dotProduct(translationAxis) < 0)
			translationAxis = -translationAxis;
			}
			}
			
			// The minimum translation vector
			// can be used to push the polygons appart.
			if (result.WillIntersect)
			result.MinimumTranslationVector = 
			translationAxis * minIntervalDistance;
			
			return result;
}
	
	
	
	
}
