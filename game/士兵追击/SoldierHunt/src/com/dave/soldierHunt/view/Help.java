package com.dave.soldierHunt.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;

public class Help implements Showable {
	private CanvasControl canvasControl;
	/**
	 * 用来记录是从主界面进入帮助，还是从游戏界面进入帮助。
	 * 0是主界面。1是游戏界面。
	 */
	private int type;
	
	private Image img_back;
	private Image img_goBack;

	public Help(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
		canvasControl.setNeedRepaint(true);
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_goBack, C.WIDTH, 530, Graphics.BOTTOM | Graphics.RIGHT);
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/help/back.png");
			if(type == 0) {
				img_goBack = Image.createImage("/button/key_0.png");
			} else {
				img_goBack = Image.createImage("/button/key_9_goback.png");
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
		switch(keyCode) {
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_9:
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
		// TODO Auto-generated method stub
		
	}


}
