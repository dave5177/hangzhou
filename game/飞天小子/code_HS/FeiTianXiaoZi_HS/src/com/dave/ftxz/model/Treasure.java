package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

/**
 * @author 戴维
 * 宝物道具
 */
public class Treasure implements Model {
	
	/**
	 * 所在游戏
	 */
	Game game;
	
	/**
	 * 坐标
	 */
	int x, y;
	
	/**
	 * 类型（0~3：减速、冲刺、无敌、吸铁石）
	 */
	int type;
	
	/**
	 * 碰撞矩形
	 */
	public Rect rect_clsn;

	/**
	 * y速度
	 */
	private int speed_y;

	/**
	 * 加速度方向（0向下，1向上）
	 */
	private int dir_a;

	/**
	 * @param game 所在游戏
	 * @param x 坐标
	 * @param y 坐标
	 * @param type 类型（0~3：减速、冲刺、无敌、吸铁石）
	 */
	public Treasure(Game game, int x, int y, int type) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;
		
		rect_clsn = new Rect(x - 23, y - 46, 46, 46);
		speed_y = 30;
		dir_a = 1;
	}

	public void show(Graphics g) {
		g.drawImage(game.a_img_treasure[type], x, y, Graphics.HCENTER | Graphics.BOTTOM);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		fire();
		falling();
	}

	/**
	 * 掉落
	 */
	private void falling() {
		y += speed_y;
		rect_clsn.y += speed_y;
		
		if(dir_a == 1) {
			speed_y -= 10;
			if(y < 0)
				dir_a = 0;
		} else {
			if(speed_y < 80)
				speed_y += 20;
		}
		
		if(y > 600)
			removeMe();
	}

	public void fire() {
		if(C.rectInsect(rect_clsn, game.hero.rect_clsn)) {//被吃到
			game.hero.getTreasure(type);
			removeMe();
		}
	}

	private void removeMe() {
		game.v_treasure.removeElement(this);
	}

}
