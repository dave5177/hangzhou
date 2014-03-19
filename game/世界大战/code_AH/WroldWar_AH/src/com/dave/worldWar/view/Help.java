package com.dave.worldWar.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.tool.C;

public class Help implements Showable {
	private CanvasControl canvasControl;
	/**
	 * 用来记录是从主界面进入帮助，还是从游戏界面进入帮助。
	 * 0是主界面。1是游戏界面。
	 */
	private int type;
	
	private Image[] a_img_back;
	private Image img_goBack;

	public Help(CanvasControl canvasControl, int type) {
		this.canvasControl = canvasControl;
		this.type = type;
	}

	public void show(Graphics g) {
//		g.setColor(255, 255, 255);
//		g.fillRect(0, 0, 640, 530);
		
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				g.drawImage(a_img_back[i * 2 + j], j * 320, i * 265, 0);
			}
		}
		g.drawImage(img_goBack, C.WIDTH, 530, Graphics.BOTTOM | Graphics.RIGHT);
	}

	public void loadResource() {
		try {
			a_img_back = new Image[4];
			for (int i = 0; i < a_img_back.length; i++) {
				a_img_back[i] = Image.createImage("/help/back_" + i + ".png");
			}
			if(type == 0) {
				img_goBack = Image.createImage("/button/key_0_back.png");
			} else {
				img_goBack = Image.createImage("/button/key_9_back.png");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		for (int i = 0; i < 4; i++) {
			a_img_back[i] = null;
		}
		a_img_back = null;
		img_goBack = null;
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_9:
			if(type == 0) {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(new Loading(canvasControl, 0));
			} else {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				canvasControl.setView(canvasControl.getGoBackView());
			}
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
