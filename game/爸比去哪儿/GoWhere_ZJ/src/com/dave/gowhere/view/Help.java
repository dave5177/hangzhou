package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class Help implements Showable {
	private CanvasControl canvasControl;
	/**
	 * ������¼�Ǵ������������������Ǵ���Ϸ������������ 0�������档1����Ϸ���档
	 */
	private int type;

	private Image img_back;
	private Image img_goBack;

	public Help(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_goBack, C.WIDTH, 530, Graphics.BOTTOM | Graphics.RIGHT);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/help/back.png");
			if (type == 0) {
				img_goBack = Image.createImage("/help/key_0_back.png");
			} else {
				img_goBack = Image.createImage("/help/key_9_back.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		img_back = null;
		img_goBack = null;
		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_9:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
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
		// TODO Auto-generated method stub

	}

	public void removeServerImage() {
		// TODO Auto-generated method stub

	}

}
