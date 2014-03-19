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
	 * �Ի������Ϳ���
	 * 0���˳���������Ի���
	 * 1���˳���Ϸ�Ի���
	 * 2: �����ˣ������?
	 * 3: ����ɹ���
	 * 4������ʧ�ܡ�
	 * 5�������ֵ��
	 * 6: ��������ҩˮ��
	 * 7������ը����
	 * 8�������޵С�
	 * 9����������ʯ��
	 * 10������ʿ����
	 * 11: ��չ���顣
	 * 12��Ӣ��������
	 * 13������������
	 * 14�����ء�
	 */
	private int type;
	
	/**
	 * ��¼ѡ���õ��±�ֵ
	 * 0���ǡ���Ĭ��ֵ��
	 * 1����
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
		
		if(type == 3 || type == 4 || type == 5 || type == 13) {//����ɹ���ʧ�ܡ���ֵ�������������Զ��ر�
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
		
		if(type == 3 || type == 4 || type == 5 || type == 13) {//����ɹ���ʧ�ܡ���ֵ����������������ʵ��ť��
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
		case 2://����
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[0]), "0123456789.", 220, 300, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 260, 300, 0);
			break;
		case 6://����ҩˮ
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[1]), "0123456789.", 370, 261, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 420, 261, 0);
			break;
		case 7://ը��
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[2]), "0123456789.", 430, 260, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 180, 295, 0);
			break;
		case 8://�޵�
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[3]), "0123456789.", 220, 280, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 260, 280, 0);
			break;
		case 9://����ʯ
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[4]), "0123456789.", 190, 280, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 220, 280, 0);
			break;
		case 10://����ʿ��
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[5]), "0123456789.", 300, 290, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 300, 290, 0);
			break;
		case 11://��չ����
			C.drawString(g, img_number, canvasControl.js_tool.getSpecificPropsPrice(CanvasControl.goodsCode[6]), "0123456789.", 205, 277, 12, 18, 0, 0, 0);
			g.drawImage(img_rmb, 220, 277, 0);
			break;
		case 12://����
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
	 * ���ع��صİ�ť
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
				case 0://�˳�������Ϸ
//					canvasControl.getMidlet().exitGame();
					CanvasControl.willExit = true;
					new ServerIptv(canvasControl).sendGameTimeInfo(CanvasControl.appStartTime + CanvasControl.iptvID, "1", "update");
					break;
				case 1://�˳����ڽ��е���Ϸ
					game.removeOtherImage();
					game.removeWeakImage();
					game = null;
					goToHome();
					break;
				case 2://���򸴻�
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 0;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e7) {
						e7.printStackTrace();
					}
					break;
				case 6://��������ҩˮ
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 1;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e6) {
						e6.printStackTrace();
					}
					break;
				case 7://����ը��
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 2;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e5) {
						e5.printStackTrace();
					}
					break;
				case 8://�����޵�
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 3;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e4) {
						e4.printStackTrace();
					}
					break;
				case 9://��������ʯ
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 4;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e3) {
						e3.printStackTrace();
					}
					break;
				case 10://����һ��ʿ��
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 5;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					break;
				case 11://��չ����
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					CanvasControl.goodsIndex = 6;
					try {
						canvasControl.js_tool.do_BuyProp(Image.createImage("/js_prop/" + CanvasControl.goodsIndex + ".png"), CanvasControl.goodsCode[CanvasControl.goodsIndex]);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case 12://����Ӣ��
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
			
			if(type == 14) {//��һ��
//				GasStation.willStart = true;
//				canvasControl.setView(canvasControl.nullView);
//				this.removeResource();
//				canvasControl.setView(new Loading(canvasControl, 0));
				switch(buttonIndex) {
				case 0://����
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 12));
					canvasControl.setGoBackView(this);
					break;
				case 1://��չ����
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					if(GasStation.soldierCount < 10)
						canvasControl.setView(new Dialog(canvasControl, 11));
					else 
						canvasControl.setView(new Dialog(canvasControl, 13));
					
					canvasControl.setGoBackView(this);
					break;
				case 2://��һ��
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
	 * ������ҳ
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
