package com.dave.soldierHunt.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.net.ServerIptv;
import com.dave.soldierHunt.tool.C;

public class Dialog implements Showable {
	public static Game game;

	private CanvasControl canvasControl;
	
	/**
	 * 对话框类型控制
	 * 0：退出整个程序对话框。
	 * 1：退出游戏对话框。
	 * 2: 牺牲了，买活吗?
	 * 3: 购买成功。
	 * 4：购买失败。
	 * 5：购买峰值。
	 * 6: 购买生命药水。
	 * 7：购买炸弹。
	 * 8：购买无敌。
	 * 9：购买吸铁石。
	 * 10：增加士兵。
	 * 11: 扩展队伍。
	 * 12：英雄升级。
	 * 13：队伍已满。
	 * 14：过关。
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
//	private Image img_nextLevel;
	
	private Image[] ima_cButton;
	private Image[] ima_uButton;

//	private Image img_coin;
	private Image img_rmb;

	private int buttonIndex;

//	private int arrowX;

	public Dialog(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
		
		if(type == 3 || type == 4 || type == 5 || type == 13) {//购买成功、失败、峰值、队伍已满，自动关闭
			new Timer().schedule(new AutoCloseDialogTimerTask(), 2000);
		}
		
		canvasControl.setNeedRepaint(true);
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 320, 80, Graphics.TOP | Graphics.HCENTER);
		if(type == 14) {
			g.drawImage(img_word, 320, 230, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(img_word, 320, 270, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		if(type == 3 || type == 4 || type == 5 || type == 13) {//购买成功、失败、峰值、队伍已满，不现实按钮。
		} else if(type == 14) {
//			C.drawString(g, img_number, Game.coinCount + "", "0123456789.", 300, 290, 12, 18, 0, 0, 0);
			
			for(int i=0; i<3; i++) {
				g.drawImage(ima_uButton[i], 320, 280 + i * 50, Graphics.HCENTER | Graphics.VCENTER);
			}
			g.drawImage(ima_cButton[buttonIndex], 320, 280 + buttonIndex * 50, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_select, 260, 280 + buttonIndex * 50, Graphics.HCENTER | Graphics.BOTTOM);
			
//			g.drawImage(img_nextLevel, 320, 380, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			if(index == 0) {
				g.drawImage(img_yes_chosed, 240, 380, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_no_unchose, 400, 380, Graphics.HCENTER | Graphics.VCENTER);
			} else if(index == 1) {
				g.drawImage(img_yes_unchose, 240, 380, Graphics.HCENTER | Graphics.VCENTER);
				g.drawImage(img_no_chosed, 400, 380, Graphics.HCENTER | Graphics.VCENTER);
			}
			g.drawImage(img_select, 200 + index * 160, 380, Graphics.HCENTER | Graphics.BOTTOM);
		}
		
		showGoodsPrice(g);
		
	}

	public void showGoodsPrice(Graphics g) {
		switch(type) {
		case 2://复活
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[0]), "0123456789.", 220, 300, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 260, 300, 0);
			break;
		case 6://生命药水
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[1]), "0123456789.", 370, 261, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 420, 261, 0);
			break;
		case 7://炸弹
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[2]), "0123456789.", 430, 260, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 180, 295, 0);
			break;
		case 8://无敌
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[3]), "0123456789.", 220, 280, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 260, 280, 0);
			break;
		case 9://吸铁石
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[4]), "0123456789.", 190, 280, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 220, 280, 0);
			break;
		case 10://增加士兵
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[5]), "0123456789.", 300, 290, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 300, 290, 0);
			break;
		case 11://扩展队伍
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[6]), "0123456789.", 205, 277, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 220, 277, 0);
			break;
		case 12://升级
			C.drawString(g, img_number, "100", "0123456789.", 190, 263, 12, 18, 0, 0, 0);
			C.drawString(g, img_number, "50", "0123456789.", 400, 263, 12, 18, 0, 0, 0);
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[7]), "0123456789.", 260, 296, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 295, 294, 0);
			break;
		}
		
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
//			img_nextLevel = Image.createImage("/dialog/nextLevel.png");
//			img_coin = Image.createImage("/dialog/nextLevel.png");
			img_rmb = Image.createImage("/dialog/rmb.png");
			
			loadWorldImage();
			
			if(type == 14) loadCompeletButton();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载过关的按钮
	 */
	public void loadCompeletButton() {
		ima_cButton = new Image[3];
		ima_uButton = new Image[3];
		try {
			for(int i=0; i<3; i++) {
				ima_cButton[i] = Image.createImage("/dialog/cButton_" + i + ".png");
				ima_uButton[i] = Image.createImage("/dialog/uButton_" + i + ".png");
			}
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
//		img_nextLevel = null;
		
//		img_coin = null;
		img_rmb = null;
		
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
		case C.KEY_DOWN:
			if(type == 14 && buttonIndex < 2) buttonIndex ++;
			break;
		case C.KEY_UP:
			if(type == 14 && buttonIndex > 0) buttonIndex --;
			break;
		case C.KEY_FIRE:
			if(index == 0) {
				switch(type) {
				case 0://退出整个游戏
//					canvasControl.getMidlet().exitGame();
					CanvasControl.willExit = true;
					new ServerIptv(canvasControl).sendGameTimeInfo(CanvasControl.appStartTime + CanvasControl.iptvID, "1", "update");
					break;
				case 1://退出正在进行的游戏
					game.removeOtherImage();
					game.removeWeakImage();
					game = null;
					goToHome();
					break;
				case 2://购买复活
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 0;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e7) {
						e7.printStackTrace();
					}
					break;
				case 6://购买生命药水
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 1;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e6) {
						e6.printStackTrace();
					}
					break;
				case 7://购买炸弹
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 2;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e5) {
						e5.printStackTrace();
					}
					break;
				case 8://购买无敌
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 3;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e4) {
						e4.printStackTrace();
					}
					break;
				case 9://购买吸铁石
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 4;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e3) {
						e3.printStackTrace();
					}
					break;
				case 10://加入一个士兵
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 5;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					break;
				case 11://扩展队伍
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 6;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 12://升级英雄
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 7;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			} else if(index == 1) {
				if(type == 2) {
					game.removeOtherImage();
					game = null;
					GasStation.willStart = true;
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Loading(canvasControl, 0));
					return;
				}
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(canvasControl.getGoBackView());
			}
			
			if(type == 14) {//下一关
//				GasStation.willStart = true;
//				canvasControl.setView(canvasControl.nullView);
//				this.removeResource();
//				canvasControl.setView(new Loading(canvasControl, 0));
				switch(buttonIndex) {
				case 0://升级
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 12));
					canvasControl.setGoBackView(this);
					break;
				case 1://扩展队伍
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					if(GasStation.soldierCount < 10)
						canvasControl.setView(new Dialog(canvasControl, 11));
					else 
						canvasControl.setView(new Dialog(canvasControl, 13));
					
					canvasControl.setGoBackView(this);
					break;
				case 2://下一关
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Loading(canvasControl, 1));
					break;
				}
			}
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			if(type == 2) {
				game.removeOtherImage();
				game = null;
				GasStation.willStart = true;
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Loading(canvasControl, 0));
				return;
			} else if(type == 14) {
				return;
			}
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
			break;
		}
		
	}
	
	/**
	 * 返回主页
	 */
	private void goToHome() {
//		canvasControl.setView(new Home(canvasControl));
		canvasControl.setView(canvasControl.nullView);
		this.removeResource();
		canvasControl.setView(new Loading(canvasControl, 2));
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	class AutoCloseDialogTimerTask extends TimerTask {
		
		public void run() {
			if(type == 3) canvasControl.doBuySuccess();
			
			canvasControl.setView(canvasControl.nullView);
			Dialog.this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
		}
	}


}
