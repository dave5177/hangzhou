package com.dave.rangzidanf.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.gameModel.Gun;
import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.prop.Prop;
import com.dave.rangzidanf.tool.C;

public class Dialog implements Showable {
	private CanvasControl canvasControl;
	
	/**
	 * 对话框类型控制
	 * 0：退出整个程序对话框。
	 * 1：退出游戏对话框。
	 * 2：购买枪支。
	 * 3：购买成功。
	 * 4：购买失败。
	 * 5：是否准备好上前线了。
	 * 6：购买高爆子弹。
	 * 7：购买隐身衣。
	 * 8：购买自动瞄准。
	 * 9：购买自动换子弹。
	 * 10：当月购买已达到峰值。
	 * 11：云南白药。
	 * 12：真的就这样放弃了吗。
	 * 13：傻逼乱开枪，买子弹去吧。
	 * 14：是否使用该枪，开始游戏
	 */
	private int type;
	
	/**
	 * 记录选择用的下标值
	 * 0：是。（默认值）
	 * 1：否。
	 */
	private int index = 0;
	
	/**
	 * 记录购买的枪的下标值。
	 */
	private int gunIndex;
	
	/**
	 * 是否使用该枪的时候的枪图片
	 */
	private Image img_gun;
	
	private String[] gunPrice = {//rmb价格
			"0.2",
			"0.5",
			"1",
			"1",
			"1",
			"1"
	};
		
	private String[] goodsPrice = {//道具价格
			"1",//高爆
			"1",
			"2",
			"1",
			"0.5",
			"1"
	};
	
	//枪金币价格
		/*{
			20,
			15,
			0,
			15,
			25,
			30
	};*/
	
	private Image img_back;
	private Image img_word;
	private Image img_yesWord;
	private Image img_noWord;
	private Image img_chosedBar;
	private Image img_unchoseBar;
	private Image img_number;
	private Image img_arrow;

	private Image img_coin;
	private Image img_rmb;

//	private int arrowX;

	public Dialog(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		canvasControl.setNeedRepaint(true);
		
		this.type = type;
		
		if(type == 3 || type == 4 || type == 10) {
			new Timer().schedule(new AutoCloseDialogTimerTask(), 1000);
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, C.WIDTH / 2, 85, Graphics.TOP | Graphics.HCENTER);//draw背景
		
		if(type == 14) {
			g.drawImage(img_word, C.WIDTH / 2, 150, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(img_gun, C.WIDTH / 2, 180, Graphics.TOP | Graphics.HCENTER);//draw枪的图片
		} else g.drawImage(img_word, C.WIDTH / 2, 200, Graphics.VCENTER | Graphics.HCENTER);//draw对话框文字
		
		if(type == 3 || type == 4 || type == 10) return;
		
		showGoodsPrice(g);
		
		//draw选择bar
		if(index == 0){
			g.drawImage(img_chosedBar, 250, 300, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_unchoseBar, 410, 300, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(img_chosedBar, 410, 300, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_unchoseBar, 250, 300, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		if(type == 11) {//只剩多少个敌人了
			C.drawString(g, img_number, CanvasControl.restEnemy + "",
					"0123456789.", 240, 193, 13, 18, 0, 0, 0);
		}
		
		if(type == 2) {
			showGunPrice(g);
		}
		
		g.drawImage(img_arrow, 200 + index * 160, 300, Graphics.RIGHT | Graphics.VCENTER);
		g.drawImage(img_yesWord, 250, 300, Graphics.HCENTER | Graphics.VCENTER);//draw文字”是“
		g.drawImage(img_noWord, 410, 300, Graphics.HCENTER | Graphics.VCENTER);//draw文字”否“
	}

	/**
	 * draw枪的价格
	 * @param g
	 */
	private void showGunPrice(Graphics g) {
//C.out("数字图片宽度------" + img_number.getWidth());
//C.out("数字图片---高度---" + img_number.getHeight());
//		C.drawString(g, img_number, gunPrice[gunIndex] + "", "0123456789.", 410, 191, 13, 18, 0, 0, 0);
//		if(img_coin == null){
//			try {
//				img_coin = Image.createImage("/dialog/coin.png");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		g.drawImage(img_coin, 190, 215, 0);
		
		C.drawString(g, img_number, gunPrice[gunIndex], "0123456789.", 360, 171, 13, 18, 0, 0, 0);
		if(img_rmb == null){
			try {
				img_rmb = Image.createImage("/dialog/rmb.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g.drawImage(img_rmb, 420, 171, 0);
	}

	/**
	 * @param g
	 * draw道具的价格
	 */
	private void showGoodsPrice(Graphics g) {
		if(img_rmb == null){
			try {
				img_rmb = Image.createImage("/dialog/rmb.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		switch(type) {
		case 6://高爆价格
			C.drawString(g, img_number, goodsPrice[0], "0123456789.", 205, 228, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 255, 228, 0);
			break;
		case 7://隐身衣价格
			C.drawString(g, img_number, goodsPrice[1], "0123456789.", 280, 193, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 330, 193, 0);
			break;
		case 8://自动瞄准价格
			C.drawString(g, img_number, goodsPrice[2], "0123456789.", 360, 193, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 410, 193, 0);
			break;
		case 9://自动换子弹价格
			C.drawString(g, img_number, goodsPrice[3], "0123456789.", 280, 193, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 330, 193, 0);
			break;
		case 11://复活价格
			C.drawString(g, img_number, goodsPrice[4], "0123456789.", 260, 155, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 310, 155, 0);
			break;
		case 13://子弹价格
			C.drawString(g, img_number, goodsPrice[5], "0123456789.", 190, 185, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 230, 185, 0);
			break;
		}
	}
	
	public void loadResource() {
		try {
			img_back = Image.createImage("/dialog/back.png");
			img_yesWord = Image.createImage("/dialog/yesWord.png");
			img_noWord = Image.createImage("/dialog/noWord.png");
			img_chosedBar = Image.createImage("/dialog/chosedBar.png");
			img_unchoseBar = Image.createImage("/dialog/unchoseBar.png");
			img_number = Image.createImage("/dialog/number.png");
			img_arrow = Image.createImage("/button/arrow.png");
			
			loadWorldImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWorldImage() throws IOException {
		img_word = Image.createImage("/dialog/word_" + type + ".png");
		/*switch(type) {
		case 0 :
		case 1 :
			img_word = Image.createImage("/dialog/exitWord.png");
			break;
		}*/
	}
	
	public void removeResource() {
		img_back = null;
		img_word = null;
		img_yesWord = null;
		img_noWord = null;
		img_chosedBar = null;
		img_unchoseBar = null;
		img_number = null;
		img_arrow = null;
		
		if(img_coin != null) img_coin = null;
		if(img_rmb != null) img_rmb = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_FIRE :
			if(index == 0) {
				switch (type) {
				case 0 ://退出游戏
					canvasControl.getMidlet().exitGame();
					break;
				case 1 ://返回，到武器库
					canvasControl.setView(new Arsenal(canvasControl));
					canvasControl.setGoBackView(null);
					this.removeResource();
					break;
				case 2 ://买枪了
					switch(gunIndex) {
					case 0://买m16，买不了的，傻逼。
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_M16);
						CanvasControl.goodsIndex = 0;
						break;
					case 1://买am
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AT15);
						CanvasControl.goodsIndex = 1;
						break;
					case 2://买AK
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AK47);
						CanvasControl.goodsIndex = 2;
						break;
					case 3://买M4
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_M4A1S);
						CanvasControl.goodsIndex = 3;
						break;
					case 4://买大狙
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AWM);
						CanvasControl.goodsIndex = 4;
						break;
					case 5://买大炮
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_BARRETT);
						CanvasControl.goodsIndex = 5;
						break;
					}
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					canvasControl.setGunIndex(gunIndex);
//					this.removeResource();
					break;
				case 5 ://准备好上前线了
					CanvasControl.firstTimes = 0;
					CanvasControl.highboom = 0;
					CanvasControl.autocb = 0;
					
					canvasControl.setView(new Game(canvasControl, CanvasControl.level, new Gun(canvasControl.getGunIndex())));
					canvasControl.setGoBackView(this);
					this.removeResource();
					break;
				case 6 ://买高爆了
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_HighBoom);
					CanvasControl.goodsIndex = 6;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					CanvasControl.highboom = 1;
//					this.removeResource();
					break;
				case 7 ://买隐身了
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_Stealth);
					CanvasControl.goodsIndex = 7;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					this.removeResource();
					break;
				case 8 ://买自动瞄准了
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AutoAim);
					CanvasControl.goodsIndex = 8;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					this.removeResource();
					break;
				case 9 ://买自动换子弹了
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AutoCB);
					CanvasControl.goodsIndex = 9;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					this.removeResource();
					break;
				case 11://买云南白药
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_Relive);
					CanvasControl.goodsIndex = 10;
					break;
				case 12://屌丝只有返回主菜单了
					canvasControl.setView(new Home(canvasControl));
					canvasControl.setGoBackView(this);
					this.removeResource();
					break;
				case 13://买子弹咯
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_Bullet);
					CanvasControl.goodsIndex = 11;
					break;
				case 14://使用该枪，开始游戏
					canvasControl.setView(new Game(canvasControl, CanvasControl.level, new Gun(canvasControl.getGunIndex())));
					this.removeResource();
					break;
				}
				System.gc();
			} else {
				canvasControl.setView(canvasControl.getGoBackView());
				canvasControl.setGoBackView(this);
				this.removeResource();
			}
			break;
		case C.KEY_LEFT :
			if(index == 1) index = 0;
			break;
		case C.KEY_RIGHT :
			if(index == 0) index = 1;
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			index = 0;
			canvasControl.setView(canvasControl.getGoBackView());
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

	public void logic() {
	}

	public final Dialog setGunIndex(int gunIndex) {
		this.gunIndex = gunIndex;
		return this;
	}
	
	/**
	 * 14，是否使用该枪的时候需要显示枪的图片
	 * @param img_gun
	 * @return
	 */
	public final Dialog setGunImage(Image img_gun) {
		this.img_gun = img_gun;
		return this;
	}

	class AutoCloseDialogTimerTask extends TimerTask {
		
		public void run() {
			canvasControl.setView(canvasControl.getGoBackView());
			canvasControl.repaint();
			Dialog.this.removeResource();
		}
	}


}
