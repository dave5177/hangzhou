package com.dave.soldierHunt.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.soldierHunt.main.CanvasControl;
import com.dave.soldierHunt.net.ServerIptv;
import com.dave.soldierHunt.tool.C;

public class Ranking implements Showable {

private CanvasControl canvasControl;
	
	public Image img_back;
	private Image img_goBack;
	public Image img_number;
	public Image img_number_me;
	
	/**
	 * 进入排行榜时间
	 */
	private long rankingStartTime;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		rankingStartTime = System.currentTimeMillis();
		new ServerIptv(canvasControl).doGetRank();
		new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "add");
		canvasControl.setNeedRepaint(true);
		
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_goBack, C.WIDTH, 530, Graphics.BOTTOM | Graphics.RIGHT);
		
		showRankString(g);
	}

	public void showRankString(Graphics g){
		if(CanvasControl.rankInfo == null) return;
		int size = CanvasControl.rankInfo.size();
		for (int i = 0; i < size; i++) {
			String[] stra_t = new String[9];
			stra_t = (String[])CanvasControl.rankInfo.elementAt(i);
			
			g.setColor(0);
			C.drawString(g, img_number, stra_t[3], "0123456789", 70, 107 + i * 32, 11, 16, 0, 0, 0);
//			C.drawString(g, img_number, stra_t[0], "0123456789", 220, 107 + i * 32, 11, 16, 0, 0, 0);
//			C.drawString(g, img_number, stra_t[1], "0123456789", 380, 107 + i * 32, 11, 16, 0, 0, 0);
//			C.drawString(g, img_number, stra_t[2], "0123456789", 540, 107 + i * 32, 11, 16, 0, 0, 0);
//			g.drawString(stra_t[3], 20, 100 + i * 34, 0);
			g.drawString(stra_t[0], 240, 107 + i * 32, Graphics.TOP | Graphics.HCENTER);
			g.drawString(stra_t[2], 390, 107 + i * 32, Graphics.TOP | Graphics.HCENTER);
			g.drawString(stra_t[1], 550, 107 + i * 32, Graphics.TOP | Graphics.HCENTER);
		}
		C.drawString(g, img_number_me, CanvasControl.rank + "", "0123456789/", 70, 450, 11, 16, 0, 0, 0);
//		C.drawString(g, img_number_me, CanvasControl.iptvID, "0123456789/", 220, 450, 11, 16, 0, 0, 0);
//		C.drawString(g, img_number_me, CanvasControl.level + "", "0123456789/", 380, 450, 11, 16, 0, 0, 0);
//		C.drawString(g, img_number_me, CanvasControl.totalCoin + "", "0123456789/", 540, 450, 11, 16, 0, 0, 0);
		g.setColor(255, 0, 0);
		g.drawString(CanvasControl.iptvID, 240, 450, Graphics.TOP | Graphics.HCENTER);
		g.drawString((CanvasControl.level) + "", 390, 450, Graphics.TOP | Graphics.HCENTER);
		g.drawString(CanvasControl.totalCoin + "", 550, 450, Graphics.TOP | Graphics.HCENTER);
	}
	
	public void loadResource() {
		try {
//			img_back = Image.createImage("/ranking/back.png");
			img_goBack = Image.createImage("/button/key_0.png");
//			img_number = Image.createImage("/ranking/number.png");
//			img_number_me = Image.createImage("/ranking/number_me.png");
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
			new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "update");
//			canvasControl.setView(new Home(canvasControl));
			canvasControl.setView(new Loading(canvasControl, 2));
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
		if(CanvasControl.rankInfo == null) {
			canvasControl.repaint();
			canvasControl.serviceRepaints();
		}
	}
}
