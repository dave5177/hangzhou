package com.dave.worldWar.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.tool.C;

/**
 * @author Administrator
 *��ҳ
 */
public class Home implements Showable {
	
	private CanvasControl canvasControl;

	public Image[] a_img_back;
	public Image[] a_img_button;
	
	/**
	 * ѡ����±�ֵ
	 * 0��ѡ�п�ʼ��Ϸ/������Ϸ
	 * 1��ѡ��ѵ��Ӫ
	 * 2��ѡ�а���
	 * 3��ѡ�����а�
	 * 4��ѡ���˳���Ϸ
	 */
	private byte index;

	public Home(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
	}
	
	public void show(Graphics g) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				g.drawImage(a_img_back[i * 2 + j], j * 320, i * 265, 0);
			}
		}
		
		for (int i = 0; i < 5; i++) {
			g.drawImage(a_img_button[i], 499, 275 + i * 60, Graphics.LEFT | Graphics.BOTTOM);
		}
		
		g.drawImage(a_img_button[index], 463, 275 + index * 60, Graphics.LEFT | Graphics.BOTTOM);
		
		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION, 10, 10, 0);
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_DOWN:
		case C.KEY_RIGHT:
			if(index < 4) index ++;
			break;
		case C.KEY_UP:
		case C.KEY_LEFT:
			if(index > 0) index --;
			break;
		case C.KEY_FIRE:
			gotoNextView(index);
			break;
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_0:
//			canvasControl.getMidlet().exitGame();
			canvasControl.setView(new Dialog(canvasControl, 0, null));
			canvasControl.setGoBackView(this);
//			this.removeResource();
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
	}

	public void removeResource() {
		for (int i = 0; i < 4; i++) {
			a_img_back[i] = null;
		}
		a_img_back = null;
		
		for (int i = 0; i < a_img_button.length; i++) {
			a_img_button[i] = null;
		}
		a_img_button = null;
		
		System.gc();
	}

	public void logic() {
		
	}
	
	/**
	 * ��ʾ�궯����ȥ����һ������
	 */
	public void gotoNextView(int index) {
		canvasControl.setView(canvasControl.nullView);
		canvasControl.setGoBackView(this);
		switch(index) {
		case 0://��ʼ���߼�����Ϸ
			this.removeResource();
			if(CanvasControl.group == 0)//��һ����Ϸ��ȥ��ѡ����Ӫ
				canvasControl.setView(new Loading(canvasControl, 4));
			else//������Ϸ��������Ϸ����
				canvasControl.setView(new Loading(canvasControl, 1));
			break;
		case 1://ѵ��Ӫ
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 2));
			break;
		case 2://����
			this.removeResource();
			canvasControl.setView(new Help(canvasControl, 0));
			break;
		case 3://���а�
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 3));
			break;
		case 4://�˳���Ϸ
			canvasControl.setView(new Dialog(canvasControl, 0, null));
			canvasControl.setGoBackView(this);
			break;
		}
	}

	public void removeServerImage() {
		// TODO Auto-generated method stub
		
	}
	
}
