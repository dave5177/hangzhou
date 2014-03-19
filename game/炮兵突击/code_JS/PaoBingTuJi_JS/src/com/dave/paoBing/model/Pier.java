package com.dave.paoBing.model;

import javax.microedition.lcdui.Graphics;

import com.dave.paoBing.view.Game;

public class Pier {
	
	/**
	 * ���ڵ���Ϸ
	 */
	private Game game;
	
	/**
	 * x��y��������ڵ�ͼ
	 */
	public int x, y;
	
	/**
	 * ��ײ��Ⱥ͸߶�
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
