package com.dave.worldWar.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.net.ServerIptv;
import com.dave.worldWar.tool.C;

public class Ranking implements Showable {

private CanvasControl canvasControl;
	
	public Image img_back;
	public Image img_number;
	public Image img_choose;
	
	private static final String[] str_group = {
		"你所在的阵营是G国",
		"你所在的阵营是U国",
		"你还没有游戏数据"
	};
	
	private int index_choose;
	
	/**
	 * 进入排行榜时间
	 */
	private long rankingStartTime;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
		
		if(CanvasControl.group == 0)
			index_choose = 0;
		else
			index_choose = CanvasControl.group - 1;
		
		rankingStartTime = System.currentTimeMillis();
		new ServerIptv(canvasControl).doGetRank(1);
		new ServerIptv(canvasControl).doGetRank(2);
		new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "add");
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		g.drawImage(img_choose, 186 + index_choose * 268, 56, Graphics.TOP | Graphics.HCENTER);
		showRankString(g);
	}

	public void showRankString(Graphics g){
		if(index_choose == 0 && CanvasControl.rankInfo_g != null) {
			int size = CanvasControl.rankInfo_g.size();
			g.setColor(0, 0, 0);
			for (int i = 0; i < size; i++) {
				String[] stra_t = new String[3];
				stra_t = (String[])CanvasControl.rankInfo_g.elementAt(i);
				
				C.drawString(g, img_number, stra_t[2], "0123456789", 80, 157 + i * 30, 10, 15, Graphics.HCENTER | Graphics.TOP, 0, 0);
				g.drawString(stra_t[0], 275, 157 + i * 30, Graphics.TOP | Graphics.HCENTER);
				g.drawString(stra_t[1], 490, 157 + i * 30, Graphics.TOP | Graphics.HCENTER);
			}
		} else if(index_choose == 1 && CanvasControl.rankInfo_u != null) {
			int size = CanvasControl.rankInfo_u.size();
			g.setColor(0, 0, 0);
			for (int i = 0; i < size; i++) {
				String[] stra_t = new String[3];
				stra_t = (String[])CanvasControl.rankInfo_u.elementAt(i);
				
				C.drawString(g, img_number, stra_t[2], "0123456789", 80, 157 + i * 30, 10, 15, Graphics.HCENTER | Graphics.TOP, 0, 0);
				g.drawString(stra_t[0], 275, 157 + i * 30, Graphics.TOP | Graphics.HCENTER);
				g.drawString(stra_t[1], 490, 157 + i * 30, Graphics.TOP | Graphics.HCENTER);
			}
		}
		
		if(index_choose == CanvasControl.group - 1) {
			g.setColor(0, 0, 0);
			C.drawString(g, img_number, CanvasControl.rank + "", "0123456789", 80, 475, 10, 15, Graphics.HCENTER | Graphics.TOP, 0, 0);
			g.drawString(CanvasControl.iptvID, 275, 475, Graphics.TOP | Graphics.HCENTER);
			g.drawString((CanvasControl.totalScore) + "", 490, 475, Graphics.TOP | Graphics.HCENTER);
		} else if(CanvasControl.group == 0){
			g.setColor(0, 0, 0);
			g.drawString(str_group[2], 80, 475, 0);
		} else {
			g.setColor(0, 0, 0);
			g.drawString(str_group[CanvasControl.group - 1], 80, 475, 0);
		}
	}
	
	public void loadResource() {
	}
	
	public void removeResource() {
		img_back = null;
		img_number = null;
		img_choose = null;
		
		System.gc();
	}
	
	public void keyPressed(int keyCode) {
		switch(keyCode) {
		case C.KEY_LEFT:
			if(index_choose > 0) index_choose --;
			break;
		case C.KEY_RIGHT:
			if(index_choose < 1) index_choose ++;
			break;
		case C.KEY_0 :
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime + CanvasControl.iptvID, "2", "update");
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 0));
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
		// TODO Auto-generated method stub
		
	}
}
