package com.dave.worldWar.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.net.ServerIptv;
import com.dave.worldWar.tool.C;

public class Dialog implements Showable {
	private CanvasControl canvasControl;
	
	/**
	 * 所在游戏
	 */
	private Game game;
	
	/**
	 * 对话框处在的界面
	 */
	private Showable lastView;
	
	/**
	 * 对话框类型控制
	 * 0：退出整个程序对话框。
	 * 1：退出游戏对话框。
	 * 3：购买成功。
	 * 4：购买失败。
	 * 5：购买道具。
	 * 6：闯关成功。
	 * 7：闯关失败。
	 * 10：当月购买已达到峰值。
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
	private Image img_yesWord;
	private Image img_noWord;
	private Image img_button;
	private Image img_number;
	private Image img_pointer;
	
	private Image img_goods_name;
	private Image img_goods_info;
	private Image img_word_soldier;
	private Image[] a_img_info_zj;

	private Image img_unit_price;//价格单位

	/**
	 * u/g
	 */
	private Image img_country;
	
	/**
	 * 道具价格。
	 */
	private String price;

	public Dialog(CanvasControl canvasControl, int type, Showable lastView) {
		this.canvasControl = canvasControl;
		this.lastView = lastView;
		
		this.type = type;
		
		if(type == 3 || type == 4 || type == 10) {
			new Timer().schedule(new AutoCloseDialogTimerTask(), 1000);
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, C.WIDTH / 2, 254, Graphics.VCENTER | Graphics.HCENTER);//draw背景
		
		if(type == 5) {
			g.drawImage(a_img_info_zj[1], C.WIDTH / 2, 128, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(a_img_info_zj[0], C.WIDTH / 2, 220, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(a_img_info_zj[2], C.WIDTH / 2, 375, Graphics.VCENTER | Graphics.HCENTER);
			C.drawString(g, img_number, CanvasControl.userCoin, "0123456789", C.WIDTH / 2 + 80, 368, 11, 17, 0, 0, 0);
			
			g.drawImage(img_goods_name, 235, 157, 0);
			g.drawImage(img_goods_info, 235, 217, 0);
			showGoodsPrice(g);
			
			if(CanvasControl.goodsIndex < 5) { //解锁
				g.drawImage(img_word_soldier, 274, 157, 0);
			} else if(CanvasControl.goodsIndex < 10) {//升级
				g.drawImage(img_word_soldier, 274, 157, 0);
				g.drawImage(img_word_soldier, 319, 215, Graphics.TOP | Graphics.HCENTER);
				C.drawString(g, img_number,  "50", "0123456789", 445, 218, 11, 17, 0, 0, 0);
				C.drawString(g, img_number,  "50", "0123456789", 330, 240, 11, 17, 0, 0, 0);
			}
		} else {
			g.drawImage(img_word, C.WIDTH / 2, 220, Graphics.VCENTER | Graphics.HCENTER);//draw对话框文字
		}
		
		if(type == 3 || type == 4 || type == 10) return;
		
		if(type == 6 || type == 7) {
			g.drawImage(img_country, 305, 181, 0);
			C.drawString(g, img_number,  (5 - Game.remain_create) + "", "0123456789", 335, 202, 11, 17, 0, 0, 0);
			C.drawString(g, img_number, (Game.useTime / 10)+ "", "0123456789", 355, 223, 11, 17, 0, 0, 0);
			C.drawString(g, img_number, CanvasControl.score_mission + "", "0123456789", 345, 244, 11, 17, 0, 0, 0);
		}
		
		//draw选择bar
		g.drawImage(img_button, 250, 320, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(img_button, 410, 320, Graphics.HCENTER | Graphics.VCENTER);
		
		g.drawImage(img_pointer, 250 + index * 160, 320, Graphics.HCENTER | Graphics.VCENTER);
		g.drawImage(img_yesWord, 250, 320, Graphics.HCENTER | Graphics.VCENTER);//draw文字”是“
		g.drawImage(img_noWord, 410, 320, Graphics.HCENTER | Graphics.VCENTER);//draw文字”否“
		
	}

	/**
	 * @param g
	 * draw道具的价格
	 */
	private void showGoodsPrice(Graphics g) {
		C.drawString(g, img_number, price, "0123456789.", 235, 189, 11, 17, 0, 0, 0);
		g.drawImage(img_unit_price, 235 + price.length() * 11, 187, 0);
	}
	
	public void loadResource() {
		try {
			img_back = Image.createImage("/dialog/back.png");
			img_number = Image.createImage("/dialog/number.png");
			img_pointer = Image.createImage("/dialog/pointer.png");
			img_button = Image.createImage("/dialog/button.png");
			if(CanvasControl.group == 1) {
				img_country = Image.createImage("/dialog/country_g.png");
			} else if(CanvasControl.group == 2) {
				img_country = Image.createImage("/dialog/country_u.png");
			}
			
			if(type == 6) {
				img_yesWord = Image.createImage("/dialog/wd_btn_stro.png");
				img_noWord = Image.createImage("/dialog/wd_btn_next.png");
			} else if(type == 7) {
				img_yesWord = Image.createImage("/dialog/wd_btn_relive.png");
				img_noWord = Image.createImage("/dialog/wd_btn_stro.png");
			} else {
				img_yesWord = Image.createImage("/dialog/yesWord.png");
				img_noWord = Image.createImage("/dialog/noWord.png");
			}
			if(type == 5) {
				price = canvasControl.anHui_Tool.getPrice(CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][0]);
				
				img_unit_price = Image.createImage("/dialog/yuan_bao.png");
				if(CanvasControl.goodsIndex < 5) {//解锁
					img_goods_name = Image.createImage("/dialog/goods_name_0.png");
					img_goods_info = Image.createImage("/dialog/goods_info_0.png");
					img_word_soldier = Image.createImage("/dialog/soldier_" + CanvasControl.goodsIndex + ".png");
				}else if(CanvasControl.goodsIndex < 10) {//升级
					img_goods_name = Image.createImage("/dialog/goods_name_uplevel.png");
					img_goods_info = Image.createImage("/dialog/goods_info_5.png");
					img_word_soldier = Image.createImage("/dialog/soldier_" + (CanvasControl.goodsIndex - 5) + ".png");
				}else if(CanvasControl.goodsIndex < 15) { //派兵
					img_goods_name = Image.createImage("/dialog/goods_name_10.png");
					img_goods_info = Image.createImage("/dialog/goods_info_" + CanvasControl.goodsIndex + ".png");
				} else {
					img_goods_name = Image.createImage("/dialog/goods_name_" + CanvasControl.goodsIndex + ".png");
					img_goods_info = Image.createImage("/dialog/goods_info_" + CanvasControl.goodsIndex + ".png");
				}
				
				a_img_info_zj = new Image[3];
				for (int i = 0; i < a_img_info_zj.length; i++) {
					a_img_info_zj[i] = Image.createImage("/dialog/info_" + i + ".png");
				}
			} else {
				loadWorldImage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWorldImage() throws IOException {
		if(type == 1) {
			img_word = Image.createImage("/dialog/word_0.png");
		} else {
			img_word = Image.createImage("/dialog/word_" + type + ".png");
		}
	}
	
	public void removeResource() {
		img_back = null;
		img_word = null;
		img_yesWord = null;
		img_noWord = null;
		img_button = null;
		img_number = null;
		img_pointer = null;
		
		if(type == 5) {
			img_unit_price = null;
			img_goods_name = null;
			img_goods_info = null;
			
			for (int i = 0; i < a_img_info_zj.length; i++) {
				a_img_info_zj[i] = null;
			}
			a_img_info_zj = null;
		}
		
		System.gc();
	}
	
	/**
	 * 设置游戏界面
	 * @param game
	 */
	public Dialog setGame(Game game) {
		this.game = game;
		return this;
	}

	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_FIRE :
			if(index == 0) {
				switch (type) {
				case 0 ://退出游戏
					CanvasControl.willExit = true;
					new ServerIptv(canvasControl).sendGameTimeInfo(CanvasControl.appStartTime + CanvasControl.iptvID, "1", "update");
					break;
				case 1 ://返回，到首页
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Loading(canvasControl, 0));
					break;
				case 5://购买道具
//					canvasControl.setView(canvasControl.nullView);
//					this.removeResource();
					canvasControl.anHui_Tool.buyProp(CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][0]);
					break;
				case 6:
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Loading(canvasControl, 2));
					break;
				case 7:
//					canvasControl.setView(canvasControl.nullView);
//					this.removeResource();
					canvasControl.anHui_Tool.buyProp(CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][0]);					
					break;
				}
				System.gc();
			} else {
				if(type == 6)
					game.initMession();
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				if(type == 7) {
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(new Loading(canvasControl, 2));
				}
				else
					canvasControl.setView(canvasControl.getGoBackView());
			}
			break;
		case C.KEY_LEFT :
			if(index == 1 && type != 8) index = 0;
			break;
		case C.KEY_RIGHT :
			if(index == 0 && type != 8) index = 1;
			break;
		case C.KEY_UP :
			if(index == 1 && type == 8) index = 0;
			break;
		case C.KEY_DOWN :
			if(index == 0 && type == 8) index = 1;
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			if(type == 7 || type == 6) {
				lastView.removeServerImage();
				canvasControl.setGoBackView(null);
				canvasControl.setView(new Loading(canvasControl, 0));
			}
			else
				canvasControl.setView(canvasControl.getGoBackView());
			index = 0;
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	class AutoCloseDialogTimerTask extends TimerTask {
		
		public void run() {
			if(type == 3)
				canvasControl.doBuySuccess();
			else if(type == 2) {
				if(CanvasControl.mission <= 25) {
//					game.initMession();
				} else {
//					canvasControl.setView(new Complete(canvasControl));
					canvasControl.repaint();
					Dialog.this.removeResource();
					return;
				}
			}
			
			canvasControl.setView(canvasControl.getGoBackView());
			canvasControl.repaint();
			Dialog.this.removeResource();
		}
	}

	
	public void removeServerImage() {
		
	}


}
