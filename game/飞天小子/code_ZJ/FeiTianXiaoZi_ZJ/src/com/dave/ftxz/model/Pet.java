package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

public class Pet extends Hero{

	/**
	 * ӵ�иó����Ӣ��
	 */
	private Hero hero;
	
	/**
	 * ���ﶯ��֡
	 */
	private int frame;

	/**
	 * �ƶ���Ӣ�۵��Աߣ�1�������ƶ���2�������ƶ���
	 */
	public int moveToSide;

	/**
	 * ���ڼ���
	 */
	private boolean join;
	
	/**
	 * �����ٶ�
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
	 * �ƶ���Ӣ�۵��ұߣ����������ʱ��
	 */
	public void moveToRight() {
		moveToSide = 2;
	}
	
	/**
	 * �ƶ���Ӣ�۵����꣬�����ҵļ�ͷ��ʱ��
	 */
	public void moveToLeft() {
		moveToSide = 1;
	}

}
