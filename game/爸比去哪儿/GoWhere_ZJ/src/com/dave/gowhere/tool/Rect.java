package com.dave.gowhere.tool;

import javax.microedition.lcdui.Graphics;


public class Rect {
	/**
	 * 矩形左上角坐标，宽度和高度
	 */
	public int x, y, w, h;

	/**
	 * @param x 矩形左上角x坐标
	 * @param y 矩形左上角y坐标
	 * @param w 矩形宽度
	 * @param h 矩形高度
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
