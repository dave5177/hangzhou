package com.dave.gowhere.intity;

import javax.microedition.lcdui.Graphics;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.gowhere.tool.Rect;
import com.dave.gowhere.view.Game;

public class Gold implements Model {
	private Game game;
	
	public int x, y;
	
	public Rect rect;//��ײ����

	/**
	 * ���ͣ�0��ͭ�ң�1�����ң�2����ң�3�����ص���4����������5������Ľ�ҡ�
	 */
	private int type;

	/**
	 * ����Ľ�ҵ�֡�����±�ֵ
	 */
	private int flyGoldIndex;
	
	/**
	 * 0:Ϊ�ɳ�״̬��1�������·���״̬
	 */
	private int flyGoldState;
	
	/**
	 * x�ٶ�
	 */
	private int xSpeed = 40, ySpeed = 2;

	/**
	 * ���н�ҵ���ߵ㡣
	 */
	private int topY;
	
	/**
	 * �����ٶ�
	 */
	private static final int ATTRACTSPEED = 20;
	
	/**
	 * ����ʱ�ƶ��ķ���
	 */
	private int direction;
	
	/**
	 * ������
	 */
	private boolean beAttract;

	/**
	 * @param game
	 * @param x
	 * @param y
	 * @param type ���ͣ�0��ͭ�ң�1�����ң�2����ң�3�����ص���4����������
	 */
	public Gold(Game game, int x, int y, int type) {
		super();
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;
		initRect();
		topY = C.R.nextInt(3) * 50 + 100;
	}

	private void initRect() {
		rect = new Rect(x - 10, y - 10, 20, 20);
		switch(type) {
		case 0:
			break;
		}
		
	}

	public void show(Graphics g) {
		if(type == 5) {
			g.drawImage(game.a_img_flyGold[flyGoldIndex], x, y, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(game.a_img_gold[type], x, y, Graphics.VCENTER | Graphics.HCENTER);
		}
		
		////////////////////////������Ϣ////////////////////////
		if(CanvasControl.DEBUG) {
			g.setColor(0x00ffff);
			rect.show(0, 0, g);
		}
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void logic() {
		flyGoldIndex = flyGoldIndex == 0 ? 1 : 0;
		if(beAttract) {
			updateDir();
			fly();
		} else {
			if((game.baby.flying || game.attract) && x < 600)
				beAttract = true;
			if(type == 5) {//����Ľ�ҿ���
				if(flyGoldState == 0) {
					x += xSpeed;
					y += ySpeed;
					xSpeed -= 2;
					if(x > 420)
						ySpeed --;
					else 
						ySpeed ++;
					if(xSpeed <= 0) {
						flyGoldState = 1;
//					ySpeed = -2;
						ySpeed = -10;
					}
				} else if(flyGoldState == 1) {
					y += ySpeed;
					if(ySpeed > 0) {
						if(y >= topY + 100)
							ySpeed *= -1;
					} else if(ySpeed < 0) {
						
						if(y <= topY) 
							ySpeed *= -1;
					}
					x -= 8;
				}
			} else {
				x -= game.speed_run;
			}
		}
		rectFollow();
		
		if(x < -20)
			game.removeGold(this);
	}

	private void rectFollow() {
		rect.x = x - 10;
		rect.y = y - 10;
	}
	

	/**
	 * �ƶ�
	 */
	private void fly() {
		x += C.cos(direction) * (game.speed_run + ATTRACTSPEED) / 100000;
		y += C.sin(direction) * (game.speed_run + ATTRACTSPEED) / 100000;
	}

	/**
	 * ���·���
	 */
	private void updateDir() {
		direction = C.arctan(game.baby.y - 30 - y, game.baby.x - x);
	}

	public void fire() {

	}

	public int getType() {
		return type;
	}

}
