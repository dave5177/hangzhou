package com.dave.paoBing.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.tool.C;
import com.dave.paoBing.view.Game;

/**
 * @author Administrator
 * 
 */
public class Missile {
	/**
	 * ���������� ,����ͷ����
	 */
	int x, y;

	/**
	 * �����ĽǶ�
	 */
	int angle;

	/**
	 * ը����������Ϸ
	 */
	private Game game;

	/**
	 * �ƶ��ٶ�
	 */
	private final static int MOVE_SPEED = 30;

	/**
	 * ͼƬ
	 */
	private Image img_missile;

	private Image[] a_img_boom;// ��ըͼƬ

	/**
	 * �ڵ��Ĺ�����
	 */
	private int power;

	/**
	 * ��ʾ��ըЧ��
	 */
	private boolean showBoom;

	/**
	 * ��ըЧ��֡
	 */
	private int boomFrame;

	/**
	 * ��ը���ĵ������
	 */
	private int y_boom, x_boom;

	/**
	 * @param game
	 *            ���ɵ�������Ϸ
	 * @param x
	 *            �������ɵ�x����
	 * @param y
	 *            �������ɵ�y����
	 * @param angle
	 *            �������ɵĽǶ�ֵ
	 */
	public Missile(Game game, int x, int y, int angle, int power,
			boolean doublePower) {
		this.game = game;
		this.angle = angle;
		setStartPoint(x, y);
		this.power = power;

		initImage();
	}

	/**
	 * �����ڵ���ʼλ���ڵ�ͷ��x,y����
	 * 
	 * @param x
	 *            ԭʼǹ��x����ֵ
	 * @param y
	 *            ԭʼǹ��y����ֵ
	 */
	private void setStartPoint(int x, int y) {
		switch (angle) {
		case 50:
			this.x = x + 48;
			this.y = y - 55;
			break;
		case 40:
			this.x = x + 52;
			this.y = y - 50;
			break;
		case 30:
			this.x = x + 60;
			this.y = y - 42;
			break;
		case 20:
			this.x = x + 70;
			this.y = y - 35;
			break;
		case 10:
			this.x = x + 80;
			this.y = y - 28;
			break;
		case 0:
			this.x = x + 70;
			this.y = y;
			break;
		case -10:
			this.x = x + 80;
			this.y = y + 28;
			break;
		case -20:
			this.x = x + 70;
			this.y = y + 35;
			break;
		case -30:
			this.x = x + 65;
			this.y = y + 45;
			break;
		case -40:
			this.x = x + 55;
			this.y = y + 50;
			break;
		case -50:
			this.x = x + 50;
			this.y = y + 63;
			break;

		default:
			break;
		}
	}

	private void initImage() {
		try {
			img_missile = Image.createImage("/missile/" + angle + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics g) {
		g.setClip(0, 0, 645, 485);
		if (showBoom && a_img_boom != null) {
			g.drawImage(a_img_boom[boomFrame], x_boom + game.x_map, y_boom,
					Graphics.VCENTER | Graphics.HCENTER);
		}

		if (power > 0) {
			if (angle < 0) {
				g.drawImage(img_missile, x + game.x_map, y, Graphics.BOTTOM
						| Graphics.RIGHT);
			} else if (angle > 0) {
				g.drawImage(img_missile, x + game.x_map, y, Graphics.TOP
						| Graphics.RIGHT);
			} else {
				g.drawImage(img_missile, x + game.x_map, y, Graphics.VCENTER
						| Graphics.RIGHT);
			}
		}
		g.setClip(0, 0, 645, 535);
	}

	/**
	 * �ƶ�����
	 */
	public void move() {
		x += MOVE_SPEED * C.cos(angle) / 100000;
		y -= MOVE_SPEED * C.sin(angle) / 100000;

		if (x > 1000 || y > 570 || y < -40) {
			game.missile = null;
			game.nextShooter();
		}

		if (x + game.x_map >= 320 && game.x_map > -320)
			game.x_map -= MOVE_SPEED * C.cos(angle) / 100000;

		if (game.x_map < -320)
			game.x_map = -320;
	}

	/**
	 * ���߼�����
	 */
	public void logic() {
		move();
		collisionDetect();
		if (!showBoom) {
		} else {
			boomFrame++;
			if (boomFrame > 4) {
				boomFrame = 0;
				showBoom = false;
				if (power <= 0) {
					game.nextShooter();
					removeBoomImage();
					game.missile = null;
				}
				System.gc();
			}
		}
	}

	/**
	 * ����ײ��ⷽ��
	 */
	private void collisionDetect() {
		for (int i = 0; i < game.v_enemy.size(); i++) {
			Enemy enemy = (Enemy) game.v_enemy.elementAt(i);
			if (enemy.dead)
				continue;
			if (y < enemy.y && y > enemy.y - enemy.height_cln
					&& x > enemy.x - enemy.width_cln / 2
					&& x < enemy.x + enemy.width_cln / 2) {// �򵽸õ���
				if (enemy.dead)
					continue;
				int life = enemy.life;
				enemy.beHit(power);
				boom(life);
				if (power <= 0) {
					return;
				}
			}
		}

		for (int i = 0; i < game.count_pier; i++) {
			Pier pier = (Pier) game.v_pier.elementAt(i);
			if (y < pier.y && y > pier.y - Pier.height_cln
					&& x > pier.x - Pier.width_cln / 2
					&& x < pier.x + Pier.width_cln / 2) {// ������
				boom(power);
				if (power <= 0) {
					return;
				}
			}
		}

		for (int i = 0; i < game.v_obstacle.size(); i++) {
			Obstacle obstacle = (Obstacle) game.v_obstacle.elementAt(i);
			if (y < obstacle.y + 20
					&& y > obstacle.y - obstacle.height_cln - 20
					&& x > obstacle.x - obstacle.width_cln / 2
					&& x < obstacle.x + obstacle.width_cln / 2 + 20) {// ���ϰ���
				if (obstacle.boom)
					continue;
				int life = obstacle.life;
				obstacle.beHit(power, 1);
				boom(life);
				if (power <= 0) {
					return;
				}
			}
		}

	}

	/**
	 * ��ը��
	 * 
	 * @param life
	 *            ���ĵ�����
	 */
	private void boom(int life) {
		showBoom = true;
		power -= life;
		x_boom = x;
		y_boom = y;
		loadBoomImage();
		if (power <= 0) {
			x = 0;
			y = 0;
			// game.nextShooter();
		}
	}

	/**
	 * ���ر�ըЧ��ͼƬ
	 */
	private void loadBoomImage() {
		a_img_boom = new Image[5];
		try {
			Image temp = Image.createImage("/missile/boom.png");
			int w = temp.getWidth();
			int h = temp.getHeight();

			for (int i = 0; i < 5; i++) {
				a_img_boom[i] = Image.createImage(temp, w * i / 5, 0, w / 5, h,
						0);
			}

			temp = null;
			System.gc();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �Ƴ���ըЧ��ͼ
	 */
	private void removeBoomImage() {
		for (int i = 0; i < 5; i++) {
			a_img_boom[i] = null;
		}

		a_img_boom = null;
	}
}
