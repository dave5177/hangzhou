package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class Shop implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_number;
	public Image img_bar;
	public Image img_roll;//滚轮
	public Image img_x;
	private Image img_btnStart;
	private Image img_btnBuy;
	public Image[] imgArr_goodsIcon;
	public Image[] imgArr_goodsName;
	public Image[] imgArr_goodsInfo;
	
	/**
	 * 选中的。0~6为选中道具，7为选中开始游戏。 
	 */
	private int index_choose;
	private int index_screen;//选中的是当前屏幕显示的第几个道具
	
	/**
	 * 屏幕中显示的第一个道具的下标
	 */
	private int startGoodsIndex;

	/**
	 * 选择指示的y坐标
	 */
	private int yChoose;

	public Shop(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_btnStart, 320, 490, Graphics.HCENTER | Graphics.VCENTER);
		for (int i = 0; i < 4; i++) {
			g.drawImage(img_bar, 300, 110 + i * 100, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(imgArr_goodsIcon[i + startGoodsIndex], 67, 108 + i * 100, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(imgArr_goodsName[i + startGoodsIndex], 128, 81 + i * 100, 0);
			g.drawImage(imgArr_goodsInfo[i + startGoodsIndex], 122, 119 + i * 100, 0);
			g.drawImage(img_x, 366, 85 + i * 100, 0);
			C.drawString(g, img_number, CanvasControl.goodsAmount[i + startGoodsIndex] + "", "0123456789", 379, 81 + i * 100, 13, 21, 0, 0, 0);
			g.drawImage(img_btnBuy, 519, 90 + i * 100, Graphics.HCENTER | Graphics.VCENTER);
		}
		int yMul = index_choose;
		if(index_choose > 6)
			yMul = 6;
		g.drawImage(img_roll, 614, 98 + yMul * 55, Graphics.HCENTER | Graphics.VCENTER);
		
		if(index_choose < 7) {
			yChoose = yChoose == 70 + index_screen * 100 ? 72 + index_screen * 100 : 70 + index_screen * 100;
			g.drawImage(canvasControl.img_choose, 558, yChoose, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			yChoose = yChoose == 470 ? 472 : 470;
			g.drawImage(canvasControl.img_choose, 375, yChoose, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		g.drawImage(canvasControl.img_key_0_goback, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
	}


	public void loadResource() {
		try {
			img_back = Image.createImage("/shop/back.png");
			img_number = Image.createImage("/shop/number.png");
			img_bar = Image.createImage("/shop/bar.png");
			img_x = Image.createImage("/shop/x.png");
			img_roll = Image.createImage("/shop/roll.png");
			img_btnBuy = Image.createImage("/shop/btn_buy.png");
			img_btnStart = Image.createImage("/shop/btn_start.png");
			imgArr_goodsIcon = new Image[7];
			imgArr_goodsInfo = new Image[7];
			imgArr_goodsName = new Image[7];
			for (int i = 0; i < 7; i++) {
				imgArr_goodsIcon[i] = Image.createImage("/shop/goods_icon_" + i + ".png");
				imgArr_goodsInfo[i] = Image.createImage("/shop/goods_info_" + i + ".png");
				imgArr_goodsName[i] = Image.createImage("/shop/goods_name_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_number = null;
		img_bar = null;
		img_x = null;
		img_roll = null;
		img_btnBuy = null;
		img_btnStart = null;
		
		for (int i = 0; i < 7; i++) {
			imgArr_goodsIcon[i] = null;
			imgArr_goodsInfo[i] = null;
			imgArr_goodsName[i] = null;
		}
		imgArr_goodsIcon = null;
		imgArr_goodsInfo = null;
		imgArr_goodsName = null;
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_DOWN:
			if(index_choose < 7) {
				if(index_screen < 3)
					index_screen ++;
				else if(index_choose < 6)
					startGoodsIndex ++;
				
				index_choose ++;
			}
			break;
		case C.KEY_UP:
			if(index_choose < 7) {
				if(index_screen > 0)
					index_screen --;
				else if(index_choose > 0)
					startGoodsIndex--;
			}
			
			if(index_choose > 0)
				index_choose --;
			break;
		case C.KEY_RIGHT://向下翻页
			if(startGoodsIndex < 3) {
				index_choose += 3 - startGoodsIndex;
				startGoodsIndex = 3;
			}
			break;
		case C.KEY_LEFT://向上翻页
			if(startGoodsIndex > 0) {
				index_choose -= startGoodsIndex;
				startGoodsIndex = 0;
			}
			break;
		case C.KEY_FIRE:
			if(index_choose == 7) {//开始游戏
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new SceneChoose(canvasControl));
			}  else {//购买道具
				
			}
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
			break;

		default:
			break;
		}
		System.out.println("indexChoose:  " + index_choose);
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	public void removeServerImage() {
		
	}
}
