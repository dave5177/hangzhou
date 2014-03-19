package com.dave.ftxz.model;

import javax.microedition.lcdui.Graphics;

import com.dave.ftxz.tool.C;
import com.dave.ftxz.tool.Rect;
import com.dave.ftxz.view.Game;

/**
 * @author ��ά
 * �������
 */
public class Treasure implements Model {
	
	/**
	 * ������Ϸ
	 */
	Game game;
	
	/**
	 * ����
	 */
	int x, y;
	
	/**
	 * ���ͣ�0~3�����١���̡��޵С�����ʯ��
	 */
	int type;
	
	/**
	 * ��ײ����
	 */
	public Rect rect_clsn;

	/**
	 * y�ٶ�
	 */
	private int speed_y;

	/**
	 * ���ٶȷ���0���£�1���ϣ�
	 */
	private int dir_a;

	/**
	 * @param game ������Ϸ
	 * @param x ����
	 * @param y ����
	 * @param type ���ͣ�0~3�����١���̡��޵С�����ʯ��
	 */
	public Treasure(Game game, int x, int y, int type) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;
		
		rect_clsn = new Rect(x - 23, y - 46, 46, 46);
		speed_y = 30;
		dir_a = 1;
	}

	public void show(Graphics g) {
		g.drawImage(game.a_img_treasure[type], x, y, Graphics.HCENTER | Graphics.BOTTOM);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {
		fire();
		falling();
	}

	/**
	 * ����
	 */
	private void falling() {
		y += speed_y;
		rect_clsn.y += speed_y;
		
		if(dir_a == 1) {
			speed_y -= 10;
			if(y < 0)
				dir_a = 0;
		} else {
			if(speed_y < 80)
				speed_y += 20;
		}
		
		if(y > 600)
			removeMe();
	}

	public void fire() {
		if(C.rectInsect(rect_clsn, game.hero.rect_clsn)) {//���Ե�
			game.hero.getTreasure(type);
			removeMe();
		}
	}

	private void removeMe() {
		game.v_treasure.removeElement(this);
	}

}
