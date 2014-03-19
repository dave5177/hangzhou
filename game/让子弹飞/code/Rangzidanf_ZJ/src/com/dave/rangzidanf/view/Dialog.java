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
	 * �Ի������Ϳ���
	 * 0���˳���������Ի���
	 * 1���˳���Ϸ�Ի���
	 * 2������ǹ֧��
	 * 3������ɹ���
	 * 4������ʧ�ܡ�
	 * 5���Ƿ�׼������ǰ���ˡ�
	 * 6������߱��ӵ���
	 * 7�����������¡�
	 * 8�������Զ���׼��
	 * 9�������Զ����ӵ���
	 * 10�����¹����Ѵﵽ��ֵ��
	 * 11�����ϰ�ҩ��
	 * 12����ľ�������������
	 * 13��ɵ���ҿ�ǹ�����ӵ�ȥ�ɡ�
	 * 14���Ƿ�ʹ�ø�ǹ����ʼ��Ϸ
	 */
	private int type;
	
	/**
	 * ��¼ѡ���õ��±�ֵ
	 * 0���ǡ���Ĭ��ֵ��
	 * 1����
	 */
	private int index = 0;
	
	/**
	 * ��¼�����ǹ���±�ֵ��
	 */
	private int gunIndex;
	
	/**
	 * �Ƿ�ʹ�ø�ǹ��ʱ���ǹͼƬ
	 */
	private Image img_gun;
	
	private String[] gunPrice = {//rmb�۸�
			"0.2",
			"0.5",
			"1",
			"1",
			"1",
			"1"
	};
		
	private String[] goodsPrice = {//���߼۸�
			"1",//�߱�
			"1",
			"2",
			"1",
			"0.5",
			"1"
	};
	
	//ǹ��Ҽ۸�
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
		g.drawImage(img_back, C.WIDTH / 2, 85, Graphics.TOP | Graphics.HCENTER);//draw����
		
		if(type == 14) {
			g.drawImage(img_word, C.WIDTH / 2, 150, Graphics.VCENTER | Graphics.HCENTER);
			g.drawImage(img_gun, C.WIDTH / 2, 180, Graphics.TOP | Graphics.HCENTER);//drawǹ��ͼƬ
		} else g.drawImage(img_word, C.WIDTH / 2, 200, Graphics.VCENTER | Graphics.HCENTER);//draw�Ի�������
		
		if(type == 3 || type == 4 || type == 10) return;
		
		showGoodsPrice(g);
		
		//drawѡ��bar
		if(index == 0){
			g.drawImage(img_chosedBar, 250, 300, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_unchoseBar, 410, 300, Graphics.HCENTER | Graphics.VCENTER);
		} else {
			g.drawImage(img_chosedBar, 410, 300, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_unchoseBar, 250, 300, Graphics.HCENTER | Graphics.VCENTER);
		}
		
		if(type == 11) {//ֻʣ���ٸ�������
			C.drawString(g, img_number, CanvasControl.restEnemy + "",
					"0123456789.", 240, 193, 13, 18, 0, 0, 0);
		}
		
		if(type == 2) {
			showGunPrice(g);
		}
		
		g.drawImage(img_arrow, 200 + index * 160, 300, Graphics.RIGHT | Graphics.VCENTER);
		g.drawImage(img_yesWord, 250, 300, Graphics.HCENTER | Graphics.VCENTER);//draw���֡��ǡ�
		g.drawImage(img_noWord, 410, 300, Graphics.HCENTER | Graphics.VCENTER);//draw���֡���
	}

	/**
	 * drawǹ�ļ۸�
	 * @param g
	 */
	private void showGunPrice(Graphics g) {
//C.out("����ͼƬ���------" + img_number.getWidth());
//C.out("����ͼƬ---�߶�---" + img_number.getHeight());
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
	 * draw���ߵļ۸�
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
		case 6://�߱��۸�
			C.drawString(g, img_number, goodsPrice[0], "0123456789.", 205, 228, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 255, 228, 0);
			break;
		case 7://�����¼۸�
			C.drawString(g, img_number, goodsPrice[1], "0123456789.", 280, 193, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 330, 193, 0);
			break;
		case 8://�Զ���׼�۸�
			C.drawString(g, img_number, goodsPrice[2], "0123456789.", 360, 193, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 410, 193, 0);
			break;
		case 9://�Զ����ӵ��۸�
			C.drawString(g, img_number, goodsPrice[3], "0123456789.", 280, 193, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 330, 193, 0);
			break;
		case 11://����۸�
			C.drawString(g, img_number, goodsPrice[4], "0123456789.", 260, 155, 13, 18, 0, 0, 0);
			g.drawImage(img_rmb, 310, 155, 0);
			break;
		case 13://�ӵ��۸�
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
				case 0 ://�˳���Ϸ
					canvasControl.getMidlet().exitGame();
					break;
				case 1 ://���أ���������
					canvasControl.setView(new Arsenal(canvasControl));
					canvasControl.setGoBackView(null);
					this.removeResource();
					break;
				case 2 ://��ǹ��
					switch(gunIndex) {
					case 0://��m16�����˵ģ�ɵ�ơ�
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_M16);
						CanvasControl.goodsIndex = 0;
						break;
					case 1://��am
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AT15);
						CanvasControl.goodsIndex = 1;
						break;
					case 2://��AK
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AK47);
						CanvasControl.goodsIndex = 2;
						break;
					case 3://��M4
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_M4A1S);
						CanvasControl.goodsIndex = 3;
						break;
					case 4://����
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AWM);
						CanvasControl.goodsIndex = 4;
						break;
					case 5://�����
						new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_BARRETT);
						CanvasControl.goodsIndex = 5;
						break;
					}
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					canvasControl.setGunIndex(gunIndex);
//					this.removeResource();
					break;
				case 5 ://׼������ǰ����
					CanvasControl.firstTimes = 0;
					CanvasControl.highboom = 0;
					CanvasControl.autocb = 0;
					
					canvasControl.setView(new Game(canvasControl, CanvasControl.level, new Gun(canvasControl.getGunIndex())));
					canvasControl.setGoBackView(this);
					this.removeResource();
					break;
				case 6 ://��߱���
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_HighBoom);
					CanvasControl.goodsIndex = 6;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					CanvasControl.highboom = 1;
//					this.removeResource();
					break;
				case 7 ://��������
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_Stealth);
					CanvasControl.goodsIndex = 7;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					this.removeResource();
					break;
				case 8 ://���Զ���׼��
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AutoAim);
					CanvasControl.goodsIndex = 8;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					this.removeResource();
					break;
				case 9 ://���Զ����ӵ���
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_AutoCB);
					CanvasControl.goodsIndex = 9;
//					canvasControl.setView(new Dialog(canvasControl, 3));
//					this.removeResource();
					break;
				case 11://�����ϰ�ҩ
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_Relive);
					CanvasControl.goodsIndex = 10;
					break;
				case 12://��˿ֻ�з������˵���
					canvasControl.setView(new Home(canvasControl));
					canvasControl.setGoBackView(this);
					this.removeResource();
					break;
				case 13://���ӵ���
					new Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE, CanvasControl.GAME_PROP_GoodsCode_Bullet);
					CanvasControl.goodsIndex = 11;
					break;
				case 14://ʹ�ø�ǹ����ʼ��Ϸ
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
	 * 14���Ƿ�ʹ�ø�ǹ��ʱ����Ҫ��ʾǹ��ͼƬ
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
