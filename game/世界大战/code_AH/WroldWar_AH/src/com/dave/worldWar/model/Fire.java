package com.dave.worldWar.model;

import javax.microedition.lcdui.Graphics;

import com.dave.worldWar.tool.C;
import com.dave.worldWar.view.Game;

public class Fire {
	int x, y;

	/**
	 * 所在游戏
	 */
	private Game game;

	/**
	 * 爆炸效果动画帧
	 */
	private int frame;

	public Fire(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		this.game = game;
		frame = C.R.nextInt(4);
	}

	public void show(Graphics g) {
		g.drawImage(game.a_img_burning[frame], x + game.x_map, y + game.y_map, Graphics.HCENTER | Graphics.BOTTOM);
	}

	public void logic() {
		frame ++;
		if(frame > 3)
			frame = 0;
	}
}
