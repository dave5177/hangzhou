package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

public class Bird implements Model {

	/**
	 * 坐标
	 */
	public int x, y;

	/**
	 * 生命值
	 */
	public int life;

	/**
	 * 生命上限
	 */
	public int lifeMax;

	/**
	 * 碰撞矩形
	 */
	public Rect rect_clsn;

	/**
	 * 所在游戏
	 */
	public Game game;

	/**
	 * 类型（0~4）
	 */
	public int type;

	/**
	 * 被打了
	 */
	private boolean beHit;

	/**
	 * 被攻击的动画帧
	 */
	private int frame_beHit;

	/**
	 * 血条显示时间
	 */
	private int showBloodTime;

	/**
	 * type-2的鸟会爆炸
	 */
	private boolean boom;

	/**
	 * 闪烁
	 */
	private int blink;

	/**
	 * 爆炸效果帧
	 */
	private int frame_boom;

	public Bird(int x, int y, Game game, int type) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.type = type;

		rect_clsn = new Rect(x - 23, y - 60, 46, 55);
		initParam();
	}

	private void initParam() {
		switch (type) {
		case 0:
			lifeMax = 100;
			break;
		case 1:
			lifeMax = 300;
			break;
		case 2:
			lifeMax = 500;
			break;
		case 3:
			lifeMax = 600;
			break;
		case 4:
			lifeMax = 800;
			break;
		}
		life = lifeMax;
	}

	public void show(Graphics g) {
		if (showBloodTime > 0 && !boom && life > 0) {// 血条
			g.drawImage(game.a_img_bar_blood[0], x, y - 80, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.setClip(x - 24, 0, life * 48 / lifeMax, 530);
			g.drawImage(game.a_img_bar_blood[1], x, y - 80, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.setClip(0, 0, 640, 530);
		}
		if (boom) {
			g.drawImage(game.a_img_boom_bird[frame_boom], x, y - 30,
					Graphics.VCENTER | Graphics.HCENTER);
		} else if (beHit) {
			if (life > 0)
				g.drawImage(game.a_2_img_bird[type][1], x, y, Graphics.BOTTOM
						| Graphics.HCENTER);
			g.drawImage(game.a_img_beHit_bird[frame_beHit], x, y - 30,
					Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(game.a_2_img_bird[type][0], x, y, Graphics.BOTTOM
					| Graphics.HCENTER);
		}

		if (life > 0 && game.treasure_info[0][2] > 50) {
			g.drawImage(game.img_slow, x, y - 35, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (life > 0 && game.treasure_info[0][2] > 0) {// 减速效果闪烁
			if (blink < 4) {
				g.drawImage(game.img_slow, x, y - 35, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}

//		g.setColor(255, 255, 255);
//		g.drawString("life--" + life, x, y - 80, Graphics.BOTTOM
//				| Graphics.HCENTER);
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void logic() {
		move();

		fire();

		if (beHit) {
			frame_beHit++;
			if (frame_beHit > 3) {
				frame_beHit = 0;
				beHit = false;

				if (life <= 0) {
					if (type == 2) {// 爆炸
						boom = true;
					} else {
						removeMe();
					}
				}
			}
		}

		if (boom) {
			frame_boom++;
			if (frame_boom == 3) {// 炸死周围的人
				Rect rect_boom = new Rect(x - 100, y - 100, 200, 100);
				int size_v = game.v_bird.size();
				for (int i = size_v - 1; i >= 0; i--) {
					Bird bird = ((Bird) game.v_bird.elementAt(i));
					if (bird.life > 0
							&& C.rectInsect(rect_boom, bird.rect_clsn)) {
						bird.beBoom();
					}
				}
			}
			if (frame_boom > 4) {
				frame_boom = 0;
				boom = false;
				removeMe();
			}
		}

		if (showBloodTime > 0)
			showBloodTime--;
		
		if(game.treasure_info[0][2] > 0) {
			if(blink == 0)
				blink = 8;
			blink--;
		}
	}

	/**
	 * 被自己人炸了
	 */
	private void beBoom() {
		life = 0;
		beHit = true;
	}

	private void move() {
		if (game.treasure_info[0][2] > 0) {// 减速效果
			y += game.hero.speed_fly * 2;
			rect_clsn.y += game.hero.speed_fly * 2;
		} else {
			y += game.hero.speed_fly * 4;
			rect_clsn.y += game.hero.speed_fly * 4;
		}

		if (y > 600) {
			removeMe();
		}
	}

	private void removeMe() {
		game.v_bird.removeElement(this);
		if (y < 530)
			for (int i = 0; i < type + 1; i++) {
				game.v_coin.addElement(new Coin(game, x, y - 20));
			}
	}

	public void fire() {
		if (life > 0 && C.rectInsect(game.hero.rect_clsn, rect_clsn)) {
			game.hero.beHit();
		} else if (game.hero.pet != null) {
			if (life > 0 && C.rectInsect(game.hero.pet.rect_clsn, rect_clsn)) {
				game.hero.pet.beHit();
			}
		}
	}

	/**
	 * 被攻击
	 * 
	 * @param bullet
	 *            打我的子弹
	 */
	public void beHit(Bullet bullet) {
		life -= bullet.power;

		showBloodTime = 4;
		beHit = true;
	}

}
