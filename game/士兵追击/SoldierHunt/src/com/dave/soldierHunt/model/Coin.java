package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Coin {
	/**
	 * ¼ÛÖµ
	 */
	public int value;
	public final int x;
	public final int y;
	public final Game game;
	private int index;
	
	public Coin(int value, int x, int y, Game game) {
		super();
		this.value = value;
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void show(Graphics g) {
		g.drawImage(ImageManager.ima_coin[index], x + game.mapX, game.mapY + y - 759, Graphics.HCENTER | Graphics.VCENTER);
	}
	
	public void logic() {
		index = index >= 1 ? 0 : 1;
	}
}
