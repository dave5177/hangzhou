package com.dave.soldierHunt.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.model.Hero;
import com.dave.soldierHunt.tool.C;

/**
 * @author Dave
 * 加油站
 */
public class GasStation implements Showable {
	public Image img_back;
	public Image img_uplevel_chosed;
	public Image img_uplevel_unchose;
	public Image img_upteam_chosed;
	public Image img_upteam_unchose;
	public Image img_start_unchose;
	public Image img_start_chosed;
	public Image img_number;
	public Image img_key_0;
	public Image img_select;
	private Image img_uplevelSuccess;
	private Image img_upteamSuccess;
	
	public Image[][] ima_hero;
	public Image[][] ima_soldier;
	public Image img_shadow;
	
	
	/**
	 * 记录光标所选择的下标值
	 * 0为扩展队伍。
	 * 1为开始游戏。（从开始游戏按钮进入的）
	 * 2为升级英雄等级。
	 */
	private int index = 0;
	
	/**
	 * 士兵数量
	 */
	public static int soldierCount;
	
	/**
	 * 士兵的x坐标
	 */
	private int heroX;
	private final int heroY = 150;
	
	/**
	 * 士兵图片下标值
	 */
	private int soldierImageIndex;
	
	/**
	 * 士兵方向
	 */
	private int soldierDir;
	
	/**
	 * 是否为开始游戏进入的状态
	 */
	public static boolean willStart;
	
	/**
	 * 升级成功
	 */
	public static boolean uplevelSuccess;
	/**
	 * 扩展成功
	 */
	public static boolean upteamSuccess;
	
	private CanvasControl canvasControl;
	
	/**
	 * 按下键时存储的上一个键。
	 */
	private int upIndex;
	
	/**
	 * 升级成功显示时间控制。
	 */
	private int timeControl;

	public GasStation(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		
		heroX = soldierCount * 40 + 80;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		
		g.drawImage(img_shadow, heroX, heroY + 25, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(ima_hero[soldierDir][soldierImageIndex], heroX, heroY, Graphics.HCENTER | Graphics.VCENTER);
		
		for(int i=0; i<soldierCount; i++) {
			g.drawImage(img_shadow, heroX - (i + 1) * 40, heroY + 25, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(ima_soldier[soldierDir][soldierImageIndex], heroX - (i + 1) * 40, heroY, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		for(int i=0; i<2; i++) {
			C.drawString(g, img_number, 600 + (Hero.level + i) * 100 + "", 
					"0123456789", 130 + i * 377, 295, 14, 20, 0, 0, 0);
		}
		
		for(int i=0; i<2; i++) {
			C.drawString(g, img_number, 80 + (Hero.level + i) * 50 + "", 
					"0123456789", 160 + i * 377, 342, 14, 20, 0, 0, 0);
		}
		
		if(willStart) {
			switch(index) {
			case 0:
				g.drawImage(img_uplevel_chosed, 120, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_upteam_unchose, 520, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_start_unchose, 320, 450, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 50, 400, Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 1:
				g.drawImage(img_uplevel_unchose, 120, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_upteam_unchose, 520, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_start_chosed, 320, 450, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 250, 450, Graphics.HCENTER | Graphics.BOTTOM);
				break;
			case 2:
				g.drawImage(img_uplevel_unchose, 120, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_upteam_chosed, 520, 400, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_start_unchose, 320, 450, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 450, 400, Graphics.HCENTER | Graphics.BOTTOM);
				break;
			}
		} else {
			if(index == 0) {
				g.drawImage(img_uplevel_chosed, 150, 420, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_upteam_unchose, 490, 420, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 80, 420, Graphics.HCENTER | Graphics.BOTTOM);
			} else {
				g.drawImage(img_uplevel_unchose, 150, 420, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_upteam_chosed, 490, 420, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_select, 420, 420, Graphics.HCENTER | Graphics.BOTTOM);
				
			}
		}
		
		if(uplevelSuccess) {
			g.drawImage(img_uplevelSuccess, 320, 200, Graphics.HCENTER | Graphics.VCENTER);
		}
		if(upteamSuccess) {
			g.drawImage(img_upteamSuccess, 320, 200, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		g.drawImage(img_key_0, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public void loadResource() {
		try {
//			img_back = Image.createImage("/gasStation/back.png");
//			img_uplevel_chosed = Image.createImage("/gasStation/uplevel_chosed.png");
//			img_uplevel_unchose = Image.createImage("/gasStation/uplevel_unchose.png");
//			img_upteam_chosed = Image.createImage("/gasStation/upteam_chosed.png");
//			img_upteam_unchose = Image.createImage("/gasStation/upteam_unchose.png");
//			img_number = Image.createImage("/gasStation/number.png");
			img_key_0 = Image.createImage("/button/key_0.png");
			img_uplevelSuccess = Image.createImage("/gasStation/uplevelSuccess.png");
			img_upteamSuccess = Image.createImage("/gasStation/upteamSuccess.png");
//			img_select = Image.createImage("/gasStation/select.png");
//			
			ima_hero = new Image[2][4];
			for(int i=0; i<2; i++) {
				Image img_heroMove = Image.createImage("/hero/0_move_" + (i * 2 + 1) + ".png");
				int w = img_heroMove.getWidth();
				int h = img_heroMove.getHeight();
				for(int j=0; j<4; j++) {
					ima_hero[i][j] = Image.createImage(img_heroMove, (w >> 2) * j, 0, w >> 2, h, 0);
				}
			}
			
			ima_soldier = new Image[2][4];
			for(int i=0; i<2; i++) {
				Image img_soldierMove = Image.createImage("/hero/2_move_" + (i * 2 + 1) + ".png");
				int w = img_soldierMove.getWidth();
				int h = img_soldierMove.getHeight();
				for(int j=0; j<4; j++) {
					ima_soldier[i][j] = Image.createImage(img_soldierMove, (w >> 2) * j, 0, w >> 2, h, 0);
				}
			}
			
			img_shadow = Image.createImage("/hero/shadow.png");
			
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
		img_uplevelSuccess = null;
		img_upteamSuccess = null;
		
		for(int i=0; i<2; i++) {
			for(int j=0; j<4; j++) {
				ima_hero[i][j] = null;
				ima_soldier[i][j] = null;
			}
		}
		
		if(willStart) {
			img_start_unchose = null;
			img_start_chosed = null;
		}
		
		System.gc();
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
		case C.KEY_DOWN :
			if(willStart && index != 1) {
				upIndex = index;
				index = 1;
			}
			break;
		case C.KEY_UP :
			if(willStart && index == 1) {
				if(upIndex == 0) index = 2;
				else index = 0;
			}
			break;
		case C.KEY_FIRE :
			switch(index) {
			case 0:
				canvasControl.setView(new Dialog(canvasControl, 12));
				canvasControl.setGoBackView(this);
//				this.removeResource();
				break;
			case 1:
//				canvasControl.setView(new Game(canvasControl, CanvasControl.level, soldierCount));
				canvasControl.setView(new Loading(canvasControl, 1));
				this.removeResource();
				break;
			case 2:
				if(soldierCount < 10)
					canvasControl.setView(new Dialog(canvasControl, 11));
				else 
					canvasControl.setView(new Dialog(canvasControl, 13));
				
				canvasControl.setGoBackView(this);
//				this.removeResource();
				break;
			}
			break;
		case C.KEY_0 :
		case C.KEY_BACK :
		case C.KEY_BACK_ZX :
//			canvasControl.setView(new Home(canvasControl));
			canvasControl.setView(new Loading(canvasControl, 2));
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
		if(soldierDir == 0) {
			heroX += 5;
			
			if(heroX > 560) soldierDir = 1;
		}
		else {
			heroX -= 5;
			if(heroX - soldierCount * 40 < 80) soldierDir = 0;
		}
		
		soldierImageIndex ++;
		if(soldierImageIndex > 3) soldierImageIndex = 0;
		
		if(uplevelSuccess) {
			timeControl ++;
			if(timeControl > 8){
				uplevelSuccess = false;
				timeControl = 0;
			}
		}
		if(upteamSuccess) {
			timeControl ++;
			if(timeControl > 8){
				upteamSuccess = false;
				timeControl = 0;
			}
		}
		
		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

}
