package com.dave.ftxz.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.ftxz.main.CanvasControl;
import com.dave.ftxz.net.ServerIptv;
import com.dave.ftxz.tool.C;
import com.dave.showable.Showable;

public class Dialog implements Showable {
	
	private CanvasControl canvasControl;

	/**
	 * 对话框处在的界面
	 */
	private Showable lastView;

	/**
	 * 对话框类型控制 0：退出整个程序对话框。 1：退出游戏对话框。2：自定义对话框。 3：购买成功。 4：购买失败。 5：购买道具。 6：闯关成功。
	 * 7：闯关失败。 10：当月购买已达到峰值。
	 */
	private int type;

	/**
	 * 记录选择用的下标值 0：是。（默认值） 1：否。
	 */
	private int index = 0;

	private Image img_back;
	private Image img_word;
	private Image img_yesWord;
	private Image img_noWord;
	private Image img_button;
	private Image img_number;
	private Image img_number_score;
	private Image img_pointer;
	private Image img_word_top;// 飞行结束
	private Image img_result_bar;

	private Image img_goods_name;
	private Image img_goods_info;

	private Image img_unit_price;// 价格单位

	private Image img_own;

	public Dialog(CanvasControl canvasControl, int type, Showable lastView) {
		this.canvasControl = canvasControl;
		this.lastView = lastView;

		this.type = type;

		if (type == 3 || type == 4 || type == 10) {
			new Timer().schedule(new AutoCloseDialogTimerTask(), 1000);
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, C.WIDTH / 2, 254, Graphics.VCENTER
				| Graphics.HCENTER);// draw背景

		if (type == 5) {
			g.drawImage(img_goods_name, 235, 167, 0);
			g.drawImage(img_goods_info, 235, 247, 0);
			showGoodsPrice(g);
		} else if (type != 7 && type != 1 && type != 2) {
			g.drawImage(img_word, C.WIDTH / 2, 220, Graphics.VCENTER
					| Graphics.HCENTER);// draw对话框文字
		}

		if (type == 2) {
			g.drawImage(img_word_top, C.WIDTH / 2, 198, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawImage(img_word, C.WIDTH / 2, 280, Graphics.VCENTER
					| Graphics.HCENTER);// draw对话框文字
			g.drawImage(img_pointer, 166 + index * 103, 279, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(img_own, 200, 305, Graphics.HCENTER | Graphics.VCENTER);
			for (int i = 0; i < 3; i++) {
				if (CanvasControl.goodsNumber[i] > 0)
					g.drawImage(img_own, 303 + i * 103, 305, Graphics.HCENTER
							| Graphics.VCENTER);
			}
			return;
		}

		if (type == 3 || type == 4 || type == 10)
			return;

		if (type == 1) {
			g.drawImage(img_word, C.WIDTH / 2, 180, Graphics.VCENTER
					| Graphics.HCENTER);// draw对话框文字
			g.drawImage(img_result_bar, C.WIDTH / 2, 240, Graphics.VCENTER
					| Graphics.HCENTER);
			C.drawString(g, img_number_score, CanvasControl.coin_total + "",
					"0123456789.-", 225, 218, 11, 16, 0, 0, 0);
			C.drawString(g, img_number_score, CanvasControl.distance + "",
					"0123456789.-", 225, 248, 11, 16, 0, 0, 0);
		}

		if (type == 7) {
			g.drawImage(img_word_top, C.WIDTH / 2, 178, Graphics.VCENTER
					| Graphics.HCENTER);
			g.drawImage(img_result_bar, C.WIDTH / 2, 230, Graphics.VCENTER
					| Graphics.HCENTER);
			C.drawString(g, img_number_score, CanvasControl.coin_total + "",
					"0123456789.-", 225, 208, 11, 16, 0, 0, 0);
			C.drawString(g, img_number_score, CanvasControl.distance + "",
					"0123456789.-", 225, 238, 11, 16, 0, 0, 0);
			g.drawImage(img_button, 320, 290, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(img_button, 320, 330, Graphics.HCENTER
					| Graphics.VCENTER);

			g.drawImage(img_pointer, 320, 290 + index * 40, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(img_yesWord, 320, 290, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”是“
			g.drawImage(img_noWord, 320, 330, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”否“
		} else {
			// draw选择bar
			g.drawImage(img_button, 250, 320, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(img_button, 410, 320, Graphics.HCENTER
					| Graphics.VCENTER);

			g.drawImage(img_pointer, 250 + index * 160, 320, Graphics.HCENTER
					| Graphics.VCENTER);
			g.drawImage(img_yesWord, 250, 320, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”是“
			g.drawImage(img_noWord, 410, 320, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”否“
		}

	}

	/**
	 * @param g
	 *            draw道具的价格
	 */
	private void showGoodsPrice(Graphics g) {
		C.drawString(g, img_number,
				CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][1],
				"0123456789.", 235, 208, 10, 15, 0, 0, 0);
		g.drawImage(img_unit_price,
				235 + CanvasControl.A_GOODS_PARAM[CanvasControl.goodsIndex][1]
						.length() * 10, 206, 0);
	}

	public void loadResource() {
		try {
			img_number = Image.createImage("/dialog/number.png");
			img_button = Image.createImage("/dialog/button.png");
			img_number_score = Image.createImage("/dialog/number_score.png");
			img_result_bar = Image.createImage("/dialog/result_bar.png");

			if (type == 2) {
				img_pointer = Image.createImage("/dialog/pointer_2.png");
				img_word_top = Image.createImage("/dialog/word_top_2.png");
				img_own = Image.createImage("/dialog/own.png");
			} else {
				img_pointer = Image.createImage("/dialog/pointer.png");
				img_word_top = Image.createImage("/dialog/word_top.png");
			}

			if (type == 7) {
				img_yesWord = Image.createImage("/dialog/word_continue.png");
				img_noWord = Image.createImage("/dialog/word_restart.png");
			} else {
				img_yesWord = Image.createImage("/dialog/yesWord.png");
				img_noWord = Image.createImage("/dialog/noWord.png");
			}

			if (type == 5) {
				img_back = Image.createImage("/dialog/back_5.png");
				img_unit_price = Image.createImage("/dialog/yuan.png");
				img_goods_name = Image.createImage("/dialog/goods_name_"
						+ CanvasControl.goodsIndex + ".png");
				img_goods_info = Image.createImage("/dialog/goods_info_"
						+ CanvasControl.goodsIndex + ".png");
			} else {
				img_back = Image.createImage("/dialog/back.png");
				loadWorldImage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadWorldImage() throws IOException {
		if (type == 1 || type == 7) {
			img_word = Image.createImage("/dialog/word_0.png");
		} else {
			img_word = Image.createImage("/dialog/word_" + type + ".png");
		}
	}

	public void removeResource() {
		img_back = null;
		img_word = null;
		img_yesWord = null;
		img_noWord = null;
		img_button = null;
		img_number = null;
		img_pointer = null;
		img_word_top = null;
		img_number_score = null;
		img_result_bar = null;
		img_own = null;

		if (type == 5) {
			img_unit_price = null;
			img_goods_name = null;
			img_goods_info = null;

		}

		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_FIRE:
			if (type == 2) {// 选好英雄开始游戏，没有则弹出购买对话框。
				if (index > 0) {
					if (CanvasControl.goodsNumber[index - 1] > 0) {
						CanvasControl.type_hero = index;
						CanvasControl.goodsNumber[index - 1]--;
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						canvasControl.setView(new Game(canvasControl));
					} else {// 购买
						CanvasControl.goodsIndex = index - 1;
						CanvasControl.willStart = true;
						canvasControl.setGoBackView(this);
						canvasControl.setView(canvasControl.nullView);
						this.removeResource();
						canvasControl.setView(new Dialog(canvasControl, 5, this));
					}
				} else {
					CanvasControl.type_hero = index;
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Game(canvasControl));
				}
				break;
			}

			if (index == 0) {
				switch (type) {
				case 0:// 退出游戏
					CanvasControl.willExit = true;
					canvasControl.getMidlet().exitGame();
					break;
				case 1:// 返回，到首页
					new ServerIptv(canvasControl).doSendScore(
							CanvasControl.distance, 0);
//					canvasControl.xj_tool.saveScore(0, CanvasControl.distance);
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Home(canvasControl));
					break;
				case 5:
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new CheckCode(canvasControl));
					break;
				case 6:
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Shop(canvasControl));
					break;
				case 7:// 购买复活
					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new Dialog(canvasControl, 5, this));
					break;
				}
				System.gc();
			} else {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				if (type == 7) {
					new ServerIptv(canvasControl).doSendScore(
							CanvasControl.distance, 0);
//					canvasControl.xj_tool.saveScore(0, CanvasControl.distance);
					lastView.removeServerImage();
					canvasControl.setGoBackView(null);
					canvasControl.setView(new Shop(canvasControl));
				} else
					canvasControl.setView(lastView);
			}
			break;
		case C.KEY_LEFT:
			if (type == 2) {
				if (index > 0)
					index--;
			} else if (index == 1 && type != 7)
				index = 0;
			break;
		case C.KEY_RIGHT:
			if (type == 2) {
				if (index < 3)
					index++;
			} else if (index == 0 && type != 7)
				index = 1;
			break;
		case C.KEY_UP:
			if (index == 1 && type == 7)
				index = 0;
			break;
		case C.KEY_DOWN:
			if (index == 0 && type == 7)
				index = 1;
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			if (type == 2) {
				CanvasControl.type_hero = 0;
				canvasControl.setView(new Game(canvasControl));
			} else if (type == 7) {
				lastView.removeServerImage();
				canvasControl.setGoBackView(null);
				canvasControl.setView(new Home(canvasControl));
			} else
				canvasControl.setView(lastView);
			index = 0;
			break;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void keyRepeated(int keyCode) {

	}

	public void logic() {
	}

	/**
	 * @author Administrator
	 * 自动关闭对话框任务
	 */
	class AutoCloseDialogTimerTask extends TimerTask {

		public void run() {
			if (type == 3)
				canvasControl.doBuySuccess();

			canvasControl.setView(canvasControl.nullView);
			removeResource();
			if (CanvasControl.willStart && type == 3) {// 开始游戏
				CanvasControl.willStart = false;
				CanvasControl.type_hero = CanvasControl.goodsIndex + 1;
				CanvasControl.goodsNumber[CanvasControl.goodsIndex]--;
				canvasControl.setView(new Game(canvasControl));
			} else if (CanvasControl.goodsIndex == 9 && type != 3) {
				canvasControl.setView(new Dialog(canvasControl, 7,
						canvasControl.getGoBackView()));
			} else {
				canvasControl.setView(canvasControl.getGoBackView());
			}
			canvasControl.repaint();
			Dialog.this.removeResource();
		}
	}

	public void removeServerImage() {

	}

}
