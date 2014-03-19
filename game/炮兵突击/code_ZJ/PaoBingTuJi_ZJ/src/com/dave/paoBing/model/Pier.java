package com.dave.paoBing.model;

import javax.microedition.lcdui.Graphics;

import com.dave.paoBing.view.Game;

public class Pier {
	
	/**
	 * 所在的游戏
	 */
	private Game game;
	
	/**
	 * x和y坐标相对于地图
	 */
	public int x, y;
	
	/**
	 * 碰撞宽度和高度
	 */
	public static int width_cln, height_cln;

	public Pier(Game game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public void show(Graphics g) {
		g.drawImage(game.img_pier, x + game.x_map, y, Graphics.BOTTOM | Graphics.HCENTER);
	}
}
