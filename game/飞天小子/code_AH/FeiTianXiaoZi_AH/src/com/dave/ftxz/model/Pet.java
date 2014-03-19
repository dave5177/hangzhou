package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

public class Pet extends Hero{

	/**
	 * 拥有该宠物的英雄
	 */
	private Hero hero;
	
	/**
	 * 宠物动画帧
	 */
	private int frame;

	/**
	 * 移动到英雄的旁边（1：往左移动；2：往右移动）
	 */
	public int moveToSide;

	/**
	 * 正在加入
	 */
	private boolean join;
	
	/**
	 * 加入速度
	 */
	private int speed_jion = 20;

	public Pet(Game game) {
		super(game, 5);
	}
	
	public Pet(Game game, Hero hero) {
		super(game, 5);
		this.hero = hero;
		this.hero.pet = this;
		x = 650;
		y = 500;
		join = true;
		rect_clsn = new Rect(x - 12, y - 40, 24, 35);
	}

	public void fire() {
		if(join)
			return;
		
		time_fire ++;
		if(time_fire >= Hero.INTERVAL_FIRE) {
			time_fire = 0;
			game.v_bullet.addElement(new Bullet(game, x, y - 30, -1));
		}
	}

	public void show(Graphics g) {
		g.drawImage(super.game.a_img_pet[frame], x, y, Graphics.HCENTER | Graphics.BOTTOM);
	}

	public void logic() {
		fly();
		
		fire();
		
		if(beHit && !join) {
			hero.pet = null;
			if(CanvasControl.goodsNumber[3] > 0) {
				new Pet(game, hero);
				CanvasControl.goodsNumber[3] --;
				game.canvasControl.saveParam();
			}
		}
		
		frame ++;
		if(frame > 3)
			frame = 0;
		
		if(join) {
			speed_jion += 5;
			x -= speed_jion;
			rect_clsn.x -= speed_jion;
			if(x - hero.x <= 80) {
				x = hero.x + 80;
				rect_clsn.x = hero.x + 68;
				join = false;
			}
			return;
		}
		
		if(moveToSide == 1) {
			x -= 20;
			rect_clsn.x -= 20;
			if(hero.x - x >= 80) {
				x = hero.x - 80;
				rect_clsn.x = hero.x - 92;
				moveToSide = 0;
			}
		} else if(moveToSide == 2){
			x += 20;
			rect_clsn.x += 20;
			if(x - hero.x >= 80) {
				x = hero.x + 80;
				rect_clsn.x = hero.x + 68;
				moveToSide = 0;
			}
		}
	}
	
	/**
	 * 移动到英雄的右边，按向左键的时候
	 */
	public void moveToRight() {
		moveToSide = 2;
	}
	
	/**
	 * 移动到英雄的坐标，按向右的箭头的时候
	 */
	public void moveToLeft() {
		moveToSide = 1;
	}

}
