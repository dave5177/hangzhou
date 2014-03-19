package com.dave.soldierHunt.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dalin.jsiptv.prop.JS_IPTV_PORP_TOOL;
import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;

/**
 * @author Administrator
 *主页
 */
public class Home implements Showable {
	
	private CanvasControl canvasControl;

	public Image img_back;
	public Image[] ima_chosed;
	public Image[] ima_unchose;
	
	/**
	 * 选择的下标值
	 * 0：选中开始游戏
	 * 1：选中加油站
	 * 2：选中英雄榜
	 * 3：选中游戏帮助
	 * 4：选中退出游戏
	 */
	private byte index;
	
	/**
	 * 选择标签的x坐标值数组
	 */
	private int[] lableX = {
			90,	
			200,
			60,
			130,
			90
	};

	public Home(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
		
		canvasControl.setNeedRepaint(true);
	}
	
	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		showLable(g);
		
		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION + JS_IPTV_PORP_TOOL.js_PropVersion, 10, 10, Graphics.BOTTOM | Graphics.LEFT);
	}
	
	/**
	 * 展示选择标签
	 * @param g 画笔类
	 */
	public void showLable(Graphics g) {
		for(int i=0; i<5; i++) {
			if(i == index) {
				g.drawImage(ima_chosed[i], lableX[i], 215 + i * 55, Graphics.VCENTER | Graphics.LEFT);
			} else {
				g.drawImage(ima_unchose[i], lableX[i], 215 + i * 55, Graphics.VCENTER | Graphics.LEFT);
			}
		}
	}

	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_DOWN:
			if(index < 4) index ++;
			break;
		case C.KEY_UP:
			if(index > 0) index --;
			break;
		case C.KEY_FIRE:
			switch(index) {
			case 0://开始游戏
			case 1://进入加油站
				GasStation.willStart = true;
//				canvasControl.setView(new GasStation(canvasControl, true));
//				canvasControl.setGoBackView(this);
//				this.removeResource();
				canvasControl.setView(new Loading(canvasControl, 0));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
//				canvasControl.setView(new GasStation(canvasControl, false));
//				canvasControl.setGoBackView(this);
//				this.removeResource();
//				GasStation.willStart = false;
//				canvasControl.setView(new Loading(canvasControl, 0));
//				canvasControl.setGoBackView(this);
//				this.removeResource();
//				break;
			case 2://进入排行榜
				canvasControl.setView(new Loading(canvasControl, 3));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
			case 3://游戏帮助
				canvasControl.setView(new Help(canvasControl, 0));
				canvasControl.setGoBackView(this);
//				this.removeResource();
				break;
			case 4://退出游戏
				canvasControl.setView(new Dialog(canvasControl, 0));
				canvasControl.setGoBackView(this);
//				this.removeResource();
				break;
			}
			break;
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_0:
			canvasControl.setView(new Dialog(canvasControl, 0));
			canvasControl.setGoBackView(this);
			this.removeResource();
			break;
		}
	}

	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void keyRepeated(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	public void loadResource() {
		/*try {
			img_back = Image.createImage("/home/back.png");
			
			Image temp_chosed = Image.createImage("/home/chosed.png");
			Image temp_unchose = Image.createImage("/home/unchose.png");
			int w = temp_chosed.getWidth();
			int h = temp_chosed.getHeight();
			int w_un = temp_unchose.getWidth();
			int h_un = temp_unchose.getHeight();
			
			ima_chosed = new Image[5];
			ima_unchose = new Image[5];
			
			for(int i=0; i<5; i++) {
				ima_chosed[i] = Image.createImage(temp_chosed, 0, h / 5 * i, w, h / 5, 0);
				ima_unchose[i] = Image.createImage(temp_unchose, 0, h_un / 5 * i, w_un, h_un / 5, 0);
			}
			
		} catch (IOException e) {
			System.out.println("无法找到图片");
			e.printStackTrace();
		}*/
		
		canvasControl.playerHandler.toPlay(0);
	}

	public void removeResource() {
		img_back = null;
		
		for(int i=0; i<5; i++) {
			ima_chosed[i] = null;
			ima_unchose[i] = null;
		}
		ima_chosed = null;
		ima_unchose =null;
		
		canvasControl.playerHandler.stop(0);
		
		System.gc();
	}

	public void logic() {
//		C.out("fuck");
	}
}
