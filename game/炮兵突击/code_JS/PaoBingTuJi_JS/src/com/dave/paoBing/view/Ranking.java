package com.dave.paoBing.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.net.ServerIptv;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;

public class Ranking implements Showable {

private CanvasControl canvasControl;
	
	public Image img_back;
	private Image img_goBack;
	public Image img_number;
	
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
		if(CanvasControl.rankInfo != null) {
			int size = CanvasControl.rankInfo.size();
			g.setColor(255, 255, 255);
			for (int i = 0; i < size; i++) {
				String[] stra_t = new String[3];
				stra_t = (String[])CanvasControl.rankInfo.elementAt(i);
				
				C.drawString(g, img_number, stra_t[2], "0123456789", 90, 117 + i * 32, 11, 16, 0, 0, 0);
				g.drawString(stra_t[0], 295, 117 + i * 32, Graphics.TOP | Graphics.HCENTER);
				g.drawString(stra_t[1], 530, 117 + i * 32, Graphics.TOP | Graphics.HCENTER);
			}
			g.setColor(255, 255, 0);
			C.drawString(g, img_number, CanvasControl.rank + "", "0123456789", 90, 465, 11, 16, 0, 0, 0);
			g.drawString(CanvasControl.iptvID, 295, 465, Graphics.TOP | Graphics.HCENTER);
			g.drawString((CanvasControl.mission) + "", 530, 465, Graphics.TOP | Graphics.HCENTER);
		}
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
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "update");
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
//			canvasControl.setView(new Home(canvasControl));
			canvasControl.setView(new Loading(canvasControl, 2));
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
