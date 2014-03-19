package com.dave.soldierHunt.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dalin.jsiptv.prop.JS_IPTV_PORP_TOOL;
import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;

/**
 * @author Administrator
 *��ҳ
 */
public class Home implements Showable {
	
	private CanvasControl canvasControl;

	public Image img_back;
	public Image[] ima_chosed;
	public Image[] ima_unchose;
	
	/**
	 * ѡ����±�ֵ
	 * 0��ѡ�п�ʼ��Ϸ
	 * 1��ѡ�м���վ
	 * 2��ѡ��Ӣ�۰�
	 * 3��ѡ����Ϸ����
	 * 4��ѡ���˳���Ϸ
	 */
	private byte index;
	
	/**
	 * ѡ���ǩ��x����ֵ����
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
	 * չʾѡ���ǩ
	 * @param g ������
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
			case 0://��ʼ��Ϸ
			case 1://�������վ
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
			case 2://�������а�
				canvasControl.setView(new Loading(canvasControl, 3));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
			case 3://��Ϸ����
				canvasControl.setView(new Help(canvasControl, 0));
				canvasControl.setGoBackView(this);
//				this.removeResource();
				break;
			case 4://�˳���Ϸ
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
			System.out.println("�޷��ҵ�ͼƬ");
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
