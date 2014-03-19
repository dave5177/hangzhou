package com.dave.worldWar.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import com.dave.worldWar.view.Game;

public class Bullet {

	/**
	 * 类型 和士兵的类型想对应
	 */
	public int type;

	/**
	 * 坐标
	 */
	public int x, y;

	/**
	 * 友方或敌方的子弹
	 */
	public boolean friendly;

	/**
	 * 所在游戏
	 */
	public Game game;

	/**
	 * 攻击目标
	 */
	private Soldier shootPoint;

	/**
	 * 能不能打中
	 */
	private boolean hit;
	
	/**
	 * 飞行的距离
	 */
	private int flyDistance;

	/**
	 * 射击的士兵
	 */
	public Soldier soldier;

	public Bullet(int type, int x, int y, boolean friendly, Game game, boolean hit, Soldier shootPoint, Soldier soldier) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.friendly = friendly;
		this.game = game;
		this.hit = hit;
		this.shootPoint = shootPoint;
		this.soldier = soldier;
	}

	public void show(Graphics g) {
		if (friendly) {
			g.drawImage(game.a_img_bullet[type], x + game.x_map, y + game.y_map, Graphics.VCENTER
					| Graphics.HCENTER);
		} else {
			g.drawRegion(game.a_img_bullet[type], 0, 0,
					game.a_img_bullet[type].getWidth(),
					game.a_img_bullet[type].getHeight(), Sprite.TRANS_MIRROR,
					x + game.x_map, y + game.y_map, Graphics.VCENTER | Graphics.HCENTER);
		}
	}

	public void fly() {
		if (friendly)
			x += 40;
		else
			x -= 40;
		
		flyDistance += 40;
		
		if(hit) {
			if(flyDistance >= soldier.range) {
				flyDistance = 0;
				game.v_bullet.removeElement(this);
				if(shootPoint.life > 0)
					shootPoint.beHit(this);
			}
		} else {
			if(flyDistance >= soldier.range + 40) {
				flyDistance = 0;				
				game.v_bullet.removeElement(this);
			}
		}
	}
}
