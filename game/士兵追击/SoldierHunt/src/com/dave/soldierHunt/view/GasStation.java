package com.dave.soldierHunt.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;

public class GasStation implements Showable {
	private Image img_back;
	private Image img_uplevel_chosed;
	private Image img_uplevel_unchose;
	private Image img_upteam_chosed;
	private Image img_upteam_unchose;
	private Image img_start_unchose;
	private Image img_start_chosed;
	private Image img_number;
	private Image img_key_0;
	private Image img_select;
	
	
	/**
	 * 记录光标所选择的下标值
	 * 0为扩展队伍。
	 * 1为开始游戏。（从开始游戏按钮进入的）
	 * 2为升级英雄等级。
	 */
	private int index = 0;
	
	private boolean willStart;
	
	private CanvasControl canvasControl;

	public GasStation(CanvasControl canvasControl, boolean willStart) {
		this.canvasControl = canvasControl;
		this.willStart = willStart;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		if(willStart) {
			switch(index) {
			case 0:
				g.drawImage(img_upteam_chosed, 120, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_uplevel_unchose, 520, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_start_unchose, 320, 450, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 50, 400, Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 1:
				g.drawImage(img_upteam_unchose, 120, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_uplevel_unchose, 520, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_start_chosed, 320, 450, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 250, 450, Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 2:
				g.drawImage(img_upteam_unchose, 120, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_uplevel_chosed, 520, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_start_unchose, 320, 450, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 450, 400, Graphics.HCENTER | Graphics.BOTTOM);
				break;
			}
		} else {
			g.drawImage(img_upteam_unchose, 200, 380, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_uplevel_unchose, 440, 380, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		
		g.drawImage(img_key_0, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/gasStation/back.png");
			img_uplevel_chosed = Image.createImage("/gasStation/uplevel_chosed.png");
			img_uplevel_unchose = Image.createImage("/gasStation/uplevel_unchose.png");
			img_upteam_chosed = Image.createImage("/gasStation/upteam_chosed.png");
			img_upteam_unchose = Image.createImage("/gasStation/upteam_unchose.png");
			img_number = Image.createImage("/gasStation/number.png");
			img_key_0 = Image.createImage("/button/key_0.png");
			img_select = Image.createImage("/gasStation/select.png");
			
			if(willStart) {
				img_start_chosed = Image.createImage("/gasStation/start_chosed.png");
				img_start_unchose = Image.createImage("/gasStation/start_unchose.png");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_uplevel_chosed = null;
		img_uplevel_unchose = null;
		img_upteam_chosed = null;
		img_upteam_unchose = null;
		img_number = null;
		img_key_0 = null;
		img_select = null;
		
		if(willStart) {
			img_start_unchose = null;
			img_start_chosed = null;
		}
	}

	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT :
			if(willStart) {
				if(index > 0) index --;
			} else {
				if(index > 0) index -= 2;
			}
			break;
		case C.KEY_RIGHT :
			if(willStart) {
				if(index < 2) index ++;
			} else {
				if(index < 2) index += 2;
			}
			break;
		case C.KEY_FIRE :
			switch(index) {
			case 0:
				break;
			case 1:
				canvasControl.setView(new Game(canvasControl, CanvasControl.level));
				this.removeResource();
				break;
			case 2:
				break;
			}
			break;
		case C.KEY_0 :
		case C.KEY_BACK :
		case C.KEY_BACK_ZX :
			canvasControl.setView(canvasControl.getGoBackView());
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

	public void logic() {
		// TODO Auto-generated method stub

	}

}
