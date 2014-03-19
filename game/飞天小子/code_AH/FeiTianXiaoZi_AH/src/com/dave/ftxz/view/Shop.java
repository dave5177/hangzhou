package com.dave.ftxz.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;

public class Shop implements Showable {

private CanvasControl canvasControl;
	
	public Image img_back;
	public Image img_number;
	public Image img_choose;
	public Image img_point;
	public Image[][] a_img_goods;
	public Image[] a_img_btn_start;
	public Image img_unit_price;
	public Image img_press_ok;
	
	private int index_choose;

	/**
	 * 选中到开始游戏时，上一个下标值。
	 */
	private int index_last;

	/**
	 * 选中动画帧
	 */
	private int frame;


	public Shop(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		if(index_choose == 9) {
			g.drawImage(a_img_goods[index_last][0], 92, 97, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(a_img_goods[index_last][1], 64, 127, 0);
			showPrice(index_last, g);
			C.drawString(g, img_number, CanvasControl.goodsNumber[index_last] + "", "0123456789.", 214, 98, 19, 16, Graphics.VCENTER | Graphics.HCENTER, 0, 0);
			g.drawImage(a_img_btn_start[1], 398, 509, Graphics.RIGHT | Graphics.BOTTOM);
		} else {
			g.drawImage(a_img_goods[index_choose][0], 92, 97, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(a_img_goods[index_choose][1], 64, 127, 0);
			showPrice(index_choose, g);
			C.drawString(g, img_number, CanvasControl.goodsNumber[index_choose] + "", "0123456789.", 214, 98, 19, 16, Graphics.VCENTER | Graphics.HCENTER, 0, 0);
			g.drawImage(a_img_btn_start[0], 398, 509, Graphics.RIGHT | Graphics.BOTTOM);
		}
		if(index_choose < 5) {
			g.drawRegion(img_point, frame * 100, 0, 100, 90, 0, 144 + index_choose * 114, 302, Graphics.RIGHT | Graphics.BOTTOM);
			g.drawImage(img_choose, 144 + index_choose * 114, 302, Graphics.VCENTER | Graphics.HCENTER);
		} else if(index_choose < 9) {
			g.drawRegion(img_point, frame * 100, 0, 100, 90, 0, 144 + (index_choose - 5) * 114, 426, Graphics.RIGHT | Graphics.BOTTOM);
			g.drawImage(img_choose, 144 + (index_choose - 5) * 114, 426, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(img_choose, 398, 509, Graphics.VCENTER | Graphics.HCENTER);
		}
			
		showRankString(g);
	}

	/**
	 * 画道具价格
	 * @param goods_index 道具下标值
	 * @param g 画笔
	 */
	private void showPrice(int goods_index, Graphics g) {
		int x = 0, y = 0;
		switch (goods_index) {
		case 0:
			x = 280;
			y = 166;
			break;
		case 1:
			x = 245;
			y = 166;
			break;
		case 2:
			x = 64;
			y = 166;
			break;
		case 3:
			x = 490;
			y = 132;
			break;
		case 4:
			x = 195;
			y = 166;
			break;
		case 5:
			x = 430;
			y = 130;
			break;
		case 6:
			x = 64;
			y = 166;
			break;
		case 7:
			x = 370;
			y = 132;
			break;
		case 8:
			x = 340;
			y = 133;
			break;

		default:
			break;
		}
		C.drawString(g, img_number, canvasControl.anHui_Tool
				.getPrice(CanvasControl.A_GOODS_PARAM[goods_index][0]), "0123456789.", x, y, 19, 16, 0, 0, 0);
		int x_unit = 0, y_unit = 0;
		x_unit = x + canvasControl.anHui_Tool
				.getPrice(CanvasControl.A_GOODS_PARAM[goods_index][0]).length() * 19;
		y_unit = y - 5;
		g.drawImage(img_unit_price, x_unit, y_unit, 0);
		C.drawString(g, img_number, ".", "0123456789.", x_unit + 60, y_unit + 5, 19, 16, 0, 0, 0);
		if(x_unit > 400) {
			x_unit = 14 - 30;
			y_unit = 166;
		}
		g.drawImage(img_press_ok, x_unit + 78, y_unit + 1, 0);
	}

	public void showRankString(Graphics g){
		
	}
	
	public void loadResource() {
	}
	
	public void removeResource() {
		
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT:
			if(index_choose > 0) index_choose --;
			break;
		case C.KEY_RIGHT:
			if(index_choose < 9) index_choose ++;
			break;
		case C.KEY_DOWN:
			if(index_choose < 5) {
				index_choose += 5;
				if(index_choose > 8)
					index_choose = 8;
			} else if(index_choose < 9) {
				index_last = index_choose;
				index_choose = 9;
			}
			break;
		case C.KEY_UP:
			if(index_choose > 8)
				index_choose -= 2;
			else if(index_choose > 4)
				index_choose -= 5;
			break;
		case C.KEY_FIRE:
			if(index_choose == 9) {//开始游戏
				canvasControl.setView(canvasControl.nullView);
				this.removeServerImage();
//				canvasControl.setView(new Loading(canvasControl, 1));
				canvasControl.setView(new Dialog(canvasControl, 2, this));
			} else {//购买道具
				CanvasControl.goodsIndex = index_choose;
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				canvasControl.setView(new Dialog(canvasControl, 5, this));
			}
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 0));
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		frame ++;
		if (frame > 3) {
			frame = 0;
		}
	}

	public void removeServerImage() {
		img_back = null;
		img_number = null;
		img_choose = null;
		img_point = null;
		img_unit_price = null;
		img_press_ok = null;
		
		for (int i = 0; i < a_img_btn_start.length; i++) {
			a_img_btn_start[i] = null;
		}
		a_img_btn_start = null;
		
		for (int i = 0; i < a_img_goods.length; i++) {
			a_img_goods[i][0] = null;
			a_img_goods[i][1] = null;
		}
		a_img_goods = null;
		
		System.gc();
	}
}

