package com.dave.ftxz.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.net.ServerIptv;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;

public class Ranking implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_number_mine;
	public Image img_number_rank;
	public Image[][] a_2_img_date;

	private int index_choose;

	/**
	 * 进入排行榜时间
	 */
	private long rankingStartTime;

	public Ranking(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;

		rankingStartTime = System.currentTimeMillis();
		new ServerIptv(canvasControl).doGetRank(0, 2);
		new ServerIptv(canvasControl).doGetRank(0, 3);
		new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime
				+ CanvasControl.iptvID, "2", "add");
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		showRankString(g);
	}

	public void showRankString(Graphics g) {
		if (index_choose == 0) {
			g.drawImage(a_2_img_date[0][0], 461, 114, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.drawImage(a_2_img_date[0][2], 461, 90, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(a_2_img_date[1][0], 172, 114, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.drawImage(a_2_img_date[1][1], 172, 90, Graphics.HCENTER
					| Graphics.VCENTER);

			if (CanvasControl.rankInfo_month != null) {
				int size = CanvasControl.rankInfo_month.size();
				g.setColor(0, 0, 0);
				for (int i = 0; i < size; i++) {
					String[] stra_t = new String[3];
					stra_t = (String[]) CanvasControl.rankInfo_month
							.elementAt(i);

					if (i > 2)
						C.drawString(g, img_number_rank, stra_t[2],
								"0123456789", 55, 157 + i * 28, 16, 14,
								Graphics.HCENTER | Graphics.TOP, 0, 0);

					C.drawString(g, img_number_rank, stra_t[0],
							"0123456789", 315, 157 + i * 28, 16, 14,
							Graphics.HCENTER | Graphics.TOP, 0, 0);
					C.drawString(g, img_number_rank, stra_t[1],
							"0123456789", 550, 157 + i * 28, 16, 14,
							Graphics.HCENTER | Graphics.TOP, 0, 0);
				}
			}
		} else if (index_choose == 1) {
			g.drawImage(a_2_img_date[0][0], 172, 114, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.drawImage(a_2_img_date[0][1], 172, 90, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(a_2_img_date[1][0], 461, 114, Graphics.BOTTOM
					| Graphics.HCENTER);
			g.drawImage(a_2_img_date[1][2], 461, 90, Graphics.HCENTER
					| Graphics.VCENTER);

			if (CanvasControl.rankInfo_week != null) {
				int size = CanvasControl.rankInfo_week.size();
				g.setColor(0, 0, 0);
				for (int i = 0; i < size; i++) {
					String[] stra_t = new String[3];
					stra_t = (String[]) CanvasControl.rankInfo_week
							.elementAt(i);

					if (i > 2)
						C.drawString(g, img_number_rank, stra_t[2],
								"0123456789", 55, 157 + i * 28, 16, 14,
								Graphics.HCENTER | Graphics.TOP, 0, 0);
					C.drawString(g, img_number_rank, stra_t[0],
							"0123456789", 315, 157 + i * 28, 16, 14,
							Graphics.HCENTER | Graphics.TOP, 0, 0);
					C.drawString(g, img_number_rank, stra_t[1],
							"0123456789", 550, 157 + i * 28, 16, 14,
							Graphics.HCENTER | Graphics.TOP, 0, 0);
				}
			}
		}

		g.setColor(0, 0, 0);
		C.drawString(g, img_number_mine, CanvasControl.rank_week + "", "0123456789",
				80, 475, 20, 18, Graphics.HCENTER | Graphics.TOP, 0, 0);
		C.drawString(g, img_number_mine, CanvasControl.iptvID + "",
				"0123456789", 315, 475, 20, 18,
				Graphics.TOP | Graphics.HCENTER, 0, 0);
		C.drawString(g, img_number_mine, CanvasControl.score_week + "",
				"0123456789", 520, 475, 20, 18,
				Graphics.HCENTER | Graphics.TOP, 0, 0);
//		g.drawString(CanvasControl.iptvID, 275, 475, Graphics.TOP
//				| Graphics.HCENTER);
//		g.drawString((CanvasControl.totalScore) + "", 490, 475, Graphics.TOP
//				| Graphics.HCENTER);
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
			if (index_choose < 1)
				index_choose++;
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			new ServerIptv(canvasControl).sendGameTimeInfo(rankingStartTime
					+ CanvasControl.iptvID, "2", "update");
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
		img_back = null;
		img_number_mine = null;
		img_number_rank = null;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				a_2_img_date[i][j] = null;
			}
			a_2_img_date[i] = null;
		}
		a_2_img_date = null;

		System.gc();
	}
}
