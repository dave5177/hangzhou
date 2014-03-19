package com.dave.gowhere.tool;

import javax.microedition.lcdui.Graphics;


public class Rect {
	/**
	 * �������Ͻ����꣬��Ⱥ͸߶�
	 */
	public int x, y, w, h;

	/**
	 * @param x �������Ͻ�x����
	 * @param y �������Ͻ�y����
	 * @param w ���ο��
	 * @param h ���θ߶�
	 */
	public Rect(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void show(int x_map, int y_map, Graphics g) {
		g.drawRect(x + x_map, y + y_map, w, h);
	}

}
