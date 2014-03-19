package com.dave.jpsc.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.jpsc.main.CanvasControl;
import com.dave.jpsc.tool.C;
import com.dave.showable.Showable;

/**
 * 副本
 * 
 * @author Dave
 * 
 */
public class Instance implements Showable {

	public CanvasControl canvasControl;

	public Image img_back;
	public Image img_back_param;
	public Image img_back_param_unopen;
	public Image img_lock;
	public Image img_number;
	public Image[] a_img_map_show;

	/**
	 * 起始的x值
	 */
	private int x_start = 320;

	private int index_choose;

	/**
	 * 运动方向0不动，1向右，2向左
	 */
	private int movedir;

	private int moveDist;

	public Instance(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);
		showInsInfo(g);
	}

	/**
	 * 显示副本信息
	 * 
	 * @param g
	 */
	private void showInsInfo(Graphics g) {
		for (int i = 0; i < 9; i++) {
			g.drawImage(a_img_map_show[i], x_start + i * 420, 250,
					Graphics.VCENTER | Graphics.HCENTER);
			if (i == 0) {
				g.drawImage(img_back_param, x_start + i * 420, 250,
						Graphics.VCENTER | Graphics.HCENTER);
				C.drawString(g, img_number, (i + 1) + "", "0123456789", x_start
						+ i * 420 + 15, 104, 14, 16, 0, 0, 0);
				g.setColor(0xffffff);
				g.setFont(C.FONT_MEDIUM_BOLD);
				g.drawString("20", x_start + i * 420 + 60, 152,
						Graphics.HCENTER | Graphics.TOP);
				if (CanvasControl.missionPassed < 16)
					g.drawImage(img_lock, x_start + i * 420, 250,
							Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(img_back_param_unopen, x_start + i * 420, 250,
						Graphics.VCENTER | Graphics.HCENTER);
				C.drawString(g, img_number, (i + 1) + "", "0123456789", x_start
						+ i * 420 + 15, 104, 14, 16, 0, 0, 0);
			}

		}
	}

	public void loadResource() {
	}

	public void removeResource() {
		img_back = null;
		System.gc();
	}

	public void keyPressed(int keyCode) {
		canvasControl.playKeySound();
		switch (keyCode) {
		case C.KEY_LEFT:
			if (index_choose > 0) {
				index_choose--;
				barMoveRight(420);
			}
			break;
		case C.KEY_RIGHT:
			if (index_choose < 8) {
				index_choose++;
				barMoveLeft(420);
			}
			break;
		case C.KEY_FIRE:
			if (CanvasControl.missionPassed >= 16) {
				CanvasControl.mission = 17;
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				this.removeServerImage();
				canvasControl.setView(new Loading(canvasControl, 7));
			}
			break;
		case C.KEY_1:
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
		case C.KEY_9:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			this.removeServerImage();
			canvasControl.setView(new Loading(canvasControl, 1));
			break;
		}
	}

	private void barMoveLeft(int dist) {
		if (movedir != 2)
			movedir = 2;

		moveDist += dist;
	}

	private void barMoveRight(int dist) {
		if (movedir != 1)
			movedir = 1;

		moveDist -= dist;
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
		if (movedir == 1) {
			moveDist += 60;
			x_start += 60;
			if (moveDist >= 0) {
				movedir = 0;
				moveDist = 0;
			}
		} else if (movedir == 2) {
			moveDist -= 60;
			x_start -= 60;
			if (moveDist <= 0) {
				moveDist = 0;
				movedir = 0;
			}
		}

	}

	public void removeServerImage() {
		img_back = null;
		img_back_param = null;
		img_back_param_unopen = null;
		img_lock = null;
		img_number = null;
		for (int i = 0; i < 9; i++) {
			a_img_map_show[i] = null;
		}
		a_img_map_show = null;
	}

	public void handleGoods(int goodsIndex) {

	}

}
