package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.view.Game;

/**
 * @author Administrator
 * �ϰ�����
 */
public class Obstacle {
	/**
	 * 1~8�����͡�1~8��ʾ��
	 */
	public int type;
	
	/**
	 * xֵ������ڵ�ͼ
	 */
	public final int x;
	
	/**
	 * yֵ������ڵ�ͼ��
	 */
	public final int y;

	/**
	 * �ϰ�����������Ϸ
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
