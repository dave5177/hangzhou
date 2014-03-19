package com.dave.modeauto;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.tool.C;

public class Pier {
	public short x;						//���յ�x����
	public short y;						//���յ�y����
	private Image img;					//���յ�ͼƬ�ӹ��췽�����á�
	public byte x_state;				//��¼���������ƶ���״̬��0Ϊ�����ƶ���1Ϊ�����ƶ�,2Ϊ��ֹ��
	private short left_x;					//�������ƶ���������ߵ�λ��
	private short right_x;				//�������ƶ��������ұߵ�λ��
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
