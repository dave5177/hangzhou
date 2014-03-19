package com.dave.modeauto;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.tool.C;

public class Pier {
	public short x;						//树墩的x坐标
	public short y;						//树墩的y坐标
	private Image img;					//树墩的图片从构造方法里获得。
	public byte x_state;				//记录树墩左右移动的状态，0为向左移动，1为向右移动,2为静止。
	private short left_x;					//树墩能移动到的最左边的位置
	private short right_x;				//树墩能移动到的最右边的位置
	public static short x_offer = 4;
	
	public Pier(Image img, short y){
		this.img = img;
		this.y = y;
		x_state = (byte)C.R.nextInt(3);
		x = (short)(270 + C.R.nextInt(100));
		left_x = (short)(270 - C.R.nextInt(100));
		right_x = (short)(370 + C.R.nextInt(100));
	}
	
	public void draw(Graphics g) {
		g.drawImage(img, x, y, Graphics.TOP|Graphics.HCENTER);
	}
	
	public void move() {
			if(x_state == 0) {
				x -= x_offer;
				check();
			}
			if(x_state == 1) {
				x += x_offer;
				check();
			}
	}
	
	public void check() {
		if(x <= left_x) x_state = 1;
		if(x >= right_x) x_state = 0;
	}
}
