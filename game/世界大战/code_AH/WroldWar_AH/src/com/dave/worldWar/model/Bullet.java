package com.dave.worldWar.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import com.dave.worldWar.view.Game;

public class Bullet {

	/**
	 * ���� ��ʿ�����������Ӧ
	 */
	public int type;

	/**
	 * ����
	 */
	public int x, y;

	/**
	 * �ѷ���з����ӵ�
	 */
	public boolean friendly;

	/**
	 * ������Ϸ
	 */
	public Game game;

	/**
	 * ����Ŀ��
	 */
	private Soldier shootPoint;

	/**
	 * �ܲ��ܴ���
	 */
	private boolean hit;
	
	/**
	 * ���еľ���
	 */
	private int flyDistance;

	/**
	 * �����ʿ��
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
