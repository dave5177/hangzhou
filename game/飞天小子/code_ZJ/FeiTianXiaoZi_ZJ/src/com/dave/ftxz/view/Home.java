package com.dave.ftxz.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;

/**
 * @author Administrator ��ҳ
 */
public class Home implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image[] a_img_button;
	public Image[] a_img_word;

	/**
	 * ѡ����±�ֵ 0��ѡ�п�ʼ��Ϸ/������Ϸ 1��ѡ���̳� 2��ѡ�����а� 3��ѡ�а��� 4��ѡ���˳���Ϸ
	 */
	private byte index;

	public Home(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);

		for (int i = 0; i < 5; i++) {
			g.drawImage(a_img_button[0], 640, 285 + i * 50, Graphics.RIGHT
					| Graphics.VCENTER);
			g.drawRegion(a_img_word[0], 0, i * 30, 123, 30, 0, 577, 285 + i * 50, Graphics.VCENTER | Graphics.HCENTER);
		}

		g.drawImage(a_img_button[1], 640, 285 + index * 50, Graphics.RIGHT
				| Graphics.VCENTER);
		g.drawRegion(a_img_word[1], 0, index * 30, 123, 30, 0, 554, 285 + index * 50, Graphics.VCENTER | Graphics.HCENTER);

		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION, 10, 10, 0);
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_DOWN:
		case C.KEY_RIGHT:
			if (index < 4)
				index++;
			break;
		case C.KEY_UP:
		case C.KEY_LEFT:
			if (index > 0)
				index--;
			break;
		case C.KEY_FIRE:
			gotoNextView(index);
			break;
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_0:
			// canvasControl.getMidlet().exitGame();
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			canvasControl.setGoBackView(this);
			// this.removeResource();
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
		img_back = null;
		for (int i = 0; i < a_img_button.length; i++) {
			a_img_button[i] = null;
		}
		a_img_button = null;

		for (int i = 0; i < a_img_word.length; i++) {
			a_img_word[i] = null;
		}
		a_img_word = null;

		System.gc();
	}

	public void logic() {

	}

	/**
	 * ȥ����һ������
	 */
	public void gotoNextView(int index) {
		canvasControl.setView(canvasControl.nullView);
		canvasControl.setGoBackView(this);
		switch (index) {
		case 0:// ��ʼ��Ϸ,�Ƚ����̳�
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 2));
			break;
		case 1:// �̳�
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 2));
			break;
		case 2:// ���а�
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 3));
			break;
		case 3:// ����
			this.removeResource();
			canvasControl.setView(new Help(canvasControl, 0));
			break;
		case 4:// �˳���Ϸ
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			canvasControl.setGoBackView(this);
			break;
		}
	}

	public void removeServerImage() {
		// TODO Auto-generated method stub

	}

}
