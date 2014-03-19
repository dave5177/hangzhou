package com.dave.ui;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.main.CanvasControl;
import com.dave.tool.C;

public class Home {

	private CanvasControl canvasControl;
	
	private Image img_Back;			//主页面背景图。
	private Image img_bar;			//选框图
	private Image img_chosed_start;
	private Image img_chosed_help;
	private Image img_chosed_activity;
	private Image img_unchose_start;
	private Image img_unchose_help;
	private Image img_unchose_activity;
	private static Image img_preKey; 	//按9键帮助，按0返回
	
	private byte index;						//记录选中位置，0为开始游戏，1为游戏帮助, 2为活动公告。
	
	
	public Home(CanvasControl canvasControl){
		this.canvasControl = canvasControl;
		loadAllSources();
	}
	
	public void removeAllThisClassSources(){
		img_Back = null;
		img_bar = null;
		img_chosed_start = null;
		img_unchose_start = null;
		img_chosed_help = null;
		img_unchose_help = null;
		img_preKey = null;
	}
	
	public void loadAllSources() {
		try {
			img_Back = Image.createImage("/home/back.png");
			img_bar = Image.createImage("/home/bar.png");
			Image all1 = Image.createImage("/home/chosed.png");
			Image all2 = Image.createImage("/home/unchose.png");
			img_chosed_start = Image.createImage(all1, 0, 0, all1.getWidth(), all1.getHeight()/3, 0);
			img_chosed_help = Image.createImage(all1, 0, all1.getHeight() / 3 * 2, all1.getWidth(), all1.getHeight()/3, 0);
			img_chosed_activity = Image.createImage(all1, 0, all1.getHeight() / 3, all1.getWidth(), all1.getHeight()/3, 0);
			img_unchose_start = Image.createImage(all2, 0, 0, all2.getWidth(), all2.getHeight()/3, 0);
			img_unchose_help = Image.createImage(all2, 0, all2.getHeight() / 3 * 2, all2.getWidth(), all2.getHeight()/3, 0);
			img_unchose_activity = Image.createImage(all2, 0, all2.getHeight() / 3, all2.getWidth(), all2.getHeight()/3, 0);
			img_preKey = Image.createImage("/home/back_help.png");
		} catch (IOException e) {
			System.out.println("can find picture");
			e.printStackTrace();
		}
	}
	
	public void keyPressed(int keyCode){
		switch(keyCode) {
		case C.KEY_UP : {
			if(index != 0) {
//				canvasControl.audioPlay.playSound((byte)0);
				index --;
			}
		} break;
		case C.KEY_DOWN : {
			if(index != 2) {
//				canvasControl.audioPlay.playSound((byte)0);
				index ++;
			}
		} break;
		case C.KEY_FIRE : {
//			canvasControl.audioPlay.playSound((byte)2);
			switch(index) {
			case 0 : {
				canvasControl.state = CanvasControl.NULL;
				canvasControl.setState(CanvasControl.GAME_PAGE);
//				canvasControl.game_page = new AutoGame(canvasControl);
//				canvasControl.repaint();
			} break;
			case 1 : {
				canvasControl.state = CanvasControl.NULL;
				canvasControl.state_Back_For = CanvasControl.HOME_PAGE;
				canvasControl.setState(CanvasControl.HELP_PAGE);
			} break;
			
			case 2 : {
				canvasControl.state = CanvasControl.NULL;
				canvasControl.si.doGetRank();
				canvasControl.si.doGetRankAll();
//				canvasControl.setState(CanvasControl.ACTIVITY_PAGE);
			} break;
			
			}
			break;
		}
		case C.KEY_BACK :
		case C.KEY_0 : {
			canvasControl.state_Back_For = CanvasControl.HOME_PAGE;
			canvasControl.state = CanvasControl.NULL;
			canvasControl.setState(CanvasControl.ALERT_PAGE);
			canvasControl.alert_page.type = C.A_TYPE_EXIT;
		} break;
		case C.KEY_9 :{
			canvasControl.state = CanvasControl.NULL;
			canvasControl.state_Back_For = CanvasControl.HOME_PAGE;
			canvasControl.setState(CanvasControl.HELP_PAGE);
		} break;
			
		}
	}
	
	public void showMe(Graphics canvas){
		canvas.drawImage(img_Back, 0, 0, Graphics.TOP|Graphics.LEFT);
		canvas.drawImage(img_bar, 200, 300, Graphics.TOP|Graphics.LEFT);
		canvas.drawImage(img_bar, 200, 350, Graphics.TOP|Graphics.LEFT);
		canvas.drawImage(img_bar, 200, 400, Graphics.TOP|Graphics.LEFT);
		canvas.drawImage(img_preKey, C.WIDTH, C.HEIGTH, Graphics.BOTTOM|Graphics.RIGHT);

		switch(index) {
		case 0 : {
			canvas.drawImage(img_chosed_start, 220, 310, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_unchose_help, 220, 360, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_unchose_activity, 220, 410, Graphics.TOP|Graphics.LEFT);
		} break;
		case 1 : {
			canvas.drawImage(img_unchose_start, 220, 310, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_chosed_help, 220, 360, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_unchose_activity, 220, 410, Graphics.TOP|Graphics.LEFT);
		} break;
		case 2 : {
			canvas.drawImage(img_unchose_start, 220, 310, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_unchose_help, 220, 360, Graphics.TOP|Graphics.LEFT);
			canvas.drawImage(img_chosed_activity, 220, 410, Graphics.TOP|Graphics.LEFT);
		} break;
		}
	}
}
