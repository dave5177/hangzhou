package com.dave.soldierHunt.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.net.Prop;
import com.dave.soldierHunt.tool.C;

public class Dialog implements Showable {
	private CanvasControl canvasControl;
	
	/**
	 * 对话框类型控制
	 * 0：退出整个程序对话框。
	 * 1：退出游戏对话框。
	 * 2: 牺牲了，买活吗?
	 * 3: 购买成功。
	 * 4：购买失败。
	 * 5：购买峰值。
	 */
	private int type;
	
	/**
	 * 记录选择用的下标值
	 * 0：是。（默认值）
	 * 1：否。
	 */
	private int index = 0;
		
	private Image img_back;
	private Image img_word;
	private Image img_yes_unchose;
	private Image img_no_unchose;
	private Image img_yes_chosed;
	private Image img_no_chosed;
	private Image img_number;
	private Image img_select;

	private Image img_coin;
	private Image img_rmb;

//	private int arrowX;

	public Dialog(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
		
		if(type == 3 || type == 4 || type == 5) {//购买成功、失败、峰值，自动关闭
			new Timer().schedule(new AutoCloseDialogTimerTask(), 2000);
		}
		
		canvasControl.setNeedRepaint(true);
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 320, 80, Graphics.TOP | Graphics.HCENTER);
		g.drawImage(img_word, 320, 270, Graphics.HCENTER | Graphics.VCENTER);
		
		if(type == 3 || type == 4 || type == 5) {//购买成功、失败、峰值，不现实按钮。
			return;
		}
		
		if(index == 0) {
			g.drawImage(img_yes_chosed, 240, 380, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_no_unchose, 400, 380, Graphics.HCENTER | Graphics.VCENTER);
		} else if(index == 1) {
			g.drawImage(img_yes_unchose, 240, 380, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_no_chosed, 400, 380, Graphics.HCENTER | Graphics.VCENTER);
		}
		g.drawImage(img_select, 200 + index * 160, 380, Graphics.HCENTER | Graphics.BOTTOM);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/dialog/back.png");
			img_yes_unchose = Image.createImage("/dialog/yes_unchose.png");
			img_no_unchose = Image.createImage("/dialog/no_unchose.png");
			img_yes_chosed = Image.createImage("/dialog/yes_chosed.png");
			img_no_chosed = Image.createImage("/dialog/no_chosed.png");
			img_number = Image.createImage("/dialog/number.png");
			img_select = Image.createImage("/dialog/select.png");
			
			loadWorldImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWorldImage() throws IOException {
		img_word = Image.createImage("/dialog/word_" + type + ".png");
	}
	
	public void removeResource() {
		img_back = null;
		img_word = null;
		img_yes_unchose = null;
		img_no_unchose = null;
		img_yes_chosed = null;
		img_no_chosed = null;
		img_number = null;
		img_select = null;
		
		if(img_coin != null) img_coin = null;
		if(img_rmb != null) img_rmb = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT:
			if(index > 0) index --;
			break;
		case C.KEY_RIGHT:
			if(index < 1) index ++;
			break;
		case C.KEY_FIRE:
			if(index == 0) {
				switch(type) {
				case 0://退出游戏
					canvasControl.getMidlet().exitGame();
					break;
				case 1:
					break;
				case 2://购买复活
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_M16);
					CanvasControl.goodsIndex = 0;
					break;
				}
			} else if(index == 1) {
				if(type == 2) {
					goToHome();
					return;
				}
				canvasControl.setView(canvasControl.getGoBackView());
				this.removeResource();
			}
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			if(type == 2) {
				goToHome();
				return;
			}
			canvasControl.setView(canvasControl.getGoBackView());
			this.removeResource();
			break;
		}
		
	}
	
	private void goToHome() {
		canvasControl.setView(new Home(canvasControl));
		this.removeResource();
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	class AutoCloseDialogTimerTask extends TimerTask {
		
		public void run() {
			if(type == 3) canvasControl.doAfterBuyGoods();
			canvasControl.setView(canvasControl.getGoBackView());
			Dialog.this.removeResource();
		}
	}


}
