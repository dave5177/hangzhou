package com.dave.soldierHunt.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.tool.C;

public class Ranking implements Showable {

private CanvasControl canvasControl;
	
	private Image img_back;
	private Image img_goBack;
	private Image img_number;
	private Image img_number_me;
	
	/**
	 * 进入排行榜时间
	 */
	private long rankingStartTime;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		rankingStartTime = System.currentTimeMillis();
//		new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "add");
		canvasControl.setNeedRepaint(true);
		
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_goBack, C.WIDTH, 530, Graphics.BOTTOM | Graphics.RIGHT);
		
		showRankString(g);
	}

	public void showRankString(Graphics g){
	}
	
	public void loadResource() {
		try {
			img_back = Image.createImage("/ranking/back.png");
			img_goBack = Image.createImage("/button/key_0.png");
			img_number = Image.createImage("/ranking/number.png");
			img_number_me = Image.createImage("/ranking/number_me.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		img_back = null;
		img_goBack = null;
		img_number = null;
		img_number_me = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
//			new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "update");
			canvasControl.setView(canvasControl.getGoBackView());
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
		
	}
}
