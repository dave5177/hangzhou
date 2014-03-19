package com.dave.modeauto;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class Monkey{
	CanvasControl canvasControl;
	public short x;
	public short y = 476;
	public short life = C.LIFE;
	public byte x_state = 2;									//记录猴子跳跃时运动方向，0为向左移动，1为向右移动，2为竖直。
	
	private Image[] img_monkey;								//猴子动画的四张图片
	private Image[] img_eye;								//猴子眼睛动画的四张图片
	private Image[] img_mouth;								//猴子嘴动画的四张图片
	
	public byte floor = 0;									//记录猴子在第几层，最下面蹲为0，然后1，然后2，然后3。
	public short all_floor = 0;
	public short old_floor = 0;
	public Pier[] pier;										//保存树墩
	
	private Image img_monkey_jump;
	private Image img_monkey_down;
	private Image img_monkey_sweat;
	
	public byte state = 0;									//记录猴子的三种状态，0为普通不动的状态，1为向上跳，2为下落。

	public Monkey(CanvasControl canvasControl, Pier[] pier, Image[] img_monkey, Image[] img_eye, Image[] img_mouth) {
		this.canvasControl = canvasControl;
		this.pier = pier;
		this.img_monkey = img_monkey;
		this.img_eye = img_eye;
		this.img_mouth = img_mouth;
		try {
			img_monkey_jump = Image.createImage("/modeauto/monkeyJump.png");
			img_monkey_down = Image.createImage("/modeauto/monkeyDown.png");
			img_monkey_sweat = Image.createImage("/modeauto/monkeySweat.png");
		} catch (IOException e) {
			System.out.println("can't find mongkeyJump");
			e.printStackTrace();
		}
		x = pier[0].x;
	}
	
	public void move() {
		switch(state) {
		case 0 :
			x = pier[floor].x;
			break;
		case 1 :
		case 2 :
			switch(x_state) {
			case 0 :
				x -= Pier.x_offer;
				break;
			case 1 :
				x += Pier.x_offer;
				break;
			}
			break;
		}
	}
	
	public void jump() {
		x_state = pier[floor].x_state;
		state = 1;
	}
	
	public void draw(Graphics g, int i) {
		
		switch(state) {
		case 0 : {
			int eye_y = y - 45;
			int mouth_y = y - 20;
			switch (i) {
			case 0 : 
				eye_y = y - 45;
				mouth_y = y - 20;
				break;
			case 2 : 
				eye_y = y - 41;
				mouth_y = y - 16;
				break;
			case 1 : 
			case 3 : 
				eye_y = y - 43;
				mouth_y = y - 18;
				break;
			}
			g.drawImage(img_monkey[i], x, y, Graphics.BOTTOM|Graphics.HCENTER);
			g.drawImage(img_eye[i], x, eye_y, Graphics.BOTTOM|Graphics.HCENTER);
			g.drawImage(img_mouth[i], x, mouth_y, Graphics.BOTTOM|Graphics.HCENTER);
		} break;
		case 1 : {
			g.drawImage(img_monkey_jump, x, y, Graphics.BOTTOM|Graphics.HCENTER);
		} break;
		
		case 2 : {
			if(y < pier[floor + 1].y + 10) {
				g.drawImage(img_monkey_jump, x, y, Graphics.BOTTOM|Graphics.HCENTER);
			}
			else {
				g.drawImage(img_monkey_down, x, y, Graphics.BOTTOM|Graphics.HCENTER);
				if(life == 0) {
					g.drawImage(img_monkey_sweat, x + 14, y - 40, Graphics.BOTTOM|Graphics.HCENTER);
				}
			}
			if(intersect(img_monkey_down, x, y, pier[floor + 1])) {
				state = 0;
				floor ++;
				all_floor ++;
				y = pier[floor].y;
			} 
			
		} break;
		}
	}
	
	public boolean intersect(Image img, int x, int y, Pier pier) {
		if(y <= pier.y + 4 && y >= pier.y - 4) {
			if(x >= pier.x && x - pier.x < img.getWidth()/3) return true;
			if(x <= pier.x && pier.x - x < img.getWidth()/3) return true;
		}
		return false;
	}
	
}
