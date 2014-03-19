package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

public class Bullet implements Model {
	
	/**
	 * 底部中间的坐标
	 */
	public int x, y;
	
	/**
	 * 类型0~4，英雄的子弹。-1：宠物的子弹
	 */
	public int type;
	
	/**
	 * 攻击力
	 */
	public int power;
	
	/**
	 * 碰撞矩形
	 */
	public Rect rect_clsn;

	/**
	 * 所处游戏
	 */
	private Game game;

	/**
	 * 飞行速度相对于屏幕
	 */
	private static final int SPEED_FLY = 30;

	public Bullet(Game game, int x, int y, int type) {
		this.game = game;
		this.x = x;
		this.y = y - 20;
		this.type = type;
		setParam();
	}

	/**
	 * 设置碰撞矩形、攻击力等参数
	 */
	private void setParam() {
		switch (type) {
		case 0:
		case -1:
			rect_clsn = new Rect(x - 6, y - 40, 12, 35);
			break;
		case 1:
			rect_clsn = new Rect(x - 12, y - 48, 24, 40);
			break;
		case 2:
			rect_clsn = new Rect(x - 15, y - 47, 30, 40);
			break;
		case 3:
			rect_clsn = new Rect(x - 22, y - 58, 44, 45);
			break;
		default:
			rect_clsn = new Rect(x - 22, y - 58, 44, 45);
			break;
		}
		
		if(type == -1) {
			power = 64;
		} else {
			power = (type + 1) * 40;
		}
	}

	public void show(Graphics g) {
		if(type == -1) {
			g.drawImage(game.a_img_bullet[0], x, y, Graphics.BOTTOM | Graphics.HCENTER);
		} else {
			int type_show = type;
			if(type > 4)
				type_show  = 4;
			g.drawImage(game.a_img_bullet[type_show], x, y, Graphics.BOTTOM | Graphics.HCENTER);
		}
//		g.drawRect(rect_clsn.x, rect_clsn.y, rect_clsn.w, rect_clsn.h);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		y -= SPEED_FLY;
		rect_clsn.y -= SPEED_FLY;
		if(y < 0)
			removeMe();
		
		hit();
	}

	private void hit() {
		int size_v = game.v_bird.size();
		for (int i = size_v - 1; i >= 0; i--) {
			Bird bird = (Bird)game.v_bird.elementAt(i);
			if(bird.life > 0 && C.rectInsect(rect_clsn, bird.rect_clsn)) {
				bird.beHit(this);
				power -= bird.life;
				if(power <= 0) {
					power = 0;
					removeMe();
					return;
				}
			}
		}
	}

	private void removeMe() {
		game.v_bullet.removeElement(this);
	}

	public void fire() {
		// TODO Auto-generated method stub

	}

}
