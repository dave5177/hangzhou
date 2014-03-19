package com.dave.jpsc.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

/**
 * @author Administrator 我的车库
 */
public class Garage implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_blink;
	public Image img_main;
	public Image img_select;
	public Image img_unknow;
	public Image img_pre_bar;
	public Image img_number;
	public Image[] a_img_car;
	public Image[] a_img_car_small;
	public Image[] a_img_btn;
	public Image[] a_img_choose_btn;

	private int index_choose = 1;

	private int index_car_choose;

	/**
	 * 我的车的数量
	 */
	private int carCount;

	/**
	 * 按键后车子移动的状态。1：按右键，车子向左移动；2：按左键，车子向右移动。
	 */
	private int carMove;

	/**
	 * 当前车子x坐标。
	 */
	private int x_t_car = 320;

	/**
	 * 移动时已成为历史的车子的x轴坐标
	 */
	private int x_l_car;

	/**
	 * 按键移动的时候
	 */
	private boolean carMoving;

	/**
	 * 星星的帧
	 */
	private int starFrame;

	public Garage(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		
		carCount = canvasControl.me.cars.length;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);

		for (int i = 0; i < 3; i++) {
			int temp_count = i + index_car_choose - 1;
			if (index_car_choose == 0)
				temp_count = i + index_car_choose;
			if (temp_count < carCount)
				g.drawImage(a_img_car_small[canvasControl.me.cars[temp_count][0]], 180 + 140 * i,
						441, Graphics.VCENTER | Graphics.HCENTER);
			else
				g.drawImage(img_unknow, 180 + 140 * i, 441, Graphics.VCENTER
						| Graphics.HCENTER);

			g.drawImage(a_img_btn[i], 80 + i * 160, 500, Graphics.VCENTER
					| Graphics.HCENTER);
		}

		if (index_choose > 0) {
			g.drawImage(a_img_choose_btn[index_choose - 1],
					80 + (index_choose - 1) * 160, 500, Graphics.VCENTER
							| Graphics.HCENTER);
		} else {
			if (index_car_choose == 0) {
				g.drawImage(img_select, 180, 436, Graphics.VCENTER
						| Graphics.HCENTER);
			} else {
				g.drawImage(img_select, 320, 436, Graphics.VCENTER
						| Graphics.HCENTER);
			}
		}

		g.drawImage(a_img_car[canvasControl.me.cars[index_car_choose][0]], x_t_car, 300, Graphics.VCENTER | Graphics.HCENTER);
		if (carMove == 1) {
			g.drawImage(a_img_car[canvasControl.me.cars[index_car_choose - 1][0]], x_l_car,
					300, Graphics.VCENTER | Graphics.HCENTER);
		} else if (carMove == 2) {
			g.drawImage(a_img_car[canvasControl.me.cars[index_car_choose + 1][0]], x_l_car,
					300, Graphics.VCENTER | Graphics.HCENTER);
		} else {
			showStar(g);
		}

		showCarProperty(g);
		
		if (!carMoving
				&& index_car_choose == canvasControl.me.mainCar)
			g.drawImage(img_main, 500, 200, Graphics.VCENTER | Graphics.HCENTER);
	}

	/**
	 * 星星
	 * @param g
	 */
	private void showStar(Graphics g) {
		g.drawRegion(img_blink, starFrame * 43, 0, 43, 45, 0, 220, 220, 0);
		int frame = starFrame + 1;
		if(frame > 5)
			frame = 0;
		g.drawRegion(img_blink, frame * 43, 0, 43, 45, 0, 430, 280, 0);
		frame ++;
		if(frame > 5)
			frame = 0;
		g.drawRegion(img_blink, frame * 43, 0, 43, 45, 0, 280, 260, 0);
		frame ++;
		if(frame > 5)
			frame = 0;
		g.drawRegion(img_blink, frame * 43, 0, 43, 45, 0, 348, 240, 0);
		frame ++;
		if(frame > 5)
			frame = 0;
		g.drawRegion(img_blink, frame * 43, 0, 43, 45, 0, 360, 320, 0);
	}

	/**
	 * 画车的属性条
	 * 
	 * @param g
	 */
	private void showCarProperty(Graphics g) {
		for (int i = 0; i < 4; i++) {
			if (i == 1) {
				g.setClip(
						230, 128 + i * 20,
						202 * canvasControl.me.carRealParams[index_car_choose][i]
								/ CanvasControl.carMaxPower[index_car_choose][i], 80);
				g.drawImage(img_pre_bar, 230, 128 + i * 20, 0);
				
				g.setClip(0, 0, 640, 530);
				C.drawString(
						g,
						img_number,
						canvasControl.me.carRealParams[index_car_choose][i]
								+ "/" + CanvasControl.carMaxPower[index_car_choose][i],
						"0123456789/", 330, 128 + i * 20, 11, 15, Graphics.TOP
								| Graphics.HCENTER, 0, 0);
			} else {
				g.setClip(
						215, 128 + i * 19,
						202 * canvasControl.me.carRealParams[index_car_choose][i]
								/ CanvasControl.carMaxPower[index_car_choose][i], 80);
				g.drawImage(img_pre_bar, 215, 128 + i * 19, 0);
				
				g.setClip(0, 0, 640, 530);
				C.drawString(g, img_number, canvasControl.me.carRealParams[index_car_choose][i] + "/" + CanvasControl.carMaxPower[index_car_choose][i], "0123456789/", 315, 128 + i * 20, 11, 15, Graphics.TOP | Graphics.HCENTER, 0, 0);
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
				if (index_car_choose > 0) {
					index_car_choose--;

					x_l_car = x_t_car;
					x_t_car = x_l_car - 600;
					carMove = 2;
					carMoving = true;
				}
			} else {
				if (index_choose > 1)
					index_choose--;
			}
			break;
		case C.KEY_RIGHT:
			if (index_choose == 0) {
				if (index_car_choose < carCount - 1) {
					index_car_choose++;

					x_l_car = x_t_car;
					x_t_car = x_l_car + 600;
					carMove = 1;
					carMoving = true;
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
			case 0:
				canvasControl.me.mainCar = index_car_choose;
				canvasControl.saveParam();
				break;
			case 1:
				canvasControl.buyGoods(16);
				break;
			case 2://出战
				CanvasControl.usingCar = index_car_choose;
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				this.removeServerImage();
				canvasControl.setView(new Loading(canvasControl, 1));
				break;
			case 3://设为主力
				canvasControl.me.mainCar = index_car_choose;
				canvasControl.saveParam();
				break;

			default:
				break;
			}
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Home(canvasControl));
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if (carMove == 1) {
			x_l_car -= 100;
			x_t_car -= 100;
			if (x_t_car <= 320) {
				x_t_car = 320;
				carMove = 0;
				carMoving = false;
			}
		} else if (carMove == 2) {
			x_l_car += 100;
			x_t_car += 100;
			if (x_t_car >= 320) {
				x_t_car = 320;
				carMove = 0;
				carMoving = false;
			}
		} else {
			starFrame ++;
			if(starFrame > 5)
				starFrame = 0;
		}
	}

	public void removeServerImage() {
		img_back = null;
		img_blink = null;
		img_main = null;
		img_select = null;
		img_unknow = null;
		img_pre_bar = null;

		for (int i = 0; i < a_img_btn.length; i++) {
			a_img_btn[i] = null;
			a_img_choose_btn[i] = null;
		}
		a_img_btn = null;
		a_img_choose_btn = null;

		for (int i = 0; i < a_img_car.length; i++) {
			a_img_car[i] = null;
			a_img_car_small[i] = null;
		}
		a_img_car = null;
		a_img_car_small = null;

		System.gc();
	}

	public void handleGoods(int goodsIndex) {
		if(goodsIndex == 16) {//升级
			canvasControl.me.cars[index_car_choose][4] ++;
			canvasControl.me.computCarParam();
			CanvasControl.carProperty[canvasControl.me.cars[index_car_choose][0]][4] ++;
		}
	}

}
