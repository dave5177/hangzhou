package com.dave.paoBing.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.view.Game;

/**
 * @author Administrator
 * 障碍物碎片
 */
public class Fragment {
	
	/**
	 * 类型与障碍物相对应：
	 * 1、石头。 2、墙壁。 3、木桶。 4、木箱。 5、木板
	 */
	int type;

	/**
	 * 坐标
	 */
	int x, y;
	
	/**
	 * 飞翔的方向
	 * 1~8 分别代表 上、左上、左、左下、下、右下、右、右上。
	 */
	int direction;
	
	/**
	 * 飞翔的speed
	 * 1,2,3档
	 */
	int speed;

	private Image img;

	private Game game;

	/**
	 * @param type 类型
	 * @param x 初始x坐标
	 * @param y 初始y坐标
	 * @param direction 飞翔的方向
	 */
	public Fragment(int type, int x, int y, int direction, int speed, Image img, Game game) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
		this.img = img;
		this.game = game;
	}
	
	/**
	 * 飞吧
	 */
	public void move() {
		switch (direction) {
		case 1://向上飞
			y -= 20 + speed * 5;
			break;
		case 2://向左上飞
			x -= 20 + speed * 5;
			y -= 20 + speed * 5;
			break;
		case 3://向左飞
			x -= 20 + speed * 5;
			break;
		case 4://向左下飞
			x -= 20 + speed * 5;
			y += 20 + speed * 5;
			break;
		case 5://向下飞
			y += 20 + speed * 5;
			break;
		case 6://向右下飞
			x += 20 + speed * 5;
			y += 20 + speed * 5;
			break;
		case 7://向右飞
			x += 20 + speed * 5;
			break;
		case 8://向右上飞
			x += 20 + speed * 5;
			y -= 20 + speed * 5;
			break;

		default:
			break;
		}
	}
	
	public void show(Graphics g) {
		g.drawImage(img, x + game.x_map, y, Graphics.VCENTER | Graphics.HCENTER);
	}
	
}
