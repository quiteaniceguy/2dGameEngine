package Platformer;

import java.util.Random;

public class MainGame extends GameComponent{
	public MainGame(){
		super();
		Random random=new Random();
		for(int i=0;i<15;i++){
			Player_Entity player=new Player_Entity("player"+String.valueOf(i),random.nextInt(1920),random.nextInt(1080));
			Entities.add(player);
		}
		xAxisLines=2;
		yAxisLines=2;
		drawLines=true;
	}

	@Override
	public void gameLoop() {
		/*
		if(quad.nodes[0]==null){
			System.out.println("the node weren't created");
		}
		else{
			System.out.println(quad.nodes[0].objects.size());
			System.out.println(quad.nodes[1].objects.size());
			System.out.println(quad.nodes[2].objects.size());
			System.out.println(quad.nodes[3].objects.size());
		}
		*/
		
		if(keyPressed[A]){
			Entities.get(0).changeX(-2);
		}
		
		if(keyPressed[D]){
			Entities.get(0).changeX(2);
		}
		
		if(keyPressed[W]){
			Entities.get(0).changeY(-2);
		}
		
		if(keyPressed[S]){
			Entities.get(0).changeY(2);
		}
	}
	
}

