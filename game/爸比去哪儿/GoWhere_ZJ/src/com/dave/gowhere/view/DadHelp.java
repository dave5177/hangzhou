package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class DadHelp implements Showable {
	private CanvasControl canvasControl;
	
	private Image img_back;
	private Image img_bar;
	private Image[][] imgArr_dad;
	private Image[] imgArr_name;
	private Image[] imgArr_info;
	private Image img_btn_buy;
	private Image img_btn_uplevel;
	private Image img_btn_start;
	
	private int yChoose;
	
	/**
	 * 选择下标，这里0选中爸爸，1选中开始按钮
	 */
	private int chooseIndex;
	
	/**
	 * 选中的爸爸下标
	 */
	private int dadIndex;

	public DadHelp(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		for (int i = 0; i < 3; i++) {
			g.drawImage(img_bar, 117 + i * 210, 260, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(imgArr_name[i], 117 + i * 210, 85, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(imgArr_info[i], 117 + i * 210, 312, Graphics.HCENTER | Graphics.VCENTER);
			if(CanvasControl.dadLevel[i] > 0) {
				g.drawImage(imgArr_dad[i][0], 117 + i * 210, 188, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_btn_uplevel, 117 + i * 210, 390, Graphics.HCENTER | Graphics.VCENTER);
			} else {
				g.drawImage(imgArr_dad[i][1], 117 + i * 210, 188, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_btn_buy, 117 + i * 210, 390, Graphics.HCENTER | Graphics.VCENTER);
			}
		}
		g.drawImage(img_btn_start, 320, 480, Graphics.HCENTER | Graphics.VCENTER);
		
		if(chooseIndex == 0) {
			yChoose = yChoose == 373 ? 371 : 373;
			g.drawImage(canvasControl.img_choose, 153 + dadIndex * 210, yChoose, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			yChoose = yChoose == 461 ? 463 : 461;
			g.drawImage(canvasControl.img_choose, 374, yChoose, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		
		g.drawImage(canvasControl.img_key_0_goback, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/dadhelp/back.png");
			img_bar = Image.createImage("/dadhelp/bar.png");
			img_btn_buy = Image.createImage("/dadhelp/btn_buy.png");;
			img_btn_uplevel = Image.createImage("/dadhelp/btn_uplevel.png");;
			img_btn_start = Image.createImage("/dadhelp/btn_start.png");;
			
			imgArr_dad = new Image[3][2];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 2; j++) {
					imgArr_dad[i][j] = Image.createImage("/dadhelp/dad_" + i + "_" + j + ".png");
				}
			}
			imgArr_info = new Image[3];
			for (int i = 0; i < 3; i++) {
				imgArr_info[i] = Image.createImage("/dadhelp/info_" + i + ".png");
			}
			imgArr_name = new Image[3];
			for (int i = 0; i < 3; i++) {
				imgArr_name[i] = Image.createImage("/dadhelp/name_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		img_back = null;
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				imgArr_dad[i][j] = null;
			}
		}
		imgArr_dad = null;
		
		for (int i = 0; i < 3; i++) {
			imgArr_info[i] = null;
		}
		imgArr_info = null;
		
		for (int i = 0; i < 3; i++) {
			imgArr_name[i] = null;
		}
		imgArr_name = null;
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_RIGHT:
			if(chooseIndex == 0) {
				if(dadIndex < 2)
					dadIndex ++;
			}
			break;
		case C.KEY_LEFT:
			if(chooseIndex == 0) {
				if(dadIndex > 0)
					dadIndex --;
			}
			break;
		case C.KEY_DOWN:
			chooseIndex = 1;
			break;
		case C.KEY_UP:
			chooseIndex = 0;
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
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

	public void removeServerImage() {
		// TODO Auto-generated method stub
		
	}


}
