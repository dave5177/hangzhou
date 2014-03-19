package com.dave.jpsc.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.tool.C;
import com.dave.jpsc.view.Game;

/**
 * @author Dave 减速攻击和致盲攻击的子弹类
 * 
 */
public class Bullet implements Model {

	private Game game;
	private int x;
	private int y;

	/**
	 * 类型： 0 减速攻击；1 致盲攻击
	 */
	public int type;

	private Image img;

	/**
	 * 攻击目标
	 */
	private Car targetCar;
	
	/**
	 * 方向（0~359）
	 */
	private int direction;
	
	/**
	 * 子弹移动速度
	 */
	private final static int speed = 35;

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
	 *            类型： 0 减速攻击；1 致盲攻击
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
		if(C.distance(x, y, targetCar.x, targetCar.y) < 35 * 35) {//击中
			targetCar.beHit(type);
			game.bullets.removeElement(this);
		}
	}

	/**
	 * 移动
	 */
	private void move() {
		x += C.cos(direction) * speed / 100000;
		y += C.sin(direction) * speed / 100000;
	}

	/**
	 * 更新方向
	 */
	private void updateDir() {
		direction = C.arctan(targetCar.y - y, targetCar.x - x);
	}

	public void fire() {
		// TODO Auto-generated method stub

	}

}
