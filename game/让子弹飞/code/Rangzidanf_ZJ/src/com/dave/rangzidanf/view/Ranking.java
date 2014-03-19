package com.dave.rangzidanf.view;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.rangzidanf.main.CanvasControl;
import com.dave.rangzidanf.net.ServerIptv;
import com.dave.rangzidanf.tool.C;

public class Ranking implements Showable {
	
	private CanvasControl canvasControl;
	
	private Image img_back;
	private Image img_goBack;
//	private Image img_number;
	private Image img_number_me;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
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
			StringBuffer scoreEveryLevel = new StringBuffer();
			
			stra_t = (String[])CanvasControl.rankInfo.elementAt(i);
			for (int j=1; j<7; j++) {
				if(j == 6) scoreEveryLevel = scoreEveryLevel.append(stra_t[j]);
				else scoreEveryLevel = scoreEveryLevel.append(stra_t[j] + "/");
			}
			g.setColor(0);
			g.drawString(stra_t[0], 100, 100 + i * 34, 0);
			g.drawString(stra_t[7], 270, 100 + i * 34, 0);
			g.drawString(scoreEveryLevel.toString(), 400, 100 + i * 34, 0);
			
//			C.drawString(g, img_number, stra_t[0], "0123456789/", 100, 100 + i * 33, 12, 17, 0, 0, 0);
//			C.drawString(g, img_number, stra_t[7], "0123456789/", 270, 100 + i * 33, 12, 17, 0, 0, 0);
//			C.drawString(g, img_number, scoreEveryLevel.toString(), "0123456789/", 400, 100 + i * 33, 12, 17, 0, 0, 0);
			/*g.setColor(0);
			g.drawString(stra_t[1], 40, 	235+i*18, 0);
			g.drawString(stra_t[0], 120, 	235+i*18, 0);
			g.drawString(stra_t[2], 260, 	235+i*18, 0);*/
		}
//		g.drawString(CanvasControl.rank + "", 42, 470, 0);
		g.drawString(CanvasControl.iptvID, 100, 470, 0);
		g.drawString(CanvasControl.totalScore + "", 270, 470, 0);
		g.drawString(CanvasControl.level_1_score + "/" + 
				CanvasControl.level_2_score + "/" +
				CanvasControl.level_3_score + "/" + 
				CanvasControl.level_4_score + "/" + 
				CanvasControl.level_5_score + "/" + 
				CanvasControl.level_6_score, 400, 470, 0);
		
		C.drawString(g, img_number_me, CanvasControl.rank + "", "0123456789/", 42, 470, 12, 17, 0, 0, 0);
		/*C.drawString(g, img_number_me, CanvasControl.iptvID, "0123456789/", 100, 470, 12, 17, 0, 0, 0);
		C.drawString(g, img_number_me, CanvasControl.totalScore + "", "0123456789/", 270, 470, 12, 17, 0, 0, 0);
		C.drawString(g, img_number_me, CanvasControl.level_1_score + "/" + 
				CanvasControl.level_2_score + "/" +
				CanvasControl.level_3_score + "/" + 
				CanvasControl.level_4_score + "/" + 
				CanvasControl.level_5_score + "/" + 
				CanvasControl.level_6_score, "0123456789/", 400, 470, 12, 17, 0, 0, 0);*/
	}
	
	public void loadResource() {
		try {
			img_back = Image.createImage("/ranking/back.png");
			img_goBack = Image.createImage("/button/goBack.png");
//			img_number = Image.createImage("/ranking/number.png");
			img_number_me = Image.createImage("/ranking/number_me.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeResource() {
		img_back = null;
		img_goBack = null;
//		img_number = null;
		img_number_me = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(new Home(canvasControl));
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
		canvasControl.repaint();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
