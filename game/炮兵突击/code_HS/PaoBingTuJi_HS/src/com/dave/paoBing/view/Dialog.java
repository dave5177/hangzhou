package com.dave.paoBing.view;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.dave.paoBing.main.CanvasControl;
import com.dave.paoBing.tool.C;
import com.dave.showable.Showable;

public class Dialog implements Showable {
	private CanvasControl canvasControl;

	/**
	 * 对话框处在游戏中时所处的游戏
	 */
	private Game game;

	/**
	 * 对话框类型控制 0：退出整个程序对话框。 1：退出游戏对话框。 2：闯关成功。 3：购买成功。 4：购买失败。 5：购买生命。 6：购买子弹。
	 * 7：购买双倍威力。 8：闯关失败购买复活？ 10：当月购买已达到峰值。
	 */
	private int type;

	/**
	 * 记录选择用的下标值 0：是。（默认值） 1：否。
	 */
	private int index = 1;

	private Image img_back;
	private Image img_word;
	private Image img_yesWord;
	private Image img_noWord;
	private Image img_bar;
	private Image img_number;
	private Image img_pointer;

	private Image img_coin;
	private Image img_rmb;

	/**
	 * 道具价格
	 */
	public static final String[] GOODS_PRICE = { "5",// 生命价格
			"5",// 子弹价格
			"5",// 双倍威力价格
			"5",// 复活价格
	};

	public Dialog(CanvasControl canvasControl, int type, Game game) {
		this.canvasControl = canvasControl;
		this.game = game;
		canvasControl.setNeedRepaint(true);

		this.type = type;

		if (type == 2 || type == 3 || type == 4 || type == 10) {
			new Timer().schedule(new AutoCloseDialogTimerTask(), 1000);
		}
	}

	public void show(Graphics g) {
		g.drawImage(img_back, C.WIDTH / 2, C.HEIGTH / 2, Graphics.VCENTER
				| Graphics.HCENTER);// draw背景

		g.drawImage(img_word, C.WIDTH / 2, 250, Graphics.VCENTER
				| Graphics.HCENTER);// draw对话框文字

		if (type == 2 || type == 3 || type == 4 || type == 10)
			return;

		showGoodsPrice(g);

		// draw选择bar
		if (type == 8) {
			g.drawImage(img_bar, 320, 310, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_bar, 320, 360, Graphics.HCENTER | Graphics.VCENTER);

			g.drawImage(img_pointer, 260, 310 + index * 50, Graphics.RIGHT
					| Graphics.VCENTER);
			g.drawImage(img_yesWord, 320, 310, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”立即复活“
			g.drawImage(img_noWord, 320, 360, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”取消“
		} else {
			g.drawImage(img_bar, 250, 340, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_bar, 410, 340, Graphics.HCENTER | Graphics.VCENTER);

			g.drawImage(img_pointer, 220 + index * 160, 340, Graphics.RIGHT
					| Graphics.VCENTER);
			g.drawImage(img_yesWord, 250, 340, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”是“
			g.drawImage(img_noWord, 410, 340, Graphics.HCENTER
					| Graphics.VCENTER);// draw文字”否“
		}
	}

	/**
	 * @param g
	 *            draw道具的价格
	 */
	private void showGoodsPrice(Graphics g) {
		if (img_rmb == null) {
			try {
				img_rmb = Image.createImage("/dialog/rmb.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		switch (type) {
		case 5:// 生命价格
			C.drawString(g, img_number, GOODS_PRICE[0], "0123456789.", 225, 258,
					13, 18, 0, 0, 0);
			// C.drawString(
			// g,
			// img_number,
			// canvasControl.js_tool
			// .getSpecificPropsPrice(CanvasControl.GAME_PROP_GoodsCode_LIFE),
			// "0123456789.", 225, 258, 13, 20, 0, 0, 0);
			g.drawImage(img_rmb, 265, 255, 0);
			break;
		case 6:// 子弹价格
			C.drawString(g, img_number, GOODS_PRICE[1], "0123456789.", 380, 243,
					13, 18, 0, 0, 0);
			// C.drawString(
			// g,
			// img_number,
			// canvasControl.js_tool
			// .getSpecificPropsPrice(CanvasControl.GAME_PROP_GoodsCode_BULLET),
			// "0123456789.", 380, 243, 13, 20, 0, 0, 0);
			 g.drawImage(img_rmb, 420, 240, 0);
			break;
		case 7:// 双倍威力价格
			C.drawString(g, img_number, GOODS_PRICE[2], "0123456789.", 230, 243,
					13, 18, 0, 0, 0);
			// C.drawString(
			// g,
			// img_number,
			// canvasControl.js_tool
			// .getSpecificPropsPrice(CanvasControl.GAME_PROP_GoodsCode_DOUBLE),
			// "0123456789.", 230, 243, 13, 20, 0, 0, 0);
			g.drawImage(img_rmb, 270, 240, 0);
			break;
		}
	}

	public void loadResource() {
		try {
			img_back = Image.createImage("/dialog/back.png");
			img_number = Image.createImage("/dialog/number.png");
			img_pointer = Image.createImage("/dialog/pointer.png");
			if (type == 8) {
				img_yesWord = Image.createImage("/dialog/relive.png");
				img_noWord = Image.createImage("/dialog/cancel.png");
				img_bar = Image.createImage("/dialog/bar_big.png");
			} else {
				img_yesWord = Image.createImage("/dialog/yesWord.png");
				img_noWord = Image.createImage("/dialog/noWord.png");
				img_bar = Image.createImage("/dialog/bar.png");
			}

			loadWorldImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadWorldImage() throws IOException {
		if (type == 1) {
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
		img_bar = null;
		img_number = null;
		img_pointer = null;

		if (img_coin != null)
			img_coin = null;
		if (img_rmb != null)
			img_rmb = null;

		System.gc();
	}

	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case C.KEY_FIRE:
			if (index == 0) {
				switch (type) {
				case 0:// 退出游戏
					canvasControl.getMidlet().exitGame();
//					CanvasControl.willExit = true;
//					new ServerIptv(canvasControl).sendGameTimeInfo(
//							CanvasControl.appStartTime + CanvasControl.iptvID,
//							"1", "update");
					break;
				case 1:// 返回，到首页
					game.removeServerImage();
					// canvasControl.setView(new Home(canvasControl));
//					canvasControl.setView(new Loading(canvasControl, 2));
					canvasControl.setView(new Home(canvasControl));
					canvasControl.setGoBackView(null);
					this.removeResource();
					break;
				case 5:// 购买生命
					CanvasControl.goodsIndex = 1;

					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new CheckCode(canvasControl, this));
					// new
					// Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE,
					// CanvasControl.GAME_PROP_GoodsCode_LIFE);
					// try {
					// canvasControl.js_tool.do_BuyProp(
					// Image.createImage("/js_prop/js_sm.png"),
					// CanvasControl.GAME_PROP_GoodsCode_LIFE);
					// } catch (IOException e3) {
					// e3.printStackTrace();
					// }
					break;
				case 6:// 购买子弹
					CanvasControl.goodsIndex = 2;

					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new CheckCode(canvasControl, this));
					// new
					// Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE,
					// CanvasControl.GAME_PROP_GoodsCode_BULLET);
					// try {
					// canvasControl.js_tool.do_BuyProp(
					// Image.createImage("/js_prop/js_danYao.png"),
					// CanvasControl.GAME_PROP_GoodsCode_LIFE);
					// } catch (IOException e2) {
					// e2.printStackTrace();
					// }
					break;
				case 7:// 购买双倍威力
					CanvasControl.goodsIndex = 3;

					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new CheckCode(canvasControl, this));
					// new
					// Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE,
					// CanvasControl.GAME_PROP_GoodsCode_DOUBLE);
					// try {
					// canvasControl.js_tool.do_BuyProp(
					// Image.createImage("/js_prop/js_shuangbei.png"),
					// CanvasControl.GAME_PROP_GoodsCode_LIFE);
					// } catch (IOException e1) {
					// e1.printStackTrace();
					// }
					break;
				case 8:// 复活
					CanvasControl.goodsIndex = 4;

					canvasControl.setView(canvasControl.nullView);
					this.removeResource();
					canvasControl.setView(new CheckCode(canvasControl, this));
					// new
					// Prop(canvasControl).buyProp(CanvasControl.GAME_PROP_CODE,
					// CanvasControl.GAME_PROP_GoodsCode_RELIVE);
					// try {
					// canvasControl.js_tool.do_BuyProp(
					// Image.createImage("/js_prop/js_fuHuo.png"),
					// CanvasControl.GAME_PROP_GoodsCode_LIFE);
					// } catch (IOException e) {
					// e.printStackTrace();
					// }
					break;
				}
				System.gc();
			} else {
				canvasControl.setView(canvasControl.nullView);
				this.removeResource();
				if (type == 8) {
					game.removeServerImage();
					// canvasControl.setView(new Home(canvasControl));
//					canvasControl.setView(new Loading(canvasControl, 2));
					canvasControl.setView(new Home(canvasControl));
				} else
					canvasControl.setView(canvasControl.getGoBackView());
			}
			break;
		case C.KEY_LEFT:
			if (index == 1 && type != 8)
				index = 0;
			break;
		case C.KEY_RIGHT:
			if (index == 0 && type != 8)
				index = 1;
			break;
		case C.KEY_UP:
			if (index == 1 && type == 8)
				index = 0;
			break;
		case C.KEY_DOWN:
			if (index == 0 && type == 8)
				index = 1;
			break;
		case C.KEY_0:
		case C.KEY_BACK:
		case C.KEY_BACK_ZX:
			canvasControl.setView(canvasControl.nullView);
			this.removeResource();
			if (type == 8) {
				game.removeServerImage();
				// canvasControl.setView(new Home(canvasControl));
//				canvasControl.setView(new Loading(canvasControl, 2));
				canvasControl.setView(new Home(canvasControl));
			} else
				canvasControl.setView(canvasControl.getGoBackView());
			index = 0;
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

	class AutoCloseDialogTimerTask extends TimerTask {

		public void run() {
			if (type == 3)
				canvasControl.doBuySuccess();
			else if (type == 2) {
				if (CanvasControl.mission <= 25) {
					game.initMession();
				} else {
					CanvasControl.mission = 1;
					canvasControl.setView(new Complete(canvasControl));
					canvasControl.repaint();
					Dialog.this.removeResource();
					return;
				}
			}

			if(CanvasControl.goodsIndex == 4 && type == 4) {
//				canvasControl.setView(new Loading(canvasControl, 2));
				canvasControl.setView(new Home(canvasControl));
			} else {
				canvasControl.setView(canvasControl.getGoBackView());
			}
			canvasControl.repaint();
			Dialog.this.removeResource();
		}
	}

}
