package com.dave.worldWar.model;

import javax.microedition.lcdui.Graphics;

import com.dave.worldWar.tool.C;
import com.dave.worldWar.view.Game;

public class Bomb {
	int x, y;

	/**
	 * ���ٶ�
	 */
	private int speed_x;
	
	/**
	 * ��ֱ�����ٶ�
	 */
	private int speed_y;
	
	/**
	 * �������ٶ�
	 */
	private int a_g = 1;

	/**
	 * ��ը��
	 */
	private boolean boom;

	/**
	 * ������Ϸ
	 */
	private Game game;

	/**
	 * ��ըЧ������֡
	 */
	private int boomFrame;

	/**
	 * ���еĴ�ֱ����
	 */
	private int flyDist;
	
	private int height;
	
	/**
	 * ����0�����£�1�����ϡ�
	 */
	private int dir;
	
	public Bomb(int x, int y, int speed_x, Game game) {
		this.x = x;
		this.y = y;
		this.speed_x = speed_x;
		this.game = game;
		if(y > 250) 
			dir = 0;
		else 
			dir = 1;
		
		height = C.R.nextInt(100);
	}
	
	public void show(Graphics g) {
		if(boom && game.bomberView) {
			g.drawImage(game.a_img_boom_5[boomFrame], x + game.x_map, y + game.y_map, Graphics.HCENTER | Graphics.VCENTER);
		} else if(game.bomberView){
			g.drawImage(game.a_img_bullet[5], x + game.x_map, y + game.y_map, Graphics.HCENTER | Graphics.VCENTER);
		}
	}
	
	public void logic() {
		if(boom) {
			boomFrame ++;
			if(boomFrame > 7) {
				boomFrame = 0;
				game.v_bomb.removeElement(this);
			}
			
			if(boomFrame == 2) {
				for (int i = 0; i < game.v_soldier.size(); i++) {
					Soldier soldier_bomb = (Soldier) game.v_soldier.elementAt(i);
					if(soldier_bomb.friendly || soldier_bomb.life <= 0)
						continue;
					if(soldier_bomb.life > 0 && C.distance(soldier_bomb.x, soldier_bomb.y, x, y) < 6400)
						soldier_bomb.beBomb();
				}
			}
			
		} else {
			x += speed_x;
			if(dir == 0) {
				y += speed_y;
			} else {
				y -= speed_y;
			}
			flyDist += speed_y;
			
			speed_y += a_g;
			
			if(flyDist >= height) {
				flyDist = 0;
				boom = true;
			}
		}
			
	}
}
