package com.dave.paoBing.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.view.Game;

/**
 * @author Administrator 敌人的子弹
 */
public class Bullet {
	/**
	 * 子弹弹的坐标 ,子弹中部。
	 */
	int x, y;

	/**
	 * 子弹的目标坐标
	 */
	int x_point, y_point;
	
	/**
	 * 开始的位置
	 */
	int x_start, y_start;

	/**
	 * 炸弹所处的游戏
	 */
	private Game game;

	/**
	 * 图片
	 */
	private Image img_bullet;

	/**
	 * 炮弹的攻击力
	 */
	private int power;

	/**
	 * 1,2,3分别代表1,2,3敌人产生的子弹
	 */
	private int type;

	public Bullet(int x, int y, int x_point, int y_point, Game game, int type) {
		this.x_start = x;
		this.y_start = y;
		this.x_point = x_point;
		this.y_point = y_point;
		this.game = game;
		this.type = type;

		init();
	}

	private void init() {
		if (type == 1) {
			power = 30;
		} else if (type == 2) {
			power = 50;
		} else if (type == 3) {
			power = 80;
		}
		x = x_start;
		y = y_start;
		
		initImage();
	}

	private void initImage() {
		try {
			if (type == 1 || type == 3) {
				img_bullet = Image.createImage("/enemy/bullet_small.png");
			} else {
				img_bullet = Image.createImage("/enemy/bullet_big.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics g) {
		g.setClip(0, 0, 645, 485);
		g.drawImage(img_bullet, x + game.x_map, y, Graphics.HCENTER | Graphics.VCENTER);
		g.setClip(0, 0, 645, 535);
	}

	/**
	 * 移动方法
	 */
	public void move() {
		x += (x_point - x_start) / 8;
		y += (y_point - y_start) / 8;
		
		if(x + game.x_map <= 320 && game.x_map < 0)
			game.x_map -= (x_point - x_start) / 8;
		
		if(game.x_map > 0) 
			game.x_map = 0;
		
		if(x < - 100 || y > 570 || y < -40){
			game.bullet = null;
			game.nextShooter();
		}
	}

	/**
	 * 主逻辑方法
	 */
	public void logic() {
		collisionDetect();
		move();
	}

	/**
	 * 主碰撞检测方法
	 */
	private void collisionDetect() {
		for (int i = 0; i < game.v_obstacle.size(); i++) {
			Obstacle obstacle = (Obstacle)game.v_obstacle.elementAt(i);
			if (y < obstacle.y + 20 && y > obstacle.y - obstacle.height_cln - 20
					&& x > obstacle.x - obstacle.width_cln / 2 - 20
					&& x < obstacle.x + obstacle.width_cln / 2 + 20) {// 打到障碍物
				if(obstacle.boom || obstacle.type == 2)
					continue;
				int life = obstacle.life;
				obstacle.beHit(power, 2);
				power -= life;
				if(power <= 0) {
					game.nextShooter();
					game.bullet = null;
				}
				if(power <= 0) {
					return;
				}
			}
		}
		
		if (x < game.hero.x + 20 && x > game.hero.x - 20 && y > game.hero.y - 80
				&& y < game.hero.y) {
			game.hero.beHit(power);

			game.bullet = null;
		}
		
//		for (int i = 0; i < game.count_pier; i++) {
//			Pier pier = (Pier)game.v_pier.elementAt(i);
//			if (y < pier.y && y > pier.y - Pier.height_cln
//					&& x > pier.x - Pier.width_cln / 2
//					&& x < pier.x + Pier.width_cln / 2) {// 打到树墩
//				game.bullet = null;
//				game.nextShooter();
//				if(power <= 0) {
//					return;
//				}
//			}
//		}
	}
}
