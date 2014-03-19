package com.dave.jpsc.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.net.ServerIptv;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

public class Ranking implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_rank_type;
	public Image img_select;
	public Image[] a_img_word_bar;

	private int index_choose;

	/**
	 * 进入排行榜时间
	 */
	private long rankingStartTime;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;

		rankingStartTime = System.currentTimeMillis();
		ServerIptv serverIptv = new ServerIptv(canvasControl);
		serverIptv.doGetRank(3, 0, "asc");
		serverIptv.doGetRank(4, 0);
		serverIptv.doGetRank(5, 0);
		serverIptv.sendGameTimeInfo(rankingStartTime
				+ CanvasControl.iptvID, "2", "add");
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		showRankString(g);
	}

	public void showRankString(Graphics g) {
		g.drawImage(img_select, index_choose * 215 + 105, 85, Graphics.VCENTER
				| Graphics.HCENTER);
		g.drawImage(img_rank_type, 320, 85, Graphics.VCENTER | Graphics.HCENTER);
		g.drawImage(a_img_word_bar[index_choose], 320, 130, Graphics.VCENTER
				| Graphics.HCENTER);

		g.setFont(C.FONT_SMALL_PLAIN);
		if (index_choose == 0) {
			if (CanvasControl.rank_win != null) {
				int size = CanvasControl.rank_win.size();
				g.setColor(255, 255, 255);
				for (int i = 0; i < size; i++) {
					String[] stra_t = new String[4];
					stra_t = (String[]) CanvasControl.rank_win.elementAt(i);

					g.drawString(stra_t[0], 30, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(C.deUserName_base64(stra_t[1]), 210, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(stra_t[2], 390, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(stra_t[3], 580, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
				}
			}
			
			g.setColor(255, 255, 0);
			g.drawString(CanvasControl.rankMe[index_choose] + "", 30, 480,  Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(canvasControl.me.nickName, 210, 480, Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(CanvasControl.winsTotal + "", 390, 480, Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(CanvasControl.gamesTotal + "", 580, 480, Graphics.HCENTER
					| Graphics.TOP);
		} else if (index_choose == 1) {
			if (CanvasControl.rank_level != null) {
				int size = CanvasControl.rank_level.size();
				g.setColor(255, 255, 255);
				for (int i = 0; i < size; i++) {
					String[] stra_t = new String[3];
					stra_t = (String[]) CanvasControl.rank_level.elementAt(i);

					g.drawString(stra_t[0], 30, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(C.deUserName_base64(stra_t[1]), 300, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(stra_t[2], 580, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
				}
			}
			
			g.setColor(255, 255, 0);
			g.drawString(CanvasControl.rankMe[index_choose] + "", 30, 480,  Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(canvasControl.me.nickName, 300, 480, Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(canvasControl.me.expeirence + "", 580, 480, Graphics.HCENTER
					| Graphics.TOP);
		} else if (index_choose == 2) {
			if (CanvasControl.rank_duel != null) {
				int size = CanvasControl.rank_duel.size();
				g.setColor(255, 255, 255);
				for (int i = 0; i < size; i++) {
					String[] stra_t = new String[3];
					stra_t = (String[]) CanvasControl.rank_duel.elementAt(i);

					g.drawString(stra_t[0], 30, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(C.deUserName_base64(stra_t[1]), 300, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
					g.drawString(stra_t[2], 580, 150 + i * 30, Graphics.HCENTER
							| Graphics.TOP);
				}
			}
			
			g.setColor(255, 255, 0);
			g.drawString(CanvasControl.rankMe[index_choose] + "", 30, 480,  Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(canvasControl.me.nickName, 300, 480, Graphics.HCENTER
					| Graphics.TOP);
			g.drawString(canvasControl.me.strength + "", 580, 480, Graphics.HCENTER
					| Graphics.TOP);
		}

		g.setColor(0, 0, 0);
		// C.drawString(g, img_rank_type, CanvasControl.rank_week + "",
		// "0123456789",
		// 80, 475, 20, 18, Graphics.HCENTER | Graphics.TOP, 0, 0);
		// C.drawString(g, img_rank_type, CanvasControl.iptvID + "",
		// "0123456789", 315, 475, 20, 18,
		// Graphics.TOP | Graphics.HCENTER, 0, 0);
		// C.drawString(g, img_rank_type, CanvasControl.score_week + "",
		// "0123456789", 520, 475, 20, 18,
		// Graphics.HCENTER | Graphics.TOP, 0, 0);
		// g.drawString(CanvasControl.iptvID, 275, 475, Graphics.TOP
		// | Graphics.HCENTER);
		// g.drawString((CanvasControl.totalScore) + "", 490, 475, Graphics.TOP
		// | Graphics.HCENTER);
	}

	public void loadResource() {
	}

	public void removeResource() {

	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT:
			if (index_choose > 0)
				index_choose--;
			break;
		case C.KEY_RIGHT:
			if (index_choose < 2)
				index_choose++;
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime
					+ CanvasControl.iptvID, "2", "update");
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Home(canvasControl));
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
		img_back = null;
		img_rank_type = null;
		img_select = null;
		for (int i = 0; i < a_img_word_bar.length; i++) {
			a_img_word_bar[i] = null;
		}
		a_img_word_bar = null;

		System.gc();
	}

	public void handleGoods(int goodsIndex) {

	}
}
