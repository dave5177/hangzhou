package com.dave.jpsc.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

public class CarShop implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_bottom;
	public Image img_number;
	public Image[] a_img_car;
	public Image[] a_img_car_name;
	public Image[] a_img_btn;
	public Image[] a_img_choose_btn;
	public Image[] a_img_arrow;// 左右移动的箭头
	public Image img_stage;// 展览台
	public Image img_pre_bar;
	public Image img_own;
	public Image img_select;

	/**
	 * 选择下标值
	 */
	private int index_choose;

	/**
	 * 车的下标值
	 */
	private int index_car;

	/**
	 * 展示台y坐标
	 */
	private int y_stage = 600;

	public CarShop(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_stage, 320, y_stage, Graphics.VCENTER
				| Graphics.HCENTER);
		g.drawImage(a_img_car[index_car], 320, y_stage - 60, Graphics.VCENTER
				| Graphics.HCENTER);
		g.drawImage(img_bottom, 0, 530, Graphics.LEFT | Graphics.BOTTOM);
		g.drawImage(a_img_car_name[index_car], 290, 225, Graphics.VCENTER
				| Graphics.HCENTER);

		for (int i = 0; i < 3; i++) {
			g.drawImage(a_img_btn[i], i * 220 + 100, 482, Graphics.VCENTER
					| Graphics.HCENTER);
		}
		if (index_choose > 0) {
			g.drawImage(a_img_choose_btn[index_choose - 1],
					(index_choose - 1) * 220 + 100, 482, Graphics.VCENTER
							| Graphics.HCENTER);
			g.drawImage(a_img_arrow[1], 30, 250, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawRegion(a_img_arrow[1], 0, 0, 29, 44, Sprite.TRANS_MIRROR,
					610, 250, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(img_select, 327, 174, Graphics.VCENTER
					| Graphics.HCENTER);
			if (index_car == 0) {
				g.drawImage(a_img_arrow[1], 30, 250, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawRegion(a_img_arrow[0], 0, 0, 29, 44, Sprite.TRANS_MIRROR,
						610, 250, Graphics.VCENTER | Graphics.HCENTER);
			} else if (index_car == 8) {
				g.drawImage(a_img_arrow[0], 30, 250, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawRegion(a_img_arrow[1], 0, 0, 29, 44, Sprite.TRANS_MIRROR,
						610, 250, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(a_img_arrow[0], 30, 250, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawRegion(a_img_arrow[0], 0, 0, 29, 44, Sprite.TRANS_MIRROR,
						610, 250, Graphics.VCENTER | Graphics.HCENTER);
			}
		}
		if (CanvasControl.carProperty[index_car][4] > 0)
			g.drawImage(img_own, 480, 225, Graphics.VCENTER | Graphics.HCENTER);

		showCarProperty(g);
	}

	/**
	 * 画车的属性条
	 * 
	 * @param g
	 */
	private void showCarProperty(Graphics g) {
		for (int i = 0; i < 4; i++) {
			if (i == 1) {
				g.setClip(226, 113 + i * 24, 202
						* CanvasControl.carProperty[index_car][i]
						/ CanvasControl.carMaxPower[index_car][i], 80);
				g.drawImage(img_pre_bar, 226, 113 + i * 24, 0);
				g.setClip(0, 0, 640, 530);
				C.drawString(
						g,
						img_number,
						CanvasControl.carProperty[index_car][i]
								+ "/" + CanvasControl.carMaxPower[index_car][i],
						"0123456789/", 330, 113 + i * 24, 11, 15, Graphics.TOP
								| Graphics.HCENTER, 0, 0);
			} else {
				g.setClip(212, 113 + i * 24, 202
						* CanvasControl.carProperty[index_car][i]
						/ CanvasControl.carMaxPower[index_car][i], 80);
				g.drawImage(img_pre_bar, 212, 113 + i * 24, 0);
				g.setClip(0, 0, 640, 530);
				C.drawString(
						g,
						img_number,
						CanvasControl.carProperty[index_car][i]
								+ "/" + CanvasControl.carMaxPower[index_car][i],
						"0123456789/", 316, 113 + i * 24, 11, 15, Graphics.TOP
								| Graphics.HCENTER, 0, 0);
			}
		}
	}

	public void loadResource() {
	}

	public void removeResource() {

	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT:
			if (index_choose == 0) {
				if (index_car > 0) {
					index_car--;
					y_stage = 600;
				}
			} else {
				if (index_choose > 1)
					index_choose--;
			}
			break;
		case C.KEY_RIGHT:
			if (index_choose == 0) {
				if (index_car < 8) {
					index_car++;
					y_stage = 600;
				}
			} else {
				if (index_choose < 3)
					index_choose++;
			}
			break;
		case C.KEY_DOWN:
			if (index_choose == 0) {
				index_choose++;
			}
			break;
		case C.KEY_UP:
			if (index_choose > 0) {
				index_choose = 0;
			}
			break;
		case C.KEY_FIRE:
			switch (index_choose) {
			case 0:// 购买
				if(CanvasControl.carProperty[index_car][4] < 1) {
					if(index_car > 0)
						canvasControl.buyGoods(index_car);
				}
				break;
			case 1:// 购买
				if(CanvasControl.carProperty[index_car][4] < 1) {
					if(index_car > 0)
						canvasControl.buyGoods(index_car);
				}
				break;
			case 2:// 我的车库
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				this.removeServerImage();
				canvasControl.setView(new Loading(canvasControl, 6));
				break;
			case 3:// 快速开始
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				this.removeServerImage();
				canvasControl.setView(new Loading(canvasControl, 1));
				break;
			}
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 0));
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if (y_stage > 400) {
			y_stage -= 20;
			if (y_stage < 400)
				y_stage = 400;
		}
	}

	public void removeServerImage() {
		img_back = null;
		img_bottom = null;
		img_stage = null;
		img_pre_bar = null;
		img_own = null;
		img_select = null;

		for (int i = 0; i < a_img_btn.length; i++) {
			a_img_btn[i] = null;
		}
		a_img_btn = null;

		for (int i = 0; i < a_img_choose_btn.length; i++) {
			a_img_choose_btn[i] = null;
		}
		a_img_choose_btn = null;

		for (int i = 0; i < a_img_car.length; i++) {
			a_img_car[i] = null;
			a_img_car_name[i] = null;
		}
		a_img_car_name = null;
		a_img_car = null;

		for (int i = 0; i < a_img_arrow.length; i++) {
			a_img_arrow[i] = null;
		}
		a_img_arrow = null;

		System.gc();
	}

	public void handleGoods(int goodsIndex) {
		canvasControl.me.addCar(goodsIndex);
		CanvasControl.carProperty[goodsIndex][4] = 1;
		canvasControl.me.computCarParam();
	}

}
