package com.dave.ftxz.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;
import com.zn.hs.prop.HSProp;

/**
 * @author Administrator 主页
 */
public class Home implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image[] a_img_button;
	public Image[] a_img_word;

	/**
	 * 选择的下标值 0：选中开始游戏/继续游戏 1：选中商城 2：选中排行榜 3：选中帮助 4：选中退出游戏
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
	 * 去到下一个界面
	 */
	public void gotoNextView(int index) {
		canvasControl.setView(canvasControl.nullView);
		canvasControl.setGoBackView(this);
		switch (index) {
		case 0:// 开始游戏,先进入商城
			this.removeResource();
			canvasControl.setView(new Shop(canvasControl));
			break;
		case 1:// 商城
			this.removeResource();
			canvasControl.setView(new Shop(canvasControl));
			break;
		case 2:// 退出游戏
			canvasControl.setView(new Dialog(canvasControl, 0, this));
			canvasControl.setGoBackView(this);
			break;
		}
	}

	public void removeServerImage() {
		// TODO Auto-generated method stub

	}

}
