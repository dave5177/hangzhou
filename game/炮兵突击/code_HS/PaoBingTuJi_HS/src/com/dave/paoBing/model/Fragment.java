package com.dave.paoBing.model;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.view.Game;

/**
 * @author Administrator
 * �ϰ�����Ƭ
 */
public class Fragment {
	
	/**
	 * �������ϰ������Ӧ��
	 * 1��ʯͷ�� 2��ǽ�ڡ� 3��ľͰ�� 4��ľ�䡣 5��ľ��
	 */
	int type;

	/**
	 * ����
	 */
	int x, y;
	
	/**
	 * ����ķ���
	 * 1~8 �ֱ���� �ϡ����ϡ������¡��¡����¡��ҡ����ϡ�
	 */
	int direction;
	
	/**
	 * �����speed
	 * 1,2,3��
	 */
	int speed;

	private Image img;

	private Game game;

	/**
	 * @param type ����
	 * @param x ��ʼx����
	 * @param y ��ʼy����
	 * @param direction ����ķ���
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
	 * �ɰ�
	 */
	public void move() {
		switch (direction) {
		case 1://���Ϸ�
			y -= 20 + speed * 5;
			break;
		case 2://�����Ϸ�
			x -= 20 + speed * 5;
			y -= 20 + speed * 5;
			break;
		case 3://�����
			x -= 20 + speed * 5;
			break;
		case 4://�����·�
			x -= 20 + speed * 5;
			y += 20 + speed * 5;
			break;
		case 5://���·�
			y += 20 + speed * 5;
			break;
		case 6://�����·�
			x += 20 + speed * 5;
			y += 20 + speed * 5;
			break;
		case 7://���ҷ�
			x += 20 + speed * 5;
			break;
		case 8://�����Ϸ�
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
