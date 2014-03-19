package com.dave.jpsc.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.tool.C;
import com.dave.jpsc.view.Game;

/**
 * @author Dave ���ٹ�������ä�������ӵ���
 * 
 */
public class Bullet implements Model {

	private Game game;
	private int x;
	private int y;

	/**
	 * ���ͣ� 0 ���ٹ�����1 ��ä����
	 */
	public int type;

	private Image img;

	/**
	 * ����Ŀ��
	 */
	private Car targetCar;
	
	/**
	 * ����0~359��
	 */
	private int direction;
	
	/**
	 * �ӵ��ƶ��ٶ�
	 */
	private final static int speed = 35;

	/**
	 * ����
	 * 
	 * @param game
	 *            ������Ϸ
	 * @param x
	 *            ���ĵ�x����
	 * @param y
	 *            ���ĵ�y����
	 * @param type
	 *            ���ͣ� 0 ���ٹ�����1 ��ä����
	 */
	public Bullet(Game game, int x, int y, int type, Car targetCar) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;
		this.targetCar = targetCar;

		init();
	}

	private void init() {
		direction = C.arctan(targetCar.y - y, targetCar.x - x);
		try {
			img = Image.createImage("/bullet/" + type + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics g) {
		g.drawImage(img, x + game.x_map, y + game.y_map, Graphics.HCENTER
				| Graphics.VCENTER);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		updateDir();
		move();
		if(C.distance(x, y, targetCar.x, targetCar.y) < 35 * 35) {//����
			targetCar.beHit(type);
			game.bullets.removeElement(this);
		}
	}

	/**
	 * �ƶ�
	 */
	private void move() {
		x += C.cos(direction) * speed / 100000;
		y += C.sin(direction) * speed / 100000;
	}

	/**
	 * ���·���
	 */
	private void updateDir() {
		direction = C.arctan(targetCar.y - y, targetCar.x - x);
	}

	public void fire() {
		// TODO Auto-generated method stub

	}

}
