package com.dave.paoBing.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;
import com.zn.hs.prop.HSProp;

/**
 * @author Administrator 主页
 */
public class Home implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_bomb;
	public Image[] ima_word;
	public Image[] ima_boom;
	public Image[] ima_fire;
	public Image img_point;
	public Image img_u_point;

	/**
	 * 选择的下标值 0：选中开始游戏 1：选中加油站 2：选中英雄榜 3：选中游戏帮助 4：选中退出游戏
	 */
	private byte index;

	/**
	 * 火焰帧
	 */
	private int frame_fire;

	/**
	 * boom帧
	 */
	private int frame_boom;

	/**
	 * boom
	 */
	private boolean boom;

	/**
	 * 将要跳转的界面的下标值
	 */
	private byte gotoIndex;

	/**
	 * boom的y坐标
	 */
	private int wordY = 475;

	public Home(CanvasControl canvasControl) {
		super();
		this.canvasControl = canvasControl;

		// canvasControl.playerHandler.playByIndex(0);

		canvasControl.setNeedRepaint(false);
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(ima_fire[frame_fire], 230, 400, Graphics.VCENTER
				| Graphics.HCENTER);
		g.drawImage(img_bomb, 200, 530, Graphics.BOTTOM | Graphics.HCENTER);

		if (boom) {
			g.drawImage(ima_boom[frame_boom], 200, 470, Graphics.VCENTER
					| Graphics.HCENTER);
		}

		g.drawImage(ima_word[index], 200, wordY, Graphics.VCENTER
				| Graphics.HCENTER);
		g.drawImage(img_u_point, 320, 490, Graphics.VCENTER | Graphics.HCENTER);
		g.drawRegion(img_u_point, 0, 0, img_u_point.getWidth(),
				img_u_point.getHeight(), Sprite.TRANS_MIRROR, 73, 490,
				Graphics.VCENTER | Graphics.HCENTER);

		if (index <= 0) {
			g.drawImage(img_point, 320, 490, Graphics.VCENTER
					| Graphics.HCENTER);
		} else if (index >= 3) {
			g.drawRegion(img_point, 0, 0, img_point.getWidth(),
					img_point.getHeight(), Sprite.TRANS_MIRROR, 73, 490,
					Graphics.VCENTER | Graphics.HCENTER);
		} else {
			g.drawImage(img_point, 320, 490, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawRegion(img_point, 0, 0, img_point.getWidth(),
					img_point.getHeight(), Sprite.TRANS_MIRROR, 73, 490,
					Graphics.VCENTER | Graphics.HCENTER);
		}

		g.setColor(255, 255, 255);
		g.drawString(CanvasControl.VERSION + "    " + HSProp.HSProp_Version,
				630, 525, Graphics.BOTTOM | Graphics.RIGHT);
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_RIGHT:
			if (index < 3)
				index++;
			break;
		case C.KEY_LEFT:
			if (index > 0)
				index--;
			break;
		case C.KEY_FIRE:
			boom = true;
			gotoIndex = index;
			break;
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_0:
			canvasControl.setView(new Dialog(canvasControl, 0, null));
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

		try {
			Image img_temp = null;
			img_back = Image.createImage("/home/back.png");
			img_bomb = Image.createImage("/home/bomb.png");
			img_point = Image.createImage("/home/point.png");
			img_u_point = Image.createImage("/home/u_point.png");

			ima_word = new Image[4];
			img_temp = Image.createImage("/home/word.png");
			int w = img_temp.getWidth();
			int h = img_temp.getHeight();
			for (int i = 0; i < 4; i++) {
				ima_word[i] = Image.createImage(img_temp, 0, (h >> 2) * i, w,
						h >> 2, 0);
			}

			ima_fire = new Image[3];
			img_temp = Image.createImage("/home/fire.png");
			w = img_temp.getWidth();
			h = img_temp.getHeight();
			for (int i = 0; i < 3; i++) {
				ima_fire[i] = Image.createImage(img_temp, w * i / 3, 0, w / 3,
						h, 0);
			}

			ima_boom = new Image[5];
			for (int i = 0; i < 5; i++) {
				ima_boom[i] = Image.createImage("/home/boom_" + i + ".png");
			}

			img_temp = null;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeResource() {
		img_back = null;
		img_bomb = null;
		img_point = null;
		img_u_point = null;

		for (int i = 0; i < 4; i++) {
			ima_word[i] = null;
		}
		ima_word = null;

		for (int i = 0; i < 3; i++) {
			ima_fire[i] = null;
		}
		ima_fire = null;

		for (int i = 0; i < 5; i++) {
			ima_boom[i] = null;
		}
		ima_boom = null;

		System.gc();
	}

	public void logic() {
		frame_fire++;
		if (frame_fire > 2)
			frame_fire = 0;

		if (boom) {
			wordY -= 20;
			frame_boom++;
			if (frame_boom > 4) {
				gotoNextView();
				boom = false;
				wordY = 475;
				frame_boom = 0;
			}
		}

		canvasControl.repaint();
		canvasControl.serviceRepaints();
	}

	/**
	 * 显示完动画后去到下一个界面
	 */
	public void gotoNextView() {
		canvasControl.setView(canvasControl.nullView);
		canvasControl.setGoBackView(this);
		switch (gotoIndex) {
		case 0:
			this.removeResource();
//			canvasControl.setView(new Loading(canvasControl, 1));
			canvasControl.setView(new Game(canvasControl));
			break;
		case 1:
			this.removeResource();
//			canvasControl.setView(new Loading(canvasControl, 3));
			canvasControl.setView(new Ranking(canvasControl));
			break;
		case 2:
			this.removeResource();
			canvasControl.setView(new Help(canvasControl, 0));
			break;
		case 3:
			canvasControl.setView(new Dialog(canvasControl, 0, null));
			break;
		}
	}

}
