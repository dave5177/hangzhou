package com.dave.rangzidanf.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.net.ServerIptv;
import com.dave.rangzidanf.tool.C;

/**
 * @author Administrator
 *��ҳ
 */
public class Home implements Showable {
	
	private CanvasControl canvasControl;

	private Image img_back;
	private Image img_select;
	private Image[] ima_chosed;
	private Image[] ima_unchose;
	
	/**
	 * ѡ����±�ֵ
	 * 0��ѡ�п�ʼ��Ϸ
	 * 1��ѡ�����а�
	 * 2��ѡ����Ϸ����
	 * 3��ѡ�и�����Ϸ
	 * 4��ѡ���˳���Ϸ
	 */
	private byte index;

	public Home(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
		
//		CanvasControl.player = new AudioPlay(6);
//		CanvasControl.player.playSound();
		
//		canvasControl.audio.playSound(6);
		
		canvasControl.setNeedRepaint(true);
	}
	
	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		showLable(g);
		
		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION, 5, 525, Graphics.BOTTOM | Graphics.LEFT);
	}
	
	/**
	 * չʾѡ���ǩ
	 * @param g ������
	 */
	public void showLable(Graphics g) {
		int h = img_select.getHeight();
		
		for(int i = 0; i<4; i++) {
			g.drawImage(ima_unchose[i], 500, 280 + i * h, Graphics.LEFT | Graphics.VCENTER);
		}
		g.drawImage(img_select, 640, 280 + index * h, Graphics.RIGHT | Graphics.VCENTER);
		g.drawImage(ima_chosed[index], 500, 280 + index * h, Graphics.LEFT | Graphics.VCENTER);
	}

	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
		switch(keyCode) {
		//���ڲ��������İ�����
//		case C.KEY_5:
//			if(canvasControl.audio.getState(6) != Player.STARTED) {
//				canvasControl.audio.playSound(6);
//			} else canvasControl.audio.stopSound(6);
//			break;
		case C.KEY_DOWN:
			if(index < 3) index ++;
			break;
		case C.KEY_UP:
			if(index > 0) index --;
			break;
		case C.KEY_FIRE:
			switch(index) {
			case 0://��ʼ��Ϸ
				canvasControl.setView(new Explanation(canvasControl, CanvasControl.level));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
			case 1://�������а�
				new ServerIptv(canvasControl).doGetRank();
				canvasControl.setView(new Ranking(canvasControl));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
			case 2://��Ϸ����
				canvasControl.setView(new Help(canvasControl, 0));
				canvasControl.setGoBackView(this);
				this.removeResource();
				break;
//			case 3:
//				break;
			case 3://�˳���Ϸ
				canvasControl.setView(new Dialog(canvasControl, 0));
				canvasControl.setGoBackView(this);
				this.removeResource();
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
		try {
			img_back = Image.createImage("/home/back.png");
			img_select = Image.createImage("/home/select.png");
			
			Image temp_chosed = Image.createImage("/home/chosed.png");
			Image temp_unchose = Image.createImage("/home/unchose.png");
			int w = temp_chosed.getWidth();
			int h = temp_chosed.getHeight();
			
			ima_chosed = new Image[5];
			ima_unchose = new Image[5];
			
			for(int i=0; i<5; i++) {
				ima_chosed[i] = Image.createImage(temp_chosed, 0, h / 5 * i, w, h / 5, 0);
				ima_unchose[i] = Image.createImage(temp_unchose, 0, h / 5 * i, w, h / 5, 0);
			}
			
			ima_chosed[3] = ima_chosed[4];
			ima_unchose[3] = ima_unchose[4];
			
		} catch (IOException e) {
			System.out.println("�޷��ҵ�ͼƬ");
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_select = null;
		
		for(int i=0; i<5; i++) {
			ima_chosed[i] = null;
			ima_unchose[i] = null;
		}
		ima_chosed = null;
		ima_unchose =null;
		
		System.gc();
	}

	public void logic() {
//		C.out("fuck");
	}
}
