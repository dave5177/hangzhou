package com.dave.jpsc.model;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.tool.Rect;
import com.dave.jpsc.view.Game;

/**
 * @author Dave ����
 */
public class Treasure implements Model {

	private Game game;
	private int x;
	private int y;

	/**
	 * ���ͣ� 9 ���״̬�� 10 ����ʱ�䣻 11 ����ģʽ�� 12 ����ȼ�ϣ� 13���ٹ����� 14 ��ä����
	 */
	public int type;

	private Image img;

	public Rect rect;
	
	private int y_show;

	/**
	 * ����
	 * 
	 * @param game
	 *            ������Ϸ
	 * @param x
	 *            ���ĵ�x����
	 * @param y
	 *            ���ĵ�y����
	 * @param type
	 *            ���ͣ� 9 ���״̬�� 10 ����ʱ�䣻 11 ����ģʽ�� 12 ����ȼ�ϣ� 13���ٹ����� 14 ��ä����
	 */
	public Treasure(Game game, int x, int y, int type) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;

		init();
	}

	private void init() {
		rect = new Rect(x - 15, y - 15, 30, 30);

		try {
			img = Image.createImage("/treasure/" + type + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics g) {
		if(y_show == y)
			y_show = y - 2;
		else
			y_show = y;
		
		g.drawImage(img, x + game.x_map, y_show + game.y_map, Graphics.HCENTER
				| Graphics.VCENTER);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub

	}

	public void logic() {

	}

	public void fire() {
		// TODO Auto-generated method stub

	}

	/**
	 * ������
	 */
	public void beEta() {
		game.treasures.removeElement(this);
		img = null;
	}

}
