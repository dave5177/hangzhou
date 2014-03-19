package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class HeroChoose implements Showable{
	private Image img_back;
	private Image[][] imgArr_baby;
	private Image[][] imgArr_icon;
	private Image[] imgArr_skill;//技能介绍,只有两个。0的位置是空的。
	private Image img_btnBuy;
	private Image img_btnSetMain;
	private Image img_btnUplevel;

	private int chooseIndex;//选择按钮下标,0为选择baby。1购买或者升级，2为设为主角。
	
	private int babyIndex;
	
	private CanvasControl canvasControl;
	
	/**
	 * 选择红心y坐标
	 */
	private int yChoose;
	
	public HeroChoose(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		if(CanvasControl.babyLevel[babyIndex] > 0) {
			g.drawImage(imgArr_baby[babyIndex][0], 320, 326, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(imgArr_baby[babyIndex][1], 320, 326, Graphics.HCENTER | Graphics.VCENTER);
		}
		if(babyIndex > 0) {
			g.drawImage(imgArr_skill[babyIndex], 420, 246, 0);
		}
		
		int iconIndex = babyIndex - 1;
		if(iconIndex < 0)
			iconIndex = 2;
		if(CanvasControl.babyLevel[iconIndex] > 0) {
			g.drawImage(imgArr_icon[iconIndex][0], 203, 142, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(imgArr_icon[iconIndex][1], 203, 142, Graphics.HCENTER | Graphics.VCENTER);
		}
		iconIndex = babyIndex + 1;
		if(iconIndex > 2)
			iconIndex = 0;
		if(CanvasControl.babyLevel[iconIndex] > 0) {
			g.drawImage(imgArr_icon[iconIndex][0], 429, 142, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(imgArr_icon[iconIndex][1], 429, 142, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		if(CanvasControl.babyLevel[babyIndex] > 0) {//已经购买显示设为主角和升级按钮
			g.drawImage(img_btnUplevel, 220, 490, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_btnSetMain, 420, 490, Graphics.HCENTER | Graphics.VCENTER);
		} else {//未解锁显示购买按钮
			g.drawImage(img_btnBuy, 320, 490, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		if(chooseIndex == 0) {
			yChoose = yChoose == 210 ? 212 : 210;
			g.drawImage(canvasControl.img_choose, 320, yChoose, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			if(CanvasControl.babyLevel[babyIndex] > 0) {
				yChoose = yChoose == 460 ? 458 : 460;
				g.drawImage(canvasControl.img_choose, 60 + chooseIndex * 200, yChoose, Graphics.HCENTER | Graphics.VCENTER);
			} else {
				yChoose = yChoose == 460 ? 458 : 460;
				g.drawImage(canvasControl.img_choose, 370, yChoose, Graphics.HCENTER | Graphics.VCENTER);
			}
		}
		
		g.drawImage(canvasControl.img_key_0_goback, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/heroChoose/back.png");
			img_btnBuy = Image.createImage("/heroChoose/btn_buy.png");
			img_btnSetMain = Image.createImage("/heroChoose/btn_setMain.png");
			img_btnUplevel = Image.createImage("/heroChoose/btn_uplevel.png");
			imgArr_baby = new Image[3][2];
			imgArr_icon = new Image[3][2];
			imgArr_skill = new Image[3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					imgArr_baby[i][j] = Image.createImage("/heroChoose/baby_" + i + "_" + j + ".png");
					imgArr_icon[i][j] = Image.createImage("/heroChoose/icon_" + i + "_" + j + ".png");
				}
				if(i > 0)
					imgArr_skill[i] = Image.createImage("/heroChoose/skill_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		// TODO Auto-generated method stub
		
	}

	public void removeServerImage() {
		img_back = null;
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_RIGHT:
			if(chooseIndex == 0) {
				babyIndex ++;
				if(babyIndex > 2)
					babyIndex = 0;
			} else {
				if(CanvasControl.babyLevel[babyIndex] > 0) {
					if(chooseIndex < 2)
						chooseIndex ++;
				}
			}
			break;
		case C.KEY_LEFT:
			if(chooseIndex == 0) {
				babyIndex --;
				if(babyIndex < 0)
					babyIndex = 2;
			} else {
				if(CanvasControl.babyLevel[babyIndex] > 0) {
					if(chooseIndex > 1)
						chooseIndex --;
				}
			}
			break;
		case C.KEY_DOWN:
			if(chooseIndex == 0)
				chooseIndex ++;
			break;
		case C.KEY_UP:
			if(chooseIndex > 0)
				chooseIndex = 0;
			break;
			
		case C.KEY_FIRE:
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
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void logic() {
		// TODO Auto-generated method stub
		
	}

}
