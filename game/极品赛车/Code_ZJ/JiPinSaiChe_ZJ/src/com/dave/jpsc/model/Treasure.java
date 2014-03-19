package com.dave.jpsc.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.tool.Rect;
import com.dave.jpsc.view.Game;

/**
 * @author Dave 宝物
 */
public class Treasure implements Model {

	private Game game;
	private int x;
	private int y;

	/**
	 * 类型： 9 解除状态； 10 补充时间； 11 急速模式； 12 补充燃料； 13减速攻击； 14 致盲攻击
	 */
	public int type;

	private Image img;

	public Rect rect;
	
	private int y_show;

	/**
	 * 宝物
	 * 
	 * @param game
	 *            所在游戏
	 * @param x
	 *            中心点x坐标
	 * @param y
	 *            中心点y坐标
	 * @param type
	 *            类型： 9 解除状态； 10 补充时间； 11 急速模式； 12 补充燃料； 13减速攻击； 14 致盲攻击
	 */
	public Treasure(Game game, int x, int y, int type) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;

		init();
	}

	private void init() {
		rect = new Rect(x - 15, y - 15, 30, 30);

		try {
			img = Image.createImage("/treasure/" + type + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics g) {
		if(y_show == y)
			y_show = y - 2;
		else
			y_show = y;
		
		g.drawImage(img, x + game.x_map, y_show + game.y_map, Graphics.HCENTER
				| Graphics.VCENTER);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {

	}

	public void fire() {
		// TODO Auto-generated method stub

	}

	/**
	 * 被吃了
	 */
	public void beEta() {
		game.treasures.removeElement(this);
		img = null;
	}

}
