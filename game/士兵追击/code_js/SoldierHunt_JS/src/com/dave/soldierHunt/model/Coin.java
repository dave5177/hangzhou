package com.dave.soldierHunt.model;

import javax.microedition.lcdui.Graphics;

import com.dave.soldierHunt.tool.C;
import com.dave.soldierHunt.tool.ImageManager;
import com.dave.soldierHunt.view.Game;

public class Coin {
	/**
	 * ��ֵ
	 */
	public int value;
	public int x;
	public int y;
	public final Game game;
	private int index;
	private int time;
	
	/**
	 * ������
	 */
	public boolean beAte;
	private int eatTimeControl;
	
	public Coin(int value, int x, int y, Game game) {
		super();
		this.value = value;
		this.x = x;
		this.y = y;
		this.game = game;
	}
	
	public void show(Graphics g) {
		if(beAte) {
			C.drawString(g, game.img_number_eatCoin, "+1", 
					"0123456789+", x + game.mapX - 27, game.mapY + y - 759 - eatTimeControl * 20,
					27, 27, 0, 0, 0);
		} else {
			g.drawImage(ImageManager.ima_coin[index], x + game.mapX, game.mapY + y - 759, Graphics.HCENTER | Graphics.VCENTER);
		}
	}
	
	public void logic() {
		index ++;
		if(index > 5) index = 0;
		
		if(game.inhale) beInhale();
		
		if(beAte) {
			eatTimeControl ++;
			if(eatTimeControl > 5) {
				game.coins.removeElement(this);
			}
		}
		
		autoDisappear();
	}
	
	/**
	 * ����ȡ���ƶ�����
	 */
	private void beInhale() {
		x += (game.hero.x - x) / 2;
		y += (game.hero.y - y) / 2;
	}

	/**
	 * �Զ���ʧ
	 */
	public void autoDisappear() {
		time ++;
		if(time > 200)
			game.coins.removeElement(this);
	}
}
