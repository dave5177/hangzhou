package com.dave.paoBing.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.net.ServerIptv;
import com.dave.paoBing.view.Dialog;
import com.dave.paoBing.view.Game;
import com.zn.hs.prop.HSProp;

/**
 * @author Administrator
 *
 */
public class Enemy {
	/**
	 * 坐标(下部中点)
	 */
	public int x, y;
	
	/**
	 * 头部的坐标
	 */
	public int x_head, y_head;

	/**
	 * 碰撞宽度，碰撞高度。
	 */
	public int width_cln, height_cln;

	/**
	 * 所在游戏
	 */
	private Game game;

	/**
	 * 方向。 0是水平向左的方向。每加1角度向上加一个单位，每减1角度向下加一个单位。
	 */
	private int direction;

	/**
	 * 总共三种类型，放别用1,2,3表示
	 */
	private int type;

	/**
	 * 攻击力
	 */
	public int attackPower;

	/**
	 * 生命值
	 */
	public int life;

	/**
	 * 最大生命值
	 */
	public int lifeMax;
	
	/**
	 * 死了
	 */
	public boolean dead;

	private Image[] a_img_enemy;
	private Image img_body;
	private Image img_head;

	/**
	 * 人物帧，不射击的时候永远是0.
	 */
	private int frame;

	/**
	 * 飞翔的头，0向后飞，1向前飞。
	 */
	private int head_fly;

	/**
	 * 飞翔的头的偏移量
	 */
	private int head_offer_y;

	/**
	 * 头停下来了
	 */
	private boolean stop_head;

	/**
	 * 飞翔的头弹动几次
	 */
	private int jump_times_head;

	/**
	 * 飞翔的头x偏移量
	 */
	private int head_offer_x;

	/**
	 * 处于设计状态
	 */
	public boolean shootting;

	/**
	 * 攻击延迟控制
	 */
	private int shootTime;

	/**
	 * 开完枪啦
	 */
	private boolean afterFier;

	/**
	 * 帧速度控制
	 */
	private int speedControl;

	/**
	 * 在木板上时的木板
	 */
	private Obstacle obstacle;

	/**
	 * 命中率（%）
	 */
	private int acc;
	
	/**
	 * enemy构造函数的o_pier参数的取值，默认值在树墩的中央
	 */
	public static final int CENTER_PIER = 0;
	/**
	 * 在树墩的左边
	 */
	public static final int LEFT_PIER = 1;
	/**
	 * 在树墩的右边
	 */
	public static final int RIGHT_PIER = 2;

	/**
	 * @param game
	 * @param pier 在树墩上时的树墩 在障碍物上时为NULL
	 * @param obstacle 在障碍物上时的障碍物 在树墩上时为NULL
	 * @param direction
	 * @param type
	 * @param o_pier
	 */
	public Enemy(Game game, Pier pier, Obstacle obstacle, int direction, int type, int o_pier) {
		this.game = game;
		if(pier == null) {
			if(o_pier == LEFT_PIER) {
				x = obstacle.x - 60;
			} else if(o_pier == RIGHT_PIER) {
				x = obstacle.x + 60;
			} else {
				x = obstacle.x;
			}
			y = obstacle.y - obstacle.height_cln;
			this.obstacle = obstacle;
		} else {
			if(o_pier == LEFT_PIER) {
				x = pier.x - 40;
			} else if(o_pier == RIGHT_PIER) {
				x = pier.x + 40;
			} else {
				x = pier.x;
			}
			y = pier.y - 25;
		}

		this.direction = direction;
		this.type = type;

		init();
	}

	/**
	 * 初始化敌人，加载敌人图片。
	 */
	private void init() {
		if(type == 1) {
			lifeMax = 50;
			attackPower = 30;
			acc = 75;
		} else if(type == 2) {
			lifeMax = 70;
			attackPower = 50;
			acc = 85;
		} else {
			lifeMax = 100;
			attackPower = 80;
			acc = 95;
		}
		life = lifeMax;
		
		loadImage();
	}

	/**
	 * 加载初始的图片
	 */
	public void loadImage() {
		try {
			Image enemy = Image.createImage("/enemy/" + type + "_enemy_"
					+ direction + ".png");
			int w = enemy.getWidth();
			int h = enemy.getHeight();

			width_cln = w / 3 - 20;
			height_cln = h - 10;

			a_img_enemy = new Image[3];
			for (int i = 0; i < 3; i++) {
				a_img_enemy[i] = Image.createImage(enemy, w * i / 3, 0, w / 3,
						h, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics g) {
		if(dead) {
			g.drawImage(img_body, x + game.x_map, y, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.drawImage(img_head, x_head + game.x_map, y_head, Graphics.BOTTOM
					| Graphics.HCENTER);
		} else {
			g.drawImage(a_img_enemy[frame], x + game.x_map, y, Graphics.BOTTOM
					| Graphics.HCENTER);
			
			showBloodBar(g);
		}
	}

	/**
	 * 画血条
	 * @param g
	 */
	private void showBloodBar(Graphics g) {
		g.drawImage(game.a_img_blood_bar[0], x + game.x_map, y - height_cln - 10, Graphics.BOTTOM
				| Graphics.HCENTER);
		
		g.setClip(0, 0, x - Game.LENGTH_BLOOD_BAR / 2 + life * Game.LENGTH_BLOOD_BAR / lifeMax + game.x_map, 535);
		g.drawImage(game.a_img_blood_bar[1], x + game.x_map, y - height_cln - 10, Graphics.BOTTOM
				| Graphics.HCENTER);
		g.setClip(0, 0, 645, 535);
	}

	/**
	 * 主逻辑方法
	 */
	public void logic() {
		if(dead && !stop_head) {
			if(head_fly == 0) {//向后飞的头
				x_head += head_offer_x;
			} else {//向前飞的头
				x_head -= head_offer_x;
			}
			
			if(head_offer_x > 0) head_offer_x --;
			
			head_offer_y += 5;
			y_head += head_offer_y;
			if(y_head > y && !stop_head) {
				if(jump_times_head < 3) {
					jump_times_head ++;
					head_offer_y = -head_offer_y * 3 / 4 ;
				} else {
					stop_head = true;
					game.v_enemy.removeElement(this);
					if(game.v_enemy.size() <= 0) {//过关了。
//						game.getCanvasControl().playerHandler.playByIndex(5);
						
						CanvasControl.mission ++;
						new ServerIptv(game.getCanvasControl()).doSendScore();
//						new ServerIptv(game.getCanvasControl()).doSendUserInfo();
						
						if(game.showWeak) {
							game.showWeak = false;
							game.hero.weak = false;
							game.removeWeakImage();
						}
						
						game.getCanvasControl().setGoBackView(game);
						game.getCanvasControl().setView(game.getCanvasControl().nullView);
						game.removeResource();
						game.getCanvasControl().setView(new Dialog(game.getCanvasControl(), 2, game));
						
						if(CanvasControl.mission > 5) Game.remainLife = Hero.life;
						System.out.println("英雄剩余生命：" + Hero.life);
					}
				}
			}
		} else if(shootting) {
			shootTime ++;
			if(shootTime > 18) {
				fire();
				shootting = false;
				shootTime = 0;
			}
		}
		
		if(afterFier) {
			speedControl++;
			if(speedControl % 2 == 0) {
				frame ++;
				if(frame > 2) {
					frame = 0;
					afterFier = false;
				}
			}
		}
		
		if(obstacle != null && !dead) {
			if(obstacle.boom) {
				beHit(lifeMax);
				y += obstacle.height_cln;
			}
		}
	}

	/**
	 * 开枪拉
	 */
	private void fire() {
//		game.getCanvasControl().playerHandler.playByIndex(3);
		
		if(HSProp.getRandom(0, 99) < acc) {
			game.bullet = new Bullet(x - 20, y - 45, game.hero.x, game.hero.y - 40, game, type);
		} else {
			if(HSProp.getRandom(0, 1) == 1) {
				game.bullet = new Bullet(x - 20, y - 45, game.hero.x, game.hero.y + HSProp.getRandom(20, 220), game, type);
			} else {
				game.bullet = new Bullet(x - 20, y - 45, game.hero.x, game.hero.y - 80 - HSProp.getRandom(0, 200), game, type);
			}
		}
		afterFier = true;
	}

	/**
	 * 被攻击
	 * 
	 * @param power
	 */
	public void beHit(int power) {
		life -= power;
		if(life <= 0 && !dead) {
//			game.getCanvasControl().playerHandler.playByIndex(4);
			
			dead = true;
//			x_head = x + 40;
			x_head = x;
			y_head = y - height_cln + 50;
			head_fly = HSProp.getRandom(0, 1);
			head_offer_y = -20;
			head_offer_x = 5;
			removeAliveImage();
			loadDeadImage();
			
//			if(game.shoottingEnemy == this) {
//				game.nextShooter();
//			}
		}
	}

	/**
	 * 移除活着的图片
	 */
	public void removeAliveImage() {
		for (int i = 0; i < 3; i++) {
			a_img_enemy[i] = null;
		}
		a_img_enemy = null;
	}

	/**
	 * 加载死亡后的图片
	 */
	private void loadDeadImage() {
		try {
			img_body = Image.createImage("/enemy/" + type + "_body.png");
			img_head = Image.createImage("/enemy/" + type + "_head.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
