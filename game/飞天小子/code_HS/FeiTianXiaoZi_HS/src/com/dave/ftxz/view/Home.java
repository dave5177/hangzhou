package com.dave.ftxz.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;
import com.zn.hs.prop.HSProp;

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

		int drawStart = 0;
		for (int i = 0; i < 3; i++) {
			g.drawImage(a_img_button[0], 640, 305 + i * 80, Graphics.RIGHT
					| Graphics.VCENTER);
			drawStart = i == 2 ? 4 : i;
			g.drawRegion(a_img_word[0], 0, drawStart * 30, 123, 30, 0, 577, 305 + i * 80, Graphics.VCENTER | Graphics.HCENTER);
		}

		g.drawImage(a_img_button[1], 640, 305 + index * 80, Graphics.RIGHT
				| Graphics.VCENTER);
		drawStart = index == 2 ? 4 : index;
		g.drawRegion(a_img_word[1], 0, drawStart * 30, 123, 30, 0, 554, 305 + index * 80, Graphics.VCENTER | Graphics.HCENTER);

		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION + "    " + HSProp.HSProp_Version, 10, 10, 0);
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_DOWN:
		case C.KEY_RIGHT:
			if (index < 2)
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
			canvasControl.setView(canvasControl.nullView);
			removeResource();
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			canvasControl.setGoBackView(this);
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
			
			a_img_button = new Image[2];
			for (int i = 0; i < 2; i++) {
				a_img_button[i] = Image.createImage("/home/btn_" + i + ".png");
			}
			a_img_word = new Image[2];
			for (int i = 0; i < a_img_word.length; i++) {
				a_img_word[i] = Image.createImage("/home/word_" + i + ".png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			canvasControl.setView(new Shop(canvasControl));
			break;
		case 1:// �̳�
			this.removeResource();
			canvasControl.setView(new Shop(canvasControl));
			break;
		case 2:// �˳���Ϸ
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			canvasControl.setGoBackView(this);
			break;
		}
	}

	public void removeServerImage() {
		// TODO Auto-generated method stub

	}

}
