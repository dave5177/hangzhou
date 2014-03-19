package com.dave.gowhere.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.gowhere.main.CanvasControl;
import com.dave.gowhere.tool.C;
import com.dave.showable.Showable;

public class Ranking implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_number;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		showRankString(g);
		
		g.drawImage(canvasControl.img_key_0_goback, 640, 530, Graphics.RIGHT | Graphics.BOTTOM);
	}

	public void showRankString(Graphics g) {
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/ranking/back.png");
			img_number = Image.createImage("/ranking/number.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeResource() {
		
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(canvasControl.getGoBackView());
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	public void removeServerImage() {

		System.gc();
	}
}
