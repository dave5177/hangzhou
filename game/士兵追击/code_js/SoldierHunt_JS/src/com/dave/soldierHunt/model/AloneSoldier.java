package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

/**
 * @author Administrator
 * 单独的士兵，等着被救
 */
public class AloneSoldier {
	/**
	 * 士兵类型
	 */
	public int type;
	
	public final int x;
	public final int y;
	public final Game game;

	private int helpY;
	private int helpControl = 1;

	/**
	 * 时间
	 */
	private int time;
	
	public AloneSoldier(int type, int x, int y, Game game) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.game = game;
		
		helpY = y - 50;
	}
	
	public void show(Graphics g) {
		g.drawImage(ImageManager.img_shadow, x + game.mapX, game.mapY + y - 759 + 25, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(ImageManager.ima_hero_move[type][3][0], x + game.mapX, game.mapY + y - 759, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(game.img_help, x + game.mapX, game.mapY + helpY - 759, Graphics.HCENTER | Graphics.VCENTER);
	}
	
	public void logic() {
		if(!game.pressOk) {
			helpControl *= -1;
			helpY += helpControl * 5;
		}
		
		autoDisappear();
	}

	/**
	 * 自动消失
	 */
	public void autoDisappear() {
		time ++;
		if(time > 200)
			game.aloneSoldiers.removeElement(this);
	}
}
