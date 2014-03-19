package com.dave.worldWar.view;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.showable.Showable;
import com.dave.worldWar.main.CanvasControl;
import com.dave.worldWar.tool.C;

/**
 * 训练营
 * 
 * @author 戴维
 * 
 */
public class Training implements Showable {

	private CanvasControl canvasControl;

	public Image img_back;
	public Image img_param_bar;
	public Image img_unchoose_bar;
	public Image img_choose_bar;
	public Image img_unchoose_bar_start;
	public Image img_choose_bar_start;
	public Image img_number_level;
	public Image img_number_param;
	public Image img_number_price;
	public Image img_unit_price;
	public Image img_word_start;
	public Image img_word_unlock;
	public Image img_word_uplevel;
	public Image img_lock;
	public Image[][] a_2_img_head;

	/**
	 * 选择指针的下标值，0：开始游戏。1~5 代表从低到高五种士兵。
	 */
	private int index = 1;

	public Training(CanvasControl canvasControl) {
		this.canvasControl = canvasControl;
	}

	public void show(Graphics g) {
		g.drawImage(img_back, 0, 0, 0);

		for (int i = 0; i < 5; i++) {
			g.drawImage(img_unchoose_bar, 76 + i * 121, 423, Graphics.VCENTER
					| Graphics.HCENTER);
		}

		g.drawImage(img_unchoose_bar_start, 320, 477, Graphics.VCENTER
				| Graphics.HCENTER);

		if (index == 0)
			g.drawImage(img_choose_bar_start, 320, 477, Graphics.VCENTER
					| Graphics.HCENTER);
		else
			g.drawImage(img_choose_bar, 76 + (index - 1) * 121, 423,
					Graphics.VCENTER | Graphics.HCENTER);

		g.drawImage(img_word_start, 320, 475, Graphics.VCENTER
				| Graphics.HCENTER);

		for (int i = 0; i < 5; i++) {
			if (CanvasControl.soldiers_level[i] == 0) {// 未解锁
				g.drawImage(a_2_img_head[i][0], 77 + i * 121, 210,
						Graphics.BOTTOM | Graphics.HCENTER);
				g.drawImage(img_lock, 74 + i * 121, 278, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(img_word_unlock, 76 + i * 121, 421,
						Graphics.VCENTER | Graphics.HCENTER);

				C.drawString(g, img_number_level, "1", "0123456789",
						98 + i * 121, 140, 13, 18, 0, 0, 0);

				C.drawString(g, img_number_price,
						canvasControl.anHui_Tool.getPrice(CanvasControl.A_GOODS_PARAM[i][0]), "0123456789.",
						60 + i * 121, 358, 12, 16, 0, 0, 0);
				g.drawImage(img_unit_price, 90 + i * 121, 357, 0);
			} else {// 解锁
				g.drawImage(a_2_img_head[i][1], 77 + i * 121, 210,
						Graphics.BOTTOM | Graphics.HCENTER);
				g.drawImage(img_param_bar, 74 + i * 121, 282, Graphics.VCENTER
						| Graphics.HCENTER);
				g.drawImage(img_word_uplevel, 76 + i * 121, 421,
						Graphics.VCENTER | Graphics.HCENTER);
				C.drawString(g, img_number_level,
						CanvasControl.soldiers_level[i] + "", "0123456789",
						98 + i * 121, 140, 13, 18, 0, 0, 0);

				C.drawString(g, img_number_price,
						canvasControl.anHui_Tool.getPrice(CanvasControl.A_GOODS_PARAM[i + 5][0]), "0123456789.",
						60 + i * 121, 358, 12, 16, 0, 0, 0);
				g.drawImage(img_unit_price, 90 + i * 121, 357, 0);

				C.drawString(
						g,
						img_number_param,
						(CanvasControl.soldiers_prpty[i][0] + 50 * (CanvasControl.soldiers_level[i] - 1))
								+ "", "0123456789", 63 + i * 121, 221, 10, 16,
						0, 0, 0);
				C.drawString(
						g,
						img_number_param,
						(CanvasControl.soldiers_prpty[i][1] + 50 * (CanvasControl.soldiers_level[i] - 1))
								+ "", "0123456789", 63 + i * 121, 243, 10, 16,
						0, 0, 0);
				C.drawString(g, img_number_param,
						(CanvasControl.soldiers_prpty[i][0] + 50 * (CanvasControl.soldiers_level[i])) + "",
						"0123456789", 63 + i * 121, 307, 10, 16, 0, 0, 0);
				C.drawString(g, img_number_param,
						(CanvasControl.soldiers_prpty[i][0] + 50 * (CanvasControl.soldiers_level[i])) + "",
						"0123456789", 63 + i * 121, 328, 10, 16, 0, 0, 0);
			}
		}
	}

	public void loadResource() {
	}

	public void removeResource() {
		img_back = null;
		img_param_bar = null;
		img_unchoose_bar = null;
		img_choose_bar = null;
		img_unchoose_bar_start = null;
		img_choose_bar_start = null;
		img_number_level = null;
		img_number_param = null;
		img_word_start = null;
		img_word_unlock = null;
		img_word_uplevel = null;
		img_lock = null;
		img_number_price = null;
		img_unit_price = null;

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 2; j++) {
				a_2_img_head[i][j] = null;
			}
		}
		a_2_img_head = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_LEFT:
			if (index > 0)
				index--;
			break;
		case C.KEY_RIGHT:
			if (index < 5)
				index++;
			break;
		case C.KEY_UP:
			if (index == 0)
				index = 3;
			break;
		case C.KEY_DOWN:
			if (index != 0)
				index = 0;
			break;
		case C.KEY_FIRE:
			if (index == 0) {// 开始游戏
				canvasControl.setGoBackView(this);
				canvasControl.setView(canvasControl.nullView);
				removeResource();
				if (CanvasControl.group == 0)// 第一次游戏，去到选择阵营
					canvasControl.setView(new Loading(canvasControl, 4));
				else
					// 继续游戏，进入游戏界面
					canvasControl.setView(new Loading(canvasControl, 1));
			} else {// 升级或解锁
				canvasControl.setGoBackView(this);
				if (CanvasControl.soldiers_level[index - 1] <= 0) {// 解锁
					CanvasControl.goodsIndex = index - 1;
					canvasControl.setView(new Dialog(canvasControl, 5, this));
				} else {// 升级
					CanvasControl.goodsIndex = index + 4;
					canvasControl.setView(new Dialog(canvasControl, 5, this));
				}
			}
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			canvasControl.setView(new Loading(canvasControl, 0));
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
