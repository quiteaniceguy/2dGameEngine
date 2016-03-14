package Platformer;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Quadtree {
	private int MAX_OBJECTS=10;
	private int MAX_LEVElS=5;
	
	private int level;
	public ArrayList<Entity> objects;
	private Rectangle bounds;
	public Quadtree[] nodes;
	
	
	
	public Quadtree(int pLevel, Rectangle pBounds){
		level=pLevel;
		objects=new ArrayList<Entity>();
		bounds=pBounds;
		nodes=new Quadtree[4];
		
	}
	
	public void clear(){
		objects.clear();
		
		for(int i=0; i<nodes.length;i++){
			if(nodes[i]!=null){
				nodes[i].clear();
				nodes[i]=null;
			}
		}
	}
	private void split(){
		int subWidth=(int)(bounds.getWidth()/2);
		int subHeight=(int)(bounds.getHeight()/2);
		int x=(int)bounds.getX();
		int y=(int)bounds.getY();
		nodes[0]=new Quadtree(level+1,new Rectangle(x+subWidth,y,subWidth,subHeight));
		nodes[1]=new Quadtree(level+1,new Rectangle(x,y,subWidth,subHeight));
		nodes[2]=new Quadtree(level+1,new Rectangle(x,y+subHeight,subWidth,subHeight));
		nodes[3]=new Quadtree(level+1,new Rectangle(x+subWidth,y+subHeight,subWidth,subHeight));
	}
	
	public int getIndex(Entity entity){
		Rectangle pRect=new Rectangle(entity.getX(),entity.getY(),entity.IMAGE.getWidth(),entity.IMAGE.getHeight());
		
		
		int index=-1;
		double verticalMidpoint=bounds.getX()+(bounds.getWidth()/2);
		double horizontalMidpoint=bounds.getY()+(bounds.getHeight()/2);
		
		//object can completely fit wihtin the top quadrant
		boolean topQuadrant=(pRect.getY()<horizontalMidpoint && pRect.getY()+pRect.getHeight()<horizontalMidpoint);
		boolean bottomQuadrant=(pRect.getY()>horizontalMidpoint);
		
		if(pRect.getX()<verticalMidpoint && pRect.getX()+pRect.getWidth()<verticalMidpoint){
			if(topQuadrant){
				index=1;
				
			}
			else if(bottomQuadrant){
				index=2;
			}
		}
		
		if(pRect.getX()>verticalMidpoint ){
			if(topQuadrant){
				index=0;
			}
			else if(bottomQuadrant){
				index=3;
			}
		}
		/*
		System.out.println(verticalMidpoint);
		System.out.println(horizontalMidpoint);
		*/
		
		return index;
	}
	public void insert(Entity entity){
		Rectangle pRect=new Rectangle(entity.getX(),entity.getY(),entity.IMAGE.getWidth(),entity.IMAGE.getHeight());			
		if(nodes[0] !=null){
			int index=getIndex(entity);
			
			if(index !=-1){
				nodes[index].insert(entity);
				
				return;
			}
		}
		
		objects.add(entity);
		
		if(objects.size() > MAX_OBJECTS && level <MAX_LEVElS){
			if(nodes[0]==null){
				split();
			}
			int i=0;
			while(i < objects.size()){
				int index=getIndex(objects.get(i));
				if(index!=-1){
					nodes[index].insert(objects.get(i));
					///next rectangle moves to zero position when other object is remvoed
					objects.remove(i);
				}
				else{
					i++;
				}
			}
		}
	}
	public ArrayList<Entity> retrieve(ArrayList<Entity> returnObjects, Entity entity){
		Rectangle pRect=new Rectangle(entity.getX(), entity.getY(),entity.IMAGE.getWidth(),entity.IMAGE.getHeight());
		int index=getIndex(entity);
		if(index!=-1 && nodes[0] !=null){
			///keeps retrieving untill nodes[0] is null, which means there are no more subnodes
			nodes[index].retrieve(returnObjects, entity);
		}
		///returns all objects in its current class, which will only have entities in the same cell 
		returnObjects.addAll(objects);
		return returnObjects;
	}
	public String getLevel(){
		return String.valueOf(level);
	}
}
