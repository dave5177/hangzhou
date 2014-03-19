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
	 * �Ի�������Ϸ��ʱ��������Ϸ
	 */
	private Game game;

	/**
	 * �Ի������Ϳ��� 0���˳���������Ի��� 1���˳���Ϸ�Ի��� 2�����سɹ��� 3������ɹ��� 4������ʧ�ܡ� 5������������ 6�������ӵ���
	 * 7������˫�������� 8������ʧ�ܹ��򸴻 10�����¹����Ѵﵽ��ֵ��
	 */
	private int type;

	/**
	 * ��¼ѡ���õ��±�ֵ 0���ǡ���Ĭ��ֵ�� 1����
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
	 * ���߼۸�
	 */
	public static final String[] GOODS_PRICE = { "5",// �����۸�
			"5",// �ӵ��۸�
			"5",// ˫�������۸�
			"5",// ����۸�
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
				| Graphics.HCENTER);// draw����

		g.drawImage(img_word, C.WIDTH / 2, 250, Graphics.VCENTER
				| Graphics.HCENTER);// draw�Ի�������

		if (type == 2 || type == 3 || type == 4 || type == 10)
			return;

		showGoodsPrice(g);

		// drawѡ��bar
		if (type == 8) {
			g.drawImage(img_bar, 320, 310, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_bar, 320, 360, Graphics.HCENTER | Graphics.VCENTER);

			g.drawImage(img_pointer, 260, 310 + index * 50, Graphics.RIGHT
					| Graphics.VCENTER);
			g.drawImage(img_yesWord, 320, 310, Graphics.HCENTER
					| Graphics.VCENTER);// draw���֡��������
			g.drawImage(img_noWord, 320, 360, Graphics.HCENTER
					| Graphics.VCENTER);// draw���֡�ȡ����
		} else {
			g.drawImage(img_bar, 250, 340, Graphics.HCENTER | Graphics.VCENTER);
			g.drawImage(img_bar, 410, 340, Graphics.HCENTER | Graphics.VCENTER);

			g.drawImage(img_pointer, 220 + index * 160, 340, Graphics.RIGHT
					| Graphics.VCENTER);
			g.drawImage(img_yesWord, 250, 340, Graphics.HCENTER
					| Graphics.VCENTER);// draw���֡��ǡ�
			g.drawImage(img_noWord, 410, 340, Graphics.HCENTER
					| Graphics.VCENTER);// draw���֡���
		}
	}

	/**
	 * @param g
	 *            draw���ߵļ۸�
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
		case 5:// �����۸�
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
		case 6:// �ӵ��۸�
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
		case 7:// ˫�������۸�
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
				case 0:// �˳���Ϸ
					canvasControl.getMidlet().exitGame();
//					CanvasControl.willExit = true;
//					new ServerIptv(canvasControl).sendGameTimeInfo(
//							CanvasControl.appStartTime + CanvasControl.iptvID,
//							"1", "update");
					break;
				case 1:// ���أ�����ҳ
					game.removeServerImage();
					// canvasControl.setView(new Home(canvasControl));
//					canvasControl.setView(new Loading(canvasControl, 2));
					canvasControl.setView(new Home(canvasControl));
					canvasControl.setGoBackView(null);
					this.removeResource();
					break;
				case 5:// ��������
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
				case 6:// �����ӵ�
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
				case 7:// ����˫������
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
				case 8:// ����
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
