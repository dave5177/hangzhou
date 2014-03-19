package com.dave.worldWar.model;

import javax.microedition.lcdui.Graphics;

import com.dave.worldWar.tool.C;
import com.dave.worldWar.view.Game;

public class Bomber {
	
	private Game game;
	
	/**
	 * ÊÀ½ç×ø±ê
	 */
	int x, y = 300;
	
	private final int speed_x = 20;
	
	public Bomber(Game game) {
		this.game = game;
		game.x_map = 0;
	}

	public void show(Graphics g) {
		g.drawImage(game.img_bomber, x + game.x_map, y, Graphics.BOTTOM | Graphics.HCENTER);
	}
	
	public void logic() {
		move();
//		if(x > 400)
			bomb();
	}
	
	public void move() {
		x += speed_x;
		
		if(x + game.x_map >= 320 && x + game.x_map <= 640)
			game.x_map -= speed_x;
		
		if(game.x_map <= -320)
			game.x_map = -320;
		
		if(x > 1000) {
			game.bomberView = false;
			game.bomber = null;
			game.removeBomberImage();
			
			System.gc();
		}
	}
	
	public void bomb() {
		int number = C.R.nextInt(2);
		for (int i = 0; i < number; i++) {
			int x_t = C.R.nextInt(40);
			int y_t = C.R.nextInt(100) + 200;
			
			game.v_bomb.addElement(new Bomb(x_t + x - 20, y_t, speed_x, game));
		}
	}
}
