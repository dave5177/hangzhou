package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

/**
 * @author Administrator
 * ������ʿ�������ű���
 */
public class AloneSoldier {
	/**
	 * ʿ������
	 */
	public int type;
	
	public final int x;
	public final int y;
	public final Game game;

	private int helpY;
	private int helpControl = 1;

	/**
	 * ʱ��
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
	 * �Զ���ʧ
	 */
	public void autoDisappear() {
		time ++;
		if(time > 200)
			game.aloneSoldiers.removeElement(this);
	}
}
