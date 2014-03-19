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
	 * ����(�²��е�)
	 */
	public int x, y;
	
	/**
	 * ͷ��������
	 */
	public int x_head, y_head;

	/**
	 * ��ײ��ȣ���ײ�߶ȡ�
	 */
	public int width_cln, height_cln;

	/**
	 * ������Ϸ
	 */
	private Game game;

	/**
	 * ���� 0��ˮƽ����ķ���ÿ��1�Ƕ����ϼ�һ����λ��ÿ��1�Ƕ����¼�һ����λ��
	 */
	private int direction;

	/**
	 * �ܹ��������ͣ��ű���1,2,3��ʾ
	 */
	private int type;

	/**
	 * ������
	 */
	public int attackPower;

	/**
	 * ����ֵ
	 */
	public int life;

	/**
	 * �������ֵ
	 */
	public int lifeMax;
	
	/**
	 * ����
	 */
	public boolean dead;

	private Image[] a_img_enemy;
	private Image img_body;
	private Image img_head;

	/**
	 * ����֡���������ʱ����Զ��0.
	 */
	private int frame;

	/**
	 * �����ͷ��0���ɣ�1��ǰ�ɡ�
	 */
	private int head_fly;

	/**
	 * �����ͷ��ƫ����
	 */
	private int head_offer_y;

	/**
	 * ͷͣ������
	 */
	private boolean stop_head;

	/**
	 * �����ͷ��������
	 */
	private int jump_times_head;

	/**
	 * �����ͷxƫ����
	 */
	private int head_offer_x;

	/**
	 * �������״̬
	 */
	public boolean shootting;

	/**
	 * �����ӳٿ���
	 */
	private int shootTime;

	/**
	 * ����ǹ��
	 */
	private boolean afterFier;

	/**
	 * ֡�ٶȿ���
	 */
	private int speedControl;

	/**
	 * ��ľ����ʱ��ľ��
	 */
	private Obstacle obstacle;

	/**
	 * �����ʣ�%��
	 */
	private int acc;
	
	/**
	 * enemy���캯����o_pier������ȡֵ��Ĭ��ֵ�����յ�����
	 */
	public static final int CENTER_PIER = 0;
	/**
	 * �����յ����
	 */
	public static final int LEFT_PIER = 1;
	/**
	 * �����յ��ұ�
	 */
	public static final int RIGHT_PIER = 2;

	/**
	 * @param game
	 * @param pier ��������ʱ������ ���ϰ�����ʱΪNULL
	 * @param obstacle ���ϰ�����ʱ���ϰ��� ��������ʱΪNULL
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
	 * ��ʼ�����ˣ����ص���ͼƬ��
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
	 * ���س�ʼ��ͼƬ
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
	 * ��Ѫ��
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
	 * ���߼�����
	 */
	public void logic() {
		if(dead && !stop_head) {
			if(head_fly == 0) {//���ɵ�ͷ
				x_head += head_offer_x;
			} else {//��ǰ�ɵ�ͷ
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
					if(game.v_enemy.size() <= 0) {//�����ˡ�
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
						System.out.println("Ӣ��ʣ��������" + Hero.life);
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
	 * ��ǹ��
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
	 * ������
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
	 * �Ƴ����ŵ�ͼƬ
	 */
	public void removeAliveImage() {
		for (int i = 0; i < 3; i++) {
			a_img_enemy[i] = null;
		}
		a_img_enemy = null;
	}

	/**
	 * �����������ͼƬ
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
