package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.view.Game;

/**
 * @author Administrator
 * 障碍物类
 */
public class Obstacle {
	/**
	 * 1~8种类型。1~8表示。
	 */
	public int type;
	
	/**
	 * x值，相对于地图
	 */
	public final int x;
	
	/**
	 * y值，相对于地图。
	 */
	public final int y;

	/**
	 * 障碍物所处的游戏
	 */
	private Game game;
	
	public Obstacle(int type, int x, int y, Game game) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void show(Graphics g) {
		g.drawImage(game.ima_obstacle[type - 1], x + game.mapX, game.mapY + y - 759, Graphics.HCENTER | Graphics.VCENTER);
	}
	
}
